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

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.util.List;

/**
 * @author Raymond Augé
 */
public class IndexerRegistryUtil {

	public static Indexer getIndexer(Class<?> clazz) {
		return getIndexerRegistry().getIndexer(clazz);
	}

	public static Indexer getIndexer(String className) {
		return getIndexerRegistry().getIndexer(className);
	}

	public static IndexerRegistry getIndexerRegistry() {
		return _instance._serviceTracker.getService();
	}

	public static List<Indexer> getIndexers() {
		return getIndexerRegistry().getIndexers();
	}

	public static Indexer nullSafeGetIndexer(Class<?> clazz) {
		return getIndexerRegistry().nullSafeGetIndexer(clazz);
	}

	public static Indexer nullSafeGetIndexer(String className) {
		return getIndexerRegistry().nullSafeGetIndexer(className);
	}

	public static void register(Indexer indexer) {
		getIndexerRegistry().register(indexer);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #register(Indexer)}
	 */
	@Deprecated
	public static void register(String className, Indexer indexer) {
		getIndexerRegistry().register(indexer);
	}

	public static void unregister(Indexer indexer) {
		getIndexerRegistry().unregister(indexer);
	}

	public static void unregister(String className) {
		getIndexerRegistry().unregister(className);
	}

	private IndexerRegistryUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(IndexerRegistry.class);

		_serviceTracker.open();
	}

	private static final IndexerRegistryUtil _instance =
		new IndexerRegistryUtil();

	private final ServiceTracker<IndexerRegistry, IndexerRegistry>
		_serviceTracker;

}