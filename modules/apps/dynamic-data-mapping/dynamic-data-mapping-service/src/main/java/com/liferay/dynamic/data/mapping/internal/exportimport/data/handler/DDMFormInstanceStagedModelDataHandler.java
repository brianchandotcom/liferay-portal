/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.exportimport.data.handler;

import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesDeserializeUtil;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(service = StagedModelDataHandler.class)
public class DDMFormInstanceStagedModelDataHandler
	extends BaseStagedModelDataHandler<DDMFormInstance> {

	public static final String[] CLASS_NAMES = {
		DDMFormInstance.class.getName()
	};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(DDMFormInstance ddmFormInstance) {
		return ddmFormInstance.getNameCurrentValue();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			DDMFormInstance ddmFormInstance)
		throws Exception {

		DDMStructure ddmStructure = ddmFormInstance.getStructure();

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, ddmFormInstance, ddmStructure,
			PortletDataContext.REFERENCE_TYPE_STRONG);

		for (DDMTemplate ddmTemplate : ddmStructure.getTemplates()) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, ddmFormInstance, ddmTemplate,
				PortletDataContext.REFERENCE_TYPE_STRONG);
		}

		Element element = portletDataContext.getExportDataElement(
			ddmFormInstance);

		String settingsDDMFormValuesPath = ExportImportPathUtil.getModelPath(
			ddmFormInstance, "settings-ddm-form-values.json");

		element.addAttribute(
			"settings-ddm-form-values-path", settingsDDMFormValuesPath);

		if (StringUtil.equals(ddmStructure.getStorageType(), "object")) {
			portletDataContext.addZipEntry(
				settingsDDMFormValuesPath,
				_addObjectDefinitionExternalReferenceCode(
					_jsonFactory.createJSONObject(
						ddmFormInstance.getSettings())));
		}
		else {
			portletDataContext.addZipEntry(
				settingsDDMFormValuesPath, ddmFormInstance.getSettings());
		}

		portletDataContext.addClassedModel(
			element, ExportImportPathUtil.getModelPath(ddmFormInstance),
			ddmFormInstance);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long ddmFormInstanceId)
		throws Exception {

		DDMFormInstance existingDDMFormInstance = fetchMissingReference(
			uuid, groupId);

		if (existingDDMFormInstance == null) {
			return;
		}

		Map<Long, Long> ddmFormInstanceIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMFormInstance.class);

		ddmFormInstanceIds.put(
			ddmFormInstanceId, existingDDMFormInstance.getFormInstanceId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			DDMFormInstance ddmFormInstance)
		throws Exception {

		DDMFormInstance importedDDMFormInstance =
			(DDMFormInstance)ddmFormInstance.clone();

		importedDDMFormInstance.setGroupId(
			portletDataContext.getScopeGroupId());
		importedDDMFormInstance.setStructureId(
			MapUtil.getLong(
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					DDMStructure.class),
				ddmFormInstance.getStructureId(),
				ddmFormInstance.getStructureId()));

		DDMFormInstance existingDDMFormInstance =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				ddmFormInstance.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingDDMFormInstance == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedDDMFormInstance = _stagedModelRepository.addStagedModel(
				portletDataContext, importedDDMFormInstance);
		}
		else {
			importedDDMFormInstance.setMvccVersion(
				existingDDMFormInstance.getMvccVersion());
			importedDDMFormInstance.setFormInstanceId(
				existingDDMFormInstance.getFormInstanceId());

			importedDDMFormInstance = _stagedModelRepository.updateStagedModel(
				portletDataContext, importedDDMFormInstance);
		}

		Element element = portletDataContext.getImportDataElement(
			ddmFormInstance);

		String serializedSettingsDDMFormValues =
			portletDataContext.getZipEntryAsString(
				element.attributeValue("settings-ddm-form-values-path"));

		if (StringUtil.equals(
				importedDDMFormInstance.getStorageType(), "object")) {

			serializedSettingsDDMFormValues = _addObjectDefinitionId(
				_jsonFactory.createJSONObject(serializedSettingsDDMFormValues));
		}

		_ddmFormInstanceLocalService.updateFormInstance(
			importedDDMFormInstance.getFormInstanceId(),
			importedDDMFormInstance.getStructureId(),
			importedDDMFormInstance.getNameMap(),
			importedDDMFormInstance.getDescriptionMap(),
			DDMFormValuesDeserializeUtil.deserialize(
				serializedSettingsDDMFormValues,
				DDMFormFactory.create(DDMFormInstanceSettings.class),
				_jsonDDMFormValuesDeserializer),
			portletDataContext.createServiceContext(importedDDMFormInstance));

		portletDataContext.importClassedModel(
			ddmFormInstance, importedDDMFormInstance);
	}

	@Override
	protected StagedModelRepository<DDMFormInstance>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	private String _addObjectDefinitionExternalReferenceCode(
			JSONObject jsonObject)
		throws Exception {

		JSONObject fieldValueJSONObject = _getFieldValueJSONObject(
			jsonObject.getJSONArray("fieldValues"), "objectDefinitionId");

		if (fieldValueJSONObject == null) {
			return jsonObject.toString();
		}

		JSONArray valueJSONArray = _jsonFactory.createJSONArray(
			fieldValueJSONObject.getString("value"));

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				valueJSONArray.getLong(0));

		if (objectDefinition == null) {
			return jsonObject.toString();
		}

		return jsonObject.put(
			"objectDefinitionExternalReferenceCode",
			objectDefinition.getExternalReferenceCode()
		).toString();
	}

	private String _addObjectDefinitionId(JSONObject jsonObject) {
		String objectDefinitionExternalReferenceCode = jsonObject.getString(
			"objectDefinitionExternalReferenceCode");

		jsonObject.remove("objectDefinitionExternalReferenceCode");

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					objectDefinitionExternalReferenceCode,
					CompanyThreadLocal.getCompanyId());

		if (objectDefinition == null) {
			return jsonObject.toString();
		}

		JSONObject fieldValueJSONObject = _getFieldValueJSONObject(
			jsonObject.getJSONArray("fieldValues"), "objectDefinitionId");

		if (fieldValueJSONObject == null) {
			return jsonObject.toString();
		}

		fieldValueJSONObject.put(
			"value",
			JSONUtil.put(
				String.valueOf(objectDefinition.getObjectDefinitionId())));

		return jsonObject.toString();
	}

	private JSONObject _getFieldValueJSONObject(
		JSONArray fieldValuesJSONArray, String name) {

		for (int i = 0; i < fieldValuesJSONArray.length(); i++) {
			JSONObject fieldValueJSONObject =
				fieldValuesJSONArray.getJSONObject(i);

			if (StringUtil.equals(
					fieldValueJSONObject.getString("name"), name)) {

				return fieldValueJSONObject;
			}
		}

		return null;
	}

	@Reference
	private DDMFormInstanceLocalService _ddmFormInstanceLocalService;

	@Reference(target = "(ddm.form.values.deserializer.type=json)")
	private DDMFormValuesDeserializer _jsonDDMFormValuesDeserializer;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.dynamic.data.mapping.model.DDMFormInstance)"
	)
	private StagedModelRepository<DDMFormInstance> _stagedModelRepository;

}