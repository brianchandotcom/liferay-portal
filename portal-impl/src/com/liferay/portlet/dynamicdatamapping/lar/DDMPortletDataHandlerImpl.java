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

package com.liferay.portlet.dynamicdatamapping.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.ImageUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.dynamicdatamapping.TemplateDuplicateTemplateKeyException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMStructureUtil;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMTemplateUtil;

import java.io.File;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Marcellus Tavares
 * @author Juan Fernández
 */
public class DDMPortletDataHandlerImpl extends BasePortletDataHandler {

	public static void exportStructure(
			PortletDataContext portletDataContext, Element structuresElement,
			DDMStructure structure)
		throws Exception {

		String path = getStructurePath(portletDataContext, structure);

		exportStructure(portletDataContext, structuresElement, path, structure);
	}

	public static void exportStructure(
			PortletDataContext portletDataContext, Element structuresElement,
			String path, DDMStructure structure)
		throws Exception {

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element structureElement = structuresElement.addElement("structure");

		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(
			structure.getCompanyId());

		if (defaultUserId == structure.getUserId()) {
			structureElement.addAttribute("preloaded", "true");
		}

		portletDataContext.addClassedModel(
			structureElement, path, structure, _NAMESPACE);
	}

	public static void exportTemplate(
			PortletDataContext portletDataContext, Element templatesElement,
			DDMTemplate template)
		throws Exception {

		String path = getTemplatePath(portletDataContext, template);

		exportTemplate(portletDataContext, templatesElement, path, template);
	}

	public static void exportTemplate(
			PortletDataContext portletDataContext, Element templatesElement,
			String path, DDMTemplate template)
		throws Exception {

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		// Clone this template to make sure changes to its content are never
		// persisted

		template = (DDMTemplate)template.clone();

		Element templateElement = templatesElement.addElement("template");

		if (template.isSmallImage() &&
			Validator.isNull(template.getSmallImageURL())) {

			Image smallImage = ImageUtil.fetchByPrimaryKey(
				template.getSmallImageId());

			if (smallImage != null) {
				String smallImagePath = getTemplateSmallImagePath(
					portletDataContext, template);

				templateElement.addAttribute(
					"small-image-path", smallImagePath);

				template.setSmallImageType(smallImage.getType());

				portletDataContext.addZipEntry(
					smallImagePath, smallImage.getTextObj());
			}
		}

		portletDataContext.addClassedModel(
			templateElement, path, template, _NAMESPACE);
	}

