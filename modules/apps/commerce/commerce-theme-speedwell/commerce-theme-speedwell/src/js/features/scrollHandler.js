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

var Speedwell = Speedwell || {features: {}};

Speedwell.features.scroll = (function(w) {
	'use strict';

	const SCROLL_EVENT = 'scroll',
		callbackQueueOnScroll = {};

	function sign(x) {
		return (x > 0) - (x < 0) || +x;
	}

	let lastKnownScrollPosition = 0,
		lastKnownScrollOffset = 0,
		ticking = false;

	const scrollThreshold = 100,
		myMap = new Map();

	myMap.set(-1, 'up');
	myMap.set(1, 'down');

	function handleOnScroll() {
		const offset = w.scrollY - lastKnownScrollPosition;

		lastKnownScrollPosition = w.scrollY;
		lastKnownScrollOffset =
			sign(offset) === sign(lastKnownScrollOffset)
				? lastKnownScrollOffset + offset
				: offset;

		if (!ticking) {
			w.requestAnimationFrame(() => {
				ticking = false;
			});

			ticking = true;
		}

		Object.keys(callbackQueueOnScroll).forEach(callbackName => {
			callbackQueueOnScroll[callbackName](scrollThreshold);
		});
	}

	return {
		initialize() {
			w.addEventListener(SCROLL_EVENT, handleOnScroll, false);
		},

		registerCallback(callback) {
			callbackQueueOnScroll[callback.name] = callback;
		},

		unregisterCallback(callback) {
			delete callbackQueueOnScroll[callback.name];
		}
	};
})(window);
