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
@Component(immediate = true, service = ServiceContextHelper.class)
public class ServiceContextHelperImpl implements ServiceContextHelper {

	public ServiceContext getServiceContext() throws PortalException {
		return getServiceContext(0, new long[0], null, false);
	}

	public ServiceContext getServiceContext(long groupId)
		throws PortalException {

		return getServiceContext(groupId, new long[0], null, false);
	}

	public ServiceContext getServiceContext(
			long groupId, long[] assetCategoryIds, User user)
		throws PortalException {

		return getServiceContext(groupId, assetCategoryIds, user, false);
	}

	public ServiceContext getServiceContext(
			long groupId, long[] assetCategoryIds, User user,
			boolean generateUuid)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			serviceContext = new ServiceContext();
		}

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

	public ServiceContext getServiceContext(User user) throws PortalException {
		return getServiceContext(0, new long[0], user, false);
	}

	@Reference
	private UserService _userService;

}