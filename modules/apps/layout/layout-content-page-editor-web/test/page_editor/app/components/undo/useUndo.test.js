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

import {renderHook} from '@testing-library/react-hooks';

import * as Actions from '../../../../../src/main/resources/META-INF/resources/page_editor/app/actions/types';
import useUndo from '../../../../../src/main/resources/META-INF/resources/page_editor/app/components/undo/useUndo';
import * as ExperienceActions from '../../../../../src/main/resources/META-INF/resources/page_editor/plugins/experience/actions';

describe('useUndo', () => {

	// List of the actions that do not need undo
	// Before adding an action here blindly, check if
	// something needs to be done to support undo for that action

	const noUndoActions = [
		Actions.ADD_FRAGMENT_COMPOSITION,
		Actions.ADD_FRAGMENT_ENTRY_LINK_COMMENT,
		Actions.ADD_MAPPED_INFO_ITEM,
		Actions.ADD_UNDO_ACTION,
		Actions.ADD_REDO_ACTION,
		Actions.ADD_USED_WIDGET,
		Actions.DELETE_FRAGMENT_ENTRY_LINK_COMMENT,
		Actions.DELETE_WIDGETS,
		Actions.EDIT_FRAGMENT_ENTRY_LINK_COMMENT,
		Actions.INIT,
		Actions.LOAD_REDUCER,
		Actions.SET_FRAGMENT_EDITABLES,
		Actions.SWITCH_SIDEBAR_PANEL,
		Actions.TOGGLE_PERMISSION,
		Actions.TOGGLE_SHOW_RESOLVED_COMMENTS,
		Actions.UNLOAD_REDUCER,
		Actions.UPDATE_COL_SIZE,
		Actions.UPDATE_FRAGMENT_ENTRY_LINK_CONTENT,
		Actions.UPDATE_LAYOUT_DATA,
		Actions.UPDATE_NETWORK,
		Actions.UPDATE_UNDO_ACTIONS,
		Actions.UPDATE_PAGE_CONTENTS,
		Actions.UPDATE_REDO_ACTIONS,
		ExperienceActions.CREATE_SEGMENTS_EXPERIENCE,
		ExperienceActions.DELETE_SEGMENTS_EXPERIENCE,
		ExperienceActions.UPDATE_SEGMENTS_EXPERIENCE,
		ExperienceActions.UPDATE_SEGMENTS_EXPERIENCES_LIST,
	];

	const dispatch = jest.fn();

	const {result} = renderHook(() => useUndo([{}, dispatch]));

	const undoDispatch = result.current[1];

	const allActions = {...Actions, ...ExperienceActions};

	Object.keys(allActions)
		.filter((action) => !noUndoActions.includes(action))
		.forEach((action) => {
			it(`is supported by "${action}" action`, () => {
				undoDispatch({type: action});

				expect(dispatch).toBeCalledWith(
					expect.objectContaining({type: Actions.ADD_UNDO_ACTION})
				);

				dispatch.mockClear();
			});
		});
});
