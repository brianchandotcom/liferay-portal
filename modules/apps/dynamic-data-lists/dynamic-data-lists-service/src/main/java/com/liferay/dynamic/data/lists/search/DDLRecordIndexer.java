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

package com.liferay.dynamic.data.lists.search;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordVersionLocalService;
import com.liferay.dynamic.data.lists.service.permission.DDLRecordPermission;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.PermissionChecker;

import java.io.Serializable;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = Indexer.class)
public class DDLRecordIndexer extends BaseIndexer<DDLRecord> {

	public static final String CLASS_NAME = DDLRecord.class.getName();

	public DDLRecordIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.UID);
		setDefaultSelectedLocalizedFieldNames(Field.DESCRIPTION, Field.TITLE);
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, String entryClassName,
			long entryClassPK, String actionId)
		throws Exception {

		return DDLRecordPermission.contains(
			permissionChecker, entryClassPK, actionId);
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		int status = GetterUtil.getInteger(
			searchContext.getAttribute(Field.STATUS),
			WorkflowConstants.STATUS_APPROVED);

		if (status != WorkflowConstants.STATUS_ANY) {
			contextBooleanFilter.addRequiredTerm(Field.STATUS, status);
		}

		long recordSetId = GetterUtil.getLong(
			searchContext.getAttribute("recordSetId"));

		if (recordSetId > 0) {
			contextBooleanFilter.addRequiredTerm("recordSetId", recordSetId);
		}

		addSearchClassTypeIds(contextBooleanFilter, searchContext);

		String ddmStructureFieldName = (String)searchContext.getAttribute(
			"ddmStructureFieldName");
		Serializable ddmStructureFieldValue = searchContext.getAttribute(
			"ddmStructureFieldValue");

		if (Validator.isNotNull(ddmStructureFieldName) &&
			Validator.isNotNull(ddmStructureFieldValue)) {

			QueryFilter queryFilter = _ddmIndexer.createFieldValueQueryFilter(
				ddmStructureFieldName, ddmStructureFieldValue,
				searchContext.getLocale());

			contextBooleanFilter.add(queryFilter, BooleanClauseOccur.MUST);
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, Field.USER_NAME, false);

		addSearchTerm(searchQuery, searchContext, "ddmContent", false);
	}

	@Override
	protected void doDelete(DDLRecord ddlRecord) throws Exception {
		deleteDocument(ddlRecord.getCompanyId(), ddlRecord.getRecordId());
	}

	@Override
	protected Document doGetDocument(DDLRecord ddlRecord) throws Exception {
		Document document = getBaseModelDocument(CLASS_NAME, ddlRecord);

		DDLRecordVersion recordVersion = ddlRecord.getRecordVersion();

		document.addKeyword(
			Field.CLASS_TYPE_ID, recordVersion.getRecordSetId());
		document.addKeyword(Field.STATUS, recordVersion.getStatus());
		document.addKeyword(Field.VERSION, recordVersion.getVersion());

		document.addText(
			"ddmContent",
			extractDDMContent(recordVersion, LocaleUtil.getSiteDefault()));
		document.addKeyword("recordSetId", recordVersion.getRecordSetId());

		DDLRecordSet recordSet = recordVersion.getRecordSet();

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		DDMFormValues ddmFormValues = _storageEngine.getDDMFormValues(
			recordVersion.getDDMStorageId());

		_ddmIndexer.addAttributes(document, ddmStructure, ddmFormValues);

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		long recordSetId = GetterUtil.getLong(document.get("recordSetId"));

		String title = getTitle(recordSetId, locale);

		Summary summary = createSummary(
			document, Field.TITLE, Field.DESCRIPTION);

		summary.setMaxContentLength(200);
		summary.setTitle(title);

		return summary;
	}

	@Override
	protected void doReindex(DDLRecord ddlRecord) throws Exception {
		DDLRecordVersion recordVersion = ddlRecord.getRecordVersion();

		Document document = getDocument(ddlRecord);

		if (!recordVersion.isApproved()) {
			if (Validator.equals(
					recordVersion.getVersion(),
					DDLRecordConstants.VERSION_DEFAULT)) {

				SearchEngineUtil.deleteDocument(
					getSearchEngineId(), ddlRecord.getCompanyId(),
					document.get(Field.UID), isCommitImmediately());
			}

			return;
		}

		SearchEngineUtil.updateDocument(
			getSearchEngineId(), ddlRecord.getCompanyId(), document,
			isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		DDLRecord record = _ddlRecordLocalService.getRecord(classPK);

		doReindex(record);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexRecords(companyId);
	}

	protected String extractDDMContent(
			DDLRecordVersion recordVersion, Locale locale)
		throws Exception {

		DDMFormValues ddmFormValues = _storageEngine.getDDMFormValues(
			recordVersion.getDDMStorageId());

		if (ddmFormValues == null) {
			return StringPool.BLANK;
		}

		DDLRecordSet recordSet = recordVersion.getRecordSet();

		return _ddmIndexer.extractIndexableAttributes(
			recordSet.getDDMStructure(), ddmFormValues, locale);
	}

	protected String getTitle(long recordSetId, Locale locale) {
		try {
			DDLRecordSet recordSet = _ddlRecordSetLocalService.getRecordSet(
				recordSetId);

			DDMStructure ddmStructure = recordSet.getDDMStructure();

			String ddmStructureName = ddmStructure.getName(locale);

			String recordSetName = recordSet.getName(locale);

			return LanguageUtil.format(
				locale, "new-x-for-list-x",
				new Object[] {ddmStructureName, recordSetName}, false);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return StringPool.BLANK;
	}

	protected void reindexRecords(long companyId) throws Exception {
		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_ddlRecordLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property recordIdProperty = PropertyFactoryUtil.forName(
						"recordId");

					DynamicQuery recordVersionDynamicQuery =
						_ddlRecordVersionLocalService.dynamicQuery();

					recordVersionDynamicQuery.setProjection(
						ProjectionFactoryUtil.property("recordId"));

					Property statusProperty = PropertyFactoryUtil.forName(
						"status");

					recordVersionDynamicQuery.add(
						statusProperty.eq(WorkflowConstants.STATUS_APPROVED));

					dynamicQuery.add(
						recordIdProperty.in(recordVersionDynamicQuery));

					Property recordSetProperty = PropertyFactoryUtil.forName(
						"recordSetId");

					DynamicQuery recordSetDynamicQuery =
						_ddlRecordSetLocalService.dynamicQuery();

					recordSetDynamicQuery.setProjection(
						ProjectionFactoryUtil.property("recordSetId"));

					Property scopeProperty = PropertyFactoryUtil.forName(
						"scope");

					recordSetDynamicQuery.add(
						scopeProperty.in(_REINDEX_SCOPES));

					dynamicQuery.add(
						recordSetProperty.in(recordSetDynamicQuery));
				}
		});
		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<DDLRecord>() {

				@Override
				public void performAction(DDLRecord record)
					throws PortalException {

					try {
						Document document = getDocument(record);

						if (document != null) {
							indexableActionableDynamicQuery.addDocuments(
								document);
						}
					}
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index dynamic data lists record " +
									record.getRecordId(),
								pe);
						}
					}
				}

		});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	@Reference(unbind = "-")
	protected void setDDLRecordLocalService(
		DDLRecordLocalService ddlRecordLocalService) {

		_ddlRecordLocalService = ddlRecordLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDLRecordSetLocalService(
		DDLRecordSetLocalService ddlRecordSetLocalService) {

		_ddlRecordSetLocalService = ddlRecordSetLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDLRecordVersionLocalService(
		DDLRecordVersionLocalService ddlRecordVersionLocalService) {

		_ddlRecordVersionLocalService = ddlRecordVersionLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDMIndexer(DDMIndexer ddmIndexer) {
		_ddmIndexer = ddmIndexer;
	}

	@Reference(unbind = "-")
	protected void setStorageEngine(StorageEngine storageEngine) {
		_storageEngine = storageEngine;
	}

	private static final int[] _REINDEX_SCOPES = new int[] {
		DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS,
		DDLRecordSetConstants.SCOPE_FORMS
	};

	private static final Log _log = LogFactoryUtil.getLog(
		DDLRecordIndexer.class);

	private volatile DDLRecordLocalService _ddlRecordLocalService;
	private volatile DDLRecordSetLocalService _ddlRecordSetLocalService;
	private volatile DDLRecordVersionLocalService _ddlRecordVersionLocalService;
	private volatile DDMIndexer _ddmIndexer;
	private volatile StorageEngine _storageEngine;

}