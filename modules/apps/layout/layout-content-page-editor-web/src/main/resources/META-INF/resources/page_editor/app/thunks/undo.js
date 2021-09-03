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

let promise = Promise.resolve();

export default function undo({store}) {
	return (dispatch) => {
		if (!store.undoHistory || store.undoHistory.length === 0) {
			return;
		}

		const [lastUndo, ...undos] = store.undoHistory || [];

		dispatch({type: UPDATE_UNDO_ACTIONS, undoHistory: undos});

		const undoDispatch = (action) => {
			return dispatch({
				...action,
				isUndo: true,
				originalType: lastUndo.originalType || lastUndo.type,
			});
		};

		promise = promise.then(() =>
			undoAction({action: lastUndo, store})(undoDispatch, () => store)
		);
	};
}
