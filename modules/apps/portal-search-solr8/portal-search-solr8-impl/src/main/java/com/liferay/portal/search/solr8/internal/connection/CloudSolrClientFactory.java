/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.connection;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.solr8.configuration.SolrConfiguration;
import com.liferay.portal.search.solr8.internal.http.HttpClientFactory;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 * @author Tibor Lipusz
 */
@Component(property = "type=CLOUD", service = SolrClientFactory.class)
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