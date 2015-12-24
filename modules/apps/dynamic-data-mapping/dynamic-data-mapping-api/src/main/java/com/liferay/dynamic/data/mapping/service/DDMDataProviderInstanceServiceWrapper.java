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

package com.liferay.dynamic.data.mapping.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DDMDataProviderInstanceService}.
 *
 * @author Brian Wing Shun Chan
 * @see DDMDataProviderInstanceService
 * @generated
 */
@ProviderType
public class DDMDataProviderInstanceServiceWrapper
	implements DDMDataProviderInstanceService,
		ServiceWrapper<DDMDataProviderInstanceService> {
	public DDMDataProviderInstanceServiceWrapper(
		DDMDataProviderInstanceService ddmDataProviderInstanceService) {
		_ddmDataProviderInstanceService = ddmDataProviderInstanceService;
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance addDataProviderInstance(
		long groupId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues ddmFormValues,
		java.lang.String type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmDataProviderInstanceService.addDataProviderInstance(groupId,
			nameMap, descriptionMap, ddmFormValues, type, serviceContext);
	}

	@Override
	public void deleteDataProviderInstance(long dataProviderInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_ddmDataProviderInstanceService.deleteDataProviderInstance(dataProviderInstanceId);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance fetchDataProviderInstance(
		long dataProviderInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmDataProviderInstanceService.fetchDataProviderInstance(dataProviderInstanceId);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance getDataProviderInstance(
		long dataProviderInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmDataProviderInstanceService.getDataProviderInstance(dataProviderInstanceId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _ddmDataProviderInstanceService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> search(
		long companyId, long[] groupIds, java.lang.String keywords, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> orderByComparator) {
		return _ddmDataProviderInstanceService.search(companyId, groupIds,
			keywords, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> search(
		long companyId, long[] groupIds, java.lang.String name,
		java.lang.String description, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> orderByComparator) {
		return _ddmDataProviderInstanceService.search(companyId, groupIds,
			name, description, andOperator, start, end, orderByComparator);
	}

	@Override
	public int searchCount(long companyId, long[] groupIds,
		java.lang.String keywords) {
		return _ddmDataProviderInstanceService.searchCount(companyId, groupIds,
			keywords);
	}

	@Override
	public int searchCount(long companyId, long[] groupIds,
		java.lang.String name, java.lang.String description, boolean andOperator) {
		return _ddmDataProviderInstanceService.searchCount(companyId, groupIds,
			name, description, andOperator);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance updateDataProviderInstance(
		long dataProviderInstanceId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues ddmFormValues,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmDataProviderInstanceService.updateDataProviderInstance(dataProviderInstanceId,
			nameMap, descriptionMap, ddmFormValues, serviceContext);
	}

	@Override
	public DDMDataProviderInstanceService getWrappedService() {
		return _ddmDataProviderInstanceService;
	}

	@Override
	public void setWrappedService(
		DDMDataProviderInstanceService ddmDataProviderInstanceService) {
		_ddmDataProviderInstanceService = ddmDataProviderInstanceService;
	}

	private DDMDataProviderInstanceService _ddmDataProviderInstanceService;
}