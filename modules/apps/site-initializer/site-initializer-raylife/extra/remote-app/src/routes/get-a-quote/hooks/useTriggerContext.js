import {useContext} from 'react';

import {ActionTypes, AppContext} from '../context/AppContextProvider';

export function useTriggerContext() {
	const {
		dispatch,
		state: {selectedTrigger},
	} = useContext(AppContext);

	const isSelected = (label) => {
		return label === selectedTrigger;
	};

	return {
		isSelected,
		updateState: (label) =>
			dispatch({
				payload: label === selectedTrigger ? '' : label,
				type: ActionTypes.SET_SELECTED_TRIGGER,
			}),
	};
}
