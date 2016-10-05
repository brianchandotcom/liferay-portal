'use strict';

import CropComponent from '../src/main/resources/META-INF/resources/CropComponent.es';
import { CancellablePromise } from '../node_modules/metal-promise/src/promise/Promise';

describe('CropComponent', () => {
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
		let assets = [getImageData('baseline.png'), getImageData('crop.png')];

		CancellablePromise.all(assets)
			.then(values => {
				[baseline, expected] = values;
				done();
			});
	});

	it('should crop an image based on the crop area state', (done) => {
		let component = new CropComponent({
			getImageEditorCanvas: () => {
				return {
					offsetHeight: baseline.height,
					offsetLeft: 0,
					offsetTop: 0,
					offsetWidth: baseline.width
				};
			}
		}, false);

		component.components[component.key + 'CropHandles'] = {
			dispose: sinon.spy(),
			element: {
				offsetHeight: 160,
				offsetLeft: 76,
				offsetTop: 25,
				offsetWidth: 145
			}
		};

		component.process(baseline)
			.then((result) => {
				resemble(result).compareTo(expected).onComplete((comparison) => {
					assert.isTrue(comparison.isSameDimensions, 'The cropped image should be 145x160 pixels');
					assert.isBelow(comparison.rawMisMatchPercentage, 1, 'Image difference should be below 1%');

					done();
				});
			});
	});
});