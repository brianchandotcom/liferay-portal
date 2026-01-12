/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {deepClone} from 'frontend-js-web';

import {IView} from '../utils/types';
import {ISnapshot} from './ViewsContext';
import getViewComponent from './getViewComponent';

export enum EViewsActionTypes {
	ADD_OR_UPDATE_SNAPSHOT = 'ADD_OR_UPDATE_SNAPSHOT',
	BATCH_UPDATE = 'BATCH_UPDATE',
	DELETE_SNAPSHOT = 'DELETE_SNAPSHOT',
	NOOP = 'NOOP',
	RENAME_ACTIVE_SNAPSHOT = 'RENAME_ACTIVE_SNAPSHOT',
	RESET_TO_DEFAULT_SNAPSHOT = 'RESET_TO_DEFAULT_SNAPSHOT',
	UPDATE_ACTIVE_SNAPSHOT = 'UPDATE_ACTIVE_SNAPSHOT',
	UPDATE_ACTIVE_VIEW = 'UPDATE_ACTIVE_VIEW',
	UPDATE_FIELD = 'UPDATE_FIELD',
	UPDATE_PAGE_NUMBER = 'UPDATE_PAGE_NUMBER',
	UPDATE_PAGINATION_DELTA = 'UPDATE_PAGINATION_DELTA',
	UPDATE_SEARCH_PARAM = 'UPDATE_SEARCH_PARAM',
	UPDATE_SNAPSHOT_UPDATED = 'UPDATE_SNAPSHOT_UPDATED',
	UPDATE_SORTING = 'UPDATE_SORTING',
	UPDATE_VIEW_COMPONENT = 'UPDATE_VIEW_COMPONENT',
	UPDATE_VISIBLE_FIELD_NAMES = 'UPDATE_VISIBLE_FIELD_NAMES',
}

type TViewsActions = {
	[K in EViewsActionTypes]: (state: any, value: any) => object;
};

