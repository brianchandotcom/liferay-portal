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

import React, {useCallback, useContext, useRef, useState} from 'react';

const INITIAL_STATE = {editableClickPosition: null, editableUniqueId: null};

const EditableProcessorDispatchContext = React.createContext(() => {});
const EditableProcessorRefContext = React.createContext({current: null});
const EditableProcessorStateContext = React.createContext(INITIAL_STATE);

export function EditableProcessorContextProvider({children}) {
	const [state, setState] = useState(INITIAL_STATE);
	const ref = useRef(null);

	ref.current = state;

	return (
		<EditableProcessorDispatchContext.Provider value={setState}>
			<EditableProcessorRefContext.Provider value={ref}>
				<EditableProcessorStateContext.Provider value={state}>
					{children}
				</EditableProcessorStateContext.Provider>
			</EditableProcessorRefContext.Provider>
		</EditableProcessorDispatchContext.Provider>
	);
}

export function useEditableProcessorClickPosition() {
	const state = useContext(EditableProcessorStateContext);

	return state.editableClickPosition;
}

export function useEditableProcessorUniqueId() {
	return useContext(EditableProcessorStateContext).editableUniqueId;
}

export function useIsProcessorEnabled() {
	const ref = useContext(EditableProcessorRefContext);

	return useCallback(
		(editableUniqueId = null) =>
			editableUniqueId
				? ref.current?.editableUniqueId === editableUniqueId
				: !!ref.current?.editableUniqueId,
		[ref]
	);
}

export function useSetEditableProcessorUniqueId() {
	const setState = useContext(EditableProcessorDispatchContext);

	return useCallback(
		(editableUniqueIdOrNull, editableClickPosition = null) => {
			setState({
				editableClickPosition,
				editableUniqueId: editableUniqueIdOrNull,
			});
		},
		[setState]
	);
}
