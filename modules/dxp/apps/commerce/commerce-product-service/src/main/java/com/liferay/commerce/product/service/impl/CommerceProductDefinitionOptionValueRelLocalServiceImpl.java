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
import com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CommerceProductOption;
import com.liferay.commerce.product.model.CommerceProductOptionValue;
import com.liferay.commerce.product.service.base.CommerceProductDefinitionOptionValueRelLocalServiceBaseImpl;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
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
public class CommerceProductDefinitionOptionValueRelLocalServiceImpl
	extends CommerceProductDefinitionOptionValueRelLocalServiceBaseImpl {

	public CommerceProductDefinitionOptionValueRel
				addCommerceProductDefinitionOptionValueRel(
					long commerceProductDefinitionOptionRelId,
					CommerceProductOptionValue commerceProductOptionValue,
					ServiceContext serviceContext)
		throws PortalException {

		return commerceProductDefinitionOptionValueRelLocalService.
			addCommerceProductDefinitionOptionValueRel(
				commerceProductDefinitionOptionRelId,
				commerceProductOptionValue.getTitleMap(),
				commerceProductOptionValue.getPriority(), serviceContext);
	}

		public CommerceProductDefinitionOptionValueRel
			addCommerceProductDefinitionOptionValueRel(
				long commerceProductDefinitionOptionRelId,
				Map<Locale, String> titleMap, int priority,
				ServiceContext serviceContext)
		throws PortalException {

		// Commerce product definition option value rel

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long commerceProductDefinitionOptionValueRelId =
			counterLocalService.increment();

		CommerceProductDefinitionOptionValueRel
			commerceProductDefinitionOptionValueRel =
				commerceProductDefinitionOptionValueRelPersistence.create(
					commerceProductDefinitionOptionValueRelId);

		commerceProductDefinitionOptionValueRel.setGroupId(groupId);
		commerceProductDefinitionOptionValueRel.setCompanyId(
			user.getCompanyId());
		commerceProductDefinitionOptionValueRel.setUserId(user.getUserId());
		commerceProductDefinitionOptionValueRel.setUserName(user.getFullName());
		commerceProductDefinitionOptionValueRel.
			setCommerceProductDefinitionOptionRelId(
				commerceProductDefinitionOptionRelId);
		commerceProductDefinitionOptionValueRel.setPriority(priority);
		commerceProductDefinitionOptionValueRel.setTitleMap(titleMap);
		commerceProductDefinitionOptionValueRel.setExpandoBridgeAttributes(
			serviceContext);

		commerceProductDefinitionOptionValueRelPersistence.update(
			commerceProductDefinitionOptionValueRel);

		return commerceProductDefinitionOptionValueRel;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceProductDefinitionOptionValueRel
			deleteCommerceProductDefinitionOptionValueRel(
				CommerceProductDefinitionOptionValueRel
					commerceProductDefinitionOptionValueRel)
		throws PortalException {

		// Commerce product definition option value rel

		commerceProductDefinitionOptionValueRelPersistence.remove(
			commerceProductDefinitionOptionValueRel);

		// Expando

		expandoRowLocalService.deleteRows(
			commerceProductDefinitionOptionValueRel.
				getCommerceProductDefinitionOptionValueRelId());

		return commerceProductDefinitionOptionValueRel;
	}

	@Override
	public CommerceProductDefinitionOptionValueRel
			deleteCommerceProductDefinitionOptionValueRel(
				long commerceProductDefinitionOptionValueRelId)
		throws PortalException {

		CommerceProductDefinitionOptionValueRel
			commerceProductDefinitionOptionValueRel =
				commerceProductDefinitionOptionValueRelPersistence.
					findByPrimaryKey(commerceProductDefinitionOptionValueRelId);

		return commerceProductDefinitionOptionValueRelLocalService.
			deleteCommerceProductDefinitionOptionValueRel(
				commerceProductDefinitionOptionValueRel);
	}

	@Override
	public void
			deleteCommerceProductDefinitionOptionValueRels(
				long commerceProductDefinitionOptionRelId)
		throws PortalException {

		List<CommerceProductDefinitionOptionValueRel>
			commerceProductDefinitionOptionValueRels =
				commerceProductDefinitionOptionValueRelLocalService.
					getCommerceProductDefinitionOptionValueRels(
						commerceProductDefinitionOptionRelId, QueryUtil.ALL_POS,
						QueryUtil.ALL_POS);

		for (CommerceProductDefinitionOptionValueRel
				commerceProductDefinitionOptionValueRel :
					commerceProductDefinitionOptionValueRels) {

			commerceProductDefinitionOptionValueRelLocalService.
				deleteCommerceProductDefinitionOptionValueRel(
					commerceProductDefinitionOptionValueRel);
		}
	}

	@Override
	public List<CommerceProductDefinitionOptionValueRel>
		getCommerceProductDefinitionOptionValueRels(
			long commerceProductDefinitionOptionRelId, int start, int end) {

		return commerceProductDefinitionOptionValueRelPersistence.
			findByCommerceProductDefinitionOptionRelId(
				commerceProductDefinitionOptionRelId, start, end);
	}

	@Override
	public List<CommerceProductDefinitionOptionValueRel>
		getCommerceProductDefinitionOptionValueRels(
			long commerceProductDefinitionOptionRelId, int start, int end,
			OrderByComparator<CommerceProductDefinitionOptionValueRel>
				orderByComparator) {

		return commerceProductDefinitionOptionValueRelPersistence.
			findByCommerceProductDefinitionOptionRelId(
				commerceProductDefinitionOptionRelId, start, end,
				orderByComparator);
	}

	@Override
	public int getCommerceProductDefinitionOptionValueRelsCount(
		long commerceProductDefinitionOptionRelId) {

		return commerceProductDefinitionOptionValueRelPersistence.
			countByCommerceProductDefinitionOptionRelId(
				commerceProductDefinitionOptionRelId);
	}

	public void importCommerceProductDefinitionOptionRels(
			long commerceProductDefinitionOptionRelId,
			ServiceContext serviceContext)
		throws PortalException {

		CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel =
		commerceProductDefinitionOptionRelLocalService.
			getCommerceProductDefinitionOptionRel(
				commerceProductDefinitionOptionRelId);

		CommerceProductOption commerceProductOption =
			commerceProductOptionLocalService.fetchCommerceProductOption(
				commerceProductDefinitionOptionRel.
					getCommerceProductOptionId());

		if (commerceProductOption == null) return;

		List<CommerceProductOptionValue> commerceProductOptionValues =
			commerceProductOptionValueLocalService.
				getCommerceProductOptionValues(
					commerceProductOption.getCommerceProductOptionId(),
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CommerceProductOptionValue commerceProductOptionValue :
				commerceProductOptionValues) {

			commerceProductDefinitionOptionValueRelLocalService.
				addCommerceProductDefinitionOptionValueRel(
					commerceProductDefinitionOptionRelId,
					commerceProductOptionValue, serviceContext);
		}
	}

	public CommerceProductDefinitionOptionValueRel
			updateCommerceProductDefinitionOptionValueRel(
				long commerceProductDefinitionOptionValueRelId,
				Map<Locale, String> titleMap, int priority,
				ServiceContext serviceContext)
		throws PortalException {

		// Commerce product definition option value rel

		CommerceProductDefinitionOptionValueRel
			commerceProductDefinitionOptionValueRel =
				commerceProductDefinitionOptionValueRelPersistence.
					findByPrimaryKey(commerceProductDefinitionOptionValueRelId);

		commerceProductDefinitionOptionValueRel.setTitleMap(titleMap);
		commerceProductDefinitionOptionValueRel.setPriority(priority);
		commerceProductDefinitionOptionValueRel.setExpandoBridgeAttributes(
			serviceContext);

		commerceProductDefinitionOptionValueRelPersistence.update(
			commerceProductDefinitionOptionValueRel);

		return commerceProductDefinitionOptionValueRel;
	}

}