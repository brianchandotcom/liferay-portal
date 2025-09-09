/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.report.service.impl.handler;

import com.liferay.exportimport.kernel.exception.ImportStagedModelExceptionHandler;
import com.liferay.exportimport.kernel.lar.ExportImportClassedModelUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.report.internal.util.ExportImportReportEntryUtil;
import com.liferay.exportimport.report.service.ExportImportReportEntryLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ExternalReferenceCodeModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alvaro Saugar
 */
@Component(service = ImportStagedModelExceptionHandler.class)
public class ImportStagedModelExceptionHandlerImpl
	implements ImportStagedModelExceptionHandler {

	@Override
	public <T extends StagedModel> void handle(
		PortletDataException portletDataException,
		PortletDataContext portletDataContext, T stagedModel) {

		String externalReferenceCode = null;

		Class<?> clazz = stagedModel.getModelClass();

		if (stagedModel instanceof ExternalReferenceCodeModel) {
			ExternalReferenceCodeModel externalReferenceCodeModel =
				(ExternalReferenceCodeModel)stagedModel;

			externalReferenceCode =
				externalReferenceCodeModel.getExternalReferenceCode();
		}

		try {
			long groupId = portletDataContext.getGroupId();

			Group group = _groupLocalService.getGroup(groupId);

			_exportImportReportEntryLocalService.
				addErrorExportImportReportEntry(
					groupId, portletDataContext.getCompanyId(),
					externalReferenceCode,
					ExportImportClassedModelUtil.getClassNameId(stagedModel),
					ExportImportClassedModelUtil.getClassPK(stagedModel),
					GetterUtil.getLong(
						portletDataContext.getExportImportProcessId()),
					portletDataException.getMessage(),
					portletDataException.toString(),
					ExportImportReportEntryUtil.getModelName(clazz),
					ExportImportReportEntryUtil.getOrigin(),
					ExportImportReportEntryUtil.getScope(group),
					ExportImportReportEntryUtil.getScopeKey(groupId));
		}
		catch (Exception exception) {
			_log.error(
				"Error adding ErrorExportImportReportEntry with the " +
					"externalReferenceCode: " + externalReferenceCode);
			_log.error(exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ImportStagedModelExceptionHandlerImpl.class);

	@Reference
	private ExportImportReportEntryLocalService
		_exportImportReportEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

}