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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Shuyang Zhou
 */
public class ServiceBeanAopCacheManager {

	public static ServiceBeanAopCacheManager create(
		List<ChainableMethodAdvice> chainableMethodAdvices) {

		ServiceBeanAopCacheManager serviceBeanAopCacheManager =
			new ServiceBeanAopCacheManager(chainableMethodAdvices);

		_serviceBeanAopCacheManagers.add(serviceBeanAopCacheManager);

		return serviceBeanAopCacheManager;
	}

	public static void destroy(
		ServiceBeanAopCacheManager serviceBeanAopCacheManager) {

		_serviceBeanAopCacheManagers.remove(serviceBeanAopCacheManager);
	}

	public static void reset() {
		for (ServiceBeanAopCacheManager serviceBeanAopCacheManager :
				_serviceBeanAopCacheManagers) {

			Map<CacheKey, AopMethod> aopMethods =
				serviceBeanAopCacheManager._aopMethods;

			aopMethods.clear();
		}
	}

	public AopMethod getAopMethod(Object target, Method method) {
		if (!TransactionsUtil.isEnabled()) {
			return new AopMethod(
				target, method, _emptyChainableMethodAdvices, null);
		}

		return _aopMethods.computeIfAbsent(
			new CacheKey(target, method), this::_createAopMethod);
	}

	private ServiceBeanAopCacheManager(
		List<ChainableMethodAdvice> chainableMethodAdvices) {

		_fullChainableMethodAdvices = chainableMethodAdvices.toArray(
			new ChainableMethodAdvice[chainableMethodAdvices.size()]);
	}

	private AopMethod _createAopMethod(CacheKey cacheKey) {
		Object target = cacheKey._target;

		Class<?> targetClass = target.getClass();

		Method method = cacheKey._method;

		List<ChainableMethodAdvice> filteredChainableMethodAdvices =
			new ArrayList<>();
		List<Object> filteredAdviceMethodContexts = new ArrayList<>();

		Map<Class<? extends Annotation>, Annotation> annotations =
			AnnotationLocator.index(method, targetClass);

		for (ChainableMethodAdvice chainableMethodAdvice :
				_fullChainableMethodAdvices) {

			Object methodContext = chainableMethodAdvice.createMethodContext(
				targetClass, method, annotations);

			if (methodContext != null) {
				filteredChainableMethodAdvices.add(chainableMethodAdvice);

				filteredAdviceMethodContexts.add(methodContext);
			}
		}

		ChainableMethodAdvice[] chainableMethodAdvices =
			_emptyChainableMethodAdvices;
		Object[] adviceMethodContexts = null;

		if (!filteredChainableMethodAdvices.isEmpty()) {
			chainableMethodAdvices = filteredChainableMethodAdvices.toArray(
				new ChainableMethodAdvice
					[filteredChainableMethodAdvices.size()]);

			adviceMethodContexts = filteredAdviceMethodContexts.toArray(
				new Object[filteredAdviceMethodContexts.size()]);
		}

		return new AopMethod(
			target, method, chainableMethodAdvices, adviceMethodContexts);
	}

	private static final ChainableMethodAdvice[] _emptyChainableMethodAdvices =
		new ChainableMethodAdvice[0];
	private static final Set<ServiceBeanAopCacheManager>
		_serviceBeanAopCacheManagers = Collections.newSetFromMap(
			new ConcurrentHashMap<>());

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

}