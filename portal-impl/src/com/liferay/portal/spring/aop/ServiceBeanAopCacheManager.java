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

import java.lang.annotation.Annotation;

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

		Annotation[] annotations = _annotations.get(methodInvocation);

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

		if ((annotations == null) || (annotations.length == 0)) {
			annotations = _nullAnnotations;
		}

		if (methodInvocation instanceof ServiceBeanMethodInvocation) {
			ServiceBeanMethodInvocation serviceBeanMethodInvocation =
				(ServiceBeanMethodInvocation)methodInvocation;

			methodInvocation = serviceBeanMethodInvocation.toCacheKeyModel();
		}

		_annotations.put(methodInvocation, annotations);
	}

	public MethodInterceptorsBag getMethodInterceptorsBag(
		MethodInvocation methodInvocation) {

		return _methodInterceptorBags.get(methodInvocation);
	}

	public Map<Class<? extends Annotation>, AnnotationChainableMethodAdvice<?>>
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

		_methodInterceptorBags.put(methodInvocation, methodInterceptorsBag);
	}

	public void registerAnnotationChainableMethodAdvice(
		Class<? extends Annotation> annotationClass,
		AnnotationChainableMethodAdvice<?> annotationChainableMethodAdvice) {

		_annotationChainableMethodAdvices.put(
			annotationClass, annotationChainableMethodAdvice);
	}

	public void removeMethodInterceptor(
		MethodInvocation methodInvocation,
		MethodInterceptor methodInterceptor) {

		if (!(methodInvocation instanceof ServiceBeanMethodInvocation)) {
			return;
		}

		ServiceBeanMethodInvocation serviceBeanMethodInvocation =
			(ServiceBeanMethodInvocation)methodInvocation;

		MethodInterceptorsBag methodInterceptorsBag =
			_methodInterceptorBags.get(serviceBeanMethodInvocation);

		if (methodInterceptorsBag == null) {
			return;
		}

		ArrayList<MethodInterceptor> methodInterceptors =
			new ArrayList<MethodInterceptor>(
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

		_methodInterceptorBags.put(
			serviceBeanMethodInvocation.toCacheKeyModel(),
			newMethodInterceptorsBag);
	}

	public void reset() {
		_annotations.clear();
		_methodInterceptorBags.clear();
	}

	private static Map<MethodInvocation, Annotation[]> _annotations =
		new ConcurrentHashMap<MethodInvocation, Annotation[]>();
	private static Annotation[] _nullAnnotations = new Annotation[0];

	private Map<Class<? extends Annotation>, AnnotationChainableMethodAdvice<?>>
		_annotationChainableMethodAdvices = new HashMap
			<Class<? extends Annotation>, AnnotationChainableMethodAdvice<?>>();
	private Map<MethodInvocation, MethodInterceptorsBag>
		_methodInterceptorBags =
			new ConcurrentHashMap<MethodInvocation, MethodInterceptorsBag>();

}