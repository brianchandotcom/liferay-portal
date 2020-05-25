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

import {
	ADD_UNDO_ACTION,
	DUPLICATE_ITEM,
} from '../../../../src/main/resources/META-INF/resources/page_editor/app/actions/types';
import undoReducer from '../../../../src/main/resources/META-INF/resources/page_editor/app/reducers/undoReducer';

describe('undoReducer', () => {
	it('allows only having 20 maximun undo items', () => {
		const initialState = {layoutData: {items: []}, undoHistory: []};

		const actions = new Array(25).fill({
			actionType: DUPLICATE_ITEM,
			type: ADD_UNDO_ACTION,
		});

		const finalState = actions.reduce((state, action) => {
			return undoReducer(state, action);
		}, initialState);

		expect(finalState.undoHistory.length).toBe(20);
	});
});
