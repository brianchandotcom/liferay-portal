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
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutJSONDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 * @author Rafael Praxedes
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + DDMPortletKeys.DYNAMIC_DATA_MAPPING},
	service = StagedModelDataHandler.class
)
public class DDMStructureVersionStagedModelDataHandler
	extends BaseStagedModelDataHandler<DDMStructureVersion> {

	public static final String[] CLASS_NAMES =
		{DDMStructureVersion.class.getName()};

	@Override
	public void deleteStagedModel(DDMStructureVersion structureVersion)
		throws PortalException {

		_ddmStructureVersionLocalService.deleteDDMStructureVersion(
			structureVersion);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		DDMStructureVersion ddmStructureVersion =
			fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (ddmStructureVersion != null) {
			deleteStagedModel(ddmStructureVersion);
		}
	}

	@Override
	public DDMStructureVersion fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _ddmStructureVersionLocalService.
			fetchDDMStructureVersionByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<DDMStructureVersion> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _ddmStructureVersionLocalService.
			getDDMStructureVersionsByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<DDMStructureVersion>());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			DDMStructureVersion structureVersion)
		throws Exception {

		DDMStructure structure = structureVersion.getStructure();

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, structureVersion, structure,
			PortletDataContext.REFERENCE_TYPE_PARENT);

		Element structureVersionElement =
			portletDataContext.getExportDataElement(structureVersion);

		exportDDMFormLayout(
			portletDataContext, structureVersion, structureVersionElement);

		portletDataContext.addClassedModel(
			structureVersionElement,
			ExportImportPathUtil.getModelPath(structureVersion),
			structureVersion);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long structureVersionId)
		throws PortletDataException {

		DDMStructureVersion existingStructureVersion = fetchMissingReference(
			uuid, groupId);

		Map<Long, Long> structureVersionIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructureVersion.class);

		structureVersionIds.put(
			structureVersionId, existingStructureVersion.getStructureId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			DDMStructureVersion structureVersion)
		throws Exception {

		long userId = portletDataContext.getUserId(
			structureVersion.getUserUuid());

		Map<Long, Long> structureIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructure.class);

		long parentStructureVersionId = MapUtil.getLong(
			structureIds, structureVersion.getParentStructureId(),
			structureVersion.getParentStructureId());

		long sructureId = MapUtil.getLong(
			structureIds, structureVersion.getStructureId(),
			structureVersion.getStructureId());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			structureVersion);

		DDMStructureVersion importedStructureVersion = null;

		Map<Long, Long> groupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Group.class);

		long targetGroupId = portletDataContext.getScopeGroupId();

		if (groupIds.containsKey(structureVersion.getGroupId())) {
			targetGroupId = groupIds.get(structureVersion.getGroupId());
		}

		DDMStructureVersion existingStructureVersion =
			fetchStagedModelByUuidAndGroupId(
				structureVersion.getUuid(), targetGroupId);

		if (existingStructureVersion == null) {
			serviceContext.setUuid(structureVersion.getUuid());

			Element structureVersionElement =
				portletDataContext.getImportDataElement(structureVersion);

			DDMFormLayout ddmFormLayout = getImportDDMFormLayout(
				portletDataContext, structureVersionElement);

			DDMStructure ddmStructure =
				_ddmStructureLocalService.getDDMStructure(sructureId);

			importedStructureVersion =
				_ddmStructureVersionLocalService.addStructureVersion(
					userId, parentStructureVersionId,
					structureVersion.getNameMap(),
					structureVersion.getDescriptionMap(),
					structureVersion.getDefinition(),
					structureVersion.getVersion(), ddmStructure, ddmFormLayout,
					serviceContext);
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Not importing DDM structure version with uuid " +
						structureVersion.getUuid() +
							" since it already exists");
			}

			importedStructureVersion = existingStructureVersion;
		}

		portletDataContext.importClassedModel(
			structureVersion, importedStructureVersion);
	}

	protected void exportDDMFormLayout(
			PortletDataContext portletDataContext,
			DDMStructureVersion structureVersion,
			Element structureVersionElement)
		throws PortalException {

		DDMStructureLayout structureLayout =
			_ddmStructureLayoutLocalService.
				getStructureLayoutByStructureVersionId(
					structureVersion.getStructureVersionId());

		String ddmFormLayoutPath = ExportImportPathUtil.getModelPath(
			structureVersion, "ddm-form-layout.json");

		structureVersionElement.addAttribute(
			"ddm-form-layout-path", ddmFormLayoutPath);

		portletDataContext.addZipEntry(
			ddmFormLayoutPath, structureLayout.getDefinition());
	}

	protected DDMFormLayout getImportDDMFormLayout(
			PortletDataContext portletDataContext,
			Element structureVersionElement)
		throws PortalException {

		String ddmFormLayoutPath = structureVersionElement.attributeValue(
			"ddm-form-layout-path");

		String serializedDDMFormLayout = portletDataContext.getZipEntryAsString(
			ddmFormLayoutPath);

		return _ddmFormLayoutJSONDeserializer.deserialize(
			serializedDDMFormLayout);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMStructureVersionStagedModelDataHandler.class);

	@Reference
	private DDMFormLayoutJSONDeserializer _ddmFormLayoutJSONDeserializer;

	@Reference
	private DDMStructureLayoutLocalService _ddmStructureLayoutLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;

}