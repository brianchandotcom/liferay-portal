/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.search.engine.adapter.document;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.DeleteByQueryDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteByQueryDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.DocumentRequestExecutor;
import com.liferay.portal.search.engine.adapter.document.GetDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.GetDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.UpdateByQueryDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateByQueryDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentResponse;
import com.liferay.portal.search.solr8.configuration.SolrConfiguration;
import com.liferay.portal.search.solr8.internal.connection.SolrClientManager;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(
	configurationPid = "com.liferay.portal.search.solr8.configuration.SolrConfiguration",
	property = "search.engine.impl=Solr",
	service = DocumentRequestExecutor.class
)
public class SolrDocumentRequestExecutor implements DocumentRequestExecutor {

	@Override
	public BulkDocumentResponse executeBulkDocumentRequest(
		BulkDocumentRequest bulkDocumentRequest) {

		return _bulkDocumentRequestExecutor.execute(bulkDocumentRequest);
	}

	@Override
	public DeleteByQueryDocumentResponse executeDocumentRequest(
		DeleteByQueryDocumentRequest deleteByQueryDocumentRequest) {

		return _deleteByQueryDocumentRequestExecutor.execute(
			deleteByQueryDocumentRequest);
	}

	@Override
	public DeleteDocumentResponse executeDocumentRequest(
		DeleteDocumentRequest deleteDocumentRequest) {

		return _deleteDocumentRequestExecutor.execute(deleteDocumentRequest);
	}

	@Override
	public GetDocumentResponse executeDocumentRequest(
		GetDocumentRequest getDocumentRequest) {

		return _getDocumentRequestExecutor.execute(getDocumentRequest);
	}

	@Override
	public IndexDocumentResponse executeDocumentRequest(
		IndexDocumentRequest indexDocumentRequest) {

		return _indexDocumentRequestExecutor.execute(indexDocumentRequest);
	}

	@Override
	public UpdateByQueryDocumentResponse executeDocumentRequest(
		UpdateByQueryDocumentRequest updateByQueryDocumentRequest) {

		return _updateByQueryDocumentRequestExecutor.execute(
			updateByQueryDocumentRequest);
	}

	@Override
	public UpdateDocumentResponse executeDocumentRequest(
		UpdateDocumentRequest updateDocumentRequest) {

		return _updateDocumentRequestExecutor.execute(updateDocumentRequest);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		modified(properties);

		_deleteDocumentRequestExecutor = new DeleteDocumentRequestExecutor(
			_solrClientManager);
		_getDocumentRequestExecutor = new GetDocumentRequestExecutor(
			_solrClientManager);
		_indexDocumentRequestExecutor = new IndexDocumentRequestExecutor(
			_solrClientManager);
		_updateByQueryDocumentRequestExecutor =
			new UpdateByQueryDocumentRequestExecutor();
		_updateDocumentRequestExecutor = new UpdateDocumentRequestExecutor(
			_solrClientManager);
	}

	@Modified
	protected void modified(Map<String, Object> properties) {
		SolrConfiguration solrConfiguration =
			ConfigurableUtil.createConfigurable(
				SolrConfiguration.class, properties);

		String defaultCollection = solrConfiguration.defaultCollection();

		_deleteByQueryDocumentRequestExecutor =
			new DeleteByQueryDocumentRequestExecutor(
				defaultCollection, _solrClientManager);
		_bulkDocumentRequestExecutor = new BulkDocumentRequestExecutor(
			defaultCollection, _solrClientManager);
	}

	private volatile BulkDocumentRequestExecutor _bulkDocumentRequestExecutor;
	private volatile DeleteByQueryDocumentRequestExecutor
		_deleteByQueryDocumentRequestExecutor;
	private DeleteDocumentRequestExecutor _deleteDocumentRequestExecutor;
	private GetDocumentRequestExecutor _getDocumentRequestExecutor;
	private IndexDocumentRequestExecutor _indexDocumentRequestExecutor;

	@Reference
	private SolrClientManager _solrClientManager;

	private UpdateByQueryDocumentRequestExecutor
		_updateByQueryDocumentRequestExecutor;
	private UpdateDocumentRequestExecutor _updateDocumentRequestExecutor;

}