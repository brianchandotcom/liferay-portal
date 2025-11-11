/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

Liferay.component(
	'SpeedwellScrollHandler',
	(function () {
		const SCROLL_EVENT = 'scroll';
		const callbackQueueOnScroll = {};

		function sign(x) {
			return (x > 0) - (x < 0) || +x;
		}

		let lastKnownScrollPosition = 0;
		let lastKnownScrollOffset = 0;
		let ticking = false;

		const scrollThreshold = 100;
		const myMap = new Map();

		myMap.set(-1, 'up');
		myMap.set(1, 'down');

		function handleOnScroll() {
			const offset = window.scrollY - lastKnownScrollPosition;

			lastKnownScrollPosition = window.scrollY;
			lastKnownScrollOffset =
				sign(offset) === sign(lastKnownScrollOffset)
					? lastKnownScrollOffset + offset
					: offset;

			if (!ticking) {
				window.requestAnimationFrame(() => {
					ticking = false;
				});

				ticking = true;
			}

			Object.keys(callbackQueueOnScroll).forEach((callbackName) => {
				callbackQueueOnScroll[callbackName](scrollThreshold);
			});
		}

		window.addEventListener(SCROLL_EVENT, handleOnScroll, false);

		return {
			registerCallback(callback) {
				callbackQueueOnScroll[callback.name] = callback;
			},

			unregisterCallback(callback) {
				delete callbackQueueOnScroll[callback.name];
			},
		};
	})(),
	{destroyOnNavigate: true}
);
