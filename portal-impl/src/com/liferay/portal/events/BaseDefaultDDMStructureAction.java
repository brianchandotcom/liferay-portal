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

package com.liferay.portal.events;

import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormXSDDeserializerUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;
import com.liferay.util.ContentUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public abstract class BaseDefaultDDMStructureAction extends SimpleAction {

	protected void addDDMStructures(
			long userId, long groupId, long classNameId, String fileName,
			ServiceContext serviceContext)
		throws Exception {

		Locale locale = PortalUtil.getSiteDefaultLocale(groupId);

		List<Element> structureElements = getDDMStructures(fileName, locale);

		for (Element structureElement : structureElements) {
			boolean dynamicStructure = GetterUtil.getBoolean(
				structureElement.elementText("dynamic-structure"));

			if (dynamicStructure) {
				continue;
			}

			String name = structureElement.elementText("name");

			String description = structureElement.elementText("description");

			String ddmStructureKey = name;

			DDMStructure ddmStructure =
				DDMStructureLocalServiceUtil.fetchStructure(
					groupId, classNameId, ddmStructureKey);

			if (ddmStructure != null) {
				continue;
			}

			Element structureElementRootElement = structureElement.element(
				"root");

			String definition = structureElementRootElement.asXML();

			Map<Locale, String> nameMap = new HashMap<Locale, String>();
			Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

			Locale[] locales = LanguageUtil.getAvailableLocales(groupId);

			for (Locale curLocale : locales) {
				nameMap.put(curLocale, LanguageUtil.get(curLocale, name));
				descriptionMap.put(
					curLocale, LanguageUtil.get(curLocale, description));
			}

			Attribute defaultLocaleAttribute =
				structureElementRootElement.attribute("default-locale");

			Locale ddmStructureDefaultLocale = LocaleUtil.fromLanguageId(
				defaultLocaleAttribute.getValue());

			definition = DDMXMLUtil.updateXMLDefaultLocale(
				definition, ddmStructureDefaultLocale, locale);

			if (name.equals(DLFileEntryTypeConstants.NAME_IG_IMAGE) &&
				!UpgradeProcessUtil.isCreateIGImageDocumentType()) {

				continue;
			}

			DDMForm ddmForm = DDMFormXSDDeserializerUtil.deserialize(
				definition);

			ddmStructure = DDMStructureLocalServiceUtil.addStructure(
				userId, groupId,
				DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID, classNameId,
				ddmStructureKey, nameMap, descriptionMap, ddmForm, "xml",
				DDMStructureConstants.TYPE_DEFAULT, serviceContext);

			Element templateElement = structureElement.element("template");

			if (templateElement == null) {
				continue;
			}

			String templateFileName = templateElement.elementText("file-name");

			boolean templateCacheable = GetterUtil.getBoolean(
				templateElement.elementText("cacheable"));

			DDMTemplateLocalServiceUtil.addTemplate(
				userId, groupId, PortalUtil.getClassNameId(DDMStructure.class),
				ddmStructure.getStructureId(), null, nameMap, null,
				DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY,
				DDMTemplateConstants.TEMPLATE_MODE_CREATE,
				TemplateConstants.LANG_TYPE_FTL, getContent(templateFileName),
				templateCacheable, false, StringPool.BLANK, null,
				serviceContext);
		}
	}

	protected String getContent(String fileName) {
		return ContentUtil.get(
			"com/liferay/portal/events/dependencies/" + fileName);
	}

	protected List<Element> getDDMStructures(String fileName, Locale locale)
		throws DocumentException {

		String xml = getContent(fileName);

		xml = StringUtil.replace(xml, "[$LOCALE_DEFAULT$]", locale.toString());

		Document document = SAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		return rootElement.elements("structure");
	}

	protected String getDynamicDDMStructureDefinition(
			String fileName, String dynamicDDMStructureName, Locale locale)
		throws DocumentException {

		List<Element> structureElements = getDDMStructures(fileName, locale);

		for (Element structureElement : structureElements) {
			boolean dynamicStructure = GetterUtil.getBoolean(
				structureElement.elementText("dynamic-structure"));

			if (!dynamicStructure) {
				continue;
			}

			String name = structureElement.elementText("name");

			if (!name.equals(dynamicDDMStructureName)) {
				continue;
			}

			Element structureElementRootElement = structureElement.element(
				"root");

			return structureElementRootElement.asXML();
		}

		return null;
	}

}