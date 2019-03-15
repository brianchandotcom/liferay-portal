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

package com.liferay.portal.search;

import com.liferay.portal.kernel.aop.AopMethodInvocation;
import com.liferay.portal.kernel.aop.ChainableMethodAdvice;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.search.IndexWriterHelperUtil;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class IndexableAdvice extends ChainableMethodAdvice {

	@Override
	public Object createMethodContext(
		Class<?> targetClass, Method method,
		Map<Class<? extends Annotation>, Annotation> annotations) {

		Indexable indexable = (Indexable)annotations.get(Indexable.class);

		if (indexable == null) {
			return null;
		}

		Class<?> returnType = method.getReturnType();

		if (!BaseModel.class.isAssignableFrom(returnType)) {
			if (_log.isWarnEnabled()) {
				_log.warn(method + " does not have a valid return type");
			}

			return null;
		}

		return new IndexableContext(
			returnType.getName(), indexable.type(),
			_getServiceContextParameterIndex(method));
	}

	@Override
	protected void afterReturning(
			AopMethodInvocation aopMethodInvocation, Object[] arguments,
			Object result)
		throws Throwable {

		if (result == null) {
			return;
		}

		if (CompanyThreadLocal.isDeleteInProcess() ||
			IndexWriterHelperUtil.isIndexReadOnly()) {

			if (_log.isDebugEnabled()) {
				if (CompanyThreadLocal.isDeleteInProcess()) {
					_log.debug(
						"Skip indexing because company delete is in process");
				}
				else if (IndexWriterHelperUtil.isIndexReadOnly()) {
					_log.debug("Skip indexing because the index is read only");
				}
			}

			return;
		}

		IndexableContext indexableContext =
			aopMethodInvocation.getAdviceMethodContext();

		String name = indexableContext._name;

		Indexer<Object> indexer = IndexerRegistryUtil.getIndexer(name);

		if (indexer == null) {
			return;
		}

		if (IndexWriterHelperUtil.isIndexReadOnly(indexer.getClassName())) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Skipping indexing read only index for " +
						indexer.getClassName());
			}

			return;
		}

		int serviceContextIndex = indexableContext._serviceContextIndex;

		if (serviceContextIndex >= 0) {
			ServiceContext serviceContext =
				(ServiceContext)arguments[serviceContextIndex];

			if ((serviceContext != null) &&
				!serviceContext.isIndexingEnabled()) {

				return;
			}
		}

		if (indexableContext._indexableType == IndexableType.DELETE) {
			indexer.delete(result);
		}
		else {
			indexer.reindex(result);
		}
	}

	private int _getServiceContextParameterIndex(Method method) {
		Class<?>[] parameterTypes = method.getParameterTypes();

		for (int i = parameterTypes.length - 1; i >= 0; i--) {
			if (ServiceContext.class.isAssignableFrom(parameterTypes[i])) {
				return i;
			}
		}

		return -1;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexableAdvice.class);

	private static class IndexableContext {

		private IndexableContext(
			String name, IndexableType indexableType, int serviceContextIndex) {

			_name = name;
			_indexableType = indexableType;
			_serviceContextIndex = serviceContextIndex;
		}

		private final IndexableType _indexableType;
		private final String _name;
		private final int _serviceContextIndex;

	}

}