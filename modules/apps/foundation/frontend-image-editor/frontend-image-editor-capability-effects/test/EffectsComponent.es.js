'use strict';

import EffectsComponent from '../src/main/resources/META-INF/resources/EffectsComponent.es';
import { CancellablePromise } from '../node_modules/metal-promise/src/promise/Promise';

const EFFECT_MISSMATCH_THRESHOLD = 3;

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
		let assertEffect = function(effectName, expectedResult, done) {
			let component = new EffectsComponent({
				modulePath: '/base/src/main/resources/META-INF/resources'
			}, false);

			component.cache_ = {};

			component.process(baseline, effectName)
				.then((result) => {
					resemble(result).compareTo(expectedResult).onComplete((comparison) => {
						assert.isBelow(comparison.rawMisMatchPercentage, EFFECT_MISSMATCH_THRESHOLD, 'Image difference for ' + effectName + ' should be below ' + EFFECT_MISSMATCH_THRESHOLD + '%');

						done();
					});
				});
		};

		it('should apply the ailis effect', (done) => {
			assertEffect('ailis', ailis, done);
		});

		it('should apply the amber effect', (done) => {
			assertEffect('amber', amber, done);
		});

		it('should apply the atari effect', (done) => {
			assertEffect('atari', atari, done);
		});

		it('should apply the aureus effect', (done) => {
			assertEffect('aureus', aureus, done);
		});

		it('should apply the chroma effect', (done) => {
			assertEffect('chroma', chroma, done);
		});

		it('should apply the elysium effect', (done) => {
			assertEffect('elysium', elysium, done);
		});

		it('should apply the expanse effect', (done) => {
			assertEffect('expanse', expanse, done);
		});

		it('should apply the flatfoot effect', (done) => {
			assertEffect('flatfoot', flatfoot, done);
		});

		it('should apply the glimmer effect', (done) => {
			assertEffect('glimmer', glimmer, done);
		});

		it('should apply the idyll effect', (done) => {
			assertEffect('idyll', idyll, done);
		});

		it('should apply the nucleus effect', (done) => {
			assertEffect('nucleus', nucleus, done);
		});

		it('should apply the orchid effect', (done) => {
			assertEffect('orchid', orchid, done);
		});

		it('should apply the paella effect', (done) => {
			assertEffect('paella', paella, done);
		});

		it('should apply the pyrexia effect', (done) => {
			assertEffect('pyrexia', pyrexia, done);
		});

		it('should apply the rouge effect', (done) => {
			assertEffect('rouge', rouge, done);
		});

		it('should apply the tripel effect', (done) => {
			assertEffect('tripel', tripel, done);
		});

		it('should apply the umbra effect', (done) => {
			assertEffect('umbra', umbra, done);
		});
	});
});