/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Widget, WidgetSet} from '../actions/updateWidgets';

export default function getWidget(
	widgets: WidgetSet[],
	portletId: string
): Widget | null {
	for (const widget of widgets) {
		const {categories, portlets} = widget;

		const categoryPortlet = portlets.find(
			(portlet) => portlet.portletId === portletId
		);

		const subCategoryPortlet = getWidget(categories || [], portletId);

		const result = subCategoryPortlet || categoryPortlet;

		if (result) {
			return result;
		}
	}

	return null;
}
