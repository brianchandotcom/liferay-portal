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

package com.liferay.portal.spring.aop;

import com.liferay.petra.lang.HashUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public abstract class AnnotationChainableMethodAdvice<T extends Annotation>
	extends ChainableMethodAdvice {

	public AnnotationChainableMethodAdvice(Class<T> annotationClass) {
		_annotationClass = Objects.requireNonNull(annotationClass);
	}

	public Class<? extends Annotation> getAnnotationClass() {
		return _annotationClass;
	}

	@Override
	public boolean isEnabled(Class<?> targetClass, Method method) {
		T annotation = serviceBeanAopCacheManager.findAnnotation(
			targetClass, method, _annotationClass);

		if (annotation == null) {
			return false;
		}

		_annotations.put(new CacheKey(targetClass, method), annotation);

		return true;
	}

	protected T findAnnotation(
		ServiceBeanMethodInvocation serviceBeanMethodInvocation) {

		Object target = serviceBeanMethodInvocation.getThis();

		return _annotations.get(
			new CacheKey(
				target.getClass(), serviceBeanMethodInvocation.getMethod()));
	}

	private final Class<? extends Annotation> _annotationClass;
	private final ConcurrentMap<CacheKey, T> _annotations =
		new ConcurrentHashMap<>();

	private static class CacheKey {

		@Override
		public boolean equals(Object obj) {
			CacheKey cacheKey = (CacheKey)obj;

			if (Objects.equals(_targetClass, cacheKey._targetClass) &&
				Objects.equals(_method, cacheKey._method)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hash = HashUtil.hash(0, _targetClass);

			return HashUtil.hash(hash, _method);
		}

		private CacheKey(Class<?> targetClass, Method method) {
			_targetClass = targetClass;
			_method = method;
		}

		private final Method _method;
		private final Class<?> _targetClass;

	}

}