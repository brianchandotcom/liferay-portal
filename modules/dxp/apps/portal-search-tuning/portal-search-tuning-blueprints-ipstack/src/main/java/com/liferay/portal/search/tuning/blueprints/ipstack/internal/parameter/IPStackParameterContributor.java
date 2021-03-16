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

package com.liferay.portal.search.tuning.blueprints.ipstack.internal.parameter;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributes;
import com.liferay.portal.search.tuning.blueprints.engine.constants.ReservedParameterNames;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.DoubleParameter;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterDataBuilder;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterDefinition;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.StringParameter;
import com.liferay.portal.search.tuning.blueprints.engine.spi.dataprovider.GeoLocationDataProvider;
import com.liferay.portal.search.tuning.blueprints.engine.spi.parameter.ParameterContributor;
import com.liferay.portal.search.tuning.blueprints.ipstack.internal.configuration.IPStackConfiguration;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	configurationPid = "com.liferay.portal.search.tuning.blueprints.ipstack.internal.configuration.IPStackConfiguration",
	immediate = true, property = "name=ipstack",
	service = ParameterContributor.class
)
public class IPStackParameterContributor implements ParameterContributor {

	@Override
	public void contribute(
		ParameterDataBuilder parameterDataBuilder, Blueprint blueprint,
		BlueprintsAttributes blueprintsAttributes, Messages messages) {

		_contribute(
			parameterDataBuilder, _getIpAddress(blueprintsAttributes),
			messages);
	}

	@Override
	public String getCategoryNameKey() {
		return "ip";
	}

	@Override
	public List<ParameterDefinition> getParameterDefinitions() {
		List<ParameterDefinition> parameterDefinitions = new ArrayList<>();

		if (!_ipStackConfiguration.isEnabled()) {
			return parameterDefinitions;
		}

		parameterDefinitions.add(
			new ParameterDefinition(
				"${ipstack.city}", StringParameter.class.getName(),
				"ipstack.parameter.city"));
		parameterDefinitions.add(
			new ParameterDefinition(
				"${ipstack.continent_code}", StringParameter.class.getName(),
				"ipstack.parameter.continent-code"));
		parameterDefinitions.add(
			new ParameterDefinition(
				"${ipstack.continent_name}", StringParameter.class.getName(),
				"ipstack.parameter.continent-name"));
		parameterDefinitions.add(
			new ParameterDefinition(
				"${ipstack.country_code}", StringParameter.class.getName(),
				"ipstack.parameter.country-code"));
		parameterDefinitions.add(
			new ParameterDefinition(
				"${ipstack.country_name}", StringParameter.class.getName(),
				"ipstack.parameter.country-name"));
		parameterDefinitions.add(
			new ParameterDefinition(
				"${ipstack.latitude}", DoubleParameter.class.getName(),
				"ipstack.parameter.latitude"));
		parameterDefinitions.add(
			new ParameterDefinition(
				"${ipstack.longitude}", DoubleParameter.class.getName(),
				"ipstack.parameter.longitude"));
		parameterDefinitions.add(
			new ParameterDefinition(
				"${ipstack.region_code}", StringParameter.class.getName(),
				"ipstack.parameter.region-code"));
		parameterDefinitions.add(
			new ParameterDefinition(
				"${ipstack.region_name}", StringParameter.class.getName(),
				"ipstack.parameter.region-name"));
		parameterDefinitions.add(
			new ParameterDefinition(
				"${ipstack.zip}", StringParameter.class.getName(),
				"ipstack.parameter.zip"));

		return parameterDefinitions;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_ipStackConfiguration = ConfigurableUtil.createConfigurable(
			IPStackConfiguration.class, properties);
	}

	private void _contribute(
		ParameterDataBuilder parameterDataBuilder, String ipAddress,
		Messages messages) {

		Optional<JSONObject> geoLocationJSONObjectOptional =
			_geoLocationDataProvider.getGeoLocationData(ipAddress, messages);

		if (!geoLocationJSONObjectOptional.isPresent()) {
			return;
		}

		JSONObject geoLocationJSONObject = geoLocationJSONObjectOptional.get();

		parameterDataBuilder.addParameter(
			new StringParameter(
				"ipstack.city", "${ipstack.city}",
				geoLocationJSONObject.getString("city")));

		parameterDataBuilder.addParameter(
			new StringParameter(
				"ipstack.continent_code", "${ipstack.continent_code}",
				geoLocationJSONObject.getString("continent_code")));

		parameterDataBuilder.addParameter(
			new StringParameter(
				"ipstack.continent_name", "${ipstack.continent_name}",
				geoLocationJSONObject.getString("continent_name")));

		parameterDataBuilder.addParameter(
			new StringParameter(
				"ipstack.country_code", "${ipstack.country_code}",
				geoLocationJSONObject.getString("country_code")));

		parameterDataBuilder.addParameter(
			new StringParameter(
				"ipstack.country_name", "${ipstack.country_name}",
				geoLocationJSONObject.getString("country_name")));

		parameterDataBuilder.addParameter(
			new DoubleParameter(
				"ipstack.latitude", "${ipstack.latitude}",
				geoLocationJSONObject.getDouble("latitude")));

		parameterDataBuilder.addParameter(
			new DoubleParameter(
				"ipstack.longitude", "${ipstack.longitude}",
				geoLocationJSONObject.getDouble("longitude")));

		parameterDataBuilder.addParameter(
			new StringParameter(
				"ipstack.region_code", "${ipstack.region_code}",
				geoLocationJSONObject.getString("region_code")));

		parameterDataBuilder.addParameter(
			new StringParameter(
				"ipstack.region_name", "${ipstack.region_name}",
				geoLocationJSONObject.getString("region_name")));

		parameterDataBuilder.addParameter(
			new StringParameter(
				"ipstack.zip", "${ipstack.zip}",
				geoLocationJSONObject.getString("zip")));
	}

	private String _getIpAddress(BlueprintsAttributes blueprintsAttributes) {
		Optional<Object> valueOptional =
			blueprintsAttributes.getAttributeOptional(
				ReservedParameterNames.IP_ADDRESS.getKey());

		return GetterUtil.getString(valueOptional.orElse(null));
	}

	@Reference
	private GeoLocationDataProvider _geoLocationDataProvider;

	private volatile IPStackConfiguration _ipStackConfiguration;

}