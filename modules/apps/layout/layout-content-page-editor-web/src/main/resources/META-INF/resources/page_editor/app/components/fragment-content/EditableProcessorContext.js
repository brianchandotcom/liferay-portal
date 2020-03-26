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

import {useFromControlsId, useToControlsId} from '../CollectionItemContext';

const EditableProcessorContext = React.createContext(null);
const INITIAL_STATE = {editableClickPosition: null, editableUniqueId: null};
const SET_EDITABLE_UNIQUE_ID = 'SET_EDITABLE_UNIQUE_ID';

function reducer(state = INITIAL_STATE, action) {
	if (action.type === SET_EDITABLE_UNIQUE_ID) {
		return {
			editableClickPosition: action.editableClickPosition,
			editableUniqueId: action.editableUniqueId,
		};
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

export function useEditableProcessorClickPosition() {
	const [state] = useContext(EditableProcessorContext);

	return state.editableClickPosition;
}

export function useEditableProcessorUniqueId() {
	const [state] = useContext(EditableProcessorContext);
	const fromControlsId = useFromControlsId();

	return fromControlsId(state.editableUniqueId);
}

export function useIsProcessorEnabled() {
	const [state] = useContext(EditableProcessorContext);
	const toControlsId = useToControlsId();

	return useCallback(
		editableUniqueId =>
			state.editableUniqueId === toControlsId(editableUniqueId),
		[state.editableUniqueId, toControlsId]
	);
}

export function useSetEditableProcessorUniqueId() {
	const [, dispatch] = useContext(EditableProcessorContext);
	const toControlsId = useToControlsId();

	return useCallback(
		(editableUniqueIdOrNull, editableClickPosition = null) => {
			dispatch({
				editableClickPosition,
				editableUniqueId: toControlsId(editableUniqueIdOrNull),
				type: SET_EDITABLE_UNIQUE_ID,
			});
		},
		[dispatch, toControlsId]
	);
}
