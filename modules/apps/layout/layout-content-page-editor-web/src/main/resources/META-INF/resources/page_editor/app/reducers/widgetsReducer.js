/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TOGGLE_WIDGET_HIGHLIGHTED, UPDATE_WIDGETS} from '../actions/types';

const HIGHLIGHTED_CATEGORY_ID = 'root--category-highlighted';

const DEFAULT_HIGHLIGHTED_CATEGORY = {
	categories: [],
	path: HIGHLIGHTED_CATEGORY_ID,
	portlets: [],
	title: Liferay.Language.get('highlighted'),
};

function normalizePortlets(portlets, portletIds) {
	return portlets.map((portlet) => {
		const normalizedPortlet = {
			...portlet,
			used: portletIds.has(portlet.portletId),
		};

		if (portlet.portletItems?.length) {
			normalizedPortlet.portletItems = normalizePortlets(
				portlet.portletItems,
				portletIds
			);
		}

		return normalizedPortlet;
	});
}

function normalizeCategories(categories, fragmentEntryLinks) {
	if (!categories) {
		return null;
	}

	return categories.map((category) => {
		const portletIds = new Set(
			Array.from(fragmentEntryLinks).map(({portletId}) => portletId)
		);

		const normalizedCategory = {
			...category,
			portlets: normalizePortlets(category.portlets, portletIds),
		};

		if (category.categories?.length) {
			normalizedCategory.categories = normalizeCategories(
				category.categories,
				portletIds
			);
		}

		return normalizedCategory;
	});
}

export default function widgetsReducer(widgets = null, action) {
	switch (action.type) {
		case TOGGLE_WIDGET_HIGHLIGHTED: {
			const {highlighted, highlightedPortlets, portletId} = action;

			const nextWidgets = widgets.reduce((categories, category) => {
				if (category.path !== HIGHLIGHTED_CATEGORY_ID) {
					categories.push({
						...category,
						portlets: category.portlets.map((widget) =>
							widget.portletId === portletId
								? {...widget, highlighted}
								: widget
						),
					});
				}

				return categories;
			}, []);

			if (highlightedPortlets.length) {
				nextWidgets.unshift({
					...DEFAULT_HIGHLIGHTED_CATEGORY,
					portlets: highlightedPortlets,
				});
			}

			return nextWidgets;
		}

		case UPDATE_WIDGETS: {
			return normalizeCategories(
				action.widgets || widgets,
				action.fragmentEntryLinks
			);
		}

		default:
			return widgets;
	}
}
