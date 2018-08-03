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

package com.liferay.journal.web.internal.display.context;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.journal.configuration.JournalServiceConfiguration;
import com.liferay.journal.web.configuration.JournalWebConfiguration;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class JournalEditStructuresDisplayContext {

	public JournalEditStructuresDisplayContext(HttpServletRequest request) {
		_request = request;

		_journalWebConfiguration =
			(JournalWebConfiguration)request.getAttribute(
				JournalWebConfiguration.class.getName());
	}

	public boolean autogenerateStructureKey() {
		return _journalWebConfiguration.autogenerateStructureKey();
	}

	public boolean changeableDefaultLanguage() {
		return _journalWebConfiguration.changeableDefaultLanguage();
	}

	public String getAvailableFields() {
		return "Liferay.FormBuilder.AVAILABLE_FIELDS.WCM_STRUCTURE";
	}

	public Locale[] getAvailableLocales() {
		DDMForm ddmForm = getDDMForm();

		if (ddmForm == null) {
			return new Locale[] {LocaleUtil.getSiteDefault()};
		}

		Set<Locale> ddmFormAvailableLocales = ddmForm.getAvailableLocales();

		return ddmFormAvailableLocales.toArray(
			new Locale[ddmFormAvailableLocales.size()]);
	}

	public String getAvailableLocalesJSONArrayString() {
		JSONArray availableLocalesJSONArray = JSONFactoryUtil.createJSONArray();

		for (Locale availableLocale : getAvailableLocales()) {
			availableLocalesJSONArray.put(
				LanguageUtil.getLanguageId(availableLocale));
		}

		if (availableLocalesJSONArray.length() > 0) {
			return availableLocalesJSONArray.toString();
		}

		return StringPool.BLANK;
	}

	public DDMForm getDDMForm() {
		try {
			return DDMUtil.getDDMForm(getScript());
		}
		catch (Exception e) {
		}

		return null;
	}

	public String getFields() throws PortalException {
		DDMStructure structure = getStructure();

		if (structure == null) {
			return StringPool.BLANK;
		}

		JSONArray fieldsJSONArray = DDMUtil.getDDMFormFieldsJSONArray(
			structure.getLatestStructureVersion(), getScript());

		if (fieldsJSONArray != null) {
			return fieldsJSONArray.toString();
		}

		return StringPool.BLANK;
	}

	public String getLocalesMap() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONObject localesMapJSONObject = JSONFactoryUtil.createJSONObject();

		for (Locale availableLocale :
				LanguageUtil.getAvailableLocales(
					themeDisplay.getSiteGroupId())) {

			localesMapJSONObject.put(
				LocaleUtil.toLanguageId(availableLocale),
				availableLocale.getDisplayName(themeDisplay.getLocale()));
		}

		return localesMapJSONObject.toString();
	}

	public long getParentStructureId() {
		if (_parentStructureId != null) {
			return _parentStructureId;
		}

		_parentStructureId = BeanParamUtil.getLong(
			getStructure(), _request, "parentStructureId",
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID);

		return _parentStructureId;
	}

	public String getParentStructureName() {
		if (_parentStructureName != null) {
			return _parentStructureName;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		DDMStructure parentStructure =
			DDMStructureLocalServiceUtil.fetchStructure(getParentStructureId());

		if (parentStructure != null) {
			_parentStructureName = parentStructure.getName(
				themeDisplay.getLocale());
		}

		return _parentStructureName;
	}

	public String getScript() throws PortalException {
		DDMStructure structure = getStructure();

		if (structure != null) {
			_script = BeanParamUtil.getString(
				structure.getLatestStructureVersion(), _request, "definition");

			return _script;
		}

		_script = ParamUtil.getString(_request, "definition");

		return _script;
	}

	public String getStorageType() {
		String storageType = StorageType.JSON.getValue();

		try {
			long companyId = CompanyThreadLocal.getCompanyId();

			JournalServiceConfiguration journalServiceConfiguration =
				ConfigurationProviderUtil.getCompanyConfiguration(
					JournalServiceConfiguration.class, companyId);

			storageType =
				journalServiceConfiguration.journalArticleStorageType();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return storageType;
	}

	public DDMStructure getStructure() {
		if (_structure != null) {
			return _structure;
		}

		_structure = DDMStructureLocalServiceUtil.fetchStructure(
			getStructureId());

		return _structure;
	}

	public long getStructureId() {
		if (_structureId != null) {
			return _structureId;
		}

		_structureId = ParamUtil.getLong(_request, "structureId");

		return _structureId;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalEditStructuresDisplayContext.class);

	private final JournalWebConfiguration _journalWebConfiguration;
	private Long _parentStructureId;
	private String _parentStructureName;
	private final HttpServletRequest _request;
	private String _script;
	private DDMStructure _structure;
	private Long _structureId;

}