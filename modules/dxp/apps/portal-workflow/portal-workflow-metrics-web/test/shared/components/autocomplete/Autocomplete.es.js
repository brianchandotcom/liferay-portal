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

import {Autocomplete} from '../../../../src/main/resources/META-INF/resources/js/shared/components/autocomplete/Autocomplete.es';

describe('The Autocomplete component should', () => {
	let getByTestId;
	let getAllByTestId;
	const onChange = jest.fn();

	const items = [
		{
			additionalName: '',
			contentType: 'UserAccount',
			familyName: 'test0',
			givenName: '0test',
			id: 39431,
			name: '0test test0',
			profileURL: ''
		}
	];
	beforeEach(() => {
		const autocomplete = render(
			<Autocomplete items={items} onChange={onChange} />
		);

		getAllByTestId = autocomplete.getAllByTestId;
		getByTestId = autocomplete.getByTestId;
	});

	afterEach(() => cleanup);

	test('Render its items list', () => {
		const dropDownListItem = getByTestId('dropDownListItem');
		expect(dropDownListItem.innerHTML).toContain('0test test0');
	});

	test('Fire onChange handler function on change its text', () => {
		const autocompleteInput = getAllByTestId('autocompleteInput');
		fireEvent.change(autocompleteInput[0], {target: {value: '0te'}});
		expect(onChange).toHaveBeenCalled();
	});
});

describe('The Autocomplete component with children should', () => {
	let getByTestId;
	let getAllByTestId;
	beforeEach(() => {
		const autocomplete = render(
			<Autocomplete items={[{name: '0test'}]}>
				<span data-testid="mockChild">Mock child</span>
			</Autocomplete>
		);

		getAllByTestId = autocomplete.getAllByTestId;
		getByTestId = autocomplete.getByTestId;
	});

	afterEach(() => cleanup);

	test('Render with no items and with children', () => {
		const autocompleteInput = getAllByTestId('autocompleteInput');

		fireEvent.change(autocompleteInput[0], {target: {value: '0te'}});
		const mockChild = getByTestId('mockChild');
		expect(mockChild.innerHTML).toContain('Mock child');
	});
});
