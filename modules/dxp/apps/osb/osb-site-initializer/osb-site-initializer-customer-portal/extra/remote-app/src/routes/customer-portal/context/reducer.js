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

export const actionTypes = {
	UPDATE_PAGE: 'UPDATE_PAGE',
	UPDATE_PROJECT: 'UPDATE_PROJECT',
	UPDATE_QUICK_LINKS: 'UPDATE_QUICK_LINKS',
	UPDATE_SESSION_ID: 'UPDATE_SESSION_ID',
	UPDATE_STRUCTURED_CONTENTS: 'UPDATE_STRUCTURED_CONTENTS',
	UPDATE_SUBSCRIPTION_GROUPS: 'UPDATE_SUBSCRIPTION_GROUPS',
	UPDATE_USER_ACCOUNT: 'UPDATE_USER_ACCOUNT',
};

const reducer = (state, action) => {
	switch (action.type) {
		case actionTypes.UPDATE_USER_ACCOUNT: {
			return {
				...state,
				userAccount: action.payload,
			};
		}
		case actionTypes.UPDATE_PROJECT: {
			return {
				...state,
				project: action.payload,
			};
		}
		case actionTypes.UPDATE_QUICK_LINKS: {
			return {
				...state,
				quickLinks: action.payload,
			};
		}
		case actionTypes.UPDATE_STRUCTURED_CONTENTS: {
			return {
				...state,
				structuredContents: action.payload,
			};
		}
		case actionTypes.UPDATE_SUBSCRIPTION_GROUPS: {
			return {
				...state,
				subscriptionGroups: action.payload,
			};
		}
		case actionTypes.UPDATE_SESSION_ID: {
			return {
				...state,
				sessionId: action.payload,
			};
		}
		case actionTypes.UPDATE_PAGE: {
			return {
				...state,
				page: action.payload,
			};
		}
		default: {
			return state;
		}
	}
};

export default reducer;
