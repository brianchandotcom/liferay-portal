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

if (window.NodeList && !NodeList.prototype.forEach) {
	NodeList.prototype.forEach = Array.prototype.forEach;
}

AUI().ready(

	/*
	This function gets loaded when all the HTML, not including the portlets, is
	loaded.
	*/

	() => {
		const searchBar = Liferay.component('search-bar');

		if (searchBar) {
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

			// 	document.querySelectorAll(".js-toggle-search").forEach(el => {
			// 		el.classList.toggle("is-active", searchBar.active);
			// });

		}
	}
);

Liferay.Portlet.ready(

	/*
	This function gets loaded after each and every portlet on the page.

	portletId: the current portlet's id
	node: the Alloy Node object of the current portlet
	*/

	(_portletId, _node) => {}
);

Liferay.on(
	'allPortletsReady',

	/*
	This function gets loaded when everything, including the portlets, is on
	the page.
	*/

	() => {
		var jsScrollArea = document.querySelector('.js-scroll-area');
		var miniumTop = document.querySelector('[name=minium-top]');

		function sign(x) {
			return (x > 0) - (x < 0) || +x;
		}

		if ('IntersectionObserver' in window) {
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

						// root: jsScrollArea,

						rootMargin: '0px',
						threshold: 1.0,
					}
				).observe(miniumTop);
			}
		}

		const scrollThreshold = 100;
		let lastKnownScrollPosition = 0;
		let lastKnownScrollOffset = 0;
		let ticking = false;
		var myMap = new Map();
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
	}
);
