/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portlet.portletdisplaytemplate.util.PortletDisplayTemplateConstants;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Eduardo Garcia
 */
public class DocumentLibraryPortletDisplayTemplateHandler
	extends BasePortletDisplayTemplateHandler {

	public String getClassName() {
		return FileEntry.class.getName();
	}

	public String getName(Locale locale) {
		String portletTitle = PortalUtil.getPortletTitle(
			PortletKeys.DOCUMENT_LIBRARY, locale);

		return portletTitle.concat(StringPool.SPACE).concat(
			LanguageUtil.get(locale, "template"));
	}

	public String getResourceName() {
		return "com.liferay.portlet.documentlibrary";
	}

	@Override
	public Map<String, TemplateVariableGroup> getTemplateVariableGroups(
			long classPK, String language, Locale locale)
		throws Exception {

		Map<String, TemplateVariableGroup> templateVariableGroups =
			super.getTemplateVariableGroups(classPK, language, locale);

		TemplateVariableGroup templateVariableGroup =
			templateVariableGroups.get("fields");

		templateVariableGroup.empty();

		templateVariableGroup.addCollectionVariable(
			"documents", List.class, PortletDisplayTemplateConstants.ENTRIES,
			"document", FileEntry.class, "curFileEntry");

		TemplateVariableGroup documentServicesTemplateVariableGroup =
			new TemplateVariableGroup("document-services");

		documentServicesTemplateVariableGroup.setAutocompleteEnabled(false);

		documentServicesTemplateVariableGroup.addServiceLocatorVariables(
			DLAppLocalService.class, DLAppService.class,
			DLFileEntryTypeLocalService.class, DLFileEntryTypeService.class);

		templateVariableGroups.put(
			documentServicesTemplateVariableGroup.getLabel(),
			documentServicesTemplateVariableGroup);

		return templateVariableGroups;
	}

	@Override
	protected String getTemplatesConfigPath() {
		return PropsValues.DL_DISPLAY_TEMPLATES_CONFIG;
	}

}