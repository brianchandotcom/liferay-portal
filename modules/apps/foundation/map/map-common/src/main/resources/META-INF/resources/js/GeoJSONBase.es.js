import State, {Config} from 'metal-state';

/**
 * GeoJSONBase
 * Allows adding controls (called features) to the map, that produce
 * diverse actions. For example, a button for centering the map
 * view will act as a feature.
 * @abstract
 */
class GeoJSONBase extends State {
	/**
	 * Receives an object with native features data and tries
	 * to parse it with the implemented method _getNativeFeatures.
	 * If the generated Array of native features is not empty, it fires
	 * a 'featuresAdded' event.
	 * @param {Object} nativeFeaturesData Data to be processed.
	 */
	addData(nativeFeaturesData) {
		const nativeFeatures = this._getNativeFeatures(nativeFeaturesData);

		if (nativeFeatures.length > 0) {
			this.emit(
				'featuresAdded',
				{
					features: nativeFeatures.map(this._wrapNativeFeature),
				}
			);
		}
	}

	/**
	 * Callback executed when a native feature has been clicked.
	 * It receives the feature as parameter, and emits a 'featureClick'
	 * event with the wrapped feature as event data.
	 * @param {Object} nativeFeature Feature to be wrapped and sent
	 * @protected
	 */
	_handleFeatureClicked(nativeFeature) {
		this.emit(
			'featureClick',
			{
				feature: this._wrapNativeFeature(nativeFeature),
			}
		);
	}

	/**
	 * Parses a nativeFeaturesData object and return an array of the
	 * parsed features. If no feature has been parsed it may return an
	 * empty array.
	 * @protected
	 * @abstract
	 * @param {Object} nativeFeaturesData
	 * @return {Object[]} List of native features to be added
	 */
	_getNativeFeatures(nativeFeaturesData) {
		throw new Error('Must be implemented');
	}

	/**
	 * Wraps a native feature.
	 * @protected
	 * @abstract
	 * @param {Object} nativeFeature
	 * @return {Object} Wrapped native feature
	 */
	_wrapNativeFeature(nativeFeature) {
		throw new Error('Must be implemented');
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */
GeoJSONBase.STATE = {
	/**
	 * Map to be used
	 * @type {Object}
	 */
	map: Config.object(),
};

window.Liferay = window.Liferay || {};

window.Liferay.MapGeojsonBase = GeoJSONBase;

export default GeoJSONBase;
export {GeoJSONBase};