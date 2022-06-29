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

import getViewComponent from './getViewComponent';

export const ACTION_UPDATE_ACTIVE_VIEW = 'ACTION_UPDATE_ACTIVE_VIEW';
export const ACTION_UPDATE_VIEW_COMPONENT = 'ACTION_UPDATE_VIEW_COMPONENT';
export const ACTION_UPDATE_VISIBLE_FIELD_NAMES =
	'ACTION_UPDATE_VISIBLE_FIELD_NAMES';

export function updateActiveView(activeViewName) {
	return {
		type: ACTION_UPDATE_ACTIVE_VIEW,
		value: activeViewName,
	};
}

export function updateViewComponent(name, component) {
	return {
		type: ACTION_UPDATE_VIEW_COMPONENT,
		value: {component, name},
	};
}

export function updateVisibleFieldNames(visibleFieldNames) {
	return {
		type: ACTION_UPDATE_VISIBLE_FIELD_NAMES,
		value: visibleFieldNames,
	};
}

export function viewsReducer(state, {type, value}) {
	const {activeView, views} = state;

	if (type === ACTION_UPDATE_ACTIVE_VIEW) {
		const activeView = views.find(({name}) => name === value);

		if (activeView) {
			activeView.component = getViewComponent(value);
		}

		return {
			...state,
			activeView,
		};
	}
	else if (type === ACTION_UPDATE_VIEW_COMPONENT) {
		const {component, name} = value;

		return {
			...state,
			activeView:
				name === activeView?.name
					? {
							...activeView,
							component,
					  }
					: activeView,
			views: views.map((view) =>
				view.name === name
					? {
							...view,
							component,
					  }
					: view
			),
		};
	}
	else if (type === ACTION_UPDATE_VISIBLE_FIELD_NAMES) {
		return {
			...state,
			visibleFieldNames: value,
		};
	}

	return state;
}
