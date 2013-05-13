/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.spring.annotation;

import com.liferay.portal.kernel.annotation.AnnotationLocator;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.spring.transaction.TransactionAttributeBuilder;

import java.io.Serializable;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import org.springframework.transaction.annotation.TransactionAnnotationParser;
import org.springframework.transaction.interceptor.TransactionAttribute;

/**
 * @author     Michael Young
 * @author     Shuyang Zhou
 * @deprecated As of 6.1.0
 */
public class PortalTransactionAnnotationParser
	implements TransactionAnnotationParser, Serializable {

	@Override
	public TransactionAttribute parseTransactionAnnotation(
		AnnotatedElement annotatedElement) {

		Transactional transactional = null;

		if (annotatedElement instanceof Method) {
			Method method = (Method)annotatedElement;

			transactional = AnnotationLocator.locate(
				method, method.getDeclaringClass(), Transactional.class);
		}
		else {
			transactional = AnnotationLocator.locate(
				(Class<?>)annotatedElement, Transactional.class);
		}

		return TransactionAttributeBuilder.build(transactional);
	}

}