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

package com.liferay.portlet.dynamicdatalists.util;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordConstants;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSetConstants;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordLocalServiceUtil;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMIndexerUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

/**
 * @author Marcellus Tavares
 */
public class DDLIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {DDLRecord.class.getName()};

	public static final String PORTLET_ID = PortletKeys.DYNAMIC_DATA_LISTS;

	public DDLIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.UID);
		setDefaultSelectedLocalizedFieldNames(Field.DESCRIPTION, Field.TITLE);
		setFilterSearch(true);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getPortletId() {
		return PORTLET_ID;
	}

	@Override
	public void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		int status = GetterUtil.getInteger(
			searchContext.getAttribute(Field.STATUS),
			WorkflowConstants.STATUS_APPROVED);

		if (status != WorkflowConstants.STATUS_ANY) {
			contextQuery.addRequiredTerm(Field.STATUS, status);
		}

		long recordSetId = GetterUtil.getLong(
			searchContext.getAttribute("recordSetId"));

		if (recordSetId > 0) {
			contextQuery.addRequiredTerm("recordSetId", recordSetId);
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, Field.USER_NAME, false);

		addSearchTerm(searchQuery, searchContext, "ddmContent", false);
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		DDLRecord record = (DDLRecord)obj;

		deleteDocument(record.getCompanyId(), record.getRecordId());
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		DDLRecord record = (DDLRecord)obj;

		Document document = getBaseModelDocument(PORTLET_ID, record);

		DDLRecordVersion recordVersion = record.getRecordVersion();

		document.addKeyword(Field.STATUS, recordVersion.getStatus());
		document.addKeyword(Field.VERSION, recordVersion.getVersion());

		document.addText(
			"ddmContent",
			extractDDMContent(recordVersion, LocaleUtil.getSiteDefault()));
		document.addKeyword("recordSetId", recordVersion.getRecordSetId());

		DDLRecordSet recordSet = recordVersion.getRecordSet();

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		Fields fields = StorageEngineUtil.getFields(
			recordVersion.getDDMStorageId());

		DDMIndexerUtil.addAttributes(document, ddmStructure, fields);

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet, PortletURL portletURL,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		long recordSetId = GetterUtil.getLong(document.get("recordSetId"));

		String title = getTitle(recordSetId, locale);

		String recordId = document.get(Field.ENTRY_CLASS_PK);

		portletURL.setParameter(
			"struts_action", "/dynamic_data_lists/view_record");
		portletURL.setParameter("recordId", recordId);
		portletURL.setParameter("version", document.get(Field.VERSION));

		Summary summary = createSummary(
			document, Field.TITLE, Field.DESCRIPTION);

		summary.setMaxContentLength(200);
		summary.setPortletURL(portletURL);
		summary.setTitle(title);

		return summary;
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		DDLRecord record = (DDLRecord)obj;

		DDLRecordVersion recordVersion = record.getRecordVersion();

		Document document = getDocument(record);

		if (!recordVersion.isApproved()) {
			if (Validator.equals(
					recordVersion.getVersion(),
					DDLRecordConstants.VERSION_DEFAULT)) {

				SearchEngineUtil.deleteDocument(
					getSearchEngineId(), record.getCompanyId(),
					document.get(Field.UID), isCommitImmediately());
			}

			return;
		}

		if (document != null) {
			SearchEngineUtil.updateDocument(
				getSearchEngineId(), record.getCompanyId(), document,
				isCommitImmediately());
		}
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		DDLRecord record = DDLRecordLocalServiceUtil.getRecord(classPK);

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

		Fields fields = StorageEngineUtil.getFields(
			recordVersion.getDDMStorageId());

		if (fields == null) {
			return StringPool.BLANK;
		}

		DDLRecordSet recordSet = recordVersion.getRecordSet();

		return DDMIndexerUtil.extractAttributes(
			recordSet.getDDMStructure(), fields, locale);
	}

	@Override
	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected String getTitle(long recordSetId, Locale locale) {
		try {
			DDLRecordSet recordSet = DDLRecordSetLocalServiceUtil.getRecordSet(
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
		Long[] minAndMaxRecordIds =
			DDLRecordLocalServiceUtil.getMinAndMaxCompanyRecordIds(
				companyId, WorkflowConstants.STATUS_APPROVED,
				DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS);

		if ((minAndMaxRecordIds[0] == null) ||
			(minAndMaxRecordIds[1] == null)) {

			return;
		}

		long minRecordId = minAndMaxRecordIds[0];
		long maxRecordId = minAndMaxRecordIds[1];

		long startRecordId = minRecordId;
		long endRecordId = startRecordId + DEFAULT_INTERVAL;

		while (startRecordId <= maxRecordId) {
			reindexRecords(companyId, startRecordId, endRecordId);

			startRecordId = endRecordId;
			endRecordId += DEFAULT_INTERVAL;
		}
	}

	protected void reindexRecords(
			long companyId, long startRecordId, long endRecordId)
		throws Exception {

		List<DDLRecord> records =
			DDLRecordLocalServiceUtil.getMinAndMaxCompanyRecords(
				companyId, WorkflowConstants.STATUS_APPROVED,
				DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS, startRecordId,
				endRecordId);

		Collection<Document> documents = new ArrayList<Document>(
			records.size());

		for (DDLRecord record : records) {
			Document document = getDocument(record);

			documents.add(document);
		}

		SearchEngineUtil.updateDocuments(
			getSearchEngineId(), companyId, documents, isCommitImmediately());
	}

	private static Log _log = LogFactoryUtil.getLog(DDLIndexer.class);

}