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
import com.liferay.portal.search.buffer.IndexerRequest;
import com.liferay.portal.search.buffer.IndexerRequestBuffer;
import com.liferay.portal.search.buffer.IndexerRequestBufferOverflowHandler;
import com.liferay.portal.search.configuration.IndexerRegistryConfiguration;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * @author Bryan Engler
 * @author André de Oliveira
 */
public class IndexerRequestBufferHandlerTest {

	public IndexerRequestBufferHandlerTest() throws Exception {
		_method = Indexer.class.getDeclaredMethod(
			"reindex", String.class, long.class);
	}

	@Test
	public void testDeepReindexMustNotOverflow() throws Exception {
		int maxBufferSize = 5;

		_indexerRequestBufferHandler = new IndexerRequestBufferHandler(
			createIndexerRequestBufferOverflowHandler(),
			createIndexerRegistryConfiguration(maxBufferSize));

		_indexerRequestBuffer = IndexerRequestBuffer.create();

		Indexer<?> indexer = createIndexerWithDeepReindex();

		List<IndexerRequest> indexerRequests = createIndexerRequests(
			indexer, maxBufferSize + 3);

		for (IndexerRequest indexerRequest : indexerRequests) {
			_indexerRequestBufferHandler.bufferRequest(
				indexerRequest, _indexerRequestBuffer);
		}
	}

	protected IndexerRegistryConfiguration createIndexerRegistryConfiguration(
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

	protected IndexerRequest createIndexerRequest(Indexer<?> indexer) {
		return new IndexerRequest(
			_method, indexer, RandomTestUtil.randomString(),
			RandomTestUtil.randomLong());
	}

	protected IndexerRequestBufferExecutorWatcher
		createIndexerRequestBufferExecutorWatcher() {

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

	protected IndexerRequestBufferOverflowHandler
		createIndexerRequestBufferOverflowHandler() {

		return new DefaultIndexerRequestBufferOverflowHandler() {
			{
				indexerRequestBufferExecutorWatcher =
					createIndexerRequestBufferExecutorWatcher();
			}
		};
	}

	protected List<IndexerRequest> createIndexerRequests(
		Indexer<?> indexer, int count) {

		List<IndexerRequest> indexerRequests = new ArrayList<>(count);

		for (int i = 0; i < count; i++) {
			indexerRequests.add(createIndexerRequest(indexer));
		}

		return indexerRequests;
	}

	protected Indexer<?> createIndexerWithDeepReindex() throws Exception {
		Indexer<?> indexer = Mockito.mock(Indexer.class);

		Mockito.doAnswer(
			new Answer<Object>() {

				@Override
				public Object answer(InvocationOnMock invocationOnMock)
					throws Exception {

					deepReindex();

					return null;
				}

			}
		).when(
			indexer
		).reindex(
			Mockito.anyString(), Mockito.anyLong()
		);

		return indexer;
	}

	protected void deepReindex() throws Exception {
		IndexerRequest indexerRequest = createIndexerRequest(_indexer);

		_indexerRequestBufferHandler.bufferRequest(
			indexerRequest, _indexerRequestBuffer);
	}

	private final Indexer<?> _indexer = Mockito.mock(Indexer.class);
	private IndexerRequestBuffer _indexerRequestBuffer;
	private IndexerRequestBufferHandler _indexerRequestBufferHandler;
	private final Method _method;

}