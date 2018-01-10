import MapBase from 'map-common/js/MapBase.es';
import {Config} from 'metal-state';
import {toElement} from 'metal-dom';

import OpenStreetMapDialog from './OpenStreetMapDialog.es';
import OpenStreetMapGeoJSON from './OpenStreetMapGeoJSON.es';
import OpenStreetMapGeocoder from './OpenStreetMapGeocoder.es';
import OpenStreetMapMarker from './OpenStreetMapMarker.es';

/**
 * MapOpenStreetMap
 * @review
 */
class MapOpenStreetMap extends MapBase {
	/**
	 * Creates a new map using OpenStreetMap's API
	 * @param  {Array} args List of arguments to be passed to State
	 * @review
	 */
	constructor(...args) {
		super(...args);

		this._map = null;
	}

	/**
	 * @inheritDoc
	 * @review
	 */
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

	/**
	 * @inheritDoc
	 * @review
	 */
	addControl(control, position) {
		const LeafLetControl = L.Control.extend(
			{
				onAdd() {
					return toElement(control);
				},

				options: {
					position: MapOpenStreetMap.POSITION_MAP[position],
				},
			}
		);

		this._map.addControl(new LeafLetControl());
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	getBounds() {
		return this._map.getBounds();
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	setCenter(location) {
		if (this._map) {
			this._map.panTo(location);
		}

		if (this._geolocationMarker) {
			this._geolocationMarker.setPosition(location);
		}
	}
}

MapBase.DialogImpl = OpenStreetMapDialog;

MapBase.GeocoderImpl = OpenStreetMapGeocoder;

MapBase.GeoJSONImpl = OpenStreetMapGeoJSON;

MapBase.MarkerImpl = OpenStreetMapMarker;

MapBase.SearchImpl = null;

MapOpenStreetMap.CONTROLS_MAP = {
	[MapBase.CONTROLS.ATTRIBUTION]: 'attributionControl',
	[MapBase.CONTROLS.ZOOM]: 'zoomControl',
};

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

/**
 * State definition.
 * @type {!Object}
 * @static
 */
MapOpenStreetMap.STATE = Object.assign({}, MapBase.STATE, {
	/**
	 * Url used for fetching map tile information
	 * @type {string}
	 */
	tileURI: Config.string().value('//{s}.tile.openstreetmap.org/{z}/{x}/{y}.png'),
});

export default MapOpenStreetMap;
export {MapOpenStreetMap};