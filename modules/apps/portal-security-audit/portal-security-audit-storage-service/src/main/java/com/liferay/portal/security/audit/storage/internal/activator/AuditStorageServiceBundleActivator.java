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

package com.liferay.portal.security.audit.storage.internal.activator;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.upgrade.release.BaseUpgradeServiceModuleRelease;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Alan Huang
 */
public class AuditStorageServiceBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Filter filter = bundleContext.createFilter(
			StringBundler.concat(
				"(&(objectClass=", ModuleServiceLifecycle.class.getName(), ")",
				ModuleServiceLifecycle.DATABASE_INITIALIZED, ")"));

		_serviceTracker = new ServiceTracker<Object, Object>(
			bundleContext, filter, null) {

			@Override
			public Object addingService(ServiceReference<Object> reference) {
				try {
					BaseUpgradeServiceModuleRelease
						upgradeServiceModuleRelease =
							new BaseUpgradeServiceModuleRelease() {

								@Override
								protected String getNamespace() {
									return "Audit";
								}

								@Override
								protected String getNewBundleSymbolicName() {
									return "com.liferay.portal.security." +
										"audit.storage.service";
								}

								@Override
								protected String getOldBundleSymbolicName() {
									return "audit-portlet";
								}

							};

					upgradeServiceModuleRelease.upgrade();

					return null;
				}
				catch (UpgradeException ue) {
					throw new RuntimeException(ue);
				}
			}

		};

		_serviceTracker.open();
	}

	@Override
	public void stop(BundleContext context) {
		_serviceTracker.close();
	}

	private ServiceTracker<Object, Object> _serviceTracker;

}