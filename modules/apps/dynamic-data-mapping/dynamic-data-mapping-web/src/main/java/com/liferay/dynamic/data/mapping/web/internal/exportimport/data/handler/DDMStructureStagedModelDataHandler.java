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

package com.liferay.dynamic.data.mapping.web.internal.exportimport.data.handler;

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.security.permission.DDMPermissionSupport;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 * @author Daniel Kocsis
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + DDMPortletKeys.DYNAMIC_DATA_MAPPING,
	service = StagedModelDataHandler.class
)
public class DDMStructureStagedModelDataHandler
	extends BaseStagedModelDataHandler<DDMStructure> {

	public static final String[] CLASS_NAMES = {DDMStructure.class.getName()};

	@Override
	public void deleteStagedModel(DDMStructure structure)
		throws PortalException {

		_ddmStructureLocalService.deleteStructure(structure);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		DDMStructure ddmStructure = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (ddmStructure != null) {
			deleteStagedModel(ddmStructure);
		}
	}

	@Override
	public DDMStructure fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _ddmStructureLocalService.fetchDDMStructureByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<DDMStructure> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _ddmStructureLocalService.getDDMStructuresByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<DDMStructure>());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(DDMStructure structure) {
		return structure.getNameCurrentValue();
	}

	@Override
	public Map<String, String> getReferenceAttributes(
		PortletDataContext portletDataContext, DDMStructure structure) {

		Map<String, String> referenceAttributes = new HashMap<>();

		referenceAttributes.put(
			"referenced-class-name", structure.getClassName());
		referenceAttributes.put("structure-key", structure.getStructureKey());

		long defaultUserId = 0;

		try {
			defaultUserId = _userLocalService.getDefaultUserId(
				structure.getCompanyId());
		}
		catch (Exception e) {
			return referenceAttributes;
		}

		referenceAttributes.put(
			"preloaded",
			String.valueOf(isPreloadedStructure(defaultUserId, structure)));

		return referenceAttributes;
	}

	@Override
	public boolean validateReference(
		PortletDataContext portletDataContext, Element referenceElement) {

		validateMissingGroupReference(portletDataContext, referenceElement);

		String uuid = referenceElement.attributeValue("uuid");

		Map<Long, Long> groupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Group.class);

		long groupId = GetterUtil.getLong(
			referenceElement.attributeValue("group-id"));

		groupId = MapUtil.getLong(groupIds, groupId);

		boolean preloaded = GetterUtil.getBoolean(
			referenceElement.attributeValue("preloaded"));

		if (!preloaded) {
			return super.validateMissingReference(uuid, groupId);
		}

		long classNameId = _portal.getClassNameId(
			referenceElement.attributeValue("referenced-class-name"));
		String structureKey = referenceElement.attributeValue("structure-key");

		DDMStructure existingStructure = fetchExistingStructureWithParentGroups(
			uuid, groupId, classNameId, structureKey, preloaded);

		if (existingStructure == null) {
			return false;
		}

		return true;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, DDMStructure structure)
		throws Exception {

		Element structureElement = portletDataContext.getExportDataElement(
			structure);

		if (structure.getParentStructureId() !=
				DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID) {

			DDMStructure parentStructure =
				_ddmStructureLocalService.getStructure(
					structure.getParentStructureId());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, structure, parentStructure,
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		long defaultUserId = _userLocalService.getDefaultUserId(
			structure.getCompanyId());

		if (isPreloadedStructure(defaultUserId, structure)) {
			structureElement.addAttribute("preloaded", "true");
		}

		exportDDMForm(portletDataContext, structure, structureElement);

		exportDDMDataProviderInstances(
			portletDataContext, structure, structureElement);

		exportDDMFormLayout(portletDataContext, structure, structureElement);

		portletDataContext.addClassedModel(
			structureElement, ExportImportPathUtil.getModelPath(structure),
			structure);

		portletDataContext.addPermissions(
			getResourceName(structure), structure.getPrimaryKey());
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, Element referenceElement)
		throws PortletDataException {

		importMissingGroupReference(portletDataContext, referenceElement);

		String uuid = referenceElement.attributeValue("uuid");

		Map<Long, Long> groupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Group.class);

		long groupId = GetterUtil.getLong(
			referenceElement.attributeValue("group-id"));

		groupId = MapUtil.getLong(groupIds, groupId);

		long classNameId = _portal.getClassNameId(
			referenceElement.attributeValue("referenced-class-name"));
		String structureKey = referenceElement.attributeValue("structure-key");
		boolean preloaded = GetterUtil.getBoolean(
			referenceElement.attributeValue("preloaded"));

		DDMStructure existingStructure;

		if (!preloaded) {
			existingStructure = fetchMissingReference(uuid, groupId);
		}
		else {
			existingStructure = fetchExistingStructureWithParentGroups(
				uuid, groupId, classNameId, structureKey, preloaded);
		}

		if (existingStructure == null) {
			return;
		}

		Map<Long, Long> structureIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructure.class);

		long structureId = GetterUtil.getLong(
			referenceElement.attributeValue("class-pk"));

		structureIds.put(structureId, existingStructure.getStructureId());

		Map<String, String> structureKeys =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructure.class + ".ddmStructureKey");

		structureKeys.put(structureKey, existingStructure.getStructureKey());

		Map<String, String> structureUuids =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructure.class + ".ddmStructureUuid");

		structureUuids.put(uuid, existingStructure.getUuid());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, DDMStructure structure)
		throws Exception {

		long userId = portletDataContext.getUserId(structure.getUserUuid());

		Map<Long, Long> structureIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructure.class);

		long parentStructureId = MapUtil.getLong(
			structureIds, structure.getParentStructureId(),
			structure.getParentStructureId());

		Map<String, String> structureKeys =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructure.class + ".ddmStructureKey");

		Element structureElement = portletDataContext.getImportDataElement(
			structure);

		DDMForm ddmForm = getImportDDMForm(
			portletDataContext, structureElement);

		importDDMDataProviderInstances(
			portletDataContext, structureElement, ddmForm);

		DDMFormLayout ddmFormLayout = getImportDDMFormLayout(
			portletDataContext, structureElement);

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			structure);

		DDMStructure importedStructure = null;

		long groupId = portletDataContext.getScopeGroupId();

		if (structure.getGroupId() ==
				portletDataContext.getSourceCompanyGroupId()) {

			Map<Long, Long> groupIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					Group.class);

			Long companyGroupId = groupIds.get(
				portletDataContext.getCompanyGroupId());

			if ((companyGroupId == null) || (groupId != companyGroupId)) {
				groupId = portletDataContext.getCompanyGroupId();
			}
		}

		if (portletDataContext.isDataStrategyMirror()) {
			Element element =
				portletDataContext.getImportDataStagedModelElement(structure);

			boolean preloaded = GetterUtil.getBoolean(
				element.attributeValue("preloaded"));

			DDMStructure existingStructure = fetchExistingStructure(
				structure.getUuid(), groupId, structure.getClassNameId(),
				structure.getStructureKey(), preloaded);

			if (existingStructure == null) {
				serviceContext.setUuid(structure.getUuid());

				existingStructure = _ddmStructureLocalService.fetchStructure(
					groupId, structure.getClassNameId(),
					structure.getStructureKey());

				String structureKey = null;

				if (existingStructure == null) {
					structureKey = structure.getStructureKey();
				}

				importedStructure = _ddmStructureLocalService.addStructure(
					userId, groupId, parentStructureId,
					structure.getClassNameId(), structureKey,
					structure.getNameMap(), structure.getDescriptionMap(),
					ddmForm, ddmFormLayout, structure.getStorageType(),
					structure.getType(), serviceContext);
			}
			else if (isModifiedStructure(existingStructure, structure)) {
				importedStructure = _ddmStructureLocalService.updateStructure(
					userId, existingStructure.getStructureId(),
					parentStructureId, structure.getNameMap(),
					structure.getDescriptionMap(), ddmForm, ddmFormLayout,
					serviceContext);
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Not importing DDM structure with key " +
							structure.getStructureKey() +
								" since it was not modified");
				}

				importedStructure = existingStructure;
			}
		}
		else {
			importedStructure = _ddmStructureLocalService.addStructure(
				userId, groupId, parentStructureId, structure.getClassNameId(),
				null, structure.getNameMap(), structure.getDescriptionMap(),
				ddmForm, ddmFormLayout, structure.getStorageType(),
				structure.getType(), serviceContext);
		}

		portletDataContext.importClassedModel(structure, importedStructure);

		portletDataContext.importPermissions(
			getResourceName(structure), structure.getPrimaryKey(),
			importedStructure.getPrimaryKey());

		structureKeys.put(
			structure.getStructureKey(), importedStructure.getStructureKey());
	}

	protected void exportDDMDataProviderInstances(
			PortletDataContext portletDataContext, DDMStructure structure,
			Element structureElement)
		throws PortalException {

		Set<Long> ddmDataProviderInstanceIdsSet = new HashSet<>();

		List<DDMDataProviderInstanceLink> ddmDataProviderInstanceLinks =
			_ddmDataProviderInstanceLinkLocalService.
				getDataProviderInstanceLinks(structure.getStructureId());

		for (DDMDataProviderInstanceLink ddmDataProviderInstanceLink :
				ddmDataProviderInstanceLinks) {

			long ddmDataProviderInstanceId =
				ddmDataProviderInstanceLink.getDataProviderInstanceId();

			DDMDataProviderInstance ddmDataProviderInstance =
				_ddmDataProviderInstanceLocalService.getDDMDataProviderInstance(
					ddmDataProviderInstanceId);

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, structure, ddmDataProviderInstance,
				PortletDataContext.REFERENCE_TYPE_STRONG);

			ddmDataProviderInstanceIdsSet.add(
				ddmDataProviderInstance.getDataProviderInstanceId());
		}

		String ddmDataProviderInstanceIds = ArrayUtil.toString(
			ddmDataProviderInstanceIdsSet.toArray(new Long[0]),
			StringPool.BLANK);

		structureElement.addAttribute(
			_DDM_DATA_PROVIDER_INSTANCE_IDS, ddmDataProviderInstanceIds);
	}

	protected void exportDDMForm(
		PortletDataContext portletDataContext, DDMStructure structure,
		Element structureElement) {

		String ddmFormPath = ExportImportPathUtil.getModelPath(
			structure, "ddm-form.json");

		structureElement.addAttribute("ddm-form-path", ddmFormPath);

		portletDataContext.addZipEntry(ddmFormPath, structure.getDefinition());
	}

	protected void exportDDMFormLayout(
			PortletDataContext portletDataContext, DDMStructure structure,
			Element structureElement)
		throws PortalException {

		DDMStructureVersion structureVersion = structure.getStructureVersion();

		DDMStructureLayout structureLayout =
			_ddmStructureLayoutLocalService.
				getStructureLayoutByStructureVersionId(
					structureVersion.getStructureVersionId());

		String ddmFormLayoutPath = ExportImportPathUtil.getModelPath(
			structure, "ddm-form-layout.json");

		structureElement.addAttribute(
			"ddm-form-layout-path", ddmFormLayoutPath);

		portletDataContext.addZipEntry(
			ddmFormLayoutPath, structureLayout.getDefinition());
	}

	protected DDMStructure fetchExistingStructure(
		String uuid, long groupId, long classNameId, String structureKey,
		boolean preloaded) {

		DDMStructure existingStructure = null;

		if (!preloaded) {
			existingStructure = fetchStagedModelByUuidAndGroupId(uuid, groupId);
		}
		else {
			existingStructure = _ddmStructureLocalService.fetchStructure(
				groupId, classNameId, structureKey);
		}

		return existingStructure;
	}

	protected DDMStructure fetchExistingStructureWithParentGroups(
		String uuid, long groupId, long classNameId, String structureKey,
		boolean preloaded) {

		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return null;
		}

		long companyId = group.getCompanyId();

		while (group != null) {
			DDMStructure existingStructure = fetchExistingStructure(
				uuid, group.getGroupId(), classNameId, structureKey, preloaded);

			if (existingStructure != null) {
				return existingStructure;
			}

			group = group.getParentGroup();
		}

		Group companyGroup = _groupLocalService.fetchCompanyGroup(companyId);

		if (companyGroup == null) {
			return null;
		}

		return fetchExistingStructure(
			uuid, companyGroup.getGroupId(), classNameId, structureKey,
			preloaded);
	}

	protected DDMForm getImportDDMForm(
		PortletDataContext portletDataContext, Element structureElement) {

		String ddmFormPath = structureElement.attributeValue("ddm-form-path");

		String serializedDDMForm = portletDataContext.getZipEntryAsString(
			ddmFormPath);

		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(
				serializedDDMForm);

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				_jsonDDMFormDeserializer.deserialize(builder.build());

		return ddmFormDeserializerDeserializeResponse.getDDMForm();
	}

	protected DDMFormLayout getImportDDMFormLayout(
		PortletDataContext portletDataContext, Element structureElement) {

		String ddmFormLayoutPath = structureElement.attributeValue(
			"ddm-form-layout-path");

		String serializedDDMFormLayout = portletDataContext.getZipEntryAsString(
			ddmFormLayoutPath);

		DDMFormLayoutDeserializerDeserializeRequest.Builder builder =
			DDMFormLayoutDeserializerDeserializeRequest.Builder.newBuilder(
				serializedDDMFormLayout);

		DDMFormLayoutDeserializerDeserializeResponse
			ddmFormLayoutDeserializerDeserializeResponse =
				_jsonDDMFormLayoutDeserializer.deserialize(builder.build());

		return ddmFormLayoutDeserializerDeserializeResponse.getDDMFormLayout();
	}

	protected long getJSONArrayFirstValue(String value) {
		try {
			JSONArray jsonArray = jsonFactory.createJSONArray(value);

			return jsonArray.getLong(0);
		}
		catch (Exception e) {
			return GetterUtil.getLong(value);
		}
	}

	protected String getResourceName(DDMStructure structure)
		throws PortalException {

		return ddmPermissionSupport.getStructureModelResourceName(
			structure.getClassName());
	}

	protected void importDDMDataProviderInstances(
			PortletDataContext portletDataContext, Element structureElement,
			DDMForm ddmForm)
		throws PortletDataException {

		String[] ddmDataProviderInstanceIds = StringUtil.split(
			structureElement.attributeValue(_DDM_DATA_PROVIDER_INSTANCE_IDS));

		if (ArrayUtil.isEmpty(ddmDataProviderInstanceIds)) {
			return;
		}

		Map<Long, Long> dataProviderInstanceIdsMap =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMDataProviderInstance.class);

		for (String ddmDataProviderInstanceId : ddmDataProviderInstanceIds) {
			long oldDDMDataProviderInstanceId = GetterUtil.getLong(
				ddmDataProviderInstanceId);

			long newDDMDataProviderInstanceId = MapUtil.getLong(
				dataProviderInstanceIdsMap, oldDDMDataProviderInstanceId);

			StagedModelDataHandlerUtil.importReferenceStagedModel(
				portletDataContext, DDMDataProviderInstance.class,
				Long.valueOf(newDDMDataProviderInstanceId));
		}

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		for (DDMFormField ddmFormField : ddmFormFields) {
			String ddmFormFieldType = ddmFormField.getType();

			if (!ddmFormFieldType.equals(DDMFormFieldType.SELECT) &&
				!ddmFormFieldType.equals(DDMFormFieldType.TEXT)) {

				continue;
			}

			long oldDDMDataProviderInstanceId = getJSONArrayFirstValue(
				GetterUtil.getString(
					ddmFormField.getProperty("ddmDataProviderInstanceId")));

			long newDDMDataProviderInstanceId = MapUtil.getLong(
				dataProviderInstanceIdsMap, oldDDMDataProviderInstanceId);

			ddmFormField.setProperty(
				"ddmDataProviderInstanceId", newDDMDataProviderInstanceId);
		}
	}

	protected boolean isModifiedStructure(
		DDMStructure existingStructure, DDMStructure structure) {

		// Check modified date first

		int value = DateUtil.compareTo(
			existingStructure.getModifiedDate(), structure.getModifiedDate());

		if (value < 0) {
			return true;
		}

		// Check other attributes

		if (!Objects.equals(
				existingStructure.getDefinition(), structure.getDefinition())) {

			return true;
		}

		if (!Objects.equals(
				existingStructure.getDescriptionMap(),
				structure.getDescriptionMap())) {

			return true;
		}

		if (!Objects.equals(
				existingStructure.getNameMap(), structure.getNameMap())) {

			return true;
		}

		if (!Objects.equals(
				existingStructure.getStorageType(),
				structure.getStorageType())) {

			return true;
		}

		if (!Objects.equals(existingStructure.getType(), structure.getType())) {
			return true;
		}

		return false;
	}

	protected boolean isPreloadedStructure(
		long defaultUserId, DDMStructure structure) {

		if (defaultUserId == structure.getUserId()) {
			return true;
		}

		DDMStructureVersion ddmStructureVersion = null;

		try {
			ddmStructureVersion =
				_ddmStructureVersionLocalService.getStructureVersion(
					structure.getStructureId(),
					DDMStructureConstants.VERSION_DEFAULT);
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		if ((ddmStructureVersion != null) &&
			(defaultUserId == ddmStructureVersion.getUserId())) {

			return true;
		}

		return false;
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLayoutLocalService(
		DDMStructureLayoutLocalService ddmStructureLayoutLocalService) {

		_ddmStructureLayoutLocalService = ddmStructureLayoutLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDMStructureVersionLocalService(
		DDMStructureVersionLocalService ddmStructureVersionLocalService) {

		_ddmStructureVersionLocalService = ddmStructureVersionLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	@Reference
	protected DDMPermissionSupport ddmPermissionSupport;

	@Reference
	protected JSONFactory jsonFactory;

	private static final String _DDM_DATA_PROVIDER_INSTANCE_IDS =
		"ddm-data-provider-instance-ids";

	private static final Log _log = LogFactoryUtil.getLog(
		DDMStructureStagedModelDataHandler.class);

	@Reference
	private DDMDataProviderInstanceLinkLocalService
		_ddmDataProviderInstanceLinkLocalService;

	@Reference
	private DDMDataProviderInstanceLocalService
		_ddmDataProviderInstanceLocalService;

	private DDMStructureLayoutLocalService _ddmStructureLayoutLocalService;
	private DDMStructureLocalService _ddmStructureLocalService;
	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference(target = "(ddm.form.deserializer.type=json)")
	private DDMFormDeserializer _jsonDDMFormDeserializer;

	@Reference(target = "(ddm.form.layout.deserializer.type=json)")
	private DDMFormLayoutDeserializer _jsonDDMFormLayoutDeserializer;

	@Reference
	private Portal _portal;

	private UserLocalService _userLocalService;

}