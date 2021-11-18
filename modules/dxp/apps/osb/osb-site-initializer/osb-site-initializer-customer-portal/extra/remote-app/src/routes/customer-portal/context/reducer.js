export const actionTypes = {
	UPDATE_PROJECT: 'UPDATE_PROJECT',
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
		default: {
			return state;
		}
	}
};

export default reducer;
