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

import {fireEvent, render, waitForElement} from '@testing-library/react';
import React from 'react';

import SearchBar from '../../../src/main/resources/META-INF/resources/js/components/SearchBar';
import {fetchResponse} from '../../../src/main/resources/META-INF/resources/js/utils/api';
import {SUGGESTIONS, SUGGEST_URL} from '../mocks/data';

import '@testing-library/jest-dom/extend-expect';

function renderSearchBar(props) {
	return render(
		<SearchBar
			handleSubmit={jest.fn()}
			suggestionsURL={SUGGEST_URL}
			{...props}
		/>
	);
}

jest.mock('../../../src/main/resources/META-INF/resources/js/utils/api');

jest.useFakeTimers();

describe('SearchBar', () => {
	beforeEach(() => {
		fetchResponse.mockImplementation(() => Promise.resolve(SUGGESTIONS));
	});

	it('renders the searchbar', async () => {
		const {getByPlaceholderText} = renderSearchBar();

		await waitForElement(() => getByPlaceholderText('search'));
	});

	it('shows suggestions when keywords are typed in', async () => {
		const {container, getByPlaceholderText, getByText} = renderSearchBar();

		fireEvent.change(getByPlaceholderText('search'), {
			target: {value: 'Test'},
		});

		jest.runAllTimers();

		await waitForElement(() =>
			container.querySelectorAll('.dropdown-menu')
		);

		SUGGESTIONS.suggestions.map((item) => getByText(item));
	});

	it('calls handleSubmit when the Enter key is pressed', async () => {
		const handleSubmit = jest.fn();

		const {getByPlaceholderText} = renderSearchBar({handleSubmit});

		await waitForElement(() => getByPlaceholderText('search'));

		fireEvent.change(getByPlaceholderText('search'), {
			target: {value: 'Test'},
		});

		fireEvent.keyDown(getByPlaceholderText('search'), {
			code: 'Enter',
			key: 'Enter',
		});

		expect(handleSubmit).toHaveBeenCalled();
	});
});
