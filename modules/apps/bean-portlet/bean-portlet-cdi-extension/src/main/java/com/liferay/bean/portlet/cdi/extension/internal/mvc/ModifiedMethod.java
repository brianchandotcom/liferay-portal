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

package com.liferay.bean.portlet.cdi.extension.internal.mvc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import java.util.List;
import java.util.Set;

import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedParameter;
import javax.enterprise.inject.spi.AnnotatedType;

/**
 * @author  Neil Griffin
 */
public class ModifiedMethod<X> implements AnnotatedMethod<X> {

	public ModifiedMethod(
		AnnotatedMethod<X> annotatedMethod, Set<Annotation> annotations) {

		_annotatedMethod = annotatedMethod;
		_annotations = annotations;
	}

	@Override
	public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
		for (Annotation annotation : _annotations) {
			Class<? extends Annotation> curAnnotationType =
				annotation.annotationType();

			if (curAnnotationType.equals(annotationType)) {
				return annotationType.cast(annotation);
			}
		}

		return null;
	}

	@Override
	public Set<Annotation> getAnnotations() {
		return _annotations;
	}

	@Override
	public Type getBaseType() {
		return _annotatedMethod.getBaseType();
	}

	@Override
	public AnnotatedType<X> getDeclaringType() {
		return _annotatedMethod.getDeclaringType();
	}

	@Override
	public Method getJavaMember() {
		return _annotatedMethod.getJavaMember();
	}

	@Override
	public List<AnnotatedParameter<X>> getParameters() {
		return _annotatedMethod.getParameters();
	}

	@Override
	public Set<Type> getTypeClosure() {
		return _annotatedMethod.getTypeClosure();
	}

	@Override
	public boolean isAnnotationPresent(
		Class<? extends Annotation> annotationType) {

		Annotation annotation = getAnnotation(annotationType);

		if (annotation == null) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isStatic() {
		return _annotatedMethod.isStatic();
	}

	private final AnnotatedMethod<X> _annotatedMethod;
	private final Set<Annotation> _annotations;

}