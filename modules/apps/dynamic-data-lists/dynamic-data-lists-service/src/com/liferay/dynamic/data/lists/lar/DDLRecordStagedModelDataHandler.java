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

package com.liferay.dynamic.data.lists.lar;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.storage.StorageEngineUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesToFieldsConverterUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataException;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.portlet.exportimport.lar.StagedModelModifiedDateComparator;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class DDLRecordStagedModelDataHandler
	extends BaseStagedModelDataHandler<DDLRecord> {

	public static final String[] CLASS_NAMES = {DDLRecord.class.getName()};

	@Override
	public void deleteStagedModel(DDLRecord record) throws PortalException {
		DDLRecordLocalServiceUtil.deleteRecord(record);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		DDLRecord record = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (record != null) {
			deleteStagedModel(record);
		}
	}

	@Override
	public DDLRecord fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return DDLRecordLocalServiceUtil.fetchDDLRecordByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<DDLRecord> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return DDLRecordLocalServiceUtil.getDDLRecordsByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<DDLRecord>());
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

		DDLRecordVersion recordVersion = record.getRecordVersion();

		DDLRecordSet recordSet = record.getRecordSet();

		DDMFormValues ddmFormValues = StorageEngineUtil.getDDMFormValues(
			recordVersion.getDDMStorageId());

		Fields fields = DDMFormValuesToFieldsConverterUtil.convert(
			recordSet.getDDMStructure(), ddmFormValues);

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

		Map<Long, Long> recordSetIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDLRecordSet.class);

		long recordSetId = MapUtil.getLong(
			recordSetIds, record.getRecordSetId(), record.getRecordSetId());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			record);

		Element recordElement = portletDataContext.getImportDataElement(record);

		Fields fields = (Fields)portletDataContext.getZipEntryAsObject(
			recordElement.attributeValue("fields-path"));

		DDLRecord importedRecord = null;

		if (portletDataContext.isDataStrategyMirror()) {
			DDLRecord existingRecord = fetchStagedModelByUuidAndGroupId(
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

	@Override
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

		if (!portletDataContext.isInitialPublication() &&
			!ArrayUtil.contains(getExportableStatuses(), status)) {

			PortletDataException pde = new PortletDataException(
				PortletDataException.STATUS_UNAVAILABLE);

			pde.setStagedModel(record);

			throw pde;
		}
	}

}