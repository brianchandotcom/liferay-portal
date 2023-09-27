/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MapBase from '@liferay/map-common/js/MapBase';

import GoogleMapsDialog from './GoogleMapsDialog';
import GoogleMapsGeoJSON from './GoogleMapsGeoJSON';
import GoogleMapsGeocoder from './GoogleMapsGeocoder';
import GoogleMapsMarker from './GoogleMapsMarker';
import GoogleMapsSearch from './GoogleMapsSearch';

/**
 * MapGoogleMaps
 * @review
 */
class MapGoogleMaps extends MapBase {

	/**
	 * Creates a new map using Google Map's API
	 * @param  {Array} args List of arguments to be passed to State
	 * @review
	 */
	constructor(...args) {
		MapBase.DialogImpl = GoogleMapsDialog;
		MapBase.GeocoderImpl = GoogleMapsGeocoder;
		MapBase.GeoJSONImpl = GoogleMapsGeoJSON;
		MapBase.MarkerImpl = GoogleMapsMarker;
		MapBase.SearchImpl = GoogleMapsSearch;

		super(...args);

		this._bounds = null;
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	_createMap(location, controlsConfig) {
		const mapConfig = {
			center: location,
			mapTypeId: google.maps.MapTypeId.ROADMAP,
			zoom: this.zoom,
		};

		const map = new google.maps.Map(
			document.querySelector(this.boundingBox),
			Object.assign(mapConfig, controlsConfig)
		);

		if (this.data && this.data.features) {
			const bounds = new google.maps.LatLngBounds();

			this.data.features.forEach((feature) =>
				bounds.extend(
					new google.maps.LatLng(
						feature.geometry.coordinates[1],
						feature.geometry.coordinates[0]
					)
				)
			);

			map.fitBounds(bounds);
		}

		return map;
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	addControl(control, position) {
		if (this._map.controls[position]) {
			if (typeof control === 'string') {
				control = document.querySelector(control);
			}

			this._map.controls[position].push(control);
		}
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	getBounds() {
		let bounds = this._map.getBounds() || this._bounds;

		if (!bounds) {
			bounds = new google.maps.LatLngBounds();

			this._bounds = bounds;
		}

		return bounds;
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	setCenter(location) {
		if (this._map) {
			this._map.setCenter(location);
		}
	}
}

MapGoogleMaps.CONTROLS_MAP = {
	[MapBase.CONTROLS.OVERVIEW]: 'overviewMapControl',
	[MapBase.CONTROLS.PAN]: 'panControl',
	[MapBase.CONTROLS.ROTATE]: 'rotateControl',
	[MapBase.CONTROLS.SCALE]: 'scaleControl',
	[MapBase.CONTROLS.STREETVIEW]: 'streetViewControl',
	[MapBase.CONTROLS.TYPE]: 'mapTypeControl',
	[MapBase.CONTROLS.ZOOM]: 'zoomControl',
};

window.Liferay = window.Liferay || {};
window.Liferay.GoogleMap = MapGoogleMaps;

export default MapGoogleMaps;
export {MapGoogleMaps};
