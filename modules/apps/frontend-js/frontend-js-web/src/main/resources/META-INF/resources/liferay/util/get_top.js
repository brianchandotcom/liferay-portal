/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
