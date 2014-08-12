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
import com.liferay.portal.wab.extender.internal.event.EventUtil;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.equinox.http.servlet.ExtendedHttpService;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class WebBundleDeployer {

	public WebBundleDeployer(
			BundleContext bundleContext,
			ExtendedHttpService extendedHttpService)
		throws Exception {

		_bundleContext = bundleContext;
		_extendedHttpService = extendedHttpService;
	}

	public void close() {
		Set<Bundle> bundles = _wabBundleProcessors.keySet();

		for (Bundle bundle : bundles) {
			try {
				doStop(bundle);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	public void doStart(Bundle bundle, String servletContextName) {
		EventUtil.sendEvent(bundle, EventUtil.DEPLOYING, null, false);

		try {
			String contextPath = WabUtil.getWebContextPath(bundle);

			WabBundleProcessor wabBundleProcessor = new WabBundleProcessor(
				bundle, servletContextName, contextPath, _extendedHttpService);

			wabBundleProcessor.init();

			_wabBundleProcessors.put(bundle, wabBundleProcessor);
		}
		catch (Exception e) {
			EventUtil.sendEvent(bundle, EventUtil.FAILED, e, false);
		}
	}

	public void doStop(Bundle bundle) {
		WabBundleProcessor wabBundleProcessor = _wabBundleProcessors.remove(
			bundle);

		if (wabBundleProcessor == null) {
			throw new IllegalArgumentException("Bundle is not a WAB");
		}

		EventUtil.sendEvent(bundle, EventUtil.UNDEPLOYING, null, false);

		try {
			wabBundleProcessor.destroy();
		}
		catch (Exception e) {
			EventUtil.sendEvent(bundle, EventUtil.FAILED, e, false);
		}

		EventUtil.sendEvent(bundle, EventUtil.UNDEPLOYED, null, false);

		handleCollidedWABs(bundle);
	}

	protected void handleCollidedWABs(Bundle bundle) {
		String servletContextName = WabUtil.getWebContextName(bundle);

		Set<Bundle> wabBundles = _wabBundleProcessors.keySet();

		for (Bundle curBundle : _bundleContext.getBundles()) {
			if (WabUtil.isFragmentBundle(curBundle) ||
				WabUtil.isNotActiveBundle(bundle) ||
				wabBundles.contains(curBundle) ||
				bundle.equals(curBundle)) {

				continue;
			}

			String curServletContextName = WabUtil.getWebContextName(curBundle);

			if (servletContextName.equals(curServletContextName)) {
				doStart(curBundle, servletContextName);

				break;
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WebBundleDeployer.class);

	private final BundleContext _bundleContext;
	private final ExtendedHttpService _extendedHttpService;
	private final Map<Bundle, WabBundleProcessor> _wabBundleProcessors =
		new ConcurrentHashMap<Bundle, WabBundleProcessor>();

}