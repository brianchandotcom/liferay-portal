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

package com.liferay.headless.commerce.delivery.catalog.internal.resource.v1_0;

import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Attachment;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Category;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.MappedProduct;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Pin;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Product;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.ProductOption;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.ProductSpecification;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.RelatedProduct;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Sku;
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
 * @author Andrea Sbarra
 * @generated
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/openapi.properties",
	service = OpenAPIResourceImpl.class
)
@Generated("")
@OpenAPIDefinition(
	info = @Info(description = "Headless Commerce Delivery Catalog API. A Java client JAR is available for use with the group ID 'com.liferay', artifact ID 'com.liferay.headless.commerce.delivery.catalog.client', and version '4.0.20'.", license = @License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0.html"), title = "Headless Commerce Delivery Catalog API", version = "v1.0")
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
				put(AttachmentResourceImpl.class, Attachment.class);
				put(CategoryResourceImpl.class, Category.class);
				put(MappedProductResourceImpl.class, MappedProduct.class);
				put(PinResourceImpl.class, Pin.class);
				put(ProductOptionResourceImpl.class, ProductOption.class);
				put(ProductResourceImpl.class, Product.class);
				put(
					ProductSpecificationResourceImpl.class,
					ProductSpecification.class);
				put(RelatedProductResourceImpl.class, RelatedProduct.class);
				put(SkuResourceImpl.class, Sku.class);

				put(OpenAPIResourceImpl.class, null);
			}
		};

	@Context
	private Company _company;

}