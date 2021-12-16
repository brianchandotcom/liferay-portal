/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import FormReport from '../../../../src/main/resources/META-INF/resources/js/custom/form-report';

const props = {
	data:
		'{"field1":{"type":"radio","values":{"option1":2,"option2":1}},"field2":{"type":"radio","values":{"option1":2,"option2":1}}}',
	fields: [
		{
			label: 'Field 1',
			name: 'field1',
			options: {option1: 'Option 1'},
			type: 'radio',
		},
		{
			label: 'Field 2',
			name: 'field2',
			options: {option2: 'Option 2'},
			type: 'radio',
		},
		{label: 'Field 3', name: 'field3', type: 'radio'},
	],
};

describe('FormReport', () => {
	afterEach(cleanup);

	it('renders a card for each field', () => {
		const {container} = render(<FormReport {...props} />);

		expect(container.querySelectorAll('.card').length).toBe(
			props.fields.length
		);
	});
});
