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

package com.liferay.bean.portlet.cdi.extension.internal;

import java.lang.annotation.Annotation;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
public class AnnotatedTypeRequestScopedImpl<X> extends AnnotatedTypeWrapper<X> {

	public AnnotatedTypeRequestScopedImpl(
		AnnotatedType<X> annotatedType,
		Set<Class<? extends Annotation>> annotationClasses) {

		super(annotatedType);

		Set<Annotation> annotations = annotatedType.getAnnotations();

		Stream<Annotation> annotationsStream = annotations.stream();

		_annotations = annotationsStream.filter(
			annotation -> {
				Class<? extends Annotation> annotationType =
					annotation.annotationType();

				return !annotationType.equals(RequestScoped.class);
			}
		).collect(
			Collectors.toSet()
		);

		if (!annotationClasses.contains(PortletRequestScoped.class)) {
			_annotations.add(new PortletRequestScopedAnnotation());
		}
	}

	@Override
	public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
		for (Annotation annotation : _annotations) {
			Class<? extends Annotation> curAnnotationType =
				annotation.annotationType();

			if (curAnnotationType.equals(annotationType)) {
				return (T)annotation;
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

	private final Set<Annotation> _annotations;

	private static class PortletRequestScopedAnnotation
		implements Annotation, PortletRequestScoped {

		@Override
		public Class<? extends Annotation> annotationType() {
			return PortletRequestScoped.class;
		}

	}

}