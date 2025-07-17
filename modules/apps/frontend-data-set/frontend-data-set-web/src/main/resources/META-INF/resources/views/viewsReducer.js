/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getViewComponent from './getViewComponent';

export const VIEWS_ACTION_TYPES = {
	ADD_OR_UPDATE_CUSTOM_VIEW: 'ADD_OR_UPDATE_CUSTOM_VIEW',
	DELETE_CUSTOM_VIEW: 'DELETE_CUSTOM_VIEW',
	RENAME_ACTIVE_CUSTOM_VIEW: 'RENAME_ACTIVE_CUSTOM_VIEW',
	RESET_TO_DEFAULT_VIEW: 'RESET_TO_DEFAULT_VIEW',
	UPDATE_ACTIVE_CUSTOM_VIEW: 'UPDATE_ACTIVE_CUSTOM_VIEW',
	UPDATE_ACTIVE_VIEW: 'UPDATE_ACTIVE_VIEW',
	UPDATE_FIELD: 'UPDATE_FIELD',
	UPDATE_FILTERS: 'UPDATE_FILTERS',
	UPDATE_PAGINATION_DELTA: 'UPDATE_PAGINATION_DELTA',
	UPDATE_SORTING: 'UPDATE_SORTING',
	UPDATE_VIEW_COMPONENT: 'UPDATE_VIEW_COMPONENT',
	UPDATE_VISIBLE_FIELD_NAMES: 'UPDATE_VISIBLE_FIELD_NAMES',
};

const VIEWS_ACTIONS = {};

VIEWS_ACTIONS[VIEWS_ACTION_TYPES.ADD_OR_UPDATE_CUSTOM_VIEW] = (
	state,
	value
) => {
	const {customViews} = state;

	const {id, viewState} = value;

	return {
		...state,
		activeCustomViewId: id,
		customViews: {
			...customViews,
			[id]: viewState,
		},
		viewUpdated: false,
	};
};

VIEWS_ACTIONS[VIEWS_ACTION_TYPES.DELETE_CUSTOM_VIEW] = (state, value) => {
	const {customViews, defaultView} = state;

	/* eslint-disable-next-line no-unused-vars */
	const {[value.id]: unusedVar, ...remainingCustomViews} = customViews;

	return {
		...state,
		...defaultView,
		activeCustomViewId: null,
		customViews: remainingCustomViews,
		viewUpdated: false,
	};
};

VIEWS_ACTIONS[VIEWS_ACTION_TYPES.RENAME_ACTIVE_CUSTOM_VIEW] = (
	state,
	value
) => {
	const {activeCustomViewId, customViews} = state;

	const customView = customViews[activeCustomViewId];

	customView.customViewLabel = value.label;

	return {
		...state,
		customViews: {
			...customViews,
			[activeCustomViewId]: customView,
		},
	};
};

VIEWS_ACTIONS[VIEWS_ACTION_TYPES.RESET_TO_DEFAULT_VIEW] = (state) => {
	const {defaultView} = state;

	return {
		...state,
		...defaultView,
		activeCustomViewId: null,
		modifiedFields: {},
		viewUpdated: false,
	};
};

VIEWS_ACTIONS[VIEWS_ACTION_TYPES.UPDATE_ACTIVE_CUSTOM_VIEW] = (
	state,
	value
) => {
	const {customViews, defaultView} = state;

	const activeCustomView = customViews[value];

	if (!activeCustomView) {
		return state;
	}

	if (!activeCustomView.activeView) {
		activeCustomView.activeView = defaultView.activeView;
	}

	activeCustomView.activeView.component =
		getViewComponent(activeCustomView.activeView) ??
		getViewComponent(defaultView.activeView);

	return {
		...state,
		...activeCustomView,
		activeCustomViewId: value,
		modifiedFields: {},
		viewUpdated: false,
	};
};

VIEWS_ACTIONS[VIEWS_ACTION_TYPES.UPDATE_ACTIVE_VIEW] = (state, value) => {
	const {views} = state;

	const activeView = views.find(({name}) => name === value);

	if (activeView) {
		activeView.component = getViewComponent(activeView);
	}

	return {
		...state,
		activeView,
		viewUpdated: true,
	};
};

VIEWS_ACTIONS[VIEWS_ACTION_TYPES.UPDATE_FILTERS] = (state, value) => {
	return {
		...state,
		filters: value,
		viewUpdated: true,
	};
};

VIEWS_ACTIONS[VIEWS_ACTION_TYPES.UPDATE_FIELD] = (state, value) => {
	const {modifiedFields} = state;

	const {name} = value;

	const fieldAttributes = modifiedFields[name] ?? {};

	return {
		...state,
		modifiedFields: {
			...modifiedFields,
			[name]: {...fieldAttributes, ...value},
		},
	};
};

VIEWS_ACTIONS[VIEWS_ACTION_TYPES.UPDATE_PAGINATION_DELTA] = (state, value) => {
	return {
		...state,
		paginationDelta: value,
		viewUpdated: true,
	};
};

VIEWS_ACTIONS[VIEWS_ACTION_TYPES.UPDATE_SORTING] = (state, value) => {
	return {
		...state,
		sorts: value,
		viewUpdated: true,
	};
};

VIEWS_ACTIONS[VIEWS_ACTION_TYPES.UPDATE_VIEW_COMPONENT] = (state, value) => {
	const {activeView, views} = state;

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
};

VIEWS_ACTIONS[VIEWS_ACTION_TYPES.UPDATE_VISIBLE_FIELD_NAMES] = (
	state,
	value
) => {
	const {modifiedFields} = state;

	const fieldNames = Object.keys(value);

	const fields = {};

	fieldNames.forEach((fieldName) => {
		const fieldAttributes = modifiedFields[fieldName] ?? {};

		fieldAttributes.visible = value[fieldName];
		fieldAttributes.width = null;

		fields[fieldName] = fieldAttributes;
	});

	return {
		...state,
		modifiedFields: fields,
		viewUpdated: true,
		visibleFieldNames: value,
	};
};

export function viewsReducer(state, {type, value}) {
	if (VIEWS_ACTIONS[type]) {
		return VIEWS_ACTIONS[type](state, value);
	}

	return state;
}
