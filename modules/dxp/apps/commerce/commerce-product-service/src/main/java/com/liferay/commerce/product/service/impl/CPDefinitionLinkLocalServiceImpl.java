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

import com.liferay.commerce.product.model.CPDefinitionLink;
import com.liferay.commerce.product.service.base.CPDefinitionLinkLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.Date;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionLinkLocalServiceImpl
	extends CPDefinitionLinkLocalServiceBaseImpl {

	@Override
	public CPDefinitionLink addCPDefinitionLink(
			long cpDefinitionId1, long cpDefinitionId2, int displayOrder,
			int type, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		Date now = new Date();

		long cpDefinitionLinkId = counterLocalService.increment();

		CPDefinitionLink cpDefinitionLink = cpDefinitionLinkPersistence.create(
			cpDefinitionLinkId);

		cpDefinitionLink.setCompanyId(user.getCompanyId());
		cpDefinitionLink.setUserId(user.getUserId());
		cpDefinitionLink.setUserName(user.getFullName());
		cpDefinitionLink.setCreateDate(now);
		cpDefinitionLink.setCPDefinitionId1(cpDefinitionId1);
		cpDefinitionLink.setCPDefinitionId2(cpDefinitionId2);
		cpDefinitionLink.setDisplayOrder(displayOrder);
		cpDefinitionLink.setType(type);

		cpDefinitionLinkPersistence.update(cpDefinitionLink);

		return cpDefinitionLink;
	}

	@Override
	public CPDefinitionLink deleteCPDefinitionLink(
			CPDefinitionLink cpDefinitionLink) throws PortalException {

		return cpDefinitionLinkPersistence.remove(cpDefinitionLink);
	}

	@Override
	public CPDefinitionLink deleteCPDefinitionLink(long cpDefinitionLinkId)
		throws PortalException {

		CPDefinitionLink cpDefinitionLink =
			cpDefinitionLinkPersistence.findByPrimaryKey(cpDefinitionLinkId);

		return deleteCPDefinitionLink(cpDefinitionLink);
	}
}