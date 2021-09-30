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

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.service.base.CPDefinitionSpecificationOptionValueLocalServiceBaseImpl;
import com.liferay.expando.kernel.service.ExpandoRowLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionSpecificationOptionValueLocalServiceImpl
	extends CPDefinitionSpecificationOptionValueLocalServiceBaseImpl {

	@Override
	public CPDefinitionSpecificationOptionValue
			addCPDefinitionSpecificationOptionValue(
				long cpDefinitionId, long cpSpecificationOptionId,
				long cpOptionCategoryId, Map<Locale, String> valueMap,
				double priority, ServiceContext serviceContext)
		throws PortalException {

		CPDefinition cpDefinition = cpDefinitionPersistence.findByPrimaryKey(
			cpDefinitionId);
		User user = userLocalService.getUser(serviceContext.getUserId());

		long cpDefinitionSpecificationOptionValueId =
			counterLocalService.increment();

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue =
				cpDefinitionSpecificationOptionValuePersistence.create(
					cpDefinitionSpecificationOptionValueId);

		if (cpDefinitionLocalService.isVersionable(cpDefinitionId)) {
			cpDefinition = cpDefinitionLocalService.copyCPDefinition(
				cpDefinitionId);

			cpDefinitionId = cpDefinition.getCPDefinitionId();
		}

		cpDefinitionSpecificationOptionValue.setGroupId(
			cpDefinition.getGroupId());
		cpDefinitionSpecificationOptionValue.setCompanyId(user.getCompanyId());
		cpDefinitionSpecificationOptionValue.setUserId(user.getUserId());
		cpDefinitionSpecificationOptionValue.setUserName(user.getFullName());
		cpDefinitionSpecificationOptionValue.setCPDefinitionId(
			cpDefinition.getCPDefinitionId());
		cpDefinitionSpecificationOptionValue.setCPSpecificationOptionId(
			cpSpecificationOptionId);
		cpDefinitionSpecificationOptionValue.setCPOptionCategoryId(
			cpOptionCategoryId);
		cpDefinitionSpecificationOptionValue.setValueMap(valueMap);
		cpDefinitionSpecificationOptionValue.setPriority(priority);
		cpDefinitionSpecificationOptionValue.setExpandoBridgeAttributes(
			serviceContext);

		cpDefinitionSpecificationOptionValue =
			cpDefinitionSpecificationOptionValuePersistence.update(
				cpDefinitionSpecificationOptionValue);

		// Commerce product definition

		reindexCPDefinition(cpDefinitionId);

		return cpDefinitionSpecificationOptionValue;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPDefinitionSpecificationOptionValue
			deleteCPDefinitionSpecificationOptionValue(
				CPDefinitionSpecificationOptionValue
					cpDefinitionSpecificationOptionValue)
		throws PortalException {

		if (cpDefinitionLocalService.isVersionable(
				cpDefinitionSpecificationOptionValue.getCPDefinitionId())) {

			try {
				CPDefinition newCPDefinition =
					cpDefinitionLocalService.copyCPDefinition(
						cpDefinitionSpecificationOptionValue.
							getCPDefinitionId());

				cpDefinitionSpecificationOptionValue =
					cpDefinitionSpecificationOptionValuePersistence.
						findByC_CSOVI(
							newCPDefinition.getCPDefinitionId(),
							cpDefinitionSpecificationOptionValue.
								getCPDefinitionSpecificationOptionValueId());
			}
			catch (PortalException portalException) {
				throw new SystemException(portalException);
			}
		}

		// Commerce product definition specification option value

		cpDefinitionSpecificationOptionValuePersistence.remove(
			cpDefinitionSpecificationOptionValue);

		// Expando

		_expandoRowLocalService.deleteRows(
			cpDefinitionSpecificationOptionValue.
				getCPDefinitionSpecificationOptionValueId());

		reindexCPDefinition(
			cpDefinitionSpecificationOptionValue.getCPDefinitionId());

		return cpDefinitionSpecificationOptionValue;
	}

	@Override
	public CPDefinitionSpecificationOptionValue
			deleteCPDefinitionSpecificationOptionValue(
				long cpDefinitionSpecificationOptionValueId)
		throws PortalException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue =
				cpDefinitionSpecificationOptionValuePersistence.
					findByPrimaryKey(cpDefinitionSpecificationOptionValueId);

		return cpDefinitionSpecificationOptionValueLocalService.
			deleteCPDefinitionSpecificationOptionValue(
				cpDefinitionSpecificationOptionValue);
	}

	@Override
	public void deleteCPDefinitionSpecificationOptionValues(long cpDefinitionId)
		throws PortalException {

		List<CPDefinitionSpecificationOptionValue>
			cpDefinitionSpecificationOptionValues =
				getCPDefinitionSpecificationOptionValues(
					cpDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		// Commerce product definition specification option value

		for (CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue :
					cpDefinitionSpecificationOptionValues) {

			cpDefinitionSpecificationOptionValueLocalService.
				deleteCPDefinitionSpecificationOptionValue(
					cpDefinitionSpecificationOptionValue);
		}

		// Commerce product definition

		reindexCPDefinition(cpDefinitionId);
	}

	@Override
	public void deleteCPSpecificationOptionDefinitionValues(
			long cpSpecificationOptionId)
		throws PortalException {

		List<CPDefinitionSpecificationOptionValue>
			cpDefinitionSpecificationOptionValues =
				getCPDefinitionSpecificationOptionValues(
					cpSpecificationOptionId);

		// Commerce product definition specification option value

		for (CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue :
					cpDefinitionSpecificationOptionValues) {

			cpDefinitionSpecificationOptionValueLocalService.
				deleteCPDefinitionSpecificationOptionValue(
					cpDefinitionSpecificationOptionValue);

			// Commerce product definition

			reindexCPDefinition(
				cpDefinitionSpecificationOptionValue.getCPDefinitionId());
		}
	}

	@Override
	public CPDefinitionSpecificationOptionValue
		fetchCPDefinitionSpecificationOptionValue(
			long cpDefinitionId, long cpDefinitionSpecificationOptionValueId) {

		return cpDefinitionSpecificationOptionValuePersistence.fetchByC_CSOVI(
			cpDefinitionId, cpDefinitionSpecificationOptionValueId);
	}

	@Override
	public List<CPDefinitionSpecificationOptionValue>
		getCPDefinitionSpecificationOptionValues(long cpSpecificationOptionId) {

		return cpDefinitionSpecificationOptionValuePersistence.
			findByCPSpecificationOptionId(cpSpecificationOptionId);
	}

	@Override
	public List<CPDefinitionSpecificationOptionValue>
		getCPDefinitionSpecificationOptionValues(
			long cpSpecificationOptionId, int start, int end) {

		return cpDefinitionSpecificationOptionValuePersistence.
			findByCPSpecificationOptionId(cpSpecificationOptionId, start, end);
	}

	@Override
	public List<CPDefinitionSpecificationOptionValue>
		getCPDefinitionSpecificationOptionValues(
			long cpDefinitionId, int start, int end,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator) {

		return cpDefinitionSpecificationOptionValuePersistence.
			findByCPDefinitionId(cpDefinitionId, start, end, orderByComparator);
	}

	@Override
	public List<CPDefinitionSpecificationOptionValue>
		getCPDefinitionSpecificationOptionValues(
			long cpDefinitionId, long cpOptionCategoryId) {

		return cpDefinitionSpecificationOptionValuePersistence.findByC_COC(
			cpDefinitionId, cpOptionCategoryId);
	}

	@Override
	public List<CPDefinitionSpecificationOptionValue>
		getCPDefinitionSpecificationOptionValuesByC_CSO(
			long cpDefinitionId, long cpSpecificationOptionId) {

		return cpDefinitionSpecificationOptionValuePersistence.findByC_CSO(
			cpDefinitionId, cpSpecificationOptionId);
	}

	@Override
	public int getCPDefinitionSpecificationOptionValuesCount(
		long cpDefinitionId) {

		return cpDefinitionSpecificationOptionValuePersistence.
			countByCPDefinitionId(cpDefinitionId);
	}

	@Override
	public int getCPSpecificationOptionDefinitionValuesCount(
		long cpSpecificationOptionId) {

		return cpDefinitionSpecificationOptionValuePersistence.
			countByCPSpecificationOptionId(cpSpecificationOptionId);
	}

	@Override
	public CPDefinitionSpecificationOptionValue
			updateCPDefinitionSpecificationOptionValue(
				long cpDefinitionSpecificationOptionValueId,
				long cpOptionCategoryId, Map<Locale, String> valueMap,
				double priority, ServiceContext serviceContext)
		throws PortalException {

		// Commerce product definition specification option value

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue =
				cpDefinitionSpecificationOptionValuePersistence.
					findByPrimaryKey(cpDefinitionSpecificationOptionValueId);

		if (cpDefinitionLocalService.isVersionable(
				cpDefinitionSpecificationOptionValue.getCPDefinitionId())) {

			CPDefinition newCPDefinition =
				cpDefinitionLocalService.copyCPDefinition(
					cpDefinitionSpecificationOptionValue.getCPDefinitionId());

			cpDefinitionSpecificationOptionValue =
				cpDefinitionSpecificationOptionValuePersistence.findByC_CSOVI(
					newCPDefinition.getCPDefinitionId(),
					cpDefinitionSpecificationOptionValue.
						getCPDefinitionSpecificationOptionValueId());
		}

		cpDefinitionSpecificationOptionValue.setCPOptionCategoryId(
			cpOptionCategoryId);
		cpDefinitionSpecificationOptionValue.setValueMap(valueMap);
		cpDefinitionSpecificationOptionValue.setPriority(priority);
		cpDefinitionSpecificationOptionValue.setExpandoBridgeAttributes(
			serviceContext);

		cpDefinitionSpecificationOptionValue =
			cpDefinitionSpecificationOptionValuePersistence.update(
				cpDefinitionSpecificationOptionValue);

		// Commerce product definition

		reindexCPDefinition(
			cpDefinitionSpecificationOptionValue.getCPDefinitionId());

		return cpDefinitionSpecificationOptionValue;
	}

	@Override
	public CPDefinitionSpecificationOptionValue updateCPOptionCategoryId(
			long cpDefinitionSpecificationOptionValueId,
			long cpOptionCategoryId)
		throws PortalException {

		// Commerce product definition specification option value

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue =
				cpDefinitionSpecificationOptionValuePersistence.
					findByPrimaryKey(cpDefinitionSpecificationOptionValueId);

		if (cpDefinitionLocalService.isVersionable(
				cpDefinitionSpecificationOptionValue.getCPDefinitionId())) {

			CPDefinition newCPDefinition =
				cpDefinitionLocalService.copyCPDefinition(
					cpDefinitionSpecificationOptionValue.getCPDefinitionId());

			cpDefinitionSpecificationOptionValue =
				cpDefinitionSpecificationOptionValuePersistence.findByC_CSOVI(
					newCPDefinition.getCPDefinitionId(),
					cpDefinitionSpecificationOptionValue.
						getCPDefinitionSpecificationOptionValueId());
		}

		cpDefinitionSpecificationOptionValue.setCPOptionCategoryId(
			cpOptionCategoryId);

		cpDefinitionSpecificationOptionValue =
			cpDefinitionSpecificationOptionValuePersistence.update(
				cpDefinitionSpecificationOptionValue);

		// Commerce product definition

		reindexCPDefinition(
			cpDefinitionSpecificationOptionValue.getCPDefinitionId());

		return cpDefinitionSpecificationOptionValue;
	}

	protected void reindexCPDefinition(long cpDefinitionId)
		throws PortalException {

		Indexer<CPDefinition> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			CPDefinition.class);

		indexer.reindex(CPDefinition.class.getName(), cpDefinitionId);
	}

	@ServiceReference(type = ExpandoRowLocalService.class)
	private ExpandoRowLocalService _expandoRowLocalService;

}