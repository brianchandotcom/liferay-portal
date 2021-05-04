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

import '@testing-library/jest-dom/extend-expect';
import {act, cleanup, render} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import UndoHistory from '../../../../../src/main/resources/META-INF/resources/page_editor/app/components/undo/UndoHistory';
import {UNDO_TYPES} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/undoTypes';
import {StoreAPIContextProvider} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import multipleUndo from '../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/multipleUndo';

const mockDispatch = jest.fn((a) => {
	if (typeof a === 'function') {
		return a(mockDispatch);
	}
});

const mockState = {
	availableSegmentsExperiences: {
		0: {
			name: 'Experience 0',
			segmentsExperienceId: '0',
		},
	},
	redoHistory: [
		{
			actionId: 0,
			itemId: 'item-0',
			itemName: 'Item 0',
			layoutData: {},
			segmentsExperienceId: '0',
			type: 'ADD_ITEM',
		},
		{
			actionId: 1,
			itemId: 'item-1',
			itemName: 'Item 1',
			layoutData: {},
			segmentsExperienceId: '0',
			type: 'MOVE_ITEM',
		},
	],
	undoHistory: [
		{
			actionId: 2,
			itemId: 'item-2',
			itemName: 'Item 2',
			layoutData: {},
			segmentsExperienceId: '0',
			type: 'UPDATE_EDITABLE_VALUES',
		},
		{
			actionId: 3,
			itemId: 'item-3',
			itemName: 'Item 3',
			layoutData: {},
			segmentsExperienceId: '0',
			type: 'DELETE_ITEM',
		},
	],
};

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/page_editor/app/components/undo/getActionLabel',
	() => jest.fn((action) => action.itemName)
);

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/multipleUndo',
	() => jest.fn(() => () => Promise.resolve())
);

function renderUndoHistory() {
	return render(
		<StoreAPIContextProvider
			dispatch={mockDispatch}
			getState={() => mockState}
		>
			<UndoHistory />
		</StoreAPIContextProvider>
	);
}

describe('UndoHistory', () => {
	afterEach(() => {
		cleanup();
		multipleUndo.mockClear();
	});

	it('shows all redo and undo history items in the list', () => {
		const {getByText} = renderUndoHistory();

		mockState.redoHistory
			.concat(mockState.undoHistory)
			.forEach((historyItem) =>
				expect(getByText(historyItem.itemName)).toBeInTheDocument()
			);
	});

	it('calls multipleUndo with the correct number of actions', async () => {
		const {getByText} = renderUndoHistory();

		const redoHistory = [...mockState.redoHistory].reverse();

		for (let i = 0; i < redoHistory.length; i++) {
			const redoItem = redoHistory[i];

			const button = getByText(redoItem.itemName);

			if (!button.disabled) {
				await act(async () => userEvent.click(button));

				expect(multipleUndo).toBeCalledWith(
					expect.objectContaining({
						numberOfActions: mockState.redoHistory.length - i,
						type: UNDO_TYPES.redo,
					})
				);
			}
		}

		const undoHistory = mockState.undoHistory;

		for (let i = 0; i < undoHistory.length; i++) {
			const undoItem = undoHistory[i];

			const button = getByText(undoItem.itemName);

			if (!button.disabled) {
				await act(async () => userEvent.click(button));

				expect(multipleUndo).toBeCalledWith(
					expect.objectContaining({
						numberOfActions: i,
						type: UNDO_TYPES.undo,
					})
				);
			}
		}
	});
});
