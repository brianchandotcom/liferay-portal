/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.blueprint.parameter.contributor;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.search.experiences.blueprint.parameter.DoubleSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.SXPParameter;
import com.liferay.search.experiences.blueprint.parameter.StringSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.contributor.SXPParameterContributor;
import com.liferay.search.experiences.blueprint.parameter.contributor.SXPParameterContributorDefinition;
import com.liferay.search.experiences.internal.blueprint.data.provider.GeoLocationDataProvider;
import com.liferay.search.experiences.model.SXPBlueprint;

import java.io.IOException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Petteri Karttunen
 */
public class IPStackParameterContributor implements SXPParameterContributor {

	public IPStackParameterContributor(
		GeoLocationDataProvider geoLocationDataProvider) {

		_geoLocationDataProvider = geoLocationDataProvider;

		_configuration = _getConfiguration();
	}

	@Override
	public void contribute(
		SearchContext searchContext, SXPBlueprint sxpBlueprint,
		Set<SXPParameter> sxpParameters) {

		if (!_isEnabled()) {
			return;
		}

		String ipAddress = (String)searchContext.getAttribute(
			"search.experiences.ipaddress");

		if (Validator.isBlank(ipAddress)) {
			return;
		}

		_contribute(ipAddress, sxpParameters);
	}

	@Override
	public String getSXPParameterCategoryNameKey() {
		return "ip";
	}

	@Override
	public List<SXPParameterContributorDefinition>
		getSXPParameterContributorDefinitions() {

		if (!_isEnabled()) {
			return Collections.<SXPParameterContributorDefinition>emptyList();
		}

		return Arrays.asList(
			new SXPParameterContributorDefinition(
				StringSXPParameter.class, "city", "ipstack.city"),
			new SXPParameterContributorDefinition(
				StringSXPParameter.class, "continent-code",
				"ipstack.continent_code"),
			new SXPParameterContributorDefinition(
				StringSXPParameter.class, "continent-name",
				"ipstack.continent_name"),
			new SXPParameterContributorDefinition(
				StringSXPParameter.class, "country-code",
				"ipstack.country_code"),
			new SXPParameterContributorDefinition(
				StringSXPParameter.class, "country-name",
				"ipstack.country_name"),
			new SXPParameterContributorDefinition(
				DoubleSXPParameter.class, "latitude", "ipstack.latitude"),
			new SXPParameterContributorDefinition(
				DoubleSXPParameter.class, "longitude", "ipstack.longitude"),
			new SXPParameterContributorDefinition(
				StringSXPParameter.class, "region-code", "ipstack.region_code"),
			new SXPParameterContributorDefinition(
				StringSXPParameter.class, "region-name", "ipstack.region_name"),
			new SXPParameterContributorDefinition(
				StringSXPParameter.class, "zip-code", "ipstack.zip"));
	}

	private void _contribute(
		String ipAddress, Set<SXPParameter> sxpParameters) {

		Optional<JSONObject> geoLocationJSONObjectOptional =
			_geoLocationDataProvider.getGeoLocationData(ipAddress);

		if (!geoLocationJSONObjectOptional.isPresent()) {
			return;
		}

		JSONObject geoLocationJSONObject = geoLocationJSONObjectOptional.get();

		sxpParameters.add(
			new StringSXPParameter(
				"ipstack.city", true, geoLocationJSONObject.getString("city")));

		sxpParameters.add(
			new StringSXPParameter(
				"ipstack.continent_code", true,
				geoLocationJSONObject.getString("continent_code")));

		sxpParameters.add(
			new StringSXPParameter(
				"ipstack.continent_name", true,
				geoLocationJSONObject.getString("continent_name")));

		sxpParameters.add(
			new StringSXPParameter(
				"ipstack.country_code", true,
				geoLocationJSONObject.getString("country_code")));

		sxpParameters.add(
			new StringSXPParameter(
				"ipstack.country_name", true,
				geoLocationJSONObject.getString("country_name")));

		sxpParameters.add(
			new DoubleSXPParameter(
				"ipstack.latitude", true,
				geoLocationJSONObject.getDouble("latitude")));

		sxpParameters.add(
			new DoubleSXPParameter(
				"ipstack.longitude", true,
				geoLocationJSONObject.getDouble("longitude")));

		sxpParameters.add(
			new StringSXPParameter(
				"ipstack.region_code", true,
				geoLocationJSONObject.getString("region_code")));

		sxpParameters.add(
			new StringSXPParameter(
				"ipstack.region_name", true,
				geoLocationJSONObject.getString("region_name")));

		sxpParameters.add(
			new StringSXPParameter(
				"ipstack.zip", true, geoLocationJSONObject.getString("zip")));
	}

	private Configuration _getConfiguration() {
		Bundle bundle = FrameworkUtil.getBundle(getClass());

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceReference<ConfigurationAdmin> serviceReference =
			bundleContext.getServiceReference(ConfigurationAdmin.class);

		ConfigurationAdmin configurationAdmin = bundleContext.getService(
			serviceReference);

		try {
			return configurationAdmin.getConfiguration(
				"com.liferay.search.experiences.configuration." +
					"IPStackConfiguration",
				StringPool.QUESTION);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
		finally {
			bundleContext.ungetService(serviceReference);
		}
	}

	private boolean _isEnabled() {
		Dictionary<String, Object> dictionary = _configuration.getProperties();

		return GetterUtil.getBoolean(dictionary.get("is-enabled"));
	}

	private final Configuration _configuration;
	private final GeoLocationDataProvider _geoLocationDataProvider;

}