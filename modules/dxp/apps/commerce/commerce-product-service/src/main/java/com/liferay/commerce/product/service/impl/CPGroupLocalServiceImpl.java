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

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.model.CPGroup;
import com.liferay.commerce.product.service.base.CPGroupLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Alessio Antonio Rendina
 */
public class CPGroupLocalServiceImpl extends CPGroupLocalServiceBaseImpl {

	@Override
	public CPGroup addCPGroup(ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long cpGroupId = counterLocalService.increment();

		CPGroup cpGroup = cpGroupPersistence.create(cpGroupId);

		cpGroup.setGroupId(groupId);
		cpGroup.setCompanyId(user.getCompanyId());
		cpGroup.setUserId(user.getUserId());
		cpGroup.setUserName(user.getFullName());

		cpGroupPersistence.update(cpGroup);

		return cpGroup;
	}

	@Override
	public CPGroup deleteCPGroupByGroupId(long groupId) throws PortalException {
		return cpGroupPersistence.removeByGroupId(groupId);
	}

	@Override
	public CPGroup fetchCPGroupByGroupId(long groupId) {
		return cpGroupPersistence.fetchByGroupId(groupId);
	}

}