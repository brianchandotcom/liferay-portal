/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fireEvent, render} from '@testing-library/react';
import React from 'react';

import FilterInput from '../../../../src/main/resources/META-INF/resources/js/components/list/FilterInput.es';

function renderTestFilterInput(props) {
	return render(
		<FilterInput
			disableSearch={false}
			onChange={jest.fn()}
			onSubmit={jest.fn()}
			searchBarTerm="test"
			{...props}
		/>
	);
}

describe('FilterInput', () => {
	it('has the searchbar term in the filter input', () => {
		const {getByPlaceholderText} = renderTestFilterInput();

		const input = getByPlaceholderText('contains-text');

		expect(input.value).toEqual('test');
	});

	it('calls the onChange function when adding to the input', () => {
		const onChange = jest.fn();

		const {queryByPlaceholderText} = renderTestFilterInput({onChange});

		const input = queryByPlaceholderText('contains-text');

		fireEvent.change(input, {target: {value: 'a'}});

		expect(onChange).toHaveBeenCalledTimes(1);
	});

	it('calls the onLoadResults function when the searchbar enter is pressed', () => {
		const onSubmit = jest.fn();

		const {queryByPlaceholderText} = renderTestFilterInput({onSubmit});

		const input = queryByPlaceholderText('contains-text');

		fireEvent.change(input, {target: {value: 'test'}});

		fireEvent.keyDown(input, {key: 'Enter', keyCode: 13, which: 13});

		expect(onSubmit).toHaveBeenCalledTimes(1);
	});
});
