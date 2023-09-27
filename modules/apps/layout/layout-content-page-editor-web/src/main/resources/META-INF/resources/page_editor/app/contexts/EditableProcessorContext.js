/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
