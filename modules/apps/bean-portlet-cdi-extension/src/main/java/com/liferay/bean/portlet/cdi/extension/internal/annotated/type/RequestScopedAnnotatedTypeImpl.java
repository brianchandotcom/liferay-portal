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

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.spi.AnnotatedType;

import javax.portlet.annotations.PortletRequestScoped;

/**
 * Decorates an annotated type by replacing the {@link RequestScoped} annotation
 * with {@link PortletRequestScoped} so that the bean will be scoped to the
 * portlet request.
 *
 * @author Neil Griffin
 */
public class RequestScopedAnnotatedTypeImpl<X> extends AnnotatedTypeWrapper<X> {

	public RequestScopedAnnotatedTypeImpl(AnnotatedType<X> annotatedType) {
		super(annotatedType);

		for (Annotation annotation : annotatedType.getAnnotations()) {
			Class<? extends Annotation> annotationType =
				annotation.annotationType();

			if (!annotationType.equals(RequestScoped.class)) {
				_annotations.add(annotation);
			}
		}

		if (!annotatedType.isAnnotationPresent(PortletRequestScoped.class)) {
			_annotations.add(_portletRequestScoped);
		}
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

	private static final Annotation _portletRequestScoped =
		new PortletRequestScoped() {

			@Override
			public Class<? extends Annotation> annotationType() {
				return PortletRequestScoped.class;
			}

		};

	private final Set<Annotation> _annotations = new HashSet<>();

}