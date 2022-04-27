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

const rectAttrs = ['bottom', 'height', 'left', 'right', 'top', 'width'];

const rectChanged = (a, b) => rectAttrs.some((prop) => a[prop] !== b[prop]);

let rafId;

const run = (node, state) => {
	const newRect = node.getBoundingClientRect();

	if (rectChanged(newRect, state.rect)) {
		state.rect = newRect;

		state.callback(state.rect);
	}

	rafId = window.requestAnimationFrame(() => run(node, state));
};

const observeRect = (node, callback) => {
	run(node, {
		callback,
		hasRectChanged: false,
		rect: {
			height: 0,
			width: 0,
			x: 0,
			y: 0,
		},
	});

	return () => {
		cancelAnimationFrame(rafId);
	};
};

export default observeRect;
