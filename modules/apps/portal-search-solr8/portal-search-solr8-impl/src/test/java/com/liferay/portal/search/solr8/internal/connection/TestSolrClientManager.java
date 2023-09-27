/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.connection;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.solr8.internal.http.BasicAuthPoolingHttpClientFactory;

import java.util.Collections;
import java.util.Map;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class TestSolrClientManager extends SolrClientManager {

	public TestSolrClientManager(Map<String, Object> configurationProperties)
		throws Exception {

		BasicAuthPoolingHttpClientFactory httpClientFactory =
			new BasicAuthPoolingHttpClientFactory() {
				{
					activate(Collections.<String, Object>emptyMap());
				}
			};

		ReflectionTestUtil.setFieldValue(
			this, "_basicAuthPoolingHttpClientFactory", httpClientFactory);

		ReplicatedSolrClientFactory replicatedSolrClientFactory =
			new ReplicatedSolrClientFactory();

		replicatedSolrClientFactory.activate(configurationProperties);

		ReflectionTestUtil.setFieldValue(
			this, "_replicatedSolrClientFactory", replicatedSolrClientFactory);

		activate(configurationProperties);
	}

}