	public static void importStructure(
			PortletDataContext portletDataContext, Element structureElement)
		throws Exception {

		String path = structureElement.attributeValue("path");

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		DDMStructure structure =
			(DDMStructure)portletDataContext.getZipEntryAsObject(
				structureElement, path);

		prepareLanguagesForImport(structure);

		long userId = portletDataContext.getUserId(structure.getUserUuid());

		Map<Long, Long> structureIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructure.class);

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			structureElement, structure, _NAMESPACE);

		DDMStructure importedStructure = null;

		if (portletDataContext.isDataStrategyMirror()) {
			boolean preloaded = GetterUtil.getBoolean(
				structureElement.attributeValue("preloaded"));

			DDMStructure existingStructure = null;

			if (!preloaded) {
				existingStructure = DDMStructureUtil.fetchByUUID_G(
					structure.getUuid(), portletDataContext.getScopeGroupId());
			}
			else {
				existingStructure = DDMStructureUtil.fetchByG_S(
					portletDataContext.getScopeGroupId(),
					structure.getStructureKey());
			}

			if (existingStructure == null) {
				serviceContext.setUuid(structure.getUuid());

				importedStructure = DDMStructureLocalServiceUtil.addStructure(
					userId, portletDataContext.getScopeGroupId(),
					structure.getParentStructureId(),
					structure.getClassNameId(), structure.getStructureKey(),
					structure.getNameMap(), structure.getDescriptionMap(),
					structure.getXsd(), structure.getStorageType(),
					structure.getType(), serviceContext);
			}
			else {
				importedStructure =
					DDMStructureLocalServiceUtil.updateStructure(
						existingStructure.getStructureId(),
						structure.getParentStructureId(),
						structure.getNameMap(), structure.getDescriptionMap(),
						structure.getXsd(), serviceContext);
			}
		}
		else {
			importedStructure = DDMStructureLocalServiceUtil.addStructure(
				userId, portletDataContext.getScopeGroupId(),
				structure.getParentStructureId(), structure.getClassNameId(),
				structure.getStructureKey(), structure.getNameMap(),
				structure.getDescriptionMap(), structure.getXsd(),
				structure.getStorageType(), structure.getType(),
				serviceContext);
		}

		portletDataContext.importClassedModel(
			structure, importedStructure, _NAMESPACE);

		structureIds.put(
			structure.getStructureId(), importedStructure.getStructureId());
	}

	public static void importTemplate(
			PortletDataContext portletDataContext, Element templateElement)
		throws Exception {

		String path = templateElement.attributeValue("path");

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		DDMTemplate template =
			(DDMTemplate)portletDataContext.getZipEntryAsObject(
				templateElement, path);

		long userId = portletDataContext.getUserId(template.getUserUuid());

		Map<Long, Long> structureIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructure.class);

		long classPK = MapUtil.getLong(
			structureIds, template.getClassPK(), template.getClassPK());

		File smallFile = null;

		String smallImagePath = templateElement.attributeValue(
			"small-image-path");

		if (template.isSmallImage() && Validator.isNotNull(smallImagePath)) {
			byte[] bytes = portletDataContext.getZipEntryAsByteArray(
				smallImagePath);

			if (bytes != null) {
				smallFile = FileUtil.createTempFile(
					template.getSmallImageType());

				FileUtil.write(smallFile, bytes);
			}
		}

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			templateElement, template, _NAMESPACE);

		DDMTemplate importedTemplate = null;

		if (portletDataContext.isDataStrategyMirror()) {
			DDMTemplate existingTemplate = DDMTemplateUtil.fetchByUUID_G(
				template.getUuid(), portletDataContext.getScopeGroupId());

			if (existingTemplate == null) {
				serviceContext.setUuid(template.getUuid());

				importedTemplate = addTemplate(
					userId, portletDataContext.getScopeGroupId(), template,
					classPK, smallFile, serviceContext);
			}
			else {
				importedTemplate = DDMTemplateLocalServiceUtil.updateTemplate(
					existingTemplate.getTemplateId(), template.getNameMap(),
					template.getDescriptionMap(), template.getType(),
					template.getMode(), template.getLanguage(),
					template.getScript(), template.isCacheable(),
					template.isSmallImage(), template.getSmallImageURL(),
					smallFile, serviceContext);
			}
		}
		else {
			importedTemplate = addTemplate(
				userId, portletDataContext.getScopeGroupId(), template, classPK,
				smallFile, serviceContext);
		}

		portletDataContext.importClassedModel(
			template, importedTemplate, _NAMESPACE);
	}

	@Override
	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {_structures, _templates};
	}

	@Override
	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] {_structures, _templates};
	}

	@Override
	public boolean isAlwaysExportable() {
		return _ALWAYS_EXPORTABLE;
	}

	@Override
	public boolean isDataLocalized() {
		return _DATA_LOCALIZED;
	}

	protected static DDMTemplate addTemplate(
			long userId, long groupId, DDMTemplate template, long classPK,
			File smallFile, ServiceContext serviceContext)
		throws Exception {

		DDMTemplate newTemplate = null;

		try {
			return DDMTemplateLocalServiceUtil.addTemplate(
				userId, groupId, template.getClassNameId(), classPK,
				template.getTemplateKey(), template.getNameMap(),
				template.getDescriptionMap(), template.getType(),
				template.getMode(), template.getLanguage(),
				template.getScript(), template.isCacheable(),
				template.isSmallImage(), template.getSmallImageURL(), smallFile,
				serviceContext);
		}
		catch (TemplateDuplicateTemplateKeyException tdtke) {
			newTemplate = DDMTemplateLocalServiceUtil.addTemplate(
				userId, groupId, template.getClassNameId(), classPK, null,
				template.getNameMap(), template.getDescriptionMap(),
				template.getType(), template.getMode(), template.getLanguage(),
				template.getScript(), template.isCacheable(),
				template.isSmallImage(), template.getSmallImageURL(), smallFile,
				serviceContext);

			if (_log.isWarnEnabled()) {
				_log.warn(
					"A template with the key " + template.getTemplateKey() +
						" already exists. The new generated key is " +
							newTemplate.getTemplateKey());
			}
		}

		return newTemplate;
	}

	protected static String getStructurePath(
		PortletDataContext portletDataContext, DDMStructure structure) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getPortletPath(
				PortletKeys.DYNAMIC_DATA_MAPPING));
		sb.append("/structures/");
		sb.append(structure.getStructureId());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getTemplatePath(
		PortletDataContext portletDataContext, DDMTemplate template) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getPortletPath(
				PortletKeys.DYNAMIC_DATA_MAPPING));
		sb.append("/templates/");
		sb.append(template.getTemplateId());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getTemplateSmallImagePath(
			PortletDataContext portletDataContext, DDMTemplate template)
		throws Exception {

		StringBundler sb = new StringBundler(5);

		sb.append(
			portletDataContext.getPortletPath(
				PortletKeys.DYNAMIC_DATA_MAPPING));
		sb.append("/templates/thumbnail-");
		sb.append(template.getTemplateId());
		sb.append(StringPool.PERIOD);
		sb.append(template.getSmallImageType());

		return sb.toString();
	}

	protected static void prepareLanguagesForImport(DDMStructure structure)
		throws PortalException {

		Locale structureDefaultLocale = LocaleUtil.fromLanguageId(
			structure.getDefaultLanguageId());

		Locale[] structureAvailableLocales = LocaleUtil.fromLanguageIds(
			structure.getAvailableLanguageIds());

		Locale defaultImportLocale = LocalizationUtil.getDefaultImportLocale(
			DDMStructure.class.getName(), structure.getPrimaryKey(),
			structureDefaultLocale, structureAvailableLocales);

		structure.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (!portletDataContext.addPrimaryKey(
				DDMPortletDataHandlerImpl.class, "deleteData")) {

			DDMTemplateLocalServiceUtil.deleteTemplates(
				portletDataContext.getScopeGroupId());

			DDMStructureLocalServiceUtil.deleteStructures(
				portletDataContext.getScopeGroupId());
		}

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPermissions(
			"com.liferay.portlet.dynamicdatamapping",
			portletDataContext.getScopeGroupId());

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("ddm-data");

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		Element structuresElement = rootElement.addElement("structures");

		List<DDMStructure> ddmStructures = DDMStructureUtil.findByGroupId(
			portletDataContext.getScopeGroupId());

		for (DDMStructure structure : ddmStructures) {
			if (portletDataContext.isWithinDateRange(
					structure.getModifiedDate())) {

				exportStructure(
					portletDataContext, structuresElement, structure);
			}
		}

		Element templatesElement = rootElement.addElement("templates");

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "templates")) {
			List<DDMTemplate> templates = DDMTemplateUtil.findByG_C(
				portletDataContext.getScopeGroupId(),
				PortalUtil.getClassNameId(DDMStructure.class));

			for (DDMTemplate template : templates) {
				if (portletDataContext.isWithinDateRange(
						template.getModifiedDate())) {

					exportTemplate(
						portletDataContext, templatesElement, template);
				}
			}
		}

		return document.formattedString();
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			"com.liferay.portlet.dynamicdatamapping",
			portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		Element structuresElement = rootElement.element("structures");

		List<Element> structureElements = structuresElement.elements(
			"structure");

		for (Element structureElement : structureElements) {
			importStructure(portletDataContext, structureElement);
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "templates")) {
			Element templatesElement = rootElement.element("templates");

			List<Element> templateElements = templatesElement.elements(
				"template");

			for (Element templateElement : templateElements) {
				importTemplate(portletDataContext, templateElement);
			}
		}

		return portletPreferences;
	}

	private static final boolean _ALWAYS_EXPORTABLE = true;

	private static final boolean _DATA_LOCALIZED = true;

	private static final String _NAMESPACE = "ddm";

	private static Log _log = LogFactoryUtil.getLog(
		DDMPortletDataHandlerImpl.class);

	private static PortletDataHandlerBoolean _structures =
		new PortletDataHandlerBoolean(_NAMESPACE, "structures", true, true);

	private static PortletDataHandlerBoolean _templates =
		new PortletDataHandlerBoolean(_NAMESPACE, "templates");

}