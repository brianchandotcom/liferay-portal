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

import AddResultSearchBar from '../../../../src/main/resources/META-INF/resources/js/components/add_result/AddResultSearchBar.es';
import React from 'react';
import {render} from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';

const TEST_TEXT = 'search query test';

describe('AddResult', () => {
	it('shows a search input', () => {
		const {getByPlaceholderText} = render(
			<AddResultSearchBar
				onSearchKeyDown={jest.fn()}
				onSearchQueryChange={jest.fn()}
				onSearchSubmit={jest.fn()}
				searchQuery={TEST_TEXT}
			/>
		);

		const input = getByPlaceholderText('search-the-engine');

		expect(input.value).toEqual(TEST_TEXT);
	});
});
