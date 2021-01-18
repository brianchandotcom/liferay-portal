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

package com.liferay.translation.web.internal.display.context;

import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.InfoFieldSetEntry;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.translation.info.field.TranslationInfoFieldChecker;
import com.liferay.translation.model.TranslationEntry;
import com.liferay.translation.service.TranslationEntryLocalServiceUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class TranslateDisplayContext {

	public TranslateDisplayContext(
		List<String> availableSourceLanguageIds,
		List<String> availableTargetLanguageIds, String className, long classPK,
		InfoForm infoForm, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, Object object,
		InfoItemFieldValues sourceInfoItemFieldValues, String sourceLanguageId,
		InfoItemFieldValues targetInfoItemFieldValues, String targetLanguageId,
		TranslationInfoFieldChecker translationInfoFieldChecker) {

		_availableSourceLanguageIds = availableSourceLanguageIds;
		_availableTargetLanguageIds = availableTargetLanguageIds;
		_className = className;
		_classPK = classPK;
		_infoForm = infoForm;
		_liferayPortletResponse = liferayPortletResponse;
		_object = object;
		_sourceInfoItemFieldValues = sourceInfoItemFieldValues;
		_sourceLanguageId = sourceLanguageId;
		_targetInfoItemFieldValues = targetInfoItemFieldValues;
		_targetLanguageId = targetLanguageId;
		_translationInfoFieldChecker = translationInfoFieldChecker;

		_httpServletRequest = PortalUtil.getHttpServletRequest(
			liferayPortletRequest);

		_sourceLocale = LocaleUtil.fromLanguageId(_sourceLanguageId);

		_targetLocale = LocaleUtil.fromLanguageId(_targetLanguageId);

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public boolean getBooleanValue(
		InfoField<TextInfoFieldType> infoField,
		InfoFieldType.Attribute<TextInfoFieldType, Boolean> attribute) {

		Optional<Boolean> attributeOptional = infoField.getAttributeOptional(
			attribute);

		return attributeOptional.orElse(false);
	}

	public String getInfoFieldLabel(InfoField infoField) {
		InfoLocalizedValue<String> labelInfoLocalizedValue =
			infoField.getLabelInfoLocalizedValue();

		return labelInfoLocalizedValue.getValue(
			PortalUtil.getLocale(_httpServletRequest));
	}

	public List<InfoField> getInfoFields(InfoFieldSetEntry infoFieldSetEntry) {
		if (infoFieldSetEntry instanceof InfoField) {
			InfoField infoField = (InfoField)infoFieldSetEntry;

			if (_translationInfoFieldChecker.isTranslatable(infoField)) {
				return Arrays.asList(infoField);
			}
		}
		else if (infoFieldSetEntry instanceof InfoFieldSet) {
			InfoFieldSet infoFieldSet = (InfoFieldSet)infoFieldSetEntry;

			return ListUtil.filter(
				infoFieldSet.getAllInfoFields(),
				_translationInfoFieldChecker::isTranslatable);
		}

		return Collections.emptyList();
	}

	public List<InfoFieldSetEntry> getInfoFieldSetEntries() {
		return _infoForm.getInfoFieldSetEntries();
	}

	public String getInfoFieldSetLabel(
		InfoFieldSetEntry infoFieldSetEntry, Locale locale) {

		if (infoFieldSetEntry instanceof InfoFieldSet) {
			InfoFieldSet infoFieldSet = (InfoFieldSet)infoFieldSetEntry;

			return infoFieldSet.getLabel(locale);
		}

		return null;
	}

	public String getLanguageIdTitle(String languageId) {
		return StringUtil.replace(
			languageId, CharPool.UNDERLINE, CharPool.DASH);
	}

	public String getPublishButtonLabel() {
		if (WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(
				_themeDisplay.getCompanyId(), _getGroupId(),
				TranslationEntry.class.getName())) {

			return "submit-for-publication";
		}

		return "publish";
	}

	public String getSaveButtonLabel() {
		TranslationEntry translationEntry = _getTranslationEntry();

		if ((translationEntry == null) || translationEntry.isApproved() ||
			translationEntry.isDraft() || translationEntry.isExpired() ||
			translationEntry.isScheduled()) {

			return "save-as-draft";
		}

		return "save";
	}

	public String getSourceLanguageId() {
		return _sourceLanguageId;
	}

	public Locale getSourceLocale() {
		return _sourceLocale;
	}

	public String getSourceStringValue(InfoField infoField, Locale locale) {
		InfoFieldValue<Object> infoFieldValue =
			_sourceInfoItemFieldValues.getInfoFieldValue(infoField.getName());

		if (infoFieldValue != null) {
			return GetterUtil.getString(infoFieldValue.getValue(locale));
		}

		return null;
	}

	public String getTargetLanguageId() {
		return _targetLanguageId;
	}

	public Locale getTargetLocale() {
		return _targetLocale;
	}

	public String getTargetStringValue(InfoField infoField, Locale locale) {
		InfoFieldValue<Object> infoFieldValue =
			_targetInfoItemFieldValues.getInfoFieldValue(infoField.getName());

		if (infoFieldValue != null) {
			return GetterUtil.getString(infoFieldValue.getValue(locale));
		}

		return null;
	}

	public String getTitle() {
		if (_sourceInfoItemFieldValues == null) {
			return LanguageUtil.get(_themeDisplay.getLocale(), "translation");
		}

		InfoFieldValue<Object> infoFieldValue =
			_sourceInfoItemFieldValues.getInfoFieldValue("title");

		return (String)infoFieldValue.getValue(_themeDisplay.getLocale());
	}

	public Map<String, Object> getTranslateLanguagesSelectorData() {
		return HashMapBuilder.<String, Object>put(
			"currentUrl", PortalUtil.getCurrentCompleteURL(_httpServletRequest)
		).put(
			"sourceAvailableLanguages", _availableSourceLanguageIds
		).put(
			"sourceLanguageId", _sourceLanguageId
		).put(
			"targetAvailableLanguages", _availableTargetLanguageIds
		).put(
			"targetLanguageId", _targetLanguageId
		).build();
	}

	public PortletURL getUpdateTranslationPortletURL() {
		PortletURL portletURL = _liferayPortletResponse.createActionURL();

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "/translation/update_translation");
		portletURL.setParameter("groupId", String.valueOf(_getGroupId()));
		portletURL.setParameter(
			"classNameId",
			String.valueOf(PortalUtil.getClassNameId(_className)));
		portletURL.setParameter("classPK", String.valueOf(_classPK));

		return portletURL;
	}

	public boolean hasTranslationPermission() {
		if (_isAvailableTargetLanguageIdsEmpty()) {
			return false;
		}

		return true;
	}

	public boolean isPublishButtonDisabled() {
		if (_isAvailableTargetLanguageIdsEmpty()) {
			return true;
		}

		TranslationEntry translationEntry = _getTranslationEntry();

		if ((translationEntry != null) &&
			(translationEntry.getStatus() !=
				WorkflowConstants.STATUS_APPROVED) &&
			(translationEntry.getStatus() != WorkflowConstants.STATUS_DRAFT)) {

			return true;
		}

		return false;
	}

	public boolean isSaveButtonDisabled() {
		return _isAvailableTargetLanguageIdsEmpty();
	}

	private long _getGroupId() {
		if (_groupId != null) {
			return _groupId;
		}

		_groupId = BeanParamUtil.getLong(
			_object, _httpServletRequest, "groupId",
			_themeDisplay.getScopeGroupId());

		return _groupId;
	}

	private TranslationEntry _getTranslationEntry() {
		if (_translationEntry != null) {
			return _translationEntry;
		}

		_translationEntry =
			TranslationEntryLocalServiceUtil.fetchTranslationEntry(
				_className, _classPK, _targetLanguageId);

		return _translationEntry;
	}

	private boolean _isAvailableTargetLanguageIdsEmpty() {
		if (_availableTargetLanguageIds.isEmpty()) {
			return true;
		}

		return false;
	}

	private final List<String> _availableSourceLanguageIds;
	private final List<String> _availableTargetLanguageIds;
	private final String _className;
	private final long _classPK;
	private Long _groupId;
	private final HttpServletRequest _httpServletRequest;
	private final InfoForm _infoForm;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final Object _object;
	private final InfoItemFieldValues _sourceInfoItemFieldValues;
	private final String _sourceLanguageId;
	private final Locale _sourceLocale;
	private final InfoItemFieldValues _targetInfoItemFieldValues;
	private final String _targetLanguageId;
	private final Locale _targetLocale;
	private final ThemeDisplay _themeDisplay;
	private TranslationEntry _translationEntry;
	private final TranslationInfoFieldChecker _translationInfoFieldChecker;

}