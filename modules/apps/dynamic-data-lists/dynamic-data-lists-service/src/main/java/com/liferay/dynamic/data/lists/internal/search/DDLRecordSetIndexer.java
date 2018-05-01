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

package com.liferay.dynamic.data.lists.internal.search;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(immediate = true, service = Indexer.class)
public class DDLRecordSetIndexer extends BaseIndexer<DDLRecordSet> {

	public static final String CLASS_NAME = DDLRecordSet.class.getName();

	public DDLRecordSetIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.UID);
		setPermissionAware(true);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	protected void doDelete(DDLRecordSet recordSet) throws Exception {
		deleteDocument(recordSet.getCompanyId(), recordSet.getRecordSetId());
	}

	@Override
	protected Document doGetDocument(DDLRecordSet ddlRecordSet)
		throws Exception {

		return getBaseModelDocument(CLASS_NAME, ddlRecordSet);
	}

	@Override
	protected Summary doGetSummary(
			Document document, Locale locale, String snippet,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		return createSummary(document, Field.TITLE, Field.DESCRIPTION);
	}

	@Override
	protected void doReindex(DDLRecordSet recordSet) throws Exception {
		Document document = getDocument(recordSet);

		indexWriterHelper.updateDocument(
			getSearchEngineId(), recordSet.getCompanyId(), document,
			isCommitImmediately());

		reindexRecords(recordSet);
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		DDLRecordSet recordSet = ddlRecordSetLocalService.getRecordSet(classPK);

		doReindex(recordSet);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexRecordSets(companyId);
	}

	protected void reindexRecords(DDLRecordSet recordSet) throws Exception {
		Indexer<DDLRecord> indexer = indexerRegistry.nullSafeGetIndexer(
			DDLRecord.class);

		indexer.reindex(recordSet.getRecords());
	}

	protected void reindexRecordSets(long companyId) throws Exception {
		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			ddlRecordSetLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<DDLRecordSet>() {

				@Override
				public void performAction(DDLRecordSet recordSet)
					throws PortalException {

					try {
						Document document = getDocument(recordSet);

						if (document != null) {
							indexableActionableDynamicQuery.addDocuments(
								document);
						}
					}
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index dynamic data lists record " +
									recordSet.getRecordSetId(),
								pe);
						}
					}
				}

			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	@Reference
	protected DDLRecordSetLocalService ddlRecordSetLocalService;

	@Reference
	protected IndexerRegistry indexerRegistry;

	@Reference
	protected IndexWriterHelper indexWriterHelper;

	private static final Log _log = LogFactoryUtil.getLog(
		DDLRecordSetIndexer.class);

}