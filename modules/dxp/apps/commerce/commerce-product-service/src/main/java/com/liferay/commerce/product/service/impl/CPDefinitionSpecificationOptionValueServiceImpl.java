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
import com.liferay.commerce.product.service.base.CPDefinitionSpecificationOptionValueServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionSpecificationOptionValueServiceImpl
	extends CPDefinitionSpecificationOptionValueServiceBaseImpl {

	@Override
	public CPDefinitionSpecificationOptionValue
			addCPDefinitionSpecificationOptionValue(
				long cpDefinitionId, long cpSpecificationOptionId,
				long cpOptionCategoryId, Map<Locale, String> valueMap,
				double priority, ServiceContext serviceContext)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return cpDefinitionSpecificationOptionValueLocalService.
			addCPDefinitionSpecificationOptionValue(
				cpDefinitionId, cpSpecificationOptionId, cpOptionCategoryId,
				valueMap, priority, serviceContext);
	}

	@Override
	public void deleteCPDefinitionSpecificationOptionValue(
			long cpDefinitionSpecificationOptionValueId)
		throws PortalException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue =
				cpDefinitionSpecificationOptionValuePersistence.
					findByPrimaryKey(cpDefinitionSpecificationOptionValueId);

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(),
			cpDefinitionSpecificationOptionValue.getCPDefinition(),
			ActionKeys.UPDATE);

		cpDefinitionSpecificationOptionValueLocalService.
			deleteCPDefinitionSpecificationOptionValue(
				cpDefinitionSpecificationOptionValue);
	}

	@Override
	public CPDefinitionSpecificationOptionValue
			fetchCPDefinitionSpecificationOptionValue(
				long cpDefinitionSpecificationOptionValueId)
		throws PortalException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue =
				cpDefinitionSpecificationOptionValueLocalService.
					fetchCPDefinitionSpecificationOptionValue(
						cpDefinitionSpecificationOptionValueId);

		if (cpDefinitionSpecificationOptionValue != null) {
			_cpDefinitionModelResourcePermission.check(
				getPermissionChecker(),
				cpDefinitionSpecificationOptionValue.getCPDefinition(),
				ActionKeys.VIEW);
		}

		return cpDefinitionSpecificationOptionValue;
	}

	@Override
	public CPDefinitionSpecificationOptionValue
			getCPDefinitionSpecificationOptionValue(
				long cpDefinitionSpecificationOptionValueId)
		throws PortalException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue =
				cpDefinitionSpecificationOptionValueLocalService.
					getCPDefinitionSpecificationOptionValue(
						cpDefinitionSpecificationOptionValueId);

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(),
			cpDefinitionSpecificationOptionValue.getCPDefinitionId(),
			ActionKeys.VIEW);

		return cpDefinitionSpecificationOptionValue;
	}

	@Override
	public List<CPDefinitionSpecificationOptionValue>
			getCPDefinitionSpecificationOptionValues(long cpDefinitionId)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.VIEW);

		return cpDefinitionSpecificationOptionValueLocalService.
			getCPDefinitionSpecificationOptionValues(cpDefinitionId);
	}

	@Override
	public List<CPDefinitionSpecificationOptionValue>
			getCPDefinitionSpecificationOptionValues(
				long cpDefinitionId, long cpOptionCategoryId)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.VIEW);

		return cpDefinitionSpecificationOptionValueLocalService.
			getCPDefinitionSpecificationOptionValues(
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
				cpDefinitionSpecificationOptionValueLocalService.
					getCPDefinitionSpecificationOptionValue(
						cpDefinitionSpecificationOptionValueId);

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(),
			cpDefinitionSpecificationOptionValue.getCPDefinition(),
			ActionKeys.UPDATE);

		return cpDefinitionSpecificationOptionValueLocalService.
			updateCPDefinitionSpecificationOptionValue(
				cpDefinitionSpecificationOptionValueId, cpOptionCategoryId,
				valueMap, priority, serviceContext);
	}

	private static volatile ModelResourcePermission<CPDefinition>
		_cpDefinitionModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CPDefinitionSpecificationOptionValueServiceImpl.class,
				"_cpDefinitionModelResourcePermission", CPDefinition.class);

}