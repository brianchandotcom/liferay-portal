/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.core.internal.util;

import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserService;

import java.util.UUID;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zoltán Takács
 */
@Component(service = ServiceContextHelper.class)
public class ServiceContextHelperImpl implements ServiceContextHelper {

	@Override
	public ServiceContext getServiceContext() throws PortalException {
		return getServiceContext(0, new long[0], null, false);
	}

	@Override
	public ServiceContext getServiceContext(long groupId)
		throws PortalException {

		return getServiceContext(groupId, new long[0], null, false);
	}

	@Override
	public ServiceContext getServiceContext(
			long groupId, long[] assetCategoryIds, User user)
		throws PortalException {

		return getServiceContext(groupId, assetCategoryIds, user, false);
	}

	@Override
	public ServiceContext getServiceContext(
			long groupId, long[] assetCategoryIds, User user,
			boolean generateUuid)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			serviceContext = new ServiceContext();
		}

		serviceContext = (ServiceContext)serviceContext.clone();

		if (user == null) {
			user = _userService.getUserById(PrincipalThreadLocal.getUserId());
		}

		if (generateUuid) {
			UUID uuid = new UUID(
				SecureRandomUtil.nextLong(), SecureRandomUtil.nextLong());

			serviceContext.setUuid(uuid.toString());
		}

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAssetCategoryIds(assetCategoryIds);
		serviceContext.setCompanyId(user.getCompanyId());
		serviceContext.setScopeGroupId(groupId);
		serviceContext.setTimeZone(user.getTimeZone());
		serviceContext.setUserId(user.getUserId());

		return serviceContext;
	}

	@Override
	public ServiceContext getServiceContext(User user) throws PortalException {
		return getServiceContext(0, new long[0], user, false);
	}

	@Reference
	private UserService _userService;

}