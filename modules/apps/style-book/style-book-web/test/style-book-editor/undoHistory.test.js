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
import {render} from '@testing-library/react';
import React from 'react';

import {StyleBookContextProvider} from '../../src/main/resources/META-INF/resources/js/style-book-editor/StyleBookContext';
import UndoHistory from '../../src/main/resources/META-INF/resources/js/style-book-editor/UndoHistory';

const STATE = {
	redoHistory: [
		{
			label: 'Brand Color 2',
			name: 'testColor2',
			value: {
				cssVariableMapping: 'test-color-2',
				value: '#DDDDDD',
			},
		},
		{
			label: 'Test Color 1',
			name: 'testColor1',
			value: {
				cssVariableMapping: 'test-color-1',
				value: '#111111',
			},
		},
	],
	undoHistory: [
		{
			label: 'Test Color 3',
			name: 'testColor3',
			value: {
				cssVariableMapping: 'test-color-3',
				value: '#2e5aac',
			},
		},
		{
			label: 'Test Color 4',
			name: 'testColor4',
			value: {
				cssVariableMapping: 'test-color-4',
				value: '#6b6c7e',
			},
		},
		{
			label: 'Test Color 5',
			name: 'testColor5',
			value: {
				cssVariableMapping: 'test-color-5',
				value: '#BBBBBB',
			},
		},
	],
};

function renderUndoHistory() {
	return render(
		<StyleBookContextProvider
			initialState={{
				draftStatus: 'saved',
				frontendTokensValues: {},
				previewLayout: {},
				previewLayoutType: 'lalala',
				redoHistory: STATE.redoHistory,
				undoHistory: STATE.undoHistory,
			}}
		>
			<UndoHistory />
		</StyleBookContextProvider>
	);
}

describe('UndoHistory', () => {
	Liferay.Util.sub.mockImplementation((key, args) =>
		key.replace('-x', ` ${args}`)
	);

	it('shows all redo and undo history items in the list', () => {
		const {getByText} = renderUndoHistory();

		STATE.redoHistory
			.concat(STATE.undoHistory)
			.forEach(({label}) =>
				expect(getByText(`update ${label}`)).toBeInTheDocument()
			);
	});
});
