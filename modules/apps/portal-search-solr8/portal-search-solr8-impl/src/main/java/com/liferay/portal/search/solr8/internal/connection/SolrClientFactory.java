/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.connection;

import com.liferay.portal.search.solr8.configuration.SolrConfiguration;
import com.liferay.portal.search.solr8.internal.http.HttpClientFactory;

import org.apache.solr.client.solrj.SolrClient;

/**
 * @author Michael C. Han
 */
public interface SolrClientFactory {

	public SolrClient getSolrClient(
			SolrConfiguration solrConfiguration,
			HttpClientFactory httpClientFactory)
		throws Exception;

}