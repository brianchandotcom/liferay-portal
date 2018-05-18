/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.service.base.CPDefinitionSpecificationOptionValueLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.ServiceContext;

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

		cpDefinitionSpecificationOptionValue.setUuid(serviceContext.getUuid());
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

		cpDefinitionSpecificationOptionValuePersistence.update(
			cpDefinitionSpecificationOptionValue);

		// Commerce product definition

		reindexCPDefinition(cpDefinitionId);

		return cpDefinitionSpecificationOptionValue;
	}

	@Override
	public void deleteCPDefinitionSpecificationOptionValues(long cpDefinitionId)
		throws PortalException {

		List<CPDefinitionSpecificationOptionValue>
			cpDefinitionSpecificationOptionValues =
				getCPDefinitionSpecificationOptionValues(cpDefinitionId);

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
				getCPSpecificationOptionDefinitionValues(
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
			long cpDefinitionId, long cpSpecificationOptionId) {

		return cpDefinitionSpecificationOptionValuePersistence.fetchByC_CSO(
			cpDefinitionId, cpSpecificationOptionId);
	}

	@Override
	public List<CPDefinitionSpecificationOptionValue>
		getCPDefinitionSpecificationOptionValues(long cpDefinitionId) {

		return cpDefinitionSpecificationOptionValuePersistence.
			findByCPDefinitionId(cpDefinitionId);
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
		getCPSpecificationOptionDefinitionValues(long cpSpecificationOptionId) {

		return cpDefinitionSpecificationOptionValuePersistence.
			findByCPSpecificationOptionId(cpSpecificationOptionId);
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

		cpDefinitionSpecificationOptionValue.setCPOptionCategoryId(
			cpOptionCategoryId);
		cpDefinitionSpecificationOptionValue.setValueMap(valueMap);
		cpDefinitionSpecificationOptionValue.setPriority(priority);
		cpDefinitionSpecificationOptionValue.setExpandoBridgeAttributes(
			serviceContext);

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

		cpDefinitionSpecificationOptionValue.setCPOptionCategoryId(
			cpOptionCategoryId);

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

}