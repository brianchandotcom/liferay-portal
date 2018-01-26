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

import com.liferay.dynamic.data.mapping.exportimport.content.processor.DDMFormValuesExportImportContentProcessor;
import com.liferay.dynamic.data.mapping.exportimport.staged.model.repository.DDMFormInstanceRecordStagedModelRepository;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONSerializer;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
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
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, DDMFormInstanceRecord record)
		throws Exception {

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, record, record.getFormInstance(),
			PortletDataContext.REFERENCE_TYPE_PARENT);

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

		Map<Long, Long> ddmFormInstanceIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMFormInstance.class);

		long ddmFormInstanceId = MapUtil.getLong(
			ddmFormInstanceIds, record.getFormInstanceId(),
			record.getFormInstanceId());

		Element recordElement = portletDataContext.getImportDataElement(record);

		DDMFormInstanceRecord importedRecord =
			(DDMFormInstanceRecord)record.clone();

		importedRecord.setGroupId(portletDataContext.getScopeGroupId());
		importedRecord.setFormInstanceId(ddmFormInstanceId);

		DDMFormValues ddmFormValues = getImportDDMFormValues(
			portletDataContext, recordElement, importedRecord);

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
			DDMFormInstanceRecord formInstanceRecord)
		throws Exception {

		DDMFormInstance formInstance = formInstanceRecord.getFormInstance();

		DDMFormInstanceVersion formInstanceVersion =
			formInstance.getFormInstanceVersion(
				formInstanceRecord.getFormInstanceVersion());

		DDMStructureVersion structureVersion =
			formInstanceVersion.getStructureVersion();

		String ddmFormValuesPath = recordElement.attributeValue(
			"ddm-form-values-path");

		String serializedDDMFormValues = portletDataContext.getZipEntryAsString(
			ddmFormValuesPath);

		DDMFormValues ddmFormValues =
			_ddmFormValuesJSONDeserializer.deserialize(
				structureVersion.getDDMForm(), serializedDDMFormValues);

		return _ddmFormValuesExportImportContentProcessor.
			replaceImportContentReferences(
				portletDataContext, structureVersion, ddmFormValues);
	}

	@Override
	protected StagedModelRepository<DDMFormInstanceRecord>
		getStagedModelRepository() {

		return _ddmFormInstanceRecordStagedModelRepository;
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

	@Reference
	private DDMFormInstanceLocalService _ddmFormInstanceLocalService;

	@Reference
	private DDMFormInstanceRecordStagedModelRepository
		_ddmFormInstanceRecordStagedModelRepository;

	@Reference
	private DDMFormValuesExportImportContentProcessor
		_ddmFormValuesExportImportContentProcessor;

	@Reference
	private DDMFormValuesJSONDeserializer _ddmFormValuesJSONDeserializer;

	@Reference
	private DDMFormValuesJSONSerializer _ddmFormValuesJSONSerializer;

	@Reference
	private StorageEngine _storageEngine;

}