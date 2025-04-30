/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.service.impl;

import com.liferay.batch.engine.model.BatchEngineImportReportEntry;
import com.liferay.batch.engine.service.base.BatchEngineImportReportEntryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(
	property = "model.class.name=com.liferay.batch.engine.model.BatchEngineImportReportEntry",
	service = AopService.class
)
public class BatchEngineImportReportEntryLocalServiceImpl
	extends BatchEngineImportReportEntryLocalServiceBaseImpl {

	@Override
	public BatchEngineImportReportEntry addBatchEngineImportReportEntry(
			long companyId, long classNameId, long classPK,
			long entityClassNameId, String entityExternalReferenceCode,
			String error, int type)
		throws Exception {

		BatchEngineImportReportEntry batchEngineImportReportEntry =
			batchEngineImportReportEntryPersistence.create(
				counterLocalService.increment());

		batchEngineImportReportEntry.setCompanyId(companyId);
		batchEngineImportReportEntry.setClassNameId(classNameId);
		batchEngineImportReportEntry.setClassPK(classPK);
		batchEngineImportReportEntry.setEntityClassNameId(entityClassNameId);
		batchEngineImportReportEntry.setEntityExternalReferenceCode(
			entityExternalReferenceCode);
		batchEngineImportReportEntry.setError(error);
		batchEngineImportReportEntry.setType(type);

		return batchEngineImportReportEntryPersistence.update(
			batchEngineImportReportEntry);
	}

	@Override
	public List<BatchEngineImportReportEntry> getBatchEngineImportReportEntries(
			long companyId, long classNameId, long classPK)
		throws Exception {

		return batchEngineImportReportEntryPersistence.findByC_C_C(
			companyId, classNameId, classPK);
	}

}