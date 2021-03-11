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
