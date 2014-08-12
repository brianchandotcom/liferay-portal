/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.wab.extender.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.wab.extender.internal.event.EventUtil;

import org.eclipse.equinox.http.servlet.ExtendedHttpService;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Miguel Pastor
 * @author Raymond Augé
 */
@Component(
	immediate = true
)
public class WabFactory {

	@Activate
	public void activate(ComponentContext componentContext) {
		_bundleContext = componentContext.getBundleContext();

		EventUtil.start(_bundleContext);

		try {
			_webBundleDeployer = new WebBundleDeployer(
				_bundleContext, _extendedHttpService);

			_startedBundleListener = new StartedBundleListener(
				_webBundleDeployer);

			_bundleContext.addBundleListener(_startedBundleListener);

			_stoppedBundleListener = new StoppedBundleListener(
				_webBundleDeployer);

			_bundleContext.addBundleListener(_stoppedBundleListener);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		checkStartableBundles();
	}

	@Deactivate
	public void deactivate() {
		_webBundleDeployer.close();

		_webBundleDeployer = null;

		_bundleContext.removeBundleListener(_startedBundleListener);

		_startedBundleListener = null;

		_bundleContext.removeBundleListener(_stoppedBundleListener);

		_stoppedBundleListener = null;

		_bundleContext = null;

		EventUtil.close();
	}

	protected void checkStartableBundles() {
		for (Bundle bundle : _bundleContext.getBundles()) {
			String servletContextName = WabUtil.getWebContextName(bundle);

			if (Validator.isNull(servletContextName)) {
				continue;
			}

			try {
				_webBundleDeployer.doStart(bundle, servletContextName);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	@Reference
	protected void setExtendedHttpService(
		ExtendedHttpService extendedHttpService) {

		_extendedHttpService = extendedHttpService;
	}

	protected void unsetExtendedHttpService(
		ExtendedHttpService extendedHttpService) {

		_extendedHttpService = null;
	}

	private static final Log _log = LogFactoryUtil.getLog(WabFactory.class);

	private BundleContext _bundleContext;
	private ExtendedHttpService _extendedHttpService;
	private StartedBundleListener _startedBundleListener;
	private StoppedBundleListener _stoppedBundleListener;
	private WebBundleDeployer _webBundleDeployer;

}