export const actionTypes = {
	CHANGE_STEP: 'CHANGE_STEP',
	UPDATE_PROJECT: 'UPDATE_PROJECT',
	UPDATE_USER_ACCOUNT: 'UPDATE_USER_ACCOUNT',
};

const reducer = (state, action) => {
	if (!action) {
		return state;
	}

	switch (action.type) {
		case actionTypes.CHANGE_STEP: {
			return {
				...state,
				step: action.payload,
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
