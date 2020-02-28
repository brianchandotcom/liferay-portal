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

package com.liferay.portal.search.elasticsearch7.internal.ccr;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.ccr.CrossClusterReplicationHelper;
import com.liferay.portal.search.configuration.CrossClusterReplicationConfigurationWrapper;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.ccr.PauseFollowCCRRequest;
import com.liferay.portal.search.engine.adapter.ccr.PutFollowCCRRequest;
import com.liferay.portal.search.engine.adapter.ccr.UnfollowCCRRequest;
import com.liferay.portal.search.engine.adapter.index.CloseIndexRequest;
import com.liferay.portal.search.engine.adapter.index.DeleteIndexRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

/**
 * @author Bryan Engler
 */
@Component(immediate = true, service = CrossClusterReplicationHelper.class)
public class CrossClusterReplicationHelperImpl
	implements CrossClusterReplicationHelper {

	@Override
	public void follow(String indexName) {
		if (!elasticsearchConnectionManager.
				isCrossClusterReplicationEnabled()) {

			return;
		}

		try {
			_putFollow(indexName);
		}
		catch (RuntimeException runtimeException) {
			_log.error(
				StringBundler.concat(
					"Unable to follow the index ", indexName, " in the ",
					_REMOTE_CLUSTER_ALIAS, " cluster"),
				runtimeException);
		}
	}

	@Override
	public void unfollow(String indexName) {
		if (!elasticsearchConnectionManager.
				isCrossClusterReplicationEnabled()) {

			return;
		}

		try {
			_pauseFollow(indexName);

			_closeIndex(indexName);

			_unfollow(indexName);

			_deleteIndex(indexName);
		}
		catch (RuntimeException runtimeException) {
			_log.error(
				"Unable to unfollow the index " + indexName, runtimeException);
		}
	}

	@Reference(cardinality = ReferenceCardinality.OPTIONAL)
	protected volatile CrossClusterReplicationConfigurationWrapper
		crossClusterReplicationConfigurationWrapper;

	@Reference
	protected ElasticsearchConnectionManager elasticsearchConnectionManager;

	@Reference
	protected SearchEngineAdapter searchEngineAdapter;

	private void _closeIndex(String indexName) {
		CloseIndexRequest closeIndexRequest = new CloseIndexRequest(indexName);

		closeIndexRequest.setConnectionId(_getConnectionId());

		searchEngineAdapter.execute(closeIndexRequest);
	}

	private void _deleteIndex(String indexName) {
		DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(
			indexName);

		deleteIndexRequest.setConnectionId(_getConnectionId());

		searchEngineAdapter.execute(deleteIndexRequest);
	}

	private String _getConnectionId() {
		return crossClusterReplicationConfigurationWrapper.
			getCCRLocalClusterConnectionId();
	}

	private void _pauseFollow(String indexName) {
		PauseFollowCCRRequest pauseFollowCCRRequest = new PauseFollowCCRRequest(
			indexName);

		pauseFollowCCRRequest.setConnectionId(_getConnectionId());

		searchEngineAdapter.execute(pauseFollowCCRRequest);
	}

	private void _putFollow(String indexName) {
		PutFollowCCRRequest putFollowRequest = new PutFollowCCRRequest(
			_REMOTE_CLUSTER_ALIAS, indexName, indexName);

		putFollowRequest.setConnectionId(_getConnectionId());

		putFollowRequest.setWaitForActiveShards(1);

		searchEngineAdapter.execute(putFollowRequest);
	}

	private void _unfollow(String indexName) {
		UnfollowCCRRequest unfollowCCRRequest = new UnfollowCCRRequest(
			indexName);

		unfollowCCRRequest.setConnectionId(_getConnectionId());

		searchEngineAdapter.execute(unfollowCCRRequest);
	}

	private static final String _REMOTE_CLUSTER_ALIAS = "leader";

	private static final Log _log = LogFactoryUtil.getLog(
		CrossClusterReplicationHelperImpl.class);

}