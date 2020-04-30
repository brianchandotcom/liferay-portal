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

package com.liferay.bean.portlet.spring.extension.internal;

import java.lang.annotation.Annotation;

import javax.portlet.annotations.ContextPath;
import javax.portlet.annotations.Namespace;
import javax.portlet.annotations.PortletName;
import javax.portlet.annotations.WindowId;

import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;

/**
 * @author Neil Griffin
 */
public class JSR362AutowireCandidateResolver
	extends ContextAnnotationAutowireCandidateResolver {

	@Override
	protected boolean isQualifier(Class<? extends Annotation> annotationType) {
		boolean qualifier = super.isQualifier(annotationType);

		if (!qualifier &&
			(ContextPath.class.isAssignableFrom(annotationType) ||
			 Namespace.class.isAssignableFrom(annotationType) ||
			 PortletName.class.isAssignableFrom(annotationType) ||
			 WindowId.class.isAssignableFrom(annotationType))) {

			qualifier = true;
		}

		return qualifier;
	}

}