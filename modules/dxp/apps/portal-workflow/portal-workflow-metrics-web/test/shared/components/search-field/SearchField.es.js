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

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import SearchField from '../../../../src/main/resources/META-INF/resources/js/shared/components/search-field/SearchField.es';
import {MockRouter} from '../../../mock/MockRouter.es';

describe('The SearchField component should', () => {
	afterEach(cleanup);

	test('Be render with empty value', () => {
		const {getByTestId} = render(
			<MockRouter>
				<SearchField />
			</MockRouter>
		);

		const searchInput = getByTestId('searchField');
		expect(searchInput.value).toBe('');
	});

	test('Be render with "test" value', () => {
		const {getByTestId} = render(
			<MockRouter query="?search=test">
				<SearchField />
			</MockRouter>
		);

		const searchInput = getByTestId('searchField');
		expect(searchInput.value).toBe('test');
	});

	test('Be render with empty value, change for "test" and submit', () => {
		const {getByTestId} = render(
			<MockRouter>
				<SearchField />
			</MockRouter>
		);

		const searchInput = getByTestId('searchField');
		const searchForm = getByTestId('searchFieldForm');

		expect(searchInput.value).toBe('');

		fireEvent.change(searchInput, {target: {value: 'test'}});

		expect(searchInput.value).toBe('test');

		fireEvent.submit(searchForm);

		//@TODO: waiting for mock router improve implamentation to expect the correct search router value
	});

	test('Be render with "test" value, change for "testing" and submit', () => {
		const {getByTestId} = render(
			<MockRouter query="?search=test">
				<SearchField />
			</MockRouter>
		);

		const searchInput = getByTestId('searchField');
		const searchForm = getByTestId('searchFieldForm');

		expect(searchInput.value).toBe('test');

		fireEvent.change(searchInput, {target: {value: 'testing'}});

		expect(searchInput.value).toBe('testing');

		fireEvent.submit(searchForm);

		//@TODO: waiting for mock router improve implamentation to expect the correct search router value
	});
});
