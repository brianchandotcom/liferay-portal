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

package com.liferay.headless.admin.address.internal.graphql.servlet.v1_0;

import com.liferay.headless.admin.address.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.headless.admin.address.internal.graphql.query.v1_0.Query;
import com.liferay.headless.admin.address.internal.resource.v1_0.CountryResourceImpl;
import com.liferay.headless.admin.address.internal.resource.v1_0.RegionResourceImpl;
import com.liferay.headless.admin.address.resource.v1_0.CountryResource;
import com.liferay.headless.admin.address.resource.v1_0.RegionResource;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Drew Brokke
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setCountryResourceComponentServiceObjects(
			_countryResourceComponentServiceObjects);
		Mutation.setRegionResourceComponentServiceObjects(
			_regionResourceComponentServiceObjects);

		Query.setCountryResourceComponentServiceObjects(
			_countryResourceComponentServiceObjects);
		Query.setRegionResourceComponentServiceObjects(
			_regionResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Headless.Admin.Address";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/headless-admin-address-graphql/v1_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

	public ObjectValuePair<Class<?>, String> getResourceMethodObjectValuePair(
		String methodName, boolean mutation) {

		if (mutation) {
			return _resourceMethodObjectValuePairs.get(
				"mutation#" + methodName);
		}

		return _resourceMethodObjectValuePairs.get("query#" + methodName);
	}

	private static final Map<String, ObjectValuePair<Class<?>, String>>
		_resourceMethodObjectValuePairs =
			new HashMap<String, ObjectValuePair<Class<?>, String>>() {
				{
					put(
						"mutation#createCountry",
						new ObjectValuePair<>(
							CountryResourceImpl.class, "postCountry"));
					put(
						"mutation#createCountryBatch",
						new ObjectValuePair<>(
							CountryResourceImpl.class, "postCountryBatch"));
					put(
						"mutation#deleteCountry",
						new ObjectValuePair<>(
							CountryResourceImpl.class, "deleteCountry"));
					put(
						"mutation#deleteCountryBatch",
						new ObjectValuePair<>(
							CountryResourceImpl.class, "deleteCountryBatch"));
					put(
						"mutation#patchCountry",
						new ObjectValuePair<>(
							CountryResourceImpl.class, "patchCountry"));
					put(
						"mutation#updateCountry",
						new ObjectValuePair<>(
							CountryResourceImpl.class, "putCountry"));
					put(
						"mutation#updateCountryBatch",
						new ObjectValuePair<>(
							CountryResourceImpl.class, "putCountryBatch"));
					put(
						"mutation#createCountryRegionsPageExportBatch",
						new ObjectValuePair<>(
							RegionResourceImpl.class,
							"postCountryRegionsPageExportBatch"));
					put(
						"mutation#createCountryRegion",
						new ObjectValuePair<>(
							RegionResourceImpl.class, "postCountryRegion"));
					put(
						"mutation#createCountryRegionBatch",
						new ObjectValuePair<>(
							RegionResourceImpl.class,
							"postCountryRegionBatch"));
					put(
						"mutation#createRegionsPageExportBatch",
						new ObjectValuePair<>(
							RegionResourceImpl.class,
							"postRegionsPageExportBatch"));
					put(
						"mutation#deleteRegion",
						new ObjectValuePair<>(
							RegionResourceImpl.class, "deleteRegion"));
					put(
						"mutation#deleteRegionBatch",
						new ObjectValuePair<>(
							RegionResourceImpl.class, "deleteRegionBatch"));
					put(
						"mutation#patchRegion",
						new ObjectValuePair<>(
							RegionResourceImpl.class, "patchRegion"));
					put(
						"mutation#updateRegion",
						new ObjectValuePair<>(
							RegionResourceImpl.class, "putRegion"));
					put(
						"mutation#updateRegionBatch",
						new ObjectValuePair<>(
							RegionResourceImpl.class, "putRegionBatch"));

					put(
						"query#countries",
						new ObjectValuePair<>(
							CountryResourceImpl.class, "getCountriesPage"));
					put(
						"query#countryByA2",
						new ObjectValuePair<>(
							CountryResourceImpl.class, "getCountryByA2"));
					put(
						"query#countryByA3",
						new ObjectValuePair<>(
							CountryResourceImpl.class, "getCountryByA3"));
					put(
						"query#countryByName",
						new ObjectValuePair<>(
							CountryResourceImpl.class, "getCountryByName"));
					put(
						"query#countryByNumber",
						new ObjectValuePair<>(
							CountryResourceImpl.class, "getCountryByNumber"));
					put(
						"query#country",
						new ObjectValuePair<>(
							CountryResourceImpl.class, "getCountry"));
					put(
						"query#countryRegions",
						new ObjectValuePair<>(
							RegionResourceImpl.class, "getCountryRegionsPage"));
					put(
						"query#countryRegionByRegionCode",
						new ObjectValuePair<>(
							RegionResourceImpl.class,
							"getCountryRegionByRegionCode"));
					put(
						"query#regions",
						new ObjectValuePair<>(
							RegionResourceImpl.class, "getRegionsPage"));
					put(
						"query#region",
						new ObjectValuePair<>(
							RegionResourceImpl.class, "getRegion"));

					put(
						"query#Region.country",
						new ObjectValuePair<>(
							CountryResourceImpl.class, "getCountry"));
					put(
						"query#Country.regionByRegionCode",
						new ObjectValuePair<>(
							RegionResourceImpl.class,
							"getCountryRegionByRegionCode"));
				}
			};

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<CountryResource>
		_countryResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<RegionResource>
		_regionResourceComponentServiceObjects;

}