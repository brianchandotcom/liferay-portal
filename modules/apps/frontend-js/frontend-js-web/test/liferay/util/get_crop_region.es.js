'use strict';

import dom from 'metal-dom';
import getCropRegion from '../../../src/main/resources/META-INF/resources/liferay/util/get_crop_region.es';

describe('Liferay.Util.getCropRegion', () => {
	it('should return an object with original image height and width if region is an empty object', () => {
		const image = {
			naturalHeight: 438,
			naturalWidth: 558,
			offsetHeight: 438,
			offsetWidth: 558,
			tagName: 'IMG'
		};

		const region = {};

		expect(getCropRegion(image, region)).toEqual({
			height: 438,
			width: 558,
			x: 0,
			y: 0
		});
	});

	it('should throw an error if image parameter is not an image element', () => {
		const image = dom.buildFragment('<div />');

		const region = {
			height: 100,
			width: 100,
			x: 0,
			y: 0
		};

		const testFn = () => {
			getCropRegion(image, region);
		};

		expect(testFn).toThrow();
	});

	it('should throw an error if region parameter is not an object', () => {
		const image = {
			naturalHeight: 438,
			naturalWidth: 558,
			offsetHeight: 438,
			offsetWidth: 558,
			tagName: 'IMG'
		};

		const region = 'foo';

		const testFn = () => {
			getCropRegion(image, region);
		};

		expect(testFn).toThrow();
	});

	it('should return an object with coordinates set to 0 if region coordinates are negative values', () => {
		const image = {
			naturalHeight: 438,
			naturalWidth: 558,
			offsetHeight: 400,
			offsetWidth: 500,
			tagName: 'IMG'
		};

		const region = {
			height: 235,
			width: 300,
			x: -1,
			y: -1
		};

		expect(getCropRegion(image, region)).toEqual({
			height: 257.325,
			width: 334.8,
			x: 0,
			y: 0
		});
	});

	it('should return an object if imagePreview parameter is an image and region parameter is an object', () => {
		const image = {
			naturalHeight: 438,
			naturalWidth: 558,
			offsetHeight: 400,
			offsetWidth: 500,
			tagName: 'IMG'
		};

		const region = {
			height: 235,
			width: 300,
			x: 0,
			y: 0
		};

		expect(getCropRegion(image, region)).toEqual({
			height: 257.325,
			width: 334.8,
			x: 0,
			y: 0
		});
	});
});
