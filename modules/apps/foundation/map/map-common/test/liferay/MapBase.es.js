/* eslint-disable require-jsdoc */
/* global sinon, assert */

'use strict';

import MapBase from '../../src/main/resources/META-INF/resources/js/MapBase.es';

describe('MapBase', () => {
	let mapImpl;
	let bounds;
	const getLocation = () => ({lat: Math.random(), lng: Math.random()});

	/** @inheritdoc */
	class MapImpl extends MapBase {
		_handlePositionChanged(data) {
			const location = (data && data.location) || getLocation();
			return super._handlePositionChanged({newVal: {location}});
		}

		_createMap(location, controlsConfig) {
			return {name: 'map', location, controlsConfig};
		}

		getBounds() {
			return bounds;
		}

		setCenter() {
		}
	}

	class MarkerImpl {
		constructor(location) {
			this.location = location;
		}

		on() {
		}

		setPosition(location) {
			this.location = location;
		}
	}

	class DialogImpl {
		constructor({map}) {
			this.map = map;
		}
	}

	class GeocoderImpl {
		constructor() {
			sinon.spy(this, 'reverse');
		}

		reverse(location, cb) {
			cb({data: {name: 'data', location}});
		}
	}

	class GeoJSONImpl {
		constructor({map}) {
			this.map = map;
		}

		on() {
		}
	}

	beforeEach(() => {
		window.Liferay = {
			Util: {
				getGeolocation: sinon.spy(),
			},
		};

		MapImpl.MarkerImpl = sinon.spy(MarkerImpl);
		MapImpl.DialogImpl = sinon.spy(DialogImpl);
		MapImpl.GeocoderImpl = sinon.spy(GeocoderImpl);
		MapImpl.GeoJSONImpl = sinon.spy(GeoJSONImpl);
		mapImpl = new MapImpl();
		sinon.spy(mapImpl, 'emit');
		sinon.spy(mapImpl, 'addMarker');
		sinon.spy(mapImpl, '_handlePositionChanged');
		sinon.spy(mapImpl, 'getBounds');
		sinon.spy(mapImpl, 'setCenter');

		bounds = {
			locations: [],
			extend: function(location) {
				this.locations.push(location);
			},
		};
	});

	describe('addMarker()', () => {
		it('should create a new instance of MarkerImpl and return it', () => {
			const marker = mapImpl.addMarker();
			assert(marker instanceof MarkerImpl);
		});

		it('should pass the given location object to the constructor', () => {
			const location = getLocation();
			mapImpl._map = 'map';
			mapImpl.addMarker(location);
			assert.equal(MapImpl.MarkerImpl.firstCall.args[0].location, location);
		});

		it('should pass the existing _map to the constructor', () => {
			const location = getLocation();
			mapImpl._map = 'map';
			mapImpl.addMarker(location);
			assert.equal(MapImpl.MarkerImpl.firstCall.args[0].map, 'map');
		});

		it('should do nothing is MarkerImpl is not implemented', () => {
			MapImpl.MarkerImpl = null;
			const result = mapImpl.addMarker();
			assert(mapImpl.addMarker.calledOnce);
			assert.equal(result ,undefined);
		});
	});

	describe('destructor()', () => {
		it('should dispose existing _geoJSONLayer', () => {
			const layer = {dispose: sinon.spy()};
			mapImpl._geoJSONLayer = layer;
			assert.equal(mapImpl._geoJSONLayer, layer);
			mapImpl.destructor();
			assert.equal(mapImpl._geoJSONLayer, null);
			assert(layer.dispose.calledOnce);
		});

		it('should dispose existing search custom control', () => {
			const control = {dispose: sinon.spy()};
			mapImpl._customControls = {[MapImpl.CONTROLS.SEARCH]: control};
			assert.equal(mapImpl._customControls[MapImpl.CONTROLS.SEARCH], control);
			mapImpl.destructor();
			assert.equal(mapImpl._customControls[MapImpl.CONTROLS.SEARCH], null);
			assert(control.dispose.calledOnce);
		});
	});

	describe('getNativeMap()', () => {
		it('should return the _map property', () => {
			mapImpl._map = {name: 'map'};
			assert.equal(mapImpl.getNativeMap(), mapImpl._map);
		});
	});

	describe('openDialog()', () => {
		it('should call _getDialog() method', () => {
			mapImpl._getDialog = sinon.spy(() => null);
			mapImpl.openDialog();
			assert(mapImpl._getDialog.calledOnce);
		});

		it('should call dialog.open() with the given dialog config', () => {
			const dialog = {open: sinon.spy()};
			const dialogConfig = {opt1: 'asd'};
			mapImpl._getDialog = () => dialog;
			mapImpl.openDialog(dialogConfig);
			assert(dialog.open.calledOnce);
			assert.equal(dialog.open.firstCall.args[0], dialogConfig);
		});
	});

	describe('_bindUIMB()', () => {
		// eslint-disable-next-line max-len
		it('should bind handlers to featuresAdded and featureClick events on geoJSONLayer', () => {
			mapImpl._handleGeoJSONLayerFeaturesAdded = sinon.spy();
			mapImpl._handleGeoJSONLayerFeatureClicked = sinon.spy();
			mapImpl._geoJSONLayer = {on: sinon.spy()};
			mapImpl._bindUIMB();
			assert.deepEqual(
				mapImpl._geoJSONLayer.on.firstCall.args,
				['featuresAdded', mapImpl._handleGeoJSONLayerFeaturesAdded]
			);
		});

		it('should bind handlers to dragend event on geolocationMarker', () => {
			mapImpl._handleGeoLocationMarkerDragended = sinon.spy();
			mapImpl._geolocationMarker = {on: sinon.spy()};
			mapImpl._bindUIMB();
			assert.deepEqual(
				mapImpl._geolocationMarker.on.firstCall.args,
				['dragend', mapImpl._handleGeoLocationMarkerDragended]
			);
		});

		it('should bind handlers to click event on customControls[HOME]', () => {
			mapImpl._handleHomeButtonClicked = sinon.spy();
			mapImpl._customControls = {
				[MapImpl.CONTROLS.HOME]: {addEventListener: sinon.spy()},
			};
			mapImpl._bindUIMB();
			assert.deepEqual(
				mapImpl
					._customControls[MapImpl.CONTROLS.HOME]
					.addEventListener
					.firstCall
					.args,
				['click', mapImpl._handleHomeButtonClicked]
			);
		});

		it('should bind handlers to search event on customControls[SEARCH]', () => {
			mapImpl._handleSearchButtonClicked = sinon.spy();
			mapImpl._customControls = {[MapImpl.CONTROLS.SEARCH]: {on: sinon.spy()}};
			mapImpl._bindUIMB();
			assert.deepEqual(
				mapImpl._customControls[MapImpl.CONTROLS.SEARCH].on.firstCall.args,
				['search', mapImpl._handleSearchButtonClicked]
			);
		});
	});

	describe('_getDialog()', () => {
		it('should do nothing if there is no MapBase.DialogImpl', () => {
			MapImpl.DialogImpl = null;
			assert.equal(mapImpl._getDialog(), undefined);
		});

		it('should create an instance of dialog if necesary', () => {
			const dialog = mapImpl._getDialog();
			assert(dialog instanceof DialogImpl);
		});

		it('should pass the existing map to the dialog constructor', () => {
			sinon.spy(MapImpl.DialogImpl, 'constructor');
			mapImpl._map = Math.random();
			const dialog = mapImpl._getDialog();
			assert.equal(dialog.map, mapImpl._map);
		});

		it('should reuse the existing dialog instance', () => {
			const dialog1 = mapImpl._getDialog();
			const dialog2 = mapImpl._getDialog();
			assert.equal(dialog1, dialog2);
		});
	});

	describe('_getGeoCoder()', () => {
		it('should do nothing if there is no MapBase.GeocoderImpl', () => {
			MapImpl.GeocoderImpl = null;
			assert.equal(mapImpl._getGeocoder(), undefined);
		});

		it('should create an instance of geocoder', () => {
			const geocoder = mapImpl._getGeocoder();
			assert(geocoder instanceof MapImpl.GeocoderImpl);
		});

		it('should reuse the existing geocoder instance', () => {
			const geocoder1 = mapImpl._getGeocoder();
			const geocoder2 = mapImpl._getGeocoder();
			assert.equal(geocoder1, geocoder2);
		});
	});

	describe('_initializeGeoJSONData()', () => {
		it('should do nothing if there is no data attribute', () => {
			mapImpl._geoJSONLayer = {addData: sinon.spy()};
			mapImpl.data = null;
			mapImpl._initializeGeoJSONData();
			assert(!mapImpl._geoJSONLayer.addData.called);
		});

		it('should do nothing if there is no geoJSONLayer', () => {
			mapImpl.data = {name: 'some data'};
			mapImpl._geoJSONLayer = null;
			mapImpl._initializeGeoJSONData();
		});

		it('should call geoJSONLayer.addData with data attribute', () => {
			mapImpl._geoJSONLayer = {addData: sinon.spy()};
			mapImpl.data = {name: 'more data'};
			mapImpl._initializeGeoJSONData();
			assert.equal(
				mapImpl._geoJSONLayer.addData.firstCall.args[0], mapImpl.data
			);
		});
	});

	describe('_initializeLocation()', () => {
		it('should initialize the map with geocoder if geolocation is true', () => {
			const geocoder = mapImpl._getGeocoder();
			const location = getLocation();
			mapImpl._initializeMap = sinon.spy();
			mapImpl.geolocation = true;
			mapImpl._initializeLocation(location);
			assert.equal(geocoder.reverse.firstCall.args[0], location);
			assert.deepEqual(
				mapImpl._initializeMap.firstCall.args[0],
				{name: 'data', location}
			);
		});

		// eslint-disable-next-line max-len
		it('should directly call initializeMap with the given location if geolocation is false', () => {
			const location = getLocation();
			mapImpl._initializeMap = sinon.spy();
			mapImpl.geolocation = false;
			mapImpl._initializeLocation(location);
			assert.deepEqual(
				mapImpl._initializeMap.firstCall.args[0],
				{location}
			);
		});
	});

	describe('_initializeMap()', () => {
		let position;

		beforeEach(() => {
			position = {location: getLocation()};
		});

		it('should store the given position as position', () => {
			mapImpl._initializeMap(position);
			assert.equal(position, mapImpl.position);
		});

		it('should store the given position as originalPosition', () => {
			mapImpl._initializeMap(position);
			assert.equal(position, mapImpl._originalPosition);
		});

		it('should create an instance of GeoJSONImpl', () => {
			mapImpl._initializeMap(position);
			assert(mapImpl._geoJSONLayer instanceof GeoJSONImpl);
		});

		it('should create a new map and store it as _map', () => {
			mapImpl._initializeMap(position);
			assert.equal(mapImpl._map.name, 'map');
			assert.equal(mapImpl._map.location, position.location);
		});

		it('should create a geolocation marker if geolocation is true', () => {
			mapImpl.geolocation = true;
			mapImpl._initializeMap(position);
			assert(mapImpl._geolocationMarker instanceof MarkerImpl);
		});

		it('should call _getControlsConfig()', () => {
			sinon.spy(mapImpl, '_getControlsConfig');
			mapImpl._initializeMap(position);
			assert(mapImpl._getControlsConfig.calledOnce);
		});

		it('should call _createCustomControls()', () => {
			sinon.spy(mapImpl, '_createCustomControls');
			mapImpl._initializeMap(position);
			assert(mapImpl._createCustomControls.calledOnce);
		});

		it('should call _bindUIMB()', () => {
			sinon.spy(mapImpl, '_bindUIMB');
			mapImpl._initializeMap(position);
			assert(mapImpl._bindUIMB.calledOnce);
		});

		it('should call _initializeGeoJSONData()', () => {
			sinon.spy(mapImpl, '_initializeGeoJSONData');
			mapImpl._initializeMap(position);
			assert(mapImpl._initializeGeoJSONData.calledOnce);
		});
	});

	describe('_handleGeoJSONLayerFeaturesAdded()', () => {
		class Geometry {
			constructor() {
				this.location = getLocation();
				sinon.spy(this, 'get');
			}

			get() {
				return this.location;
			}
		}

		class Feature {
			constructor() {
				this.geometry = new Geometry();
				sinon.spy(this, 'getGeometry');
			}

			getGeometry() {
				return this.geometry;
			}
		}

		it('should get the map bounds using getBounds()', () => {
			mapImpl._handleGeoJSONLayerFeaturesAdded({features: []});
			assert(mapImpl.getBounds.calledOnce);
		});

		it('should get the feature geometry for each feature', () => {
			const feature = new Feature();
			mapImpl._handleGeoJSONLayerFeaturesAdded({features: [feature]});
			assert(feature.getGeometry.calledOnce);
			assert(feature.geometry.get.calledOnce);
		});

		it('should update the map position when there is a single feature', () => {
			const feature = new Feature();
			mapImpl._handleGeoJSONLayerFeaturesAdded({features: [feature]});
			assert.equal(mapImpl.position.location, feature.geometry.location);
		});

		// eslint-disable-next-line max-len
		it('should add the features locations to the map bounds when there are multiple features', () => {
			const featureA = new Feature();
			const featureB = new Feature();

			mapImpl._handleGeoJSONLayerFeaturesAdded({
				features: [featureA, featureB],
			});

			assert.deepEqual(
				mapImpl.getBounds().locations,
				[featureA.geometry.location, featureB.geometry.location]
			);
		});
	});

	describe('_handleGeoJSONLayerFeatureClicked()', () => {
		it('should emit a featureClick event', () => {
			mapImpl._handleGeoJSONLayerFeatureClicked({});
			assert.equal(mapImpl.emit.firstCall.args[0], 'featureClick');
		});

		it('should send the given feature as event data', () => {
			mapImpl._handleGeoJSONLayerFeatureClicked({feature: 'feature'});
			assert.deepEqual(mapImpl.emit.firstCall.args[1], {feature: 'feature'});
		});
	});

	describe('_handleGeoLocationMarkerDragended()', () => {
		it('should get the location position with geocoder.reverse()', () => {
			const location = getLocation();
			mapImpl._handleGeoLocationMarkerDragended({location});
			assert.equal(mapImpl._getGeocoder().reverse.firstCall.args[0], location);
		});

		it('should update the instance position', () => {
			const location = getLocation();
			mapImpl._handleGeoLocationMarkerDragended({location});
			assert.deepEqual(mapImpl.position, {name: 'data', location});
		});
	});

	describe('_handleHomeButtonClicked()', () => {
		it('should call preventDefault on the given event', () => {
			const event = {preventDefault: sinon.spy()};
			mapImpl._handleHomeButtonClicked(event);
			assert(event.preventDefault.calledOnce);
		});

		it('should set the instance position to _originalPosition', () => {
			const event = {preventDefault: sinon.spy()};
			const position = {location: getLocation()};
			mapImpl.position = null;
			mapImpl._originalPosition = position;
			mapImpl._handleHomeButtonClicked(event);
			assert.equal(mapImpl.position, position);
		});
	});

	describe('_handlePositionChanged()', () => {
		it('should call setCenter with the given location', () => {
			const location = getLocation();
			mapImpl._handlePositionChanged({location});
			assert.deepEqual(mapImpl.setCenter.firstCall.args, [location]);
		});

		it('should update the geolocationMarker position if present', () => {
			const locationA = getLocation();
			const locationB = getLocation();
			mapImpl._geolocationMarker = mapImpl.addMarker(locationA);
			assert.deepEqual(mapImpl._geolocationMarker.location.location, locationA);
			mapImpl._handlePositionChanged({location: locationB});
			assert.deepEqual(mapImpl._geolocationMarker.location, locationB);
		});
	});

	describe('_handleSearchButtonClicked()', () => {
		it('should update the instance position', () => {
			const position = {location: getLocation()};
			mapImpl._handleSearchButtonClicked({position});
			assert.equal(mapImpl.position, position);
		});
	});

	describe('getBounds()', () => {
		it('should throw a not implemented Error', () => {
			assert.throws(() => new MapBase().getBounds());
		});
	});

	describe('setCenter()', () => {
		it('should throw a not implemented Error', () => {
			assert.throws(() => new MapBase().setCenter());
		});
	});

	describe('_createMap()', () => {
		it('should throw a not implemented Error', () => {
			assert.throws(() => new MapBase()._createMap());
		});
	});
});