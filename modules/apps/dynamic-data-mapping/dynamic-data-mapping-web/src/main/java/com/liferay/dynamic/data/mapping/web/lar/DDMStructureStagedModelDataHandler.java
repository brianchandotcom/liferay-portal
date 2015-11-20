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

package com.liferay.dynamic.data.mapping.web.lar;

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.io.DDMFormJSONDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutJSONDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataException;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.portlet.exportimport.lar.StagedModelModifiedDateComparator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 * @author Daniel Kocsis
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + DDMPortletKeys.DYNAMIC_DATA_MAPPING},
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

		boolean preloaded = false;

		if (defaultUserId == structure.getUserId()) {
			preloaded = true;
		}

		referenceAttributes.put("preloaded", String.valueOf(preloaded));

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

		long classNameId = PortalUtil.getClassNameId(
			referenceElement.attributeValue("referenced-class-name"));
		String structureKey = referenceElement.attributeValue("structure-key");
		boolean preloaded = GetterUtil.getBoolean(
			referenceElement.attributeValue("preloaded"));

		DDMStructure existingStructure = fetchExistingStructure(
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

		if (defaultUserId == structure.getUserId()) {
			structureElement.addAttribute("preloaded", "true");
		}

		exportDDMForm(portletDataContext, structure, structureElement);

		exportDDMFormLayout(portletDataContext, structure, structureElement);

		portletDataContext.addClassedModel(
			structureElement, ExportImportPathUtil.getModelPath(structure),
			structure);
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

		long classNameId = PortalUtil.getClassNameId(
			referenceElement.attributeValue("referenced-class-name"));
		String structureKey = referenceElement.attributeValue("structure-key");
		boolean preloaded = GetterUtil.getBoolean(
			referenceElement.attributeValue("preloaded"));

		DDMStructure existingStructure = null;

		existingStructure = fetchExistingStructure(
			uuid, groupId, classNameId, structureKey, preloaded);

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

		DDMFormLayout ddmFormLayout = getImportDDMFormLayout(
			portletDataContext, structureElement);

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			structure);

		DDMStructure importedStructure = null;

		if (portletDataContext.isDataStrategyMirror()) {
			Element element =
				portletDataContext.getImportDataStagedModelElement(structure);

			boolean preloaded = GetterUtil.getBoolean(
				element.attributeValue("preloaded"));

			DDMStructure existingStructure = fetchExistingStructure(
				structure.getUuid(), portletDataContext.getScopeGroupId(),
				structure.getClassNameId(), structure.getStructureKey(),
				preloaded);

			if (existingStructure == null) {
				serviceContext.setUuid(structure.getUuid());

				importedStructure = _ddmStructureLocalService.addStructure(
					userId, portletDataContext.getScopeGroupId(),
					parentStructureId, structure.getClassNameId(),
					structure.getStructureKey(), structure.getNameMap(),
					structure.getDescriptionMap(), ddmForm, ddmFormLayout,
					structure.getStorageType(), structure.getType(),
					serviceContext);
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
				userId, portletDataContext.getScopeGroupId(), parentStructureId,
				structure.getClassNameId(), null, structure.getNameMap(),
				structure.getDescriptionMap(), ddmForm, ddmFormLayout,
				structure.getStorageType(), structure.getType(),
				serviceContext);
		}

		portletDataContext.importClassedModel(structure, importedStructure);

		structureKeys.put(
			structure.getStructureKey(), importedStructure.getStructureKey());
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

	protected DDMForm getImportDDMForm(
			PortletDataContext portletDataContext, Element structureElement)
		throws PortalException {

		String ddmFormPath = structureElement.attributeValue("ddm-form-path");

		String serializedDDMForm = portletDataContext.getZipEntryAsString(
			ddmFormPath);

		return _ddmFormJSONDeserializer.deserialize(serializedDDMForm);
	}

	protected DDMFormLayout getImportDDMFormLayout(
			PortletDataContext portletDataContext, Element structureElement)
		throws PortalException {

		String ddmFormLayoutPath = structureElement.attributeValue(
			"ddm-form-layout-path");

		String serializedDDMFormLayout = portletDataContext.getZipEntryAsString(
			ddmFormLayoutPath);

		return _ddmFormLayoutJSONDeserializer.deserialize(
			serializedDDMFormLayout);
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

		if (!Validator.equals(
				existingStructure.getDefinition(), structure.getDefinition())) {

			return true;
		}

		if (!Validator.equals(
				existingStructure.getDescriptionMap(),
				structure.getDescriptionMap())) {

			return true;
		}

		if (!Validator.equals(
				existingStructure.getNameMap(), structure.getNameMap())) {

			return true;
		}

		if (!Validator.equals(
				existingStructure.getStorageType(),
				structure.getStorageType())) {

			return true;
		}

		if (!Validator.equals(
				existingStructure.getType(), structure.getType())) {

			return true;
		}

		return false;
	}

	@Reference(unbind = "-")
	protected void setDDMFormJSONDeserializer(
		DDMFormJSONDeserializer ddmFormJSONDeserializer) {

		_ddmFormJSONDeserializer = ddmFormJSONDeserializer;
	}

	@Reference(unbind = "-")
	protected void setDDMFormLayoutJSONDeserializer(
		DDMFormLayoutJSONDeserializer ddmFormLayoutJSONDeserializer) {

		_ddmFormLayoutJSONDeserializer = ddmFormLayoutJSONDeserializer;
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
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMStructureStagedModelDataHandler.class);

	private volatile DDMFormJSONDeserializer _ddmFormJSONDeserializer;
	private volatile DDMFormLayoutJSONDeserializer _ddmFormLayoutJSONDeserializer;
	private volatile DDMStructureLayoutLocalService _ddmStructureLayoutLocalService;
	private volatile DDMStructureLocalService _ddmStructureLocalService;
	private volatile UserLocalService _userLocalService;

}