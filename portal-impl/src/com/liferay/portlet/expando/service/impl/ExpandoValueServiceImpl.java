/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoValue;
import com.liferay.portlet.expando.service.base.ExpandoValueServiceBaseImpl;
import com.liferay.portlet.expando.service.permission.ExpandoColumnPermissionUtil;

import java.io.Serializable;

import java.util.Collection;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class ExpandoValueServiceImpl extends ExpandoValueServiceBaseImpl {

	@Override
	public ExpandoValue addValue(
			long companyId, String className, String tableName,
			String columnName, long classPK, Object data)
		throws PortalException, SystemException {

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
		throws PortalException, SystemException {

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
		throws PortalException, SystemException {

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
		throws PortalException, SystemException {

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
		throws PortalException, SystemException {

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			companyId, className, tableName, columnName);

		if (ExpandoColumnPermissionUtil.contains(
				getPermissionChecker(), column, ActionKeys.VIEW)) {

			return expandoValueLocalService.getData(
				companyId, className, tableName, columnName, classPK);
		}
		else {
			return null;
		}
	}

	@Override
	public JSONObject getJSONData(
			long companyId, String className, String tableName,
			String columnName, long classPK)
		throws PortalException, SystemException {

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			companyId, className, tableName, columnName);

		if (ExpandoColumnPermissionUtil.contains(
				getPermissionChecker(), column, ActionKeys.VIEW)) {

			String data = expandoValueLocalService.getData(
				companyId, className, tableName, columnName, classPK,
				StringPool.BLANK);

			if (Validator.isNotNull(data)) {
				if (!data.startsWith(StringPool.OPEN_CURLY_BRACE)) {
					data = "{data:".concat(data).concat("}");
				}

				return JSONFactoryUtil.createJSONObject(data);
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}
	}

}