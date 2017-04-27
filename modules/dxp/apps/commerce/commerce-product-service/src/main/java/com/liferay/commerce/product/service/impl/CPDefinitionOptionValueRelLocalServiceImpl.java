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

import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.commerce.product.service.base.CPDefinitionOptionValueRelLocalServiceBaseImpl;
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
public class CPDefinitionOptionValueRelLocalServiceImpl
	extends CPDefinitionOptionValueRelLocalServiceBaseImpl {

	public CPDefinitionOptionValueRel
			addCPDefinitionOptionValueRel(
				long cpDefinitionOptionRelId,
				CPOptionValue cpOptionValue,
				ServiceContext serviceContext)
		throws PortalException {

		return cpDefinitionOptionValueRelLocalService.
			addCPDefinitionOptionValueRel(
				cpDefinitionOptionRelId,
				cpOptionValue.getTitleMap(),
				cpOptionValue.getPriority(), serviceContext);
	}

	public CPDefinitionOptionValueRel
			addCPDefinitionOptionValueRel(
				long cpDefinitionOptionRelId,
				Map<Locale, String> titleMap, int priority,
				ServiceContext serviceContext)
		throws PortalException {

		// Commerce product definition option value rel

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long cpDefinitionOptionValueRelId =
			counterLocalService.increment();

		CPDefinitionOptionValueRel
			cpDefinitionOptionValueRel =
				cpDefinitionOptionValueRelPersistence.create(
					cpDefinitionOptionValueRelId);

		cpDefinitionOptionValueRel.setGroupId(groupId);
		cpDefinitionOptionValueRel.setCompanyId(
			user.getCompanyId());
		cpDefinitionOptionValueRel.setUserId(user.getUserId());
		cpDefinitionOptionValueRel.setUserName(user.getFullName());
		cpDefinitionOptionValueRel.
			setCPDefinitionOptionRelId(
				cpDefinitionOptionRelId);
		cpDefinitionOptionValueRel.setPriority(priority);
		cpDefinitionOptionValueRel.setTitleMap(titleMap);
		cpDefinitionOptionValueRel.setExpandoBridgeAttributes(
			serviceContext);

		cpDefinitionOptionValueRelPersistence.update(
			cpDefinitionOptionValueRel);

		return cpDefinitionOptionValueRel;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPDefinitionOptionValueRel
			deleteCPDefinitionOptionValueRel(
				CPDefinitionOptionValueRel
					cpDefinitionOptionValueRel)
		throws PortalException {

		// Commerce product definition option value rel

		cpDefinitionOptionValueRelPersistence.remove(
			cpDefinitionOptionValueRel);

		// Expando

		expandoRowLocalService.deleteRows(
			cpDefinitionOptionValueRel.
				getCPDefinitionOptionValueRelId());

		return cpDefinitionOptionValueRel;
	}

	@Override
	public CPDefinitionOptionValueRel
			deleteCPDefinitionOptionValueRel(
				long cpDefinitionOptionValueRelId)
		throws PortalException {

		CPDefinitionOptionValueRel
			cpDefinitionOptionValueRel =
				cpDefinitionOptionValueRelPersistence.
					findByPrimaryKey(cpDefinitionOptionValueRelId);

		return cpDefinitionOptionValueRelLocalService.
			deleteCPDefinitionOptionValueRel(
				cpDefinitionOptionValueRel);
	}

	@Override
	public void
			deleteCPDefinitionOptionValueRels(
				long cpDefinitionOptionRelId)
		throws PortalException {

		List<CPDefinitionOptionValueRel>
			cpDefinitionOptionValueRels =
				cpDefinitionOptionValueRelLocalService.
					getCPDefinitionOptionValueRels(
						cpDefinitionOptionRelId, QueryUtil.ALL_POS,
						QueryUtil.ALL_POS);

		for (CPDefinitionOptionValueRel
				cpDefinitionOptionValueRel :
					cpDefinitionOptionValueRels) {

			cpDefinitionOptionValueRelLocalService.
				deleteCPDefinitionOptionValueRel(
					cpDefinitionOptionValueRel);
		}
	}

	@Override
	public List<CPDefinitionOptionValueRel>
		getCPDefinitionOptionValueRels(
			long cpDefinitionOptionRelId, int start, int end) {

		return cpDefinitionOptionValueRelPersistence.
			findByCPDefinitionOptionRelId(
				cpDefinitionOptionRelId, start, end);
	}

	@Override
	public List<CPDefinitionOptionValueRel>
		getCPDefinitionOptionValueRels(
			long cpDefinitionOptionRelId, int start, int end,
			OrderByComparator<CPDefinitionOptionValueRel>
				orderByComparator) {

		return cpDefinitionOptionValueRelPersistence.
			findByCPDefinitionOptionRelId(
				cpDefinitionOptionRelId, start, end,
				orderByComparator);
	}

	@Override
	public int getCPDefinitionOptionValueRelsCount(
		long cpDefinitionOptionRelId) {

		return cpDefinitionOptionValueRelPersistence.
			countByCPDefinitionOptionRelId(
				cpDefinitionOptionRelId);
	}

	public void importCPDefinitionOptionRels(
			long cpDefinitionOptionRelId,
			ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionOptionRel cpDefinitionOptionRel =
			cpDefinitionOptionRelLocalService.
				getCPDefinitionOptionRel(
					cpDefinitionOptionRelId);

		CPOption cpOption =
			cpOptionLocalService.fetchCPOption(
				cpDefinitionOptionRel.
					getCPOptionId());

		if (cpOption == null) {
			return;
		}

		List<CPOptionValue> cpOptionValues =
			cpOptionValueLocalService.
				getCPOptionValues(
					cpOption.getCPOptionId(),
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CPOptionValue cpOptionValue :
				cpOptionValues) {

			cpDefinitionOptionValueRelLocalService.
				addCPDefinitionOptionValueRel(
					cpDefinitionOptionRelId,
					cpOptionValue, serviceContext);
		}
	}

	public CPDefinitionOptionValueRel
			updateCPDefinitionOptionValueRel(
				long cpDefinitionOptionValueRelId,
				Map<Locale, String> titleMap, int priority,
				ServiceContext serviceContext)
		throws PortalException {

		// Commerce product definition option value rel

		CPDefinitionOptionValueRel
			cpDefinitionOptionValueRel =
				cpDefinitionOptionValueRelPersistence.
					findByPrimaryKey(cpDefinitionOptionValueRelId);

		cpDefinitionOptionValueRel.setTitleMap(titleMap);
		cpDefinitionOptionValueRel.setPriority(priority);
		cpDefinitionOptionValueRel.setExpandoBridgeAttributes(
			serviceContext);

		cpDefinitionOptionValueRelPersistence.update(
			cpDefinitionOptionValueRel);

		return cpDefinitionOptionValueRel;
	}

}