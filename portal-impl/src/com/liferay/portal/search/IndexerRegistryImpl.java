/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.search;

import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Raymond Augé
 */
public class IndexerRegistryImpl implements IndexerRegistry {

	public Indexer getIndexer(String className) {
		return _indexers.get(className);
	}

	public List<Indexer> getIndexers() {
		return ListUtil.fromMapValues(_indexers);
	}

	public void register(String className, Indexer indexerInstance) {
		_indexers.put(className, indexerInstance);
	}

	public void unregister(String className) {
		_indexers.remove(className);
	}

	private Map<String, Indexer> _indexers =
		new ConcurrentHashMap<String, Indexer>();

}