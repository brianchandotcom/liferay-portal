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

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.solr7.configuration.SolrConfiguration;
import com.liferay.portal.search.solr7.internal.http.HttpClientFactory;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 * @author Tibor Lipusz
 */
@Component(
	immediate = true, property = "type=CLOUD", service = SolrClientFactory.class
)
public class CloudSolrClientFactory implements SolrClientFactory {

	@Override
	public SolrClient getSolrClient(
			SolrConfiguration solrConfiguration,
			HttpClientFactory httpClientFactory)
		throws Exception {

		String defaultCollection = solrConfiguration.defaultCollection();

		if (Validator.isNull(defaultCollection)) {
			throw new IllegalStateException("Default collection is null");
		}

		String zkHost = solrConfiguration.zkHost();

		if (Validator.isNull(zkHost)) {
			throw new IllegalStateException("Zookeeper host is null");
		}

		CloudSolrClient.Builder builder = new CloudSolrClient.Builder();

		builder.withHttpClient(httpClientFactory.createInstance());
		builder.withZkHost(zkHost);

		CloudSolrClient cloudSolrClient = builder.build();

		cloudSolrClient.setDefaultCollection(defaultCollection);

		return cloudSolrClient;
	}

}