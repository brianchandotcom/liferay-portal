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

import {buildFragment} from 'metal-dom';
import State, {Config} from 'metal-state';

import GeoJSONBase from './GeoJSONBase.es';
import MarkerBase from './MarkerBase.es';
import {isSubsetOf} from './validators.es';

/**
 * HTML template string used for generating the home button that is
 * used for centering the map at the user location or the original position.
 * @review
 * @type {string}
 */
const TPL_HOME_BUTTON = `
	<button class='btn btn-secondary home-button'>
		<i class='glyphicon glyphicon-screenshot'></i>
	</button>
`;

/**
 * HTML template string used for generating the search box that is used
 * for looking for map locations.
 * @review
 * @type {string}
 */
const TPL_SEARCH_BOX = `
	<div class='col-md-6 search-controls'>
		<input class='search-input' placeholder='' type='text' />
	<div>
`;

/**
 * Object that will hold callbacks waiting for being executed
 * when maps are created.
 * @review
 * @see MapBase.register()
 * @see MapBase.get()
 */
const pendingCallbacks = {};

/**
 * MapBase
 * Each instance represents the map object itself, and adds
 * all necesary listeners and object specified in the given
 * configuration. This class is the core of the module, and
 * will create instances of all the other objects, including
 * the native maps implemented by inheriting classes.
 * @abstract
 * @review
 */
class MapBase extends State {
	/**
	 * MapBase constructor
	 * @param  {Array} args List of arguments to be sent to State constructor
	 * @review
	 */
	constructor(...args) {
		super(...args);

		this._customControls = {};
		this._dialog = null;
		this._eventHandlers = [];
		this._geoJSONLayer = null;
		this._geocoder = null;
		this._geolocationMarker = null;
		this._map = null;
		this._originalPosition = null;

		this._handleGeoJSONLayerFeatureClicked = this._handleGeoJSONLayerFeatureClicked.bind(
			this
		);
		this._handleGeoJSONLayerFeaturesAdded = this._handleGeoJSONLayerFeaturesAdded.bind(
			this
		);
		this._handleGeoLocationMarkerDragended = this._handleGeoLocationMarkerDragended.bind(
			this
		);
		this._handleHomeButtonClicked = this._handleHomeButtonClicked.bind(
			this
		);
		this._handlePositionChanged = this._handlePositionChanged.bind(this);
		this._handleSearchButtonClicked = this._handleSearchButtonClicked.bind(
			this
		);

		this.on('positionChange', this._handlePositionChanged);

		const geolocation =
			this.position && this.position.location
				? this.position.location
				: {};

		if (!geolocation.lat || !geolocation.lng) {
			Liferay.Util.getGeolocation(
				(lat, lng) => {
					this._initializeLocation({lat, lng});
				},
				() => {
					this.zoom = 2;
					this._initializeLocation({lat: 0, lng: 0});
				}
			);
		} else {
			this._initializeLocation(geolocation);
		}
	}

	/**
	 * Destroys the existing _geoJSONLayer and _customControls[SEARCH]
	 * @review
	 * @see MapBase._initializeMap()
	 * @see MapBase._createCustomControls()
	 */
	destructor() {
		if (this._geoJSONLayer) {
			this._geoJSONLayer.dispose();

			this._geoJSONLayer = null;
		}

		if (
			this._customControls &&
			this._customControls[this.constructor.CONTROLS.SEARCH]
		) {
			this._customControls[this.constructor.CONTROLS.SEARCH].dispose();

			this._customControls[this.constructor.CONTROLS.SEARCH] = null;
		}
	}

	/**
	 * @protected
	 * @review
	 *
	 * Add event listeners to:
	 * @see this._geoJSONLayer
	 * @see this._geolocationMarker
	 * @see this._customControls
	 *
	 * All added listeners are implemented as binded methods:
	 * @see this._handleGeoJSONLayerFeaturesAdded
	 * @see this._handleGeoJSONLayerFeatureClicked
	 * @see this._handleGeoLocationMarkerDragended
	 * @see this._handleHomeButtonClicked
	 * @see this._handleSearchButtonClicked
	 */
	_bindUIMB() {
		if (this._geoJSONLayer) {
			this._geoJSONLayer.on(
				'featuresAdded',
				this._handleGeoJSONLayerFeaturesAdded
			);

			this._geoJSONLayer.on(
				'featureClick',
				this._handleGeoJSONLayerFeatureClicked
			);
		}

		if (this._geolocationMarker) {
			this._geolocationMarker.on(
				'dragend',
				this._handleGeoLocationMarkerDragended
			);
		}

		if (this._customControls) {
			const homeControl = this._customControls[
				this.constructor.CONTROLS.HOME
			];
			const searchControl = this._customControls[
				this.constructor.CONTROLS.SEARCH
			];

			if (homeControl) {
				homeControl.addEventListener(
					'click',
					this._handleHomeButtonClicked
				);
			}

			if (searchControl) {
				searchControl.on('search', this._handleSearchButtonClicked);
			}
		}
	}

