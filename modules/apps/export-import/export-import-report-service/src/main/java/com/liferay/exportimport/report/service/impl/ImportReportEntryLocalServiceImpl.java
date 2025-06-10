/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.report.service.impl;

import com.liferay.exportimport.report.model.ImportReportEntry;
import com.liferay.exportimport.report.service.base.ImportReportEntryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Correa
 */
@Component(
	property = "model.class.name=com.liferay.exportimport.report.model.ImportReportEntry",
	service = AopService.class
)
public class ImportReportEntryLocalServiceImpl
	extends ImportReportEntryLocalServiceBaseImpl {

	@Override
	public ImportReportEntry addImportReportEntry(
		long companyId, long classNameId, long classPK, long entityClassNameId,
		String entityExternalReferenceCode, String error, int type) {

		ImportReportEntry importReportEntry =
			importReportEntryPersistence.create(
				counterLocalService.increment());

		importReportEntry.setCompanyId(companyId);
		importReportEntry.setClassNameId(classNameId);
		importReportEntry.setClassPK(classPK);
		importReportEntry.setEntityClassNameId(entityClassNameId);
		importReportEntry.setEntityExternalReferenceCode(
			entityExternalReferenceCode);
		importReportEntry.setError(error);
		importReportEntry.setType(type);

		return importReportEntryPersistence.update(importReportEntry);
	}

	@Override
	public List<ImportReportEntry> getImportReportEntries(
		long companyId, long classNameId, long classPK) {

		return importReportEntryPersistence.findByC_C_C(
			companyId, classNameId, classPK);
	}

}