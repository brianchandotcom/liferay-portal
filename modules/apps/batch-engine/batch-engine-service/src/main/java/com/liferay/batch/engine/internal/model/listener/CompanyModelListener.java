/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.model.listener;

import com.liferay.batch.engine.model.BatchEngineExportTask;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.service.BatchEngineExportTaskLocalService;
import com.liferay.batch.engine.service.BatchEngineImportTaskLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Correa
 */
@Component(service = ModelListener.class)
public class CompanyModelListener extends BaseModelListener<Company> {

	@Override
	public void onBeforeRemove(Company company) throws ModelListenerException {
		for (BatchEngineExportTask batchEngineExportTask :
				_batchEngineExportTaskLocalService.getBatchEngineExportTasks(
					company.getCompanyId(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS)) {

			try {
				_batchEngineExportTaskLocalService.deleteBatchEngineExportTask(
					batchEngineExportTask.getBatchEngineExportTaskId());
			}
			catch (PortalException portalException) {
				_log.error(
					"Unable to delete batch engine export task " +
						batchEngineExportTask.getBatchEngineExportTaskId(),
					portalException);
			}
		}

		for (BatchEngineImportTask batchEngineImportTask :
				_batchEngineImportTaskLocalService.getBatchEngineImportTasks(
					company.getCompanyId(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS)) {

			try {
				_batchEngineImportTaskLocalService.deleteBatchEngineImportTask(
					batchEngineImportTask.getBatchEngineImportTaskId());
			}
			catch (PortalException portalException) {
				_log.error(
					"Unable to delete batch engine import task " +
						batchEngineImportTask.getBatchEngineImportTaskId(),
					portalException);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CompanyModelListener.class);

	@Reference
	private BatchEngineExportTaskLocalService
		_batchEngineExportTaskLocalService;

	@Reference
	private BatchEngineImportTaskLocalService
		_batchEngineImportTaskLocalService;

}