	/**
	 * Creates existing custom controls and stores them inside _customControls.
	 * It only adds the home button and the search box if the corresponding
	 * flags are activated inside MapBase.controls.
	 * @protected
	 * @review
	 */
	_createCustomControls() {
		const controls = this.controls || [];
		const customControls = {};

		if (controls.indexOf(this.constructor.CONTROLS.HOME) !== -1) {
			const homeControl = buildFragment(TPL_HOME_BUTTON).querySelector(
				'.btn.btn-secondary.home-button'
			);
			customControls[this.constructor.CONTROLS.HOME] = homeControl;
			this.addControl(
				homeControl,
				this.constructor.POSITION.RIGHT_BOTTOM
			);
		}

		if (
			controls.indexOf(this.constructor.CONTROLS.SEARCH) !== -1 &&
			this.constructor.SearchImpl
		) {
			const searchControl = buildFragment(TPL_SEARCH_BOX).querySelector(
				'div.col-md-6.search-controls'
			);
			customControls[
				this.constructor.CONTROLS.SEARCH
			] = new this.constructor.SearchImpl({
				inputNode: searchControl.querySelector('input')
			});
			this.addControl(searchControl, this.constructor.POSITION.TOP_LEFT);
		}

		this._customControls = customControls;
	}

	/**
	 * Creates a new map for the given location and controlsConfig.
	 * @abstract
	 * @param {Object} location
	 * @param {Object} controlsConfig
	 * @protected
	 * @return {Object} Created map
	 * @review
	 */
	_createMap(
		/* eslint-disable no-unused-vars */
		location,
		controlsConfig
		/* eslint-enable no-unused-vars */
	) {
		throw new Error('This method must be implemented');
	}

	/**
	 * Returns an object with the control configuration associated to the
	 * existing controls (MapBase.controls).
	 * @protected
	 * @return {Object} Object with the control options with the following shape:
	 *  for each control, there is a corresponding [control] attribute with a
	 *  boolean value indicating if there is a configuration for the control. If
	 *  true, where will be a [control]Options attribute with the configuration
	 *  content.
	 * @review
	 */
	_getControlsConfig() {
		const config = {};
		const availableControls = this.controls.map(item => {
			return typeof item === 'string' ? item : item.name;
		});

		Object.keys(this.constructor.CONTROLS_MAP).forEach(key => {
			const controlIndex = availableControls.indexOf(key);
			const value = this.constructor.CONTROLS_MAP[key];

			if (controlIndex > -1) {
				const controlConfig = this.controls[controlIndex];

				if (
					controlConfig &&
					typeof controlConfig === 'object' &&
					controlConfig.cfg
				) {
					config[`${value}Options`] = controlConfig.cfg;
				}

				config[value] = controlIndex !== -1;
			}
		});

		return config;
	}

	/**
	 * If there is an existing this._dialog, it returns it.
	 * Otherwise, if the MapBase.DialogImpl class has been set, it creates
	 * a new instance with the existing map and returns it.
	 * @protected
	 * @return {MapBase.DialogImpl|null}
	 * @review
	 */
	_getDialog() {
		if (!this._dialog && this.constructor.DialogImpl) {
			this._dialog = new this.constructor.DialogImpl({
				map: this._map
			});
		}

		return this._dialog;
	}

	/**
	 * If there is an existing this._geocoder, it returns it.
	 * Otherwise, if the MapBase.GeocoderImpl class has been set, it creates
	 * a new instance and returns it.
	 * @protected
	 * @return {MapBase.GeocoderImpl|null}
	 * @review
	 */
	_getGeocoder() {
		if (!this._geocoder && this.constructor.GeocoderImpl) {
			this._geocoder = new this.constructor.GeocoderImpl();
		}

		return this._geocoder;
	}

