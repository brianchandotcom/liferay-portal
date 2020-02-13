/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import React, {useCallback, useContext, useReducer} from 'react';

const EditableProcessorContext = React.createContext(null);
const INITIAL_STATE = null;
const SET_EDITABLE_UNIQUE_ID = 'SET_EDITABLE_UNIQUE_ID';

function reducer(state = INITIAL_STATE, action) {
	if (action.type === SET_EDITABLE_UNIQUE_ID) {
		return action.editableUniqueId;
	}

	return state;
}

export function EditableProcessorContextProvider({children}) {
	const store = useReducer(reducer, INITIAL_STATE);

	return (
		<EditableProcessorContext.Provider value={store}>
			{children}
		</EditableProcessorContext.Provider>
	);
}

export function useEditableProcessorUniqueId() {
	const [state] = useContext(EditableProcessorContext);

	return state;
}

export function useSetEditableProcessorUniqueId() {
	const [, dispatch] = useContext(EditableProcessorContext);

	return useCallback(
		editableUniqueIdOrNull => {
			dispatch({
				editableUniqueId: editableUniqueIdOrNull,
				type: SET_EDITABLE_UNIQUE_ID
			});
		},
		[dispatch]
	);
}
