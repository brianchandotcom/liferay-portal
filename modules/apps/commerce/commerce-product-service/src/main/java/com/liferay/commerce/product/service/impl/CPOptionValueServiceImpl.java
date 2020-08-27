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

import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.commerce.product.service.base.CPOptionValueServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 */
public class CPOptionValueServiceImpl extends CPOptionValueServiceBaseImpl {

	@Override
	public CPOptionValue addCPOptionValue(
			long cpOptionId, Map<Locale, String> titleMap, double priority,
			String key, ServiceContext serviceContext)
		throws PortalException {

		_cpOptionModelResourcePermission.check(
			getPermissionChecker(), cpOptionId, ActionKeys.UPDATE);

		return cpOptionValueLocalService.addCPOptionValue(
			cpOptionId, titleMap, priority, key, serviceContext);
	}

	@Override
	public void deleteCPOptionValue(long cpOptionValueId)
		throws PortalException {

		CPOptionValue cpOptionValue =
			cpOptionValueLocalService.fetchCPOptionValue(cpOptionValueId);

		_cpOptionModelResourcePermission.check(
			getPermissionChecker(), cpOptionValue.getCPOptionId(),
			ActionKeys.UPDATE);

		cpOptionValueLocalService.deleteCPOptionValue(cpOptionValue);
	}

	public CPOptionValue fetchByExternalReferenceCode(
			long companyId, String externalReferenceCode)
		throws PortalException {

		CPOptionValue cpOptionValue =
			cpOptionValueLocalService.fetchByExternalReferenceCode(
				companyId, externalReferenceCode);

		if (cpOptionValue != null) {
			_cpOptionModelResourcePermission.check(
				getPermissionChecker(), cpOptionValue.getCPOptionId(),
				ActionKeys.VIEW);
		}

		return cpOptionValue;
	}

	@Override
	public CPOptionValue fetchCPOptionValue(long cpOptionValueId)
		throws PortalException {

		CPOptionValue cpOptionValue =
			cpOptionValueLocalService.fetchCPOptionValue(cpOptionValueId);

		if (cpOptionValue != null) {
			_cpOptionModelResourcePermission.check(
				getPermissionChecker(), cpOptionValue.getCPOptionId(),
				ActionKeys.VIEW);
		}

		return cpOptionValue;
	}

	@Override
	public CPOptionValue getCPOptionValue(long cpOptionValueId)
		throws PortalException {

		CPOptionValue cpOptionValue =
			cpOptionValueLocalService.getCPOptionValue(cpOptionValueId);

		_cpOptionModelResourcePermission.check(
			getPermissionChecker(), cpOptionValue.getCPOptionId(),
			ActionKeys.VIEW);

		return cpOptionValue;
	}

	@Override
	public List<CPOptionValue> getCPOptionValues(
			long cpOptionId, int start, int end)
		throws PortalException {

		_cpOptionModelResourcePermission.check(
			getPermissionChecker(), cpOptionId, ActionKeys.VIEW);

		return cpOptionValueLocalService.getCPOptionValues(
			cpOptionId, start, end);
	}

	@Override
	public int getCPOptionValuesCount(long cpOptionId) throws PortalException {
		_cpOptionModelResourcePermission.check(
			getPermissionChecker(), cpOptionId, ActionKeys.VIEW);

		return cpOptionValueLocalService.getCPOptionValuesCount(cpOptionId);
	}

	@Override
	public CPOptionValue updateCPOptionValue(
			long cpOptionValueId, Map<Locale, String> titleMap, double priority,
			String key, ServiceContext serviceContext)
		throws PortalException {

		CPOptionValue cpOptionValue = cpOptionValueService.getCPOptionValue(
			cpOptionValueId);

		_cpOptionModelResourcePermission.check(
			getPermissionChecker(), cpOptionValue.getCPOptionId(),
			ActionKeys.VIEW);

		return cpOptionValueLocalService.updateCPOptionValue(
			cpOptionValue.getCPOptionValueId(), titleMap, priority, key,
			serviceContext);
	}

	@Override
	public CPOptionValue upsertCPOptionValue(
			long cpOptionId, Map<Locale, String> nameMap, double priority,
			String key, String externalReferenceCode,
			ServiceContext serviceContext)
		throws PortalException {

		CPOptionValue cpOptionValue =
			cpOptionValueLocalService.fetchByExternalReferenceCode(
				serviceContext.getCompanyId(), externalReferenceCode);

		if (cpOptionValue == null) {
			_cpOptionModelResourcePermission.check(
				getPermissionChecker(), cpOptionId, ActionKeys.UPDATE);
		}

		return cpOptionValueLocalService.upsertCPOptionValue(
			cpOptionId, nameMap, priority, key, externalReferenceCode,
			serviceContext);
	}

	private static volatile ModelResourcePermission<CPOption>
		_cpOptionModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CPOptionValueServiceImpl.class,
				"_cpOptionModelResourcePermission", CPOption.class);

}