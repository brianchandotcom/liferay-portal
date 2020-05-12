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

package com.liferay.bean.portlet.spring.extension.internal.mvc;

import com.liferay.bean.portlet.extension.BeanPortletMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import java.util.Set;

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
 * @author Neil Griffin
 */
public class BeanValidationInterceptor extends BeanPortletMethodInterceptor {

	public BeanValidationInterceptor(
		BeanPortletMethod beanPortletMethod, boolean controller,
		MessageInterpolator messageInterpolator,
		MutableBindingResult mutableBindingResult, MvcContext mvcContext,
		Object validationObject, Validator validator) {

		super(beanPortletMethod, controller);

		_messageInterpolator = messageInterpolator;
		_mutableBindingResult = mutableBindingResult;
		_mvcContext = mvcContext;
		_validationObject = validationObject;
		_validator = validator;
	}

	@Override
	public Object invoke(Object... args) throws ReflectiveOperationException {
		if ((_validator == null) || !isController()) {
			return super.invoke(args);
		}

		Set<ConstraintViolation<Object>> constraintViolations =
			_validator.validate(_validationObject);

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

				_mutableBindingResult.addValidationError(
					new ValidationErrorImpl(
						constraintViolation, interpolatedMessage, paramName));
			}
		}

		return super.invoke(args);
	}

	private final MessageInterpolator _messageInterpolator;
	private final MutableBindingResult _mutableBindingResult;
	private final MvcContext _mvcContext;
	private final Object _validationObject;
	private final Validator _validator;

}