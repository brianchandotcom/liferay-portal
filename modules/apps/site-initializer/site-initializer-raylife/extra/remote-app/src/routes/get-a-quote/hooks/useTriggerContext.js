import {useContext} from 'react';

import {ActionTypes, AppContext} from '../context/AppContextProvider';

export function useTriggerContext() {
	const {
		dispatch,
		state: {selectedTrigger},
	} = useContext(AppContext);

	const isSelected = (label) => label === selectedTrigger;

	const dispatchState = (payload) =>
		dispatch({
			payload,
			type: ActionTypes.SET_SELECTED_TRIGGER,
		});

	return {
		clearState: () => dispatchState(''),
		isSelected,
		selectedTrigger,
		updateState: (label) => dispatchState(!isSelected(label) ? label : ''),
	};
}
