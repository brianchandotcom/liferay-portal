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

import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.document.DeleteByQueryDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateByQueryDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Dylan Rebelak
 */
public class ElasticsearchDocumentRequestExecutorTest {

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		_elasticsearchDocumentRequestExecutor =
			new ElasticsearchDocumentRequestExecutor() {
				{
					setDeleteByQueryDocumentRequestExecutor(
						_deleteByQueryDocumentRequestExecutor);
					setDeleteDocumentRequestExecutor(
						_deleteDocumentRequestExecutor);
					setIndexDocumentRequestExecutor(
						_indexDocumentRequestExecutor);
					setUpdateByQueryDocumentRequestExecutor(
						_updateByQueryDocumentRequestExecutor);
					setUpdateDocumentRequestExecutor(
						_updateDocumentRequestExecutor);
				}
			};
	}

	@Test
	public void testExecuteDeleteByQueryDocumentRequest() {
		DeleteByQueryDocumentRequest deleteByQueryDocumentRequest =
			new DeleteByQueryDocumentRequest(null);

		_elasticsearchDocumentRequestExecutor.executeDocumentRequest(
			deleteByQueryDocumentRequest);

		Mockito.verify(
			_deleteByQueryDocumentRequestExecutor
		).execute(
			deleteByQueryDocumentRequest
		);
	}

	@Test
	public void testExecuteDeleteDocumentRequest() {
		DeleteDocumentRequest deleteDocumentRequest = new DeleteDocumentRequest(
			null, null);

		_elasticsearchDocumentRequestExecutor.executeDocumentRequest(
			deleteDocumentRequest);

		Mockito.verify(
			_deleteDocumentRequestExecutor
		).execute(
			deleteDocumentRequest
		);
	}

	@Test
	public void testExecuteIndexDocumentRequest() {
		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			null, (Document)null);

		_elasticsearchDocumentRequestExecutor.executeDocumentRequest(
			indexDocumentRequest);

		Mockito.verify(
			_indexDocumentRequestExecutor
		).execute(
			indexDocumentRequest
		);
	}

	@Test
	public void testExecuteUpdateByQueryDocumentRequest() {
		UpdateByQueryDocumentRequest updateByQueryDocumentRequest =
			new UpdateByQueryDocumentRequest(null, null);

		_elasticsearchDocumentRequestExecutor.executeDocumentRequest(
			updateByQueryDocumentRequest);

		Mockito.verify(
			_updateByQueryDocumentRequestExecutor
		).execute(
			updateByQueryDocumentRequest
		);
	}

	@Test
	public void testExecuteUpdateDocumentRequest() {
		UpdateDocumentRequest updateDocumentRequest = new UpdateDocumentRequest(
			null, null, (Document)null);

		_elasticsearchDocumentRequestExecutor.executeDocumentRequest(
			updateDocumentRequest);

		Mockito.verify(
			_updateDocumentRequestExecutor
		).execute(
			updateDocumentRequest
		);
	}

	@Mock
	private DeleteByQueryDocumentRequestExecutor
		_deleteByQueryDocumentRequestExecutor;

	@Mock
	private DeleteDocumentRequestExecutor _deleteDocumentRequestExecutor;

	private ElasticsearchDocumentRequestExecutor
		_elasticsearchDocumentRequestExecutor;

	@Mock
	private IndexDocumentRequestExecutor _indexDocumentRequestExecutor;

	@Mock
	private UpdateByQueryDocumentRequestExecutor
		_updateByQueryDocumentRequestExecutor;

	@Mock
	private UpdateDocumentRequestExecutor _updateDocumentRequestExecutor;

}