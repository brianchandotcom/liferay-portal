export const actionTypes = {
	UPDATE_USER_ACCOUNT: 'UPDATE_USER_ACCOUNT',
};

const reducer = (state, action) => {
	if (!action) {
		return state;
	}

	switch (action.type) {
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
