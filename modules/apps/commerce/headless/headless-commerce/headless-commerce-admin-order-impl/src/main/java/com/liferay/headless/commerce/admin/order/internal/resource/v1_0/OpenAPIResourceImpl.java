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

package com.liferay.headless.commerce.admin.order.internal.resource.v1_0;

import com.liferay.headless.commerce.admin.order.dto.v1_0.Account;
import com.liferay.headless.commerce.admin.order.dto.v1_0.AccountGroup;
import com.liferay.headless.commerce.admin.order.dto.v1_0.BillingAddress;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Channel;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderItem;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderNote;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRule;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRuleAccount;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRuleAccountGroup;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRuleChannel;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRuleOrderType;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderType;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderTypeChannel;
import com.liferay.headless.commerce.admin.order.dto.v1_0.ShippingAddress;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Term;
import com.liferay.headless.commerce.admin.order.dto.v1_0.TermOrderType;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.resource.OpenAPIResource;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/openapi.properties",
	service = OpenAPIResourceImpl.class
)
@Generated("")
@OpenAPIDefinition(
	info = @Info(description = "Liferay Commerce Admin Order API. A Java client JAR is available for use with the group ID 'com.liferay', artifact ID 'com.liferay.headless.commerce.admin.order.client', and version '4.0.21'.", license = @License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0.html"), title = "Liferay Commerce Admin Order API", version = "v1.0")
)
@Path("/v1.0")
public class OpenAPIResourceImpl {

	@GET
	@Path("/openapi.{type:json|yaml}")
	@Produces({MediaType.APPLICATION_JSON, "application/yaml"})
	public Response getOpenAPI(@PathParam("type") String type)
		throws Exception {

		try {
			Class<? extends OpenAPIResource> clazz =
				_openAPIResource.getClass();

			clazz.getMethod(
				"getOpenAPI", long.class, Map.class, String.class,
				UriInfo.class);
		}
		catch (NoSuchMethodException noSuchMethodException) {
			return _openAPIResource.getOpenAPI(_resourceClasses.keySet(), type);
		}

		return _openAPIResource.getOpenAPI(
			_company.getCompanyId(), _resourceClasses, type, _uriInfo);
	}

	@Reference
	private OpenAPIResource _openAPIResource;

	@Context
	private UriInfo _uriInfo;

	private final Map<Class<?>, Class<?>> _resourceClasses =
		new HashMap<Class<?>, Class<?>>() {
			{
				put(AccountGroupResourceImpl.class, AccountGroup.class);
				put(AccountResourceImpl.class, Account.class);
				put(BillingAddressResourceImpl.class, BillingAddress.class);
				put(ChannelResourceImpl.class, Channel.class);
				put(OrderItemResourceImpl.class, OrderItem.class);
				put(OrderNoteResourceImpl.class, OrderNote.class);
				put(OrderResourceImpl.class, Order.class);
				put(
					OrderRuleAccountGroupResourceImpl.class,
					OrderRuleAccountGroup.class);
				put(OrderRuleAccountResourceImpl.class, OrderRuleAccount.class);
				put(OrderRuleChannelResourceImpl.class, OrderRuleChannel.class);
				put(
					OrderRuleOrderTypeResourceImpl.class,
					OrderRuleOrderType.class);
				put(OrderRuleResourceImpl.class, OrderRule.class);
				put(OrderTypeChannelResourceImpl.class, OrderTypeChannel.class);
				put(OrderTypeResourceImpl.class, OrderType.class);
				put(ShippingAddressResourceImpl.class, ShippingAddress.class);
				put(TermOrderTypeResourceImpl.class, TermOrderType.class);
				put(TermResourceImpl.class, Term.class);

				put(OpenAPIResourceImpl.class, null);
			}
		};

	@Context
	private Company _company;

}