	/**
	 * Event handler executed when any feature is added to the existing.
	 * GeoJSONLayer. It will update the current position if necesary.
	 * @param {{ features: Array<Object> }} param0 Array of features that
	 *  will be processed.
	 * @protected
	 * @review
	 * @see MapBase.getBounds()
	 * @see MapBase.position
	 */
	_handleGeoJSONLayerFeaturesAdded({features}) {
		const bounds = this.getBounds();

		const locations = features.map(feature => feature.getGeometry().get());

		if (locations.length > 1) {
			locations.forEach(location => bounds.extend(location));
		} else {
			this.position = {location: locations[0]};
		}
	}

	/**
	 * Event handler executed when a GeoJSONLayer feature is clicked. It
	 * simple propagates the event with the given feature.
	 * @param {{ feature: Object }} param0 Feature to be propagated.
	 * @protected
	 * @review
	 */
	_handleGeoJSONLayerFeatureClicked({feature}) {
		this.emit('featureClick', {feature});
	}

	/**
	 * Event handler executed when the geolocation marker has been dragged to
	 * a new position. It updates the instance position.
	 * @param {{ location: Object }} param0 New marker location.
	 * @protected
	 * @review
	 * @see MapBase._getGeoCoder()
	 * @see MapBase.position
	 */
	_handleGeoLocationMarkerDragended({location}) {
		this._getGeocoder().reverse(location, ({data}) => {
			this.position = data;
		});
	}

	/**
	 * Event handler executed when the home button GeoJSONLayer feature is
	 * clicked. It resets the instance position to _originalPosition and stops
	 * the event propagation.
	 * @param {Event} event Click event.
	 * @review
	 * @see MapBase.position
	 * @see MapBase._originalPosition
	 */
	_handleHomeButtonClicked(event) {
		event.preventDefault();

		this.position = this._originalPosition;
	}

	/**
	 * Event handler executed when the user position changes. It centers the map
	 * and, if existing, updates the _geolocationMarker position.
	 * @param {{ newVal: { location: Object } }} param0 New location information.
	 * @review
	 * @see MapBase.setCenter()
	 * @see MapBase._geolocationMarker
	 */
	_handlePositionChanged({newVal: {location}}) {
		this.setCenter(location);

		if (this._geolocationMarker) {
			this._geolocationMarker.setPosition(location);
		}
	}

	/**
	 * Event handler executed when the search button GeoJSONLayer feature has
	 * been clicked. It updates the instance position.
	 * @param {{ position: Object }} param0 New position.
	 * @review
	 * @see MapBase.position
	 */
	_handleSearchButtonClicked({position}) {
		this.position = position;
	}

	/**
	 * If this.data has a truthy value and this._geoJSONLayer has been
	 * set, it tries to parse geoJSON information with _geoJSONLayer.addData()
	 * @protected
	 * @review
	 * @see this._geoJSONLayer
	 * @see this.data
	 */
	_initializeGeoJSONData() {
		if (this.data && this._geoJSONLayer) {
			this._geoJSONLayer.addData(this.data);
		}
	}

	/**
	 * Initializes the instance map using the given location and, if
	 * this.geolocation property is true, tries to get the location using
	 * the geocoder.
	 * @param {Object} location Location object user for map initialization.
	 * @protected
	 * @review
	 * @see this._initializeMap()
	 * @see this._getGeocoder()
	 */
	_initializeLocation(geolocation) {
		const geocoder = this._getGeocoder();

		if (this.geolocation && geocoder) {
			geocoder.reverse(geolocation, ({data}) =>
				this._initializeMap(data)
			);
		} else {
			this._initializeMap({location: geolocation});
		}
	}

