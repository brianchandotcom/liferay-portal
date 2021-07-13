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

import ValidationDate from '../../../src/main/resources/META-INF/resources/Validation/ValidationDate';

const globalLanguageDirection = Liferay.Language.direction;

const parameters = {
	dateRange: [
		{
			label: Liferay.Language.get('starts-from'),
			name: 'startsFrom',
			options: [
				{
					checked: false,
					label: Liferay.Language.get('response-date'),
					name: 'responseDate',
					value: 'responseDate',
				},
			],
		},
		{
			label: Liferay.Language.get('ends-on'),
			name: 'endsOn',
			options: [
				{
					checked: false,
					label: Liferay.Language.get('response-date'),
					name: 'responseDate',
					value: 'responseDate',
				},
			],
		},
	],
	futureDates: [
		{
			label: Liferay.Language.get('starts-from'),
			name: 'startsFrom',
			options: [
				{
					checked: false,
					label: Liferay.Language.get('response-date'),
					name: 'responseDate',
					value: 'responseDate',
				},
			],
		},
	],
	pastDates: [
		{
			label: Liferay.Language.get('ends-on'),
			name: 'endsOn',
			options: [
				{
					checked: false,
					label: Liferay.Language.get('response-date'),
					name: 'responseDate',
					value: 'responseDate',
				},
			],
		},
	],
};

const validations = [
	{
		checked: false,
		label: 'Future Dates',
		name: 'futureDates',
		parameterMessage: '',
		template: 'futureDates({name}, "{parameter}")',
		value: 'futureDates',
	},
	{
		checked: false,
		label: 'Past Dates',
		name: 'pastDates',
		parameterMessage: '',
		template: 'pastDates({name}, "{parameter}")',
		value: 'pastDates',
	},
	{
		checked: false,
		label: 'Range',
		name: 'dateRange',
		parameterMessage: '',
		template: 'dateRange({name}, "{parameter}")',
		value: 'dateRange',
	},
];

describe('ValidationDate', () => {
	beforeAll(() => {
		Liferay.Language.direction = {
			en_US: 'rtl',
		};
	});

	afterAll(() => {
		Liferay.Language.direction = globalLanguageDirection;
	});

	afterEach(cleanup);

	it('shows future dates validation', () => {
		const {container} = render(
			<ValidationDate
				defaultLanguageId="en_US"
				editingLanguageId="en_US"
				localizedValue={() => {}}
				name="validationDate"
				onChange={() => {}}
				parameters={parameters}
				selectedValidation={{
					label: '',
					name: 'futureDates',
					parameterMessage: '',
					template: 'futureDates({name}, "{parameter}")',
				}}
				validations={validations}
				visible={true}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('shows past dates validation', () => {
		const {container} = render(
			<ValidationDate
				defaultLanguageId="en_US"
				editingLanguageId="en_US"
				localizedValue={() => {}}
				name="validationDate"
				onChange={() => {}}
				parameters={parameters}
				selectedValidation={{
					label: '',
					name: 'pastDates',
					parameterMessage: '',
					template: 'pastDates({name}, "{parameter}")',
				}}
				validations={validations}
				visible={true}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('shows date range validation', () => {
		const {container} = render(
			<ValidationDate
				defaultLanguageId="en_US"
				editingLanguageId="en_US"
				localizedValue={() => {}}
				name="validationDate"
				onChange={() => {}}
				parameters={parameters}
				selectedValidation={{
					label: '',
					name: 'dateRange',
					parameterMessage: '',
					template: 'dateRange({name}, "{parameter}")',
				}}
				validations={validations}
				visible={true}
			/>
		);

		expect(container).toMatchSnapshot();
	});
});
