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

package com.liferay.portlet.dynamicdatalists.lar;

import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.portlet.dynamicdatalists.service.persistence.DDLRecordSetUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class DDLRecordSetStagedModelDataHandler
	extends BaseStagedModelDataHandler<DDLRecordSet> {

	public static final String[] CLASS_NAMES = {DDLRecordSet.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, DDLRecordSet recordSet)
		throws Exception {

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, ddmStructure);

		List<DDMTemplate> ddmTemplates = ddmStructure.getTemplates();

		Element recordSetElement = portletDataContext.getExportDataElement(
			recordSet);

		for (DDMTemplate ddmTemplate : ddmTemplates) {
			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, ddmTemplate);

			portletDataContext.addReferenceElement(
				recordSet, recordSetElement, ddmTemplate, false);
		}

		portletDataContext.addClassedModel(
			recordSetElement, ExportImportPathUtil.getModelPath(recordSet),
			recordSet, DDLPortletDataHandler.NAMESPACE);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, DDLRecordSet recordSet)
		throws Exception {

		long userId = portletDataContext.getUserId(recordSet.getUserUuid());

		Map<Long, Long> ddmStructureIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructure.class);

		String structurePath = ExportImportPathUtil.getModelPath(
			portletDataContext, DDMStructure.class.getName(),
			recordSet.getDDMStructureId());

		DDMStructure ddmStructure =
			(DDMStructure)portletDataContext.getZipEntryAsObject(structurePath);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, ddmStructure);

		long ddmStructureId = MapUtil.getLong(
			ddmStructureIds, recordSet.getDDMStructureId(),
			recordSet.getDDMStructureId());

		List<Element> ddmTemplateElements =
			portletDataContext.getReferenceDataElements(
				recordSet, DDMTemplate.class);

		for (Element ddmTemplateElement : ddmTemplateElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, ddmTemplateElement);
		}

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			recordSet, DDLPortletDataHandler.NAMESPACE);

		DDLRecordSet importedRecordSet = null;

		if (portletDataContext.isDataStrategyMirror()) {
			DDLRecordSet existingRecordSet = DDLRecordSetUtil.fetchByUUID_G(
				recordSet.getUuid(), portletDataContext.getScopeGroupId());

			if (existingRecordSet == null) {
				serviceContext.setUuid(recordSet.getUuid());

				importedRecordSet = DDLRecordSetLocalServiceUtil.addRecordSet(
					userId, portletDataContext.getScopeGroupId(),
					ddmStructureId, recordSet.getRecordSetKey(),
					recordSet.getNameMap(), recordSet.getDescriptionMap(),
					recordSet.getMinDisplayRows(), recordSet.getScope(),
					serviceContext);
			}
			else {
				importedRecordSet =
					DDLRecordSetLocalServiceUtil.updateRecordSet(
						existingRecordSet.getRecordSetId(), ddmStructureId,
						recordSet.getNameMap(), recordSet.getDescriptionMap(),
						recordSet.getMinDisplayRows(), serviceContext);
			}
		}
		else {
			importedRecordSet = DDLRecordSetLocalServiceUtil.addRecordSet(
				userId, portletDataContext.getScopeGroupId(), ddmStructureId,
				recordSet.getRecordSetKey(), recordSet.getNameMap(),
				recordSet.getDescriptionMap(), recordSet.getMinDisplayRows(),
				recordSet.getScope(), serviceContext);
		}

		portletDataContext.importClassedModel(
			recordSet, importedRecordSet, DDLPortletDataHandler.NAMESPACE);
	}

}