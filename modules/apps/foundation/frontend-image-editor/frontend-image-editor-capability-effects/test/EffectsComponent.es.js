'use strict';

import EffectsComponent from '../src/main/resources/META-INF/resources/EffectsComponent.es';
import { CancellablePromise } from '../node_modules/metal-promise/src/promise/Promise';

describe('EffectsComponent', () => {
	let baseline, ailis, amber, atari, aureus, chroma, elysium, expanse, flatfoot, glimmer, idyll, nucleus, orchid, paella, pyrexia, rouge, tripel, umbra;

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
		let assets = [
			getImageData('baseline.png'),
			getImageData('ailis.png'),
			getImageData('amber.png'),
			getImageData('atari.png'),
			getImageData('aureus.png'),
			getImageData('chroma.png'),
			getImageData('elysium.png'),
			getImageData('expanse.png'),
			getImageData('flatfoot.png'),
			getImageData('glimmer.png'),
			getImageData('idyll.png'),
			getImageData('nucleus.png'),
			getImageData('orchid.png'),
			getImageData('paella.png'),
			getImageData('pyrexia.png'),
			getImageData('rouge.png'),
			getImageData('tripel.png'),
			getImageData('umbra.png')
		];

		CancellablePromise.all(assets)
			.then(values => {
				[baseline, ailis, amber, atari, aureus, chroma, elysium, expanse, flatfoot, glimmer, idyll, nucleus, orchid, paella, pyrexia, rouge, tripel, umbra] = values;
				done();
			});
	});

	describe('Available Effects', () => {
		let assertFilter = function(methodFn, filterName, expectedResult, done) {
			let component = new EffectsComponent({
				modulePath: '/base/src/main/resources/META-INF/resources'
			}, false);

			component.cache_ = {};

			component[methodFn](baseline, filterName)
				.then((result) => {
					resemble(result).compareTo(expectedResult).onComplete((comparison) => {
						assert.isBelow(comparison.rawMisMatchPercentage, 1, 'Image difference for ' + filterName + ' should be below 1%');

						done();
					});
				});
		};

		let assertFilterPreview = function(filterName, expectedResult, done) {
			assertFilter('preview', filterName, expectedResult, done);
		};

		let assertFilterProcess = function(filterName, expectedResult, done) {
			assertFilter('process', filterName, expectedResult, done);
		}

		it('should apply the ailis filter', (done) => {
			assertFilterPreview('ailis', ailis, done);
			assertFilterProcess('ailis', ailis, done);
		});

		it('should apply the amber filter', (done) => {
			assertFilterPreview('amber', amber, done);
			assertFilterProcess('amber', amber, done);
		});

		it('should apply the atari filter', (done) => {
			assertFilterPreview('atari', atari, done);
			assertFilterProcess('atari', atari, done);
		});

		it('should apply the aureus filter', (done) => {
			assertFilterPreview('aureus', aureus, done);
			assertFilterProcess('aureus', aureus, done);
		});

		it('should apply the chroma filter', (done) => {
			assertFilterPreview('chroma', chroma, done);
			assertFilterProcess('chroma', chroma, done);
		});

		it('should apply the elysium filter', (done) => {
			assertFilterPreview('elysium', elysium, done);
			assertFilterProcess('elysium', elysium, done);
		});

		it('should apply the expanse filter', (done) => {
			assertFilterPreview('expanse', expanse, done);
			assertFilterProcess('expanse', expanse, done);
		});

		it('should apply the flatfoot filter', (done) => {
			assertFilterPreview('flatfoot', flatfoot, done);
			assertFilterProcess('flatfoot', flatfoot, done);
		});

		it('should apply the glimmer filter', (done) => {
			assertFilterPreview('glimmer', glimmer, done);
			assertFilterProcess('glimmer', glimmer, done);
		});

		it('should apply the idyll filter', (done) => {
			assertFilterPreview('idyll', idyll, done);
			assertFilterProcess('idyll', idyll, done);
		});

		it('should apply the nucleus filter', (done) => {
			assertFilterPreview('nucleus', nucleus, done);
			assertFilterProcess('nucleus', nucleus, done);
		});

		it('should apply the orchid filter', (done) => {
			assertFilterPreview('orchid', orchid, done);
			assertFilterProcess('orchid', orchid, done);
		});

		it('should apply the paella filter', (done) => {
			assertFilterPreview('paella', paella, done);
			assertFilterProcess('paella', paella, done);
		});

		it('should apply the pyrexia filter', (done) => {
			assertFilterPreview('pyrexia', pyrexia, done);
			assertFilterProcess('pyrexia', pyrexia, done);
		});

		it('should apply the rouge filter', (done) => {
			assertFilterPreview('rouge', rouge, done);
			assertFilterProcess('rouge', rouge, done);
		});

		it('should apply the tripel filter', (done) => {
			assertFilterPreview('tripel', tripel, done);
			assertFilterProcess('tripel', tripel, done);
		});

		it('should apply the umbra filter', (done) => {
			assertFilterPreview('umbra', umbra, done);
			assertFilterProcess('umbra', umbra, done);
		});
	});
});