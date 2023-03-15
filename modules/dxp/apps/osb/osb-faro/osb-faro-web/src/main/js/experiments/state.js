import React, {createContext, useContext, useReducer} from 'react';

export const StateContext = createContext({});

export const StateProvider = ({children}) => {
	const initialState = {
		allRefetch: [],
		sessionViewTriggered: 'total',
		variantChartTriggered: 'medians'
	};

	const reducer = (state, action) => {
		switch (action.type) {
			case 'changeSessionView':
				return {
					...state,
					sessionViewTriggered: action.newAction
				};

			case 'addRefetch':
				return {
					...state,
					allRefetch: [...state.allRefetch, action.newAction]
				};

			case 'setVariantChartTriggered':
				return {
					...state,
					variantChartTriggered: action.newAction
				};

			default:
				return state;
		}
	};

	return (
		<StateContext.Provider value={useReducer(reducer, initialState)}>
			{children}
		</StateContext.Provider>
	);
};

export const useStateValue = () => useContext(StateContext);
