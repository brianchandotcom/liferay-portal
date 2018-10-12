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

package com.liferay.bean.portlet.cdi.extension.internal.annotated.type;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import java.util.Set;

import javax.enterprise.inject.spi.AnnotatedConstructor;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedType;

/**
 * @author Shuyang Zhou
 */
public class ModifiedAnnotatedType<X> implements AnnotatedType<X> {

	public ModifiedAnnotatedType(
		AnnotatedType<X> annotatedType, Set<Annotation> annotations,
		Set<Type> types) {

		_annotatedType = annotatedType;
		_annotations = annotations;
		_types = types;
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
		return _annotatedType.getBaseType();
	}

	@Override
	public Set<AnnotatedConstructor<X>> getConstructors() {
		return _annotatedType.getConstructors();
	}

	@Override
	public Set<AnnotatedField<? super X>> getFields() {
		return _annotatedType.getFields();
	}

	@Override
	public Class<X> getJavaClass() {
		return _annotatedType.getJavaClass();
	}

	@Override
	public Set<AnnotatedMethod<? super X>> getMethods() {
		return _annotatedType.getMethods();
	}

	@Override
	public Set<Type> getTypeClosure() {
		return _types;
	}

	@Override
	public boolean isAnnotationPresent(
		Class<? extends Annotation> annotationType) {

		for (Annotation annotation : _annotations) {
			Class<? extends Annotation> curAnnotationType =
				annotation.annotationType();

			if (curAnnotationType.equals(annotationType)) {
				return true;
			}
		}

		return false;
	}

	private final AnnotatedType<X> _annotatedType;
	private final Set<Annotation> _annotations;
	private final Set<Type> _types;

}