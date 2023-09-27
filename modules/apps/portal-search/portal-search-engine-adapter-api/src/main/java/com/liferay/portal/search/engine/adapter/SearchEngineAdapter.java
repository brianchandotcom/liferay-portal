/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter;

import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.search.engine.adapter.ccr.CCRRequest;
import com.liferay.portal.search.engine.adapter.ccr.CCRResponse;
import com.liferay.portal.search.engine.adapter.cluster.ClusterRequest;
import com.liferay.portal.search.engine.adapter.cluster.ClusterResponse;
import com.liferay.portal.search.engine.adapter.document.DocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DocumentResponse;
import com.liferay.portal.search.engine.adapter.index.IndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndexResponse;
import com.liferay.portal.search.engine.adapter.search.SearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchResponse;
import com.liferay.portal.search.engine.adapter.snapshot.SnapshotRequest;
import com.liferay.portal.search.engine.adapter.snapshot.SnapshotResponse;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface SearchEngineAdapter {

	public <T extends CCRResponse> T execute(CCRRequest<T> ccrRequest);

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