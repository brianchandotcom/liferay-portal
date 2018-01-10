import GeoJSONBase from '../../src/main/resources/META-INF/resources/js/GeoJSONBase.es';

describe('GeoJSONBase', () => {
	let features = [
		{
			name: 'FeatureA'
		},
		{
			name: 'FeatureB'
		}
	];

	let geoJSONBase;
	let geoJSONChild;

	class GeoJSONChild extends GeoJSONBase {
		_getNativeFeatures(data) {
			return data ? features : [];
		}

		_wrapNativeFeature(feature) {
			return {feature, wrapped: true};
		}
	}

	beforeEach(() => {
		geoJSONBase = new GeoJSONBase();
		geoJSONChild = new GeoJSONChild();

		jest.spyOn(geoJSONChild, '_getNativeFeatures');
		jest.spyOn(geoJSONChild, '_wrapNativeFeature');
		jest.spyOn(geoJSONChild, 'emit');
	});

	describe('addData()', () => {
		it('should apply _getNativeFeatures() to the given parameter', () => {
			geoJSONChild.addData('some data to be parsed');

			expect(geoJSONChild._getNativeFeatures).toHaveBeenCalledTimes(1);
			expect(geoJSONChild._getNativeFeatures).toHaveBeenCalledWith('some data to be parsed');
		});

		it('should emit a featuresAdded event after adding data', () => {
			geoJSONChild.addData('more data');

			expect(geoJSONChild.emit).toHaveBeenCalledTimes(1);
			expect(geoJSONChild.emit.mock.calls[0][0]).toBe('featuresAdded');
		});

		it('should not emit a featuresAdded event with no features', () => {
			geoJSONChild.addData();

			expect(geoJSONChild.emit).not.toHaveBeenCalled();
		});

		it('should wrap every feature with _wrapNativeFeature()', () => {
			geoJSONChild.addData('even more stuff');

			expect(geoJSONChild.emit).toHaveBeenCalledTimes(1);

			const eventData = geoJSONChild.emit.mock.calls[0][1];

			eventData.features.forEach(
				(wrappedFeature, index) => {
					expect(wrappedFeature.wrapped);
					expect(wrappedFeature.feature).toBe(features[index]);
				}
			);
		});
	});

	describe('_handleFeatureClicked()', () => {
		it('should emit a featureClick event', () => {
			geoJSONChild._handleFeatureClicked();

			expect(geoJSONChild.emit).toHaveBeenCalledTimes(1);
			expect(geoJSONChild.emit.mock.calls[0][0], 'featureClick');
		});

		it('should wrap the given feature with _wrapNativeFeature()', () => {
			geoJSONChild._handleFeatureClicked('Nice feature baby');

			expect(geoJSONChild.emit).toHaveBeenCalledTimes(1);

			const eventData = geoJSONChild.emit.mock.calls[0][1];

			expect(eventData.feature).toEqual(
				{
					feature: 'Nice feature baby',
					wrapped: true,
				}
			);
		});
	});

	describe('_getNativeFeatures()', () => {
		it('should throw a not implemented error', () => {
			expect(
				() => {
					geoJSONBase._getNativeFeatures();
				}
			).toThrow();
		});
	});

	describe('_wrapNativeFeature()', () => {
		it('should throw a not implemented error', () => {
			expect(
				() => {
					geoJSONBase._wrapNativeFeature();
				}
			).toThrow();
		});
	});
});
