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

package com.liferay.portal.search.elasticsearch6.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseSearchEngine;
import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.elasticsearch6.internal.index.IndexFactory;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.cluster.ClusterHealthStatus;
import com.liferay.portal.search.engine.adapter.cluster.HealthClusterRequest;
import com.liferay.portal.search.engine.adapter.cluster.HealthClusterResponse;
import com.liferay.portal.search.engine.adapter.index.CloseIndexRequest;
import com.liferay.portal.search.engine.adapter.index.CloseIndexResponse;
import com.liferay.portal.search.engine.adapter.snapshot.CreateSnapshotRepositoryRequest;
import com.liferay.portal.search.engine.adapter.snapshot.CreateSnapshotRequest;
import com.liferay.portal.search.engine.adapter.snapshot.CreateSnapshotResponse;
import com.liferay.portal.search.engine.adapter.snapshot.DeleteSnapshotRequest;
import com.liferay.portal.search.engine.adapter.snapshot.GetSnapshotRepositoriesRequest;
import com.liferay.portal.search.engine.adapter.snapshot.GetSnapshotRepositoriesResponse;
import com.liferay.portal.search.engine.adapter.snapshot.RestoreSnapshotRequest;
import com.liferay.portal.search.engine.adapter.snapshot.SnapshotDetails;
import com.liferay.portal.search.engine.adapter.snapshot.SnapshotRepositoryDetails;
import com.liferay.portal.search.engine.adapter.snapshot.SnapshotState;
import com.liferay.portal.search.index.IndexNameBuilder;

import java.util.List;
import java.util.Map;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.Strings;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {
		"search.engine.id=SYSTEM_ENGINE", "search.engine.impl=Elasticsearch"
	},
	service = {ElasticsearchSearchEngine.class, SearchEngine.class}
)
public class ElasticsearchSearchEngine extends BaseSearchEngine {

	@Override
	public synchronized String backup(long companyId, String backupName)
		throws SearchException {

		backupName = StringUtil.toLowerCase(backupName);

		validateBackupName(backupName);

		createBackupRepository();

		CreateSnapshotRequest createSnapshotRequest = new CreateSnapshotRequest(
			_BACKUP_REPOSITORY_NAME, backupName);

		createSnapshotRequest.setIndexNames(
			_indexNameBuilder.getIndexName(companyId));

		CreateSnapshotResponse createSnapshotResponse =
			_searchEngineAdapter.execute(createSnapshotRequest);

		SnapshotDetails snapshotDetails =
			createSnapshotResponse.getSnapshotDetails();

		SnapshotState snapshotState = snapshotDetails.getSnapshotState();

		if (snapshotState.equals(SnapshotState.FAILED)) {
			throw new IllegalStateException("Unable to complete snapshot");
		}

		return backupName;
	}

	@Override
	public void initialize(long companyId) {
		super.initialize(companyId);

		waitForYellowStatus();

		Client client = _elasticsearchConnectionManager.getClient();

		_indexFactory.createIndices(client.admin(), companyId);

		_elasticsearchConnectionManager.registerCompanyId(companyId);

		waitForYellowStatus();
	}

	@Override
	public synchronized void removeBackup(long companyId, String backupName) {
		if (!hasBackupRepository()) {
			return;
		}

		DeleteSnapshotRequest deleteSnapshotRequest = new DeleteSnapshotRequest(
			_BACKUP_REPOSITORY_NAME, backupName);

		_searchEngineAdapter.execute(deleteSnapshotRequest);
	}

