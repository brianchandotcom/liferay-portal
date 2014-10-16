AUI.add(
	'liferay-map-google',
	function(A) {
		var Lang = A.Lang;

		var MapBase = Liferay.MapBase;

		var CONTROLS_CONFIG_MAP = {};

		CONTROLS_CONFIG_MAP[MapBase.CONTROLS.OVERVIEW] = 'overviewMapControl';
		CONTROLS_CONFIG_MAP[MapBase.CONTROLS.PAN] = 'panControl';
		CONTROLS_CONFIG_MAP[MapBase.CONTROLS.ROTATE] = 'rotateControl';
		CONTROLS_CONFIG_MAP[MapBase.CONTROLS.SCALE] = 'scaleControl';
		CONTROLS_CONFIG_MAP[MapBase.CONTROLS.STREETVIEW] = 'streetViewControl';
		CONTROLS_CONFIG_MAP[MapBase.CONTROLS.TYPE] = 'mapTypeControl';
		CONTROLS_CONFIG_MAP[MapBase.CONTROLS.ZOOM] = 'zoomControl';

		var GoogleMapDialog = A.Component.create(
			{
				ATTRS: {
					map: {
						validator: Lang.isObject
					}
				},

				EXTENDS: A.Base,

				NAME: 'lfr-map-dialog-google',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._dialog = new google.maps.InfoWindow();
					},

					open: function(cfg) {
						var instance = this;

						instance._dialog.setOptions(cfg);

						instance._dialog.open(instance.get('map'));
					}
				}
			}
		);

		var GoogleMapGeocoder = A.Component.create(
			{
				EXTENDS: A.Base,

				NAME: 'lfr-map-geocoder-google',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._geocoder = new google.maps.Geocoder();
					},

					forward: function(query, callback) {
						var instance = this;

						var payload = {
							address: query
						};

						instance._geocoder.geocode(
							payload,
							A.rbind('_handleGeocoderResponse', instance, callback)
						);
					},

					reverse: function(location, callback) {
						var instance = this;

						var payload = {
							location: location
						};

						instance._geocoder.geocode(
							payload,
							A.rbind('_handleGeocoderResponse', instance, callback)
						);
					},

					_handleGeocoderResponse: function(response, status, callback) {
						var result = {
							data: {},
							err: status === google.maps.GeocoderStatus.OK ? null : status
						};

						if (!result.err) {
							var geocoderResult = response[0];

							var location = geocoderResult.geometry.location;

							result.data = {
								address: geocoderResult.formatted_address,
								location: {
									lat: location.lat(),
									lng: location.lng()
								}
							};
						}

						callback(result);
					}
				}
			}
		);

		var GoogleMapGeojson = A.Component.create(
			{
				ATTRS: {
					map: {
						validator: Lang.isObject
					}
				},

				AUGMENTS: [Liferay.MapGeojsonBase],

				EXTENDS: A.Base,

				NAME: 'lfr-map-geojson-google',

				prototype: {
					initializer: function() {
						var instance = this;

						instance.get('map').data.setStyle(A.bind('_getFeatureStyle', instance));

						instance._bindUI();
					},

					destructor: function() {
						var instance = this;

						A.Array.each(
							instance._eventHandles,
							function(item, index, collection) {
								google.maps.event.removeListener(item);
							}
						);
					},

					_addData: function(geojsonData) {
						var instance = this;

						return instance.get('map').data.addGeoJson(geojsonData);
					},

					_bindUI: function() {
						var instance = this;

						instance._eventHandles = [
							google.maps.event.addListener(instance.get('map').data, 'click', A.bind('_onFeatureClick', instance))
						];
					},

					_getFeatureStyle: function(feature) {
						var style = {
							icon: feature.getProperty('icon')
						};

						return style;
					},

					_wrapNativeFeature: function(feature) {
						return feature;
					}
				}
			}
		);

		var GoogleMapMarker = A.Component.create(
			{
				AUGMENTS: [Liferay.MapMarkerBase],

				EXTENDS: A.Base,

				NAME: 'lfr-map-marker-google',

				prototype: {
					setPosition: function(location) {
						var instance = this;

						var nativeMarker = instance.get('nativeMarker');

						nativeMarker.setPosition(location);
					},

					_bindNativeMarker: function(nativeMarker) {
						var instance = this;

						google.maps.event.addListener(nativeMarker, 'click', instance._getNativeEventFn('click'));
						google.maps.event.addListener(nativeMarker, 'dblclick', instance._getNativeEventFn('dblclick'));
						google.maps.event.addListener(nativeMarker, 'drag', instance._getNativeEventFn('drag'));
						google.maps.event.addListener(nativeMarker, 'dragend', instance._getNativeEventFn('dragend'));
						google.maps.event.addListener(nativeMarker, 'dragstart', instance._getNativeEventFn('dragstart'));
						google.maps.event.addListener(nativeMarker, 'mousedown', instance._getNativeEventFn('mousedown'));
						google.maps.event.addListener(nativeMarker, 'mouseout', instance._getNativeEventFn('mouseout'));
						google.maps.event.addListener(nativeMarker, 'mouseover', instance._getNativeEventFn('mouseover'));
					},

					_createNativeMarker: function(location, map) {
						var instance = this;

						var nativeMarker = new google.maps.Marker(
							{
								draggable: true,
								map: map,
								position: location
							}
						);

						return nativeMarker;
					},

					_normalizeEvent: function(nativeEvent) {
						var instance = this;

						return {
							location: {
								lat: nativeEvent.latLng.lat(),
								lng: nativeEvent.latLng.lng()
							}
						};
					}
				}
			}
		);

		var GoogleMapSearch = A.Component.create(
			{
				ATTRS: {
					inputNode: {
						setter: A.one
					}
				},

				EXTENDS: A.Base,

				NAME: 'lfr-map-search-google',

				prototype: {
					initializer: function() {
						var instance = this;

						var inputNode = instance.get('inputNode').getDOMNode();

						instance._autocomplete = new google.maps.places.Autocomplete(inputNode);

						instance._bindUI();
					},

					destructor: function() {
						var instance = this;

						A.Array.each(
							instance._eventHandles,
							function(item, index, collection) {
								google.maps.event.removeListener(item);
							}
						);
					},

					_bindUI: function() {
						var instance = this;

						instance._eventHandles = [
							google.maps.event.addListener(instance._autocomplete, 'place_changed', A.bind('_onPlaceChanged', instance))
						];
					},

					_onPlaceChanged: function() {
						var instance = this;

						var place = instance._autocomplete.getPlace();

						if (Lang.isObject(place)) {
							var location = place.geometry.location;

							instance.fire(
								'search',
								{
									position: {
										address: place.formatted_address,
										location: {
											lat: location.lat(),
											lng: location.lng()
										}
									}
								}
							);
						}
					}
				}
			}
		);

		var GoogleMap = A.Component.create(
			{
				AUGMENTS: [MapBase],

				EXTENDS: A.Widget,

				NAME: 'lfr-map-google',

				prototype: {
					DialogImpl: GoogleMapDialog,

					GeocoderImpl: GoogleMapGeocoder,

					GeojsonImpl: GoogleMapGeojson,

					MarkerImpl: GoogleMapMarker,

					SearchImpl: GoogleMapSearch,

					addControl: function(control, position) {
						var instance = this;

						var map = instance._map;

						if (map.controls[position]) {
							map.controls[position].push(control.getDOMNode());
						}
					},

					fitBounds: function(bounds) {
						var instance = this;

						instance._map.fitBounds(bounds);
						instance._map.panToBounds(bounds);
					},

					getBounds: function() {
						var instance = this;

						var bounds = instance._map.getBounds() || instance._bounds;

						if (!bounds) {
							bounds = new google.maps.LatLngBounds();

							instance._bounds = bounds;
						}

						return bounds;
					},

					setCenter: function(location) {
						var instance = this;

						instance._map.setCenter(location);
					},

					_createMap: function(location, controlsConfig) {
						var instance = this;

						var mapConfig = {
							center: location,
							mapTypeId: google.maps.MapTypeId.ROADMAP,
							zoom: instance.get('zoom')
						};

						return new google.maps.Map(
							instance.get('boundingBox').getDOMNode(),
							A.merge(mapConfig, controlsConfig)
						);
					},

					CONTROLS_CONFIG_MAP: CONTROLS_CONFIG_MAP
				}
			}
		);

		Liferay.GoogleMap = GoogleMap;
	},
	'',
	{
		requires: ['liferay-map-base']
	}
);