/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const actionTypes = {
	UPDATE_PAGE: 'UPDATE_PAGE',
	UPDATE_PROJECT: 'UPDATE_PROJECT',
	UPDATE_QUICK_LINKS: 'UPDATE_QUICK_LINKS',
	UPDATE_QUICK_LINKS_EXPANDED_PANEL: 'UPDATE_QUICK_LINKS_EXPANDED_PANEL',
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
		case actionTypes.UPDATE_QUICK_LINKS_EXPANDED_PANEL: {
			return {
				...state,
				isQuickLinksExpanded: action.payload,
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
