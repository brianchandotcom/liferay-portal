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

import com.liferay.dynamic.data.mapping.internal.exportimport.staged.model.repository.DDMFormInstanceRecordStagedModelRepository;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
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

	public static final String[] CLASS_NAMES = {
		DDMFormInstanceRecord.class.getName()
	};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(DDMFormInstanceRecord record) {
		return record.getUuid();
	}

	protected DDMFormValues deserialize(String content, DDMForm ddmForm) {
		DDMFormValuesDeserializerDeserializeRequest.Builder builder =
			DDMFormValuesDeserializerDeserializeRequest.Builder.newBuilder(
				content, ddmForm);

		DDMFormValuesDeserializerDeserializeResponse
			ddmFormValuesDeserializerDeserializeResponse =
				_jsonDDMFormValuesDeserializer.deserialize(builder.build());

		return ddmFormValuesDeserializerDeserializeResponse.getDDMFormValues();
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

		if (existingRecord == null) {
			return;
		}

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
			importedRecord.setMvccVersion(existingRecord.getMvccVersion());
			importedRecord.setFormInstanceRecordId(
				existingRecord.getFormInstanceRecordId());

			importedRecord =
				_ddmFormInstanceRecordStagedModelRepository.updateStagedModel(
					portletDataContext, importedRecord, ddmFormValues);
		}

		portletDataContext.importClassedModel(record, importedRecord);
	}

	protected void exportDDMFormValues(
			PortletDataContext portletDataContext,
			DDMFormInstanceRecord ddmFormInstanceRecord, Element recordElement)
		throws Exception {

		String ddmFormValuesPath = ExportImportPathUtil.getModelPath(
			ddmFormInstanceRecord, "ddm-form-values.json");

		recordElement.addAttribute("ddm-form-values-path", ddmFormValuesPath);

		DDMFormValues ddmFormValues = ddmFormInstanceRecord.getDDMFormValues();

		ddmFormValues =
			_ddmFormValuesExportImportContentProcessor.
				replaceExportContentReferences(
					portletDataContext, ddmFormInstanceRecord, ddmFormValues,
					true, false);

		portletDataContext.addZipEntry(
			ddmFormValuesPath, serialize(ddmFormValues));
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

		DDMFormValues ddmFormValues = deserialize(
			serializedDDMFormValues, ddmStructure.getDDMForm());

		return _ddmFormValuesExportImportContentProcessor.
			replaceImportContentReferences(
				portletDataContext, ddmStructure, ddmFormValues);
	}

	@Override
	protected StagedModelRepository<DDMFormInstanceRecord>
		getStagedModelRepository() {

		return _ddmFormInstanceRecordStagedModelRepository;
	}

	protected String serialize(DDMFormValues ddmFormValues) {
		DDMFormValuesSerializerSerializeRequest.Builder builder =
			DDMFormValuesSerializerSerializeRequest.Builder.newBuilder(
				ddmFormValues);

		DDMFormValuesSerializerSerializeResponse
			ddmFormValuesSerializerSerializeResponse =
				_jsonDDMFormValuesSerializer.serialize(builder.build());

		return ddmFormValuesSerializerSerializeResponse.getContent();
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

	@Reference(service = DDMFormInstanceLocalService.class)
	private DDMFormInstanceLocalService _ddmFormInstanceLocalService;

	@Reference(
		service = DDMFormInstanceRecordStagedModelRepository.class,
		target = "(model.class.name=com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord)"
	)
	private DDMFormInstanceRecordStagedModelRepository
		_ddmFormInstanceRecordStagedModelRepository;

	@Reference(
		service = ExportImportContentProcessor.class,
		target = "(model.class.name=com.liferay.dynamic.data.mapping.storage.DDMFormValues)"
	)
	private ExportImportContentProcessor<DDMFormValues>
		_ddmFormValuesExportImportContentProcessor;

	@Reference(target = "(ddm.form.values.deserializer.type=json)")
	private DDMFormValuesDeserializer _jsonDDMFormValuesDeserializer;

	@Reference(target = "(ddm.form.values.serializer.type=json)")
	private DDMFormValuesSerializer _jsonDDMFormValuesSerializer;

}