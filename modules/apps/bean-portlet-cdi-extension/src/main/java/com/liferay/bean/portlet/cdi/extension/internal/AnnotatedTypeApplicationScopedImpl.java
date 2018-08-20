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

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.spi.AnnotatedType;

import javax.portlet.annotations.PortletRequestScoped;
import javax.portlet.annotations.PortletSessionScoped;

/**
 * @author Neil Griffin
 */
public class AnnotatedTypeApplicationScopedImpl<X>
	extends AnnotatedTypeWrapper<X> {

	public AnnotatedTypeApplicationScopedImpl(AnnotatedType<X> annotatedType) {
		super(annotatedType);

		_annotations = new HashSet<>();

		for (Annotation annotation : annotatedType.getAnnotations()) {
			Class<? extends Annotation> clazz = annotation.annotationType();

			if (!clazz.equals(ConversationScoped.class) &&
				!clazz.equals(RequestScoped.class) &&
				!clazz.equals(PortletRequestScoped.class) &&
				!clazz.equals(PortletSessionScoped.class) &&
				!clazz.equals(SessionScoped.class)) {

				_annotations.add(annotation);
			}
		}

		_annotations.add(
			DefaultApplicationScoped.class.getAnnotation(
				ApplicationScoped.class));
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

	private final Set<Annotation> _annotations;

}