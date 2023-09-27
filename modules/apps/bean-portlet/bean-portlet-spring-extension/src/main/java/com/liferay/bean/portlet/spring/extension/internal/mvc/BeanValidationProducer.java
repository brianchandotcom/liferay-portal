/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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