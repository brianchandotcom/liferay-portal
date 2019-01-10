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

package com.liferay.portal.search.solr7.internal.search.engine.adapter.document;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.search.query.QueryTranslator;
import com.liferay.portal.search.engine.adapter.document.DeleteByQueryDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteByQueryDocumentResponse;
import com.liferay.portal.search.solr7.configuration.SolrConfiguration;
import com.liferay.portal.search.solr7.internal.connection.SolrClientManager;

import java.util.Map;

import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrException;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(
	configurationPid = "com.liferay.portal.search.solr7.configuration.SolrConfiguration",
	immediate = true, service = DeleteByQueryDocumentRequestExecutor.class
)
public class DeleteByQueryDocumentRequestExecutorImpl
	implements DeleteByQueryDocumentRequestExecutor {

	@Override
	public DeleteByQueryDocumentResponse execute(
		DeleteByQueryDocumentRequest deleteByQueryDocumentRequest) {

		UpdateRequest updateRequest = new UpdateRequest();

		String queryString = _queryTranslator.translate(
			deleteByQueryDocumentRequest.getQuery(), null);

		updateRequest.deleteByQuery(queryString);

		if (deleteByQueryDocumentRequest.isRefresh()) {
			updateRequest.setAction(UpdateRequest.ACTION.COMMIT, true, true);
		}

		try {
			UpdateResponse updateResponse = updateRequest.process(
				_solrClientManager.getSolrClient(), _defaultCollection);

			return new DeleteByQueryDocumentResponse(
				updateResponse.getStatus(), updateResponse.getElapsedTime());
		}
		catch (Exception e) {
			if (e instanceof SolrException) {
				SolrException se = (SolrException)e;

				throw se;
			}

			throw new RuntimeException(e);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_solrConfiguration = ConfigurableUtil.createConfigurable(
			SolrConfiguration.class, properties);

		_defaultCollection = _solrConfiguration.defaultCollection();
	}

	@Reference(target = "(search.engine.impl=Solr)", unbind = "-")
	protected void setQueryTranslator(QueryTranslator<String> queryTranslator) {
		_queryTranslator = queryTranslator;
	}

	@Reference(unbind = "-")
	protected void setSolrClientManager(SolrClientManager solrClientManager) {
		_solrClientManager = solrClientManager;
	}

	private String _defaultCollection;
	private QueryTranslator<String> _queryTranslator;
	private SolrClientManager _solrClientManager;
	private volatile SolrConfiguration _solrConfiguration;

}