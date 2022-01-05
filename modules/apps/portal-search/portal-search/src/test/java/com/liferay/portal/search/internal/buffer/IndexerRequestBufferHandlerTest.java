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

package com.liferay.portal.search.internal.buffer;

import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.configuration.IndexerRegistryConfiguration;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Bryan Engler
 * @author André de Oliveira
 */
public class IndexerRequestBufferHandlerTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	public IndexerRequestBufferHandlerTest() throws Exception {
		_method = Indexer.class.getDeclaredMethod(
			"reindex", String.class, long.class);
	}

	@Test
	public void testDeepReindexMustNotOverflow() throws Exception {
		int maxBufferSize = 5;

		_indexerRequestBufferHandler = new IndexerRequestBufferHandler(
			_createIndexerRequestBufferOverflowHandler(),
			_createIndexerRegistryConfiguration(maxBufferSize));

		_indexerRequestBuffer = IndexerRequestBuffer.create();

		Indexer<?> indexer = _createIndexerWithDeepReindex();

		List<IndexerRequest> indexerRequests = _createIndexerRequests(
			indexer, maxBufferSize + 3);

		for (IndexerRequest indexerRequest : indexerRequests) {
			_indexerRequestBufferHandler.bufferRequest(
				indexerRequest, _indexerRequestBuffer);
		}
	}

	private IndexerRegistryConfiguration _createIndexerRegistryConfiguration(
		int maxBufferSize) {

		IndexerRegistryConfiguration indexerRegistryConfiguration =
			Mockito.mock(IndexerRegistryConfiguration.class);

		Mockito.doReturn(
			maxBufferSize
		).when(
			indexerRegistryConfiguration
		).maxBufferSize();

		return indexerRegistryConfiguration;
	}

	private IndexerRequest _createIndexerRequest(Indexer<?> indexer) {
		return new IndexerRequest(
			_method, indexer, RandomTestUtil.randomString(),
			RandomTestUtil.randomLong());
	}

	private IndexerRequestBufferExecutorWatcher
		_createIndexerRequestBufferExecutorWatcher() {

		IndexerRequestBufferExecutorWatcher
			indexerRequestBufferExecutorWatcher =
				new IndexerRequestBufferExecutorWatcher();

		indexerRequestBufferExecutorWatcher.activate(
			Collections.<String, Object>emptyMap());

		indexerRequestBufferExecutorWatcher.addIndexerRequestBufferExecutor(
			new DefaultIndexerRequestBufferExecutor(),
			Collections.singletonMap(
				"buffered.execution.mode", (Object)"DEFAULT"));

		return indexerRequestBufferExecutorWatcher;
	}

	private IndexerRequestBufferOverflowHandler
		_createIndexerRequestBufferOverflowHandler() {

		return new DefaultIndexerRequestBufferOverflowHandler() {
			{
				indexerRequestBufferExecutorWatcher =
					_createIndexerRequestBufferExecutorWatcher();
			}
		};
	}

	private List<IndexerRequest> _createIndexerRequests(
		Indexer<?> indexer, int count) {

		List<IndexerRequest> indexerRequests = new ArrayList<>(count);

		for (int i = 0; i < count; i++) {
			indexerRequests.add(_createIndexerRequest(indexer));
		}

		return indexerRequests;
	}

	private Indexer<?> _createIndexerWithDeepReindex() throws Exception {
		Indexer<?> indexer = Mockito.mock(Indexer.class);

		Mockito.doAnswer(
			invocationOnMock -> {
				_deepReindex();

				return null;
			}
		).when(
			indexer
		).reindex(
			Mockito.anyString(), Mockito.anyLong()
		);

		return indexer;
	}

	private void _deepReindex() throws Exception {
		IndexerRequest indexerRequest = _createIndexerRequest(_indexer);

		_indexerRequestBufferHandler.bufferRequest(
			indexerRequest, _indexerRequestBuffer);
	}

	private final Indexer<?> _indexer = Mockito.mock(Indexer.class);
	private IndexerRequestBuffer _indexerRequestBuffer;
	private IndexerRequestBufferHandler _indexerRequestBufferHandler;
	private final Method _method;

}