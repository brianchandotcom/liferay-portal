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

import {
	ADD_FRAGMENT_ENTRY_LINKS,
	ADD_ITEM,
	DELETE_ITEM,
	DELETE_WIDGETS,
} from '../actions/types';
import {getWidgetPath} from '../utils/getWidgetPath';
import {setWidgetUsage} from '../utils/setWidgetUsage';

export default function widgetsReducer(widgets, action) {
	switch (action.type) {
		case ADD_FRAGMENT_ENTRY_LINKS: {
			const fragmentEntryLinks = action.fragmentEntryLinks || [];

			let nextWidgets = widgets;

			fragmentEntryLinks.forEach((fragmentEntryLink) => {
				if (
					fragmentEntryLink.editableValues &&
					fragmentEntryLink.editableValues.portletId &&
					!fragmentEntryLink.editableValues.instanceable
				) {
					const widgetPath = getWidgetPath(
						widgets,
						fragmentEntryLink.editableValues.portletId
					);

					nextWidgets = setWidgetUsage(nextWidgets, widgetPath, {
						used: true,
					});
				}
			});

			return nextWidgets;
		}
		case ADD_ITEM: {
			const portletIds = action.portletIds || [];
			let nextWidgets = widgets;

			portletIds.forEach((portletId) => {
				const widgetPath = getWidgetPath(widgets, portletId);

				nextWidgets = setWidgetUsage(nextWidgets, widgetPath, {
					used: true,
				});
			});

			return nextWidgets;
		}

		case DELETE_ITEM: {
			const portletIds = action.portletIds || [];
			let nextWidgets = widgets;

			portletIds.forEach((portletId) => {
				const widgetPath = getWidgetPath(widgets, portletId);

				nextWidgets = setWidgetUsage(nextWidgets, widgetPath, {
					used: false,
				});
			});

			return nextWidgets;
		}

		case DELETE_WIDGETS: {
			let nextWidgets = widgets;

			action.fragmentEntryLinks.forEach((fragmentEntryLink) => {
				if (
					fragmentEntryLink.editableValues &&
					fragmentEntryLink.editableValues.portletId &&
					!fragmentEntryLink.editableValues.instanceable
				) {
					const widgetPath = getWidgetPath(
						nextWidgets,
						fragmentEntryLink.editableValues.portletId
					);

					nextWidgets = setWidgetUsage(nextWidgets, widgetPath, {
						used: false,
					});
				}
			});

			return nextWidgets;
		}

		default:
			return widgets;
	}
}
