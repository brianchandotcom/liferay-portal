/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.module.service.Snapshot;

import java.util.List;
import java.util.Set;

/**
 * @author Raymond Augé
 */
public class IndexerRegistryUtil {

	public static <T> Indexer<T> getIndexer(Class<T> clazz) {
		IndexerRegistry indexerRegistry = _indexerRegistrySnapshot.get();

		return indexerRegistry.getIndexer(clazz);
	}

	public static <T> Indexer<T> getIndexer(String className) {
		IndexerRegistry indexerRegistry = _indexerRegistrySnapshot.get();

		return indexerRegistry.getIndexer(className);
	}

	public static List<IndexerPostProcessor> getIndexerPostProcessors(
		Indexer<?> indexer) {

		IndexerRegistry indexerRegistry = _indexerRegistrySnapshot.get();

		return indexerRegistry.getIndexerPostProcessors(indexer);
	}

	public static List<IndexerPostProcessor> getIndexerPostProcessors(
		String className) {

		IndexerRegistry indexerRegistry = _indexerRegistrySnapshot.get();

		return indexerRegistry.getIndexerPostProcessors(className);
	}

	public static Set<Indexer<?>> getIndexers() {
		IndexerRegistry indexerRegistry = _indexerRegistrySnapshot.get();

		return indexerRegistry.getIndexers();
	}

	public static <T> Indexer<T> nullSafeGetIndexer(Class<T> clazz) {
		IndexerRegistry indexerRegistry = _indexerRegistrySnapshot.get();

		return indexerRegistry.nullSafeGetIndexer(clazz);
	}

	public static <T> Indexer<T> nullSafeGetIndexer(String className) {
		IndexerRegistry indexerRegistry = _indexerRegistrySnapshot.get();

		return indexerRegistry.nullSafeGetIndexer(className);
	}

	private static final Snapshot<IndexerRegistry> _indexerRegistrySnapshot =
		new Snapshot<>(IndexerRegistryUtil.class, IndexerRegistry.class);

}