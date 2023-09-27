/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const registeredPanels = new Map();

export function exposeSidePanel(id, callback) {
	registeredPanels.set(id, callback);
}

export function getOpenedSidePanel() {
	let openedSidePanel = null;

	registeredPanels.forEach((getData, id) => {
		const data = getData();

		if (data.visible) {
			openedSidePanel = {
				id,
				...data,
			};
		}
	});

	return openedSidePanel;
}
