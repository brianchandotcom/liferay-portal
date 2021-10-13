import React, {createContext, useEffect, useReducer} from 'react';
import {TIP_EVENT_DISMISS} from '~/common/utils/events';
import {AVAILABLE_STEPS} from '~/routes/get-a-quote/utils/constants';

import {setSelectedTrigger} from './actions';
import {reducer} from './reducer';

const initialState = {
	selectedProduct: '',
	selectedStep: {
		percentage: {
			[AVAILABLE_STEPS.BASICS_BUSINESS_INFORMATION.section]: 0,
			[AVAILABLE_STEPS.BUSINESS.section]: 0,
			[AVAILABLE_STEPS.EMPLOYEES.section]: 0,
			[AVAILABLE_STEPS.PROPERTY.section]: 0,
		},
		section: 'basics',
		subsection: 'business-type',
		title: "Welcome! Let's start.",
	},
	selectedTrigger: '',
};

export const AppContext = createContext({});

export const AppProvider = ({children}) => {
	const [state, dispatch] = useReducer(reducer, initialState);

	useEffect(() => {
		const onDismiss = () => dispatch(setSelectedTrigger(''));

		window.addEventListener(TIP_EVENT_DISMISS, onDismiss);

		return () => window.removeEventListener(TIP_EVENT_DISMISS, onDismiss);
	}, []);

	return (
		<AppContext.Provider value={{dispatch, state}}>
			{children}
		</AppContext.Provider>
	);
};