const viewsActions: TViewsActions = {
	[EViewsActionTypes.ADD_OR_UPDATE_SNAPSHOT]: (state, value) => {
		const {snapshots} = state;

		const {configuration, erc} = value;

		const existentSnapshot = snapshots.find(
			(snapshot: ISnapshot) => snapshot.erc === erc
		);

		let updatedSnapshots;

		if (!existentSnapshot) {
			updatedSnapshots = snapshots.concat([value]);
		}
		else {
			updatedSnapshots = snapshots.map((snapshot: ISnapshot) => {
				if (snapshot.erc === erc) {
					snapshot.configuration = configuration;
				}

				return snapshot;
			});
		}

		return {
			...state,
			activeSnapshotERC: erc,
			snapshotUpdated: false,
			snapshots: updatedSnapshots,
		};
	},
	[EViewsActionTypes.BATCH_UPDATE]: (state, stateUpdates) => {
		if (!Array.isArray(stateUpdates) || !stateUpdates.length) {
			return state;
		}

		return stateUpdates.reduce((acc, current) => {
			const {type, value}: {type: keyof typeof viewsActions; value: any} =
				current;
			if (!viewsActions[type]) {
				return acc;
			}

			return viewsActions[type](acc, value);
		}, state);
	},
	[EViewsActionTypes.DELETE_SNAPSHOT]: (state, value) => {
		const {defaultSnapshot, snapshots} = state;

		const remainingSnapshots = snapshots.filter(
			(snapshot: ISnapshot) => snapshot.erc !== value.snapshotERC
		);

		return {
			...state,
			...defaultSnapshot,
			activeSnapshotERC: null,
			snapshotUpdated: false,
			snapshots: remainingSnapshots,
		};
	},
	[EViewsActionTypes.NOOP]: (state) => state,
	[EViewsActionTypes.RENAME_ACTIVE_SNAPSHOT]: (state, value) => {
		const {activeSnapshotERC, snapshots} = state;

		const updatedSnapshots = snapshots.map((snapshot: ISnapshot) => {
			if (snapshot.erc === activeSnapshotERC) {
				snapshot.label = value.label;
			}

			return snapshot;
		});

		return {
			...state,
			snapshotUpdated: false,
			snapshots: [...updatedSnapshots],
		};
	},
	[EViewsActionTypes.RESET_TO_DEFAULT_SNAPSHOT]: (state) => {
		const {defaultSnapshot} = state;

		return {
			...state,
			...defaultSnapshot,
			activeSnapshotERC: null,
			snapshotUpdated: false,
		};
	},
	[EViewsActionTypes.UPDATE_ACTIVE_SNAPSHOT]: (state, value) => {
		const {defaultSnapshot} = state;

		if (!value.configuration.activeView) {
			value.configuration.activeView = defaultSnapshot.activeView;
		}

		value.configuration.activeView.component =
			getViewComponent(value.configuration.activeView) ??
			getViewComponent(defaultSnapshot.activeView);

		return {
			...state,
			...value.configuration,
			activeSnapshotERC: value.erc,
			snapshotUpdated: false,
		};
	},
	[EViewsActionTypes.UPDATE_ACTIVE_VIEW]: (state, value) => {
		const {views} = state;

		const activeView = views.find(
			({name}: {name: string}) => name === value
		);

		if (activeView) {
			activeView.component = getViewComponent(activeView);
		}

		return {
			...state,
			activeView,
			snapshotUpdated: true,
		};
	},
	[EViewsActionTypes.UPDATE_FIELD]: (state, value) => {
		const {defaultSnapshot, modifiedFields} = state;

		const {name} = value;

		const fieldAttributes = modifiedFields[name] ?? {};

		if (!defaultSnapshot.modifiedFields[name]) {
			defaultSnapshot.modifiedFields[name] = {
				...fieldAttributes,
				...value,
			};
		}

		return {
			...state,
			modifiedFields: {
				...modifiedFields,
				[name]: {...fieldAttributes, ...value},
			},
		};
	},
	[EViewsActionTypes.UPDATE_PAGE_NUMBER]: (state, value) => {
		return {
			...state,
			pageNumber: value,
		};
	},
	[EViewsActionTypes.UPDATE_PAGINATION_DELTA]: (state, value) => {
		return {
			...state,
			paginationDelta: value,
			snapshotUpdated: true,
		};
	},
	[EViewsActionTypes.UPDATE_SEARCH_PARAM]: (state, value) => {
		return {
			...state,
			searchParam: value,
		};
	},
	[EViewsActionTypes.UPDATE_SNAPSHOT_UPDATED]: (state, value: boolean) => {
		return {
			...state,
			snapshotUpdated: value,
		};
	},
	[EViewsActionTypes.UPDATE_SORTING]: (state, value) => {
		return {
			...state,
			snapshotUpdated: true,
			sorts: value,
		};
	},
	[EViewsActionTypes.UPDATE_VIEW_COMPONENT]: (state, value) => {
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
			views: views.map((view: IView) =>
				view.name === name
					? {
							...view,
							component,
						}
					: view
			),
		};
	},
	[EViewsActionTypes.UPDATE_VISIBLE_FIELD_NAMES]: (state, value) => {
		const {modifiedFields} = state;

		const fieldNames = Object.keys(value);

		const fields: {[key: string]: any} = {};

		fieldNames.forEach((fieldName) => {
			const fieldAttributes = modifiedFields[fieldName] ?? {};

			fieldAttributes.visible = value[fieldName];
			fieldAttributes.width = null;

			fields[fieldName] = fieldAttributes;
		});

		return {
			...state,
			modifiedFields: fields,
			snapshotUpdated: true,
			visibleFieldNames: value,
		};
	},
};

const viewsReducer = (
	state: any,
	{type, value}: {type: keyof TViewsActions; value: any}
) => {
	if (viewsActions[type]) {
		return viewsActions[type](state, value);
	}

	return deepClone(state);
};

export default viewsReducer;
