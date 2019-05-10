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

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessor;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import javax.portlet.PortletPreferences;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Máté Thurzó
 */
@ProviderType
public abstract class BaseExportImportPortletPreferencesProcessor
	implements ExportImportPortletPreferencesProcessor {

	protected abstract String getExportPortletPreferencesValue(
			PortletDataContext portletDataContext, Portlet portlet,
			String className, long primaryKeyLong)
		throws Exception;

	protected abstract Long getImportPortletPreferencesNewValue(
			PortletDataContext portletDataContext, Class<?> clazz,
			long companyGroupId, Map<Long, Long> primaryKeys,
			String portletPreferencesOldValue)
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

				String newPreferencesValue = getExportPortletPreferencesValue(
					portletDataContext, portlet, className, primaryKeyLong);

				if (Validator.isNull(newPreferencesValue)) {
					if (_log.isWarnEnabled()) {
						StringBundler sb = new StringBundler(5);

						sb.append("Unable to export portlet preferences ");
						sb.append("value for class ");
						sb.append(className);
						sb.append(" with primary key ");
						sb.append(primaryKeyLong);

						_log.warn(sb.toString());
					}

					continue;
				}

				newValue = StringUtil.replace(
					newValue, primaryKey, newPreferencesValue);
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

			String[] portletPreferencesOldValues = StringUtil.split(oldValue);

			for (String portletPreferencesOldValue :
					portletPreferencesOldValues) {

				Long newPrimaryKey = getImportPortletPreferencesNewValue(
					portletDataContext, clazz, companyGroupId, primaryKeys,
					portletPreferencesOldValue);

				if (Validator.isNull(newPrimaryKey)) {
					if (_log.isInfoEnabled()) {
						_log.info(
							"Unable to import portlet preferences value " +
								portletPreferencesOldValue);
					}
				}
				else {
					newValue = StringUtil.replace(
						newValue, portletPreferencesOldValue,
						newPrimaryKey.toString());
				}
			}

			newValues[i] = newValue;
		}

		portletPreferences.setValues(key, newValues);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseExportImportPortletPreferencesProcessor.class);

}