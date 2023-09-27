/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.service;

import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DDMDataProviderInstanceService}.
 *
 * @author Brian Wing Shun Chan
 * @see DDMDataProviderInstanceService
 * @generated
 */
public class DDMDataProviderInstanceServiceWrapper
	implements DDMDataProviderInstanceService,
			   ServiceWrapper<DDMDataProviderInstanceService> {

	public DDMDataProviderInstanceServiceWrapper() {
		this(null);
	}

	public DDMDataProviderInstanceServiceWrapper(
		DDMDataProviderInstanceService ddmDataProviderInstanceService) {

		_ddmDataProviderInstanceService = ddmDataProviderInstanceService;
	}

	@Override
	public DDMDataProviderInstance addDataProviderInstance(
			long groupId, java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			com.liferay.dynamic.data.mapping.storage.DDMFormValues
				ddmFormValues,
			String type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmDataProviderInstanceService.addDataProviderInstance(
			groupId, nameMap, descriptionMap, ddmFormValues, type,
			serviceContext);
	}

	@Override
	public void deleteDataProviderInstance(long dataProviderInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_ddmDataProviderInstanceService.deleteDataProviderInstance(
			dataProviderInstanceId);
	}

	@Override
	public DDMDataProviderInstance fetchDataProviderInstance(
			long dataProviderInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmDataProviderInstanceService.fetchDataProviderInstance(
			dataProviderInstanceId);
	}

	@Override
	public DDMDataProviderInstance fetchDataProviderInstanceByUuid(String uuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmDataProviderInstanceService.fetchDataProviderInstanceByUuid(
			uuid);
	}

	@Override
	public DDMDataProviderInstance getDataProviderInstance(
			long dataProviderInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmDataProviderInstanceService.getDataProviderInstance(
			dataProviderInstanceId);
	}

	@Override
	public DDMDataProviderInstance getDataProviderInstanceByUuid(String uuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmDataProviderInstanceService.getDataProviderInstanceByUuid(
			uuid);
	}

	@Override
	public java.util.List<DDMDataProviderInstance> getDataProviderInstances(
		long companyId, long[] groupIds, int start, int end) {

		return _ddmDataProviderInstanceService.getDataProviderInstances(
			companyId, groupIds, start, end);
	}

	@Override
	public int getDataProviderInstancesCount(long companyId, long[] groupIds) {
		return _ddmDataProviderInstanceService.getDataProviderInstancesCount(
			companyId, groupIds);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _ddmDataProviderInstanceService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<DDMDataProviderInstance> search(
		long companyId, long[] groupIds, String keywords, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMDataProviderInstance> orderByComparator) {

		return _ddmDataProviderInstanceService.search(
			companyId, groupIds, keywords, start, end, orderByComparator);
	}

	@Override
	public java.util.List<DDMDataProviderInstance> search(
		long companyId, long[] groupIds, String name, String description,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<DDMDataProviderInstance> orderByComparator) {

		return _ddmDataProviderInstanceService.search(
			companyId, groupIds, name, description, andOperator, start, end,
			orderByComparator);
	}

	@Override
	public int searchCount(long companyId, long[] groupIds, String keywords) {
		return _ddmDataProviderInstanceService.searchCount(
			companyId, groupIds, keywords);
	}

	@Override
	public int searchCount(
		long companyId, long[] groupIds, String name, String description,
		boolean andOperator) {

		return _ddmDataProviderInstanceService.searchCount(
			companyId, groupIds, name, description, andOperator);
	}

	@Override
	public DDMDataProviderInstance updateDataProviderInstance(
			long dataProviderInstanceId,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			com.liferay.dynamic.data.mapping.storage.DDMFormValues
				ddmFormValues,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmDataProviderInstanceService.updateDataProviderInstance(
			dataProviderInstanceId, nameMap, descriptionMap, ddmFormValues,
			serviceContext);
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