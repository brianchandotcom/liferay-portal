'use strict';

import RotateComponent from '../src/main/resources/META-INF/resources/RotateComponent.es';
import { CancellablePromise } from '../node_modules/metal-promise/src/promise/Promise';

describe('RotateComponent', () => {
	let baseline, rotateLeft90, rotateRight90, rotate180;

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
		let assets = [getImageData('baseline.png'), getImageData('rotate_left_90.png'), getImageData('rotate_right_90.png'), getImageData('rotate_180.png')];

		CancellablePromise.all(assets)
			.then(values => {
				[baseline, rotateLeft90, rotateRight90, rotate180] = values;
				done();
			});
	});

	it('should rotate an image 90º to the left', (done) => {
		let component = new RotateComponent({
			requestImageEditorPreview: () => {}
		});

		component.rotateLeft();

		component.process(baseline)
			.then((result) => {
				resemble(result).compareTo(rotateLeft90).onComplete((comparison) => {
					assert.isTrue(comparison.isSameDimensions, 'The rotated image should be 305x300 pixels');
					assert.isBelow(comparison.rawMisMatchPercentage, 1, 'Image difference should be below 1%');

					done();
				});
			});
	});

	it('should rotate an image 90º to the right', (done) => {
		let component = new RotateComponent({
			requestImageEditorPreview: () => {}
		});

		component.rotateRight();

		component.process(baseline)
			.then((result) => {
				resemble(result).compareTo(rotateRight90).onComplete((comparison) => {
					assert.isTrue(comparison.isSameDimensions, 'The rotated image should be 305x300 pixels');
					assert.isBelow(comparison.rawMisMatchPercentage, 1, 'Image difference should be below 1%');

					done();
				});
			});
	});

	it('should rotate an image 180º to the left', (done) => {
		let component = new RotateComponent({
			requestImageEditorPreview: () => {}
		});

		component.rotateLeft();
		component.rotateLeft();

		component.process(baseline)
			.then((result) => {
				resemble(result).compareTo(rotate180).onComplete((comparison) => {
					assert.isTrue(comparison.isSameDimensions, 'The rotated image should be 305x300 pixels');
					assert.isBelow(comparison.rawMisMatchPercentage, 1, 'Image difference should be below 1%');

					done();
				});
			});
	});

	it('should rotate an image 180º to the right', (done) => {
		let component = new RotateComponent({
			requestImageEditorPreview: () => {}
		});

		component.rotateRight();
		component.rotateRight();

		component.process(baseline)
			.then((result) => {
				resemble(result).compareTo(rotate180).onComplete((comparison) => {
					assert.isTrue(comparison.isSameDimensions, 'The rotated image should be 305x300 pixels');
					assert.isBelow(comparison.rawMisMatchPercentage, 1, 'Image difference should be below 1%');

					done();
				});
			});
	});
});