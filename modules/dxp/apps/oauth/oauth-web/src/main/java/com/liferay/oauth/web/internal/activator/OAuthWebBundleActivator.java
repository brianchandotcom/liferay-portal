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

package com.liferay.oauth.web.internal.activator;

import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.upgrade.release.BaseUpgradeWebModuleRelease;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Carlos Sierra Andrés
 */
public class OAuthWebBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Filter filter = bundleContext.createFilter(
			"(&(objectClass=" + ModuleServiceLifecycle.class.getName() + ")" +
				ModuleServiceLifecycle.DATABASE_INITIALIZED + ")");

		_serviceTracker = new ServiceTracker<Object, Object>(
			bundleContext, filter, null) {

			@Override
			public Object addingService(ServiceReference<Object> reference) {
				BaseUpgradeWebModuleRelease upgradeWebModuleRelease =
					new BaseUpgradeWebModuleRelease() {

						@Override
						protected String getBundleSymbolicName() {
							return "com.liferay.oauth.web";
						}

						@Override
						protected String[] getPortletIds() {
							return new String[] {
								"1_WAR_oauthportlet", "2_WAR_oauthportlet",
								"3_WAR_oauthportlet"
							};
						}

					};

				try {
					upgradeWebModuleRelease.upgrade();
				}
				catch (UpgradeException ue) {
					throw new RuntimeException(ue);
				}

				return null;
			}

		};

		_serviceTracker.open();
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		_serviceTracker.close();
	}

	private ServiceTracker<Object, Object> _serviceTracker;

}