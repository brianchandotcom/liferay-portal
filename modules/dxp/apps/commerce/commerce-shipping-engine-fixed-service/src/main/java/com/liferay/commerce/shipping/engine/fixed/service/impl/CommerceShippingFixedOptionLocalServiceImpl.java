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

package com.liferay.commerce.shipping.engine.fixed.service.impl;

import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.service.base.CommerceShippingFixedOptionLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShippingFixedOptionLocalServiceImpl
	extends CommerceShippingFixedOptionLocalServiceBaseImpl {

	@Override
	public CommerceShippingFixedOption addCommerceShippingFixedOption(
			long commerceShippingMethodId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, double amount, double priority,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long commerceShippingFixedOptionId = counterLocalService.increment();

		CommerceShippingFixedOption commerceShippingFixedOption =
			commerceShippingFixedOptionPersistence.create(
				commerceShippingFixedOptionId);

		commerceShippingFixedOption.setGroupId(groupId);
		commerceShippingFixedOption.setCompanyId(user.getCompanyId());
		commerceShippingFixedOption.setUserId(user.getUserId());
		commerceShippingFixedOption.setUserName(user.getFullName());
		commerceShippingFixedOption.setCommerceShippingMethodId(
			commerceShippingMethodId);
		commerceShippingFixedOption.setNameMap(nameMap);
		commerceShippingFixedOption.setDescriptionMap(descriptionMap);
		commerceShippingFixedOption.setAmount(amount);
		commerceShippingFixedOption.setPriority(priority);

		commerceShippingFixedOptionPersistence.update(
			commerceShippingFixedOption);

		return commerceShippingFixedOption;
	}

	@Override
	public CommerceShippingFixedOption deleteCommerceShippingFixedOption(
		CommerceShippingFixedOption commerceShippingFixedOption) {

		// Commerce shipping fixed option

		commerceShippingFixedOptionPersistence.remove(
			commerceShippingFixedOption);

		// Commerce shipping fixed option rels

		commerceShippingFixedOptionRelLocalService.
			deleteCommerceShippingFixedOptionRels(
				commerceShippingFixedOption.getCommerceShippingFixedOptionId());

		return commerceShippingFixedOption;
	}

	@Override
	public void deleteCommerceShippingFixedOptions(
		long commerceShippingMethodId) {

		commerceShippingFixedOptionPersistence.removeByCommerceShippingMethodId(
			commerceShippingMethodId);
	}

	@Override
	public List<CommerceShippingFixedOption> getCommerceShippingFixedOptions(
		long commerceShippingMethodId, int start, int end) {

		return commerceShippingFixedOptionPersistence.
			findByCommerceShippingMethodId(
				commerceShippingMethodId, start, end);
	}

	@Override
	public List<CommerceShippingFixedOption> getCommerceShippingFixedOptions(
		long commerceShippingMethodId, int start, int end,
		OrderByComparator<CommerceShippingFixedOption> orderByComparator) {

		return commerceShippingFixedOptionPersistence.
			findByCommerceShippingMethodId(
				commerceShippingMethodId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceShippingFixedOptionsCount(
		long commerceShippingMethodId) {

		return commerceShippingFixedOptionPersistence.
			countByCommerceShippingMethodId(commerceShippingMethodId);
	}

	@Override
	public CommerceShippingFixedOption updateCommerceShippingFixedOption(
			long commerceShippingFixedOptionId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, double amount, double priority)
		throws PortalException {

		CommerceShippingFixedOption commerceShippingFixedOption =
			commerceShippingFixedOptionPersistence.findByPrimaryKey(
				commerceShippingFixedOptionId);

		commerceShippingFixedOption.setNameMap(nameMap);
		commerceShippingFixedOption.setDescriptionMap(descriptionMap);
		commerceShippingFixedOption.setAmount(amount);
		commerceShippingFixedOption.setPriority(priority);

		commerceShippingFixedOptionPersistence.update(
			commerceShippingFixedOption);

		return commerceShippingFixedOption;
	}

}