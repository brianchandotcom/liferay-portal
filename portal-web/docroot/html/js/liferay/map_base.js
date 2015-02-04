AUI.add(
	'liferay-map-base',
	function(A) {
		var AArray = A.Array;

		var Lang = A.Lang;

		var STR_CONTROLS = 'controls';

		var TPL_HOME_BUTTON = '<button class="btn btn-default home-button"><i class="glyphicon glyphicon-screenshot"></i></button>';

		var TPL_SEARCHBOX = '<div class="col-md-6 search-controls"><input class="search-input" placeholder="" type="text"></div>';

		var GeojsonBase = function() {
		};

		A.mix(
			GeojsonBase,
			{
				ATTRS: {
					map: {
						validator: Lang.isObject
					}
				},

				NAME: 'lfrmapgeojsonbase',

				NS: 'lfrmapgeojsonbase',

				prototype: {
					addData: function(data) {
						var instance = this;

						var nativeFeatures = instance._addData(data);

						var features = AArray.map(nativeFeatures, instance._wrapNativeFeature, instance);

						instance.fire(
							'featuresAdded',
							{
								features: features
							}
						);
					},

					_onFeatureClick: function(event) {
						var instance = this;

						instance.fire(
							'featureClick',
							{
								feature: instance._wrapNativeFeature(event)
							}
						);
					}
				}
			},
			true
		);

		var MarkerBase = function() {
		};

		A.mix(
			MarkerBase,
			{
				ATTRS: {
					location: {
						validator: Lang.isObject,
						value: {
							lat: 0,
							lng: 0
						}
					},

					map: {
						validator: Lang.isObject
					},

					nativeMarker: {
						readOnly: true,
						validator: Lang.isObject
					}
				},

				NAME: 'lfrmapmarkerbase',

				NS: 'lfrmapmarkerbase',

				prototype: {
					initializer: function() {
						var instance = this;

						var location = instance.get('location');

						var map = instance.get('map');

						var nativeMarker = instance._createNativeMarker(location, map);

						instance._bindNativeMarker(nativeMarker);

						instance._set('nativeMarker', nativeMarker);
					},

					_getNativeEventFn: function(eventType) {
						var instance = this;

						var nativeEventFn = instance['_onNative' + eventType + 'Fn'];

						if (!nativeEventFn) {
							nativeEventFn = A.rbind('_onNativeEvent', instance, eventType);
						}

						return nativeEventFn;
					},

					_onNativeEvent: function(nativeEvent, eventType) {
						var instance = this;

						var normalizedEvent = instance._normalizeEvent(nativeEvent);

						normalizedEvent.nativeEvent = nativeEvent;

						instance.fire(eventType, normalizedEvent);
					}
				}
			},
			true
		);

		var Base = function() {
		};

		A.mix(
			Base,
			{
				ATTRS: {
					controls: {
						validator: Lang.isArray,
						value: [
							'Base.CONTROLS.PAN',
							'Base.CONTROLS.TYPE',
							'Base.CONTROLS.ZOOM'
						]
					},

					data: {
						validator: Lang.isObject
					},

					geolocation: {
						validator: Lang.isBoolean,
						value: false
					},

					position: {
						validator: Lang.isObject,
						value: {
							location: {
								lat: 0,
								lng: 0
							}
						}
					},

					zoom: {
						validator: Lang.isNumber,
						value: 11
					}
				},

				CONTROLS: {
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
				},

				NAME: 'lfrmapbase',

				NS: 'lfrmapbase',

				POSITION: {
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
				},

				prototype: {
					initializer: function() {
						var instance = this;

						var position = instance.get('position');

						var location = (position && position.location) ? position.location : {};

						if (!location.lat || !location.lng) {
							Liferay.Util.getGeolocation(
								function(latitude, longitude) {
									var location = {
										lat: latitude,
										lng: longitude
									};

									instance._initializeLocation(location);
								}
							);
						}
						else {
							instance._initializeLocation(location);
						}
					},

					bindUI: function() {
						var instance = this;

						instance._eventHandles = [
							instance.on('positionChange', A.bind('_onPositionChange', instance))
						];
					},

					destructor: function() {
						var instance = this;

						var geojsonLayer = instance._geojsonLayer;

						if (geojsonLayer) {
							geojsonLayer.destroy();
						}

						var searchControl = instance._customControls[Base.CONTROLS.SEARCH];

						if (searchControl) {
							searchControl.destroy();
						}

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					addMarker: function(location) {
						var instance = this;

						var MarkerImpl = instance.MarkerImpl;

						if (MarkerImpl) {
							return new MarkerImpl(
								{
									location: location,
									map: instance._map
								}
							);
						}
					},

					getNativeMap: function() {
						var instance = this;

						return instance._map;
					},

					openDialog: function(dialogConfig) {
						var instance = this;

						instance._getDialog().open(dialogConfig);
					},

					_bindUIMB: function() {
						var instance = this;

						var geojsonLayer = instance._geojsonLayer;

						if (geojsonLayer) {
							geojsonLayer.on(
								'featuresAdded',
								function(event) {
									var bounds = instance.getBounds();

									var features = event.features;

									if (features.length > 1) {
										AArray.each(
											event.features,
											function(item, index) {
												bounds.extend(item.getGeometry().get());
											}
										);

										instance.fitBounds(bounds);
									}
									else {
										instance.set(
											'position',
											{
												location: features[0].getGeometry().get()
											}
										);
									}
								}
							);

							geojsonLayer.on(
								'featureClick',
								function(event) {
									instance.fire(
										'featureClick',
										{
											feature: event.feature
										}
									);
								}
							);
						}

						var geolocationMarker = instance._geolocationMarker;

						if (geolocationMarker) {
							geolocationMarker.on(
								'dragend',
								function(event) {
									var geocoder = instance._getGeocoder();

									geocoder.reverse(
										event.location,
										function(event) {
											instance.set('position', event.data);
										}
									);
								}
							);
						}

						var customControls = instance._customControls;

						var homeControl = customControls[Base.CONTROLS.HOME];

						if (homeControl) {
							homeControl.on('click', instance._onHomeButtonClick, instance);
						}

						var searchControl = customControls[Base.CONTROLS.SEARCH];

						if (searchControl) {
							searchControl.on(
								'search',
								function(event) {
									instance.set('position', event.position);
								}
							);
						}
					},

					_createCustomControls: function() {
						var instance = this;

						var controls = instance.get(STR_CONTROLS);

						var customControls = {};

						if (AArray.indexOf(controls, Base.CONTROLS.HOME) !== -1) {
							var homeControl = A.Node.create(TPL_HOME_BUTTON);

							customControls[Base.CONTROLS.HOME] = homeControl;

							instance.addControl(homeControl, Base.POSITION.RIGHT_BOTTOM);
						}

						if (AArray.indexOf(controls, Base.CONTROLS.SEARCH) !== -1) {
							var SearchImpl = instance.SearchImpl;

							if (SearchImpl) {
								var searchControl = A.Node.create(TPL_SEARCHBOX);

								customControls[Base.CONTROLS.SEARCH] = new SearchImpl(
									{
										inputNode: searchControl.one('input')
									}
								);

								instance.addControl(searchControl, Base.POSITION.TOP_LEFT);
							}
						}

						instance._customControls = customControls;
					},

					_getControlsConfig: function() {
						var instance = this;

						var controls = instance.get(STR_CONTROLS);

						var availableControls = controls.map(
							function(item, index) {
								return Lang.isString(item) ? item : item.name;
							}
						);

						var config = {};

						A.Object.each(
							instance.CONTROLS_CONFIG_MAP,
							function(item, index) {
								var controlIndex = AArray.indexOf(availableControls, index);

								if (controlIndex > -1) {
									var controlConfig = controls[controlIndex];

									if (Lang.isObject(controlConfig) && controlConfig.cfg) {
										config[item + 'Options'] = controlConfig.cfg;
									}
								}

								config[item] = (controlIndex !== -1);
							}
						);

						return config;
					},

					_getDialog: function() {
						var instance = this;

						var dialog = instance._dialog;

						if (!dialog) {
							var DialogImpl = instance.DialogImpl;

							if (DialogImpl) {
								dialog = new DialogImpl(
									{
										map: instance._map
									}
								);

								instance._dialog = dialog;
							}
						}

						return dialog;
					},

					_getGeocoder: function() {
						var instance = this;

						var geocoder = instance._geocoder;

						if (!geocoder) {
							var GeoCoderImpl = instance.GeocoderImpl;

							if (GeoCoderImpl) {
								geocoder = new GeoCoderImpl();

								instance._geocoder = geocoder;
							}
						}

						return geocoder;
					},

					_initializeGeojsonData: function() {
						var instance = this;

						var data = instance.get('data');

						if (data && instance._geojsonLayer) {
							instance._geojsonLayer.addData(data);
						}
					},

					_initializeLocation: function(location) {
						var instance = this;

						if (instance.get('geolocation') && instance.GeocoderImpl) {
							var geocoder = instance._getGeocoder();

							geocoder.reverse(
								location,
								function(event) {
									instance._initializeMap(event.data);
								}
							);
						}
						else {
							instance._initializeMap(
								{
									location: location
								}
							);
						}
					},

					_initializeMap: function(position) {
						var instance = this;

						var location = position.location;

						instance._originalPosition = position;

						var controlsConfig = instance._getControlsConfig();

						instance._map = instance._createMap(location, controlsConfig);

						if (instance.get('geolocation')) {
							instance._geolocationMarker = instance.addMarker(location);
						}

						var GeojsonImpl = instance.GeojsonImpl;

						if (GeojsonImpl) {
							instance._geojsonLayer = new GeojsonImpl(
								{
									map: instance._map
								}
							);
						}

						instance.set('position', position);

						instance._createCustomControls();
						instance._bindUIMB();
						instance._initializeGeojsonData();
					},

					_onHomeButtonClick: function(event) {
						var instance = this;

						event.preventDefault();

						instance.set('position', instance._originalPosition);
					},

					_onPositionChange: function(event) {
						var instance = this;

						var location = event.newVal.location;

						instance.setCenter(location);

						var geolocationMarker = instance._geolocationMarker;

						if (geolocationMarker) {
							geolocationMarker.setPosition(location);
						}
					}
				},

				get: function(id, callback) {
					var instance = this;

					var map = Liferay.component(id);

					if (map) {
						callback(map);
					}
					else {
						var pendingCallbacks = instance._pendingCallbacks[id] || [];

						pendingCallbacks.push(callback);

						instance._pendingCallbacks[id] = pendingCallbacks;
					}
				},

				register: function(id, map) {
					var instance = this;

					Liferay.component(id, map);

					A.Array.each(
						instance._pendingCallbacks[id],
						function(item, index) {
							item(map);
						}
					);
				},

				_pendingCallbacks: {}
			},
			true
		);

		Liferay.MapBase = Base;
		Liferay.MapGeojsonBase = GeojsonBase;
		Liferay.MapMarkerBase = MarkerBase;
	},
	'',
	{
		requires: ['aui-base']
	}
);