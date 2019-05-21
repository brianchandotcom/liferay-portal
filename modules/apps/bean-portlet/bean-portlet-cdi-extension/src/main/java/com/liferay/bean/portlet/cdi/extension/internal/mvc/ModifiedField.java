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
import java.lang.reflect.Field;
import java.lang.reflect.Type;

import java.util.Set;

import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedType;

/**
 * @author  Neil Griffin
 */
public class ModifiedField<X> implements AnnotatedField<X> {

	public ModifiedField(
		AnnotatedField<X> annotatedField, Set<Annotation> annotations) {

		_annotatedField = annotatedField;
		_annotations = annotations;
	}

	@Override
	public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
		for (Annotation annotation : _annotations) {
			if (annotationClass.isAssignableFrom(annotation.getClass())) {
				return annotationClass.cast(annotation);
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
		return _annotatedField.getBaseType();
	}

	@Override
	public AnnotatedType<X> getDeclaringType() {
		return _annotatedField.getDeclaringType();
	}

	@Override
	public Field getJavaMember() {
		return _annotatedField.getJavaMember();
	}

	@Override
	public Set<Type> getTypeClosure() {
		return _annotatedField.getTypeClosure();
	}

	@Override
	public boolean isAnnotationPresent(
		Class<? extends Annotation> annotationClass) {

		if (getAnnotation(annotationClass) != null) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isStatic() {
		return _annotatedField.isStatic();
	}

	private final AnnotatedField<X> _annotatedField;
	private final Set<Annotation> _annotations;

}