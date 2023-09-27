/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.inventory.internal.jaxrs.exception.mapper;

import com.liferay.commerce.product.exception.NoSuchChannelRelException;
import com.liferay.headless.commerce.core.exception.mapper.BaseExceptionMapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Crescenzo Rega
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Headless.Commerce.Admin.Inventory)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Headless.Commerce.Admin.Inventory.NoSuchChannelRelExceptionMapper"
	},
	service = ExceptionMapper.class
)
public class NoSuchChannelRelExceptionMapper
	extends BaseExceptionMapper<NoSuchChannelRelException> {

	@Override
	public String getErrorDescription() {
		return "Commerce Channel Rel not found";
	}

	@Override
	public Response.Status getStatus() {
		return Response.Status.NOT_FOUND;
	}

}