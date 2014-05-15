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

package com.liferay.portlet.dynamicdatamapping.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mate Thurzo
 * @author Daniel Kocsis
 */
public class DDMStructureStagedModelDataHandler
	extends BaseStagedModelDataHandler<DDMStructure> {

	public static final String[] CLASS_NAMES = {DDMStructure.class.getName()};

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException, SystemException {

		DDMStructure ddmStructure =
			DDMStructureLocalServiceUtil.fetchDDMStructureByUuidAndGroupId(
				uuid, groupId);

		if (ddmStructure != null) {
			DDMStructureLocalServiceUtil.deleteStructure(ddmStructure);
		}
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

		Map<String, String> referenceAttributes = new HashMap<String, String>();

		referenceAttributes.put(
			"referenced-class-name", structure.getClassName());
		referenceAttributes.put("structure-key", structure.getStructureKey());

		long defaultUserId = 0;

		try {
			defaultUserId = UserLocalServiceUtil.getDefaultUserId(
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
	public void importMissingReference(
			PortletDataContext portletDataContext, Element referenceElement)
		throws PortletDataException {

		importMissingGroupReference(portletDataContext, referenceElement);

		String uuid = referenceElement.attributeValue("uuid");

		Map<Long, Long> groupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Group.class);

		long liveGroupId = GetterUtil.getLong(
			referenceElement.attributeValue("live-group-id"));

		liveGroupId = MapUtil.getLong(groupIds, liveGroupId, liveGroupId);

		long classNameId = PortalUtil.getClassNameId(
			referenceElement.attributeValue("referenced-class-name"));
		String structureKey = referenceElement.attributeValue("structure-key");
		boolean preloaded = GetterUtil.getBoolean(
			referenceElement.attributeValue("preloaded"));

		DDMStructure existingStructure = null;

		try {
			existingStructure = fetchExistingStructure(
				uuid, liveGroupId, classNameId, structureKey, preloaded);
		}
		catch (SystemException se) {
			throw new PortletDataException(se);
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
	}

	@Override
	public boolean validateReference(
		PortletDataContext portletDataContext, Element referenceElement) {

		if (!validateMissingGroupReference(
				portletDataContext, referenceElement)) {

			return false;
		}

		String uuid = referenceElement.attributeValue("uuid");

		Map<Long, Long> groupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Group.class);

		long liveGroupId = GetterUtil.getLong(
			referenceElement.attributeValue("live-group-id"));

		liveGroupId = MapUtil.getLong(groupIds, liveGroupId, liveGroupId);

		long classNameId = PortalUtil.getClassNameId(
			referenceElement.attributeValue("referenced-class-name"));
		String structureKey = referenceElement.attributeValue("structure-key");
		boolean preloaded = GetterUtil.getBoolean(
			referenceElement.attributeValue("preloaded"));

		try {
			DDMStructure existingStructure = fetchExistingStructure(
				uuid, liveGroupId, classNameId, structureKey, preloaded);

			if (existingStructure == null) {
				return false;
			}

			return true;
		}
		catch (SystemException se) {
			return false;
		}
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
				DDMStructureLocalServiceUtil.getStructure(
					structure.getParentStructureId());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, structure, parentStructure,
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(
			structure.getCompanyId());

		if (defaultUserId == structure.getUserId()) {
			structureElement.addAttribute("preloaded", "true");
		}

		portletDataContext.addClassedModel(
			structureElement, ExportImportPathUtil.getModelPath(structure),
			structure);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, DDMStructure structure)
		throws Exception {

		long userId = portletDataContext.getUserId(structure.getUserUuid());

		if (structure.getParentStructureId() !=
				DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID) {

			StagedModelDataHandlerUtil.importReferenceStagedModel(
				portletDataContext, structure, DDMStructure.class,
				structure.getParentStructureId());
		}

		Map<Long, Long> structureIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructure.class);

		long parentStructureId = MapUtil.getLong(
			structureIds, structure.getParentStructureId(),
			structure.getParentStructureId());

		Map<String, String> structureKeys =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructure.class + ".ddmStructureKey");

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

				importedStructure = DDMStructureLocalServiceUtil.addStructure(
					userId, portletDataContext.getScopeGroupId(),
					parentStructureId, structure.getClassNameId(),
					structure.getStructureKey(), structure.getNameMap(),
					structure.getDescriptionMap(), structure.getXsd(),
					structure.getStorageType(), structure.getType(),
					serviceContext);
			}
			else {
				importedStructure =
					DDMStructureLocalServiceUtil.updateStructure(
						existingStructure.getStructureId(), parentStructureId,
						structure.getNameMap(), structure.getDescriptionMap(),
						structure.getXsd(), serviceContext);
			}
		}
		else {
			importedStructure = DDMStructureLocalServiceUtil.addStructure(
				userId, portletDataContext.getScopeGroupId(), parentStructureId,
				structure.getClassNameId(), structure.getStructureKey(),
				structure.getNameMap(), structure.getDescriptionMap(),
				structure.getXsd(), structure.getStorageType(),
				structure.getType(), serviceContext);
		}

		portletDataContext.importClassedModel(structure, importedStructure);

		structureKeys.put(
			structure.getStructureKey(), importedStructure.getStructureKey());
	}

	protected DDMStructure fetchExistingStructure(
			String uuid, long groupId, long classNameId, String structureKey,
			boolean preloaded)
		throws SystemException {

		DDMStructure existingStructure = null;

		if (!preloaded) {
			existingStructure =
				DDMStructureLocalServiceUtil.fetchDDMStructureByUuidAndGroupId(
					uuid, groupId);
		}
		else {
			existingStructure = DDMStructureLocalServiceUtil.fetchStructure(
				groupId, classNameId, structureKey);
		}

		return existingStructure;
	}

}