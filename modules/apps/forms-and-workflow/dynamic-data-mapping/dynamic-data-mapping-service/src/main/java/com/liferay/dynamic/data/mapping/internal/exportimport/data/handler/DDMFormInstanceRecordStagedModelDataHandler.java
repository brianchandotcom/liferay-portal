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

package com.liferay.dynamic.data.mapping.internal.exportimport.data.handler;

import com.liferay.dynamic.data.mapping.exportimport.staged.model.repository.DDMFormInstanceRecordStagedModelRepository;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONSerializer;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class DDMFormInstanceRecordStagedModelDataHandler
	extends BaseStagedModelDataHandler<DDMFormInstanceRecord> {

	public static final String[] CLASS_NAMES =
		{DDMFormInstanceRecord.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(DDMFormInstanceRecord record) {
		return record.getUuid();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, DDMFormInstanceRecord record)
		throws Exception {

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, record, record.getFormInstance(),
			PortletDataContext.REFERENCE_TYPE_STRONG);

		Element recordElement = portletDataContext.getExportDataElement(record);

		exportDDMFormValues(portletDataContext, record, recordElement);

		portletDataContext.addClassedModel(
			recordElement, ExportImportPathUtil.getModelPath(record), record);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long recordId)
		throws Exception {

		DDMFormInstanceRecord existingRecord = fetchMissingReference(
			uuid, groupId);

		Map<Long, Long> recordIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMFormInstanceRecord.class);

		recordIds.put(recordId, existingRecord.getFormInstanceRecordId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, DDMFormInstanceRecord record)
		throws Exception {

		Map<Long, Long> recordSetIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMFormInstance.class);

		long ddmFormInstanceId = MapUtil.getLong(
			recordSetIds, record.getFormInstanceId(),
			record.getFormInstanceId());

		Element recordElement = portletDataContext.getImportDataElement(record);

		DDMFormValues ddmFormValues = getImportDDMFormValues(
			portletDataContext, recordElement, ddmFormInstanceId);

		DDMFormInstanceRecord importedRecord =
			(DDMFormInstanceRecord)record.clone();

		importedRecord.setGroupId(portletDataContext.getScopeGroupId());
		importedRecord.setFormInstanceId(ddmFormInstanceId);

		DDMFormInstanceRecord existingRecord =
			_ddmFormInstanceRecordStagedModelRepository.
				fetchStagedModelByUuidAndGroupId(
					record.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingRecord == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedRecord =
				_ddmFormInstanceRecordStagedModelRepository.addStagedModel(
					portletDataContext, importedRecord, ddmFormValues);
		}
		else {
			importedRecord.setFormInstanceRecordId(
				existingRecord.getFormInstanceRecordId());

			importedRecord =
				_ddmFormInstanceRecordStagedModelRepository.updateStagedModel(
					portletDataContext, importedRecord, ddmFormValues);
		}

		portletDataContext.importClassedModel(record, importedRecord);
	}

	protected void exportDDMFormValues(
			PortletDataContext portletDataContext, DDMFormInstanceRecord record,
			Element recordElement)
		throws Exception {

		String ddmFormValuesPath = ExportImportPathUtil.getModelPath(
			record, "ddm-form-values.json");

		recordElement.addAttribute("ddm-form-values-path", ddmFormValuesPath);

		DDMFormValues ddmFormValues = _storageEngine.getDDMFormValues(
			record.getStorageId());

		ddmFormValues =
			_ddmFormValuesExportImportContentProcessor.
				replaceExportContentReferences(
					portletDataContext, record, ddmFormValues, true, false);

		portletDataContext.addZipEntry(
			ddmFormValuesPath,
			_ddmFormValuesJSONSerializer.serialize(ddmFormValues));
	}

	protected DDMFormValues getImportDDMFormValues(
			PortletDataContext portletDataContext, Element recordElement,
			long ddmFormInstanceId)
		throws Exception {

		DDMFormInstance recordSet =
			_ddmFormInstanceLocalService.getFormInstance(ddmFormInstanceId);

		DDMStructure ddmStructure = recordSet.getStructure();

		String ddmFormValuesPath = recordElement.attributeValue(
			"ddm-form-values-path");

		String serializedDDMFormValues = portletDataContext.getZipEntryAsString(
			ddmFormValuesPath);

		DDMFormValues ddmFormValues =
			_ddmFormValuesJSONDeserializer.deserialize(
				ddmStructure.getDDMForm(), serializedDDMFormValues);

		return _ddmFormValuesExportImportContentProcessor.
			replaceImportContentReferences(
				portletDataContext, ddmStructure, ddmFormValues);
	}

	@Override
	protected StagedModelRepository<DDMFormInstanceRecord>
		getStagedModelRepository() {

		return _ddmFormInstanceRecordStagedModelRepository;
	}

	@Reference(unbind = "-")
	protected void setDDMFormInstanceLocalService(
		DDMFormInstanceLocalService ddmFormInstanceLocalService) {

		_ddmFormInstanceLocalService = ddmFormInstanceLocalService;
	}

	@Reference(
		target = "(model.class.name=com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord)",
		unbind = "-"
	)
	protected void setDDMFormInstanceRecordStagedModelRepository(
		DDMFormInstanceRecordStagedModelRepository
			ddmFormInstanceRecordStagedModelRepository) {

		_ddmFormInstanceRecordStagedModelRepository =
			ddmFormInstanceRecordStagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.dynamic.data.mapping.storage.DDMFormValues)",
		unbind = "-"
	)
	protected void setDDMFormValuesExportImportContentProcessor(
		ExportImportContentProcessor<DDMFormValues>
			ddmFormValuesExportImportContentProcessor) {

		_ddmFormValuesExportImportContentProcessor =
			ddmFormValuesExportImportContentProcessor;
	}

	@Reference(unbind = "-")
	protected void setDDMFormValuesJSONDeserializer(
		DDMFormValuesJSONDeserializer ddmFormValuesJSONDeserializer) {

		_ddmFormValuesJSONDeserializer = ddmFormValuesJSONDeserializer;
	}

	@Reference(unbind = "-")
	protected void setDDMFormValuesJSONSerializer(
		DDMFormValuesJSONSerializer ddmFormValuesJSONSerializer) {

		_ddmFormValuesJSONSerializer = ddmFormValuesJSONSerializer;
	}

	@Reference(unbind = "-")
	protected void setStorageEngine(StorageEngine storageEngine) {
		_storageEngine = storageEngine;
	}

	@Override
	protected void validateExport(
			PortletDataContext portletDataContext, DDMFormInstanceRecord record)
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

			pde.setStagedModelDisplayName(record.getUuid());
			pde.setStagedModelClassName(record.getModelClassName());
			pde.setStagedModelClassPK(
				GetterUtil.getString(record.getFormInstanceRecordId()));

			throw pde;
		}
	}

	private DDMFormInstanceLocalService _ddmFormInstanceLocalService;
	private DDMFormInstanceRecordStagedModelRepository
		_ddmFormInstanceRecordStagedModelRepository;
	private ExportImportContentProcessor<DDMFormValues>
		_ddmFormValuesExportImportContentProcessor;
	private DDMFormValuesJSONDeserializer _ddmFormValuesJSONDeserializer;
	private DDMFormValuesJSONSerializer _ddmFormValuesJSONSerializer;
	private StorageEngine _storageEngine;

}