	/**
	 * Creates a new map with the given position and initialize the map
	 * controls, loads the geoJSONData and, if geolocation is active, creates
	 * a marker for it.
	 * @param {Object} position Initial position added to the map.
	 * @protected
	 * @review
	 * @see MapBase._getControlsConfig()
	 * @see MapBase._createMap()
	 * @see MapBase.GeoJSONImpl
	 * @see MapBase.addMarker()
	 * @see MapBase._createCustomControls()
	 * @see MapBase._bindUIMB()
	 * @see MapBase._initializeGeoJSONData()
	 */
	_initializeMap(position) {
		const controlsConfig = this._getControlsConfig();
		const geolocation = position.location;

		this._originalPosition = position;
		this._map = this._createMap(geolocation, controlsConfig);

		if (
			this.constructor.GeoJSONImpl &&
			this.constructor.GeoJSONImpl !== GeoJSONBase
		) {
			this._geoJSONLayer = new this.constructor.GeoJSONImpl({
				map: this._map
			});
		}

		if (this.geolocation) {
			this._geolocationMarker = this.addMarker(geolocation);
		}

		this.position = position;

		this._createCustomControls();
		this._bindUIMB();
		this._initializeGeoJSONData();
	}

	/**
	 * Adds a new control to the interface at the given position.
	 * @param {Object} control Native control object
	 * @param {MapBase.POSITION} position Position defined in MapBase class
	 * @review
	 */
	addControl(
		/* eslint-disable no-unused-vars */
		control,
		position
		/* eslint-enable no-unused-vars */
	) {
		throw new Error('This method must be implemented');
	}

	/**
	 * Returns the map bounds.
	 * @abstract
	 * @return {Object} Map bounds
	 * @review
	 */
	getBounds() {
		throw new Error('This method must be implemented');
	}

	/**
	 * Centers the map on the given location.
	 * @abstract
	 * @param {Object} location
	 * @review
	 */
	setCenter(
		/* eslint-disable no-unused-vars */
		location
		/* eslint-enable no-unused-vars */
	) {
		throw new Error('This method must be implemented');
	}

	/**
	 * If the class MapBase.MarkerImpl has been specified, creates an instance
	 * of this class with the given location and the existing this._map object
	 * and returns it.
	 * @param {Object} location Location object used for the marker position
	 * @return {MapBase.MarkerImpl}
	 * @review
	 */
	addMarker(location) {
		let marker;

		if (
			this.constructor.MarkerImpl &&
			this.constructor.MarkerImpl !== MarkerBase
		) {
			marker = new this.constructor.MarkerImpl({
				location,
				map: this._map
			});
		}

		return marker;
	}

	/**
	 * Returns the stored map
	 * @return {Object}
	 * @review
	 * @see MapBase._initializeMap()
	 */
	getNativeMap() {
		return this._map;
	}

	/**
	 * Adds a listener for the given event using the given context. This
	 * methods uses MetalJS' functionality, but overrides the context binding
	 * in order to avoid breaking changes with the old implementation.
	 * @param {string} eventName Event name that will be listened.
	 * @param {function} callback Callback executed when the event fires.
	 * @param {Object} [context] Optional context that will be used for binding
	 *  the callback when specified.
	 * @return {*} Result of the State.on method.
	 * @review
	 * @see State.on
	 */
	on(eventName, callback, context) {
		let boundCallback = callback;

		if (context) {
			boundCallback = callback.bind(context);
		}

		return super.on(eventName, boundCallback);
	}

	/**
	 * Opens a dialog if this_getDialog() returns a valid object.
	 * @param {*} dialogConfig Dialog configuration that will be sent to the
	 *  dialog.open() method.
	 * @review
	 * @see MapBase._getDialog()
	 */
	openDialog(dialogConfig) {
		const dialog = this._getDialog();

		if (dialog) {
			dialog.open(dialogConfig);
		}
	}

	/**
	 * Setter called everytime the position attribute is changed.
	 * @param {Object} position New position
	 * @return {Object} The given position object
	 * @review
	 */
	setPosition(position) {
		this.emit('positionChange', {
			newVal: {
				address: position.address,
				location: position.location
			}
		});

		return position;
	}
}

/**
 * Registers the given callback to be executed when the map identified
 * by the given id has been created. If the map has already been created, it
 * is executed immediatly.
 * @param {string} id Id of the map that needs to be created
 * @param {function} callback Callback being executed
 * @review
 */
MapBase.get = function(id, callback) {
	const map = Liferay.component(id);

	if (map) {
		callback(map);
	} else {
		const idPendingCallbacks = pendingCallbacks[id] || [];

		idPendingCallbacks.push(callback);

		pendingCallbacks[id] = idPendingCallbacks;
	}
};

