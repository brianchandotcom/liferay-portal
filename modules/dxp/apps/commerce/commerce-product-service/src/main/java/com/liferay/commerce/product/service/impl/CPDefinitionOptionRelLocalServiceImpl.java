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
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.base.CPDefinitionOptionRelLocalServiceBaseImpl;
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
public class CPDefinitionOptionRelLocalServiceImpl
	extends CPDefinitionOptionRelLocalServiceBaseImpl {

	public CPDefinitionOptionRel
			addCPDefinitionOptionRel(
				long cpDefinitionId, long cpOptionId,
				Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
				String ddmFormFieldTypeName, int priority, boolean facetable,
				boolean skuContributor, ServiceContext serviceContext)
		throws PortalException {

		// Commerce product definition option rel

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long cpDefinitionOptionRelId = counterLocalService.increment();

		CPDefinitionOptionRel cpDefinitionOptionRel =
			cpDefinitionOptionRelPersistence.create(cpDefinitionOptionRelId);

		cpDefinitionOptionRel.setGroupId(groupId);
		cpDefinitionOptionRel.setCompanyId(user.getCompanyId());
		cpDefinitionOptionRel.setUserId(user.getUserId());
		cpDefinitionOptionRel.setUserName(user.getFullName());
		cpDefinitionOptionRel.setCPDefinitionId(cpDefinitionId);
		cpDefinitionOptionRel.setCPOptionId(cpOptionId);
		cpDefinitionOptionRel.setNameMap(nameMap);
		cpDefinitionOptionRel.setDescriptionMap(descriptionMap);
		cpDefinitionOptionRel.setDDMFormFieldTypeName(ddmFormFieldTypeName);
		cpDefinitionOptionRel.setPriority(priority);
		cpDefinitionOptionRel.setFacetable(facetable);
		cpDefinitionOptionRel.setSkuContributor(skuContributor);
		cpDefinitionOptionRel.setExpandoBridgeAttributes(serviceContext);

		cpDefinitionOptionRelPersistence.update(cpDefinitionOptionRel);

		cpDefinitionOptionValueRelLocalService.importCPDefinitionOptionRels(
			cpDefinitionOptionRelId, serviceContext);

		return cpDefinitionOptionRel;
	}

	public CPDefinitionOptionRel
			addCPDefinitionOptionRel(
				long cpDefinitionId, long cpOptionId,
				ServiceContext serviceContext)
		throws PortalException {

		CPOption cpOption = cpOptionLocalService.getCPOption(cpOptionId);

		return cpDefinitionOptionRelLocalService.addCPDefinitionOptionRel(
			cpDefinitionId, cpOptionId, cpOption.getNameMap(),
			cpOption.getDescriptionMap(), cpOption.getDDMFormFieldTypeName(), 0,
			false, false, serviceContext);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPDefinitionOptionRel
			deleteCPDefinitionOptionRel(
				CPDefinitionOptionRel
					cpDefinitionOptionRel)
		throws PortalException {

		// Commerce product definition option rel

		cpDefinitionOptionRelPersistence.remove(cpDefinitionOptionRel);

		// Commerce product definition option value rels

		cpDefinitionOptionValueRelLocalService.
			deleteCPDefinitionOptionValueRels(
				cpDefinitionOptionRel.getCPDefinitionOptionRelId());

		// Expando

		expandoRowLocalService.deleteRows(
			cpDefinitionOptionRel.getCPDefinitionOptionRelId());

		return cpDefinitionOptionRel;
	}

	@Override
	public CPDefinitionOptionRel
			deleteCPDefinitionOptionRel(
				long cpDefinitionOptionRelId)
		throws PortalException {

		CPDefinitionOptionRel cpDefinitionOptionRel =
			cpDefinitionOptionRelPersistence.findByPrimaryKey(
				cpDefinitionOptionRelId);

		return cpDefinitionOptionRelLocalService.deleteCPDefinitionOptionRel(
			cpDefinitionOptionRel);
	}

	@Override
	public void deleteCPDefinitionOptionRels(long cpDefinitionId)
		throws PortalException {

		List<CPDefinitionOptionRel> cpDefinitionOptionRels =
			cpDefinitionOptionRelLocalService.getCPDefinitionOptionRels(
				cpDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CPDefinitionOptionRel
				cpDefinitionOptionRel :
					cpDefinitionOptionRels) {

			cpDefinitionOptionRelLocalService.deleteCPDefinitionOptionRel(
				cpDefinitionOptionRel);
		}
	}

	@Override
	public List<CPDefinitionOptionRel> getCPDefinitionOptionRels(
		long cpDefinitionId, int start, int end) {

		return cpDefinitionOptionRelPersistence.findByCPDefinitionId(
			cpDefinitionId, start, end);
	}

	@Override
	public List<CPDefinitionOptionRel> getCPDefinitionOptionRels(
		long cpDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {

		return cpDefinitionOptionRelPersistence.findByCPDefinitionId(
			cpDefinitionId, start, end, orderByComparator);
	}

	@Override
	public int getCPDefinitionOptionRelsCount(long cpDefinitionId) {
		return cpDefinitionOptionRelPersistence.countByCPDefinitionId(
			cpDefinitionId);
	}

	@Override
	public int getSkuContributorCPDefinitionOptionRelCount(
		long cpDefinitionId) {

		return cpDefinitionOptionRelPersistence.countByC_SC(
			cpDefinitionId, true);
	}

	@Override
	public List<CPDefinitionOptionRel> getSkuContributorCPDefinitionOptionRels(
		long cpDefinitionId) {

		return cpDefinitionOptionRelPersistence.findByC_SC(
			cpDefinitionId, true);
	}

	public CPDefinitionOptionRel
			updateCPDefinitionOptionRel(
				long cpDefinitionOptionRelId, long cpOptionId,
				Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
				String ddmFormFieldTypeName, int priority, boolean facetable,
				boolean skuContributor, ServiceContext serviceContext)
		throws PortalException {

		// Commerce product definition option rel

		CPDefinitionOptionRel cpDefinitionOptionRel =
			cpDefinitionOptionRelPersistence.findByPrimaryKey(
				cpDefinitionOptionRelId);

		cpDefinitionOptionRel.setCPOptionId(cpOptionId);
		cpDefinitionOptionRel.setNameMap(nameMap);
		cpDefinitionOptionRel.setDescriptionMap(descriptionMap);
		cpDefinitionOptionRel.setDDMFormFieldTypeName(ddmFormFieldTypeName);
		cpDefinitionOptionRel.setPriority(priority);
		cpDefinitionOptionRel.setFacetable(facetable);
		cpDefinitionOptionRel.setSkuContributor(skuContributor);
		cpDefinitionOptionRel.setExpandoBridgeAttributes(serviceContext);

		cpDefinitionOptionRelPersistence.update(cpDefinitionOptionRel);

		return cpDefinitionOptionRel;
	}

}