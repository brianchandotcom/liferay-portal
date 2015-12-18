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

package com.liferay.portal.kernel.search;

import java.util.Collection;

/**
 * @author Bruno Farache
 * @author Raymond Augé
 * @author Michael C. Han
 */
@Deprecated
public class SearchEngineUtil extends SearchEngineHelperUtil {

	@Deprecated
	public static final String GENERIC_ENGINE_ID =
		SearchEngineHelper.GENERIC_ENGINE_ID;

	@Deprecated
	public static final String SYSTEM_ENGINE_ID =
		SearchEngineHelper.SYSTEM_ENGINE_ID;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #addDocument(String, long,
	 *             Document, boolean)}
	 */
	@Deprecated
	public static void addDocument(
			String searchEngineId, long companyId, Document document)
		throws SearchException {

		addDocument(searchEngineId, companyId, document, false);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #addDocuments(String, long,
	 *             Collection, boolean)}
	 */
	@Deprecated
	public static void addDocuments(
			String searchEngineId, long companyId,
			Collection<Document> documents)
		throws SearchException {

		addDocuments(searchEngineId, companyId, documents, false);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #deleteDocument(String, long,
	 *             String, boolean)}
	 */
	@Deprecated
	public static void deleteDocument(
			String searchEngineId, long companyId, String uid)
		throws SearchException {

		deleteDocument(searchEngineId, companyId, uid, false);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #deleteDocuments(String,
	 *             long, Collection, boolean)}
	 */
	@Deprecated
	public static void deleteDocuments(
			String searchEngineId, long companyId, Collection<String> uids)
		throws SearchException {

		deleteDocuments(searchEngineId, companyId, uids, false);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #deleteEntityDocuments(String, long, String, boolean)}
	 */
	@Deprecated
	public static void deletePortletDocuments(
			String searchEngineId, long companyId, String portletId)
		throws SearchException {

		deleteEntityDocuments(searchEngineId, companyId, portletId, false);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #search(SearchContext,
	 *             Query)}
	 */
	@Deprecated
	public static Hits search(
			String searchEngineId, long companyId, Query query, int start,
			int end)
		throws SearchException {

		return search(
			searchEngineId, companyId, query, SortFactoryUtil.getDefaultSorts(),
			start, end);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #search(SearchContext,
	 *             Query)}
	 */
	@Deprecated
	public static Hits search(
			String searchEngineId, long companyId, Query query, Sort sort,
			int start, int end)
		throws SearchException {

		return search(
			searchEngineId, companyId, query, new Sort[] {sort}, start, end);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #search(SearchContext,
	 *             Query)}
	 */
	@Deprecated
	public static Hits search(
			String searchEngineId, long companyId, Query query, Sort[] sorts,
			int start, int end)
		throws SearchException {

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);
		searchContext.setSearchEngineId(searchEngineId);
		searchContext.setSorts(sorts);
		searchContext.setStart(start);

		return search(searchContext, query);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #updateDocument(String, long,
	 *             Document, boolean)}
	 */
	@Deprecated
	public static void updateDocument(
			String searchEngineId, long companyId, Document document)
		throws SearchException {

		updateDocument(searchEngineId, companyId, document, false);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #updateDocuments(String,
	 *             long, Collection, boolean)}
	 */
	@Deprecated
	public static void updateDocuments(
			String searchEngineId, long companyId,
			Collection<Document> documents)
		throws SearchException {

		updateDocuments(searchEngineId, companyId, documents, false);
	}

}