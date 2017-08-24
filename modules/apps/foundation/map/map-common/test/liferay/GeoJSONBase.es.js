/* global sinon, assert */

'use strict';

import GeoJSONBase from '../../src/main/resources/META-INF/resources/js/GeoJSONBase.es';

describe('GeoJSONBase', () => {
	let geoJSONBase;
	let geoJSONChild;
	let features = [{name: 'FeatureA'}, {name: 'FeatureB'}];

	/**
	 * Class extending the GeoJSONBase class
	 * We need to implement some methods with dummy content
	 */
	class GeoJSONChild extends GeoJSONBase {
		/** @inheritdoc */
		_getNativeFeatures(data) {
			return data ? features : [];
		}

		/** @inheritdoc */
		_wrapNativeFeature(feature) {
			return {feature, wrapped: true};
		}
	}

	beforeEach(() => {
		geoJSONBase = new GeoJSONBase();
		geoJSONChild = new GeoJSONChild();
		sinon.spy(geoJSONChild, 'emit');
		sinon.spy(geoJSONChild, '_getNativeFeatures');
		sinon.spy(geoJSONChild, '_wrapNativeFeature');
	});

	describe('addData()', () => {
		it('should apply _getNativeFeatures() to the given parameter', () => {
			geoJSONChild.addData('some data to be parsed');
			assert(geoJSONChild._getNativeFeatures.calledOnce);
			assert(
				geoJSONChild._getNativeFeatures.calledWith('some data to be parsed')
			);
		});

		it('should emit a featuresAdded event after adding data', () => {
			geoJSONChild.addData('more data');
			assert(geoJSONChild.emit.calledOnce);
			assert.equal(geoJSONChild.emit.firstCall.args[0], 'featuresAdded');
		});

		it('should not emit a featuresAdded event with no features', () => {
			geoJSONChild.addData();
			assert(geoJSONChild.emit.notCalled);
		});

		it('should wrap every feature with _wrapNativeFeature()', () => {
			geoJSONChild.addData('even more stuff');
			assert(geoJSONChild.emit.calledOnce);
			const eventData = geoJSONChild.emit.firstCall.args[1];
			eventData.features.forEach((wrappedFeature, index) => {
				assert(wrappedFeature.wrapped);
				assert.equal(wrappedFeature.feature, features[index]);
			});
		});
	});

	describe('_handleFeatureClicked()', () => {
		it('should emit a featureClick event', () => {
			geoJSONChild._handleFeatureClicked();
			assert(geoJSONChild.emit.calledOnce);
			assert(geoJSONChild.emit.firstCall.args[0], 'featureClick');
		});

		it('should wrap the given feature with _wrapNativeFeature()', () => {
			geoJSONChild._handleFeatureClicked('Nice feature baby');
			assert(geoJSONChild.emit.calledOnce);
			const eventData = geoJSONChild.emit.firstCall.args[1];
			assert.deepEqual(eventData.feature, {
				feature: 'Nice feature baby',
				wrapped: true,
			});
		});
	});

	describe('_getNativeFeatures()', () => {
		it('should throw a not implemented error', () => {
			assert.throw(() => geoJSONBase._getNativeFeatures());
		});
	});

	describe('_wrapNativeFeature()', () => {
		it('should throw a not implemented error', () => {
			assert.throw(() => geoJSONBase._wrapNativeFeature());
		});
	});
});