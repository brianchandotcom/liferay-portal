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

import {createContext} from 'react';

export const ADD_DEPLOYMENT = 'ADD_DEPLOYMENT';
export const PRODUCT_MENU = 'productMenu';
export const REMOVE_DEPLOYMENT = 'REMOVE_DEPLOYMENT';
export const SITE_ID_ALL = -1;
export const TOGGLE_SETTINGS_SITE_ID = 'TOGGLE_SETTINGS_SITE_ID';
export const UPDATE_APP = 'UPDATE_APP';
export const UPDATE_DATA_LAYOUT_ID = 'UPDATE_DATA_LAYOUT_ID';
export const UPDATE_DATA_LIST_VIEW_ID = 'UPDATE_DATA_LIST_VIEW_ID';
export const UPDATE_NAME = 'UPDATE_NAME';
export const UPDATE_SETTINGS_SCOPE = 'UPDATE_SETTINGS_SCOPE';

const uppdateAppDeployment = (state, appDeploymentType, appDeployment) => ({
	...state,
	app: {
		...state.app,
		appDeployments: state.app.appDeployments
			.filter(appDeployment => appDeployment.type !== appDeploymentType)
			.concat(appDeployment)
	}
});

const reducer = (state, action) => {
	switch (action.type) {
		case ADD_DEPLOYMENT: {
			let settings = {};

			if (action.deploymentType == PRODUCT_MENU) {
				settings = {
					scope: ['control_panel']
				};
			}

			return {
				...state,
				app: {
					...state.app,
					appDeployments: state.app.appDeployments.concat({
						settings,
						type: action.deploymentType
					})
				}
			};
		}
		case REMOVE_DEPLOYMENT: {
			return {
				...state,
				app: {
					...state.app,
					appDeployments: state.app.appDeployments.filter(
						appDeployment =>
							appDeployment.type !== action.deploymentType
					)
				}
			};
		}
		case TOGGLE_SETTINGS_SITE_ID: {
			const appDeployment = state.app.appDeployments.find(
				appDeployment => appDeployment.type === PRODUCT_MENU
			);

			let {
				settings: {siteIds = []}
			} = appDeployment;

			const {siteId} = action;

			if (siteId === -1) {
				siteIds = siteIds.includes(siteId) ? [] : [siteId];
			}
			else {
				siteIds = siteIds.includes(siteId)
					? siteIds.filter(id => id != siteId)
					: siteIds.concat(siteId);
			}

			const newAppDeployment = {
				...appDeployment,
				settings: {
					...appDeployment.settings,
					siteIds
				}
			};

			return uppdateAppDeployment(state, PRODUCT_MENU, newAppDeployment);
		}
		case UPDATE_APP: {
			return {
				...state,
				app: {
					...state.app,
					...action.app
				}
			};
		}
		case UPDATE_DATA_LAYOUT_ID: {
			return {
				...state,
				app: {
					...state.app,
					dataLayoutId: action.id
				}
			};
		}
		case UPDATE_DATA_LIST_VIEW_ID: {
			return {
				...state,
				app: {
					...state.app,
					dataListViewId: action.id
				}
			};
		}
		case UPDATE_NAME: {
			return {
				...state,
				app: {
					...state.app,
					name: {
						en_US: action.appName
					}
				}
			};
		}
		case UPDATE_SETTINGS_SCOPE: {
			const appDeployment = state.app.appDeployments.find(
				appDeployment => appDeployment.type === PRODUCT_MENU
			);

			const newAppDeployment = {
				...appDeployment,
				settings: {
					...appDeployment.settings,
					scope: action.scope
				}
			};

			return uppdateAppDeployment(state, PRODUCT_MENU, newAppDeployment);
		}
		default: {
			return state;
		}
	}
};

const EditAppContext = createContext();

export {reducer};
export default EditAppContext;
