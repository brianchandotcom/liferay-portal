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

package com.liferay.journal.web.portlet.template;

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
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateVariableCodeHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + JournalPortletKeys.JOURNAL},
	service = TemplateHandler.class
)
public class JournalTemplateHandler extends BaseDDMTemplateHandler {

	public static final Map<String, String> journalArticleLanguageContent =
		new HashMap<>();

	static {
		journalArticleLanguageContent.put(
			"css",
			"com/liferay/journal/web/portlet/template/dependencies/" +
				"template.css");
		journalArticleLanguageContent.put(
			"ftl",
			"com/liferay/journal/web/portlet/template/dependencies/" +
				"template.ftl");
		journalArticleLanguageContent.put(
			"vm",
			"com/liferay/journal/web/portlet/template/dependencies/" +
				"template.vm");
	}

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
		catch (SecurityException se) {
			_log.error(se, se);
		}

		return contextObjects;
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		String portletTitle = PortalUtil.getPortletTitle(
			JournalPortletKeys.JOURNAL, resourceBundle);

		return portletTitle.concat(StringPool.SPACE).concat(
			LanguageUtil.get(locale, "template"));
	}

	@Override
	public String getResourceName() {
		return "com.liferay.journal.template";
	}

	@Override
	public String getTemplatesHelpPath(String language) {
		return journalArticleLanguageContent.get(language);
	}

	@Override
	public Map<String, TemplateVariableGroup> getTemplateVariableGroups(
			long classPK, String language, Locale locale)
		throws Exception {

		Map<String, TemplateVariableGroup> templateVariableGroups =
			super.getTemplateVariableGroups(classPK, language, locale);

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
	protected TemplateVariableCodeHandler getTemplateVariableCodeHandler() {
		return _templateVariableCodeHandler;
	}

	@Reference
	protected void setJournalContent(JournalContent journalContent) {
		_journalContent = journalContent;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalTemplateHandler.class);

	private JournalContent _journalContent;
	private final TemplateVariableCodeHandler _templateVariableCodeHandler =
		new DDMTemplateVariableCodeHandler(
			JournalTemplateHandler.class.getClassLoader(),
			"com/liferay/journal/web/portlet/template/dependencies/",
			SetUtil.fromArray(
				new String[] {
					"boolean", "date", "document-library", "geolocation",
					"image", "link-to-page"
				}));

}