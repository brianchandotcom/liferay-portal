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

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.document;

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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 */
@Component(
	immediate = true, property = "search.engine.impl=Elasticsearch",
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

	@Reference(unbind = "-")
	protected void setBulkDocumentRequestExecutor(
		BulkDocumentRequestExecutor bulkDocumentRequestExecutor) {

		_bulkDocumentRequestExecutor = bulkDocumentRequestExecutor;
	}

	@Reference(unbind = "-")
	protected void setDeleteByQueryDocumentRequestExecutor(
		DeleteByQueryDocumentRequestExecutor
			deleteByQueryDocumentRequestExecutor) {

		_deleteByQueryDocumentRequestExecutor =
			deleteByQueryDocumentRequestExecutor;
	}

	@Reference(unbind = "-")
	protected void setDeleteDocumentRequestExecutor(
		DeleteDocumentRequestExecutor deleteDocumentRequestExecutor) {

		_deleteDocumentRequestExecutor = deleteDocumentRequestExecutor;
	}

	@Reference(unbind = "-")
	protected void setGetDocumentRequestExecutor(
		GetDocumentRequestExecutor getDocumentRequestExecutor) {

		_getDocumentRequestExecutor = getDocumentRequestExecutor;
	}

	@Reference(unbind = "-")
	protected void setIndexDocumentRequestExecutor(
		IndexDocumentRequestExecutor indexDocumentRequestExecutor) {

		_indexDocumentRequestExecutor = indexDocumentRequestExecutor;
	}

	@Reference(unbind = "-")
	protected void setUpdateByQueryDocumentRequestExecutor(
		UpdateByQueryDocumentRequestExecutor
			updateByQueryDocumentRequestExecutor) {

		_updateByQueryDocumentRequestExecutor =
			updateByQueryDocumentRequestExecutor;
	}

	@Reference(unbind = "-")
	protected void setUpdateDocumentRequestExecutor(
		UpdateDocumentRequestExecutor updateDocumentRequestExecutor) {

		_updateDocumentRequestExecutor = updateDocumentRequestExecutor;
	}

	private BulkDocumentRequestExecutor _bulkDocumentRequestExecutor;
	private DeleteByQueryDocumentRequestExecutor
		_deleteByQueryDocumentRequestExecutor;
	private DeleteDocumentRequestExecutor _deleteDocumentRequestExecutor;
	private GetDocumentRequestExecutor _getDocumentRequestExecutor;
	private IndexDocumentRequestExecutor _indexDocumentRequestExecutor;
	private UpdateByQueryDocumentRequestExecutor
		_updateByQueryDocumentRequestExecutor;
	private UpdateDocumentRequestExecutor _updateDocumentRequestExecutor;

}