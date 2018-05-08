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
			zoom: this.zoom,
		};

		return new google.maps.Map(
			toElement(this.boundingBox),
			Object.assign(mapConfig, controlsConfig)
		);
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
	[MapBase.CONTROLS.ZOOM]: 'zoomControl',
};

window.Liferay = window.Liferay || {};
window.Liferay.GoogleMap = MapGoogleMaps;

export default MapGoogleMaps;
export {MapGoogleMaps};