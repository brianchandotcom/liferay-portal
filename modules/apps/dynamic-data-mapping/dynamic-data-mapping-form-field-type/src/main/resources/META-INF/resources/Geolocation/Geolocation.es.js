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

import '../FieldBase/FieldBase.es';

import './GeolocationRegister.soy';

import L from 'leaflet';
import MapGoogleMaps from 'map-google-maps/js/MapGoogleMaps.es';
import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import {setJSONArrayValue} from '../util/setters.es';
import templates from './Geolocation.soy';

import 'leaflet/dist/leaflet.css';

/**
 * Geolocation.
 * @extends Component
 */

const GEOLOCATE_CONFIG = {
	geolocateTitle: Liferay.Language.get('geolocate'),
	pathThemeImages: Liferay.ThemeDisplay.getPathThemeImages()
};

const MAP_PROVIDER = {
	googleMaps: 'GoogleMaps',
	openStreetMap: 'OpenStreetMap'
};

const {CONTROLS} = Liferay.MapBase;

const MAP_CONFIG = {
	controls: [
		CONTROLS.HOME,
		CONTROLS.PAN,
		CONTROLS.SEARCH,
		CONTROLS.TYPE,
		CONTROLS.ZOOM
	],
	geolocation: true,
	position: {location: {lat: 0, lng: 0}}
};

class Geolocation extends Component {
	constructor(...args) {
		super(...args);

		this._mapComponent = null;
	}

	attached() {
		this.setState(GEOLOCATE_CONFIG, () => {
			const {readOnly, viewMode} = this;

			if (!readOnly || viewMode) {
				const mapConfig = {...MAP_CONFIG};
				mapConfig.boundingBox = `#map_${this.instanceId}`;

				if (this.initialConfig_.value && readOnly) {
					mapConfig.position.location = JSON.parse(
						this.initialConfig_.value
					);
				}

				switch (this.mapProviderKey) {
					case MAP_PROVIDER.openStreetMap:
						this._createMapOpenStreetMaps(mapConfig);
						break;

					case MAP_PROVIDER.googleMaps:
						this._createGoogleMaps(mapConfig);
						break;

					default:
						throw new Error('mapProvider is required!');
				}
			}
		});
	}

	shouldUpdate() {
		return false;
	}

	_createMapOpenStreetMaps(mapConfig) {
		L.Icon.Default.imagePath =
			'https://npmcdn.com/leaflet@1.2.0/dist/images/';

		if (!window['L']) {
			window['L'] = L;
		}

		Liferay.Loader.require(this.moduleName, _MapProvide => {
			this._registerComponent(_MapProvide.default, mapConfig);
		});
	}

	_registerComponent(_MapProvide, mapConfig) {
		this._mapComponent = new _MapProvide(mapConfig);

		this._mapComponent.on(
			'positionChange',
			this._eventHandlerPositionChanged.bind(this)
		);

		Liferay.MapBase.register(
			this.name,
			this._mapComponent,
			`#map_${this.instanceId}`
		);
	}

	_createGoogleMaps(mapConfig) {
		if (
			window.google &&
			window.google.maps &&
			Liferay.Maps &&
			Liferay.Maps.gmapsReady
		) {
			this._registerComponent(MapGoogleMaps, mapConfig);
		}
		else {
			Liferay.namespace('Maps').onGMapsReady = function() {
				Liferay.Maps.gmapsReady = true;
				Liferay.fire('gmapsReady');
			};

			Liferay.once('gmapsReady', () =>
				this._registerComponent(MapGoogleMaps, mapConfig)
			);

			let apiURL = `${location.protocol}//maps.googleapis.com/maps/api/js?v=3.exp&libraries=places&callback=Liferay.Maps.onGMapsReady`;

			if (this.googleMapsAPIKey) {
				apiURL += '&key=' + this.googleMapsAPIKey;
			}

			const script = document.createElement('script');
			script.src = apiURL;
			document.head.appendChild(script);
		}
	}

