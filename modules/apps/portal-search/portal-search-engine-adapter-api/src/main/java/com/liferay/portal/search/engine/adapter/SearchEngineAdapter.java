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

package com.liferay.portal.search.engine.adapter;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.search.engine.adapter.cluster.ClusterRequest;
import com.liferay.portal.search.engine.adapter.cluster.ClusterResponse;
import com.liferay.portal.search.engine.adapter.document.DocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DocumentResponse;
import com.liferay.portal.search.engine.adapter.index.IndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndexResponse;
import com.liferay.portal.search.engine.adapter.search2.SearchRequest;
import com.liferay.portal.search.engine.adapter.search2.SearchResponse;
import com.liferay.portal.search.engine.adapter.snapshot.SnapshotRequest;
import com.liferay.portal.search.engine.adapter.snapshot.SnapshotResponse;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface SearchEngineAdapter {

	public
		<V extends
			com.liferay.portal.search.engine.adapter.search.SearchResponse> V
			execute(
				com.liferay.portal.search.engine.adapter.search.SearchRequest<V>
					searchRequest);

	public <T extends ClusterResponse> T execute(
		ClusterRequest<T> clusterRequest);

	public <S extends DocumentResponse> S execute(
		DocumentRequest<S> documentRequest);

	public <U extends IndexResponse> U execute(IndexRequest<U> indexRequest);

	public <V extends SearchResponse> V execute(SearchRequest<V> searchRequest);

	public <W extends SnapshotResponse> W execute(
		SnapshotRequest<W> snapshotRequest);

	public String getQueryString(Query query);

}