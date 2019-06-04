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

package com.liferay.portal.vulcan.internal.jaxrs.validation;

import java.util.Collections;
import java.util.List;

import javax.validation.Validation;
import javax.validation.ValidationProviderResolver;
import javax.validation.Validator;
import javax.validation.spi.ValidationProvider;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

/**
 * @author Javier Gamarra
 */
public class ValidatorFactory {

	public static Validator getValidator() {
		HibernateValidatorConfiguration hibernateValidatorConfiguration =
			(HibernateValidatorConfiguration)Validation.byDefaultProvider(
			).providerResolver(
				new OSGiServiceDiscoverer()
			).configure();

		hibernateValidatorConfiguration.
			allowOverridingMethodAlterParameterConstraint(true);

		hibernateValidatorConfiguration.messageInterpolator(
			new ParameterMessageInterpolator());

		javax.validation.ValidatorFactory validatorFactory =
			hibernateValidatorConfiguration.buildValidatorFactory();

		return validatorFactory.getValidator();
	}

	private static class OSGiServiceDiscoverer
		implements ValidationProviderResolver {

		@Override
		public List<ValidationProvider<?>> getValidationProviders() {
			return Collections.singletonList(new HibernateValidator());
		}

	}

}