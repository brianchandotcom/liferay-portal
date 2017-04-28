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

import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.base.CPOptionLocalServiceBaseImpl;
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
 */
public class CPOptionLocalServiceImpl extends CPOptionLocalServiceBaseImpl {

	@Override
	public CPOption addCPOption(
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String ddmFormFieldTypeName, ServiceContext serviceContext)
		throws PortalException {

		// Commerce product option

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long cpOptionId = counterLocalService.increment();

		CPOption cpOption = cpOptionPersistence.create(cpOptionId);

		cpOption.setGroupId(groupId);
		cpOption.setCompanyId(user.getCompanyId());
		cpOption.setUserId(user.getUserId());
		cpOption.setUserName(user.getFullName());
		cpOption.setNameMap(nameMap);
		cpOption.setDescriptionMap(descriptionMap);
		cpOption.setDDMFormFieldTypeName(ddmFormFieldTypeName);
		cpOption.setExpandoBridgeAttributes(serviceContext);

		cpOptionPersistence.update(cpOption);

		// Resources

		resourceLocalService.addModelResources(cpOption, serviceContext);

		return cpOption;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPOption deleteCPOption(CPOption cpOption) throws PortalException {

		// Commerce product option

		cpOptionPersistence.remove(cpOption);

		// Commerce product option values

		cpOptionValueLocalService.deleteCPOptionValues(
			cpOption.getCPOptionId());

		// Resources

		resourceLocalService.deleteResource(
			cpOption.getCompanyId(), CPOption.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, cpOption.getCPOptionId());

		// Expando

		expandoRowLocalService.deleteRows(cpOption.getCPOptionId());

		return cpOption;
	}

	@Override
	public CPOption deleteCPOption(long cpOptionId) throws PortalException {
		CPOption cpOption = cpOptionPersistence.findByPrimaryKey(cpOptionId);

		return cpOptionLocalService.deleteCPOption(cpOption);
	}

	@Override
	public List<CPOption> getCPOptions(long groupId, int start, int end) {
		return cpOptionPersistence.findByGroupId(groupId, start, end);
	}

	@Override
	public List<CPOption> getCPOptions(
		long groupId, int start, int end,
		OrderByComparator<CPOption> orderByComparator) {

		return cpOptionPersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCPOptionsCount(long groupId) {
		return cpOptionPersistence.countByGroupId(groupId);
	}

	@Override
	public CPOption updateCPOption(
			long cpOptionId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String ddmFormFieldTypeName,
			ServiceContext serviceContext)
		throws PortalException {

		// Commerce product option

		CPOption cpOption = cpOptionPersistence.findByPrimaryKey(cpOptionId);

		cpOption.setNameMap(nameMap);
		cpOption.setDescriptionMap(descriptionMap);
		cpOption.setDDMFormFieldTypeName(ddmFormFieldTypeName);
		cpOption.setExpandoBridgeAttributes(serviceContext);

		cpOptionPersistence.update(cpOption);

		return cpOption;
	}

}