/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.channel.internal.jaxrs.exception.mapper;

import com.liferay.commerce.shipping.engine.fixed.exception.DuplicateCommerceShippingFixedOptionQualifierException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Headless.Commerce.Admin.Channel)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Headless.Commerce.Admin.Order.DuplicateShippingFixedOptionQualifierExceptionMapper"
	},
	service = ExceptionMapper.class
)
@Provider
public class DuplicateShippingFixedOptionQualifierExceptionMapper
	extends BaseExceptionMapper
		<DuplicateCommerceShippingFixedOptionQualifierException> {

	@Override
	protected Problem getProblem(
		DuplicateCommerceShippingFixedOptionQualifierException
			duplicateCommerceShippingFixedOptionQualifierException) {

		return new Problem(
			null, Response.Status.CONFLICT,
			_language.get(
				_acceptLanguage.getPreferredLocale(),
				"the-shipping-fixed-option-qualifier-already-exists"),
			DuplicateCommerceShippingFixedOptionQualifierException.class.
				getName());
	}

	@Context
	private AcceptLanguage _acceptLanguage;

	@Reference
	private Language _language;

}