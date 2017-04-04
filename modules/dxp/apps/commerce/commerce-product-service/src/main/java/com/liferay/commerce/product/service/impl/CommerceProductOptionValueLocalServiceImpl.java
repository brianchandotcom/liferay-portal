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

import com.liferay.commerce.product.model.CommerceProductOptionValue;
import com.liferay.commerce.product.service.base.CommerceProductOptionValueLocalServiceBaseImpl;
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
 * @see CommerceProductOptionValueLocalServiceBaseImpl
 * @see com.liferay.commerce.product.service.CommerceProductOptionValueLocalServiceUtil
 */
public class CommerceProductOptionValueLocalServiceImpl
	extends CommerceProductOptionValueLocalServiceBaseImpl {

	@Override
	public CommerceProductOptionValue addCommerceProductOptionValue(
			long commerceProductOptionId, Map<Locale, String> titleMap,
			long priority, ServiceContext serviceContext)
		throws PortalException {

		//Product Option

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long commerceProductOptionValueId = counterLocalService.increment();

		CommerceProductOptionValue commerceProductOptionValue =
			commerceProductOptionValuePersistence.create(
				commerceProductOptionValueId);

		commerceProductOptionValue.setGroupId(groupId);
		commerceProductOptionValue.setCompanyId(user.getCompanyId());
		commerceProductOptionValue.setUserId(user.getUserId());
		commerceProductOptionValue.setUserName(user.getFullName());
		commerceProductOptionValue.setTitleMap(titleMap);
		commerceProductOptionValue.setCommerceProductOptionId(
			commerceProductOptionId);
		commerceProductOptionValue.setPriority(priority);
		commerceProductOptionValue.setExpandoBridgeAttributes(serviceContext);
		commerceProductOptionValuePersistence.update(
			commerceProductOptionValue);

		// Resources

		resourceLocalService.addModelResources(
			commerceProductOptionValue, serviceContext);

		return commerceProductOptionValue;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceProductOptionValue deleteCommerceProductOptionValue(
			CommerceProductOptionValue commerceProductOptionValue)
		throws PortalException {

		// Product option

		commerceProductOptionValuePersistence.remove(
			commerceProductOptionValue);

		// Resources

		resourceLocalService.deleteResource(
			commerceProductOptionValue.getCompanyId(),
			CommerceProductOptionValue.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			commerceProductOptionValue.getCommerceProductOptionValueId());

		// Expando

		expandoRowLocalService.deleteRows(
			commerceProductOptionValue.getCommerceProductOptionValueId());

		return commerceProductOptionValue;
	}

	@Override
	public CommerceProductOptionValue deleteCommerceProductOptionValue(
			long commerceProductOptionValueId)
		throws PortalException {

		CommerceProductOptionValue commerceProductOptionValue =
			commerceProductOptionValuePersistence.findByPrimaryKey(
				commerceProductOptionValueId);

		return commerceProductOptionValueLocalService.
			deleteCommerceProductOptionValue(commerceProductOptionValue);
	}

	@Override
	public List<CommerceProductOptionValue> getCommerceProductOptionValues(
		long commerceProductOptionId, int start, int end) {

		return commerceProductOptionValuePersistence.
			findByCommerceProductOptionId(commerceProductOptionId, start, end);
	}

	@Override
	public List<CommerceProductOptionValue> getCommerceProductOptionValues(
		long commerceProductOptionId, int start, int end,
		OrderByComparator<CommerceProductOptionValue> orderByComparator) {

		return commerceProductOptionValuePersistence.
			findByCommerceProductOptionId(
				commerceProductOptionId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceProductOptionValuesCount(
		long commerceProductOptionId) {

		return commerceProductOptionValuePersistence.
			countByCommerceProductOptionId(commerceProductOptionId);
	}

	@Override
	public CommerceProductOptionValue updateCommerceProductOptionValue(
			long commerceProductOptionValueId, Map<Locale, String> titleMap,
			long priority, ServiceContext serviceContext)
		throws PortalException {

		// Product option

		CommerceProductOptionValue commerceProductOptionValue =
			commerceProductOptionValuePersistence.create(
				commerceProductOptionValueId);

		commerceProductOptionValue.setTitleMap(titleMap);
		commerceProductOptionValue.setPriority(priority);
		commerceProductOptionValue.setExpandoBridgeAttributes(serviceContext);
		commerceProductOptionValuePersistence.update(
			commerceProductOptionValue);

		return commerceProductOptionValue;
	}

}