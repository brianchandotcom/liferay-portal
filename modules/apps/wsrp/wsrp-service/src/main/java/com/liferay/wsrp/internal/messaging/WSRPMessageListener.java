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

package com.liferay.wsrp.internal.messaging;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.HotDeployMessageListener;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.wsrp.internal.jmx.WSRPConsumerPortletManager;
import com.liferay.wsrp.service.WSRPConsumerPortletLocalService;
import com.liferay.wsrp.util.ExtensionHelperUtil;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Shuyang Zhou
 * @author Peter Fellwock
 */
@Component(
	immediate = true, property = {"destination.name=" + DestinationNames.WSRP},
	service = MessageListener.class
)
public class WSRPMessageListener extends HotDeployMessageListener {

	public void destroy() {
		_serviceTracker.close();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext, MBeanServer.class,
			new MBeanServerServiceTrackerCustomizer());

		_serviceTracker.open();

		ExtensionHelperUtil.initialize();

		try {
			_wSRPConsumerPortletLocalService.destroyWSRPConsumerPortlets();
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to destroy WSRP consumer portlets", pe);
			}
		}

		_wSRPConsumerPortletLocalService.initWSRPConsumerPortlets();
	}

	@Deactivate
	protected void deactivate(BundleContext bundleContext) {
		try {
			_wSRPConsumerPortletLocalService.destroyWSRPConsumerPortlets();
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to destroy WSRP consumer portlets", pe);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WSRPMessageListener.class);

	private BundleContext _bundleContext;
	private ServiceTracker<MBeanServer, MBeanServer> _serviceTracker;

	@Reference
	private WSRPConsumerPortletLocalService _wSRPConsumerPortletLocalService;

	private class MBeanServerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<MBeanServer, MBeanServer> {

		@Override
		public MBeanServer addingService(
			ServiceReference<MBeanServer> serviceReference) {

			MBeanServer mBeanServer = _bundleContext.getService(
				serviceReference);

			try {
				mBeanServer.registerMBean(
					new WSRPConsumerPortletManager(),
					new ObjectName(
						"com.liferay.wsrp:classification=wsrp,name=" +
							"WSRPConsumerPortletManager"));
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to register WSRP consumer portlet manager", e);
				}
			}

			return mBeanServer;
		}

		@Override
		public void modifiedService(
			ServiceReference<MBeanServer> serviceReference,
			MBeanServer mBeanServer) {
		}

		@Override
		public void removedService(
			ServiceReference<MBeanServer> serviceReference,
			MBeanServer mBeanServer) {
		}

	}

}