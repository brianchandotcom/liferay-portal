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

package com.liferay.portal.search.tuning.blueprints.engine.internal.sort.translator;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.geolocation.DistanceUnit;
import com.liferay.portal.search.geolocation.GeoBuilders;
import com.liferay.portal.search.geolocation.GeoDistanceType;
import com.liferay.portal.search.sort.GeoDistanceSort;
import com.liferay.portal.search.sort.Sort;
import com.liferay.portal.search.sort.SortMode;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.search.tuning.blueprints.constants.json.keys.sort.SortConfigurationKeys;
import com.liferay.portal.search.tuning.blueprints.engine.spi.sort.SortTranslator;
import com.liferay.portal.search.tuning.blueprints.message.Message;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.message.Severity;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "type=geo_distance",
	service = SortTranslator.class
)
public class GeoDistanceSortTranslator implements SortTranslator {

	@Override
	public Optional<Sort> translate(
		JSONObject configurationJSONObject, SortOrder sortOrder,
		Messages messages) {

		String field = configurationJSONObject.getString(
			SortConfigurationKeys.FIELD.getJsonKey());

		if (!configurationJSONObject.has(
				SortConfigurationKeys.CONFIGURATION.getJsonKey())) {

			return Optional.empty();
		}

		JSONObject sortConfigurationJSONObject =
			configurationJSONObject.getJSONObject(
				SortConfigurationKeys.CONFIGURATION.getJsonKey());

		if (!sortConfigurationJSONObject.has("locations")) {
			return Optional.empty();
		}

		GeoDistanceSort geoDistanceSort = _sorts.geoDistance(field);

		geoDistanceSort.setSortOrder(sortOrder);

		try {
			_setLocations(geoDistanceSort, sortConfigurationJSONObject);

			_setDistanceUnit(geoDistanceSort, sortConfigurationJSONObject);

			_setGeoDistanceType(geoDistanceSort, sortConfigurationJSONObject);

			_setSortMode(geoDistanceSort, sortConfigurationJSONObject);

			return Optional.of(geoDistanceSort);
		}
		catch (IllegalArgumentException illegalArgumentException) {
			messages.addMessage(
				new Message.Builder().className(
					getClass().getName()
				).localizationKey(
					"core.error.unknown-sort-configuration-error"
				).msg(
					illegalArgumentException.getMessage()
				).rootObject(
					configurationJSONObject
				).severity(
					Severity.ERROR
				).throwable(
					illegalArgumentException
				).build());

			_log.error(
				illegalArgumentException.getMessage(),
				illegalArgumentException);
		}

		return Optional.empty();
	}

	private void _setDistanceUnit(
		GeoDistanceSort geoDistanceSort, JSONObject configurationJSONObject) {

		String geoDistanceUnitString = configurationJSONObject.getString(
			"unit");

		if (!Validator.isBlank(geoDistanceUnitString)) {
			geoDistanceUnitString = StringUtil.toLowerCase(
				geoDistanceUnitString);

			for (DistanceUnit distanceUnit : DistanceUnit.values()) {
				String unit = distanceUnit.getUnit();

				if (unit.equals(geoDistanceUnitString)) {
					geoDistanceSort.setDistanceUnit(distanceUnit);
				}
			}
		}
	}

	private void _setGeoDistanceType(
		GeoDistanceSort geoDistanceSort, JSONObject configurationJSONObject) {

		String geoDistanceTypeString = configurationJSONObject.getString(
			"distance_type", GeoDistanceType.ARC.name());

		if (!Validator.isBlank(geoDistanceTypeString)) {
			geoDistanceSort.setGeoDistanceType(
				GeoDistanceType.valueOf(
					StringUtil.toUpperCase(geoDistanceTypeString)));
		}
	}

	private void _setLocations(
		GeoDistanceSort geoDistanceSort, JSONObject configurationJSONObject) {

		JSONArray locationsJSONArray = configurationJSONObject.getJSONArray(
			"locations");

		for (int i = 0; i < locationsJSONArray.length(); i++) {
			JSONArray locationJSONArray = locationsJSONArray.getJSONArray(i);

			if (locationJSONArray.length() != 2) {
				continue;
			}

			Double latitude = locationJSONArray.getDouble(0);

			Double longitude = locationJSONArray.getDouble(1);

			geoDistanceSort.addGeoLocationPoints(
				_geoBuilders.geoLocationPoint(latitude, longitude));
		}
	}

	private void _setSortMode(
		GeoDistanceSort geoDistanceSort, JSONObject configurationJSONObject) {

		String sortModeString = configurationJSONObject.getString("mode");

		if (!Validator.isBlank(sortModeString)) {
			geoDistanceSort.setSortMode(
				SortMode.valueOf(StringUtil.toUpperCase(sortModeString)));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GeoDistanceSortTranslator.class);

	@Reference
	private GeoBuilders _geoBuilders;

	@Reference
	private Sorts _sorts;

}