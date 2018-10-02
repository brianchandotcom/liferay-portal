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
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Shuyang Zhou
 */
public class ServiceBeanAopCacheManager {

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #findAnnotation(MethodInvocation, Class, Object)}
	 */
	@Deprecated
	public static <T> T getAnnotation(
		MethodInvocation methodInvocation,
		Class<? extends Annotation> annotationType, T defaultValue) {

		return _findAnnotation(
			_annotations, methodInvocation, annotationType, defaultValue);
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public static void putAnnotations(
		MethodInvocation methodInvocation, Annotation[] annotations) {

		_setAnnotations(_annotations, methodInvocation, annotations);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #ServiceBeanAopCacheManager(MethodInterceptor)}
	 */
	@Deprecated
	public ServiceBeanAopCacheManager() {
		_classLevelMethodInterceptors = new MethodInterceptor[0];
		_fullMethodInterceptors = new MethodInterceptor[0];
	}

	public ServiceBeanAopCacheManager(MethodInterceptor methodInterceptor) {
		List<MethodInterceptor> classLevelMethodInterceptors =
			new ArrayList<>();
		List<MethodInterceptor> fullMethodInterceptors = new ArrayList<>();

		while (true) {
			if (!(methodInterceptor instanceof ChainableMethodAdvice)) {
				classLevelMethodInterceptors.add(methodInterceptor);
				fullMethodInterceptors.add(methodInterceptor);

				break;
			}

			ChainableMethodAdvice chainableMethodAdvice =
				(ChainableMethodAdvice)methodInterceptor;

			chainableMethodAdvice.setServiceBeanAopCacheManager(this);

			if (methodInterceptor instanceof AnnotationChainableMethodAdvice) {
				AnnotationChainableMethodAdvice<?>
					annotationChainableMethodAdvice =
						(AnnotationChainableMethodAdvice<?>)methodInterceptor;

				Class<? extends Annotation> annotationClass =
					annotationChainableMethodAdvice.getAnnotationClass();

				Target target = annotationClass.getAnnotation(Target.class);

				if (target == null) {
					classLevelMethodInterceptors.add(methodInterceptor);
				}
				else {
					for (ElementType elementType : target.value()) {
						if (elementType == ElementType.TYPE) {
							classLevelMethodInterceptors.add(methodInterceptor);

							break;
						}
					}
				}
			}
			else {
				classLevelMethodInterceptors.add(methodInterceptor);
			}

			fullMethodInterceptors.add(methodInterceptor);

			methodInterceptor = chainableMethodAdvice.nextMethodInterceptor;
		}

		_classLevelMethodInterceptors = classLevelMethodInterceptors.toArray(
			new MethodInterceptor[classLevelMethodInterceptors.size()]);
		_fullMethodInterceptors = fullMethodInterceptors.toArray(
			new MethodInterceptor[fullMethodInterceptors.size()]);
	}

	public <T> T findAnnotation(
		MethodInvocation methodInvocation,
		Class<? extends Annotation> annotationType, T defaultValue) {

		T annotation = _findAnnotation(
			_methodAnnotations, methodInvocation, annotationType, defaultValue);

		if (annotation == null) {
			annotation = defaultValue;

			Object target = methodInvocation.getThis();

			List<Annotation> annotations = AnnotationLocator.locate(
				methodInvocation.getMethod(), target.getClass());

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

			_setAnnotations(
				_methodAnnotations, methodInvocation,
				annotations.toArray(new Annotation[annotations.size()]));
		}

		return annotation;
	}

	public MethodInterceptor[] getMethodInterceptors(
		MethodInvocation methodInvocation) {

		MethodInterceptor[] methodInterceptors = _methodInterceptors.get(
			methodInvocation.getMethod());

		if (methodInterceptors == null) {
			methodInterceptors = _fullMethodInterceptors;

			_methodInterceptors.put(
				methodInvocation.getMethod(), methodInterceptors);
		}

		return methodInterceptors;
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #getMethodInterceptors(MethodInvocation)}
	 */
	@Deprecated
	public MethodInterceptorsBag getMethodInterceptorsBag(
		MethodInvocation methodInvocation) {

		MethodInterceptor[] methodInterceptors = getMethodInterceptors(
			methodInvocation);

		return new MethodInterceptorsBag(
			Arrays.asList(_classLevelMethodInterceptors),
			Arrays.asList(methodInterceptors));
	}

	public Map
		<Class<? extends Annotation>, AnnotationChainableMethodAdvice<?>[]>
			getRegisteredAnnotationChainableMethodAdvices() {

		return _annotationChainableMethodAdvices;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public boolean isRegisteredAnnotationClass(
		Class<? extends Annotation> annotationClass) {

		return _annotationChainableMethodAdvices.containsKey(annotationClass);
	}

	public void putMethodInterceptors(
		MethodInvocation methodInvocation,
		MethodInterceptor[] methodInterceptors) {

		_methodInterceptors.put(
			methodInvocation.getMethod(), methodInterceptors);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #putMethodInterceptors(MethodInvocation, MethodInterceptor[])}
	 */
	@Deprecated
	public void putMethodInterceptorsBag(
		MethodInvocation methodInvocation,
		MethodInterceptorsBag methodInterceptorsBag) {

		List<MethodInterceptor> methodInterceptors =
			methodInterceptorsBag.getMergedMethodInterceptors();

		putMethodInterceptors(
			methodInvocation,
			methodInterceptors.toArray(
				new MethodInterceptor[methodInterceptors.size()]));
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

		MethodInterceptor[] methodInterceptors = _methodInterceptors.get(
			method);

		if (methodInterceptors == null) {
			return;
		}

		int index = -1;

		for (int i = 0; i < methodInterceptors.length; i++) {
			if (methodInterceptors[i].equals(methodInterceptor)) {
				index = i;

				break;
			}
		}

		if (index < 0) {
			return;
		}

		int newLength = methodInterceptors.length - 1;

		MethodInterceptor[] newMethodInterceptors = new MethodInterceptor[
			newLength];

		if (index > 0) {
			System.arraycopy(
				methodInterceptors, 0, newMethodInterceptors, 0, index);
		}

		if (index < newLength) {
			System.arraycopy(
				methodInterceptors, index + 1, newMethodInterceptors, index,
				newLength - index);
		}

		if (Arrays.equals(
				newMethodInterceptors, _classLevelMethodInterceptors)) {

			newMethodInterceptors = _classLevelMethodInterceptors;
		}

		_methodInterceptors.put(method, newMethodInterceptors);
	}

	public void reset() {
		_annotations.clear();
		_methodInterceptors.clear();
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public void setAnnotations(
		MethodInvocation methodInvocation, Annotation[] annotations) {

		_setAnnotations(_methodAnnotations, methodInvocation, annotations);
	}

	private static <T> T _findAnnotation(
		Map<Method, Annotation[]> methodAnnotations,
		MethodInvocation methodInvocation,
		Class<? extends Annotation> annotationType, T defaultValue) {

		Annotation[] annotations = methodAnnotations.get(
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

	private static void _setAnnotations(
		Map<Method, Annotation[]> methodAnnotations,
		MethodInvocation methodInvocation, Annotation[] annotations) {

		if (ArrayUtil.isEmpty(annotations)) {
			annotations = _nullAnnotations;
		}

		methodAnnotations.put(methodInvocation.getMethod(), annotations);
	}

	private static final Map<Method, Annotation[]> _annotations =
		new ConcurrentHashMap<>();
	private static final Annotation[] _nullAnnotations = new Annotation[0];

	private final
		Map<Class<? extends Annotation>, AnnotationChainableMethodAdvice<?>[]>
			_annotationChainableMethodAdvices = new HashMap<>();
	private final MethodInterceptor[] _classLevelMethodInterceptors;
	private final MethodInterceptor[] _fullMethodInterceptors;
	private final Map<Method, Annotation[]> _methodAnnotations =
		new ConcurrentHashMap<>();
	private final Map<Method, MethodInterceptor[]> _methodInterceptors =
		new ConcurrentHashMap<>();

}