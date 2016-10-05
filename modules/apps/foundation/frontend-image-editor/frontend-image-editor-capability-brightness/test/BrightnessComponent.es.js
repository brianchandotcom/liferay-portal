'use strict';

import BrightnessComponent from '../src/main/resources/META-INF/resources/BrighntessComponent.es';
import { CancellablePromise } from '../node_modules/metal-promise/src/promise/Promise';

describe('BrightnessComponent', () => {
	let baseline, expected;

	let getImageData = function(image) {
		let imageSrc = '/base/test/assets/' + image;

		return new CancellablePromise((resolve, reject) => {
			let image = new Image();

			image.onerror = (event) => { throw new Error('Could not load image ' + imageSrc); };
			image.onload = (event) => {
				let bufferCanvas = document.createElement('canvas');
				let bufferContext = bufferCanvas.getContext('2d');

				let height = image.height;
				let width = image.width;

				bufferCanvas.width = width;
				bufferCanvas.height = height;

				bufferContext.drawImage(image, 0, 0, width, height);

				resolve(bufferContext.getImageData(0, 0, width, height));
			};

			image.src = imageSrc;
		});
	};

	before(done => {
		let assets = [getImageData('baseline.png'), getImageData('brightness_75.png')];

		CancellablePromise.all(assets)
			.then(values => {
				[baseline, expected] = values;
				done();
			});
	});

	it('should apply a contrast effect to the image', (done) => {
		let component = new BrightnessComponent({
			modulePath: '/base/src/main/resources/META-INF/resources'
		}, false);

		component.cache_ = {};
		component.components.slider = {
			value: 75
		};

		component.process(baseline)
			.then((result) => {
				resemble(result).compareTo(expected).onComplete((comparison) => {
					assert.isBelow(comparison.rawMisMatchPercentage, 1, 'Image difference should be below 1%');

					done();
				});
			});
	});
});