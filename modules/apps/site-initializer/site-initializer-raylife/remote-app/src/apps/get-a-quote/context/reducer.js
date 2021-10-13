import { ActionTypes } from './actions';

export const reducer = (state, action) => {
	switch (action.type) {
		case ActionTypes.SET_SELECTED_STEP:
			return {
				...state,
				selectedStep: action.payload,
			};

		case ActionTypes.SET_SELECTED_TRIGGER:
			return {
				...state,
				selectedTrigger: action.payload,
			};

		case ActionTypes.SET_SELECTED_PRODUCT:
			return {
				...state,
				selectedProduct: action.payload,
			};

		default:
			return state;
	}
};
