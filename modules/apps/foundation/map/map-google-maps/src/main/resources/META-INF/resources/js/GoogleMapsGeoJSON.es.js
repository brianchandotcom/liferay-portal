import GeoJSONBase from 'map-common/js/GeoJSONBase.es';

/**
 * GoogleMapsGeoJSON
 */
class GoogleMapsGeoJSON extends GeoJSONBase {
	/**
	 * Creates a new geojson parser using Google Map's API
	 * @param  {Array} args List of arguments to be passed to State
	 */
	constructor(...args) {
		super(...args);

		this.eventHandlers = [];

		this._getFeatureStyle = this._getFeatureStyle.bind(this);
		this._handleFeatureClicked = this._handleFeatureClicked.bind(this);

		this.map.data.setStyle(this._getFeatureStyle);

		this._bindUI();
	}

	/**
	 * Removes the listeners that have been added to the map object.
	 */
	destructor() {
		this._eventHandlers.forEach(
			item => {
				google.maps.event.removeListener(item);
			}
		);
	}

	/**
	 * Adds listeners for the created map object.
	 * It listens for click events and executes
	 * GoogleMapsGeoJSON._handleFeatureClicked.
	 * @protected
	 */
	_bindUI() {
		this._eventHandlers = [
			google.maps.event.addListener(
				this.map.data,
				'click',
				this._handleFeatureClicked
			),
		];
	}

	/**
	 * Gets the internal style of the given feature. Both the feature and the
	 * style are native Google Maps objects.
	 * @param {Object} feature Google Maps native feature to be parsed.
	 * @protected
	 * @return {Object} Obtained style
	 */
	_getFeatureStyle(feature) {
		return {
			icon: feature.getProperty('icon'),
		};
	}

	/**
	 * @inheritDoc
	 */
	_getNativeFeatures(geoJSONData) {
		return this.map.data.addGeoJson(geoJSONData);
	}

	/**
	 * @inheritDoc
	 */
	_wrapNativeFeature(nativeFeature) {
		const feature = nativeFeature.getGeometry
			? nativeFeature
			: nativeFeature.feature;

		feature.getMarker = () => {
			if (!feature._marker) {
				const marker = new google.maps.Marker(
					{
						icon: feature.getProperty('icon'),
						map: this.map,
						opacity: 0,
						position: feature.getGeometry().get('location'),
						zIndex: -1,
					}
				);

				feature._marker = marker;
			}

			return feature.marker;
		};

		return feature;
	}
}

export default GoogleMapsGeoJSON;
export {GoogleMapsGeoJSON};