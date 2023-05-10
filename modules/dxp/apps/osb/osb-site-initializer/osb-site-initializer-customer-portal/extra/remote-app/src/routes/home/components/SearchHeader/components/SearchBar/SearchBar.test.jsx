/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {fireEvent, render, screen} from '@testing-library/react';
import SearchBar from './';

describe('Home: SearchBar', () => {
	const functionMock = jest.fn();

	it('updates input while typing value', () => {
		const searchProjectName = 'Test Account 01';

		render(<SearchBar onSearchSubmit={functionMock} />);

		const searchInput = screen.getByPlaceholderText(/find a project/i);

		fireEvent.change(searchInput, {target: {value: searchProjectName}});
		expect(searchInput.value).toBe('Test Account 01');
		expect(functionMock).toHaveBeenCalled();
	});
});
