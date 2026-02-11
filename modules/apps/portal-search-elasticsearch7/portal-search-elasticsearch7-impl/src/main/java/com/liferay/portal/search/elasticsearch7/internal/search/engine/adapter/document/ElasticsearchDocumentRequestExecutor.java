/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.document;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.document.configuration.BulkDocumentRequestRetryConfiguration;
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

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 */
@Component(
	configurationPid = "com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.document.configuration.BulkDocumentRequestRetryConfiguration",
	property = "search.engine.impl=Elasticsearch",
	service = DocumentRequestExecutor.class
)
public class ElasticsearchDocumentRequestExecutor
	implements DocumentRequestExecutor {

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

		_deleteByQueryDocumentRequestExecutor =
			new DeleteByQueryDocumentRequestExecutor(
				_elasticsearchClientResolver);
		_deleteDocumentRequestExecutor = new DeleteDocumentRequestExecutor(
			_elasticsearchClientResolver);
		_indexDocumentRequestExecutor = new IndexDocumentRequestExecutor(
			_elasticsearchClientResolver);
	}

	@Modified
	protected void modified(Map<String, Object> properties) {
		BulkDocumentRequestRetryConfiguration
			bulkDocumentRequestRetryConfiguration =
				ConfigurableUtil.createConfigurable(
					BulkDocumentRequestRetryConfiguration.class, properties);

		_bulkDocumentRequestExecutor = new BulkDocumentRequestExecutor(
			_elasticsearchClientResolver,
			bulkDocumentRequestRetryConfiguration.numberOfTries(),
			bulkDocumentRequestRetryConfiguration.waitInSeconds());
	}

	private volatile BulkDocumentRequestExecutor _bulkDocumentRequestExecutor;
	private DeleteByQueryDocumentRequestExecutor
		_deleteByQueryDocumentRequestExecutor;
	private DeleteDocumentRequestExecutor _deleteDocumentRequestExecutor;

	@Reference
	private ElasticsearchClientResolver _elasticsearchClientResolver;

	@Reference
	private GetDocumentRequestExecutor _getDocumentRequestExecutor;

	private IndexDocumentRequestExecutor _indexDocumentRequestExecutor;

	@Reference
	private UpdateByQueryDocumentRequestExecutor
		_updateByQueryDocumentRequestExecutor;

	@Reference
	private UpdateDocumentRequestExecutor _updateDocumentRequestExecutor;

}