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

import com.liferay.portal.search.buffer.IndexerRequestBuffer;
import com.liferay.portal.search.buffer.IndexerRequestBufferExecutor;
import com.liferay.portal.search.buffer.IndexerRequestBufferOverflowHandler;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true, property = {"mode=DEFAULT"},
	service = IndexerRequestBufferOverflowHandler.class
)
public class DefaultIndexerRequestBufferOverflowHandler
	implements IndexerRequestBufferOverflowHandler {

	@Override
	public void bufferOverflowed(
		IndexerRequestBuffer indexerRequestBuffer, int maxBufferSize) {

		int numRequests = indexerRequestBuffer.size() - maxBufferSize;

		if (numRequests > 0) {
			IndexerRequestBufferExecutor indexerRequestBufferExecutor =
				_indexerRequestBufferExecutorWatcher.
					getIndexerRequestBufferExecutor();

			indexerRequestBufferExecutor.execute(
				indexerRequestBuffer, numRequests);
		}
	}

	@Reference
	private IndexerRequestBufferExecutorWatcher
		_indexerRequestBufferExecutorWatcher;

}