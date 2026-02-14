/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.batch.engine.action;

import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.action.ExportTaskPostAction;
import com.liferay.batch.engine.model.BatchEngineExportTask;
import com.liferay.changeset.model.ChangesetEntry;
import com.liferay.changeset.service.ChangesetEntryLocalService;
import com.liferay.changeset.util.ChangesetThreadLocal;
import com.liferay.exportimport.internal.lar.ExportImportDescriptorThreadLocal;
import com.liferay.exportimport.internal.lar.PortletDataContextThreadLocal;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.Method;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(service = ExportTaskPostAction.class)
public class ChangesetExportTaskPostAction implements ExportTaskPostAction {

	@Override
	public void run(
			BatchEngineExportTask batchEngineExportTask,
			BatchEngineTaskItemDelegate<?> batchEngineTaskItemDelegate,
			Object item)
		throws Exception {

		if (!ExportImportThreadLocal.isStagingInProcess()) {
			return;
		}

		ExportImportVulcanBatchEngineTaskItemDelegate.ExportImportDescriptor
			exportImportDescriptor =
				ExportImportDescriptorThreadLocal.getExportImportDescriptor();

		if (exportImportDescriptor == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to get the export import descriptor");
			}

			return;
		}

		PortletDataContext portletDataContext =
			PortletDataContextThreadLocal.getPortletDataContext();

		long changesetCollectionId = MapUtil.getLong(
			portletDataContext.getParameterMap(), "changesetCollectionId");

		if (changesetCollectionId == 0) {
			return;
		}

		String externalReferenceCode = _getExternalReferenceCode(item);

		if (Validator.isBlank(externalReferenceCode)) {
			return;
		}

		ChangesetEntry changesetEntry =
			_changesetEntryLocalService.fetchChangesetEntry(
				changesetCollectionId, externalReferenceCode,
				_classNameLocalService.getClassNameId(
					exportImportDescriptor.getModelClassName()));

		if (changesetEntry == null) {
			return;
		}

		ChangesetThreadLocal.addExportedChangesetEntryId(
			changesetEntry.getChangesetEntryId());
	}

	private String _getExternalReferenceCode(Object item) {
		try {
			Class<?> clazz = item.getClass();

			Method method = clazz.getMethod("getExternalReferenceCode");

			if (method.getParameterCount() == 0) {
				return GetterUtil.getString(method.invoke(item));
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ChangesetExportTaskPostAction.class);

	@Reference
	private ChangesetEntryLocalService _changesetEntryLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

}