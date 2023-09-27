/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.cluster;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.cluster.ClusterRequestExecutor;

/**
 * @author Dylan Rebelak
 */
public class ClusterRequestExecutorFixture {

	public ClusterRequestExecutor getClusterRequestExecutor() {
		return _clusterRequestExecutor;
	}

	public void setUp() {
		ClusterHealthStatusTranslator clusterHealthStatusTranslator =
			new ClusterHealthStatusTranslatorImpl();

		_clusterRequestExecutor = new ElasticsearchClusterRequestExecutor();

		ReflectionTestUtil.setFieldValue(
			_clusterRequestExecutor, "_healthClusterRequestExecutor",
			_createHealthClusterRequestExecutor(
				clusterHealthStatusTranslator, _elasticsearchClientResolver));
		ReflectionTestUtil.setFieldValue(
			_clusterRequestExecutor, "_stateClusterRequestExecutor",
			_createStateClusterRequestExecutor(_elasticsearchClientResolver));
		ReflectionTestUtil.setFieldValue(
			_clusterRequestExecutor, "_statsClusterRequestExecutor",
			_createStatsClusterRequestExecutor(
				clusterHealthStatusTranslator, _elasticsearchClientResolver));
	}

	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	private HealthClusterRequestExecutor _createHealthClusterRequestExecutor(
		ClusterHealthStatusTranslator clusterHealthStatusTranslator,
		ElasticsearchClientResolver elasticsearchClientResolver) {

		HealthClusterRequestExecutor healthClusterRequestExecutor =
			new HealthClusterRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			healthClusterRequestExecutor, "_clusterHealthStatusTranslator",
			clusterHealthStatusTranslator);
		ReflectionTestUtil.setFieldValue(
			healthClusterRequestExecutor, "_elasticsearchClientResolver",
			elasticsearchClientResolver);

		return healthClusterRequestExecutor;
	}

	private StateClusterRequestExecutor _createStateClusterRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		StateClusterRequestExecutor stateClusterRequestExecutor =
			new StateClusterRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			stateClusterRequestExecutor, "_elasticsearchClientResolver",
			elasticsearchClientResolver);

		return stateClusterRequestExecutor;
	}

	private StatsClusterRequestExecutor _createStatsClusterRequestExecutor(
		ClusterHealthStatusTranslator clusterHealthStatusTranslator,
		ElasticsearchClientResolver elasticsearchClientResolver) {

		StatsClusterRequestExecutor statsClusterRequestExecutor =
			new StatsClusterRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			statsClusterRequestExecutor, "_clusterHealthStatusTranslator",
			clusterHealthStatusTranslator);
		ReflectionTestUtil.setFieldValue(
			statsClusterRequestExecutor, "_elasticsearchClientResolver",
			elasticsearchClientResolver);
		ReflectionTestUtil.setFieldValue(
			statsClusterRequestExecutor, "_jsonFactory", new JSONFactoryImpl());

		return statsClusterRequestExecutor;
	}

	private ClusterRequestExecutor _clusterRequestExecutor;
	private ElasticsearchClientResolver _elasticsearchClientResolver;

}