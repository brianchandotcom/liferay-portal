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

package com.liferay.segments.internal.search;

import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryLocalService;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Garcia
 */
@Component(immediate = true, service = Indexer.class)
public class SegmentsEntryIndexer extends BaseIndexer<SegmentsEntry> {

	public static final String CLASS_NAME = SegmentsEntry.class.getName();

	public SegmentsEntryIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.GROUP_ID, Field.MODIFIED_DATE, Field.NAME,
			Field.SCOPE_GROUP_ID);
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

		return _segmentsEntryModelResourcePermission.contains(
			permissionChecker, entryClassPK, ActionKeys.VIEW);
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		boolean active = GetterUtil.getBoolean(
			searchContext.getAttribute("active"));

		if (active) {
			TermFilter termFilter = new TermFilter(
				"active", Boolean.TRUE.toString());

			contextBooleanFilter.add(termFilter, BooleanClauseOccur.MUST);
		}
	}

	@Override
	protected void doDelete(SegmentsEntry segmentsEntry) throws Exception {
		deleteDocument(
			segmentsEntry.getCompanyId(), segmentsEntry.getSegmentsEntryId());
	}

	@Override
	protected Document doGetDocument(SegmentsEntry segmentsEntry)
		throws Exception {

		Document document = getBaseModelDocument(CLASS_NAME, segmentsEntry);

		document.addDate(Field.MODIFIED_DATE, segmentsEntry.getModifiedDate());
		document.addLocalizedKeyword(
			Field.NAME, segmentsEntry.getNameMap(), true);
		document.addKeyword("active", segmentsEntry.isActive());
		document.addKeyword(
			"segmentsEntryId", segmentsEntry.getSegmentsEntryId());

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Summary summary = createSummary(document);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(SegmentsEntry segmentsEntry) throws Exception {
		Document document = getDocument(segmentsEntry);

		_indexWriterHelper.updateDocument(
			getSearchEngineId(), segmentsEntry.getCompanyId(), document,
			isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		SegmentsEntry segmentsEntry =
			_segmentsEntryLocalService.getSegmentsEntry(classPK);

		doReindex(segmentsEntry);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexSegmensEntries(companyId);
	}

	protected void reindexSegmensEntries(long companyId)
		throws PortalException {

		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_segmentsEntryLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(SegmentsEntry segmentsEntry) -> {
				try {
					Document document = getDocument(segmentsEntry);

					indexableActionableDynamicQuery.addDocuments(document);
				}
				catch (PortalException pe) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to index segments entry " +
								segmentsEntry.getSegmentsEntryId(),
							pe);
					}
				}
			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsEntryIndexer.class);

	@Reference
	private IndexWriterHelper _indexWriterHelper;

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.segments.model.SegmentsEntry)"
	)
	private ModelResourcePermission<SegmentsEntry>
		_segmentsEntryModelResourcePermission;

}