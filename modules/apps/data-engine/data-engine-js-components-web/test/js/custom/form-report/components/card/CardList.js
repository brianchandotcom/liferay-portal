/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import CardList from '../../../../../../src/main/resources/META-INF/resources/js/custom/form-report/components/card/CardList';

const props = {
	data: {
		field1: {
			totalEntries: 1,
			type: 'text',
			values: [{formInstanceRecordId: 1, value: 'name'}],
		},
		field2: {
			totalEntries: 1,
			type: 'radio',
			values: {option1: 1},
		},
		field3: {
			type: 'checkbox_multiple',
			values: {option1: 1},
		},
		field4: {
			type: 'select',
			values: {option1: 1},
		},
		field5: {
			type: 'date',
			values: {option1: 1},
		},
		field6: {
			type: 'numeric',
			values: {option1: 1},
		},
		totalEntries: 6,
	},
	fields: [
		{
			label: 'Field 1',
			name: 'field1',
			options: {Option: 'Option'},
			type: 'text',
		},
		{
			label: 'Field 2',
			name: 'field2',
			options: {option1: 'option1'},
			type: 'radio',
		},
		{
			label: 'Field 3',
			name: 'field3',
			options: {option1: 'option1'},
			type: 'checkbox_multiple',
		},
		{
			label: 'Field 4',
			name: 'field4',
			options: {option1: 'option1'},
			type: 'select',
		},
		{
			label: 'Field 5',
			name: 'field5',
			options: {option: 'option'},
			type: 'date',
		},
		{
			label: 'Field 6',
			name: 'field6',
			options: {option: 'option'},
			type: 'numeric',
		},
	],
};

describe('CardList', () => {
	afterEach(cleanup);

	it('shows the card of each field', () => {
		const {getByText} = render(<CardList {...props} />);

		expect(getByText('Field 1')).toBeTruthy();
		expect(getByText('Field 2')).toBeTruthy();
		expect(getByText('Field 3')).toBeTruthy();
		expect(getByText('Field 4')).toBeTruthy();
		expect(getByText('Field 5')).toBeTruthy();
		expect(getByText('Field 6')).toBeTruthy();
	});
});