	@Override
	public void removeCompany(long companyId) {
		super.removeCompany(companyId);

		try {
			_indexFactory.deleteIndices(
				_elasticsearchConnectionManager.getAdminClient(), companyId);

			_elasticsearchConnectionManager.unregisterCompanyId(companyId);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to delete index for " + companyId, e);
			}
		}
	}

	@Override
	public synchronized void restore(long companyId, String backupName)
		throws SearchException {

		backupName = StringUtil.toLowerCase(backupName);

		validateBackupName(backupName);

		CloseIndexRequest closeIndexRequest = new CloseIndexRequest(
			_indexNameBuilder.getIndexName(companyId));

		CloseIndexResponse closeIndexResponse = _searchEngineAdapter.execute(
			closeIndexRequest);

		if (!closeIndexResponse.isAcknowledged()) {
			throw new SystemException(
				"Error closing index: " +
					_indexNameBuilder.getIndexName(companyId));
		}

		RestoreSnapshotRequest restoreSnapshotRequest =
			new RestoreSnapshotRequest(_BACKUP_REPOSITORY_NAME, backupName);

		restoreSnapshotRequest.setIndexNames(
			_indexNameBuilder.getIndexName(companyId));

		_searchEngineAdapter.execute(restoreSnapshotRequest);

		waitForYellowStatus();
	}

	@Override
	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	public void setIndexSearcher(IndexSearcher indexSearcher) {
		super.setIndexSearcher(indexSearcher);
	}

	@Override
	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	public void setIndexWriter(IndexWriter indexWriter) {
		super.setIndexWriter(indexWriter);
	}

	public void unsetElasticsearchConnectionManager(
		ElasticsearchConnectionManager elasticsearchConnectionManager) {

		_elasticsearchConnectionManager = null;
	}

	public void unsetIndexFactory(IndexFactory indexFactory) {
		_indexFactory = null;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		setVendor(MapUtil.getString(properties, "search.engine.impl"));
	}

	protected void createBackupRepository() {
		if (hasBackupRepository()) {
			return;
		}

		CreateSnapshotRepositoryRequest createSnapshotRepositoryRequest =
			new CreateSnapshotRepositoryRequest(
				_BACKUP_REPOSITORY_NAME, "es_backup");

		_searchEngineAdapter.execute(createSnapshotRepositoryRequest);
	}

	protected boolean hasBackupRepository() {
		try {
			GetSnapshotRepositoriesRequest getSnapshotRepositoriesRequest =
				new GetSnapshotRepositoriesRequest(_BACKUP_REPOSITORY_NAME);

			GetSnapshotRepositoriesResponse getSnapshotRepositoriesResponse =
				_searchEngineAdapter.execute(getSnapshotRepositoriesRequest);

			List<SnapshotRepositoryDetails> snapshotRepositoryDetailsList =
				getSnapshotRepositoriesResponse.getSnapshotRepositoryDetails();

			if (snapshotRepositoryDetailsList.isEmpty()) {
				return false;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return true;
	}

	@Reference
	protected void setElasticsearchConnectionManager(
		ElasticsearchConnectionManager elasticsearchConnectionManager) {

		_elasticsearchConnectionManager = elasticsearchConnectionManager;
	}

	@Reference
	protected void setIndexFactory(IndexFactory indexFactory) {
		_indexFactory = indexFactory;
	}

	@Reference(unbind = "-")
	protected void setIndexNameBuilder(IndexNameBuilder indexNameBuilder) {
		_indexNameBuilder = indexNameBuilder;
	}

	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	protected void setSearchEngineAdapter(
		SearchEngineAdapter searchEngineAdapter) {

		_searchEngineAdapter = searchEngineAdapter;
	}

	protected void validateBackupName(String backupName)
		throws SearchException {

		if (Validator.isNull(backupName)) {
			throw new SearchException(
				"Backup name must not be an empty string");
		}

		if (StringUtil.contains(backupName, StringPool.COMMA)) {
			throw new SearchException("Backup name must not contain comma");
		}

		if (StringUtil.startsWith(backupName, StringPool.DASH)) {
			throw new SearchException("Backup name must not start with dash");
		}

		if (StringUtil.contains(backupName, StringPool.POUND)) {
			throw new SearchException("Backup name must not contain pounds");
		}

		if (StringUtil.contains(backupName, StringPool.SPACE)) {
			throw new SearchException("Backup name must not contain spaces");
		}

		if (StringUtil.contains(backupName, StringPool.TAB)) {
			throw new SearchException("Backup name must not contain tabs");
		}

		for (char c : backupName.toCharArray()) {
			if (Strings.INVALID_FILENAME_CHARS.contains(c)) {
				throw new SearchException(
					"Backup name must not contain invalid file name " +
						"characters");
			}
		}
	}

	protected void waitForYellowStatus() {
		long timeout = 30 * Time.SECOND;

		if (PortalRunMode.isTestMode()) {
			timeout = Time.HOUR;
		}

		HealthClusterRequest healthClusterRequest = new HealthClusterRequest();

		healthClusterRequest.setTimeout(timeout);

		healthClusterRequest.setWaitForClusterHealthStatus(
			ClusterHealthStatus.YELLOW);

		HealthClusterResponse healthClusterResponse =
			_searchEngineAdapter.execute(healthClusterRequest);

		if (healthClusterResponse.getClusterHealthStatus() ==
				ClusterHealthStatus.RED) {

			throw new IllegalStateException(
				"Unable to initialize Elasticsearch cluster: " +
					healthClusterResponse);
		}
	}

	private static final String _BACKUP_REPOSITORY_NAME = "liferay_backup";

	private static final Log _log = LogFactoryUtil.getLog(
		ElasticsearchSearchEngine.class);

	private ElasticsearchConnectionManager _elasticsearchConnectionManager;
	private IndexFactory _indexFactory;
	private IndexNameBuilder _indexNameBuilder;
	private SearchEngineAdapter _searchEngineAdapter;

}