/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMStructureUtil;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMTemplateUtil;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Marcellus Tavares
 */
public class DDMPortletDataHandlerImpl extends BasePortletDataHandler {

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

	protected static void exportStructure(
			PortletDataContext portletDataContext, Element structuresElement,
			DDMStructure structure)
		throws Exception {

		String path = getStructurePath(portletDataContext, structure);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element structureElement = structuresElement.addElement("structure");

		portletDataContext.addClassedModel(
			structureElement, path, structure, _NAMESPACE);
	}

	protected static void exportTemplate(
			PortletDataContext portletDataContext, Element templatesElement,
			DDMTemplate template)
		throws Exception {

		String path = getTemplatePath(portletDataContext, template);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element templateElement = templatesElement.addElement("template");

		portletDataContext.addClassedModel(
			templateElement, path, template, _NAMESPACE);
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

	protected static void importStructure(
			PortletDataContext portletDataContext, Element structureElement)
		throws Exception {

		String path = structureElement.attributeValue("path");

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		DDMStructure structure =
			(DDMStructure)portletDataContext.getZipEntryAsObject(path);

		long userId = portletDataContext.getUserId(structure.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			structureElement, structure, _NAMESPACE);

		DDMStructure importedStructure = null;

		if (portletDataContext.isDataStrategyMirror()) {
			DDMStructure existingStructure =  DDMStructureUtil.fetchByUUID_G(
				structure.getUuid(), portletDataContext.getScopeGroupId());

			if (existingStructure == null) {
				serviceContext.setUuid(structure.getUuid());

				importedStructure = DDMStructureLocalServiceUtil.addStructure(
					userId, portletDataContext.getScopeGroupId(),
					structure.getClassNameId(), structure.getStructureKey(),
					structure.getNameMap(), structure.getDescriptionMap(),
					structure.getXsd(), structure.getStorageType(),
					serviceContext);
			}
			else {
				importedStructure =
					DDMStructureLocalServiceUtil.updateStructure(
						existingStructure.getStructureId(),
						structure.getNameMap(), structure.getDescriptionMap(),
						structure.getXsd(), serviceContext);
			}
		}
		else {
			importedStructure = DDMStructureLocalServiceUtil.addStructure(
				userId, portletDataContext.getScopeGroupId(),
				structure.getClassNameId(), structure.getStructureKey(),
				structure.getNameMap(), structure.getDescriptionMap(),
				structure.getXsd(), structure.getStorageType(), serviceContext);
		}

		portletDataContext.importClassedModel(
			structure, importedStructure, _NAMESPACE);
	}

	protected static void importTemplate(
			PortletDataContext portletDataContext, Element templateElement)
		throws Exception {

		String path = templateElement.attributeValue("path");

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		DDMTemplate template =
			(DDMTemplate)portletDataContext.getZipEntryAsObject(path);

		long userId = portletDataContext.getUserId(template.getUserUuid());

		Map<Long, Long> structureIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructure.class);

		long structureId = MapUtil.getLong(
			structureIds, template.getStructureId(), template.getStructureId());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			templateElement, template, _NAMESPACE);

		DDMTemplate importedTemplate = null;

		if (portletDataContext.isDataStrategyMirror()) {
			DDMTemplate existingTemplate =  DDMTemplateUtil.fetchByUUID_G(
				template.getUuid(), portletDataContext.getScopeGroupId());

			if (existingTemplate == null) {
				serviceContext.setUuid(template.getUuid());

				importedTemplate = DDMTemplateLocalServiceUtil.addTemplate(
					userId, portletDataContext.getScopeGroupId(), structureId,
					template.getNameMap(), template.getDescriptionMap(),
					template.getType(), template.getMode(),
					template.getLanguage(), template.getScript(),
					serviceContext);
			}
			else {
				importedTemplate = DDMTemplateLocalServiceUtil.updateTemplate(
					existingTemplate.getTemplateId(), template.getNameMap(),
					template.getDescriptionMap(), template.getType(),
					template.getMode(), template.getLanguage(),
					template.getScript(), serviceContext);
			}
		}
		else {
			importedTemplate = DDMTemplateLocalServiceUtil.addTemplate(
				userId, portletDataContext.getScopeGroupId(), structureId,
				template.getNameMap(), template.getDescriptionMap(),
				template.getType(), template.getMode(), template.getLanguage(),
				template.getScript(), serviceContext);
		}

		portletDataContext.importClassedModel(
			template, importedTemplate, _NAMESPACE);
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

		List<DDMStructure> structures = DDMStructureUtil.findByGroupId(
			portletDataContext.getScopeGroupId());

		for (DDMStructure structure : structures) {
			if (portletDataContext.isWithinDateRange(
					structure.getModifiedDate())) {

				exportStructure(
					portletDataContext, structuresElement, structure);
			}
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "templates")) {
			Element templatesElement = rootElement.addElement("templates");

			List<DDMTemplate> templates = DDMTemplateUtil.findByGroupId(
				portletDataContext.getScopeGroupId());

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

	private static final String _NAMESPACE = "ddm";

	private static PortletDataHandlerBoolean _structures =
		new PortletDataHandlerBoolean(_NAMESPACE, "structures", true, true);

	private static PortletDataHandlerBoolean _templates =
		new PortletDataHandlerBoolean(_NAMESPACE, "templates");

}