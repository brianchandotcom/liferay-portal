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

package com.liferay.portlet.dynamicdatalists.lar;

import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.portlet.dynamicdatalists.service.persistence.DDLRecordSetUtil;
import com.liferay.portlet.dynamicdatamapping.lar.DDMPortletDataHandlerImpl;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Michael C. Han
 */
public class DDLPortletDataHandlerImpl
	extends BasePortletDataHandler implements DDLPortletDataHandler {

	public void exportRecordSet(
			PortletDataContext portletDataContext, Element recordSetsElement,
			DDLRecordSet recordSet)
		throws Exception {

		String path = getRecordSetPath(portletDataContext, recordSet);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element recordSetElement = recordSetsElement.addElement("record-set");

		portletDataContext.addClassedModel(
			recordSetElement, path, recordSet, _NAMESPACE);

		Element ddmStructuresElement = recordSetElement.addElement(
			"ddm-structures");

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		DDMPortletDataHandlerImpl.exportStructure(
			portletDataContext, ddmStructuresElement, ddmStructure);

		Element ddmTemplatesElement = recordSetElement.addElement(
			"ddm-templates");

		List<DDMTemplate> ddmTemplates = ddmStructure.getTemplates();

		for (DDMTemplate ddmTemplate : ddmTemplates) {
			DDMPortletDataHandlerImpl.exportTemplate(
				portletDataContext, ddmTemplatesElement, ddmTemplate);
		}
	}

	public void importRecordSet(
			PortletDataContext portletDataContext, Element recordSetElement)
		throws Exception {

		Element ddmStructuresElement = recordSetElement.element(
			"ddm-structures");

		if (ddmStructuresElement != null) {
			importDDMStructures(portletDataContext, ddmStructuresElement);
		}

		Element ddmTemplatesElement = recordSetElement.element("ddm-templates");

		if (ddmTemplatesElement != null) {
			importDDMTemplates(portletDataContext, ddmTemplatesElement);
		}

		String path = recordSetElement.attributeValue("path");

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		DDLRecordSet recordSet =
			(DDLRecordSet)portletDataContext.getZipEntryAsObject(path);

		long userId = portletDataContext.getUserId(recordSet.getUserUuid());

		Map<Long, Long> structureIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructure.class);

		long structureId = MapUtil.getLong(
			structureIds, recordSet.getDDMStructureId(),
			recordSet.getDDMStructureId());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			recordSetElement, recordSet, _NAMESPACE);

		DDLRecordSet importedRecordSet = null;

		if (portletDataContext.isDataStrategyMirror()) {
			DDLRecordSet existingRecordSet = DDLRecordSetUtil.fetchByUUID_G(
				recordSet.getUuid(), portletDataContext.getScopeGroupId());

			if (existingRecordSet == null) {
				serviceContext.setUuid(recordSet.getUuid());

				importedRecordSet = DDLRecordSetLocalServiceUtil.addRecordSet(
					userId, portletDataContext.getScopeGroupId(), structureId,
					recordSet.getRecordSetKey(), recordSet.getNameMap(),
					recordSet.getDescriptionMap(),
					recordSet.getMinDisplayRows(), recordSet.getScope(),
					serviceContext);
			}
			else {
				importedRecordSet =
					DDLRecordSetLocalServiceUtil.updateRecordSet(
						existingRecordSet.getRecordSetId(), structureId,
						recordSet.getNameMap(), recordSet.getDescriptionMap(),
						recordSet.getMinDisplayRows(), serviceContext);
			}
		}
		else {
			importedRecordSet = DDLRecordSetLocalServiceUtil.addRecordSet(
				userId, portletDataContext.getScopeGroupId(), structureId,
				recordSet.getRecordSetKey(), recordSet.getNameMap(),
				recordSet.getDescriptionMap(), recordSet.getMinDisplayRows(),
				recordSet.getScope(), serviceContext);
		}

		portletDataContext.importClassedModel(
			recordSet, importedRecordSet, _NAMESPACE);
	}

	@Override
	public boolean isAlwaysExportable() {
		return _ALWAYS_EXPORTABLE;
	}

	@Override
	public boolean isDataLocalized() {
		return _DATA_LOCALIZED;
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (!portletDataContext.addPrimaryKey(
				DDLPortletDataHandlerImpl.class, "deleteData")) {

			DDLRecordSetLocalServiceUtil.deleteRecordSets(
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
			"com.liferay.portlet.dynamicdatalist",
			portletDataContext.getScopeGroupId());

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("ddl-data");

		Element recordSetsElement = rootElement.addElement("record-sets");

		List<DDLRecordSet> recordSets =
			DDLRecordSetLocalServiceUtil.getRecordSets(
				portletDataContext.getScopeGroupId());

		for (DDLRecordSet recordSet : recordSets) {
			if (portletDataContext.isWithinDateRange(
					recordSet.getModifiedDate())) {

				exportRecordSet(
					portletDataContext, recordSetsElement, recordSet);
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
			"com.liferay.portlet.dynamicdatalist",
			portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		Element recordSetsElement = rootElement.element("record-sets");

		List<Element> recordSetElements = recordSetsElement.elements(
			"record-set");

		for (Element recordSetElement : recordSetElements) {
			importRecordSet(portletDataContext, recordSetElement);
		}

		return portletPreferences;
	}

	protected String getRecordSetPath(
		PortletDataContext portletDataContext, DDLRecordSet recordSet) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getPortletPath(PortletKeys.DYNAMIC_DATA_LISTS));
		sb.append("/record-sets/");
		sb.append(recordSet.getRecordSetId());
		sb.append(".xml");

		return sb.toString();
	}

	protected void importDDMStructures(
			PortletDataContext portletDataContext,
			Element ddmStructureReferencesElement)
		throws Exception {

		List<Element> ddmStructureElements =
			ddmStructureReferencesElement.elements("structure");

		for (Element ddmStructureElement : ddmStructureElements) {
			DDMPortletDataHandlerImpl.importStructure(
				portletDataContext, ddmStructureElement);
		}
	}

	protected void importDDMTemplates(
			PortletDataContext portletDataContext,
			Element ddmTemplateReferencesElement)
		throws Exception {

		List<Element> ddmTemplateElements =
			ddmTemplateReferencesElement.elements("template");

		for (Element ddmTemplateElement : ddmTemplateElements) {
			DDMPortletDataHandlerImpl.importTemplate(
				portletDataContext, ddmTemplateElement);
		}
	}

	private static final boolean _ALWAYS_EXPORTABLE = true;

	private static final boolean _DATA_LOCALIZED = true;

	private static final String _NAMESPACE = "ddl";

}