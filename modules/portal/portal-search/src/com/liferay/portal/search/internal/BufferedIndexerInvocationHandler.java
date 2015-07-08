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

package com.liferay.portal.search.internal;

import com.liferay.portal.kernel.search.Bufferable;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.search.configuration.IndexerRegistryConfiguration;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Michael C. Han
 */
public class BufferedIndexerInvocationHandler implements InvocationHandler {

	public BufferedIndexerInvocationHandler(
		Indexer indexer,
		IndexerRegistryConfiguration indexerRegistryConfiguration) {

		_indexer = indexer;
		_indexerRegistryConfiguration = indexerRegistryConfiguration;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
		throws Throwable {

		Annotation annotation = method.getAnnotation(Bufferable.class);

		IndexerRequestBuffer indexerRequestBuffer = IndexerRequestBuffer.get();

		if ((annotation == null) || (args.length != 1) ||
			(indexerRequestBuffer == null)) {

			return method.invoke(_indexer, args);
		}

		if (!(args[0] instanceof BaseModel) &&
			!(args[0] instanceof ClassedModel) &&
			!(args[0].getClass().isArray() ||
				args[0].getClass().equals(Collection.class))) {

			return method.invoke(_indexer, args);
		}

		MethodKey methodKey = new MethodKey(
			Indexer.class, method.getName(), Object.class);

		if (args[0] instanceof ClassedModel) {
			bufferRequest(methodKey, args[0], indexerRequestBuffer);
		}
		else {
			Collection objects = null;

			if (args[0].getClass().isArray()) {
				objects = Arrays.asList((Object[])args[0]);
			}
			else {
				objects = (Collection)args[0];
			}

			for (Object object : objects) {
				if (!(object instanceof ClassedModel)) {
					return method.invoke(_indexer, args);
				}

				bufferRequest(methodKey, object, indexerRequestBuffer);
			}
		}

		return null;
	}

	public void setIndexerRegistryConfiguration(
		IndexerRegistryConfiguration indexerRegistryConfiguration) {

		_indexerRegistryConfiguration = indexerRegistryConfiguration;
	}

	protected void bufferRequest(
			MethodKey methodKey, Object object,
			IndexerRequestBuffer indexerRequestBuffer)
		throws Exception {

		BaseModel baseModel = (BaseModel)object;

		ClassedModel classModel = (ClassedModel)baseModel.clone();

		IndexerRequest indexerRequest = new IndexerRequest(
			methodKey.getMethod(), classModel, _indexer);

		indexerRequestBuffer.add(indexerRequest);

		int numRequests =
			indexerRequestBuffer.size() -
				_indexerRegistryConfiguration.maxBufferSize();

		if (numRequests > 0) {
			indexerRequestBuffer.execute(numRequests);
		}
	}

	private final Indexer _indexer;
	private volatile IndexerRegistryConfiguration _indexerRegistryConfiguration;

}