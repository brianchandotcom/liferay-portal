/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {updateNetwork} from '../actions/index';
import {
	ADD_REDO_ACTION,
	ADD_UNDO_ACTION,
	UPDATE_REDO_ACTIONS,
	UPDATE_UNDO_ACTIONS,
} from '../actions/types';
import {undoAction} from '../components/undo/undoActions';
import {SERVICE_NETWORK_STATUS_TYPES} from '../config/constants/serviceNetworkStatusTypes';
import {UNDO_TYPES} from '../config/constants/undoTypes';
import {reducer} from '../reducers/index';
import {canUndoAction} from './../components/undo/undoActions';

export default function multipleUndo({numberOfActions, store, type}) {
	return (dispatch) => {
		if (!store.undoHistory && !store.redoHistory) {
			return;
		}

		let isUndoAction;
		let remainingUndos;
		let undosToUndo;
		let updateHistoryAction;

		let updatedStore = store;

		const multipleUndoDispatch = (originalType) => (action) => {
			if (canUndoAction(action)) {
				updatedStore = reducer(updatedStore, {
					...action,
					actionType: action.type,
					originalType,
					type:
						type === UNDO_TYPES.undo
							? ADD_REDO_ACTION
							: ADD_UNDO_ACTION,
				});

				updatedStore = reducer(updatedStore, {
					...action,
					...isUndoAction,
					originalType,
				});
			}
		};

		if (type === UNDO_TYPES.undo) {
			isUndoAction = {isUndo: true};

			undosToUndo = store.undoHistory.slice(0, numberOfActions);

			remainingUndos = store.undoHistory.slice(
				numberOfActions,
				store.undoHistory.length
			);

			updateHistoryAction = {
				type: UPDATE_UNDO_ACTIONS,
				undoHistory: remainingUndos,
			};
		}
		else {
			isUndoAction = {isRedo: true};

			undosToUndo = store.redoHistory.slice(0, numberOfActions);

			remainingUndos = store.redoHistory.slice(
				numberOfActions,
				store.redoHistory.length
			);

			updateHistoryAction = {
				redoHistory: remainingUndos,
				type: UPDATE_REDO_ACTIONS,
			};
		}

		dispatch(
			updateNetwork({
				status: SERVICE_NETWORK_STATUS_TYPES.savingDraft,
			})
		);

		return undosToUndo
			.reduce((promise, undo) => {
				return promise.then(() => {
					return undoAction({
						action: undo,
						store: updatedStore,
					})(
						multipleUndoDispatch(undo.originalType || undo.type),
						() => updatedStore
					);
				});
			}, Promise.resolve())
			.then(() => {
				dispatch(
					updateNetwork({
						status: SERVICE_NETWORK_STATUS_TYPES.draftSaved,
					})
				);

				updatedStore = reducer(updatedStore, updateHistoryAction);

				dispatch({store: updatedStore, type: 'UPDATE_STORE'});
			})
			.catch((error) => {
				if (process.env.NODE_ENV === 'development') {
					console.error(error);
				}

				dispatch(
					updateNetwork({
						error: Liferay.Language.get(
							'an-unexpected-error-occurred'
						),
						status: SERVICE_NETWORK_STATUS_TYPES.error,
					})
				);
			});
	};
}
