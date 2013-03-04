/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.util;

import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Eudaldo Alonso
 */
public class DDMTemplateTestUtil {

	public static DDMTemplate addDDMTemplate(long ddmStructureId)
		throws Exception {

		return addDDMTemplate(
			ddmStructureId, TemplateConstants.LANG_TYPE_VM,
			getSampleTemplateXSL(), LocaleUtil.getDefault());
	}

	public static DDMTemplate addDDMTemplate(
			long ddmStructureId, Locale defaultLocale)
		throws Exception {

		return addDDMTemplate(
			ddmStructureId, TemplateConstants.LANG_TYPE_VM,
			getSampleTemplateXSL(), defaultLocale);
	}

	public static DDMTemplate addDDMTemplate(long groupId, long ddmStructureId)
		throws Exception {

		return addDDMTemplate(
			groupId, ddmStructureId, TemplateConstants.LANG_TYPE_VM,
			getSampleTemplateXSL(), LocaleUtil.getDefault());
	}

	public static DDMTemplate addDDMTemplate(
			long groupId, long ddmStructureId, Locale defaultLocale)
		throws Exception {

		return addDDMTemplate(
			groupId, ddmStructureId, TemplateConstants.LANG_TYPE_VM,
			getSampleTemplateXSL(), defaultLocale);
	}

	public static DDMTemplate addDDMTemplate(
			long groupId, long ddmStructureId, String language, String script,
			Locale defaultLocale)
		throws Exception {

		Map<Locale, String> nameMap = new HashMap<Locale, String>();

		nameMap.put(defaultLocale, "Test Template");

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return DDMTemplateLocalServiceUtil.addTemplate(
			TestPropsValues.getUserId(), groupId,
			PortalUtil.getClassNameId(DDMStructure.class.getName()),
			ddmStructureId, nameMap, null,
			DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, null, language, script,
			serviceContext);
	}

	public static DDMTemplate addDDMTemplate(
			long ddmStructureId, String language, String script)
		throws Exception {

		return addDDMTemplate(
			TestPropsValues.getGroupId(), ddmStructureId, language, script,
			LocaleUtil.getDefault());
	}

	public static DDMTemplate addDDMTemplate(
			long ddmStructureId, String language, String script,
			Locale defaultLocale)
		throws Exception {

		return addDDMTemplate(
			TestPropsValues.getGroupId(), ddmStructureId, language, script,
			defaultLocale);
	}

	public static void addDynamicContentElement(
		Element dynamicElementElement, String languageId, String value) {

		Element dynamicContentElement = dynamicElementElement.addElement(
			"dynamic-content");

		dynamicContentElement.addAttribute("language-id", languageId);
		dynamicContentElement.setText(value);
	}

	public static Element addDynamicElementElement(
		Element element, String type, String name) {

		Element dynamicElementElement = element.addElement("dynamic-element");

		dynamicElementElement.addAttribute("name", name);
		dynamicElementElement.addAttribute("type", type);

		return dynamicElementElement;
	}

	public static Document createDocument(
		String availableLocales, String defaultLocale) {

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		rootElement.addAttribute("available-locales", availableLocales);
		rootElement.addAttribute("default-locale", defaultLocale);
		rootElement.addElement("request");

		return document;
	}

	public static String getSampleTemplateXSL() {
		return "$name.getData()";
	}

}