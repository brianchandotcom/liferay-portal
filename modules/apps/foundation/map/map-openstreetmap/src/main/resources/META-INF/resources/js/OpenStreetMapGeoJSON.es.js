import GeoJSONBase from 'map-common/js/GeoJSONBase.es';

/**
 * OpenStreetMapGeoJSONBase
 * @review
 */
class OpenStreetMapGeoJSONBase extends GeoJSONBase {
	/**
	 * Creates a new map geojson parser using OpenStreetMap's API
	 * @param {Array} args List of arguments to be passed to State
	 * @review
	 */
	constructor(...args) {
		super(...args);

		this._handleFeatureClicked = this._handleFeatureClicked.bind(this);
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	_getNativeFeatures(geoJSONData) {
		const features = [];

		L.geoJson(
			geoJSONData, {
				onEachFeature: (feature, layer) => {
					layer.on('click', this._handleFeatureClicked);

					features.push(feature);
				},
			}
		).addTo(this.map);

		return features;
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	_wrapNativeFeature(nativeFeature) {
		const feature = nativeFeature.geometry
			? nativeFeature
			: nativeFeature.target.feature;
		const geometry = feature.geometry;

		return {
			getGeometry() {
				return {
					get() {
						return L.latLng(geometry.coordinates[1], geometry.coordinates[0]);
					},
				};
			},

			getMarker() {
				return nativeFeature.target;
			},

			getProperty(prop) {
				return feature.properties[prop];
			},
		};
	}
}

export default OpenStreetMapGeoJSONBase;
export {OpenStreetMapGeoJSONBase};