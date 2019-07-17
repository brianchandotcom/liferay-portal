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

import State, {Config} from 'metal-state';

/**
 * GeoJSONBase
 * Allows adding controls (called features) to the map, that produce
 * diverse actions. For example, a button for centering the map
 * view will act as a feature.
 * @abstract
 * @review
 */
class GeoJSONBase extends State {
	/**
	 * Receives an object with native features data and tries
	 * to parse it with the implemented method _getNativeFeatures.
	 * If the generated Array of native features is not empty, it fires
	 * a 'featuresAdded' event.
	 * @param {Object} nativeFeaturesData Data to be processed.
	 * @review
	 */
	addData(nativeFeaturesData) {
		const nativeFeatures = this._getNativeFeatures(nativeFeaturesData);

		if (nativeFeatures.length > 0) {
			this.emit('featuresAdded', {
				features: nativeFeatures.map(this._wrapNativeFeature)
			});
		}
	}

	/**
	 * Callback executed when a native feature has been clicked.
	 * It receives the feature as parameter, and emits a 'featureClick'
	 * event with the wrapped feature as event data.
	 * @param {Object} nativeFeature Feature to be wrapped and sent
	 * @protected
	 * @review
	 */
	_handleFeatureClicked(nativeFeature) {
		this.emit('featureClick', {
			feature: this._wrapNativeFeature(nativeFeature)
		});
	}

	/**
	 * Parses an object and return an array of the
	 * parsed features. If no feature has been parsed it may return an
	 * empty array.
	 * @abstract
	 * @param {Object} nativeFeaturesData
	 * @protected
	 * @return {Object[]} List of native features to be added
	 * @review
	 */
	_getNativeFeatures(
		/* eslint-disable no-unused-vars */
		nativeFeaturesData
		/* eslint-enable no-unused-vars */
	) {
		throw new Error('Must be implemented');
	}

	/**
	 * Wraps a native feature.
	 * @abstract
	 * @param {Object} nativeFeature
	 * @protected
	 * @return {Object} Wrapped native feature
	 * @review
	 */
	_wrapNativeFeature(
		/* eslint-disable no-unused-vars */
		nativeFeature
		/* eslint-enable no-unused-vars */
	) {
		throw new Error('Must be implemented');
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
GeoJSONBase.STATE = {
	/**
	 * Map to be used
	 * @review
	 * @type {Object}
	 */
	map: Config.object()
};

window.Liferay = window.Liferay || {};

window.Liferay.MapGeojsonBase = GeoJSONBase;

export default GeoJSONBase;
export {GeoJSONBase};
