/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import SearchField from '../../../../src/main/resources/META-INF/resources/js/shared/components/search-field/SearchField.es';
import {MockRouter} from '../../../mock/MockRouter.es';

describe('The SearchField component should', () => {
	afterEach(cleanup);

	test('Be render with empty value', () => {
		const {container} = render(
			<MockRouter>
				<SearchField />
			</MockRouter>
		);

		const searchInput = container.querySelector('input.form-control');

		expect(searchInput.value).toBe('');
	});

	test('Be render with "test" value', () => {
		const {container} = render(
			<MockRouter query="?search=test">
				<SearchField />
			</MockRouter>
		);

		const searchInput = container.querySelector('input.form-control');

		expect(searchInput.value).toBe('test');
	});

	test('Be render with empty value, change for "test" and submit', () => {
		const {container, getByRole} = render(
			<MockRouter>
				<SearchField />
			</MockRouter>
		);

		const searchInput = container.querySelector('input.form-control');
		const searchForm = getByRole('search');

		expect(searchInput.value).toBe('');

		fireEvent.change(searchInput, {target: {value: 'test'}});

		expect(searchInput.value).toBe('test');

		fireEvent.submit(searchForm);
	});

	test('Be render with "test" value, change for "testing" and submit', () => {
		const {container, getByRole} = render(
			<MockRouter query="?search=test">
				<SearchField />
			</MockRouter>
		);

		const searchInput = container.querySelector('input.form-control');
		const searchForm = getByRole('search');

		expect(searchInput.value).toBe('test');

		fireEvent.change(searchInput, {target: {value: 'testing'}});

		expect(searchInput.value).toBe('testing');

		fireEvent.submit(searchForm);
	});
});
