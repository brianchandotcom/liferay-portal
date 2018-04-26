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

package com.liferay.expando.kernel.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ExpandoValueService}.
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoValueService
 * @generated
 */
@ProviderType
public class ExpandoValueServiceWrapper implements ExpandoValueService,
	ServiceWrapper<ExpandoValueService> {
	public ExpandoValueServiceWrapper(ExpandoValueService expandoValueService) {
		_expandoValueService = expandoValueService;
	}

	@Override
	public com.liferay.expando.kernel.model.ExpandoValue addValue(
		long companyId, String className, String tableName, String columnName,
		long classPK, Object data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueService.addValue(companyId, className, tableName,
			columnName, classPK, data);
	}

	@Override
	public com.liferay.expando.kernel.model.ExpandoValue addValue(
		long companyId, String className, String tableName, String columnName,
		long classPK, String data)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueService.addValue(companyId, className, tableName,
			columnName, classPK, data);
	}

	@Override
	public void addValues(long companyId, String className, String tableName,
		long classPK,
		java.util.Map<String, java.io.Serializable> attributeValues)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoValueService.addValues(companyId, className, tableName,
			classPK, attributeValues);
	}

	@Override
	public java.util.Map<String, java.io.Serializable> getData(long companyId,
		String className, String tableName,
		java.util.Collection<String> columnNames, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueService.getData(companyId, className, tableName,
			columnNames, classPK);
	}

	@Override
	public java.io.Serializable getData(long companyId, String className,
		String tableName, String columnName, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueService.getData(companyId, className, tableName,
			columnName, classPK);
	}

	@Override
	public com.liferay.portal.kernel.json.JSONObject getJSONData(
		long companyId, String className, String tableName, String columnName,
		long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValueService.getJSONData(companyId, className,
			tableName, columnName, classPK);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _expandoValueService.getOSGiServiceIdentifier();
	}

	@Override
	public ExpandoValueService getWrappedService() {
		return _expandoValueService;
	}

	@Override
	public void setWrappedService(ExpandoValueService expandoValueService) {
		_expandoValueService = expandoValueService;
	}

	private ExpandoValueService _expandoValueService;
}