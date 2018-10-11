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

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.spi.AnnotatedType;

import javax.portlet.PortletSession;
import javax.portlet.annotations.PortletSessionScoped;

/**
 * @author Neil Griffin
 */
public class SessionScopedAnnotatedTypeImpl<X> extends AnnotatedTypeWrapper<X> {

	public SessionScopedAnnotatedTypeImpl(
		AnnotatedType<X> annotatedType,
		Set<Class<? extends Annotation>> annotationClasses) {

		super(annotatedType);

		for (Annotation annotation : annotatedType.getAnnotations()) {
			Class<? extends Annotation> annotationType =
				annotation.annotationType();

			if (!annotationType.equals(SessionScoped.class)) {
				_annotations.add(annotation);
			}
		}

		if (!annotationClasses.contains(PortletSessionScoped.class)) {
			_annotations.add(_portletSessionScoped);
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

	private static final Annotation _portletSessionScoped =
		new PortletSessionScoped() {

			@Override
			public Class<? extends Annotation> annotationType() {
				return PortletSessionScoped.class;
			}

			@Override
			public int value() {
				return PortletSession.PORTLET_SCOPE;
			}

		};

	private final Set<Annotation> _annotations = new HashSet<>();

}