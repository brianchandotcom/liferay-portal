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

package com.liferay.portal.search.solr.internal.connection;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.solr.configuration.SolrConfiguration;
import com.liferay.portal.search.solr.connection.SolrClientFactory;
import com.liferay.portal.search.solr.http.HttpClientFactory;

import org.apache.http.client.HttpClient;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true, property = {"type=CLOUD"},
	service = SolrClientFactory.class
)
public class CloudSolrClientFactory implements SolrClientFactory {

	@Override
	public SolrClient getSolrClient(
			SolrConfiguration solrConfiguration,
			HttpClientFactory httpClientFactory)
		throws Exception {

		String zkHost = solrConfiguration.zkHost();

		if (Validator.isNull(zkHost)) {
			throw new IllegalStateException("Must configure Zookeeper host");
		}

		HttpClient httpClient = httpClientFactory.createInstance();

		return new CloudSolrClient(zkHost, httpClient);
	}

}