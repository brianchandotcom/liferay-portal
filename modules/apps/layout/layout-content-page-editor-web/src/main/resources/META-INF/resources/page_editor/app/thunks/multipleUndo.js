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

import {updateNetwork} from '../actions/index';
import {
	UPDATE_NETWORK,
	UPDATE_REDO_ACTIONS,
	UPDATE_UNDO_ACTIONS,
} from '../actions/types';
import {undoAction} from '../components/undo/undoActions';
import {SERVICE_NETWORK_STATUS_TYPES} from '../config/constants/serviceNetworkStatusTypes';
import {UNDO_TYPES} from '../config/constants/undoTypes';
import {canUndoAction} from './../components/undo/undoActions';

export default function multipleUndo({numberOfActions, store, type}) {
	return (dispatch) => {
		if (!store.undoHistory && !store.redoHistory) {
			return;
		}

		const actions = [];

		let updatedLayoutData = store.layoutData;

		const multipleUndoDispatch = (originalType) => (action) => {
			if (canUndoAction(action)) {
				if (action.layoutData) {
					updatedLayoutData = action.layoutData;
				}

				actions.push({action, originalType});
			}
			else if (action.type !== UPDATE_NETWORK) {
				dispatch(action);
			}
		};

		let isUndoAction, remainingUndos, undosToUndo, updateHistoryAction;

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
						store: {...store, layoutData: updatedLayoutData},
					})(multipleUndoDispatch(undo.originalType || undo.type));
				});
			}, Promise.resolve())
			.then(() => {
				actions.forEach(({action, originalType}) => {
					dispatch({...action, ...isUndoAction, originalType});
				});
			})
			.then(() => {
				dispatch(updateHistoryAction);
				dispatch(
					updateNetwork({
						requestGenerateDraft: false,
						status: SERVICE_NETWORK_STATUS_TYPES.draftSaved,
					})
				);
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
