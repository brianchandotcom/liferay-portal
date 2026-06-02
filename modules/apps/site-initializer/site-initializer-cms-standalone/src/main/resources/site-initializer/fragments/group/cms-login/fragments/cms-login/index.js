/* eslint-disable no-undef */

/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	const carousel = fragmentElement.querySelector('[data-cms-carousel]');

	if (!carousel || carousel._cmsLoginInit) {
		return;
	}

	carousel._cmsLoginInit = true;

	const slides = Array.prototype.slice.call(
		carousel.querySelectorAll('.cms-login-cslide')
	);
	const dots = Array.prototype.slice.call(
		carousel.querySelectorAll('.cms-login-cnav__dot')
	);
	const pauseButton = carousel.querySelector('[data-cms-pause]');

	if (slides.length < 2) {
		return;
	}

	let current = 0;
	let paused = false;
	let timer;
	const INTERVAL = 7000;

	function goTo(index) {
		if (index === current) {
			return;
		}

		const previous = current;

		current = (index + slides.length) % slides.length;

		slides[previous].classList.add('is-out');
		slides[previous].classList.remove('is-active');
		dots[previous].classList.remove('is-active');

		setTimeout(() => {
			slides[previous].classList.remove('is-out');
		}, 350);

		slides[current].classList.add('is-active');
		dots[current].classList.add('is-active');
	}

	function startTimer() {
		clearInterval(timer);

		if (!paused) {
			timer = setInterval(() => {
				goTo((current + 1) % slides.length);
			}, INTERVAL);
		}
	}

	function setPaused(value) {
		paused = value;

		if (pauseButton) {
			pauseButton.classList.toggle('is-paused', paused);
			pauseButton.setAttribute(
				'aria-label',
				paused ? 'Resume carousel' : 'Pause carousel'
			);
		}

		if (paused) {
			clearInterval(timer);
		}
		else {
			startTimer();
		}
	}

	dots.forEach((dot) => {
		dot.addEventListener('click', () => {
			goTo(parseInt(dot.getAttribute('data-slide'), 10));
			startTimer();
		});
	});

	if (pauseButton) {
		pauseButton.addEventListener('click', (event) => {
			event.stopPropagation();
			setPaused(!paused);
		});
	}

	startTimer();
})();
