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
