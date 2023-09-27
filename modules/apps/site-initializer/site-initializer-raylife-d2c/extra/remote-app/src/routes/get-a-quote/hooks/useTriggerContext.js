/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

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
