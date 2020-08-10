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

package com.liferay.commerce.frontend.internal.address;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.liferay.commerce.context.CommerceContextFactory;
import com.liferay.commerce.frontend.internal.address.model.CountryModel;
import com.liferay.commerce.frontend.internal.address.model.RegionModel;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceCountryService;
import com.liferay.commerce.service.CommerceRegionService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = AddressResource.class)
public class AddressResource {

	@GET
	@Path("/address/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCommerceAddress(
		@PathParam("id") long commerceAddressId) {

		try {
			CommerceAddress commerceAddress =
				_commerceAddressService.fetchCommerceAddress(commerceAddressId);

			if (commerceAddress != null) {
				String json = _OBJECT_MAPPER.writeValueAsString(
					JSONFactoryUtil.looseSerialize(commerceAddress));

				return Response.ok(
					json, MediaType.APPLICATION_JSON
				).build();
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return Response.status(
			Response.Status.INTERNAL_SERVER_ERROR
		).build();
	}

	@GET
	@Path("/address/regions/{countryId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCommerceRegions(
		@PathParam("countryId") long commerceCountryId,
		@Context ThemeDisplay themeDisplay) {

		List<RegionModel> regionModels = new ArrayList<>();

		List<CommerceRegion> commerceRegions =
			_commerceRegionService.getCommerceRegions(commerceCountryId, true);

		for (CommerceRegion commerceRegion : commerceRegions) {
			regionModels.add(
				new RegionModel(
					commerceRegion.getCommerceRegionId(),
					commerceRegion.getName()));
		}

		try {
			String json = _OBJECT_MAPPER.writeValueAsString(regionModels);

			return Response.ok(
				json, MediaType.APPLICATION_JSON
			).build();
		}
		catch (JsonProcessingException jpe) {
			_log.error(jpe, jpe);
		}

		return Response.status(
			Response.Status.INTERNAL_SERVER_ERROR
		).build();
	}

	/**
	 * @deprecated As of Mueller (7.2.x), passing companyId is redundant
	 */
	@Deprecated
	@GET
	@Path("/address/countries")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getShippingCommerceCountries(
		@QueryParam("companyId") long companyId,
		@Context ThemeDisplay themeDisplay) {

		List<CommerceCountry> commerceCountries =
			_commerceCountryService.getCommerceCountries(companyId, true);

		return _getCommerceCountries(
			commerceCountries, themeDisplay.getLanguageId());
	}

	@GET
	@Path("/address/countries")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getShippingCommerceCountries(
		@Context ThemeDisplay themeDisplay) {

		List<CommerceCountry> commerceCountries =
			_commerceCountryService.getCommerceCountries(
				themeDisplay.getCompanyId(), true);

		return _getCommerceCountries(
			commerceCountries, themeDisplay.getLanguageId());
	}

	@GET
	@Path("/address/countries-by-channel-id")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getShippingCommerceCountriesByChannelId(
		@QueryParam("channelId") long channelId,
		@Context ThemeDisplay themeDisplay) {

		List<CommerceCountry> commerceCountries =
			_commerceCountryService.getShippingCommerceCountriesByChannelId(
				channelId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		return _getCommerceCountries(
			commerceCountries, themeDisplay.getLanguageId());
	}

	private Response _getCommerceCountries(
		List<CommerceCountry> commerceCountries, String languageId) {

		List<CountryModel> countryModels = new ArrayList<>();

		for (CommerceCountry commerceCountry : commerceCountries) {
			countryModels.add(
				new CountryModel(
					commerceCountry.getCommerceCountryId(),
					commerceCountry.getName(languageId),
					commerceCountry.isBillingAllowed(),
					commerceCountry.isShippingAllowed()));
		}

		try {
			String json = _OBJECT_MAPPER.writeValueAsString(countryModels);

			return Response.ok(
				json, MediaType.APPLICATION_JSON
			).build();
		}
		catch (JsonProcessingException jpe) {
			_log.error(jpe, jpe);
		}

		return Response.status(
			Response.Status.INTERNAL_SERVER_ERROR
		).build();
	}

	private static final ObjectMapper _OBJECT_MAPPER = new ObjectMapper() {
		{
			configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
			enable(SerializationFeature.INDENT_OUTPUT);
		}
	};

	private static final Log _log = LogFactoryUtil.getLog(
		AddressResource.class);

	@Reference
	private CommerceAddressService _commerceAddressService;

	@Reference
	private CommerceContextFactory _commerceContextFactory;

	@Reference
	private CommerceCountryService _commerceCountryService;

	@Reference
	private CommerceRegionService _commerceRegionService;

}