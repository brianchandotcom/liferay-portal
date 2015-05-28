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

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.util.PortalUtil;

/**
 * @author Michael C. Han
 */
public class BaseRelatedEntryIndexer implements RelatedEntryIndexer {

	public void addRelatedClassNames(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		searchContext.setAttribute("relatedClassName", Boolean.TRUE);

		String[] relatedEntryClassNames = (String[])searchContext.getAttribute(
			"relatedEntryClassNames");

		if (ArrayUtil.isEmpty(relatedEntryClassNames)) {
			return;
		}

		BooleanQuery relatedQueries = BooleanQueryFactoryUtil.create(
			searchContext);

		for (String relatedEntryClassName : relatedEntryClassNames) {
			Indexer indexer = IndexerRegistryUtil.getIndexer(
				relatedEntryClassName);

			if (indexer == null) {
				continue;
			}

			BooleanQuery relatedQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			indexer.postProcessContextQuery(relatedQuery, searchContext);

			for (IndexerPostProcessor indexerPostProcessor :
					indexer.getIndexerPostProcessors()) {

				indexerPostProcessor.postProcessContextQuery(
					relatedQuery, searchContext);
			}

			relatedQuery.addRequiredTerm(
				Field.CLASS_NAME_ID,
				PortalUtil.getClassNameId(relatedEntryClassName));

			relatedQueries.add(relatedQuery, BooleanClauseOccur.SHOULD);
		}

		contextQuery.add(relatedQueries, BooleanClauseOccur.MUST);

		searchContext.setAttribute("relatedClassName", Boolean.FALSE);
	}

	@Override
	public void addRelatedEntryFields(Document document, Object obj)
		throws Exception {
	}

	@Override
	public void updateFullQuery(SearchContext searchContext) {
	}

}