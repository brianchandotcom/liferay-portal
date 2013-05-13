/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.DummyIndexer;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.spring.aop.ServiceBeanAopCacheManagerUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Raymond Augé
 */
public class IndexerRegistryImpl implements IndexerRegistry {

	@Override
	public Indexer getIndexer(String className) {
		return _indexers.get(className);
	}

	@Override
	public List<Indexer> getIndexers() {
		return ListUtil.fromMapValues(_indexers);
	}

	@Override
	public Indexer nullSafeGetIndexer(String className) {
		Indexer indexer = _indexers.get(className);

		if (indexer != null) {
			return indexer;
		}

		if (_log.isWarnEnabled()) {
			_log.warn("No indexer found for " + className);
		}

		return _dummyIndexer;
	}

	@Override
	public void register(String className, Indexer indexerInstance) {
		_indexers.put(className, indexerInstance);

		ServiceBeanAopCacheManagerUtil.reset();
	}

	@Override
	public void unregister(String className) {
		_indexers.remove(className);
	}

	private static Log _log = LogFactoryUtil.getLog(IndexerRegistryImpl.class);

	private Indexer _dummyIndexer = new DummyIndexer();
	private Map<String, Indexer> _indexers =
		new ConcurrentHashMap<String, Indexer>();

}