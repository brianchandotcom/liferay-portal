import MapBase from '../../src/main/resources/META-INF/resources/js/MapBase.es';

describe('MapBase', () => {
	const getLocation = () => ({lat: Math.random(), lng: Math.random()});

	let mapImpl;
	let bounds;

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

		setCenter() {}
	}

	const MarkerImpl = jest.fn().mockImplementation(function(location) {
		this.location = location;

		this.on = jest.fn();

		this.setPosition = function(location) {
			this.location = location;
		};
	});

	let DialogImpl = jest.fn().mockImplementation(({map}) => {});

	let geocoderImpl = {
		reverse: function(location, cb) {
			cb({data: {name: 'data', location}});
		},
	};

	let GeocoderImpl = jest.fn().mockImplementation(() => geocoderImpl);

	let geoJSONImpl = {
		on: jest.fn(),
	};

	let GeoJSONImpl = jest.fn().mockImplementation(({map}) => geoJSONImpl);

	beforeEach(() => {
		window.Liferay = {
			Util: {
				getGeolocation: jest.fn(),
			},
		};

		MapImpl.MarkerImpl = MarkerImpl;
		MapImpl.DialogImpl = DialogImpl;
		MapImpl.GeocoderImpl = GeocoderImpl;
		MapImpl.GeoJSONImpl = GeoJSONImpl;

		mapImpl = new MapImpl();

		jest.spyOn(mapImpl, '_handlePositionChanged');
		jest.spyOn(mapImpl, 'addMarker');
		jest.spyOn(mapImpl, 'emit');
		jest.spyOn(mapImpl, 'getBounds');
		jest.spyOn(mapImpl, 'setCenter');

		bounds = {
			locations: [],
			extend: function(location) {
				this.locations.push(location);
			},
		};
	});

	describe('addMarker()', () => {
		it('should pass the given location object to the constructor', () => {
			const location = getLocation();

			mapImpl._map = 'map';

			mapImpl.addMarker(location);

			expect(MapImpl.MarkerImpl.mock.calls[0][0].location).toBe(location);
		});

		it('should pass the existing _map to the constructor', () => {
			const location = getLocation();

			mapImpl._map = 'map';

			mapImpl.addMarker(location);

			expect(MapImpl.MarkerImpl.mock.calls[0][0].map).toBe('map');
		});

		it('should do nothing is MarkerImpl is not implemented', () => {
			MapImpl.MarkerImpl = null;

			const result = mapImpl.addMarker();

			expect(mapImpl.addMarker).toHaveBeenCalledTimes(1);
			expect(result).toBe(undefined);
		});
	});

	describe('destructor()', () => {
		it('should dispose existing _geoJSONLayer', () => {
			const layer = {dispose: jest.fn()};

			mapImpl._geoJSONLayer = layer;

			expect(mapImpl._geoJSONLayer).toBe(layer);

			mapImpl.destructor();

			expect(mapImpl._geoJSONLayer).toBe(null);
			expect(layer.dispose).toHaveBeenCalledTimes(1);
		});

		it('should dispose existing search custom control', () => {
			const control = {dispose: jest.fn()};

			mapImpl._customControls = {[MapImpl.CONTROLS.SEARCH]: control};

			expect(mapImpl._customControls[MapImpl.CONTROLS.SEARCH]).toBe(control);

			mapImpl.destructor();

			expect(mapImpl._customControls[MapImpl.CONTROLS.SEARCH]).toBe(null);
			expect(control.dispose).toHaveBeenCalledTimes(1);
		});
	});

	describe('getNativeMap()', () => {
		it('should return the _map property', () => {
			mapImpl._map = {name: 'map'};

			expect(mapImpl.getNativeMap()).toBe(mapImpl._map);
		});
	});

	describe('openDialog()', () => {
		it('should call _getDialog() method', () => {
			mapImpl._getDialog = jest.fn(() => null);

			mapImpl.openDialog();

			expect(mapImpl._getDialog).toHaveBeenCalledTimes(1);
		});

		it('should call dialog.open() with the given dialog config', () => {
			const dialog = {open: jest.fn()};
			const dialogConfig = {opt1: 'asd'};

			mapImpl._getDialog = () => dialog;

			mapImpl.openDialog(dialogConfig);

			expect(dialog.open).toHaveBeenCalledTimes(1);
			expect(dialog.open.mock.calls[0][0]).toBe(dialogConfig);
		});
	});

	describe('_bindUIMB()', () => {
		it('should bind handlers to featuresAdded and featureClick events on geoJSONLayer', () => {
			mapImpl._handleGeoJSONLayerFeatureClicked = jest.fn();
			mapImpl._handleGeoJSONLayerFeaturesAdded = jest.fn();

			mapImpl._geoJSONLayer = {on: jest.fn()};

			mapImpl._bindUIMB();

			expect(mapImpl._geoJSONLayer.on.mock.calls[0]).toEqual([
				'featuresAdded',
				mapImpl._handleGeoJSONLayerFeaturesAdded,
			]);
		});

		it('should bind handlers to dragend event on geolocationMarker', () => {
			mapImpl._geolocationMarker = {on: jest.fn()};
			mapImpl._handleGeoLocationMarkerDragended = jest.fn();

			mapImpl._bindUIMB();

			expect(mapImpl._geolocationMarker.on.mock.calls[0]).toEqual([
				'dragend',
				mapImpl._handleGeoLocationMarkerDragended,
			]);
		});

		it('should bind handlers to click event on customControls[HOME]', () => {
			mapImpl._handleHomeButtonClicked = jest.fn();

			mapImpl._customControls = {
				[MapImpl.CONTROLS.HOME]: {addEventListener: jest.fn()},
			};

			mapImpl._bindUIMB();

			expect(
				mapImpl._customControls[MapImpl.CONTROLS.HOME].addEventListener.mock.calls[0]
			).toEqual(['click', mapImpl._handleHomeButtonClicked]);
		});

		it('should bind handlers to search event on customControls[SEARCH]', () => {
			mapImpl._handleSearchButtonClicked = jest.fn();
			mapImpl._customControls = {[MapImpl.CONTROLS.SEARCH]: {on: jest.fn()}};

			mapImpl._bindUIMB();

			expect(
				mapImpl._customControls[MapImpl.CONTROLS.SEARCH].on.mock.calls[0]
			).toEqual(['search', mapImpl._handleSearchButtonClicked]);
		});
	});

	describe('_getDialog()', () => {
		it('should do nothing if there is no MapBase.DialogImpl', () => {
			MapImpl.DialogImpl = null;

			expect(mapImpl._getDialog()).toBe(null);
		});

		it('should pass the existing map to the dialog constructor', () => {
			mapImpl._map = Math.random();

			const dialog = mapImpl._getDialog();

			expect(MapImpl.DialogImpl.mock.calls[0][0].map).toBe(mapImpl._map);
		});

		it('should reuse the existing dialog instance', () => {
			const dialog1 = mapImpl._getDialog();
			const dialog2 = mapImpl._getDialog();

			expect(dialog1).toBe(dialog2);
		});
	});

	describe('_getGeoCoder()', () => {
		it('should do nothing if there is no MapBase.GeocoderImpl', () => {
			MapImpl.GeocoderImpl = null;

			expect(mapImpl._getGeocoder()).toBe(null);
		});

		it('should reuse the existing geocoder instance', () => {
			const geocoder1 = mapImpl._getGeocoder();
			const geocoder2 = mapImpl._getGeocoder();

			expect(geocoder1).toBe(geocoder2);
		});
	});

	describe('_initializeGeoJSONData()', () => {
		it('should do nothing if there is no data attribute', () => {
			mapImpl._geoJSONLayer = {addData: jest.fn()};

			mapImpl.data = null;

			mapImpl._initializeGeoJSONData();

			expect(mapImpl._geoJSONLayer.addData).not.toHaveBeenCalled();
		});

		it('should call geoJSONLayer.addData with data attribute', () => {
			mapImpl._geoJSONLayer = {addData: jest.fn()};

			mapImpl.data = {name: 'more data'};

			mapImpl._initializeGeoJSONData();

			expect(mapImpl._geoJSONLayer.addData.mock.calls[0][0]).toBe(mapImpl.data);
		});
	});

	describe('_initializeLocation()', () => {
		it('should directly call initializeMap with the given location if geolocation is false', () => {
			const location = getLocation();

			mapImpl._initializeMap = jest.fn();

			mapImpl.geolocation = false;

			mapImpl._initializeLocation(location);

			expect(mapImpl._initializeMap.mock.calls[0][0]).toEqual({location});
		});
	});

	describe('_initializeMap()', () => {
		let position;

		beforeEach(() => {
			position = {location: getLocation()};
		});

		it('should store the given position as position', () => {
			mapImpl._initializeMap(position);

			expect(position).toBe(mapImpl.position);
		});

		it('should store the given position as originalPosition', () => {
			mapImpl._initializeMap(position);

			expect(position).toBe(mapImpl._originalPosition);
		});

		it('should create a new map and store it as _map', () => {
			mapImpl._initializeMap(position);

			expect(mapImpl._map.name).toBe('map');
			expect(mapImpl._map.location).toBe(position.location);
		});

		it('should call _getControlsConfig()', () => {
			jest.spyOn(mapImpl, '_getControlsConfig');

			mapImpl._initializeMap(position);

			expect(mapImpl._getControlsConfig).toHaveBeenCalledTimes(1);
		});

		it('should call _createCustomControls()', () => {
			jest.spyOn(mapImpl, '_createCustomControls');

			mapImpl._initializeMap(position);

			expect(mapImpl._createCustomControls).toHaveBeenCalledTimes(1);
		});

		it('should call _bindUIMB()', () => {
			jest.spyOn(mapImpl, '_bindUIMB');

			mapImpl._initializeMap(position);

			expect(mapImpl._bindUIMB).toHaveBeenCalledTimes(1);
		});

		it('should call _initializeGeoJSONData()', () => {
			jest.spyOn(mapImpl, '_initializeGeoJSONData');

			mapImpl._initializeMap(position);

			expect(mapImpl._initializeGeoJSONData).toHaveBeenCalledTimes(1);
		});
	});

	describe('_handleGeoJSONLayerFeaturesAdded()', () => {
		class Geometry {
			constructor() {
				this.location = getLocation();

				jest.spyOn(this, 'get');
			}

			get() {
				return this.location;
			}
		}

		class Feature {
			constructor() {
				this.geometry = new Geometry();

				jest.spyOn(this, 'getGeometry');
			}

			getGeometry() {
				return this.geometry;
			}
		}

		it('should get the map bounds using getBounds()', () => {
			mapImpl._handleGeoJSONLayerFeaturesAdded({features: []});

			expect(mapImpl.getBounds).toHaveBeenCalledTimes(1);
		});

		it('should get the feature geometry for each feature', () => {
			const feature = new Feature();

			mapImpl._handleGeoJSONLayerFeaturesAdded({features: [feature]});

			expect(feature.getGeometry).toHaveBeenCalledTimes(1);
			expect(feature.geometry.get).toHaveBeenCalledTimes(1);
		});

		it('should update the map position when there is a single feature', () => {
			const feature = new Feature();

			mapImpl._handleGeoJSONLayerFeaturesAdded({features: [feature]});

			expect(mapImpl.position.location).toBe(feature.geometry.location);
		});

		it('should add the features locations to the map bounds when there are multiple features', () => {
			const featureA = new Feature();
			const featureB = new Feature();

			mapImpl._handleGeoJSONLayerFeaturesAdded(
				{
					features: [featureA, featureB],
				}
			);

			expect(mapImpl.getBounds().locations).toEqual([
				featureA.geometry.location,
				featureB.geometry.location,
			]);
		});
	});

	describe('_handleGeoJSONLayerFeatureClicked()', () => {
		it('should emit a featureClick event', () => {
			mapImpl._handleGeoJSONLayerFeatureClicked({});

			expect(mapImpl.emit.mock.calls[0][0]).toBe('featureClick');
		});

		it('should send the given feature as event data', () => {
			mapImpl._handleGeoJSONLayerFeatureClicked({feature: 'feature'});

			expect(mapImpl.emit.mock.calls[0][1]).toEqual({feature: 'feature'});
		});
	});

	describe('_handleGeoLocationMarkerDragended()', () => {
		it('should get the location position with geocoder.reverse()', () => {
			const location = getLocation();

			jest.spyOn(mapImpl._getGeocoder(), 'reverse');

			mapImpl._handleGeoLocationMarkerDragended({location});

			expect(mapImpl._getGeocoder().reverse.mock.calls[0][0]).toBe(location);
		});

		it('should update the instance position', () => {
			const location = getLocation();

			mapImpl._handleGeoLocationMarkerDragended({location});

			expect(mapImpl.position).toEqual({name: 'data', location});
		});
	});

	describe('_handleHomeButtonClicked()', () => {
		it('should call preventDefault on the given event', () => {
			const event = {preventDefault: jest.fn()};
			const position = {location: getLocation()};

			mapImpl._originalPosition = position;

			mapImpl._handleHomeButtonClicked(event);

			expect(event.preventDefault).toHaveBeenCalledTimes(1);
		});

		it('should set the instance position to _originalPosition', () => {
			const event = {preventDefault: jest.fn()};
			const position = {location: getLocation()};

			mapImpl._originalPosition = position;

			mapImpl._handleHomeButtonClicked(event);

			expect(mapImpl.position).toBe(position);
		});
	});

	describe('_handlePositionChanged()', () => {
		it('should call setCenter with the given location', () => {
			const location = getLocation();

			mapImpl._handlePositionChanged({location});

			expect(mapImpl.setCenter.mock.calls[0]).toEqual([location]);
		});

		it('should update the geolocationMarker position if present', () => {
			const locationA = getLocation();
			const locationB = getLocation();

			mapImpl._geolocationMarker = mapImpl.addMarker(locationA);

			expect(mapImpl._geolocationMarker.location.location).toEqual(locationA);

			mapImpl._handlePositionChanged({location: locationB});

			expect(mapImpl._geolocationMarker.location).toEqual(locationB);
		});
	});

	describe('_handleSearchButtonClicked()', () => {
		it('should update the instance position', () => {
			const position = {location: getLocation()};

			mapImpl._handleSearchButtonClicked({position});

			expect(mapImpl.position).toBe(position);
		});
	});

	describe('getBounds()', () => {
		it('should throw a not implemented Error', () => {
			expect(
				() => {
					new MapBase().getBounds();
				}
			).toThrow();
		});
	});

	describe('setCenter()', () => {
		it('should throw a not implemented Error', () => {
			expect(
				() => {
					new MapBase().setCenter();
				}
			).toThrow();
		});
	});

	describe('_createMap()', () => {
		it('should throw a not implemented Error', () => {
			expect(
				() => {
					new MapBase()._createMap();
				}
			).toThrow();
		});
	});
});
