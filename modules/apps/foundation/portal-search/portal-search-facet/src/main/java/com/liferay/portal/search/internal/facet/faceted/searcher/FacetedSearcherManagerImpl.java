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

package com.liferay.portal.search.internal.facet.faceted.searcher;

import com.liferay.expando.kernel.util.ExpandoBridgeFactory;
import com.liferay.portal.kernel.search.IndexSearcherHelper;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcher;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcherManager;
import com.liferay.portal.kernel.service.GroupLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(immediate = true, service = FacetedSearcherManager.class)
public class FacetedSearcherManagerImpl implements FacetedSearcherManager {

	@Override
	public FacetedSearcher createFacetedSearcher() {
		return new FacetedSearcherImpl(
			expandoBridgeFactory, groupLocalService, indexerRegistry,
			indexSearcherHelper);
	}

	@Reference
	protected ExpandoBridgeFactory expandoBridgeFactory;

	@Reference
	protected GroupLocalService groupLocalService;

	@Reference
	protected IndexerRegistry indexerRegistry;

	@Reference
	protected IndexSearcherHelper indexSearcherHelper;

}