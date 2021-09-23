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

package com.liferay.search.experiences.internal.blueprint.sort.translator;

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

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=_geo_distance",
	service = SortTranslator.class
)
public class GeoDistanceSortTranslator implements SortTranslator {

	@Override
	public Optional<Sort> translate(
		String field, JSONObject jsonObject, SortOrder sortOrder) {

		JSONArray jsonArray = jsonObject.getJSONArray("locations");

		if ((jsonArray == null) || (jsonArray.length() == 0)) {
			return Optional.empty();
		}

		GeoDistanceSort geoDistanceSort = _sorts.geoDistance(field);

		geoDistanceSort.setSortOrder(sortOrder);

		_setLocations(geoDistanceSort, jsonObject);

		_setDistanceUnit(geoDistanceSort, jsonObject);

		_setGeoDistanceType(geoDistanceSort, jsonObject);

		_setSortMode(geoDistanceSort, jsonObject);

		return Optional.of(geoDistanceSort);
	}

	private void _setDistanceUnit(
		GeoDistanceSort geoDistanceSort, JSONObject jsonObject) {

		String geoDistanceUnit = jsonObject.getString("unit");

		if (Validator.isBlank(geoDistanceUnit)) {
			return;
		}

		geoDistanceUnit = StringUtil.toLowerCase(geoDistanceUnit);

		for (DistanceUnit distanceUnit : DistanceUnit.values()) {
			String unit = distanceUnit.getUnit();

			if (unit.equals(geoDistanceUnit)) {
				geoDistanceSort.setDistanceUnit(distanceUnit);

				break;
			}
		}
	}

	private void _setGeoDistanceType(
		GeoDistanceSort geoDistanceSort, JSONObject jsonObject) {

		String distanceType = jsonObject.getString(
			"distance_type", GeoDistanceType.ARC.name());

		if (Validator.isBlank(distanceType)) {
			return;
		}

		try {
			geoDistanceSort.setGeoDistanceType(
				GeoDistanceType.valueOf(StringUtil.toUpperCase(distanceType)));
		}
		catch (IllegalArgumentException illegalArgumentException) {
			_log.error(illegalArgumentException);
		}
	}

	private void _setLocations(
		GeoDistanceSort geoDistanceSort, JSONObject jsonObject) {

		JSONArray locationsJSONArray = jsonObject.getJSONArray("locations");

		for (int i = 0; i < locationsJSONArray.length(); i++) {
			JSONArray locationJSONArray = locationsJSONArray.getJSONArray(i);

			if (locationJSONArray.length() != 2) {
				continue;
			}

			geoDistanceSort.addGeoLocationPoints(
				_geoBuilders.geoLocationPoint(
					locationJSONArray.getDouble(0),
					locationJSONArray.getDouble(1)));
		}
	}

	private void _setSortMode(
		GeoDistanceSort geoDistanceSort, JSONObject jsonObject) {

		String mode = jsonObject.getString("mode");

		if (Validator.isBlank(mode)) {
			return;
		}

		geoDistanceSort.setSortMode(
			SortMode.valueOf(StringUtil.toUpperCase(mode)));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GeoDistanceSortTranslator.class);

	@Reference
	private GeoBuilders _geoBuilders;

	@Reference
	private Sorts _sorts;

}