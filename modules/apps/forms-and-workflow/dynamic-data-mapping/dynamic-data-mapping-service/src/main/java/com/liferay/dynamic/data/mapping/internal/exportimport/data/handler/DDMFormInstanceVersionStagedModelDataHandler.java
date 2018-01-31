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

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceVersionLocalService;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 * @author Rafael Praxedes
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class DDMFormInstanceVersionStagedModelDataHandler
	extends BaseStagedModelDataHandler<DDMFormInstanceVersion> {

	public static final String[] CLASS_NAMES =
		{DDMFormInstanceVersion.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			DDMFormInstanceVersion formInstanceVersion)
		throws Exception {

		DDMFormInstance formInstance = formInstanceVersion.getFormInstance();

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, formInstanceVersion, formInstance,
			PortletDataContext.REFERENCE_TYPE_PARENT);

		DDMStructureVersion ddmStructureVersion =
			formInstanceVersion.getStructureVersion();

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, formInstanceVersion, ddmStructureVersion,
			PortletDataContext.REFERENCE_TYPE_STRONG);

		Element formInstanceVersionElement =
			portletDataContext.getExportDataElement(formInstanceVersion);

		portletDataContext.addClassedModel(
			formInstanceVersionElement,
			ExportImportPathUtil.getModelPath(formInstanceVersion),
			formInstanceVersion);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long formInstanceVersionId)
		throws Exception {

		DDMFormInstanceVersion existingFormInstanceVersion =
			fetchMissingReference(uuid, groupId);

		Map<Long, Long> formInstanceVersionIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMFormInstanceVersion.class);

		formInstanceVersionIds.put(
			formInstanceVersionId,
			existingFormInstanceVersion.getFormInstanceVersionId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			DDMFormInstanceVersion formInstanceVersion)
		throws Exception {

		DDMFormInstanceVersion importedFormInstanceVersion =
			(DDMFormInstanceVersion)formInstanceVersion.clone();

		importedFormInstanceVersion.setGroupId(
			portletDataContext.getScopeGroupId());

		Map<Long, Long> ddmStructureVersionIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructureVersion.class);

		long ddmStructureVersionId = MapUtil.getLong(
			ddmStructureVersionIds, formInstanceVersion.getStructureVersionId(),
			formInstanceVersion.getStructureVersionId());

		importedFormInstanceVersion.setStructureVersionId(
			ddmStructureVersionId);

		DDMFormInstanceVersion existingFormInstanceVersion =
			_ddmFormInstanceVersionLocalService.
				fetchDDMFormInstanceVersionByUuidAndGroupId(
					formInstanceVersion.getUuid(),
					portletDataContext.getScopeGroupId());

		if (existingFormInstanceVersion == null) {
			long userId = portletDataContext.getUserId(
				formInstanceVersion.getUserUuid());

			ServiceContext serviceContext =
				portletDataContext.createServiceContext(formInstanceVersion);

			serviceContext.setUuid(formInstanceVersion.getUuid());

			Map<Long, Long> ddmFormInstanceIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					DDMFormInstance.class);

			long ddmFormInstanceId = MapUtil.getLong(
				ddmFormInstanceIds, formInstanceVersion.getFormInstanceId(),
				formInstanceVersion.getFormInstanceId());

			DDMFormInstance formInstance =
				_ddmFormInstanceLocalService.getDDMFormInstance(
					ddmFormInstanceId);

			importedFormInstanceVersion =
				_ddmFormInstanceVersionLocalService.addFormInstanceVersion(
					ddmStructureVersionId, userId, formInstance,
					formInstanceVersion.getVersion(), serviceContext);
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Not importing DDM form instance version with uuid " +
						existingFormInstanceVersion.getUuid() +
							" since it already exists");
			}

			importedFormInstanceVersion = existingFormInstanceVersion;
		}

		portletDataContext.importClassedModel(
			formInstanceVersion, importedFormInstanceVersion);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceVersionStagedModelDataHandler.class);

	@Reference
	private DDMFormInstanceLocalService _ddmFormInstanceLocalService;

	@Reference
	private DDMFormInstanceVersionLocalService
		_ddmFormInstanceVersionLocalService;

}