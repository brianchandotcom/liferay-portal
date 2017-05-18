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

package com.liferay.trash.internal.search;

import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.trash.model.TrashEntry;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Julio Camarero
 * @author Zsolt Berentey
 */
@Component(immediate = true, service = Indexer.class)
public class TrashIndexer extends BaseIndexer<TrashEntry> {

	public static final String CLASS_NAME = TrashEntry.class.getName();

	public TrashIndexer() {
		setDefaultSelectedFieldNames(
			Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.REMOVED_BY_USER_NAME, Field.REMOVED_DATE,
			Field.ROOT_ENTRY_CLASS_NAME, Field.ROOT_ENTRY_CLASS_PK, Field.UID);
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public BooleanQuery getFullQuery(SearchContext searchContext)
		throws SearchException {

		try {
			BooleanFilter fullQueryBooleanFilter = new BooleanFilter();

			fullQueryBooleanFilter.addRequiredTerm(
				Field.COMPANY_ID, searchContext.getCompanyId());

			List<TrashHandler> trashHandlers =
				TrashHandlerRegistryUtil.getTrashHandlers();

			for (TrashHandler trashHandler : trashHandlers) {
				Filter filter = trashHandler.getExcludeFilter(searchContext);

				if (filter != null) {
					fullQueryBooleanFilter.add(
						filter, BooleanClauseOccur.MUST_NOT);
				}

				processTrashHandlerExcludeQuery(
					searchContext, fullQueryBooleanFilter, trashHandler);
			}

			long[] groupIds = searchContext.getGroupIds();

			if (ArrayUtil.isNotEmpty(groupIds)) {
				TermsFilter groupTermsFilter = new TermsFilter(Field.GROUP_ID);

				groupTermsFilter.addValues(ArrayUtil.toStringArray(groupIds));

				fullQueryBooleanFilter.add(
					groupTermsFilter, BooleanClauseOccur.MUST);
			}

			fullQueryBooleanFilter.addRequiredTerm(
				Field.STATUS, WorkflowConstants.STATUS_IN_TRASH);

			BooleanQuery fullQuery = createFullQuery(
				fullQueryBooleanFilter, searchContext);

			return fullQuery;
		}
		catch (SearchException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, String entryClassName,
			long entryClassPK, String actionId)
		throws Exception {

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			entryClassName);

		return trashHandler.hasTrashPermission(
			permissionChecker, 0, entryClassPK, actionId);
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		if (searchContext.getAttributes() == null) {
			return;
		}

		addSearchTerm(searchQuery, searchContext, Field.CONTENT, true);
		addSearchTerm(
			searchQuery, searchContext, Field.REMOVED_BY_USER_NAME, true);
		addSearchTerm(searchQuery, searchContext, Field.TITLE, true);
		addSearchTerm(searchQuery, searchContext, Field.TYPE, false);
		addSearchTerm(searchQuery, searchContext, Field.USER_NAME, true);
	}

	@Override
	protected void doDelete(TrashEntry trashEntry) {
	}

	@Override
	protected Document doGetDocument(TrashEntry trashEntry) {
		return null;
	}

	@Override
	protected String doGetSortField(String orderByCol) {
		if (orderByCol.equals("removed-date")) {
			return Field.REMOVED_DATE;
		}
		else if (orderByCol.equals("removed-by")) {
			return Field.REMOVED_BY_USER_NAME;
		}
		else {
			return orderByCol;
		}
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return null;
	}

	@Override
	protected void doReindex(String className, long classPK) {
	}

	@Override
	protected void doReindex(String[] ids) {
	}

	@Override
	protected void doReindex(TrashEntry trashEntry) {
	}

	/**
	 * @deprecated As of 1.0.0, added strictly to support backwards
	 *             compatibility of {@link
	 *             TrashHandler#getExcludeQuery(SearchContext)}
	 */
	@Deprecated
	protected void processTrashHandlerExcludeQuery(
		SearchContext searchContext, BooleanFilter fullQueryBooleanFilter,
		TrashHandler trashHandler) {

		Query query = trashHandler.getExcludeQuery(searchContext);

		if (query != null) {
			fullQueryBooleanFilter.add(
				new QueryFilter(query), BooleanClauseOccur.MUST_NOT);
		}
	}

}