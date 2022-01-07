import React, {createContext, useEffect, useReducer} from 'react';
import useWindowDimensions from '../../../common/hooks/useWindowDimensions';
import {TIP_EVENT_DISMISS} from '../../../common/utils/events';
import {getTaxonomyVocabularies} from '../services/TaxonomyVolucabularies';
import {AVAILABLE_STEPS} from '../utils/constants';

const initialState = {
	dimensions: {},
	selectedProduct: '',
	selectedStep: {
		percentage: {
			[AVAILABLE_STEPS.BASICS_BUSINESS_INFORMATION.section]: 0,
			[AVAILABLE_STEPS.BUSINESS.section]: 0,
			[AVAILABLE_STEPS.EMPLOYEES.section]: 0,
			[AVAILABLE_STEPS.PROPERTY.section]: 0,
		},
		section: 'basics',
		subsection: 'product-quote',
		title: 'Welcome! Select a product.',
	},
	selectedTrigger: '',
	taxonomyVocabulary: {},
};

export const ActionTypes = {
	SET_DIMENSIONS: 'SET_DIMENSIONS',
	SET_SELECTED_PRODUCT: 'SET_SELECTED_PRODUCT',
	SET_SELECTED_STEP: 'SET_SELECTED_STEP',
	SET_SELECTED_TRIGGER: 'SET_SELECTED_TRIGGER',
	SET_TAXONOMY_VOCABULARY: 'SET_TAXONOMY_VOCABULARY',
};

function AppContextReducer(state, action) {
	switch (action.type) {
		case ActionTypes.SET_DIMENSIONS:
			return {
				...state,
				dimensions: action.payload,
			};

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

		case ActionTypes.SET_TAXONOMY_VOCABULARY:
			return {
				...state,
				taxonomyVocabulary: action.payload,
			};

		default:
			return state;
	}
}

export const AppContext = createContext({});

export function AppContextProvider({children}) {
	const dimensions = useWindowDimensions();
	const [state, dispatch] = useReducer(AppContextReducer, initialState);

	useEffect(() => {
		const onDismiss = () =>
			dispatch({
				payload: '',
				type: ActionTypes.SET_SELECTED_TRIGGER,
			});

		window.addEventListener(TIP_EVENT_DISMISS, onDismiss);

		return () => window.removeEventListener(TIP_EVENT_DISMISS, onDismiss);
	}, []);

	useEffect(() => {
		getTaxonomyVocabularies()
			.then(({data}) =>
				dispatch({
					payload: data.items[0],
					type: ActionTypes.SET_TAXONOMY_VOCABULARY,
				})
			)
			.catch((error) => console.error(error));
	}, []);

	useEffect(() => {
		dispatch({
			payload: dimensions,
			type: ActionTypes.SET_DIMENSIONS,
		});
	}, [dimensions]);

	return (
		<AppContext.Provider value={{dispatch, state}}>
			{children}
		</AppContext.Provider>
	);
}
