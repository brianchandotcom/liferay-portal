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

import getElement from './get_element';

export default function inBrowserView(node, win, nodeRegion) {
	let viewable = false;

	node = getElement(node);

	if (node) {
		if (!nodeRegion) {
			nodeRegion = node.getBoundingClientRect();

			nodeRegion = {
				left: nodeRegion.left + window.scrollX,
				top: nodeRegion.top + window.scrollY,
			};

			nodeRegion.bottom = nodeRegion.top + node.offsetHeight;
			nodeRegion.right = nodeRegion.left + node.offsetWidth;
		}

		if (!win) {
			win = window;
		}

		win = getElement(win);

		const winRegion = {};

		winRegion.left = win.scrollX;
		winRegion.right = winRegion.left + win.innerWidth;

		winRegion.top = win.scrollY;
		winRegion.bottom = winRegion.top + win.innerHeight;

		viewable =
			nodeRegion.bottom <= winRegion.bottom &&
			nodeRegion.left >= winRegion.left &&
			nodeRegion.right <= winRegion.right &&
			nodeRegion.top >= winRegion.top;

		if (viewable) {
			const frameEl = win.frameElement;

			if (frameEl) {
				let frameOffset = frameEl.getBoundingClientRect();

				frameOffset = {
					left: frameOffset.left + window.scrollX,
					top: frameOffset.top + window.scrollY,
				};

				const xOffset = frameOffset.left - winRegion.left;

				nodeRegion.left += xOffset;
				nodeRegion.right += xOffset;

				const yOffset = frameOffset.top - winRegion.top;

				nodeRegion.top += yOffset;
				nodeRegion.bottom += yOffset;

				viewable = inBrowserView(node, win.parent, nodeRegion);
			}
		}
	}

	return viewable;
}
