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

package com.liferay.document.library.web.internal.display.context;

import com.liferay.document.library.web.internal.dynamic.data.mapping.util.DLDDMDisplay;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.service.DDMStorageLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.dynamic.data.mapping.util.DDMDisplay;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Cristina González
 */
public class DLEditFileEntryTypeDisplayContext {

	public DLEditFileEntryTypeDisplayContext(
		DDM ddm, DDMStorageLinkLocalService ddmStorageLinkLocalService,
		DDMStructureLocalService ddmStructureLocalService, Language language,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_ddm = ddm;
		_ddmStorageLinkLocalService = ddmStorageLinkLocalService;
		_ddmStructureLocalService = ddmStructureLocalService;
		_language = language;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
	}

	public String getAvailableFields() {
		DDMDisplay ddmDisplay = new DLDDMDisplay();

		return ddmDisplay.getAvailableFields();
	}

	public boolean getFieldNameEditionDisabled() {
		DDMStructure ddmStructure = _getDDMStructure();

		if (ddmStructure == null) {
			return false;
		}

		int structureStorageLinksCount =
			_ddmStorageLinkLocalService.getStructureStorageLinksCount(
				ddmStructure.getStructureId());

		if ((ddmStructure != null) && (structureStorageLinksCount > 0)) {
			return true;
		}

		return false;
	}

	public String getFieldsJSONArrayString() {
		DDMStructure ddmStructure = _getDDMStructure();

		long ddmStructureId = BeanParamUtil.getLong(
			ddmStructure, _liferayPortletRequest, "structureId");

		String definition = BeanParamUtil.getString(
			ddmStructure, _liferayPortletRequest, "definition");

		return Optional.ofNullable(
			_ddm.getDDMFormFieldsJSONArray(
				_ddmStructureLocalService.fetchDDMStructure(ddmStructureId),
				definition)
		).map(
			JSONArray::toString
		).orElseGet(
			() -> StringPool.BLANK
		);
	}

	public String getLocalesMap() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Locale locale = LocaleUtil.fromLanguageId(
			_language.getLanguageId(_liferayPortletRequest));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		for (Locale availableLocale :
				_language.getAvailableLocales(themeDisplay.getSiteGroupId())) {

			jsonObject.put(
				LocaleUtil.toLanguageId(availableLocale),
				availableLocale.getDisplayName(locale));
		}

		return jsonObject.toString();
	}

	public TranslationManagerInfo getTranslationManagerInfo() {
		String definition = BeanParamUtil.getString(
			_getDDMStructure(), _liferayPortletRequest, "definition");

		if (Validator.isNotNull(definition)) {
			try {
				DDMForm ddmForm = _ddm.getDDMForm(definition);

				Set<Locale> locales = ddmForm.getAvailableLocales();

				return new TranslationManagerInfo(
					locales.toArray(new Locale[0]),
					!Objects.equals(
						LocaleUtil.getSiteDefault(),
						ddmForm.getDefaultLocale()),
					LocaleUtil.toLanguageId(ddmForm.getDefaultLocale()));
			}
			catch (PortalException pe) {
				_log.error(pe, pe);
			}
		}

		return new TranslationManagerInfo(
			new Locale[] {LocaleUtil.getSiteDefault()}, false,
			LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()));
	}

	public class TranslationManagerInfo {

		public TranslationManagerInfo(
			Locale[] availableLocales, boolean changeableDefaultLanguage,
			String defaultLanguageId) {

			_availableLocales = availableLocales;
			_changeableDefaultLanguage = changeableDefaultLanguage;
			_defaultLanguageId = defaultLanguageId;
		}

		public Locale[] getAvailableLocales() {
			return _availableLocales;
		}

		public String getAvailableLocalesString() {
			JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
				Stream.of(
					_availableLocales
				).map(
					locale -> _language.getLanguageId(locale)
				).collect(
					Collectors.toList()
				));

			return jsonArray.toString();
		}

		public String getDefaultLanguageId() {
			return _defaultLanguageId;
		}

		public boolean isChangeableDefaultLanguage() {
			return _changeableDefaultLanguage;
		}

		private final Locale[] _availableLocales;
		private final boolean _changeableDefaultLanguage;
		private final String _defaultLanguageId;

	}

	private DDMStructure _getDDMStructure() {
		return (DDMStructure)_liferayPortletRequest.getAttribute(
			WebKeys.DOCUMENT_LIBRARY_DYNAMIC_DATA_MAPPING_STRUCTURE);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLEditFileEntryTypeDisplayContext.class);

	private final DDM _ddm;
	private final DDMStorageLinkLocalService _ddmStorageLinkLocalService;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final Language _language;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;

}