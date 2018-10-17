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

package com.liferay.portal.search.solr7.internal.connection;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.search.solr7.configuration.SolrConfiguration;
import com.liferay.portal.search.solr7.internal.http.BasicAuthPoolingHttpClientFactory;

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

		SolrConfiguration solrConfiguration =
			ConfigurableUtil.createConfigurable(
				SolrConfiguration.class, configurationProperties);

		setHttpClientFactory(
			httpClientFactory,
			Collections.singletonMap(
				"type", (Object)solrConfiguration.authenticationMode()));
		setSolrClientFactory(
			new ReplicatedSolrClientFactory(),
			Collections.singletonMap(
				"type", (Object)solrConfiguration.clientType()));

		activate(configurationProperties);
	}

}