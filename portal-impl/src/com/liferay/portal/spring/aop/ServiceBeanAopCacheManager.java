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
import com.liferay.petra.reflect.AnnotationLocator;
import com.liferay.portal.transaction.TransactionsUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Shuyang Zhou
 */
public class ServiceBeanAopCacheManager {

	public ServiceBeanAopCacheManager(
		List<ChainableMethodAdvice> chainableMethodAdvices) {

		for (ChainableMethodAdvice chainableMethodAdvice :
				chainableMethodAdvices) {

			chainableMethodAdvice.setServiceBeanAopCacheManager(this);

			if (chainableMethodAdvice instanceof
					AnnotationChainableMethodAdvice) {

				AnnotationChainableMethodAdvice<?>
					annotationChainableMethodAdvice =
						(AnnotationChainableMethodAdvice<?>)
							chainableMethodAdvice;

				Class<? extends Annotation> annotationClass =
					annotationChainableMethodAdvice.getAnnotationClass();

				_annotationClasses.add(annotationClass);
			}
		}

		_fullChainableMethodAdvices = chainableMethodAdvices.toArray(
			new ChainableMethodAdvice[chainableMethodAdvices.size()]);
	}

	public AopMethod getAopMethod(Object target, Method method) {
		if (!TransactionsUtil.isEnabled()) {
			return new AopMethod(target, method, _emptyChainableMethodAdvices);
		}

		return _aopMethods.computeIfAbsent(
			new CacheKey(target, method), this::_createAopMethod);
	}

	public void reset() {
		_aopMethods.clear();
	}

	private AopMethod _createAopMethod(CacheKey cacheKey) {
		Object target = cacheKey._target;

		Class<?> targetClass = target.getClass();

		Method method = cacheKey._method;

		List<ChainableMethodAdvice> filteredChainableMethodAdvices =
			new ArrayList<>();

		ChainableMethodAdvice.AnnotationHelper annotationHelper =
			new DefaultAnnotationHelper();

		for (ChainableMethodAdvice chainableMethodAdvice :
				_fullChainableMethodAdvices) {

			if (chainableMethodAdvice.isEnabled(
					targetClass, method, annotationHelper)) {

				filteredChainableMethodAdvices.add(chainableMethodAdvice);
			}
		}

		ChainableMethodAdvice[] chainableMethodAdvices =
			_emptyChainableMethodAdvices;

		if (!filteredChainableMethodAdvices.isEmpty()) {
			chainableMethodAdvices = filteredChainableMethodAdvices.toArray(
				new ChainableMethodAdvice
					[filteredChainableMethodAdvices.size()]);
		}

		return new AopMethod(target, method, chainableMethodAdvices);
	}

	private static final ChainableMethodAdvice[] _emptyChainableMethodAdvices =
		new ChainableMethodAdvice[0];
	private static final Annotation[] _nullAnnotations = new Annotation[0];

	private final Set<Class<? extends Annotation>> _annotationClasses =
		new HashSet<>();
	private final Map<CacheKey, AopMethod> _aopMethods =
		new ConcurrentHashMap<>();
	private final ChainableMethodAdvice[] _fullChainableMethodAdvices;

	private static class CacheKey {

		@Override
		public boolean equals(Object obj) {
			CacheKey cacheKey = (CacheKey)obj;

			if (Objects.equals(_target, cacheKey._target) &&
				Objects.equals(_method, cacheKey._method)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hash = HashUtil.hash(0, _target);

			return HashUtil.hash(hash, _method);
		}

		private CacheKey(Object target, Method method) {
			_target = target;
			_method = method;
		}

		private final Method _method;
		private final Object _target;

	}

	private class DefaultAnnotationHelper
		implements ChainableMethodAdvice.AnnotationHelper {

		@Override
		public <T extends Annotation> T findAnnotation(
			Class<?> targetClass, Method method, Class<T> annotationType) {

			Annotation[] annotationArray = _methodAnnotations.get(method);

			if (annotationArray == _nullAnnotations) {
				return null;
			}

			T annotation = null;

			if (annotationArray == null) {
				List<Annotation> annotations = AnnotationLocator.locate(
					method, targetClass);

				Iterator<Annotation> iterator = annotations.iterator();

				while (iterator.hasNext()) {
					Annotation curAnnotation = iterator.next();

					Class<? extends Annotation> curAnnotationType =
						curAnnotation.annotationType();

					if (!_annotationClasses.contains(curAnnotationType)) {
						iterator.remove();
					}
					else if (annotationType == curAnnotationType) {
						annotation = (T)curAnnotation;
					}
				}

				if (annotations.isEmpty()) {
					_methodAnnotations.put(method, _nullAnnotations);
				}
				else {
					_methodAnnotations.put(
						method,
						annotations.toArray(
							new Annotation[annotations.size()]));
				}
			}
			else {
				for (Annotation curAnnotation : annotationArray) {
					if (curAnnotation.annotationType() == annotationType) {
						return (T)curAnnotation;
					}
				}
			}

			return annotation;
		}

		private final Map<Method, Annotation[]> _methodAnnotations =
			new HashMap<>();

	}

}