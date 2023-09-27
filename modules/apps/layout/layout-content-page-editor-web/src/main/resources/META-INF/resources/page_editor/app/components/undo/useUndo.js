/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useRef} from 'react';

import {ADD_REDO_ACTION, ADD_UNDO_ACTION} from '../../actions/types';
import {canUndoAction} from './undoActions';

export default function useUndo([state, dispatch]) {
	const ref = useRef((action) => {
		if (canUndoAction(action)) {
			if (action.isUndo) {
				dispatch({
					...action,
					actionType: action.type,
					type: ADD_REDO_ACTION,
				});
			}
			else {
				dispatch({
					...action,
					actionType: action.type,
					type: ADD_UNDO_ACTION,
				});
			}
		}

		dispatch(action);
	});

	return [state, ref.current];
}
