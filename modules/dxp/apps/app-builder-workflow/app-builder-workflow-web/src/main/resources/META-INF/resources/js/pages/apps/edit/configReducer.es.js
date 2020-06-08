/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

export const UPDATE_DATA_OBJECT = 'UPDATE_DATA_OBJECT';
export const UPDATE_FORM_VIEW = 'UPDATE_FORM_VIEW';
export const UPDATE_TABLE_VIEW = 'UPDATE_TABLE_VIEW';

export default (state, action) => {
	switch (action.type) {
		case UPDATE_DATA_OBJECT: {
			return {
				...state,
				dataObject: action.dataObject,
			};
		}
		case UPDATE_FORM_VIEW: {
			return {
				...state,
				formView: action.formView,
			};
		}
		case UPDATE_TABLE_VIEW: {
			return {
				...state,
				tableView: action.tableView,
			};
		}
		default: {
			return state;
		}
	}
};
