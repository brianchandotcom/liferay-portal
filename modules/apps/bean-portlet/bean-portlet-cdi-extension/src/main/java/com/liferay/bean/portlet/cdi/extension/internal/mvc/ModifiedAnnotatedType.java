/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
 * @author Neil Griffin
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