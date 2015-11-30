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

package com.liferay.dynamic.data.mapping.service.impl;

import com.liferay.dynamic.data.mapping.constants.DDMActionKeys;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.service.base.DDMDataProviderInstanceServiceBaseImpl;
import com.liferay.dynamic.data.mapping.service.permission.DDMDataProviderInstancePermission;
import com.liferay.dynamic.data.mapping.service.permission.DDMPermission;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class DDMDataProviderInstanceServiceImpl
	extends DDMDataProviderInstanceServiceBaseImpl {

	@Override
	public DDMDataProviderInstance addDataProviderInstance(
			long groupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, DDMFormValues ddmFormValues,
			String type, ServiceContext serviceContext)
		throws PortalException {

		DDMPermission.check(
			getPermissionChecker(), groupId,
			DDMActionKeys.ADD_DATA_PROVIDER_INSTANCE,
			DDMDataProviderInstance.class.getName());

		return ddmDataProviderInstanceLocalService.addDataProviderInstance(
			getUserId(), groupId, nameMap, descriptionMap, ddmFormValues, type,
			serviceContext);
	}

	@Override
	public void deleteDataProviderInstance(long dataProviderInstanceId)
		throws PortalException {

		DDMDataProviderInstancePermission.check(
			getPermissionChecker(), dataProviderInstanceId, ActionKeys.DELETE);

		ddmDataProviderInstanceLocalService.deleteDataProviderInstance(
			dataProviderInstanceId);
	}

	@Override
	public DDMDataProviderInstance fetchDataProviderInstance(
			long dataProviderInstanceId)
		throws PortalException {

		DDMDataProviderInstance dataProviderInstance =
			ddmDataProviderInstanceLocalService.fetchDataProviderInstance(
				dataProviderInstanceId);

		if (dataProviderInstance == null) {
			return null;
		}

		DDMDataProviderInstancePermission.check(
			getPermissionChecker(),
			dataProviderInstance.getDataProviderInstanceId(), ActionKeys.VIEW);

		return dataProviderInstance;
	}

	@Override
	public DDMDataProviderInstance getDataProviderInstance(
			long dataProviderInstanceId)
		throws PortalException {

		DDMDataProviderInstancePermission.check(
			getPermissionChecker(), dataProviderInstanceId, ActionKeys.VIEW);

		return ddmDataProviderInstanceLocalService.getDataProviderInstance(
			dataProviderInstanceId);
	}

	@Override
	public List<DDMDataProviderInstance> search(
		long companyId, long[] groupIds, String keywords, int start, int end,
		OrderByComparator<DDMDataProviderInstance> orderByComparator) {

		return ddmDataProviderInstanceFinder.filterByKeywords(
			companyId, groupIds, keywords, start, end, orderByComparator);
	}

	@Override
	public List<DDMDataProviderInstance> search(
		long companyId, long[] groupIds, String name, String description,
		boolean andOperator, int start, int end,
		OrderByComparator<DDMDataProviderInstance> orderByComparator) {

		return ddmDataProviderInstanceFinder.filterFindByC_G_N_D(
			companyId, groupIds, name, description, andOperator, start, end,
			orderByComparator);
	}

	@Override
	public int searchCount(long companyId, long[] groupIds, String keywords) {
		return ddmDataProviderInstanceFinder.filterCountByKeywords(
			companyId, groupIds, keywords);
	}

	@Override
	public int searchCount(
		long companyId, long[] groupIds, String name, String description,
		boolean andOperator) {

		return ddmDataProviderInstanceFinder.filterCountByC_G_N_D(
			companyId, groupIds, name, description, andOperator);
	}

	@Override
	public DDMDataProviderInstance updateDataProviderInstance(
			long dataProviderInstanceId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws PortalException {

		DDMDataProviderInstancePermission.check(
			getPermissionChecker(), dataProviderInstanceId, ActionKeys.UPDATE);

		return ddmDataProviderInstanceLocalService.updateDataProviderInstance(
			getUserId(), dataProviderInstanceId, nameMap, descriptionMap,
			ddmFormValues, serviceContext);
	}

}