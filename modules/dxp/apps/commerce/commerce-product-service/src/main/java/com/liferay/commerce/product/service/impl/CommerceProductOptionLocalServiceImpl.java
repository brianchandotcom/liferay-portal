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

import com.liferay.commerce.product.model.CommerceProductOption;
import com.liferay.commerce.product.service.base.CommerceProductOptionLocalServiceBaseImpl;
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
 * @see CommerceProductOptionLocalServiceBaseImpl
 * @see com.liferay.commerce.product.service.CommerceProductOptionLocalServiceUtil
 */
public class CommerceProductOptionLocalServiceImpl
	extends CommerceProductOptionLocalServiceBaseImpl {

	@Override
	public CommerceProductOption addCommerceProductOption(
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String ddmFormFieldTypeName, ServiceContext serviceContext)
		throws PortalException {

		//Product Option

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long commerceProductOptionId = counterLocalService.increment();

		CommerceProductOption commerceProductOption =
			commerceProductOptionPersistence.create(commerceProductOptionId);

		commerceProductOption.setGroupId(groupId);
		commerceProductOption.setCompanyId(user.getCompanyId());
		commerceProductOption.setUserId(user.getUserId());
		commerceProductOption.setUserName(user.getFullName());
		commerceProductOption.setNameMap(nameMap);
		commerceProductOption.setDescriptionMap(descriptionMap);
		commerceProductOption.setDDMFormFieldTypeName(ddmFormFieldTypeName);
		commerceProductOption.setExpandoBridgeAttributes(serviceContext);
		commerceProductOptionPersistence.update(commerceProductOption);

		// Resources

		resourceLocalService.addModelResources(
			commerceProductOption, serviceContext);

		return commerceProductOption;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceProductOption deleteCommerceProductOption(
			CommerceProductOption commerceProductOption)
		throws PortalException {

		// Product option

		commerceProductOptionPersistence.remove(commerceProductOption);

		// Resources

		resourceLocalService.deleteResource(
			commerceProductOption.getCompanyId(),
			CommerceProductOption.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			commerceProductOption.getCommerceProductOptionId());

		// Expando

		expandoRowLocalService.deleteRows(
			commerceProductOption.getCommerceProductOptionId());

		return commerceProductOption;
	}

	@Override
	public CommerceProductOption deleteCommerceProductOption(
			long commerceProductOptionId)
		throws PortalException {

		CommerceProductOption commerceProductOption =
			commerceProductOptionPersistence.findByPrimaryKey(
				commerceProductOptionId);

		return commerceProductOptionLocalService.deleteCommerceProductOption(
			commerceProductOption);
	}

	@Override
	public List<CommerceProductOption> getCommerceProductOptions(
		long groupId, int start, int end) {

		return commerceProductOptionPersistence.findByGroupId(
			groupId, start, end);
	}

	@Override
	public List<CommerceProductOption> getCommerceProductOptions(
		long groupId, int start, int end,
		OrderByComparator<CommerceProductOption> orderByComparator) {

		return commerceProductOptionPersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceProductOptionsCount(long groupId) {
		return commerceProductOptionPersistence.countByGroupId(groupId);
	}

	@Override
	public CommerceProductOption updateCommerceProductOption(
			long commerceProductOptionId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String ddmFormFieldTypeName,
			ServiceContext serviceContext)
		throws PortalException {

		// Product option

		CommerceProductOption commerceProductOption =
			commerceProductOptionPersistence.create(commerceProductOptionId);

		commerceProductOption.setNameMap(nameMap);
		commerceProductOption.setDescriptionMap(descriptionMap);
		commerceProductOption.setDDMFormFieldTypeName(ddmFormFieldTypeName);
		commerceProductOption.setExpandoBridgeAttributes(serviceContext);
		commerceProductOptionPersistence.update(commerceProductOption);

		return commerceProductOption;
	}

}