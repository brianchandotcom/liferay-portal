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

package com.liferay.portal.osgi.web.wab.extender.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.osgi.web.servlet.JSPServletFactory;
import com.liferay.portal.osgi.web.servlet.JSPTaglibHelper;
import com.liferay.portal.osgi.web.servlet.context.helper.ServletContextHelperFactory;
import com.liferay.portal.osgi.web.wab.extender.internal.configuration.WabExtenderConfiguration;
import com.liferay.portal.profile.PortalProfile;

import java.util.Dictionary;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Miguel Pastor
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.portal.osgi.web.wab.extender.internal.configuration.WabExtenderConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	service = {}
)
public class WabFactory
	implements BundleTrackerCustomizer<WabFactory.WABExtension> {

	@Override
	public WABExtension addingBundle(Bundle bundle, BundleEvent bundleEvent) {
		String contextPath = WabUtil.getWebContextPath(bundle);

		if (contextPath == null) {
			return null;
		}

		WABExtension wabExtension = new WABExtension(bundle);

		wabExtension.start();

		return wabExtension;
	}

	@Override
	public void modifiedBundle(
		Bundle bundle, BundleEvent bundleEvent, WABExtension wabExtension) {
	}

	@Override
	public void removedBundle(
		Bundle bundle, BundleEvent bundleEvent, WABExtension wabExtension) {

		wabExtension.destroy();
	}

	public class WABExtension {

		public WABExtension(Bundle bundle) {
			_bundle = bundle;
		}

		public void destroy() {
			try {
				_started.await(
					_wabExtenderConfiguration.stopTimeout(),
					TimeUnit.MILLISECONDS);
			}
			catch (InterruptedException ie) {
				_log.error(
					String.format(
						"The wait for bundle {0}/{1} to start before " +
							"destroying was interrupted",
						_bundle.getSymbolicName(), _bundle.getBundleId()),
					ie);
			}

			if (_serviceRegistration != null) {
				_serviceRegistration.unregister();
			}

			_webBundleDeployer.doStop(_bundle);
		}

		public void start() {
			try {
				_serviceRegistration = _webBundleDeployer.doStart(_bundle);
			}
			finally {
				_started.countDown();
			}
		}

		private final Bundle _bundle;
		private ServiceRegistration<PortalProfile> _serviceRegistration;
		private final CountDownLatch _started = new CountDownLatch(1);

	}

	@Activate
	protected void activate(ComponentContext componentContext) {
		BundleContext bundleContext = componentContext.getBundleContext();

		Dictionary<String, Object> properties =
			componentContext.getProperties();

		_wabExtenderConfiguration = ConfigurableUtil.createConfigurable(
			WabExtenderConfiguration.class, properties);

		_webBundleDeployer = new WebBundleDeployer(
			bundleContext, _jspServletFactory, _jspTaglibHelper, properties);

		_bundleTracker = new BundleTracker<>(
			bundleContext, Bundle.ACTIVE | Bundle.STARTING, this);

		_bundleTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_bundleTracker.close();

		_webBundleDeployer.close();

		_webBundleDeployer = null;
	}

	private static final Log _log = LogFactoryUtil.getLog(WabFactory.class);

	private BundleTracker<?> _bundleTracker;

	@Reference
	private JSPServletFactory _jspServletFactory;

	@Reference
	private JSPTaglibHelper _jspTaglibHelper;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private ServletContextHelperFactory _servletContextHelperFactory;

	private WabExtenderConfiguration _wabExtenderConfiguration;
	private WebBundleDeployer _webBundleDeployer;

}