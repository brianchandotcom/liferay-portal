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

package com.liferay.portal.search.elasticsearch6.internal.cluster;

import com.liferay.portal.kernel.cluster.ClusterExecutor;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutor;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchConnection;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.elasticsearch6.internal.connection.OperationMode;
import com.liferay.portal.search.index.IndexNameBuilder;

import java.util.List;

import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(immediate = true, service = ElasticsearchCluster.class)
public class ElasticsearchCluster {

	@Activate
	protected void activate() {
		_replicasClusterListener = new ReplicasClusterListener(
			new ReplicasClusterContextImpl());

		_clusterExecutor.addClusterEventListener(_replicasClusterListener);

		_clusterMasterExecutor.addClusterMasterTokenTransitionListener(
			_replicasClusterListener);
	}

	@Deactivate
	protected void deactivate() {
		_clusterExecutor.removeClusterEventListener(_replicasClusterListener);

		_clusterMasterExecutor.removeClusterMasterTokenTransitionListener(
			_replicasClusterListener);

		_replicasClusterListener = null;
	}

	@Reference
	protected CompanyLocalService companyLocalService;

	@Reference
	protected IndexNameBuilder indexNameBuilder;

	protected class ReplicasClusterContextImpl
		implements ReplicasClusterContext {

		@Override
		public int getClusterSize() {
			List<ClusterNode> clusterNodes = _clusterExecutor.getClusterNodes();

			return clusterNodes.size();
		}

		@Override
		public ReplicasManager getReplicasManager() {
			ElasticsearchConnection elasticsearchConnection =
				getActiveElasticsearchConnection();

			Client client = elasticsearchConnection.getClient();

			AdminClient adminClient = client.admin();

			return new ReplicasManagerImpl(adminClient.indices());
		}

		@Override
		public String[] getTargetIndexNames() {
			List<Company> companies = companyLocalService.getCompanies();

			String[] targetIndexNames = new String[companies.size() + 1];

			for (int i = 0; i < (targetIndexNames.length - 1); i++) {
				Company company = companies.get(i);

				targetIndexNames[i] = indexNameBuilder.getIndexName(
					company.getCompanyId());
			}

			targetIndexNames[targetIndexNames.length - 1] =
				indexNameBuilder.getIndexName(CompanyConstants.SYSTEM);

			return targetIndexNames;
		}

		@Override
		public boolean isEmbeddedOperationMode() {
			ElasticsearchConnection elasticsearchConnection =
				getActiveElasticsearchConnection();

			OperationMode operationMode =
				elasticsearchConnection.getOperationMode();

			if (operationMode == OperationMode.EMBEDDED) {
				return true;
			}

			return false;
		}

		@Override
		public boolean isMaster() {
			return _clusterMasterExecutor.isMaster();
		}

		protected ElasticsearchConnection getActiveElasticsearchConnection() {
			return _elasticsearchConnectionManager.getElasticsearchConnection();
		}

	}

	@Reference
	private ClusterExecutor _clusterExecutor;

	@Reference
	private ClusterMasterExecutor _clusterMasterExecutor;

	@Reference
	private ElasticsearchConnectionManager _elasticsearchConnectionManager;

	private ReplicasClusterListener _replicasClusterListener;

}