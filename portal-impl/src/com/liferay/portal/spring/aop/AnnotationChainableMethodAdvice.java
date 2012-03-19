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

package com.liferay.portal.spring.aop;

import com.liferay.portal.kernel.annotation.AnnotationLocator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public abstract class AnnotationChainableMethodAdvice<T extends Annotation>
	extends ChainableMethodAdvice {

	public static void registerAnnotationClass(
		Class<? extends Annotation> annotationClass) {

		_annotationChainableMethodAdvices.put(annotationClass, null);
	}

	public AnnotationChainableMethodAdvice() {
		_nullAnnotation = getNullAnnotation();

		_annotationClass = _nullAnnotation.annotationType();
	}

	public void afterPropertiesSet() {
		_annotationChainableMethodAdvices.put(_annotationClass, this);
	}

	public Class<? extends Annotation> getAnnotationClass() {
		return _annotationClass;
	}

	public abstract T getNullAnnotation();

	protected T findAnnotation(MethodInvocation methodInvocation) {
		Annotation annotation = ServiceMethodAnnotationCache.get(
			methodInvocation, _annotationClass, _nullAnnotation);

		if (annotation != null) {
			return (T)annotation;
		}

		Object thisObject = methodInvocation.getThis();

		Class<?> targetClass = thisObject.getClass();

		Method method = methodInvocation.getMethod();

		List<Annotation> annotations = AnnotationLocator.locate(
			method, targetClass);

		Iterator<Annotation> iterator = annotations.iterator();

		while (iterator.hasNext()) {
			Annotation curAnnotation = iterator.next();

			if (!_annotationChainableMethodAdvices.containsKey(
					curAnnotation.annotationType())) {

				iterator.remove();
			}
		}

		ServiceMethodAnnotationCache.put(
			methodInvocation,
			annotations.toArray(new Annotation[annotations.size()]));

		Set<Class<? extends Annotation>> annotationClasses =
			new HashSet<Class<? extends Annotation>>();

		annotation = _nullAnnotation;

		for (Annotation curAnnotation : annotations) {
			Class<? extends Annotation> annotationClass =
				curAnnotation.annotationType();

			if (annotationClass == _annotationClass) {
				annotation = curAnnotation;
			}

			annotationClasses.add(annotationClass);
		}

		for (Map.Entry<Class<? extends Annotation>,
				AnnotationChainableMethodAdvice<?>> entry :
					_annotationChainableMethodAdvices.entrySet()) {

			Class<? extends Annotation> annotationClass = entry.getKey();
			AnnotationChainableMethodAdvice<?> annotationChainableMethodAdvice =
				entry.getValue();

			if (!annotationClasses.contains(annotationClass) &&
				(annotationChainableMethodAdvice != null)) {

				ServiceBeanAopProxy.removeMethodInterceptor(
					methodInvocation, annotationChainableMethodAdvice);
			}
		}

		return (T)annotation;
	}

	private static Map<Class<? extends Annotation>,
		AnnotationChainableMethodAdvice<?>> _annotationChainableMethodAdvices =
			new HashMap<Class<? extends Annotation>,
				AnnotationChainableMethodAdvice<?>>();

	private Class<? extends Annotation> _annotationClass;
	private T _nullAnnotation;

}