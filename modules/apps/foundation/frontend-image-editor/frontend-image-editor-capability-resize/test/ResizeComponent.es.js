'use strict';

import ResizeComponent from '../src/main/resources/META-INF/resources/ResizeComponent.es';
import { CancellablePromise } from '../node_modules/metal-promise/src/promise/Promise';

describe('ResizeComponent', () => {
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
		let assets = [getImageData('baseline.png'), getImageData('result_200x203.png')];

		CancellablePromise.all(assets)
			.then(values => {
				[baseline, expected] = values;
				done();
			});
	});

	it('should resize an image to the given dimensions', (done) => {
		let component = new ResizeComponent({}, false);

		component.imageWidth = 200;
		component.imageHeight = 203;

		component.process(baseline)
			.then((result) => {
				resemble(result).compareTo(expected).onComplete((comparison) => {
					assert.isTrue(comparison.isSameDimensions, 'The resized image should be 200x203 pixels');
					assert.isBelow(comparison.rawMisMatchPercentage, 1, 'Image difference should be below 1%');

					done();
				});
			});
	});

	it('should maintain aspect ratio when setting the image dimensions by default', (done) => {
		let component = new ResizeComponent({
			getImageEditorImageData: () => CancellablePromise.resolve(baseline),
			key: 'resize'
		});

		// Wait for the `attached` microtask to finish and calculate the image aspect ratio
		setTimeout(() => {
			let inputWidth = component.element.querySelector('#' + component.key + 'Width');
			let inputHeight = component.element.querySelector('#' + component.key + 'Height');

			inputWidth.value = 100;
			simulateEvent(inputWidth, 'input');

			assert.strictEqual(inputHeight.value, '101', 'Height value should have been synced');

			inputHeight.value = 204;
			simulateEvent(inputHeight, 'input');

			assert.strictEqual(inputWidth.value, '200', 'Width value should have been synced');

			done();
		}, 0);
	});

	it('should not sync dimensions if lockProportions is set to false', (done) => {
		let component = new ResizeComponent({
			getImageEditorImageData: () => CancellablePromise.resolve(baseline),
			key: 'resize'
		});

		// Wait for the `attached` microtask to finish and calculate the image aspect ratio
		setTimeout(() => {
			component.lockProportions = false;

			let inputWidth = component.element.querySelector('#' + component.key + 'Width');
			let inputHeight = component.element.querySelector('#' + component.key + 'Height');

			inputWidth.value = 40;
			simulateEvent(inputWidth, 'input');

			inputHeight.value = 600;
			simulateEvent(inputHeight, 'input');

			assert.strictEqual(inputWidth.value, '40', 'Width value should have the custom value');
			assert.strictEqual(inputHeight.value, '600', 'Height value should have custom value');

			done();
		}, 0);
	});

	it('should toggle the lockProportions value when clicking on the lock icon', (done) => {
		let component = new ResizeComponent({
			getImageEditorImageData: () => CancellablePromise.resolve(baseline),
			key: 'resize'
		});

		// Wait for the `attached` microtask to finish and calculate the image aspect ratio
		setTimeout(() => {
			let lockProportionsButton = component.element.querySelector('a.btn');

			simulateEvent(lockProportionsButton, 'click');

			assert.isFalse(component.lockProportions, 'Value of lockProportions should have been toggled');

			simulateEvent(lockProportionsButton, 'click');

			assert.isTrue(component.lockProportions, 'Value of lockProportions should have been toggled');

			done();
		}, 0);
	});
});