/* global L */

import {Config} from 'metal-state';
import {toElement} from 'metal-dom';
import MapBase from 'map-common/js/MapBase.es';

import OpenStreetMapDialog from './OpenStreetMapDialog.es';
import OpenStreetMapGeocoder from './OpenStreetMapGeocoder.es';
import OpenStreetMapGeoJSON from './OpenStreetMapGeoJSON.es';
import OpenStreetMapMarker from './OpenStreetMapMarker.es';

/**
 * MapOpenStreetMap
 */
class MapOpenStreetMap extends MapBase {
	/**
	 * Creates a new map using OpenStreetMap's API
	 * @param  {Array} args List of arguments to be passed to State
	 */
	constructor(...args) {
		super(...args);
		this._map = null;
	}

	/** @inheritdoc */
	addControl(control, position) {
		const LeafLetControl = L.Control.extend({
			onAdd() {
				return toElement(control);
			},

			options: {
				position: MapOpenStreetMap.POSITION_MAP[position],
			},
		});

		this._map.addControl(new LeafLetControl());
	}

	/** @inheritdoc */
	getBounds() {
		return this._map.getBounds();
	}

	/** @inheritdoc */
	setCenter(location) {
		if (this._map) {
			this._map.panTo(location);
		}

		if (this._geolocationMarker) {
			this._geolocationMarker.setPosition(location);
		}
	}

	/** @inheritdoc */
	_createMap(location, controlsConfig) {
		const mapConfig = {
			center: location,
			layers: [L.tileLayer(this.tileURI)],
			zoom: this.zoom,
		};

		return L.map(
			toElement(this.boundingBox),
			Object.assign(mapConfig, controlsConfig)
		);
	}
}

/** @inheritdoc */
MapBase.DialogImpl = OpenStreetMapDialog;

/** @inheritdoc */
MapBase.GeocoderImpl = OpenStreetMapGeocoder;

/** @inheritdoc */
MapBase.GeoJSONImpl = OpenStreetMapGeoJSON;

/** @inheritdoc */
MapBase.MarkerImpl = OpenStreetMapMarker;

/** @inheritdoc */
MapBase.SearchImpl = null;

/** @inheritdoc */
MapOpenStreetMap.CONTROLS_MAP = {
	[MapBase.CONTROLS.ATTRIBUTION]: 'attributionControl',
	[MapBase.CONTROLS.ZOOM]: 'zoomControl',
};

/** @inheritdoc */
MapOpenStreetMap.POSITION_MAP = {
	[MapBase.POSITION.BOTTOM]: 'bottomleft',
	[MapBase.POSITION.BOTTOM_CENTER]: 'bottomleft',
	[MapBase.POSITION.BOTTOM_LEFT]: 'bottomleft',
	[MapBase.POSITION.BOTTOM_RIGHT]: 'bottomright',
	[MapBase.POSITION.CENTER]: 'topleft',
	[MapBase.POSITION.LEFT]: 'topleft',
	[MapBase.POSITION.LEFT_BOTTOM]: 'bottomleft',
	[MapBase.POSITION.LEFT_CENTER]: 'topleft',
	[MapBase.POSITION.LEFT_TOP]: 'topleft',
	[MapBase.POSITION.RIGHT]: 'bottomright',
	[MapBase.POSITION.RIGHT_BOTTOM]: 'bottomright',
	[MapBase.POSITION.RIGHT_CENTER]: 'bottomright',
	[MapBase.POSITION.RIGHT_TOP]: 'topright',
	[MapBase.POSITION.TOP]: 'topright',
	[MapBase.POSITION.TOP_CENTER]: 'topright',
	[MapBase.POSITION.TOP_LEFT]: 'topleft',
	[MapBase.POSITION.TOP_RIGHT]: 'topright',
};

MapOpenStreetMap.STATE = Object.assign({}, MapBase.STATE, {
	/**
	 * Url used for fetching map tile information
	 * @type {string}
	 */
	tileURI: Config.string().value('//{s}.tile.openstreetmap.org/{z}/{x}/{y}.png'),
});

export default MapOpenStreetMap;
