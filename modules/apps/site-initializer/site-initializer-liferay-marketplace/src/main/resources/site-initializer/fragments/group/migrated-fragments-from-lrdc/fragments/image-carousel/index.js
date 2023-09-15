/* eslint-disable no-var */
/* eslint-disable no-undef */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI().applyConfig({
	groups: {
		glide: {
			async: true,
			base: 'https://unpkg.com/@glidejs/glide@3.6.0/dist/',
			modules: {
				'glide': {
					path: 'glide.min.js',
					requires: ['glide-css'],
				},
				'glide-css': {
					path: 'css/glide.core.min.css',
				},
			},
		},
	},
});

AUI().use('glide', () => {
	if (fragmentElement.id) {
		const calcSlidePosition = function () {
			const offsetMultiplier = orderArr.length - 1;

			for (let i = 0; i < orderArr.length; i++) {
				const item = fragmentElement.querySelector(
					'[data-step="' + orderArr[i] + '"]'
				);

				const leftSpacing = spacerX * (offsetMultiplier - i) + 'px';
				if (item.classList.contains('auto')) {
					const autoLeft = orderArr[i] * -100;
					item.style.left =
						'calc(' + autoLeft + '% + ' + leftSpacing + ')';
				}
				else {
					item.style.left = leftSpacing;
				}
				item.style.top = spacerY * i + 'px';
				item.style.zIndex = offsetMultiplier - i;
			}
		};

		const calcTrackSize = function () {
			const track = fragmentElement.querySelector('.glide__track');

			const offsetMultiplier = orderArr.length - 1;

			track.style.maxWidth =
				'calc(100% - ' + spacerX * offsetMultiplier + 'px)';
			track.style.marginBottom = spacerY * offsetMultiplier + 'px';
			track.style.overflow = 'visible';
		};

		const updateOrder = function (newActive, newPrevious) {
			orderArr = orderArr.filter((event) => {
				return event !== String(newActive);
			});
			orderArr = orderArr.filter((event) => {
				return event !== String(newPrevious);
			});
			orderArr.unshift(String(newPrevious));
			orderArr.unshift(String(newActive));
		};

		var orderArr = (function () {
			const array = [];
			const elements = fragmentElement.querySelectorAll('[data-step]');
			const startStep = fragmentElement
				.querySelector('.glide__slide--active')
				.getAttribute('data-step');

			for (let i = startStep; i >= 0; i--) {
				array.push(String(i));
			}

			for (let j = startStep; j < elements.length; j++) {
				if (j !== startStep) {
					array.push(String(j));
				}
			}

			return array;
		})();

		var spacerX = 17;
		var spacerY = 19;

		calcSlidePosition();
		calcTrackSize();

		const carouselSlideDownSelector =
			'#' + fragmentElement.id + ' .f-image-carousel-slide-down';
		const isEditable = fragmentElement.querySelector('lfr-editable');

		const swipeThreshold = isEditable ? false : 80;

		const glide = new Glide(carouselSlideDownSelector, {
			dragThreshold: false,
			gap: 0,
			startAt: orderArr[0],
			swipeThreshold,
		}).mount();

		const glideSlides = fragmentElement.querySelector('.glide__slides');

		if (glideSlides) {
			glideSlides.addEventListener('click', (event) => {
				if (
					event.target.closest('.glide__slide--active') ||
					event.target.classList.contains('glide__slide--active')
				) {
					glide.go('>');
				}
				else {
					glide.go('<');
				}
			});
		}

		glide.on('run', () => {
			const nextActive = fragmentElement.querySelector(
				'[data-step="' + glide.index + '"]'
			);

			const current = fragmentElement.querySelector(
				'[data-step="' + orderArr[0] + '"]'
			);

			if (nextActive && current) {
				if (nextActive !== current) {
					nextActive.classList.add('f-image-carousel-fade');

					var transitionEndActiveEnd = function () {
						nextActive.removeEventListener(
							'transitionend',
							transitionEndActiveEnd,
							false
						);
						nextActive.classList.remove('f-image-carousel-fade');
					};

					var transitionEndActiveStart = function () {
						nextActive.removeEventListener(
							'transitionend',
							transitionEndActiveStart,
							false
						);
						nextActive.addEventListener(
							'transitionend',
							transitionEndActiveEnd,
							false
						);

						nextActive.style.opacity = 1;
					};

					nextActive.addEventListener(
						'transitionend',
						transitionEndActiveStart,
						false
					);

					nextActive.style.opacity = 0;

					setTimeout(() => {
						updateOrder(glide.index, orderArr[0]);

						calcSlidePosition();
					}, 250);
				}
			}
		});
	}
});
