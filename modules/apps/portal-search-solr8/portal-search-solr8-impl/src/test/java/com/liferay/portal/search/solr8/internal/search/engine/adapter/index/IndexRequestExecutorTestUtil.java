/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.search.engine.adapter.index;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.engine.adapter.index.IndexRequestExecutor;
import com.liferay.portal.search.solr8.internal.connection.SolrClientManager;

/**
 * @author Shuyang Zhou
 */
public class IndexRequestExecutorTestUtil {

	public static IndexRequestExecutor createIndexRequestExecutor(
		SolrClientManager solrClientManager) {

		IndexRequestExecutor indexRequestExecutor =
			new SolrIndexRequestExecutor();

		ReflectionTestUtil.setFieldValue(
			indexRequestExecutor, "_solrClientManager", solrClientManager);

		ReflectionTestUtil.invoke(
			indexRequestExecutor, "activate", new Class<?>[0]);

		return indexRequestExecutor;
	}

}