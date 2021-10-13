import {useContext} from 'react';

import {AppContext} from '../context/AppContext';
import {setSelectedTrigger} from '../context/actions';

export const useTriggerContext = () => {
	const {
		dispatch,
		state: {selectedTrigger},
	} = useContext(AppContext);

	const isSelected = (label) => {
		return label === selectedTrigger;
	};

	const updateState = (label) => {
		if (label === selectedTrigger) {
			dispatch(setSelectedTrigger(''));
		} else {
			dispatch(setSelectedTrigger(label));
		}
	};

	return {
		isSelected,
		updateState,
	};
};
