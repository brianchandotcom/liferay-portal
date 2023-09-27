/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getElement from './get_element';

export default function inBrowserView(node, baseWindow, nodeRegion) {
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

		if (!baseWindow) {
			baseWindow = window;
		}

		baseWindow = getElement(baseWindow);

		const winRegion = {};

		winRegion.left = baseWindow.scrollX;
		winRegion.right = winRegion.left + baseWindow.innerWidth;

		winRegion.top = baseWindow.scrollY;
		winRegion.bottom = winRegion.top + baseWindow.innerHeight;

		viewable =
			nodeRegion.bottom <= winRegion.bottom &&
			nodeRegion.left >= winRegion.left &&
			nodeRegion.right <= winRegion.right &&
			nodeRegion.top >= winRegion.top;

		if (viewable) {
			const frameElement = baseWindow.frameElement;

			if (frameElement) {
				let frameOffset = frameElement.getBoundingClientRect();

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

				viewable = inBrowserView(node, baseWindow.parent, nodeRegion);
			}
		}
	}

	return viewable;
}
