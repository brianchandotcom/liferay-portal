/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

function handleSearchBar() {
	Liferay.on('search-bar-toggled', ({active}) => {
		document.querySelectorAll('.js-toggle-search').forEach((element) => {
			element.classList.toggle('is-active', active);
		});

		document
			.getElementById('minium')
			.classList.toggle('has-search', active);
	});
}

Liferay.on('allPortletsReady', () => {
	handleSearchBar();

	const jsScrollArea = document.querySelector('.js-scroll-area');
	const miniumTop = document.querySelector('[name=minium-top]');

	function sign(x) {
		return (x > 0) - (x < 0) || +x;
	}

	if (jsScrollArea && miniumTop) {
		new IntersectionObserver(
			(entries) => {
				if (document.getElementById('minium')) {
					document
						.getElementById('minium')
						.classList.toggle(
							'is-scrolled',
							!entries[0].isIntersecting
						);
				}
			},
			{
				rootMargin: '0px',
				threshold: 1.0,
			}
		).observe(miniumTop);
	}

	let lastKnownScrollPosition = 0;
	let lastKnownScrollOffset = 0;
	let ticking = false;
	const scrollThreshold = 100;
	const myMap = new Map();

	myMap.set(-1, 'up');
	myMap.set(1, 'down');

	const miniumWrapper = document.getElementById('minium');

	window.addEventListener(
		'scroll',
		() => {
			const offset = window.scrollY - lastKnownScrollPosition;
			lastKnownScrollPosition = window.scrollY;
			lastKnownScrollOffset =
				sign(offset) === sign(lastKnownScrollOffset)
					? lastKnownScrollOffset + offset
					: offset;

			if (!ticking) {
				window.requestAnimationFrame(() => {
					if (Math.abs(lastKnownScrollOffset) > scrollThreshold) {
						miniumWrapper.classList.add(
							'is-scrolling-' +
								myMap.get(sign(lastKnownScrollOffset))
						);
						miniumWrapper.classList.remove(
							'is-scrolling-' +
								myMap.get(-1 * sign(lastKnownScrollOffset))
						);
					}
					ticking = false;
				});
				ticking = true;
			}
		},
		false
	);
});
