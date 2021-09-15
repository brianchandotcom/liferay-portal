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

package com.liferay.journal.web.internal.portlet.template;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateService;
import com.liferay.dynamic.data.mapping.template.BaseDDMTemplateHandler;
import com.liferay.dynamic.data.mapping.template.DDMTemplateVariableCodeHandler;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.util.JournalContent;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateVariableCodeHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + JournalPortletKeys.JOURNAL,
	service = TemplateHandler.class
)
public class JournalTemplateHandler extends BaseDDMTemplateHandler {

	@Override
	public String getClassName() {
		return JournalArticle.class.getName();
	}

	@Override
	public Map<String, Object> getCustomContextObjects() {
		Map<String, Object> contextObjects = new HashMap<>();

		try {
			contextObjects.put("journalContent", _journalContent);

			// Deprecated

			contextObjects.put("journalContentUtil", _journalContent);
		}
		catch (SecurityException securityException) {
			_log.error(securityException, securityException);
		}

		return contextObjects;
	}

	@Override
	public String getName(Locale locale) {
		String portletTitle = _portal.getPortletTitle(
			JournalPortletKeys.JOURNAL,
			ResourceBundleUtil.getBundle(locale, getClass()));

		return LanguageUtil.format(locale, "x-template", portletTitle, false);
	}

	@Override
	public String getResourceName() {
		return "com.liferay.journal.template";
	}

	@Override
	public String getTemplatesHelpPath(String language) {
		return "com/liferay/journal/web/portlet/template/dependencies" +
			"/template.ftl";
	}

	@Override
	public Map<String, TemplateVariableGroup> getTemplateVariableGroups(
			long classPK, String language, Locale locale)
		throws Exception {

		Map<String, TemplateVariableGroup> templateVariableGroups =
			super.getTemplateVariableGroups(classPK, language, locale);

		TemplateVariableGroup fieldsTemplateVariableGroup =
			templateVariableGroups.get("fields");

		if (fieldsTemplateVariableGroup != null) {
			fieldsTemplateVariableGroup.addVariable(
				"friendly-url", String.class,
				"friendlyURLs[themeDisplay.getLanguageId()]!\"\"");
		}

		String[] restrictedVariables = getRestrictedVariables(language);

		TemplateVariableGroup journalUtilTemplateVariableGroup =
			new TemplateVariableGroup("journal-util", restrictedVariables);

		journalUtilTemplateVariableGroup.addVariable(
			"journal-content", JournalContent.class, "journalContent");

		templateVariableGroups.put(
			"journal-util", journalUtilTemplateVariableGroup);

		TemplateVariableGroup journalServicesTemplateVariableGroup =
			new TemplateVariableGroup("journal-services", restrictedVariables);

		journalServicesTemplateVariableGroup.setAutocompleteEnabled(false);

		journalServicesTemplateVariableGroup.addServiceLocatorVariables(
			JournalArticleLocalService.class, JournalArticleService.class,
			DDMStructureLocalService.class, DDMStructureService.class,
			DDMTemplateLocalService.class, DDMTemplateService.class);

		templateVariableGroups.put(
			journalServicesTemplateVariableGroup.getLabel(),
			journalServicesTemplateVariableGroup);

		return templateVariableGroups;
	}

	@Override
	protected TemplateVariableGroup getStructureFieldsTemplateVariableGroup(
			long ddmStructureId, Locale locale)
		throws PortalException {

		if (ddmStructureId <= 0) {
			return null;
		}

		TemplateVariableGroup templateVariableGroup = new TemplateVariableGroup(
			"fields");

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			ddmStructureId);

		DDMForm fullHierarchyDDMForm = ddmStructure.getFullHierarchyDDMForm();

		Map<String, String> fieldNameVariableNameMap = new LinkedHashMap<>();

		for (DDMFormField ddmFormField :
				fullHierarchyDDMForm.getDDMFormFields()) {

			fieldNameVariableNameMap.put(
				ddmFormField.getName(), ddmFormField.getFieldReference());

			collectNestedFieldNameVariableName(
				ddmFormField, fieldNameVariableNameMap);
		}

		for (Map.Entry<String, String> fieldNameVariableName :
				fieldNameVariableNameMap.entrySet()) {

			String fieldName = fieldNameVariableName.getKey();

			String dataType = ddmStructure.getFieldDataType(fieldName);

			if (Validator.isNull(dataType)) {
				continue;
			}

			if (Objects.equals(
					ddmStructure.getFieldType(fieldName),
					"checkbox_multiple")) {

				DDMFormField ddmFormField = ddmStructure.getDDMFormField(
					fieldName);

				DDMFormFieldOptions ddmFormFieldOptions =
					(DDMFormFieldOptions)ddmFormField.getProperty("options");

				Map<String, LocalizedValue> options =
					ddmFormFieldOptions.getOptions();

				if (options.size() == 1) {
					dataType = "boolean";
				}
			}

			String label = ddmStructure.getFieldLabel(fieldName, locale);
			String tip = ddmStructure.getFieldTip(fieldName, locale);
			boolean repeatable = ddmStructure.getFieldRepeatable(fieldName);

			templateVariableGroup.addFieldVariable(
				label, getFieldVariableClass(),
				fieldNameVariableName.getValue(), tip, dataType, repeatable,
				getTemplateVariableCodeHandler());
		}

		return templateVariableGroup;
	}

	@Override
	protected TemplateVariableCodeHandler getTemplateVariableCodeHandler() {
		return _templateVariableCodeHandler;
	}

	@Reference(unbind = "-")
	protected void setJournalContent(JournalContent journalContent) {
		_journalContent = journalContent;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalTemplateHandler.class);

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	private JournalContent _journalContent;

	@Reference
	private Portal _portal;

	private final TemplateVariableCodeHandler _templateVariableCodeHandler =
		new DDMTemplateVariableCodeHandler(
			JournalTemplateHandler.class.getClassLoader(),
			"com/liferay/journal/web/portlet/template/dependencies/",
			SetUtil.fromArray(
				new String[] {
					"boolean", "date", "document-library", "geolocation",
					"image", "journal-article", "link-to-page"
				}));

}