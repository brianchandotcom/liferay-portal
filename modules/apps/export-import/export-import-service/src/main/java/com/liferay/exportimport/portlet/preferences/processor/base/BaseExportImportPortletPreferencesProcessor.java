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

package com.liferay.exportimport.portlet.preferences.processor.base;

import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Portlet;
import com.liferay.portlet.exportimport.lar.PortletDataContext;

import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Mate Thurzo
 */
public abstract class BaseExportImportPortletPreferencesProcessor
	implements ExportImportPortletPreferencesProcessor {

	protected abstract String getExportPortletPreferencesUuid(
			PortletDataContext portletDataContext, Portlet portlet,
			String className, long primaryKeyLong)
		throws Exception;

	protected abstract Long getImportPortletPreferencesNewPrimaryKey(
			PortletDataContext portletDataContext, Class<?> clazz,
			long companyGroupId, Map<Long, Long> primaryKeys, String uuid)
		throws Exception;

	protected void updateExportPortletPreferencesClassPKs(
			PortletDataContext portletDataContext, Portlet portlet,
			PortletPreferences portletPreferences, String key, String className)
		throws Exception {

		String[] oldValues = portletPreferences.getValues(key, null);

		if (oldValues == null) {
			return;
		}

		String[] newValues = new String[oldValues.length];

		for (int i = 0; i < oldValues.length; i++) {
			String oldValue = oldValues[i];

			String newValue = oldValue;

			String[] primaryKeys = StringUtil.split(oldValue);

			for (String primaryKey : primaryKeys) {
				if (!Validator.isNumber(primaryKey)) {
					break;
				}

				long primaryKeyLong = GetterUtil.getLong(primaryKey);

				String uuid = getExportPortletPreferencesUuid(
					portletDataContext, portlet, className, primaryKeyLong);

				if (Validator.isNull(uuid)) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to get UUID for class " + className +
								" with primary key " + primaryKeyLong);
					}

					continue;
				}

				newValue = StringUtil.replace(newValue, primaryKey, uuid);
			}

			newValues[i] = newValue;
		}

		portletPreferences.setValues(key, newValues);
	}

	protected void updateImportPortletPreferencesClassPKs(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences, String key, Class<?> clazz,
			long companyGroupId)
		throws Exception {

		String[] oldValues = portletPreferences.getValues(key, null);

		if (oldValues == null) {
			return;
		}

		Map<Long, Long> primaryKeys =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(clazz);

		String[] newValues = new String[oldValues.length];

		for (int i = 0; i < oldValues.length; i++) {
			String oldValue = oldValues[i];

			String newValue = oldValue;

			String[] uuids = StringUtil.split(oldValue);

			for (String uuid : uuids) {
				Long newPrimaryKey = getImportPortletPreferencesNewPrimaryKey(
					portletDataContext, clazz, companyGroupId, primaryKeys,
					uuid);

				if (Validator.isNull(newPrimaryKey)) {
					if (_log.isWarnEnabled()) {
						StringBundler sb = new StringBundler(8);

						sb.append("Unable to get primary key for ");
						sb.append(clazz);
						sb.append(" with UUID ");
						sb.append(uuid);
						sb.append(" in company group ");
						sb.append(companyGroupId);
						sb.append(" or in group ");
						sb.append(portletDataContext.getScopeGroupId());

						_log.warn(sb.toString());
					}
				}
				else {
					newValue = StringUtil.replace(
						newValue, uuid, newPrimaryKey.toString());
				}
			}

			newValues[i] = newValue;
		}

		portletPreferences.setValues(key, newValues);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseExportImportPortletPreferencesProcessor.class);

}