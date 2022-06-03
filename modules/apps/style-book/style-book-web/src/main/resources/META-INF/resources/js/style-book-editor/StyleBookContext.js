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

import {openToast} from 'frontend-js-web';
import React, {useCallback, useContext, useReducer} from 'react';

import {
	ADD_REDO_ACTION,
	ADD_UNDO_ACTION,
	LOADING,
	SET_DRAFT_STATUS,
	SET_PREVIEW_LAYOUT,
	SET_PREVIEW_LAYOUT_TYPE,
	SET_TOKEN_VALUES,
	UPDATE_UNDO_REDO_HISTORY,
} from './constants/actionTypes';
import {DRAFT_STATUS} from './constants/draftStatusConstants';
import {UNDO_TYPES} from './constants/undoTypes';
import reducer from './reducer';
import saveDraft from './saveDraft';

const StyleBookDispatchContext = React.createContext(() => {});
export const StyleBookStoreContext = React.createContext({
	draftStatus: null,
	frontendTokensValues: {},
	loading: true,
	previewLayout: {},
	previewLayoutType: null,
	redoHistory: [],
	undoHistory: [],
});

export function StyleBookContextProvider({children, initialState}) {
	const [state, dispatch] = useReducer(reducer, initialState);

	return (
		<StyleBookDispatchContext.Provider value={dispatch}>
			<StyleBookStoreContext.Provider value={state}>
				{children}
			</StyleBookStoreContext.Provider>
		</StyleBookDispatchContext.Provider>
	);
}

export function useDispatch() {
	return useContext(StyleBookDispatchContext);
}

export function useDraftStatus() {
	return useContext(StyleBookStoreContext).draftStatus;
}

export function useFrontendTokensValues() {
	return useContext(StyleBookStoreContext).frontendTokensValues;
}

export function useLoading() {
	return useContext(StyleBookStoreContext).loading;
}

export function usePreviewLayout() {
	return useContext(StyleBookStoreContext).previewLayout;
}

export function usePreviewLayoutType() {
	return useContext(StyleBookStoreContext).previewLayoutType;
}

export function useRedoHistory() {
	return useContext(StyleBookStoreContext).redoHistory;
}

export function useUndoHistory() {
	return useContext(StyleBookStoreContext).undoHistory;
}

function internalSaveTokenValues({dispatch, frontendTokensValues, tokens}) {
	dispatch({
		type: SET_DRAFT_STATUS,
		value: DRAFT_STATUS.saving,
	});

	return saveDraft({...frontendTokensValues, ...tokens})
		.then(() => {
			dispatch({
				type: SET_DRAFT_STATUS,
				value: DRAFT_STATUS.draftSaved,
			});

			dispatch({
				tokens,
				type: SET_TOKEN_VALUES,
			});
		})
		.catch((error) => {
			if (process.env.NODE_ENV === 'development') {
				console.error(error);
			}

			dispatch({
				type: SET_DRAFT_STATUS,
				value: DRAFT_STATUS.notSaved,
			});

			openToast({
				message: error.message,
				type: 'danger',
			});
		});
}

export function useSaveTokenValue() {
	const dispatch = useDispatch();
	const frontendTokensValues = useFrontendTokensValues();

	return ({label, name, value}) => {
		const previousValue = frontendTokensValues[name];

		internalSaveTokenValues({
			dispatch,
			frontendTokensValues,
			tokens: {[name]: value},
		}).then(() => {
			dispatch({
				label,
				name,
				type: ADD_UNDO_ACTION,
				value: previousValue,
			});
		});
	};
}

export function useSetLoading() {
	const dispatch = useDispatch();

	return useCallback((value) => dispatch({type: LOADING, value}), [dispatch]);
}

export function useSetPreviewLayout() {
	const dispatch = useDispatch();

	return useCallback(
		(layout) => dispatch({layout, type: SET_PREVIEW_LAYOUT}),
		[dispatch]
	);
}

export function useSetPreviewLayoutType() {
	const dispatch = useDispatch();

	return (layoutType) =>
		dispatch({layoutType, type: SET_PREVIEW_LAYOUT_TYPE});
}

export function useOnUndo() {
	const dispatch = useDispatch();
	const frontendTokensValues = useFrontendTokensValues();
	const undoHistory = useUndoHistory();

	return () => {
		const [lastUndo, ...undos] = undoHistory;
		const previousValue = frontendTokensValues[lastUndo.name];

		if (!lastUndo.value) {
			delete frontendTokensValues[lastUndo.name];
		}

		internalSaveTokenValues({
			dispatch,
			frontendTokensValues,
			tokens: lastUndo.value ? {[lastUndo.name]: lastUndo.value} : {},
		}).then(() => {
			dispatch({
				type: UPDATE_UNDO_REDO_HISTORY,
				undoHistory: undos,
			});
			dispatch({
				label: lastUndo.label,
				name: lastUndo.name,
				type: ADD_REDO_ACTION,
				value: previousValue,
			});
		});
	};
}

export function useOnRedo() {
	const dispatch = useDispatch();
	const frontendTokensValues = useFrontendTokensValues();
	const redoHistory = useRedoHistory();

	return () => {
		const [lastRedo, ...redos] = redoHistory;
		const previousValue = frontendTokensValues[lastRedo.name];

		internalSaveTokenValues({
			dispatch,
			frontendTokensValues,
			tokens: {[lastRedo.name]: lastRedo.value},
		}).then(() => {
			dispatch({
				redoHistory: redos,
				type: UPDATE_UNDO_REDO_HISTORY,
			});
			dispatch({
				isRedo: true,
				label: lastRedo.label,
				name: lastRedo.name,
				type: ADD_UNDO_ACTION,
				value: previousValue,
			});
		});
	};
}

export function useMultipleUndo() {
	const dispatch = useDispatch();
	const undoHistory = useUndoHistory();
	const redoHistory = useRedoHistory();
	const frontendTokensValues = useFrontendTokensValues();
	let tokens = {};

	return ({numberOfActions, type}) => {
		let remainingUndos;
		let undosToUndo;
		let updateHistoryAction;

		if (type === UNDO_TYPES.undo) {
			undosToUndo = undoHistory.slice(0, numberOfActions);

			remainingUndos = undoHistory.slice(
				numberOfActions,
				undoHistory.length
			);

			const nextRedoHistory = undosToUndo.map(({label, name}) => ({
				label,
				name,
				value: frontendTokensValues[name],
			}));

			updateHistoryAction = {
				redoHistory: [...nextRedoHistory.reverse(), ...redoHistory],
				type: UPDATE_UNDO_REDO_HISTORY,
				undoHistory: remainingUndos,
			};
		}
		else {
			undosToUndo = redoHistory.slice(0, numberOfActions);

			remainingUndos = redoHistory.slice(
				numberOfActions,
				redoHistory.length
			);

			const nextUndoHistory = undosToUndo.map(({label, name}) => ({
				label,
				name,
				value: frontendTokensValues[name],
			}));

			updateHistoryAction = {
				redoHistory: remainingUndos,
				type: UPDATE_UNDO_REDO_HISTORY,
				undoHistory: [...nextUndoHistory.reverse(), ...undoHistory],
			};
		}

		for (const undo of undosToUndo) {
			if (undo.value) {
				tokens = {...tokens, [undo.name]: undo.value};
			}
			else {
				delete frontendTokensValues[undo.name];
			}
		}

		return internalSaveTokenValues({
			dispatch,
			frontendTokensValues,
			tokens,
		}).then(() => {
			dispatch(updateHistoryAction);
		});
	};
}
