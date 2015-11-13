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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.search.IndexerRequestBufferOverflowHandler;
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
		Indexer<?> indexer,
		IndexerRegistryConfiguration indexerRegistryConfiguration) {

		_indexer = indexer;
		_indexerRegistryConfiguration = indexerRegistryConfiguration;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
		throws Throwable {

		Annotation annotation = method.getAnnotation(Bufferable.class);

		IndexerRequestBuffer indexerRequestBuffer = IndexerRequestBuffer.get();

		if ((annotation == null) || (args.length == 0) || (args.length > 2) ||
			(indexerRequestBuffer == null)) {

			return method.invoke(_indexer, args);
		}

		Class<?> args0Class = args[0].getClass();

		if (!(args[0] instanceof BaseModel) &&
			!(args[0] instanceof ClassedModel) &&
			!(args0Class.isArray() ||
			  Collection.class.isAssignableFrom(args0Class)) &&
			!((args.length == 2) && (args[0] instanceof String) &&
			  Validator.equals(args[1].getClass(), Long.class))) {

			return method.invoke(_indexer, args);
		}

		if (args[0] instanceof ClassedModel) {
			MethodKey methodKey = new MethodKey(
				Indexer.class, method.getName(), Object.class);

			bufferRequest(methodKey, args[0], indexerRequestBuffer);
		}
		else if (args.length == 2) {
			MethodKey methodKey = new MethodKey(
				Indexer.class, method.getName(), String.class, Long.TYPE);

			String className = (String)args[0];
			Long classPK = (Long)args[1];

			bufferRequest(methodKey, className, classPK, indexerRequestBuffer);
		}
		else {
			MethodKey methodKey = new MethodKey(
				Indexer.class, method.getName(), Object.class);

			Collection<Object> objects = null;

			if (args0Class.isArray()) {
				objects = Arrays.asList((Object[])args[0]);
			}
			else {
				objects = (Collection<Object>)args[0];
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

	public void setIndexerRequestBufferOverflowHandler(
		IndexerRequestBufferOverflowHandler
			indexerRequestBufferOverflowHandler) {

		_indexerRequestBufferOverflowHandler =
			indexerRequestBufferOverflowHandler;
	}

	protected void bufferRequest(
			MethodKey methodKey, Object object,
			IndexerRequestBuffer indexerRequestBuffer)
		throws Exception {

		BaseModel<?> baseModel = (BaseModel<?>)object;

		ClassedModel classedModel = (ClassedModel)baseModel.clone();

		IndexerRequest indexerRequest = new IndexerRequest(
			methodKey.getMethod(), classedModel, _indexer);

		doBufferRequest(indexerRequest, indexerRequestBuffer);
	}

	protected void bufferRequest(
			MethodKey methodKey, String className, Long classPK,
			IndexerRequestBuffer indexerRequestBuffer)
		throws Exception {

		IndexerRequest indexerRequest = new IndexerRequest(
			methodKey.getMethod(), _indexer, className, classPK);

		doBufferRequest(indexerRequest, indexerRequestBuffer);
	}

	protected void doBufferRequest(
			IndexerRequest indexerRequest,
			IndexerRequestBuffer indexerRequestBuffer)
		throws Exception {

		indexerRequestBuffer.add(indexerRequest);

		if (indexerRequestBuffer.size() >
				_indexerRegistryConfiguration.maxBufferSize()) {

			_indexerRequestBufferOverflowHandler.bufferOverflowed(
				indexerRequestBuffer,
				_indexerRegistryConfiguration.maxBufferSize());
		}
	}

	private final Indexer<?> _indexer;
	private volatile IndexerRegistryConfiguration _indexerRegistryConfiguration;
	private volatile IndexerRequestBufferOverflowHandler
		_indexerRequestBufferOverflowHandler;

}