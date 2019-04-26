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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.solr7.configuration.SolrConfiguration;
import com.liferay.portal.search.solr7.internal.http.HttpClientFactory;

import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.LBHttpSolrClient;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.search.solr7.configuration.SolrConfiguration",
	immediate = true, property = "type=REPLICATED",
	service = SolrClientFactory.class
)
public class ReplicatedSolrClientFactory implements SolrClientFactory {

	@Override
	public SolrClient getSolrClient(
			SolrConfiguration solrConfiguration,
			HttpClientFactory httpClientFactory)
		throws Exception {

		String[] readURLs = _removeCollectionPath(solrConfiguration.readURL());
		String[] writeURLs = _removeCollectionPath(
			solrConfiguration.writeURL());

		if (ArrayUtil.isEmpty(writeURLs)) {
			throw new IllegalArgumentException(
				"Must configure at least one write URL");
		}
		else if (ArrayUtil.isEmpty(readURLs)) {
			if (_log.isInfoEnabled()) {
				_log.info("No read URLs configured, using write URLs for read");
			}

			readURLs = writeURLs;
		}

		LBHttpSolrClient.Builder builder = new LBHttpSolrClient.Builder();

		builder.withBaseSolrUrls(readURLs);
		builder.withHttpClient(httpClientFactory.createInstance());

		LBHttpSolrClient readerLBHttpSolrClient = builder.build();

		builder.withBaseSolrUrls(writeURLs);
		builder.withHttpClient(httpClientFactory.createInstance());

		LBHttpSolrClient writerLBHttpSolrClient = builder.build();

		return new ReadWriteSolrClient(
			readerLBHttpSolrClient, writerLBHttpSolrClient);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_solrConfiguration = ConfigurableUtil.createConfigurable(
			SolrConfiguration.class, properties);

		_defaultCollectionPath =
			StringPool.FORWARD_SLASH + _solrConfiguration.defaultCollection();
	}

	private String[] _removeCollectionPath(String[] urls) {
		String[] newUrls = new String[urls.length];

		for (int i = 0; i < urls.length; i++) {
			String url = urls[i];

			if (url.endsWith(_defaultCollectionPath)) {
				newUrls[i] = StringUtil.removeSubstring(
					url, _defaultCollectionPath);
			}
			else {
				newUrls[i] = url;
			}
		}

		return newUrls;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ReplicatedSolrClientFactory.class);

	private String _defaultCollectionPath;
	private volatile SolrConfiguration _solrConfiguration;

}