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

package com.liferay.exportimport.internal.search.spi.model.index.contributor;

import com.liferay.exportimport.kernel.lar.ExportImportHelper;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.io.Serializable;

import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 * @author Akos Thurzo
 * @author Luan Maoski
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.exportimport.kernel.model.ExportImportConfiguration",
	service = ModelDocumentContributor.class
)
public class ExportImportConfigurationModelDocumentContributor
	implements ModelDocumentContributor<ExportImportConfiguration> {

	@Override
	public void contribute(
		Document document,
		ExportImportConfiguration exportImportConfiguration) {

		document.addText(
			Field.DESCRIPTION, exportImportConfiguration.getDescription());
		document.addText(Field.NAME, exportImportConfiguration.getName());
		document.addKeyword(Field.TYPE, exportImportConfiguration.getType());
		document.addNumber(
			"exportImportConfigurationId",
			exportImportConfiguration.getExportImportConfigurationId());

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		populateDates(document, settingsMap);
		populateLayoutIds(document, settingsMap);
		populateLocale(document, settingsMap);
		populateParameterMap(document, settingsMap);
		populateSiteInformation(document, settingsMap);
		populateTimeZone(document, settingsMap);

		document.addKeyword(
			_PREFIX_SETTING + Field.USER_ID,
			MapUtil.getLong(settingsMap, "userId"));
	}

	protected void populateDates(
		Document document, Map<String, Serializable> settingsMap) {

		if (settingsMap.containsKey("endDate")) {
			Date endDate = (Date)settingsMap.get("endDate");

			document.addDate(_PREFIX_SETTING + "endDate", endDate);
		}

		if (settingsMap.containsKey("startDate")) {
			Date startDate = (Date)settingsMap.get("startDate");

			document.addDate(_PREFIX_SETTING + "startDate", startDate);
		}
	}

	protected void populateLayoutIds(
		Document document, Map<String, Serializable> settingsMap) {

		if (!settingsMap.containsKey("layoutIdMap") &&
			!settingsMap.containsKey("layoutIds")) {

			return;
		}

		long[] layoutIds = GetterUtil.getLongValues(
			settingsMap.get("layoutIds"));

		if (ArrayUtil.isEmpty(layoutIds)) {
			Map<Long, Boolean> layoutIdMap =
				(Map<Long, Boolean>)settingsMap.get("layoutIdMap");

			try {
				layoutIds = _exportImportHelper.getLayoutIds(layoutIdMap);
			}
			catch (PortalException pe) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(pe, pe);
				}
			}
		}

		document.addKeyword("layoutIds", layoutIds);
	}

	protected void populateLocale(
		Document document, Map<String, Serializable> settingsMap) {

		Locale locale = (Locale)settingsMap.get("locale");

		document.addText(_PREFIX_SETTING + "locale", locale.toString());
	}

	protected void populateParameterMap(
		Document document, Map<String, Serializable> settingsMap) {

		if (!settingsMap.containsKey("parameterMap")) {
			return;
		}

		Map<String, String[]> parameterMap =
			(Map<String, String[]>)settingsMap.get("parameterMap");

		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			String parameterName = entry.getKey();

			if (!Field.validateFieldName(parameterName)) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Skipping invalid parameter name: " + parameterName);
				}

				continue;
			}

			String[] parameterValues = ArrayUtil.clone(entry.getValue());

			for (int i = 0; i < parameterValues.length; i++) {
				String parameterValue = parameterValues[i];

				if (parameterValue.equals(StringPool.TRUE)) {
					parameterValues[i] = "on";
				}
				else if (parameterValue.equals(StringPool.FALSE)) {
					parameterValues[i] = "off";
				}
			}

			document.addKeyword(
				_PREFIX_PARAMETER + entry.getKey(), parameterValues);
		}
	}

	protected void populateSiteInformation(
		Document document, Map<String, Serializable> settingsMap) {

		document.addKeyword(
			_PREFIX_SETTING + "privateLayout",
			MapUtil.getBoolean(settingsMap, "privateLayout"));
		document.addKeyword(
			_PREFIX_SETTING + "sourceGroupId",
			MapUtil.getLong(settingsMap, "sourceGroupId"));
		document.addKeyword(
			_PREFIX_SETTING + "targetGroupId",
			MapUtil.getLong(settingsMap, "targetGroupId"));
	}

	protected void populateTimeZone(
		Document document, Map<String, Serializable> settingsMap) {

		TimeZone timeZone = (TimeZone)settingsMap.get("timeZone");

		if (timeZone != null) {
			document.addKeyword(
				_PREFIX_SETTING + "timeZone", timeZone.getDisplayName());
		}
	}

	private static final String _PREFIX_PARAMETER = "parameter_";

	private static final String _PREFIX_SETTING = "setting_";

	private static final Log _log = LogFactoryUtil.getLog(
		ExportImportConfigurationModelDocumentContributor.class);

	@Reference
	private ExportImportHelper _exportImportHelper;

}