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

/**
 * Throttle implementation that fires on the leading and trailing edges.
 * If multiple calls come in during the throttle interval, the last call's
 * arguments and context are used, replacing those of any previously pending
 * calls.
 *
 * @param {!function()} fn
 * @param {number} interval
 * @return {!function()}
 */
export default function throttle(fn, interval) {
	let timeout = null;
	let last;

	return function(...args) {
		const context = this;
		const now = Date.now();

		const schedule = () => {
			timeout = setTimeout(() => {
				timeout = null;
			}, interval);
			last = now;
			fn.apply(context, args);
		};

		if (timeout === null) {
			schedule();
		} else {
			const remaining = Math.max(last + interval - now, 0);
			clearTimeout(timeout);
			timeout = setTimeout(schedule, remaining);
		}
	};
}
