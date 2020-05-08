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

import {useRef} from 'react';

import {
	ADD_UNDO_ACTION,
	CLEAN_UNDO_ACTIONS,
	DELETE_ITEM,
} from '../../actions/types';
import {config} from '../../config/index';
import {canUndoAction} from './undoActions';

export default function useUndo([state, dispatch]) {
	const ref = useRef((action) => {
		if (config.undoEnabled && canUndoAction(action)) {
			dispatch({
				...action,
				actionType: action.type,
				type: ADD_UNDO_ACTION,
			});
		}

		if (action.type === DELETE_ITEM) {
			dispatch({
				itemId: action.itemId,
				type: CLEAN_UNDO_ACTIONS,
			});
		}

		dispatch(action);
	});

	return [state, ref.current];
}
