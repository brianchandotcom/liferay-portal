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

/**
 * Iterates the widgets array recursively to set a given widget 'used' property
 * and returns a new array with the given widget modified
 */
export function setWidgetUsage(widgets, path, usage) {
	if (!path.length) {
		return {
			...widgets,
			used: usage.used,
		};
	}

	const [currentPath, ...restPath] = path;

	if (Array.isArray(widgets)) {
		return widgets.map((widget, index) => {
			if (index === currentPath) {
				return setWidgetUsage(widgets[currentPath], restPath, usage);
			}

			return widget;
		});
	}
	else {
		return {
			...widgets,
			[currentPath]: setWidgetUsage(
				widgets[currentPath],
				restPath,
				usage
			),
		};
	}
}