/**
 * Registers the given map with the given id, and executes all existing
 * callbacks associated with the id. Then it clears the list of callbacks.
 * @param {string} id Id of the map that has been created
 * @param {Object} map Map that has been created
 * @param {string} portletId Id of the portlet that registers the map
 * @review
 */
MapBase.register = function(id, map, portletId) {
	const componentConfig = portletId ? {portletId} : {destroyOnNavigate: true};

	Liferay.component(id, map, componentConfig);

	const idPendingCallbacks = pendingCallbacks[id];

	if (idPendingCallbacks) {
		idPendingCallbacks.forEach(callback => callback(map));

		idPendingCallbacks.length = 0;
	}
};

/**
 * Class that will be used for creating dialog objects.
 * @review
 */
MapBase.DialogImpl = null;

/**
 * Class that will be used for parsing geoposition information.
 * @review
 */
MapBase.GeocoderImpl = null;

/**
 * Class that will be used for creating GeoJSON instances.
 * This class must be replaced by another one extending GeoJSONBase.
 * @review
 */
MapBase.GeoJSONImpl = GeoJSONBase;

/**
 * Class that will be used for creating map markers.
 * This class must be replaced by another one extending MarkerBase.
 * @review
 */
MapBase.MarkerImpl = MarkerBase;

/**
 * Class that will be used for creating an instance of searchbox.
 * This class must be replaced by another one extending MarkerBase.
 * @review
 */
MapBase.SearchImpl = null;

/**
 * List of controls that maybe shown inside the rendered map.
 * @review
 */
MapBase.CONTROLS = {
	ATTRIBUTION: 'attribution',
	GEOLOCATION: 'geolocation',
	HOME: 'home',
	OVERVIEW: 'overview',
	PAN: 'pan',
	ROTATE: 'rotate',
	SCALE: 'scale',
	SEARCH: 'search',
	STREETVIEW: 'streetview',
	TYPE: 'type',
	ZOOM: 'zoom'
};

/**
 * Control mapping that should be overriden by child classes.
 * @review
 */
MapBase.CONTROLS_MAP = {};

/**
 * Available map positions.
 * @review
 */
MapBase.POSITION = {
	BOTTOM: 11,
	BOTTOM_CENTER: 11,
	BOTTOM_LEFT: 10,
	BOTTOM_RIGHT: 12,
	CENTER: 13,
	LEFT: 5,
	LEFT_BOTTOM: 6,
	LEFT_CENTER: 4,
	LEFT_TOP: 5,
	RIGHT: 7,
	RIGHT_BOTTOM: 9,
	RIGHT_CENTER: 8,
	RIGHT_TOP: 7,
	TOP: 2,
	TOP_CENTER: 2,
	TOP_LEFT: 1,
	TOP_RIGHT: 3
};

/**
 * Position mapping that should be overriden by child classes.
 * @review
 */
MapBase.POSITION_MAP = {};

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
MapBase.STATE = {
	/**
	 * DOM node selector identifying the element that will be used
	 * for rendering the map
	 * @review
	 * @type {string}
	 */
	boundingBox: Config.string().value(''),

	/**
	 * List of controls that will be shown on the map.
	 * The full control list is kept inside MapBase.CONTROLS.
	 * @review
	 * @type {Array<string>}
	 */
	controls: Config.validator(
		isSubsetOf(Object.values(MapBase.CONTROLS))
	).value([
		MapBase.CONTROLS.PAN,
		MapBase.CONTROLS.TYPE,
		MapBase.CONTROLS.ZOOM
	]),

	/**
	 * Data that will be parsed as GeoJSONData
	 * @review
	 * @type {Object}
	 */
	data: Config.object(),

	/**
	 * If true, the geolocation API will be used (if implemented)
	 * @review
	 * @type {boolean}
	 */
	geolocation: Config.bool().value(false),

	/**
	 * Position being shown on the map. This value will be updated
	 * if the position is changed internally.
	 * @review
	 * @type {{ lat: number, lng: number }}
	 */
	position: Config.shapeOf({
		location: Config.shapeOf({
			lat: Config.number().value(0),
			lng: Config.number().value(0)
		})
	})
		.value({
			location: {lat: 0, lng: 0}
		})
		.setter('setPosition'),

	/**
	 * Zoom being used on the map.
	 * @review
	 * @type {number}
	 */
	zoom: Config.number().value(11)
};

Liferay.MapBase = MapBase;

export default MapBase;
export {MapBase};
