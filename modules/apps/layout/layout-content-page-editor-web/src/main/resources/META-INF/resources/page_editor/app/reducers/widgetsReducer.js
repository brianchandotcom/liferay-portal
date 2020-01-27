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

import {TYPES} from '../actions/index';

/**
 * Get widget path from the widgets tree by portletId
 */
function getWidgetPath(widgets, portletId, path = []) {
	let widgetPath = null;

	for (
		let categoryIndex = 0;
		categoryIndex < widgets.length;
		categoryIndex += 1
	) {
		const {categories = [], portlets = []} = widgets[categoryIndex];

		const categoryPortletIndex = portlets.findIndex(
			_portlet => _portlet.portletId === portletId
		);

		const subCategoryPortletPath = getWidgetPath(categories, portletId, [
			...path,
			categoryIndex.toString(),
			'categories'
		]);

		if (categoryPortletIndex !== -1) {
			widgetPath = [
				...path,
				categoryIndex,
				'portlets',
				categoryPortletIndex
			];
		}

		if (subCategoryPortletPath) {
			widgetPath = subCategoryPortletPath;
		}
	}

	return widgetPath;
}

/**
 * Iterates the widgets array recursively to set a given widget 'used' property
 * and returns a new array with the given widget modified
 */
function setWidgetUsage(widgets, path, usage) {
	if (!path.length) {
		return {
			...widgets,
			used: usage.used
		};
	}

	const [currentPath, ...restPath] = path;

	if (widgets instanceof Array) {
		return widgets.map((widget, index) => {
			if (index === currentPath) {
				return setWidgetUsage(widgets[currentPath], restPath, usage);
			}

			return widget;
		});
	} else {
		return {
			...widgets,
			[currentPath]: setWidgetUsage(widgets[currentPath], restPath, usage)
		};
	}
}

export default function widgetsReducer(widgets, action) {
	switch (action.type) {
		case TYPES.ADD_FRAGMENT_ENTRY_LINK: {
			if (
				action.fragmentEntryLink.editableValues.portletId &&
				!action.fragmentEntryLink.editableValues.instanceable
			) {
				const widgetPath = getWidgetPath(
					widgets,
					action.fragmentEntryLink.editableValues.portletId
				);

				return setWidgetUsage(widgets, widgetPath, {used: true});
			}

			return widgets;
		}

		case TYPES.DELETE_WIDGET: {
			if (
				action.fragmentEntryLink.editableValues.portletId &&
				!action.fragmentEntryLink.editableValues.instanceable
			) {
				const widgetPath = getWidgetPath(
					widgets,
					action.fragmentEntryLink.editableValues.portletId
				);

				return setWidgetUsage(widgets, widgetPath, {used: false});
			}

			return widgets;
		}

		default:
			return widgets;
	}
}