	_eventHandlerPositionChanged(event) {
		const {
			newVal: {location}
		} = event;

		const value = JSON.stringify(location);

		document
			.getElementById(`input_value_${this.instanceId}`)
			.setAttribute('value', value);

		this.emit('fieldEdited', {
			fieldInstance: this,
			value
		});
	}

	prepareStateForRender(state) {
		const {predefinedValue} = state;

		const predefinedValueArray = this._getArrayValue(predefinedValue);

		return {
			...state,
			predefinedValue: predefinedValueArray[0] || '',
			...GEOLOCATE_CONFIG
		};
	}

	disposed() {
		if (this._mapComponent) {
			this._mapComponent.dispose();
		}
	}

	_getArrayValue(value) {
		let newValue = value || '';

		if (!Array.isArray(newValue)) {
			newValue = [newValue];
		}

		return newValue;
	}
}

Geolocation.STATE = {
	/**
	 * @default 'string'
	 * @instance
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	dataType: Config.string().value('string'),

	/**
	 * @default undefined
	 * @instance
	 * @memberof FieldBase
	 * @type {?bool}
	 */

	evaluable: Config.bool().value(false),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Geolocation
	 * @type {?(string|undefined)}
	 */

	fieldName: Config.string(),

	/**
	 * @default ''
	 * @instance
	 * @memberof Geolocation
	 * @type {?(string)}
	 */

	geolocateTitle: Config.string().value(''),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Geolocation
	 * @type {?(string|undefined)}
	 */

	googleMapsAPIKey: Config.string().value(''),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Geolocation
	 * @type {?(string|undefined)}
	 */

	inline: Config.bool().value(false),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Geolocation
	 * @type {?(string|undefined)}
	 */

	label: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Geolocation
	 * @type {?(string|undefined)}
	 */
	mapProviderKey: Config.string().value(MAP_PROVIDER.openStreetMap),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Geolocation
	 * @type {?(string|undefined)}
	 */
	moduleName: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Geolocation
	 * @type {?(string|undefined)}
	 */

	options: Config.arrayOf(
		Config.shapeOf({
			label: Config.string(),
			name: Config.string(),
			value: Config.string()
		})
	).value([
		{
			label: 'Option 1'
		},
		{
			label: 'Option 2'
		}
	]),

	/**
	 * @default ''
	 * @instance
	 * @memberof Geolocation
	 * @type {?(string)}
	 */

	pathThemeImages: Config.string().value(''),

	/**
	 * @default Choose an Option
	 * @instance
	 * @memberof Geolocation
	 * @type {?string}
	 */

	predefinedValue: Config.oneOfType([
		Config.array(),
		Config.object(),
		Config.string()
	])
		.setter(setJSONArrayValue)
		.value([]),

	/**
	 * @default false
	 * @instance
	 * @memberof Geolocation
	 * @type {?bool}
	 */

	readOnly: Config.bool().value(false),

	/**
	 * @default undefined
	 * @instance
	 * @memberof FieldBase
	 * @type {?(bool|undefined)}
	 */

	repeatable: Config.bool(),

	/**
	 * @default false
	 * @instance
	 * @memberof Geolocation
	 * @type {?bool}
	 */

	required: Config.bool().value(false),

	/**
	 * @default true
	 * @instance
	 * @memberof Geolocation
	 * @type {?bool}
	 */

	showLabel: Config.bool().value(true),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Geolocation
	 * @type {?(string|undefined)}
	 */

	spritemap: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Geolocation
	 * @type {?(string|undefined)}
	 */

	tip: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	type: Config.string().value('geolocation'),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Geolocation
	 * @type {?(string|undefined)}
	 */

	value: Config.string(),

	/**
	 * @default false
	 * @instance
	 * @memberof Geolocation
	 * @type {?bool}
	 */

	viewMode: Config.bool().value(false)
};

Soy.register(Geolocation, templates);

export default Geolocation;
