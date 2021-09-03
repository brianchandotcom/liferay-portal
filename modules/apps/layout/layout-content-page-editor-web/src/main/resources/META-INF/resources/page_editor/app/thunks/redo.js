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

import {UPDATE_REDO_ACTIONS} from '../actions/types';
import {undoAction} from '../components/undo/undoActions';

let promise = Promise.resolve();

export default function redo({store}) {
	return (dispatch) => {
		if (!store.redoHistory || store.redoHistory.length === 0) {
			return;
		}

		const [lastRedo, ...redos] = store.redoHistory || [];

		dispatch({redoHistory: redos, type: UPDATE_REDO_ACTIONS});

		const redoDispatch = (action) => {
			return dispatch({
				...action,
				isRedo: true,
				originalType: lastRedo.originalType || lastRedo.type,
			});
		};

		promise = promise.then(() =>
			undoAction({action: lastRedo, store})(redoDispatch, () => store)
		);
	};
}
