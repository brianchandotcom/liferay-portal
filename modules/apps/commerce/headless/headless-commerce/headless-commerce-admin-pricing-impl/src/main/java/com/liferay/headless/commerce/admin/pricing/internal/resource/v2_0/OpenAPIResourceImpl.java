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

package com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0;

import com.liferay.headless.commerce.admin.pricing.dto.v2_0.Account;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.AccountGroup;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.Category;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.Channel;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.Discount;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountAccount;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountAccountGroup;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountCategory;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountChannel;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountOrderType;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountProduct;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountProductGroup;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountRule;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountSku;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.OrderType;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceEntry;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceList;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceListAccount;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceListAccountGroup;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceListChannel;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceListDiscount;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceListOrderType;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceModifier;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceModifierCategory;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceModifierProduct;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceModifierProductGroup;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.Product;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.ProductGroup;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.Sku;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.TierPrice;
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
 * @author Zoltán Takács
 * @generated
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v2_0/openapi.properties",
	service = OpenAPIResourceImpl.class
)
@Generated("")
@OpenAPIDefinition(
	info = @Info(description = "Liferay Commerce Admin Pricing API. A Java client JAR is available for use with the group ID 'com.liferay', artifact ID 'com.liferay.headless.commerce.admin.pricing.client', and version '4.0.15'.", license = @License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0.html"), title = "Liferay Commerce Admin Pricing API", version = "v2.0")
)
@Path("/v2.0")
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
				put(CategoryResourceImpl.class, Category.class);
				put(ChannelResourceImpl.class, Channel.class);
				put(
					DiscountAccountGroupResourceImpl.class,
					DiscountAccountGroup.class);
				put(DiscountAccountResourceImpl.class, DiscountAccount.class);
				put(DiscountCategoryResourceImpl.class, DiscountCategory.class);
				put(DiscountChannelResourceImpl.class, DiscountChannel.class);
				put(
					DiscountOrderTypeResourceImpl.class,
					DiscountOrderType.class);
				put(
					DiscountProductGroupResourceImpl.class,
					DiscountProductGroup.class);
				put(DiscountProductResourceImpl.class, DiscountProduct.class);
				put(DiscountResourceImpl.class, Discount.class);
				put(DiscountRuleResourceImpl.class, DiscountRule.class);
				put(DiscountSkuResourceImpl.class, DiscountSku.class);
				put(OrderTypeResourceImpl.class, OrderType.class);
				put(PriceEntryResourceImpl.class, PriceEntry.class);
				put(
					PriceListAccountGroupResourceImpl.class,
					PriceListAccountGroup.class);
				put(PriceListAccountResourceImpl.class, PriceListAccount.class);
				put(PriceListChannelResourceImpl.class, PriceListChannel.class);
				put(
					PriceListDiscountResourceImpl.class,
					PriceListDiscount.class);
				put(
					PriceListOrderTypeResourceImpl.class,
					PriceListOrderType.class);
				put(PriceListResourceImpl.class, PriceList.class);
				put(
					PriceModifierCategoryResourceImpl.class,
					PriceModifierCategory.class);
				put(
					PriceModifierProductGroupResourceImpl.class,
					PriceModifierProductGroup.class);
				put(
					PriceModifierProductResourceImpl.class,
					PriceModifierProduct.class);
				put(PriceModifierResourceImpl.class, PriceModifier.class);
				put(ProductGroupResourceImpl.class, ProductGroup.class);
				put(ProductResourceImpl.class, Product.class);
				put(SkuResourceImpl.class, Sku.class);
				put(TierPriceResourceImpl.class, TierPrice.class);

				put(OpenAPIResourceImpl.class, null);
			}
		};

	@Context
	private Company _company;

}