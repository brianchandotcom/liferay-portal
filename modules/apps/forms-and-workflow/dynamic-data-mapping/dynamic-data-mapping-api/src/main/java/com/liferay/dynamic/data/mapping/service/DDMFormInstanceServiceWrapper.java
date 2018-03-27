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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DDMFormInstanceService}.
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceService
 * @generated
 */
@ProviderType
public class DDMFormInstanceServiceWrapper implements DDMFormInstanceService,
	ServiceWrapper<DDMFormInstanceService> {
	public DDMFormInstanceServiceWrapper(
		DDMFormInstanceService ddmFormInstanceService) {
		_ddmFormInstanceService = ddmFormInstanceService;
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstance addFormInstance(
		long groupId, long ddmStructureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues settingsDDMFormValues,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmFormInstanceService.addFormInstance(groupId, ddmStructureId,
			nameMap, descriptionMap, settingsDDMFormValues, serviceContext);
	}

	@Override
	public void deleteFormInstance(long ddmFormInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_ddmFormInstanceService.deleteFormInstance(ddmFormInstanceId);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstance fetchFormInstance(
		long ddmFormInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmFormInstanceService.fetchFormInstance(ddmFormInstanceId);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstance getFormInstance(
		long ddmFormInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmFormInstanceService.getFormInstance(ddmFormInstanceId);
	}

	@Override
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMFormInstance> getFormInstances(
		long companyId, long groupId, int start, int end) {
		return _ddmFormInstanceService.getFormInstances(companyId, groupId,
			start, end);
	}

	@Override
	public int getFormInstancesCount(long companyId, long groupId) {
		return _ddmFormInstanceService.getFormInstancesCount(companyId, groupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _ddmFormInstanceService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMFormInstance> search(
		long companyId, long groupId, java.lang.String keywords, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMFormInstance> orderByComparator) {
		return _ddmFormInstanceService.search(companyId, groupId, keywords,
			start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMFormInstance> search(
		long companyId, long groupId, java.lang.String[] names,
		java.lang.String[] descriptions, boolean andOperator, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMFormInstance> orderByComparator) {
		return _ddmFormInstanceService.search(companyId, groupId, names,
			descriptions, andOperator, start, end, orderByComparator);
	}

	@Override
	public int searchCount(long companyId, long groupId,
		java.lang.String keywords) {
		return _ddmFormInstanceService.searchCount(companyId, groupId, keywords);
	}

	@Override
	public int searchCount(long companyId, long groupId,
		java.lang.String[] names, java.lang.String[] descriptions,
		boolean andOperator) {
		return _ddmFormInstanceService.searchCount(companyId, groupId, names,
			descriptions, andOperator);
	}

	/**
	* Updates the the record set's settings.
	*
	* @param formInstanceId the primary key of the form instance
	* @param settingsDDMFormValues the record set's settings. For more
	information see <code>DDMFormValues</code> in the
	<code>dynamic.data.mapping.api</code> module.
	* @return the record set
	* @throws PortalException if a portal exception occurred
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstance updateFormInstance(
		long formInstanceId,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues settingsDDMFormValues)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmFormInstanceService.updateFormInstance(formInstanceId,
			settingsDDMFormValues);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstance updateFormInstance(
		long ddmFormInstanceId, long ddmStructureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues settingsDDMFormValues,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmFormInstanceService.updateFormInstance(ddmFormInstanceId,
			ddmStructureId, nameMap, descriptionMap, settingsDDMFormValues,
			serviceContext);
	}

	@Override
	public DDMFormInstanceService getWrappedService() {
		return _ddmFormInstanceService;
	}

	@Override
	public void setWrappedService(DDMFormInstanceService ddmFormInstanceService) {
		_ddmFormInstanceService = ddmFormInstanceService;
	}

	private DDMFormInstanceService _ddmFormInstanceService;
}