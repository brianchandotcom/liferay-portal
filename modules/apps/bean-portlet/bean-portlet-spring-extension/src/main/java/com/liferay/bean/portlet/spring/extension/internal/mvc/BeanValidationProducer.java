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

import javax.annotation.PostConstruct;

import javax.validation.MessageInterpolator;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Neil Griffin
 */
@Configuration
public class BeanValidationProducer {

	@Bean
	@BeanValidationMessageInterpolator
	public MessageInterpolator getMessageInterpolator() {
		return _messageInterpolator;
	}

	@Bean
	@BeanValidationValidator
	public Validator getValidator() {
		return _validator;
	}

	@PostConstruct
	public void postConstruct() {
		_messageInterpolator = _validatorFactory.getMessageInterpolator();
		_validator = _validatorFactory.getValidator();
	}

	private MessageInterpolator _messageInterpolator;
	private Validator _validator;

	@Autowired
	private ValidatorFactory _validatorFactory;

}