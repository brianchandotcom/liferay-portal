/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.service.impl;

import com.liferay.marketplace.internal.service.permission.MarketplacePermission;
import com.liferay.marketplace.model.App;
import com.liferay.marketplace.service.base.AppServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.File;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ryan Park
 */
@Component(
	property = {
		"json.web.service.context.name=marketplace",
		"json.web.service.context.path=App"
	},
	service = AopService.class
)
public class AppServiceImpl extends AppServiceBaseImpl {

	@Override
	public App deleteApp(long appId) throws PortalException {
		MarketplacePermission.check(getPermissionChecker());

		return appLocalService.deleteApp(appId);
	}

	@Override
	public void installApp(long remoteAppId) throws PortalException {
		MarketplacePermission.check(getPermissionChecker());

		appLocalService.installApp(remoteAppId);
	}

	@Override
	public void uninstallApp(long remoteAppId) throws PortalException {
		MarketplacePermission.check(getPermissionChecker());

		appLocalService.uninstallApp(remoteAppId);
	}

	@Override
	public App updateApp(File file) throws PortalException {
		MarketplacePermission.check(getPermissionChecker());

		return appLocalService.updateApp(getUserId(), file);
	}

}