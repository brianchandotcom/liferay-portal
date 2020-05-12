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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Iterator;

import javax.annotation.PostConstruct;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;

import javax.inject.Inject;

import javax.validation.MessageInterpolator;
import javax.validation.NoProviderFoundException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * @author Neil Griffin
 */
@ApplicationScoped
public class BeanValidationProducer {

	@BeanValidationMessageInterpolator
	@Dependent
	@Produces
	public MessageInterpolator getMessageInterpolator() {
		return _messageInterpolator;
	}

	@BeanValidationValidator
	@Dependent
	@Produces
	public Validator getValidator() {
		return _validator;
	}

	@PostConstruct
	public void postConstruct() {
		ValidatorFactory validatorFactory = null;

		Iterator<ValidatorFactory> iterator =
			_validatorFactoryInstance.iterator();

		if (iterator.hasNext()) {
			validatorFactory = iterator.next();
		}

		if (validatorFactory == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"The ValidatorFactory was not injected. If you are using " +
						"Hibernate Validator, then include the " +
							"hibernate-validator-cdi dependency");
			}

			try {
				validatorFactory = Validation.buildDefaultValidatorFactory();
			}
			catch (NoProviderFoundException noProviderFoundException) {
				_log.error(noProviderFoundException, noProviderFoundException);
			}
		}

		if (validatorFactory != null) {
			_messageInterpolator = validatorFactory.getMessageInterpolator();

			if ((_messageInterpolator == null) && _log.isWarnEnabled()) {
				_log.warn(
					"The bean validation MessageInterpolator is not available");
			}

			_validator = validatorFactory.getValidator();

			if ((_validator == null) && _log.isWarnEnabled()) {
				_log.warn("The bean validation validator is not available");
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BeanValidationProducer.class);

	private MessageInterpolator _messageInterpolator;
	private Validator _validator;

	@Inject
	private Instance<ValidatorFactory> _validatorFactoryInstance;

}