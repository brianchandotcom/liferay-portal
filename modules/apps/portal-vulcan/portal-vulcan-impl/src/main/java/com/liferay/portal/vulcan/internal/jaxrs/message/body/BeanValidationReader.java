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

package com.liferay.portal.vulcan.internal.jaxrs.message.body;

import java.io.IOException;

import java.lang.reflect.Method;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.InterceptorChain;
import org.apache.cxf.jaxrs.lifecycle.ResourceProvider;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.jaxrs.model.OperationResourceInfo;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.validation.AbstractValidationInterceptor;

/**
 * @author Javier Gamarra
 */
@Provider
public class BeanValidationReader
	extends AbstractValidationInterceptor implements ContainerRequestFilter {

	public BeanValidationReader() {
		super("pre-invoke");
	}

	public BeanValidationReader(String phase) {
		super(phase);
	}

	@Override
	public void filter(ContainerRequestContext containerRequestContext)
		throws IOException {

		Message currentMessage = PhaseInterceptorChain.getCurrentMessage();

		InterceptorChain chain = currentMessage.getInterceptorChain();

		chain.add(this);
	}

	public void handleMessage(Message message) throws Fault {
		Object serviceObject = getServiceObject(message);

		if (serviceObject != null) {
			Method method = getServiceMethod(message);

			if (method != null) {
				List<Object> arguments = MessageContentsList.getContentsList(
					message);

				handleValidation(message, serviceObject, method, arguments);
			}
		}
	}

	@Override
	protected Object getServiceObject(Message message) {
		Exchange exchange = message.getExchange();

		final OperationResourceInfo operationResourceInfo = exchange.get(
			OperationResourceInfo.class);

		if (operationResourceInfo == null) {
			return null;
		}

		ClassResourceInfo classResourceInfo =
			operationResourceInfo.getClassResourceInfo();

		if (!classResourceInfo.isRoot()) {
			return exchange.get("org.apache.cxf.service.object.last");
		}

		final ResourceProvider resourceProvider =
			classResourceInfo.getResourceProvider();

		return resourceProvider.getInstance(message);
	}

	@Override
	protected void handleValidation(
		Message message, Object resourceInstance, Method method,
		List<Object> arguments) {

		if (!arguments.isEmpty()) {
			Validator validator = _getValidator();

			ExecutableValidator executableValidator =
				validator.forExecutables();

			Set<ConstraintViolation<Object>> constraintViolations =
				executableValidator.validateParameters(
					resourceInstance, method, arguments.toArray());

			if (!constraintViolations.isEmpty()) {
				throw new ConstraintViolationException(constraintViolations);
			}
		}
	}

	private Validator _getValidator() {
		ValidatorFactory validatorFactory =
			ValidatorFactoryHolder.VALIDATOR_FACTORY;

		return validatorFactory.getValidator();
	}

}