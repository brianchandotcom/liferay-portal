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

package com.liferay.portal.kernel.search.queue;

import com.liferay.portal.kernel.search.BaseSearchEngine;
import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.dummy.DummyIndexSearcher;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.QueuingInvocationHandler;

/**
 * @author Michael C. Han
 */
public class QueuingSearchEngine extends BaseSearchEngine {

	public QueuingSearchEngine(int capacity) {
		_queuingInvocationHandler = new QueuingInvocationHandler(capacity);

		Class<?> clazz = getClass();

		_indexWriter = (IndexWriter)ProxyUtil.newProxyInstance(
			clazz.getClassLoader(), new Class<?>[] {IndexWriter.class},
			_queuingInvocationHandler);
	}

	public void flush() {
		_queuingInvocationHandler.flush();
	}

	@Override
	public IndexSearcher getIndexSearcher() {
		return _indexSearcher;
	}

	@Override
	public IndexWriter getIndexWriter() {
		return _indexWriter;
	}

	public void invokeQueued(IndexWriter indexWriter) throws Exception {
		_queuingInvocationHandler.invokeQueued(indexWriter);
	}

	private final IndexSearcher _indexSearcher = new DummyIndexSearcher();
	private final IndexWriter _indexWriter;
	private final QueuingInvocationHandler _queuingInvocationHandler;

}