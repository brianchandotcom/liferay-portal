/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import toggleWidgetHighlighted from '../actions/toggleWidgetHighlighted';
import {TOGGLE_WIDGET_HIGHLIGHTED, UPDATE_WIDGETS} from '../actions/types';
import updateWidgets, {Widget, WidgetSet} from '../actions/updateWidgets';
import {HIGHLIGHTED_CATEGORY_ID} from '../config/constants/highlightedCategoryId';

const DEFAULT_HIGHLIGHTED_CATEGORY = {
	categories: [],
	path: HIGHLIGHTED_CATEGORY_ID,
	portlets: [],
	title: Liferay.Language.get('highlighted'),
} as const;

function markUsedPortlets(portlets: Widget[], usedPortletsIds: Set<string>) {
	return portlets.map<Widget>((portlet) => {
		const normalizedPortlet = {
			...portlet,
			used: usedPortletsIds.has(portlet.portletId),
		};

		if (portlet.portletItems?.length) {
			normalizedPortlet.portletItems = markUsedPortlets(
				portlet.portletItems,
				usedPortletsIds
			);
		}

		return normalizedPortlet;
	});
}

function markUsedCategories(
	categories: WidgetSet[] | null,
	usedPortletsIds: Set<string>
) {
	if (!categories) {
		return null;
	}

	return categories.map((category) => {
		const updatedCategory: WidgetSet = {
			...category,
			portlets: markUsedPortlets(category.portlets, usedPortletsIds),
		};

		if (category.categories?.length) {
			updatedCategory.categories = markUsedCategories(
				category.categories,
				usedPortletsIds
			);
		}

		return updatedCategory;
	});
}

export default function widgetsReducer(
	widgets: WidgetSet[] | null = null,
	action:
		| ReturnType<typeof toggleWidgetHighlighted>
		| ReturnType<typeof updateWidgets>
) {
	switch (action.type) {
		case TOGGLE_WIDGET_HIGHLIGHTED: {
			const {highlighted, highlightedPortlets, portletId} = action;

			const nextWidgets = widgets?.reduce(
				(categories: WidgetSet[], category) => {
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
				},
				[]
			);

			if (nextWidgets && highlightedPortlets.length) {
				nextWidgets.unshift({
					...DEFAULT_HIGHLIGHTED_CATEGORY,
					categories: [],
					portlets: highlightedPortlets,
				});
			}

			return nextWidgets;
		}

		case UPDATE_WIDGETS: {
			return markUsedCategories(
				action.widgets || widgets,
				new Set(
					action.fragmentEntryLinks
						.map(({portletId}) => portletId!)
						.filter((portletId) => portletId)
				)
			);
		}

		default:
			return widgets;
	}
}
