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
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.editor.configuration.EditorConfiguration;
import com.liferay.portal.kernel.editor.configuration.EditorConfigurationFactoryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.translation.constants.TranslationPortletKeys;
import com.liferay.translation.info.field.TranslationInfoFieldChecker;
import com.liferay.translation.model.TranslationEntry;
import com.liferay.translation.service.TranslationEntryLocalServiceUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class TranslateDisplayContext {

	public TranslateDisplayContext(
		List<String> availableSourceLanguageIds,
		List<String> availableTargetLanguageIds,
		BooleanSupplier booleanSupplier, String className, long classPK,
		InfoForm infoForm, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, Object object,
		InfoItemFieldValues sourceInfoItemFieldValues, String sourceLanguageId,
		InfoItemFieldValues targetInfoItemFieldValues, String targetLanguageId,
		TranslationInfoFieldChecker translationInfoFieldChecker) {

		_availableSourceLanguageIds = availableSourceLanguageIds;
		_availableTargetLanguageIds = availableTargetLanguageIds;
		_booleanSupplier = booleanSupplier;
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

	public String getAutoTranslateURL() {
		return PortalUtil.getPortalURL(_httpServletRequest) +
			Portal.PATH_MODULE + "/translation/auto_translate";
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

	public Map<String, Object> getInfoFieldSetEntriesData() {
		List<HashMap<String, Object>> infoFieldSetEntries = new ArrayList<>();

		for (InfoFieldSetEntry infoFieldSetEntry : getInfoFieldSetEntries()) {
			List<InfoField> infoFields = getInfoFields(infoFieldSetEntry);

			if (ListUtil.isEmpty(infoFields)) {
				continue;
			}

			Stream<InfoField> stream = infoFields.stream();

			infoFieldSetEntries.add(
				HashMapBuilder.<String, Object>put(
					"fields",
					stream.map(
						infoField -> {
							String infoFieldId =
								"infoField--" + infoField.getName() + "--";

							Map<String, Object> editorConfiguration = null;

							if (getBooleanValue(
									infoField, TextInfoFieldType.HTML)) {

								editorConfiguration = _getInfoFieldEditorConfig(
									infoFieldId);
							}

							return HashMapBuilder.<String, Object>put(
								"editorConfiguration", editorConfiguration
							).put(
								"html",
								getBooleanValue(
									infoField, TextInfoFieldType.HTML)
							).put(
								"id", infoFieldId
							).put(
								"label",
								infoField.getLabel(_themeDisplay.getLocale())
							).put(
								"multiline",
								getBooleanValue(
									infoField, TextInfoFieldType.MULTILINE)
							).put(
								"sourceContent",
								getSourceStringValue(
									infoField, getSourceLocale())
							).put(
								"sourceContentDir",
								LanguageUtil.get(getSourceLocale(), "lang.dir")
							).put(
								"targetContent",
								getTargetStringValue(
									infoField, getTargetLocale())
							).put(
								"targetContentDir",
								LanguageUtil.get(getTargetLocale(), "lang.dir")
							).put(
								"targetLanguageId", getTargetLanguageId()
							).build();
						}
					).collect(
						Collectors.toList()
					)
				).put(
					"legend",
					getInfoFieldSetLabel(
						infoFieldSetEntry, _themeDisplay.getLocale())
				).build());
		}

		return HashMapBuilder.<String, Object>put(
			"aditionalFields",
			HashMapBuilder.<String, Object>put(
				"redirect", ParamUtil.getString(_httpServletRequest, "redirect")
			).put(
				"sourceLanguageId", getSourceLanguageId()
			).put(
				"targetLanguageId", getTargetLanguageId()
			).build()
		).put(
			"autoTranslateEnabled", isAutoTranslateEnabled()
		).put(
			"getAutoTranslateURL", getAutoTranslateURL()
		).put(
			"infoFieldSetEntries", infoFieldSetEntries
		).put(
			"publishButtonDisabled", isPublishButtonDisabled()
		).put(
			"publishButtonLabel",
			LanguageUtil.get(_httpServletRequest, getPublishButtonLabel())
		).put(
			"redirectURL", ParamUtil.getString(_httpServletRequest, "redirect")
		).put(
			"saveButtonDisabled", isSaveButtonDisabled()
		).put(
			"saveButtonLabel",
			LanguageUtil.get(_httpServletRequest, getSaveButtonLabel())
		).put(
			"sourceLanguageId", getSourceLanguageId()
		).put(
			"sourceLanguageIdTitle", getLanguageIdTitle(getSourceLanguageId())
		).put(
			"targetLanguageId", getTargetLanguageId()
		).put(
			"targetLanguageIdTitle", getLanguageIdTitle(getTargetLanguageId())
		).put(
			"translateLanguagesSelectorData",
			getTranslateLanguagesSelectorData()
		).put(
			"translationPermission", hasTranslationPermission()
		).put(
			"updateTranslationPortletURL",
			String.valueOf(getUpdateTranslationPortletURL())
		).put(
			"workflowActions",
			HashMapBuilder.<String, Object>put(
				"PUBLISH", String.valueOf(WorkflowConstants.ACTION_PUBLISH)
			).put(
				"SAVE_DRAFT",
				String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT)
			).build()
		).build();
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
		return PortletURLBuilder.createActionURL(
			_liferayPortletResponse
		).setActionName(
			"/translation/update_translation"
		).setParameter(
			"classNameId", PortalUtil.getClassNameId(_className)
		).setParameter(
			"classPK", _classPK
		).setParameter(
			"groupId", _getGroupId()
		).build();
	}

	public boolean hasTranslationPermission() {
		if (_isAvailableTargetLanguageIdsEmpty()) {
			return false;
		}

		return true;
	}

	public boolean isAutoTranslateEnabled() {
		if (_booleanSupplier.getAsBoolean() && hasTranslationPermission()) {
			return true;
		}

		return false;
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

	private Map<String, Object> _getInfoFieldEditorConfig(String infoFieldId) {
		EditorConfiguration editorConfiguration =
			EditorConfigurationFactoryUtil.getEditorConfiguration(
				TranslationPortletKeys.TRANSLATION, "translateEditor",
				"ckeditor",
				HashMapBuilder.<String, Object>put(
					"liferay-ui:input-editor:allowBrowseDocuments", true
				).put(
					"liferay-ui:input-editor:name", infoFieldId
				).build(),
				_themeDisplay,
				RequestBackedPortletURLFactoryUtil.create(_httpServletRequest));

		return editorConfiguration.getData();
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
	private final BooleanSupplier _booleanSupplier;
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