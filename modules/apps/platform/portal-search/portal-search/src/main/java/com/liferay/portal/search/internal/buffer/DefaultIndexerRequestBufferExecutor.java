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

import com.liferay.portal.search.buffer.IndexerRequest;
import com.liferay.portal.search.buffer.IndexerRequestBuffer;
import com.liferay.portal.search.buffer.IndexerRequestBufferExecutor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true, property = {"buffered.execution.mode=DEFAULT"},
	service = IndexerRequestBufferExecutor.class
)
public class DefaultIndexerRequestBufferExecutor
	extends BaseIndexerRequestBufferExecutor
	implements IndexerRequestBufferExecutor {

	public void execute(
		IndexerRequestBuffer indexerRequestBuffer, int numRequests) {

		Set<String> searchEngineIds = new HashSet<>();

		Collection<IndexerRequest> completedIndexerRequests = new ArrayList<>();

		for (IndexerRequest indexerRequest :
				indexerRequestBuffer.getIndexerRequests()) {

			executeIndexerRequest(searchEngineIds, indexerRequest);

			completedIndexerRequests.add(indexerRequest);

			if (completedIndexerRequests.size() == numRequests) {
				break;
			}
		}

		for (IndexerRequest indexerRequest : completedIndexerRequests) {
			indexerRequestBuffer.remove(indexerRequest);
		}

		commit(searchEngineIds);
	}

}