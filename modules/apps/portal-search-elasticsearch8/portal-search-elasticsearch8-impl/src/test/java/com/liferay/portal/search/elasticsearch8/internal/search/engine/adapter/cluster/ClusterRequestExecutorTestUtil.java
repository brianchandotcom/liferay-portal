/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.cluster;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.cluster.ClusterRequestExecutor;

/**
 * @author Shuyang Zhou
 */
public class ClusterRequestExecutorTestUtil {

	public static ClusterRequestExecutor createClusterRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		ClusterRequestExecutor clusterRequestExecutor =
			new ElasticsearchClusterRequestExecutor();

		ReflectionTestUtil.setFieldValue(
			clusterRequestExecutor, "_elasticsearchClientResolver",
			elasticsearchClientResolver);
		ReflectionTestUtil.setFieldValue(
			clusterRequestExecutor, "_jsonFactory", new JSONFactoryImpl());

		ReflectionTestUtil.invoke(
			clusterRequestExecutor, "activate", new Class<?>[0]);

		return clusterRequestExecutor;
	}

}