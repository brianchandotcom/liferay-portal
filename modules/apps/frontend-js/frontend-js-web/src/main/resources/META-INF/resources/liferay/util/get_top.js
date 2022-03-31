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

const SIMULATION_DEVICE_IFRAME = 'simulationDeviceIframe';
let _topWindow;

export default function getTop() {
	let topWindow = _topWindow;

	if (!topWindow) {
		let parentWindow = window.parent;

		let parentThemeDisplay;

		while (parentWindow !== window) {
			try {
				if (typeof parentWindow.location.href === 'undefined') {
					break;
				}

				parentThemeDisplay = parentWindow.themeDisplay;
			}
			catch (error) {
				break;
			}

			if (
				!parentThemeDisplay ||
				window.name === SIMULATION_DEVICE_IFRAME
			) {
				break;
			}
			else if (
				!parentThemeDisplay.isStatePopUp() ||
				parentWindow === parentWindow.parent
			) {
				topWindow = parentWindow;

				break;
			}

			parentWindow = parentWindow.parent;
		}

		if (!topWindow) {
			topWindow = window;
		}

		_topWindow = topWindow;
	}

	return topWindow;
}
