/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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