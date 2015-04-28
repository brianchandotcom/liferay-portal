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

package com.liferay.portlet.documentlibrary.template;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portletdisplaytemplate.BasePortletDisplayTemplateHandler;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.service.DLAppLocalService;
import com.liferay.portlet.documentlibrary.service.DLAppService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeService;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.portletdisplaytemplate.util.PortletDisplayTemplateConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Eduardo Garcia
 */
public class DocumentLibraryPortletDisplayTemplateHandler
	extends BasePortletDisplayTemplateHandler {

	@Override
	public String getClassName() {
		return FileEntry.class.getName();
	}

	@Override
	public Map<String, Object> getCustomContextObjects() {
		Map<String, Object> contextObjects = new HashMap<>();

		try {
			contextObjects.put("dlUtil", DLUtil.getDL());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		return contextObjects;
	}

	@Override
	public String getName(Locale locale) {
		String portletTitle = PortalUtil.getPortletTitle(
			PortletKeys.DOCUMENT_LIBRARY, locale);

		return portletTitle.concat(StringPool.SPACE).concat(
			LanguageUtil.get(locale, "template"));
	}

	@Override
	public String getResourceName() {
		return PortletKeys.DOCUMENT_LIBRARY;
	}

	@Override
	public Map<String, TemplateVariableGroup> getTemplateVariableGroups(
			long classPK, String language, Locale locale)
		throws Exception {

		Map<String, TemplateVariableGroup> templateVariableGroups =
			super.getTemplateVariableGroups(classPK, language, locale);

		String[] restrictedVariables = getRestrictedVariables(language);

		TemplateVariableGroup documentUtilTemplateVariableGroup =
			new TemplateVariableGroup("document-util", restrictedVariables);

		documentUtilTemplateVariableGroup.addVariable(
			"dl-util", DLUtil.class, "dlUtil");

		templateVariableGroups.put(
			"document-util", documentUtilTemplateVariableGroup);

		TemplateVariableGroup documentServicesTemplateVariableGroup =
			new TemplateVariableGroup("document-services", restrictedVariables);

		documentServicesTemplateVariableGroup.setAutocompleteEnabled(false);

		documentServicesTemplateVariableGroup.addServiceLocatorVariables(
			DLAppLocalService.class, DLAppService.class,
			DLFileEntryTypeLocalService.class, DLFileEntryTypeService.class);

		templateVariableGroups.put(
			documentServicesTemplateVariableGroup.getLabel(),
			documentServicesTemplateVariableGroup);

		TemplateVariableGroup fieldsTemplateVariableGroup =
			templateVariableGroups.get("fields");

		fieldsTemplateVariableGroup.empty();

		fieldsTemplateVariableGroup.addCollectionVariable(
			"documents", List.class, PortletDisplayTemplateConstants.ENTRIES,
			"document", FileEntry.class, "curFileEntry", "title");

		return templateVariableGroups;
	}

	@Override
	protected String getTemplatesConfigPath() {
		return PropsValues.DL_DISPLAY_TEMPLATES_CONFIG;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DocumentLibraryPortletDisplayTemplateHandler.class);

}