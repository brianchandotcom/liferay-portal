export const actionTypes = {
	CHANGE_STEP: 'CHANGE_STEP',
	UPDATE_HAS_SUBSCRIPTION_DXP: 'UPDATE_HAS_SUBSCRIPTION_DXP',
	UPDATE_PROJECT: 'UPDATE_PROJECT',
	UPDATE_USER_ACCOUNT: 'UPDATE_USER_ACCOUNT',
};

const reducer = (state, action) => {
	switch (action.type) {
		case actionTypes.CHANGE_STEP: {
			return {
				...state,
				step: action.payload,
			};
		}
		case actionTypes.UPDATE_HAS_SUBSCRIPTION_DXP: {
			return {
				...state,
				hasSubscriptionsDXPCloud: action.payload
			};
		}
		case actionTypes.UPDATE_PROJECT: {
			return {
				...state,
				project: action.payload,
			};
		}
		case actionTypes.UPDATE_USER_ACCOUNT: {
			return {
				...state,
				userAccount: action.payload,
			};
		}
		default: {
			return state;
		}
	}
};

export default reducer;
