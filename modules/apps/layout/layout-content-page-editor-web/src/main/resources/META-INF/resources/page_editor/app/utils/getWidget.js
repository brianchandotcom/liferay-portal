/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function getWidget(widgets, portletId) {
	let widget = null;

	for (let i = 0; i < widgets?.length; i++) {
		const {categories = [], portlets = []} = widgets[i];
		const categoryPortlet = portlets.find(
			(portlet) => portlet.portletId === portletId
		);
		const subCategoryPortlet = getWidget(categories, portletId);

		widget = subCategoryPortlet || categoryPortlet;

		if (widget) {
			return widget;
		}
	}

	return widget;
}
