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

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.bval.jsr.ApacheValidationProvider;

/**
 * @author Javier Gamarra
 */
public enum ApacheValidatorFactory {

	SINGLE_INSTANCE {

		private ValidatorFactory _validatorFactory = Validation.byProvider(
			ApacheValidationProvider.class
		).configure(
		).buildValidatorFactory();

		@Override
		public Validator getValidator() {
			return _validatorFactory.getValidator();
		}

	};

	public abstract Validator getValidator();

}