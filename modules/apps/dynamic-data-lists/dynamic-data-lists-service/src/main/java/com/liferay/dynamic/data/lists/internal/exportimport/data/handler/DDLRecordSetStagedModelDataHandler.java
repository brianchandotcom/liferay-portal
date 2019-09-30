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

package com.liferay.dynamic.data.lists.internal.exportimport.data.handler;

import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordSetSettings;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class DDLRecordSetStagedModelDataHandler
	extends BaseStagedModelDataHandler<DDLRecordSet> {

	public static final String[] CLASS_NAMES = {DDLRecordSet.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(DDLRecordSet recordSet) {
		return recordSet.getNameCurrentValue();
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
			PortletDataContext portletDataContext, DDLRecordSet recordSet)
		throws Exception {

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, recordSet, ddmStructure,
			PortletDataContext.REFERENCE_TYPE_STRONG);

		List<DDMTemplate> ddmTemplates = ddmStructure.getTemplates();

		Element recordSetElement = portletDataContext.getExportDataElement(
			recordSet);

		for (DDMTemplate ddmTemplate : ddmTemplates) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, recordSet, ddmTemplate,
				PortletDataContext.REFERENCE_TYPE_STRONG);
		}

		if (recordSet.getScope() == DDLRecordSetConstants.SCOPE_FORMS) {
			exportRecordSetSettings(
				portletDataContext, recordSet, recordSetElement);
		}

		portletDataContext.addClassedModel(
			recordSetElement, ExportImportPathUtil.getModelPath(recordSet),
			recordSet);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long recordSetId)
		throws Exception {

		DDLRecordSet existingRecordSet = fetchMissingReference(uuid, groupId);

		if (existingRecordSet == null) {
			return;
		}

		Map<Long, Long> recordSetIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDLRecordSet.class);

		recordSetIds.put(recordSetId, existingRecordSet.getRecordSetId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, DDLRecordSet recordSet)
		throws Exception {

		Map<Long, Long> ddmStructureIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructure.class);

		long ddmStructureId = MapUtil.getLong(
			ddmStructureIds, recordSet.getDDMStructureId(),
			recordSet.getDDMStructureId());

		DDLRecordSet importedRecordSet = (DDLRecordSet)recordSet.clone();

		importedRecordSet.setGroupId(portletDataContext.getScopeGroupId());
		importedRecordSet.setDDMStructureId(ddmStructureId);

		DDLRecordSet existingRecordSet =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				recordSet.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingRecordSet == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedRecordSet = _stagedModelRepository.addStagedModel(
				portletDataContext, importedRecordSet);
		}
		else {
			importedRecordSet.setMvccVersion(
				existingRecordSet.getMvccVersion());
			importedRecordSet.setRecordSetId(
				existingRecordSet.getRecordSetId());

			importedRecordSet = _stagedModelRepository.updateStagedModel(
				portletDataContext, importedRecordSet);
		}

		if (recordSet.getScope() == DDLRecordSetConstants.SCOPE_FORMS) {
			Element recordSetElement = portletDataContext.getImportDataElement(
				recordSet);

			DDMFormValues settingsDDMFormValues = getImportRecordSetSettings(
				portletDataContext, recordSetElement);

			_ddlRecordSetLocalService.updateRecordSet(
				importedRecordSet.getRecordSetId(), settingsDDMFormValues);
		}

		portletDataContext.importClassedModel(recordSet, importedRecordSet);
	}

	protected void exportRecordSetSettings(
		PortletDataContext portletDataContext, DDLRecordSet recordSet,
		Element recordSetElement) {

		String settingsDDMFormValuesPath = ExportImportPathUtil.getModelPath(
			recordSet, "settings-ddm-form-values.json");

		recordSetElement.addAttribute(
			"settings-ddm-form-values-path", settingsDDMFormValuesPath);

		portletDataContext.addZipEntry(
			settingsDDMFormValuesPath, recordSet.getSettings());
	}

	protected DDMFormValues getImportRecordSetSettings(
			PortletDataContext portletDataContext, Element recordSetElement)
		throws Exception {

		DDMForm ddmForm = DDMFormFactory.create(DDLRecordSetSettings.class);

		String settingsDDMFormValuesPath = recordSetElement.attributeValue(
			"settings-ddm-form-values-path");

		String serializedSettingsDDMFormValues =
			portletDataContext.getZipEntryAsString(settingsDDMFormValuesPath);

		return deserialize(serializedSettingsDDMFormValues, ddmForm);
	}

	@Override
	protected StagedModelRepository<DDLRecordSet> getStagedModelRepository() {
		return _stagedModelRepository;
	}

	@Reference(unbind = "-")
	protected void setDDLRecordSetLocalService(
		DDLRecordSetLocalService ddlRecordSetLocalService) {

		_ddlRecordSetLocalService = ddlRecordSetLocalService;
	}

	@Reference(
		target = "(model.class.name=com.liferay.dynamic.data.lists.model.DDLRecordSet)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<DDLRecordSet> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference(target = "(ddm.form.values.deserializer.type=json)")
	private DDMFormValuesDeserializer _jsonDDMFormValuesDeserializer;

	private StagedModelRepository<DDLRecordSet> _stagedModelRepository;

}