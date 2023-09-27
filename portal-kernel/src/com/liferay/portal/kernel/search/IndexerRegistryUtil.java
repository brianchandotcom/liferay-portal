/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.List;
import java.util.Set;

/**
 * @author Raymond Augé
 */
public class IndexerRegistryUtil {

	public static <T> Indexer<T> getIndexer(Class<T> clazz) {
		return _indexerRegistry.getIndexer(clazz);
	}

	public static <T> Indexer<T> getIndexer(String className) {
		return _indexerRegistry.getIndexer(className);
	}

	public static List<IndexerPostProcessor> getIndexerPostProcessors(
		Indexer<?> indexer) {

		return _indexerRegistry.getIndexerPostProcessors(indexer);
	}

	public static List<IndexerPostProcessor> getIndexerPostProcessors(
		String className) {

		return _indexerRegistry.getIndexerPostProcessors(className);
	}

	public static Set<Indexer<?>> getIndexers() {
		return _indexerRegistry.getIndexers();
	}

	public static <T> Indexer<T> nullSafeGetIndexer(Class<T> clazz) {
		return _indexerRegistry.nullSafeGetIndexer(clazz);
	}

	public static <T> Indexer<T> nullSafeGetIndexer(String className) {
		return _indexerRegistry.nullSafeGetIndexer(className);
	}

	private static volatile IndexerRegistry _indexerRegistry =
		ServiceProxyFactory.newServiceTrackedInstance(
			IndexerRegistry.class, IndexerRegistryUtil.class,
			"_indexerRegistry", false);

}