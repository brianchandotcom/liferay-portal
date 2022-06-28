/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

function handleSearchBar() {
	Liferay.componentReady('search-bar')
		.then((searchBar) => {
			searchBar.on('toggled', (status) => {
				document
					.querySelectorAll('.js-toggle-search')
					.forEach((element) => {
						element.classList.toggle('is-active', status);
					});

				document
					.getElementById('minium')
					.classList.toggle('has-search', status);
			});
		})
		.catch((error) => {
			console.error('Search bar not found!', error);
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
