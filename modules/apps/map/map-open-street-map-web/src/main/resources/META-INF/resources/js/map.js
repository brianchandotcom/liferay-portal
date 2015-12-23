/* global L */

AUI.add(
	'liferay-map-openstreet',
	function(A) {
		var Lang = A.Lang;

		var MapBase = Liferay.MapBase;

		var CONTROLS_CONFIG_MAP = {};

		CONTROLS_CONFIG_MAP[MapBase.CONTROLS.ATTRIBUTION] = 'attributionControl';
		CONTROLS_CONFIG_MAP[MapBase.CONTROLS.ZOOM] = 'zoomControl';

		var CONTROLS_POSITION_MAP = {};

		CONTROLS_POSITION_MAP[MapBase.POSITION.BOTTOM] = 'bottomleft';
		CONTROLS_POSITION_MAP[MapBase.POSITION.BOTTOM_CENTER] = 'bottomleft';
		CONTROLS_POSITION_MAP[MapBase.POSITION.BOTTOM_LEFT] = 'bottomleft';
		CONTROLS_POSITION_MAP[MapBase.POSITION.BOTTOM_RIGHT] = 'bottomright';
		CONTROLS_POSITION_MAP[MapBase.POSITION.CENTER] = 'topleft';
		CONTROLS_POSITION_MAP[MapBase.POSITION.LEFT] = 'topleft';
		CONTROLS_POSITION_MAP[MapBase.POSITION.LEFT_BOTTOM] = 'bottomleft';
		CONTROLS_POSITION_MAP[MapBase.POSITION.LEFT_CENTER] = 'topleft';
		CONTROLS_POSITION_MAP[MapBase.POSITION.LEFT_TOP] = 'topleft';
		CONTROLS_POSITION_MAP[MapBase.POSITION.RIGHT] = 'bottomright';
		CONTROLS_POSITION_MAP[MapBase.POSITION.RIGHT_BOTTOM] = 'bottomright';
		CONTROLS_POSITION_MAP[MapBase.POSITION.RIGHT_CENTER] = 'bottomright';
		CONTROLS_POSITION_MAP[MapBase.POSITION.RIGHT_TOP] = 'topright';
		CONTROLS_POSITION_MAP[MapBase.POSITION.TOP] = 'topright';
		CONTROLS_POSITION_MAP[MapBase.POSITION.TOP_CENTER] = 'topright';
		CONTROLS_POSITION_MAP[MapBase.POSITION.TOP_LEFT] = 'topleft';
		CONTROLS_POSITION_MAP[MapBase.POSITION.TOP_RIGHT] = 'topright';

		var OpenStreetMapDialog = A.Component.create(
			{
				ATTRS: {
					map: {
						validator: Lang.isObject
					}
				},

				EXTENDS: A.Base,

				NAME: 'lfrmapdialogopenstreet',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._dialog = L.popup(
							{
								className: 'leaflet-popup',
								minWidth: 400
							}
						);
					},

					open: function(cfg) {
						var instance = this;

						var dialog = instance._dialog;

						dialog.setContent(cfg.content);

						dialog.setLatLng(cfg.position);

						dialog.options.offset = cfg.marker.options.icon.options.popupAnchor || [0, 0];

						dialog.openOn(instance.get('map'));
					}
				}
			}
		);

		var OpenStreetMapGeocoder = A.Component.create(
			{
				EXTENDS: A.Base,

				NAME: 'lfrmapgeocoderopenstreet',

				prototype: {
					TPL_FORWARD_GEOCODING_URL: '//nominatim.openstreetmap.org/search?format=json&json_callback={callback}&q={query}',

					TPL_REVERSE_GEOCODING_URL: '//nominatim.openstreetmap.org/reverse?format=json&json_callback={callback}&lat={lat}&lon={lng}',

					forward: function(query, callback) {
						var instance = this;

						var forwardURL = Lang.sub(
							instance.TPL_FORWARD_GEOCODING_URL,
							{
								query: query
							}
						);

						A.jsonp(
							forwardURL,
							{
								context: instance,
								on: {
									success: A.rbind('_handleForwardJSONP', instance, callback)
								}
							}
						);
					},

					reverse: function(location, callback) {
						var instance = this;

						var reverseURL = Lang.sub(
							instance.TPL_REVERSE_GEOCODING_URL,
							location
						);

						A.jsonp(
							reverseURL,
							{
								context: instance,
								on: {
									success: A.rbind('_handleReverseJSONP', instance, callback)
								}
							}
						);
					},

					_handleForwardJSONP: function(response, callback) {
						callback(response);
					},

					_handleReverseJSONP: function(response, callback) {
						var result = {
							data: {},
							err: response.error
						};

						if (!result.err) {
							result.data = {
								address: response.display_name,
								location: {
									lat: response.lat,
									lng: response.lon
								}
							};
						}

						callback(result);
					}
				}
			}
		);

		var OpenStreetMapGeojson = A.Component.create(
			{
				AUGMENTS: [Liferay.MapGeojsonBase],

				EXTENDS: A.Base,

				NAME: 'lfrmapgeojsonopenstreet',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._eventHandles = [];
					},

					destructor: function() {
						var instance = this;

					},

					_addData: function(geojsonData) {
						var instance = this;

						var features = [];

						L.geoJson(
							geojsonData,
							{
								onEachFeature: function(feature, layer) {
									instance._bindLayer(layer);

									features.push(feature);
								}
							}
						).addTo(instance.get('map'));

						return features;
					},

					_bindLayer: function(layer) {
						var instance = this;

						layer.on('click', A.bind('_onFeatureClick', instance));
					},

					_getFeatureStyle: function(feature) {
						var style = {
							icon: feature.getProperty('icon')
						};

						return style;
					},

					_wrapNativeFeature: function(event) {
						var feature = (event.geometry) ? event : event.target.feature;

						var geometry = feature.geometry;

						return {
							getGeometry: function() {
								return {
									get: function() {
										return L.latLng(geometry.coordinates[1], geometry.coordinates[0]);
									},

									getType: function() {
										return geometry.type;
									}
								};
							},

							getMarker: function() {
								return event.target;
							},

							getProperty: function(prop) {
								return feature.properties[prop];
							}
						};
					}
				}
			}
		);

		var OpenStreetMapMarker = A.Component.create(
			{
				AUGMENTS: [Liferay.MapMarkerBase],

				EXTENDS: A.Base,

				NAME: 'lfrmapmarkeropenstreet',

				prototype: {
					setPosition: function(location) {
						var instance = this;

						var nativeMarker = instance.get('nativeMarker');

						nativeMarker.setLatLng(location);
					},

					_bindNativeMarker: function(nativeMarker) {
						var instance = this;

						nativeMarker.on('click', instance._getNativeEventFn('click'));
						nativeMarker.on('dblclick', instance._getNativeEventFn('dblclick'));
						nativeMarker.on('drag', instance._getNativeEventFn('drag'));
						nativeMarker.on('dragend', instance._getNativeEventFn('dragend'));
						nativeMarker.on('dragstart', instance._getNativeEventFn('dragstart'));
						nativeMarker.on('mousedown', instance._getNativeEventFn('mousedown'));
						nativeMarker.on('mouseout', instance._getNativeEventFn('mouseout'));
						nativeMarker.on('mouseover', instance._getNativeEventFn('mouseover'));
					},

					_createNativeMarker: function(location, map) {
						var instance = this;

						var nativeMarker = L.marker(
							location,
							{
								draggable: true
							}
						).addTo(map);

						return nativeMarker;
					},

					_normalizeEvent: function(nativeEvent) {
						var instance = this;

						return {
							location: nativeEvent.target.getLatLng()
						};
					}
				}
			}
		);

		var OpenStreetMap = A.Component.create(
			{
				ATTRS: {
					tileURI: {
						validator: Lang.isString,
						value: '//{s}.tile.openstreetmap.org/{z}/{x}/{y}.png'
					}
				},

				AUGMENTS: [MapBase],

				EXTENDS: A.Widget,

				NAME: 'lfrmapopenstreet',

				prototype: {
					DialogImpl: OpenStreetMapDialog,

					GeocoderImpl: OpenStreetMapGeocoder,

					GeojsonImpl: OpenStreetMapGeojson,

					MarkerImpl: OpenStreetMapMarker,

					addControl: function(control, position) {
						var instance = this;

						var LeafletControl = L.Control.extend(
							{
								onAdd: function(map) {
									return control.getDOMNode();
								},
								options: {
									position: CONTROLS_POSITION_MAP[position]
								}
							}
						);

						instance._map.addControl(new LeafletControl());
					},

					fitBounds: function(bounds) {
						var instance = this;

						A.soon(A.bind('fitBounds', instance._map, bounds));
					},

					getBounds: function() {
						var instance = this;

						return instance._map.getBounds();
					},

					setCenter: function(location) {
						var instance = this;

						instance._map.panTo(location);
					},

					_createMap: function(location, controlsConfig) {
						var instance = this;

						var mapConfig = {
							center: location,
							layers: [L.tileLayer(instance.get('tileURI'))],
							zoom: instance.get('zoom')
						};

						return L.map(
							instance.get('boundingBox').getDOMNode(),
							A.merge(mapConfig, controlsConfig)
						);
					},

					CONTROLS_CONFIG_MAP: CONTROLS_CONFIG_MAP
				}
			}
		);

		Liferay.OpenStreetMap = OpenStreetMap;
	},
	'',
	{
		requires: ['jsonp', 'liferay-map-base', 'timers']
	}
);