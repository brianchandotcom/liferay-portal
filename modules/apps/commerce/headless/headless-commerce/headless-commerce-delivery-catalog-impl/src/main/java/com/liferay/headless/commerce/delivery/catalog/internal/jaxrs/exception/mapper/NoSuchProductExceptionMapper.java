/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.catalog.internal.jaxrs.exception.mapper;

import com.liferay.commerce.product.exception.NoSuchCProductException;
import com.liferay.headless.commerce.core.exception.mapper.BaseExceptionMapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.osgi.service.component.annotations.Component;

/**
 * @author Aandrea Sbarra
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Headless.Commerce.Delivery.Catalog)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Headless.Commerce.Delivery.Catalog.NoSuchProductExceptionMapper"
	},
	service = ExceptionMapper.class
)
@Provider
public class NoSuchProductExceptionMapper
	extends BaseExceptionMapper<NoSuchCProductException> {

	@Override
	public String getErrorDescription() {
		return "Product not found";
	}

	@Override
	public Response.Status getStatus() {
		return Response.Status.NOT_FOUND;
	}

}