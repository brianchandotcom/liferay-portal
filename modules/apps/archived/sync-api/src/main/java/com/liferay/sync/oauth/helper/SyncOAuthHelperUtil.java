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

package com.liferay.sync.oauth.helper;

import com.liferay.portal.kernel.service.ServiceContext;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Shinn Lok
 */
@Component(immediate = true, service = SyncOAuthHelperUtil.class)
public class SyncOAuthHelperUtil {

	public static void enableOAuth(
			long companyId, ServiceContext serviceContext)
		throws Exception {

		SyncOAuthHelper syncOAuthHelper = getService();

		syncOAuthHelper.enableOAuth(companyId, serviceContext);
	}

	public static SyncOAuthHelper getService() {
		ServiceReference<SyncOAuthHelper> serviceReference =
			_bundleContext.getServiceReference(SyncOAuthHelper.class);

		if (serviceReference != null) {
			SyncOAuthHelper syncOAuthHelper = _bundleContext.getService(
				serviceReference);

			if (syncOAuthHelper != null) {
				return syncOAuthHelper;
			}
		}

		return new SyncOAuthHelper() {

			public void enableOAuth(
				long companyId, ServiceContext serviceContext) {
			}

			public boolean isDeployed() {
				return false;
			}

			public boolean isOAuthApplicationAvailable(
				long oAuthApplicationId) {

				return false;
			}

		};
	}

	public static boolean isDeployed() {
		SyncOAuthHelper syncOAuthHelper = getService();

		return syncOAuthHelper.isDeployed();
	}

	public static boolean isOAuthApplicationAvailable(long oAuthApplicationId) {
		SyncOAuthHelper syncOAuthService = getService();

		return syncOAuthService.isOAuthApplicationAvailable(oAuthApplicationId);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private static BundleContext _bundleContext;

}