/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.spring.aop.AnnotationChainableMethodAdvice;
import com.liferay.portal.spring.aop.ServiceBeanAopProxy;

import java.lang.annotation.Annotation;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Shuyang Zhou
 */
public class IndexableAdvice
	extends AnnotationChainableMethodAdvice<Indexable> {

	@Override
	public void afterReturning(MethodInvocation methodInvocation, Object result)
		throws Throwable {

		if (result == null) {
			return;
		}

		Indexable indexable = findAnnotation(methodInvocation);

		if (indexable == _nullIndexable) {
			return;
		}

		Class<?> returnType = methodInvocation.getMethod().getReturnType();

		if (!BaseModel.class.isAssignableFrom(returnType)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					methodInvocation +
					" does not have an Indexable return type.");
			}

			return;
		}

		Indexer indexer = IndexerRegistryUtil.getIndexer(returnType.getName());

		if (indexer != null) {
			if (indexable.type() == IndexableType.DELETE) {
				indexer.delete(result);
			}
			else {
				indexer.reindex(result);
			}
		}
		else {
			ServiceBeanAopProxy.removeMethodInterceptor(methodInvocation, this);
		}
	}

	@Override
	public Indexable getNullAnnotation() {
		return _nullIndexable;
	}

	private static Log _log = LogFactoryUtil.getLog(IndexableAdvice.class);

	private static Indexable _nullIndexable =
		new Indexable() {

			public Class<? extends Annotation> annotationType() {
				return Indexable.class;
			}

			public IndexableType type() {
				return null;
			}

		};

}