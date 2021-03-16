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

import BlueprintsSearch from '../../../src/main/resources/META-INF/resources/js/components/BlueprintsSearch';
import {fetchResponse} from '../../../src/main/resources/META-INF/resources/js/utils/api';
import {FETCH_URL, RESOURCE, SUGGESTIONS, SUGGEST_URL} from '../mocks/data';

import '@testing-library/jest-dom/extend-expect';

jest.mock('../../../src/main/resources/META-INF/resources/js/utils/api');

function renderBlueprintsSearch() {
	return render(
		<BlueprintsSearch
			fetchResultsURL={FETCH_URL}
			suggestionsURL={SUGGEST_URL}
		/>
	);
}

describe('BlueprintsSearch', () => {
	beforeEach(() => {
		fetchResponse.mockImplementation(() => Promise.resolve(SUGGESTIONS));
		fetch.mockResponse(JSON.stringify(RESOURCE));
	});

	it('renders the default searchbar', async () => {
		const {getByPlaceholderText} = renderBlueprintsSearch();

		await waitForElement(() => getByPlaceholderText('search'));
	});

	it('displays results when search is performed', async () => {
		const {getByPlaceholderText, getByText} = renderBlueprintsSearch();

		fireEvent.change(getByPlaceholderText('search'), {
			target: {value: 'Test'},
		});

		fireEvent.keyDown(getByPlaceholderText('search'), {
			code: 'Enter',
			key: 'Enter',
		});

		await waitForElement(() =>
			getByText('x-results-for-x', {exact: false})
		);

		RESOURCE.items.map((item) => getByText(item.description));
	});
});
