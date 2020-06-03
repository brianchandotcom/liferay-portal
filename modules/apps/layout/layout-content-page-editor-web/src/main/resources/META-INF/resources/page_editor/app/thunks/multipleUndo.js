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

import {UPDATE_UNDO_ACTIONS} from '../actions/types';
import {undoAction} from '../components/undo/undoActions';
import {canUndoAction} from './../components/undo/undoActions';

export default function multipleUndo({numberOfActions, store}) {
	return (dispatch) => {
		if (!store.undoHistory) {
			return;
		}

		const actions = [];

		const multipleUndoDispatch = (action) => {
			if (canUndoAction(action)) {
				actions.push(action);
			}
		};

		const undosToUndo = store.undoHistory.slice(0, numberOfActions);

		const remainingUndos = store.undoHistory.slice(
			numberOfActions,
			store.undoHistory.length
		);

		undosToUndo
			.reduce((promise, undo) => {
				return promise.then(() => {
					return undoAction({action: undo, store})(
						multipleUndoDispatch
					);
				});
			}, Promise.resolve())
			.then(() => {
				actions.forEach((action) => {
					dispatch({...action, isUndo: true});
				});
			})
			.then(() => {
				dispatch({
					type: UPDATE_UNDO_ACTIONS,
					undoHistory: remainingUndos,
				});
			});
	};
}
