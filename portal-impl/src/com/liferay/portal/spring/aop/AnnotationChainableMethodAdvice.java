/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public abstract class AnnotationChainableMethodAdvice<T extends Annotation>
	extends ChainableMethodAdvice {

	public static void registerAnnotationType(
		Class<? extends Annotation> annotationType) {

		_annotationTypes.add(annotationType);
	}

	public AnnotationChainableMethodAdvice() {
		_nullAnnotation = getNullAnnotation();

		_annotationType = _nullAnnotation.annotationType();

		_annotationTypes.add(_annotationType);
	}

	public abstract T getNullAnnotation();

	protected T findAnnotation(MethodInvocation methodInvocation) {
		Annotation annotation = ServiceMethodAnnotationCache.get(
			methodInvocation, _annotationType, _nullAnnotation);

		if (annotation != null) {
			return (T)annotation;
		}

		Object thisObject = methodInvocation.getThis();

		Class<?> targetClass = thisObject.getClass();

		Method method = methodInvocation.getMethod();

		Method targetMethod = null;

		try {
			targetMethod = targetClass.getDeclaredMethod(
				method.getName(), method.getParameterTypes());
		}
		catch (Throwable t) {
		}

		Annotation[] annotations = null;

		if (targetMethod != null) {
			annotations = targetMethod.getAnnotations();
		}

		if ((annotations == null) || (annotations.length == 0)) {
			annotations = method.getAnnotations();
		}

		if ((annotations != null) && (annotations.length > 0)) {
			List<Annotation> filteredAnnotations = new ArrayList<Annotation>(
							annotations.length);

			for (Annotation curAnnotation : annotations) {
				if (_annotationTypes.contains(
						curAnnotation.annotationType())) {

					filteredAnnotations.add(curAnnotation);
				}
			}

			annotations = filteredAnnotations.toArray(
				new Annotation[filteredAnnotations.size()]);
		}

		ServiceMethodAnnotationCache.put(methodInvocation, annotations);

		for (Annotation curAnnotation : annotations) {
			if (curAnnotation.annotationType() == _annotationType) {
				return (T)curAnnotation;
			}
		}

		return _nullAnnotation;
	}

	private static Set<Class<? extends Annotation>> _annotationTypes =
		new HashSet<Class<? extends Annotation>>();

	private Class<? extends Annotation> _annotationType;
	private T _nullAnnotation;

}