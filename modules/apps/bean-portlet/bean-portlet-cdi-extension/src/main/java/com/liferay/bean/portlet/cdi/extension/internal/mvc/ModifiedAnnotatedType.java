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
import java.lang.reflect.Type;

import java.util.Set;

import javax.enterprise.inject.spi.AnnotatedConstructor;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedType;

/**
 * @author  Neil Griffin
 */
public class ModifiedAnnotatedType<X> implements AnnotatedType<X> {

	public ModifiedAnnotatedType(
		AnnotatedType<X> annotatedType, Set<AnnotatedField<? super X>> fields,
		Set<AnnotatedMethod<? super X>> methods) {

		_annotatedType = annotatedType;
		_fields = fields;
		_methods = methods;
	}

	@Override
	public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
		return _annotatedType.getAnnotation(annotationType);
	}

	@Override
	public Set<Annotation> getAnnotations() {
		return _annotatedType.getAnnotations();
	}

	@Override
	public Type getBaseType() {
		return _annotatedType.getBaseType();
	}

	@Override
	public Set<AnnotatedConstructor<X>> getConstructors() {
		return _annotatedType.getConstructors();
	}

	@Override
	public Set<AnnotatedField<? super X>> getFields() {
		return _fields;
	}

	@Override
	public Class<X> getJavaClass() {
		return _annotatedType.getJavaClass();
	}

	@Override
	public Set<AnnotatedMethod<? super X>> getMethods() {
		return _methods;
	}

	@Override
	public Set<Type> getTypeClosure() {
		return _annotatedType.getTypeClosure();
	}

	@Override
	public boolean isAnnotationPresent(
		Class<? extends Annotation> annotationType) {

		return _annotatedType.isAnnotationPresent(annotationType);
	}

	private final AnnotatedType<X> _annotatedType;
	private final Set<AnnotatedField<? super X>> _fields;
	private final Set<AnnotatedMethod<? super X>> _methods;

}