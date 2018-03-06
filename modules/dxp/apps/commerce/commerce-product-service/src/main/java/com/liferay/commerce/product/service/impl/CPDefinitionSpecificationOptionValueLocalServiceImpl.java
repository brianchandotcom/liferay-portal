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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
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

		return cpDefinitionSpecificationOptionValue;
	}

	@Override
	public void deleteCPDefinitionSpecificationOptionValues(
		long cpDefinitionId) {

		cpDefinitionSpecificationOptionValuePersistence.removeByCPDefinitionId(
			cpDefinitionId);
	}

	@Override
	public void deleteCPSpecificationOptionDefinitionValues(
		long cpSpecificationOptionId) {

		cpDefinitionSpecificationOptionValuePersistence.
			removeByCPSpecificationOptionId(cpSpecificationOptionId);
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
	public CPDefinitionSpecificationOptionValue
			updateCPDefinitionSpecificationOptionValue(
				long cpDefinitionSpecificationOptionValueId,
				long cpOptionCategoryId, Map<Locale, String> valueMap,
				double priority, ServiceContext serviceContext)
		throws PortalException {

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

		return cpDefinitionSpecificationOptionValue;
	}

	@Override
	public CPDefinitionSpecificationOptionValue updateCPOptionCategoryId(
			long cpDefinitionSpecificationOptionValueId,
			long cpOptionCategoryId)
		throws PortalException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue =
				cpDefinitionSpecificationOptionValuePersistence.
					findByPrimaryKey(cpDefinitionSpecificationOptionValueId);

		cpDefinitionSpecificationOptionValue.setCPOptionCategoryId(
			cpOptionCategoryId);

		cpDefinitionSpecificationOptionValuePersistence.update(
			cpDefinitionSpecificationOptionValue);

		return cpDefinitionSpecificationOptionValue;
	}

}