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

import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.InfoFieldSetEntry;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.web.internal.constants.JournalWebConstants;
import com.liferay.journal.web.internal.portlet.action.ActionUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.exception.PortalException;
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
public class JournalTranslateDisplayContext {

	public JournalTranslateDisplayContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		_liferayPortletResponse = liferayPortletResponse;

		_article = ActionUtil.getArticle(liferayPortletRequest);

		_httpServletRequest = PortalUtil.getHttpServletRequest(
			liferayPortletRequest);

		_infoForm = (InfoForm)_httpServletRequest.getAttribute(
			InfoForm.class.getName());

		_journalEditArticleDisplayContext =
			new JournalEditArticleDisplayContext(
				_httpServletRequest, liferayPortletResponse, _article);

		_sourceInfoItemFieldValues =
			(InfoItemFieldValues)_httpServletRequest.getAttribute(
				JournalWebConstants.SOURCE_INFO_ITEM_FIELD_VALUES);

		_sourceLanguageId = (String)_httpServletRequest.getAttribute(
			JournalWebConstants.SOURCE_LANGUAGE_ID);

		_sourceLocale = LocaleUtil.fromLanguageId(_sourceLanguageId);

		_targetInfoItemFieldValues =
			(InfoItemFieldValues)_httpServletRequest.getAttribute(
				JournalWebConstants.TARGET_INFO_ITEM_FIELD_VALUES);

		_targetLanguageId = (String)_httpServletRequest.getAttribute(
			JournalWebConstants.TARGET_LANGUAGE_ID);

		_targetLocale = LocaleUtil.fromLanguageId(_targetLanguageId);

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
		_translationInfoFieldChecker =
			(TranslationInfoFieldChecker)_httpServletRequest.getAttribute(
				TranslationInfoFieldChecker.class.getName());
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
				_themeDisplay.getCompanyId(),
				_journalEditArticleDisplayContext.getGroupId(),
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
		return _article.getTitle(PortalUtil.getLocale(_httpServletRequest));
	}

	public Map<String, Object> getTranslateLanguagesSelectorData() {
		return HashMapBuilder.<String, Object>put(
			"currentUrl", PortalUtil.getCurrentCompleteURL(_httpServletRequest)
		).put(
			"sourceAvailableLanguages",
			_httpServletRequest.getAttribute(
				JournalWebConstants.AVAILABLE_SOURCE_LANGUAGE_IDS)
		).put(
			"sourceLanguageId", _sourceLanguageId
		).put(
			"targetAvailableLanguages",
			_httpServletRequest.getAttribute(
				JournalWebConstants.AVAILABLE_TARGET_LANGUAGE_IDS)
		).put(
			"targetLanguageId", _targetLanguageId
		).build();
	}

	public PortletURL getUpdateTranslationPortletURL() {
		PortletURL portletURL = _liferayPortletResponse.createActionURL();

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "/journal/update_translation");
		portletURL.setParameter(
			"groupId", String.valueOf(_article.getGroupId()));
		portletURL.setParameter(
			"classNameId",
			String.valueOf(
				PortalUtil.getClassNameId(JournalArticle.class.getName())));
		portletURL.setParameter(
			"classPK", String.valueOf(_article.getResourcePrimKey()));

		return portletURL;
	}

	public boolean hasTranslationPermission() {
		if (_isAvailableTargetLanguageIdsEmpty()) {
			return false;
		}

		return true;
	}

	public boolean isPublishButtonDisabled() throws PortalException {
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

	public boolean isSaveButtonDisabled() throws PortalException {
		return _isAvailableTargetLanguageIdsEmpty();
	}

	private TranslationEntry _getTranslationEntry() {
		if (_translationEntry != null) {
			return _translationEntry;
		}

		_translationEntry =
			TranslationEntryLocalServiceUtil.fetchTranslationEntry(
				JournalArticle.class.getName(), _article.getResourcePrimKey(),
				_targetLanguageId);

		return _translationEntry;
	}

	private boolean _isAvailableTargetLanguageIdsEmpty() {
		List<String> availableTargetLanguageIds =
			(List<String>)_httpServletRequest.getAttribute(
				JournalWebConstants.AVAILABLE_TARGET_LANGUAGE_IDS);

		if (availableTargetLanguageIds.isEmpty()) {
			return true;
		}

		return false;
	}

	private final JournalArticle _article;
	private final HttpServletRequest _httpServletRequest;
	private final InfoForm _infoForm;
	private final JournalEditArticleDisplayContext
		_journalEditArticleDisplayContext;
	private final LiferayPortletResponse _liferayPortletResponse;
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