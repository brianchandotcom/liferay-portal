/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {UPDATE_REDO_ACTIONS} from '../actions/types';
import {undoAction} from '../components/undo/undoActions';

let promise = Promise.resolve();

export default function redo({store}) {
	return (dispatch) => {
		if (!store.redoHistory || !store.redoHistory.length) {
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
