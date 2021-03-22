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

import SortSelect from '../../../src/main/resources/META-INF/resources/js/components/SortSelect';

import '@testing-library/jest-dom/extend-expect';

function renderSortSelect(props) {
	return render(<SortSelect onChange={jest.fn()} value={{}} {...props} />);
}

describe('TimeSelect', () => {
	it('renders the sort selection', () => {
		const {getByLabelText} = renderSortSelect();

		getByLabelText('sort-by');
	});

	it('calls onChange when option is selected', () => {
		const onChange = jest.fn();

		const {getByLabelText} = renderSortSelect({onChange});

		fireEvent.change(getByLabelText('sort-by'), {
			target: {value: 'title'},
		});

		expect(onChange).toHaveBeenCalled();
	});

	it('calls onChange when order button is clicked', () => {
		const onChange = jest.fn();

		const {getByLabelText} = renderSortSelect({
			onChange,
		});

		fireEvent.click(getByLabelText('sort-direction'));

		expect(onChange).toHaveBeenCalled();
	});
});
