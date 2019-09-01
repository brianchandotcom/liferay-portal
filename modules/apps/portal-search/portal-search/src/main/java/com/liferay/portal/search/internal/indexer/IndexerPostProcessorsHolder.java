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

package com.liferay.portal.search.internal.indexer;

import com.liferay.portal.kernel.search.IndexerPostProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author André de Oliveira
 */
public class IndexerPostProcessorsHolder {

	public void addIndexerPostProcessor(
		IndexerPostProcessor indexerPostProcessor) {

		_indexerPostProcessors.add(indexerPostProcessor);
	}

	public void forEach(Consumer<IndexerPostProcessor> consumer) {
		_indexerPostProcessors.forEach(consumer);
	}

	public void removeIndexerPostProcessor(
		IndexerPostProcessor indexerPostProcessor) {

		_indexerPostProcessors.remove(indexerPostProcessor);
	}

	public IndexerPostProcessor[] toArray() {
		return _indexerPostProcessors.toArray(new IndexerPostProcessor[0]);
	}

	private final List<IndexerPostProcessor> _indexerPostProcessors =
		new ArrayList<>();

}