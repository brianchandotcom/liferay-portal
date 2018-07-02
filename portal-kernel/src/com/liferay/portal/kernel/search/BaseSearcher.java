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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Eudaldo Alonso
 * @author László Csontos
 */
public abstract class BaseSearcher extends BaseIndexer<Object> {

	@Override
	public String getClassName() {
		return StringPool.BLANK;
	}

	@Override
	public IndexerPostProcessor[] getIndexerPostProcessors() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		String[] classNames = getSearchClassNames();

		if (ArrayUtil.isEmpty(classNames)) {
			return;
		}

		for (String className : classNames) {
			Indexer<?> indexer = IndexerRegistryUtil.getIndexer(className);

			if (indexer == null) {
				continue;
			}

			indexer.postProcessSearchQuery(
				searchQuery, fullQueryBooleanFilter, searchContext);
		}
	}

	@Override
	public void registerIndexerPostProcessor(
		IndexerPostProcessor indexerPostProcessor) {

		throw new UnsupportedOperationException();
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Summary doGetSummary(
			Document document, Locale locale, String snippet,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), added strictly to support
	 *             backwards compatibility of {@link
	 *             Indexer#postProcessSearchQuery(BooleanQuery, SearchContext)}
	 */
	@Deprecated
	@Override
	protected void doPostProcessSearchQuery(
			Indexer<?> indexer, BooleanQuery searchQuery,
			SearchContext searchContext)
		throws Exception {
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		throw new UnsupportedOperationException();
	}

}