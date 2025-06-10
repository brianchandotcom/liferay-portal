/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.report.internal.exception.handler;

import com.liferay.batch.engine.exception.handler.BatchEngineImportTaskExceptionHandler;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.report.constants.ImportReportEntryConstants;
import com.liferay.exportimport.report.service.ImportReportEntryLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.ClassUtil;

import java.lang.reflect.Method;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Correa
 */
@Component(service = BatchEngineImportTaskExceptionHandler.class)
public class ExportImportBatchEngineImportTaskExceptionHandler
	implements BatchEngineImportTaskExceptionHandler {

	@Override
	public void handle(
		BatchEngineImportTask batchEngineImportTask, Exception exception,
		Object item) {

		if (!ExportImportThreadLocal.isImportInProcess()) {
			return;
		}

		_importReportEntryLocalService.addImportReportEntry(
			batchEngineImportTask.getCompanyId(),
			ExportImportThreadLocal.getClassNameId(),
			ExportImportThreadLocal.getClassPK(),
			_classNameLocalService.getClassNameId(ClassUtil.getClassName(item)),
			_getExternalReferenceCode(item), exception.getMessage(),
			ImportReportEntryConstants.TYPE_ERROR);
	}

	private String _getExternalReferenceCode(Object item) {
		try {
			Class<?> clazz = item.getClass();

			Method method = clazz.getDeclaredMethod("getExternalReferenceCode");

			return String.valueOf(method.invoke(item));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExportImportBatchEngineImportTaskExceptionHandler.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private ImportReportEntryLocalService _importReportEntryLocalService;

}