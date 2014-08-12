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

import com.liferay.portal.kernel.annotation.AnnotationLocator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

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

	public AnnotationChainableMethodAdvice() {
		_nullAnnotation = getNullAnnotation();

		_annotationClass = _nullAnnotation.annotationType();
	}

	public Class<? extends Annotation> getAnnotationClass() {
		return _annotationClass;
	}

	public abstract T getNullAnnotation();

	protected T findAnnotation(MethodInvocation methodInvocation) {
		Annotation annotation = ServiceBeanAopCacheManager.getAnnotation(
			methodInvocation, _annotationClass, _nullAnnotation);

		if (annotation != null) {
			return (T)annotation;
		}

		Method method = methodInvocation.getMethod();

		ServiceBeanMethodInvocation serviceBeanMethodInvocation =
			(ServiceBeanMethodInvocation)methodInvocation;

		Class<?> targetClass = serviceBeanMethodInvocation.getTargetClass();

		List<Annotation> annotations = AnnotationLocator.locate(
			method, targetClass);

		Iterator<Annotation> iterator = annotations.iterator();

		while (iterator.hasNext()) {
			Annotation curAnnotation = iterator.next();

			if (!serviceBeanAopCacheManager.isRegisteredAnnotationClass(
					curAnnotation.annotationType())) {

				iterator.remove();
			}
		}

		ServiceBeanAopCacheManager.putAnnotations(
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

		Map<Class<? extends Annotation>, AnnotationChainableMethodAdvice<?>[]>
			annotationChainableMethodAdvices =
				serviceBeanAopCacheManager.
					getRegisteredAnnotationChainableMethodAdvices();

		for (Map.Entry<Class<? extends Annotation>,
				AnnotationChainableMethodAdvice<?>[]> entry :
					annotationChainableMethodAdvices.entrySet()) {

			Class<? extends Annotation> annotationClass = entry.getKey();
			AnnotationChainableMethodAdvice<?>[]
				annotationChainableMethodAdvicesArray = entry.getValue();

			if (annotationClasses.contains(annotationClass) ||
				(annotationChainableMethodAdvicesArray == null)) {

				continue;
			}

			for (AnnotationChainableMethodAdvice<?>
					annotationChainableMethodAdvice :
						annotationChainableMethodAdvicesArray) {

				serviceBeanAopCacheManager.removeMethodInterceptor(
					methodInvocation, annotationChainableMethodAdvice);
			}
		}

		return (T)annotation;
	}

	@Override
	protected void setServiceBeanAopCacheManager(
		ServiceBeanAopCacheManager serviceBeanAopCacheManager) {

		if (this.serviceBeanAopCacheManager != null) {
			return;
		}

		this.serviceBeanAopCacheManager = serviceBeanAopCacheManager;

		serviceBeanAopCacheManager.registerAnnotationChainableMethodAdvice(
			_annotationClass, this);
	}

	private final Class<? extends Annotation> _annotationClass;
	private final T _nullAnnotation;

}