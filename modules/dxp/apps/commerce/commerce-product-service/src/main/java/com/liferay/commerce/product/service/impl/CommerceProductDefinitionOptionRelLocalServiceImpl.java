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

import com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel;
import com.liferay.commerce.product.service.base.CommerceProductDefinitionOptionRelLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
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
public class CommerceProductDefinitionOptionRelLocalServiceImpl
	extends CommerceProductDefinitionOptionRelLocalServiceBaseImpl {

	public CommerceProductDefinitionOptionRel
			addCommerceProductDefinitionOptionRel(
				long commerceProductDefinitionId, long commerceProductOptionId,
				Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
				String ddmFormFieldTypeName, int priority,
				ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long commerceProductDefinitionOptionRelId =
			counterLocalService.increment();

		CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel =
			commerceProductDefinitionOptionRelPersistence.create(
				commerceProductDefinitionOptionRelId);

		commerceProductDefinitionOptionRel.setGroupId(groupId);
		commerceProductDefinitionOptionRel.setCompanyId(user.getCompanyId());
		commerceProductDefinitionOptionRel.setUserId(user.getUserId());
		commerceProductDefinitionOptionRel.setUserName(user.getFullName());
		commerceProductDefinitionOptionRel.setCommerceProductDefinitionId(
			commerceProductDefinitionId);
		commerceProductDefinitionOptionRel.setCommerceProductOptionId(
			commerceProductOptionId);
		commerceProductDefinitionOptionRel.setPriority(priority);
		commerceProductDefinitionOptionRel.setNameMap(nameMap);
		commerceProductDefinitionOptionRel.setDescriptionMap(descriptionMap);
		commerceProductDefinitionOptionRel.setDDMFormFieldTypeName(
			ddmFormFieldTypeName);
		commerceProductDefinitionOptionRel.setExpandoBridgeAttributes(
			serviceContext);

		commerceProductDefinitionOptionRelPersistence.update(
			commerceProductDefinitionOptionRel);

		return commerceProductDefinitionOptionRel;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceProductDefinitionOptionRel
			deleteCommerceProductDefinitionOptionRel(
				CommerceProductDefinitionOptionRel
					commerceProductDefinitionOptionRel)
		throws PortalException {

		// Commerce product definition option rel

		commerceProductDefinitionOptionRelPersistence.remove(
			commerceProductDefinitionOptionRel);

		// Expando

		expandoRowLocalService.deleteRows(
			commerceProductDefinitionOptionRel.
				getCommerceProductDefinitionOptionRelId());

		return commerceProductDefinitionOptionRel;
	}

	@Override
	public CommerceProductDefinitionOptionRel
			deleteCommerceProductDefinitionOptionRel(
				long commerceProductDefinitionOptionRelId)
		throws PortalException {

		CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel =
			commerceProductDefinitionOptionRelPersistence.findByPrimaryKey(
				commerceProductDefinitionOptionRelId);

		return commerceProductDefinitionOptionRelLocalService.
			deleteCommerceProductDefinitionOptionRel(
				commerceProductDefinitionOptionRel);
	}

	@Override
	public List<CommerceProductDefinitionOptionRel>
		getCommerceProductDefinitionOptionRels(
			long commerceProductDefinitionId, int start, int end) {

		return commerceProductDefinitionOptionRelPersistence.
			findByCommerceProductDefinitionId(
				commerceProductDefinitionId, start, end);
	}

	@Override
	public List<CommerceProductDefinitionOptionRel>
		getCommerceProductDefinitionOptionRels(
			long commerceProductDefinitionId, int start, int end,
			OrderByComparator<CommerceProductDefinitionOptionRel>
				orderByComparator) {

		return commerceProductDefinitionOptionRelPersistence.
			findByCommerceProductDefinitionId(
				commerceProductDefinitionId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceProductDefinitionOptionRelsCount(
		long commerceProductDefinitionId) {

		return commerceProductDefinitionOptionRelPersistence.
			countByCommerceProductDefinitionId(commerceProductDefinitionId);
	}

	public CommerceProductDefinitionOptionRel
			updateCommerceProductDefinitionOptionRel(
				long commerceProductDefinitionOptionRelId,
				long commerceProductOptionId, Map<Locale, String> nameMap,
				Map<Locale, String> descriptionMap, String ddmFormFieldTypeName,
				int priority, ServiceContext serviceContext)
		throws PortalException {

		CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel =
			commerceProductDefinitionOptionRelPersistence.findByPrimaryKey(
				commerceProductDefinitionOptionRelId);

		commerceProductDefinitionOptionRel.setCommerceProductOptionId(
			commerceProductOptionId);
		commerceProductDefinitionOptionRel.setPriority(priority);
		commerceProductDefinitionOptionRel.setNameMap(nameMap);
		commerceProductDefinitionOptionRel.setDescriptionMap(descriptionMap);
		commerceProductDefinitionOptionRel.setDDMFormFieldTypeName(
			ddmFormFieldTypeName);
		commerceProductDefinitionOptionRel.setExpandoBridgeAttributes(
			serviceContext);

		commerceProductDefinitionOptionRelPersistence.update(
			commerceProductDefinitionOptionRel);

		return commerceProductDefinitionOptionRel;
	}

}