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

import com.liferay.petra.reflect.AnnotationLocator;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Shuyang Zhou
 */
public class ServiceBeanAopCacheManager {

	public ServiceBeanAopCacheManager(
		ChainableMethodAdvice chainableMethodAdvice) {

		List<ChainableMethodAdvice> fullChainableMethodAdvices =
			new ArrayList<>();

		while (chainableMethodAdvice != null) {
			chainableMethodAdvice.setServiceBeanAopCacheManager(this);

			if (chainableMethodAdvice instanceof
					AnnotationChainableMethodAdvice) {

				AnnotationChainableMethodAdvice<?>
					annotationChainableMethodAdvice =
						(AnnotationChainableMethodAdvice<?>)
							chainableMethodAdvice;

				Class<? extends Annotation> annotationClass =
					annotationChainableMethodAdvice.getAnnotationClass();

				_registerAnnotationChainableMethodAdvice(
					annotationClass, annotationChainableMethodAdvice);
			}

			fullChainableMethodAdvices.add(chainableMethodAdvice);

			chainableMethodAdvice = (ChainableMethodAdvice)
				chainableMethodAdvice.nextMethodInterceptor;
		}

		_fullChainableMethodAdvices = fullChainableMethodAdvices.toArray(
			new ChainableMethodAdvice[fullChainableMethodAdvices.size()]);
	}

	public <T> T findAnnotation(
		Class<?> targetClass, Method method,
		Class<? extends Annotation> annotationType, T defaultValue) {

		T annotation = _findAnnotation(
			_methodAnnotations, method, annotationType, defaultValue);

		if (annotation == null) {
			annotation = defaultValue;

			List<Annotation> annotations = AnnotationLocator.locate(
				method, targetClass);

			Iterator<Annotation> iterator = annotations.iterator();

			while (iterator.hasNext()) {
				Annotation curAnnotation = iterator.next();

				Class<? extends Annotation> curAnnotationType =
					curAnnotation.annotationType();

				if (!_annotationChainableMethodAdvices.containsKey(
						curAnnotationType)) {

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
					annotations.toArray(new Annotation[annotations.size()]));
			}
		}

		return annotation;
	}

	public ChainableMethodAdvice[] getMethodInterceptors(
		Class<?> targetClass, Method method) {

		ChainableMethodAdvice[] chainableMethodAdvices =
			_chainableMethodAdvices.get(method);

		if (chainableMethodAdvices == null) {
			List<ChainableMethodAdvice> filteredChainableMethodAdvices =
				new ArrayList<>();

			for (ChainableMethodAdvice chainableMethodAdvice :
					_fullChainableMethodAdvices) {

				if (chainableMethodAdvice.isEnabled(targetClass, method)) {
					filteredChainableMethodAdvices.add(chainableMethodAdvice);
				}
			}

			if (filteredChainableMethodAdvices.isEmpty()) {
				chainableMethodAdvices = _emptyChainableMethodAdvices;
			}
			else {
				chainableMethodAdvices = filteredChainableMethodAdvices.toArray(
					new ChainableMethodAdvice[
						filteredChainableMethodAdvices.size()]);
			}

			_chainableMethodAdvices.put(method, chainableMethodAdvices);
		}

		return chainableMethodAdvices;
	}

	public void reset() {
		_chainableMethodAdvices.clear();
	}

	private static <T> T _findAnnotation(
		Map<Method, Annotation[]> methodAnnotations, Method method,
		Class<? extends Annotation> annotationType, T defaultValue) {

		Annotation[] annotations = methodAnnotations.get(method);

		if (annotations == _nullAnnotations) {
			return defaultValue;
		}

		if (annotations == null) {
			return null;
		}

		for (Annotation annotation : annotations) {
			if (annotation.annotationType() == annotationType) {
				return (T)annotation;
			}
		}

		return defaultValue;
	}

	private void _registerAnnotationChainableMethodAdvice(
		Class<? extends Annotation> annotationClass,
		AnnotationChainableMethodAdvice<?> annotationChainableMethodAdvice) {

		AnnotationChainableMethodAdvice<?>[] annotationChainableMethodAdvices =
			_annotationChainableMethodAdvices.get(annotationClass);

		if (annotationChainableMethodAdvices == null) {
			annotationChainableMethodAdvices =
				new AnnotationChainableMethodAdvice<?>[1];

			annotationChainableMethodAdvices[0] =
				annotationChainableMethodAdvice;
		}
		else {
			annotationChainableMethodAdvices = ArrayUtil.append(
				annotationChainableMethodAdvices,
				annotationChainableMethodAdvice);
		}

		_annotationChainableMethodAdvices.put(
			annotationClass, annotationChainableMethodAdvices);
	}

	private static final ChainableMethodAdvice[] _emptyChainableMethodAdvices =
		new ChainableMethodAdvice[0];
	private static final Annotation[] _nullAnnotations = new Annotation[0];

	private final
		Map<Class<? extends Annotation>, AnnotationChainableMethodAdvice<?>[]>
			_annotationChainableMethodAdvices = new HashMap<>();
	private final Map<Method, ChainableMethodAdvice[]> _chainableMethodAdvices =
		new ConcurrentHashMap<>();
	private final ChainableMethodAdvice[] _fullChainableMethodAdvices;
	private final Map<Method, Annotation[]> _methodAnnotations =
		new ConcurrentHashMap<>();

}