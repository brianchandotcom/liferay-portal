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

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.io.Serializable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.Set;

import javax.annotation.Priority;

import javax.enterprise.inject.spi.BeanManager;

import javax.inject.Inject;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import javax.mvc.MvcContext;

import javax.validation.ConstraintViolation;
import javax.validation.ElementKind;
import javax.validation.MessageInterpolator;
import javax.validation.Path;
import javax.validation.Validator;

import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.QueryParam;

/**
 * @author  Neil Griffin
 */
@BeanValidationInterceptorBinding
@Interceptor
@Priority(Interceptor.Priority.LIBRARY_BEFORE)
public class BeanValidationInterceptor implements Serializable {

	@AroundInvoke
	public Object validateMethodInvocation(InvocationContext invocationContext)
		throws Exception {

		if (_validator == null) {
			return invocationContext.proceed();
		}

		Set<ConstraintViolation<Object>> constraintViolations =
			_validator.validate(invocationContext.getTarget());

		for (ConstraintViolation<Object> constraintViolation :
				constraintViolations) {

			Path propertyPath = constraintViolation.getPropertyPath();

			Path.Node lastPathNode = null;

			for (Path.Node pathNode : propertyPath) {
				lastPathNode = pathNode;
			}

			if ((lastPathNode != null) &&
				(lastPathNode.getKind() == ElementKind.PROPERTY)) {

				Object leafBean = constraintViolation.getLeafBean();

				Class<?> leafBeanClass = leafBean.getClass();

				BeanInfo beanInfo = Introspector.getBeanInfo(
					leafBean.getClass());

				PropertyDescriptor[] propertyDescriptors =
					beanInfo.getPropertyDescriptors();

				for (PropertyDescriptor propertyDescriptor :
						propertyDescriptors) {

					String propertyDescriptorName =
						propertyDescriptor.getName();

					if (propertyDescriptorName.equals("targetClass")) {
						Method method = propertyDescriptor.getReadMethod();

						leafBeanClass = (Class<?>)method.invoke(leafBean);
					}
				}

				Field field = leafBeanClass.getDeclaredField(
					lastPathNode.getName());

				String paramName = null;

				Annotation[] annotations = field.getAnnotations();

				for (Annotation annotation : annotations) {
					if (annotation instanceof CookieParam) {
						CookieParam cookieParam = (CookieParam)annotation;

						paramName = cookieParam.value();

						break;
					}
					else if (annotation instanceof FormParam) {
						FormParam formParam = (FormParam)annotation;

						paramName = formParam.value();

						break;
					}
					else if (annotation instanceof HeaderParam) {
						HeaderParam headerParam = (HeaderParam)annotation;

						paramName = headerParam.value();

						break;
					}
					else if (annotation instanceof QueryParam) {
						QueryParam queryParam = (QueryParam)annotation;

						paramName = queryParam.value();
					}
				}

				String interpolatedMessage = constraintViolation.getMessage();

				if (_messageInterpolator != null) {
					interpolatedMessage = _messageInterpolator.interpolate(
						constraintViolation.getMessageTemplate(),
						new MessageInterpolatorContextImpl(constraintViolation),
						_mvcContext.getLocale());
				}

				MutableBindingResult mutableBindingResult =
					BeanUtil.getMutableBindingResult(_beanManager);

				mutableBindingResult.addValidationError(
					new ValidationErrorImpl(
						constraintViolation, interpolatedMessage, paramName));
			}
		}

		return invocationContext.proceed();
	}

	private static final long serialVersionUID = 2378576156374329311L;

	@Inject
	private BeanManager _beanManager;

	@BeanValidationMessageInterpolator
	@Inject
	private MessageInterpolator _messageInterpolator;

	@Inject
	private MvcContext _mvcContext;

	@BeanValidationValidator
	@Inject
	private Validator _validator;

}