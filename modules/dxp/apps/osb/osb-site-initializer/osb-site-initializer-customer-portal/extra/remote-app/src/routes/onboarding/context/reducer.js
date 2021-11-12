export const actionTypes = {
	CHANGE_STEP: 'CHANGE_STEP',
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
		default: {
			return state;
		}
	}
};

export default reducer;
