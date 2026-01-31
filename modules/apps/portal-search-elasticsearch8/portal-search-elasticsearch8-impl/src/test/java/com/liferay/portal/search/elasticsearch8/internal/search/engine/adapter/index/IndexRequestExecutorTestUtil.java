/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.index;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.index.IndexRequestExecutor;

/**
 * @author Shuyang Zhou
 */
public class IndexRequestExecutorTestUtil {

	public static IndexRequestExecutor createIndexRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		IndexRequestExecutor indexRequestExecutor =
			new ElasticsearchIndexRequestExecutor();

		ReflectionTestUtil.setFieldValue(
			indexRequestExecutor, "_elasticsearchClientResolver",
			elasticsearchClientResolver);
		ReflectionTestUtil.setFieldValue(
			indexRequestExecutor, "_jsonFactory", new JSONFactoryImpl());

		ReflectionTestUtil.invoke(
			indexRequestExecutor, "activate", new Class<?>[0]);

		return indexRequestExecutor;
	}

}