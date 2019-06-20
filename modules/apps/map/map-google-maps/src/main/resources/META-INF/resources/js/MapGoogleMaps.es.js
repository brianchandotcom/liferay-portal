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

import MapBase from 'map-common/js/MapBase.es';
import {toElement} from 'metal-dom';

import GoogleMapsDialog from './GoogleMapsDialog.es';
import GoogleMapsGeoJSON from './GoogleMapsGeoJSON.es';
import GoogleMapsGeocoder from './GoogleMapsGeocoder.es';
import GoogleMapsMarker from './GoogleMapsMarker.es';
import GoogleMapsSearch from './GoogleMapsSearch.es';

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
			zoom: this.zoom
		};

		const map = new google.maps.Map(
			toElement(this.boundingBox),
			Object.assign(mapConfig, controlsConfig)
		);

		if (this.data && this.data.features) {
			const bounds = new google.maps.LatLngBounds();

			this.data.features.forEach(feature =>
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
			this._map.controls[position].push(toElement(control));
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

MapBase.DialogImpl = GoogleMapsDialog;

MapBase.GeocoderImpl = GoogleMapsGeocoder;

MapBase.GeoJSONImpl = GoogleMapsGeoJSON;

MapBase.MarkerImpl = GoogleMapsMarker;

MapBase.SearchImpl = GoogleMapsSearch;

MapGoogleMaps.CONTROLS_MAP = {
	[MapBase.CONTROLS.OVERVIEW]: 'overviewMapControl',
	[MapBase.CONTROLS.PAN]: 'panControl',
	[MapBase.CONTROLS.ROTATE]: 'rotateControl',
	[MapBase.CONTROLS.SCALE]: 'scaleControl',
	[MapBase.CONTROLS.STREETVIEW]: 'streetViewControl',
	[MapBase.CONTROLS.TYPE]: 'mapTypeControl',
	[MapBase.CONTROLS.ZOOM]: 'zoomControl'
};

window.Liferay = window.Liferay || {};
window.Liferay.GoogleMap = MapGoogleMaps;

export default MapGoogleMaps;
export {MapGoogleMaps};
