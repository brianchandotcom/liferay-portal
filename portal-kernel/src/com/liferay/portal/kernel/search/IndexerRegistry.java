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

package com.liferay.portal.kernel.search;

import java.util.List;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public interface IndexerRegistry {

	public <T> Indexer<T> getIndexer(Class<T> clazz);

	public <T> Indexer<T> getIndexer(String className);

	public List<IndexerPostProcessor> getIndexerPostProcessors(
		Indexer<?> indexer);

	public List<IndexerPostProcessor> getIndexerPostProcessors(
		String className);

	public Set<Indexer<?>> getIndexers();

	public <T> Indexer<T> nullSafeGetIndexer(Class<T> clazz);

	public <T> Indexer<T> nullSafeGetIndexer(String className);

}