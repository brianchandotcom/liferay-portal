/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {UPDATE_UNDO_ACTIONS} from '../actions/types';
import {undoAction} from '../components/undo/undoActions';

let promise = Promise.resolve();

export default function undo({store}) {
	return (dispatch) => {
		if (!store.undoHistory || !store.undoHistory.length) {
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
