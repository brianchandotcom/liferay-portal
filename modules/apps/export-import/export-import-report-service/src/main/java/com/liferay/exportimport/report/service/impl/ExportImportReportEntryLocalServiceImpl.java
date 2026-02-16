/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.report.service.impl;

import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.report.constants.ExportImportReportEntryConstants;
import com.liferay.exportimport.report.internal.util.ExportImportReportEntryUtil;
import com.liferay.exportimport.report.model.ExportImportReportEntry;
import com.liferay.exportimport.report.service.base.ExportImportReportEntryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Correa
 * @author Jonathan McCann
 */
@Component(
	property = "model.class.name=com.liferay.exportimport.report.model.ExportImportReportEntry",
	service = AopService.class
)
public class ExportImportReportEntryLocalServiceImpl
	extends ExportImportReportEntryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ExportImportReportEntry addEmptyExportImportReportEntry(
		long groupId, long companyId, String classExternalReferenceCode,
		long classNameId, long exportImportConfigurationId,
		String modelNameLanguageKey) {

		ExportImportReportEntry exportImportReportEntry =
			exportImportReportEntryPersistence.create(
				counterLocalService.increment());

		exportImportReportEntry.setGroupId(groupId);
		exportImportReportEntry.setCompanyId(companyId);
		exportImportReportEntry.setClassExternalReferenceCode(
			classExternalReferenceCode);
		exportImportReportEntry.setClassNameId(classNameId);
		exportImportReportEntry.setExportImportConfigurationId(
			exportImportConfigurationId);
		exportImportReportEntry.setModelNameLanguageKey(modelNameLanguageKey);
		exportImportReportEntry.setOrigin(
			ExportImportReportEntryUtil.getOrigin());
		exportImportReportEntry.setType(
			ExportImportReportEntryConstants.TYPE_EMPTY);
		exportImportReportEntry.setStatus(
			ExportImportReportEntryConstants.STATUS_UNRESOLVED);

		return exportImportReportEntryPersistence.update(
			exportImportReportEntry);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ExportImportReportEntry addErrorExportImportReportEntry(
		long groupId, long companyId, String classExternalReferenceCode,
		long classNameId, long classPK, long exportImportConfigurationId,
		String errorMessage, String errorStacktrace,
		String modelNameLanguageKey) {

		ExportImportReportEntry exportImportReportEntry =
			exportImportReportEntryPersistence.create(
				counterLocalService.increment());

		exportImportReportEntry.setGroupId(groupId);
		exportImportReportEntry.setCompanyId(companyId);
		exportImportReportEntry.setClassExternalReferenceCode(
			classExternalReferenceCode);
		exportImportReportEntry.setClassNameId(classNameId);
		exportImportReportEntry.setClassPK(classPK);
		exportImportReportEntry.setExportImportConfigurationId(
			exportImportConfigurationId);
		exportImportReportEntry.setErrorMessage(errorMessage);
		exportImportReportEntry.setErrorStacktrace(errorStacktrace);
		exportImportReportEntry.setModelNameLanguageKey(modelNameLanguageKey);
		exportImportReportEntry.setOrigin(
			ExportImportReportEntryUtil.getOrigin());
		exportImportReportEntry.setType(
			ExportImportReportEntryConstants.TYPE_ERROR);
		exportImportReportEntry.setStatus(
			ExportImportReportEntryConstants.STATUS_UNRESOLVED);

		return exportImportReportEntryPersistence.update(
			exportImportReportEntry);
	}

	@Override
	public List<ExportImportReportEntry> getExportImportReportEntries(
		long companyId, long exportImportConfigurationId) {

		return exportImportReportEntryPersistence.findByC_E(
			companyId, exportImportConfigurationId);
	}

	@Override
	public ExportImportReportEntry getOrAddEmptyExportImportReportEntry(
		long groupId, long companyId, String classExternalReferenceCode,
		long classNameId, long exportImportConfigurationId,
		String modelNameLanguageKey) {

		ExportImportReportEntry exportImportReportEntry =
			exportImportReportEntryPersistence.fetchByG_C_C_C_E_T(
				groupId, companyId, classExternalReferenceCode, classNameId,
				exportImportConfigurationId,
				ExportImportReportEntryConstants.TYPE_EMPTY);

		if (exportImportReportEntry != null) {
			return exportImportReportEntry;
		}

		return exportImportReportEntryLocalService.
			addEmptyExportImportReportEntry(
				groupId, companyId, classExternalReferenceCode, classNameId,
				exportImportConfigurationId, modelNameLanguageKey);
	}

	@Override
	public void resolveEmptyExportImportReportEntries(
			long groupId, long companyId, String classExternalReferenceCode,
			long classNameId)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property classExternalReferenceCodeProperty =
					PropertyFactoryUtil.forName("classExternalReferenceCode");

				dynamicQuery.add(
					classExternalReferenceCodeProperty.eq(
						classExternalReferenceCode));

				Property classNameIdProperty = PropertyFactoryUtil.forName(
					"classNameId");

				dynamicQuery.add(classNameIdProperty.eq(classNameId));

				Property statusProperty = PropertyFactoryUtil.forName("status");

				dynamicQuery.add(
					statusProperty.eq(
						ExportImportReportEntryConstants.STATUS_UNRESOLVED));

				Property typeProperty = PropertyFactoryUtil.forName("type");

				dynamicQuery.add(
					typeProperty.eq(
						ExportImportReportEntryConstants.TYPE_EMPTY));
			});

		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setGroupId(groupId);

		Long exportImportConfigurationId =
			ExportImportThreadLocal.getExportImportConfigurationId();

		actionableDynamicQuery.setPerformActionMethod(
			(ExportImportReportEntry exportImportReportEntry) -> {
				long exportImportReportEntryId =
					exportImportReportEntry.getExportImportReportEntryId();

				try {
					if ((GetterUtil.getLong(exportImportConfigurationId) !=
							0) &&
						(exportImportConfigurationId ==
							exportImportReportEntry.
								getExportImportConfigurationId())) {

						if (_log.isDebugEnabled()) {
							_log.debug(
								"Deleting report entry " +
									exportImportReportEntryId);
						}

						exportImportReportEntryLocalService.
							deleteExportImportReportEntry(
								exportImportReportEntryId);
					}
					else {
						if (_log.isDebugEnabled()) {
							_log.debug(
								"Resolving report entry " +
									exportImportReportEntryId);
						}

						exportImportReportEntry.setStatus(
							ExportImportReportEntryConstants.STATUS_RESOLVED);

						updateExportImportReportEntry(exportImportReportEntry);
					}
				}
				catch (Exception exception) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Unable to resolve the report entry " +
								exportImportReportEntryId,
							exception);
					}
				}
			});

		actionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExportImportReportEntryLocalServiceImpl.class);

}