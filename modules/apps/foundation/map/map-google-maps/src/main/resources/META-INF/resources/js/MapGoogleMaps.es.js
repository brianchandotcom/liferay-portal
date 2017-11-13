/* global google, Liferay */

import {toElement} from 'metal-dom';
import MapBase from 'map-common/js/MapBase.es';

import GoogleMapsDialog from './GoogleMapsDialog.es';
import GoogleMapsGeocoder from './GoogleMapsGeocoder.es';
import GoogleMapsGeoJSON from './GoogleMapsGeoJSON.es';
import GoogleMapsMarker from './GoogleMapsMarker.es';
import GoogleMapsSearch from './GoogleMapsSearch.es';

/**
 * MapGoogleMaps
 */
class MapGoogleMaps extends MapBase {
	/**
	 * Creates a new map using Google Map's API
	 * @param  {Array} args List of arguments to be passed to State
	 */
	constructor(...args) {
		super(...args);
		this._bounds = null;
	}

	/** @inheritdoc */
	addControl(control, position) {
		if (this._map.controls[position]) {
			this._map.controls[position].push(toElement(control));
		}
	}

	/** @inheritdoc */
	getBounds() {
		let bounds = this._map.getBounds() || this._bounds;

		if (!bounds) {
			bounds = new GoogleMapsDialog.maps.LatLngBounds();
			this._bounds = bounds;
		}

		return bounds;
	}

	/** @inheritdoc */
	setCenter(location) {
		if (this._map) {
			this._map.setCenter(location);
		}
	}

	/** @inheritdoc */
	_createMap(location, controlsConfig) {
		const mapConfig = {
			center: location,
			mapTypeId: google.maps.MapTypeId.ROADMAP,
			zoom: this.zoom,
		};

		return new google.maps.Map(
			toElement(this.boundingBox),
			Object.assign(mapConfig, controlsConfig)
		);
	}
}

/** @inheritdoc */
MapBase.DialogImpl = GoogleMapsDialog;

/** @inheritdoc */
MapBase.GeocoderImpl = GoogleMapsGeocoder;

/** @inheritdoc */
MapBase.GeoJSONImpl = GoogleMapsGeoJSON;

/** @inheritdoc */
MapBase.MarkerImpl = GoogleMapsMarker;

/** @inheritdoc */
MapBase.SearchImpl = GoogleMapsSearch;

/** @inheritdoc */
MapGoogleMaps.CONTROLS_MAP = {
	[MapBase.CONTROLS.OVERVIEW]: 'overviewMapControl',
	[MapBase.CONTROLS.PAN]: 'panControl',
	[MapBase.CONTROLS.ROTATE]: 'rotateControl',
	[MapBase.CONTROLS.SCALE]: 'scaleControl',
	[MapBase.CONTROLS.STREETVIEW]: 'streetViewControl',
	[MapBase.CONTROLS.TYPE]: 'mapTypeControl',
	[MapBase.CONTROLS.ZOOM]: 'zoomControl',
};

Liferay.GoogleMap = MapGoogleMaps;
export default MapGoogleMaps;
