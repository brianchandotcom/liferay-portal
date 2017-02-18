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

import com.liferay.portal.kernel.util.ArrayUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Shuyang Zhou
 */
public class ServiceBeanAopCacheManager {

	public static <T> T getAnnotation(
		MethodInvocation methodInvocation,
		Class<? extends Annotation> annotationType, T defaultValue) {

		Annotation[] annotations = _annotations.get(
			methodInvocation.getMethod());

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

	public static void putAnnotations(
		MethodInvocation methodInvocation, Annotation[] annotations) {

		if (ArrayUtil.isEmpty(annotations)) {
			annotations = _nullAnnotations;
		}

		_annotations.put(methodInvocation.getMethod(), annotations);
	}

	public MethodInterceptorsBag getMethodInterceptorsBag(
		MethodInvocation methodInvocation) {

		return _methodInterceptorBags.get(methodInvocation.getMethod());
	}

	public Map
		<Class<? extends Annotation>, AnnotationChainableMethodAdvice<?>[]>
			getRegisteredAnnotationChainableMethodAdvices() {

		return _annotationChainableMethodAdvices;
	}

	public boolean isRegisteredAnnotationClass(
		Class<? extends Annotation> annotationClass) {

		return _annotationChainableMethodAdvices.containsKey(annotationClass);
	}

	public void putMethodInterceptorsBag(
		MethodInvocation methodInvocation,
		MethodInterceptorsBag methodInterceptorsBag) {

		_methodInterceptorBags.put(
			methodInvocation.getMethod(), methodInterceptorsBag);
	}

	public void registerAnnotationChainableMethodAdvice(
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

	public void removeMethodInterceptor(
		MethodInvocation methodInvocation,
		MethodInterceptor methodInterceptor) {

		Method method = methodInvocation.getMethod();

		MethodInterceptorsBag methodInterceptorsBag =
			_methodInterceptorBags.get(method);

		if (methodInterceptorsBag == null) {
			return;
		}

		ArrayList<MethodInterceptor> methodInterceptors = new ArrayList<>(
			methodInterceptorsBag.getMergedMethodInterceptors());

		methodInterceptors.remove(methodInterceptor);

		MethodInterceptorsBag newMethodInterceptorsBag = null;

		if (methodInterceptors.equals(
				methodInterceptorsBag.getClassLevelMethodInterceptors())) {

			newMethodInterceptorsBag = new MethodInterceptorsBag(
				methodInterceptorsBag.getClassLevelMethodInterceptors(),
				methodInterceptorsBag.getClassLevelMethodInterceptors());
		}
		else {
			methodInterceptors.trimToSize();

			newMethodInterceptorsBag = new MethodInterceptorsBag(
				methodInterceptorsBag.getClassLevelMethodInterceptors(),
				methodInterceptors);
		}

		_methodInterceptorBags.put(method, newMethodInterceptorsBag);
	}

	public void reset() {
		_annotations.clear();
		_methodInterceptorBags.clear();
	}

	private static final Map<Method, Annotation[]> _annotations =
		new ConcurrentHashMap<>();
	private static final Annotation[] _nullAnnotations = new Annotation[0];

	private final
		Map<Class<? extends Annotation>, AnnotationChainableMethodAdvice<?>[]>
			_annotationChainableMethodAdvices = new HashMap<>();
	private final Map<Method, MethodInterceptorsBag> _methodInterceptorBags =
		new ConcurrentHashMap<>();

}