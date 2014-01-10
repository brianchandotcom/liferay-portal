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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil;

import java.util.Map;

/**
 * @author Daniel Kocsis
 */
public class DDLRecordStagedModelDataHandler
	extends BaseStagedModelDataHandler<DDLRecord> {

	public static final String[] CLASS_NAMES = {DDLRecord.class.getName()};

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException, SystemException {

		DDLRecord record =
			DDLRecordLocalServiceUtil.fetchDDLRecordByUuidAndGroupId(
				uuid, groupId);

		if (record != null) {
			DDLRecordLocalServiceUtil.deleteRecord(record);
		}
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(DDLRecord record) {
		return record.getUuid();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, DDLRecord record)
		throws Exception {

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, record, record.getRecordSet(),
			PortletDataContext.REFERENCE_TYPE_STRONG);

		DDLRecordVersion currentRecordVersion = record.getRecordVersion();

		Fields fields = StorageEngineUtil.getFields(
			currentRecordVersion.getDDMStorageId());

		String fieldsPath = ExportImportPathUtil.getModelPath(
			record, "fields.xml");

		portletDataContext.addZipEntry(fieldsPath, fields);

		Element recordElement = portletDataContext.getExportDataElement(record);

		recordElement.addAttribute("fields-path", fieldsPath);

		portletDataContext.addClassedModel(
			recordElement, ExportImportPathUtil.getModelPath(record), record);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, DDLRecord record)
		throws Exception {

		long userId = portletDataContext.getUserId(record.getUserUuid());

		StagedModelDataHandlerUtil.importReferenceStagedModel(
			portletDataContext, record, DDLRecordSet.class,
			record.getRecordSetId());

		Map<Long, Long> ddlRecordSetIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDLRecordSet.class);

		long recordSetId = MapUtil.getLong(
			ddlRecordSetIds, record.getRecordSetId(), record.getRecordSetId());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			record);

		Element recordElement = portletDataContext.getImportDataElement(record);

		Fields fields = (Fields)portletDataContext.getZipEntryAsObject(
			recordElement.attributeValue("fields-path"));

		DDLRecord importedRecord = null;

		if (portletDataContext.isDataStrategyMirror()) {
			DDLRecord existingRecord =
				DDLRecordLocalServiceUtil.fetchDDLRecordByUuidAndGroupId(
					record.getUuid(), portletDataContext.getScopeGroupId());

			if (existingRecord == null) {
				serviceContext.setUuid(record.getUuid());

				importedRecord = DDLRecordLocalServiceUtil.addRecord(
					userId, portletDataContext.getScopeGroupId(), recordSetId,
					record.getDisplayIndex(), fields, serviceContext);
			}
			else {
				importedRecord = DDLRecordLocalServiceUtil.updateRecord(
					userId, existingRecord.getRecordId(), false,
					record.getDisplayIndex(), fields, true, serviceContext);
			}
		}
		else {
			importedRecord = DDLRecordLocalServiceUtil.addRecord(
				userId, portletDataContext.getScopeGroupId(), recordSetId,
				record.getDisplayIndex(), fields, serviceContext);
		}

		portletDataContext.importClassedModel(record, importedRecord);
	}

	protected void validateExport(
			PortletDataContext portletDataContext, DDLRecord record)
		throws PortletDataException {

		int status = WorkflowConstants.STATUS_ANY;

		try {
			status = record.getStatus();
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}

		if (!ArrayUtil.contains(getExportableStatuses(), status)) {
			PortletDataException pde = new PortletDataException(
				PortletDataException.STATUS_UNAVAILABLE);

			pde.setStagedModel(record);

			throw pde;
		}
	}

}