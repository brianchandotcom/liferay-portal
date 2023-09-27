/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.expando.kernel.service;

import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ExpandoValueService}.
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoValueService
 * @generated
 */
public class ExpandoValueServiceWrapper
	implements ExpandoValueService, ServiceWrapper<ExpandoValueService> {

	public ExpandoValueServiceWrapper() {
		this(null);
	}

	public ExpandoValueServiceWrapper(ExpandoValueService expandoValueService) {
		_expandoValueService = expandoValueService;
	}

	@Override
	public ExpandoValue addValue(
			long companyId, String className, String tableName,
			String columnName, long classPK, Object data)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _expandoValueService.addValue(
			companyId, className, tableName, columnName, classPK, data);
	}

	@Override
	public ExpandoValue addValue(
			long companyId, String className, String tableName,
			String columnName, long classPK, String data)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _expandoValueService.addValue(
			companyId, className, tableName, columnName, classPK, data);
	}

	@Override
	public void addValues(
			long companyId, String className, String tableName, long classPK,
			java.util.Map<String, java.io.Serializable> attributeValues)
		throws com.liferay.portal.kernel.exception.PortalException {

		_expandoValueService.addValues(
			companyId, className, tableName, classPK, attributeValues);
	}

	@Override
	public java.util.Map<String, java.io.Serializable> getData(
			long companyId, String className, String tableName,
			java.util.Collection<String> columnNames, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _expandoValueService.getData(
			companyId, className, tableName, columnNames, classPK);
	}

	@Override
	public java.io.Serializable getData(
			long companyId, String className, String tableName,
			String columnName, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _expandoValueService.getData(
			companyId, className, tableName, columnName, classPK);
	}

	@Override
	public com.liferay.portal.kernel.json.JSONObject getJSONData(
			long companyId, String className, String tableName,
			String columnName, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _expandoValueService.getJSONData(
			companyId, className, tableName, columnName, classPK);
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