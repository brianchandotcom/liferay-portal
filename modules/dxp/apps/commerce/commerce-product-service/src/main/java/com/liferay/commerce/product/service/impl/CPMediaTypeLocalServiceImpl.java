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

import com.liferay.commerce.product.model.CPMediaType;
import com.liferay.commerce.product.service.base.CPMediaTypeLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CPMediaTypeLocalServiceImpl
	extends CPMediaTypeLocalServiceBaseImpl {

	@Override
	public CPMediaType addCPMediaType(
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			int priority, ServiceContext serviceContext)
		throws PortalException {

		// Commerce product media type

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long cpMediaTypeId = counterLocalService.increment();

		CPMediaType cpMediaType = cpMediaTypePersistence.create(cpMediaTypeId);

		cpMediaType.setUuid(serviceContext.getUuid());
		cpMediaType.setGroupId(groupId);
		cpMediaType.setCompanyId(user.getCompanyId());
		cpMediaType.setUserId(user.getUserId());
		cpMediaType.setUserName(user.getFullName());
		cpMediaType.setTitleMap(titleMap);
		cpMediaType.setDescriptionMap(descriptionMap);
		cpMediaType.setPriority(priority);
		cpMediaType.setExpandoBridgeAttributes(serviceContext);

		cpMediaTypePersistence.update(cpMediaType);

		// Resources

		resourceLocalService.addModelResources(cpMediaType, serviceContext);

		return cpMediaType;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPMediaType deleteCPMediaType(CPMediaType cpMediaType)
		throws PortalException {

		// Commerce product media type

		cpMediaTypePersistence.remove(cpMediaType);

		// Resources

		resourceLocalService.deleteResource(
			cpMediaType.getCompanyId(), CPMediaType.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, cpMediaType.getCPMediaTypeId());

		// Expando

		expandoRowLocalService.deleteRows(cpMediaType.getCPMediaTypeId());

		return cpMediaType;
	}

	@Override
	public CPMediaType deleteCPMediaType(long cpMediaTypeId)
		throws PortalException {

		CPMediaType cpMediaType = cpMediaTypePersistence.findByPrimaryKey(
			cpMediaTypeId);

		return cpMediaTypeLocalService.deleteCPMediaType(cpMediaType);
	}

	@Override
	public List<CPMediaType> getCPMediaTypes(long groupId, int start, int end) {
		return cpMediaTypePersistence.findByGroupId(groupId, start, end);
	}

	@Override
	public List<CPMediaType> getCPMediaTypes(
		long groupId, int start, int end,
		OrderByComparator<CPMediaType> orderByComparator) {

		return cpMediaTypePersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCPMediaTypesCount(long groupId) {
		return cpMediaTypePersistence.countByGroupId(groupId);
	}

	@Override
	public CPMediaType updateCPMediaType(
			long cpMediaTypeId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, int priority,
			ServiceContext serviceContext)
		throws PortalException {

		CPMediaType cpMediaType = cpMediaTypePersistence.findByPrimaryKey(
			cpMediaTypeId);

		cpMediaType.setTitleMap(titleMap);
		cpMediaType.setDescriptionMap(descriptionMap);
		cpMediaType.setPriority(priority);
		cpMediaType.setExpandoBridgeAttributes(serviceContext);

		cpMediaTypePersistence.update(cpMediaType);

		return cpMediaType;
	}

}