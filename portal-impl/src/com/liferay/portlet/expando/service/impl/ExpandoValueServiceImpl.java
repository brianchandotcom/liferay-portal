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

package com.liferay.portlet.expando.service.impl;

import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.permission.ExpandoColumnPermissionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.expando.service.base.ExpandoValueServiceBaseImpl;

import java.io.Serializable;

import java.util.Collection;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class ExpandoValueServiceImpl extends ExpandoValueServiceBaseImpl {

	@JSONWebService(mode = JSONWebServiceMode.IGNORE)
	@Override
	public ExpandoValue addValue(
			long companyId, String className, String tableName,
			String columnName, long classPK, Object data)
		throws PortalException {

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			companyId, className, tableName, columnName);

		ExpandoColumnPermissionUtil.check(
			getPermissionChecker(), column, ActionKeys.UPDATE);

		return expandoValueLocalService.addValue(
			companyId, className, tableName, columnName, classPK, data);
	}

	@Override
	public ExpandoValue addValue(
			long companyId, String className, String tableName,
			String columnName, long classPK, String data)
		throws PortalException {

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			companyId, className, tableName, columnName);

		ExpandoColumnPermissionUtil.check(
			getPermissionChecker(), column, ActionKeys.UPDATE);

		return expandoValueLocalService.addValue(
			companyId, className, tableName, columnName, classPK, data);
	}

	@Override
	public void addValues(
			long companyId, String className, String tableName, long classPK,
			Map<String, Serializable> attributeValues)
		throws PortalException {

		for (Map.Entry<String, Serializable> entry :
				attributeValues.entrySet()) {

			addValue(
				companyId, className, tableName, entry.getKey(), classPK,
				entry.getValue());
		}
	}

	@Override
	public Map<String, Serializable> getData(
			long companyId, String className, String tableName,
			Collection<String> columnNames, long classPK)
		throws PortalException {

		Map<String, Serializable> attributeValues =
			expandoValueLocalService.getData(
				companyId, className, tableName, columnNames, classPK);

		for (String columnName : columnNames) {
			ExpandoColumn column = expandoColumnLocalService.getColumn(
				companyId, className, tableName, columnName);

			if (!ExpandoColumnPermissionUtil.contains(
					getPermissionChecker(), column, ActionKeys.VIEW)) {

				attributeValues.remove(columnName);
			}
		}

		return attributeValues;
	}

	@Override
	public Serializable getData(
			long companyId, String className, String tableName,
			String columnName, long classPK)
		throws PortalException {

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			companyId, className, tableName, columnName);

		if (ExpandoColumnPermissionUtil.contains(
				getPermissionChecker(), column, ActionKeys.VIEW)) {

			return expandoValueLocalService.getData(
				companyId, className, tableName, columnName, classPK);
		}

		return null;
	}

	@Override
	public JSONObject getJSONData(
			long companyId, String className, String tableName,
			String columnName, long classPK)
		throws PortalException {

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			companyId, className, tableName, columnName);

		if (!ExpandoColumnPermissionUtil.contains(
				getPermissionChecker(), column, ActionKeys.VIEW)) {

			return null;
		}

		Serializable dataSerializable = expandoValueLocalService.getData(
			companyId, className, tableName, columnName, classPK);

		String data = dataSerializable.toString();

		if (Validator.isNull(data)) {
			return null;
		}

		if (data.startsWith(StringPool.OPEN_CURLY_BRACE)) {
			return JSONFactoryUtil.createJSONObject(data);
		}

		return JSONUtil.put("data", data);
	}

}