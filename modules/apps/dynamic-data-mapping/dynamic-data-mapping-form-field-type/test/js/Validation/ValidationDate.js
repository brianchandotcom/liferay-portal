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

import '@testing-library/jest-dom/extend-expect';
import {cleanup, render} from '@testing-library/react';
import React from 'react';

import ValidationDate from '../../../src/main/resources/META-INF/resources/Validation/ValidationDate';

const globalLanguageDirection = Liferay.Language.direction;

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

	it('shows custom date fields for Future dates', () => {
		const parameter = {
			en_US: {
				startsFrom: {
					date: 'responseDate',
					quantity: 1,
					type: 'customDate',
					unit: 'days',
				},
			},
		};

		const localizedValue = jest.fn(() => parameter['en_US']);
		const {container} = render(
			<ValidationDate
				defaultLanguageId="en_US"
				editingLanguageId="en_US"
				localizedValue={localizedValue}
				name="validationDate"
				onChange={() => {}}
				parameter={parameter}
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

		const startDate = container.querySelector(
			'select[name="selectedDate_startsFrom_field"]'
		);

		const startOperation = container.querySelector(
			'select[name="selectedOperation_startsFrom_field"]'
		);

		const startQuantity = container.querySelector(
			'input[name="inputedQuantity_startsFrom"]'
		);

		const startUnit = container.querySelector(
			'input[name="selectedUnit_startsFrom"]'
		);

		expect(startDate).toBeInTheDocument();
		expect(startOperation).toBeInTheDocument();
		expect(startOperation.value).toBe('plus');
		expect(startQuantity).toBeInTheDocument();
		expect(startQuantity.value).toBe('1');
		expect(startUnit).toBeInTheDocument();
		expect(startUnit.value).toBe('days');
	});

	it('shows custom date fields for Past dates and operation minus when quantity is negative', () => {
		const parameter = {
			en_US: {
				endsOn: {
					date: 'responseDate',
					quantity: -1,
					type: 'customDate',
					unit: 'days',
				},
			},
		};

		const localizedValue = jest.fn(() => parameter['en_US']);
		const {container} = render(
			<ValidationDate
				defaultLanguageId="en_US"
				editingLanguageId="en_US"
				localizedValue={localizedValue}
				name="validationDate"
				onChange={() => {}}
				parameter={parameter}
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

		const endDate = container.querySelector(
			'select[name="selectedDate_endsOn_field"]'
		);

		const endOperation = container.querySelector(
			'select[name="selectedOperation_endsOn_field"]'
		);

		const endQuantity = container.querySelector(
			'input[name="inputedQuantity_endsOn"]'
		);

		const endUnit = container.querySelector(
			'input[name="selectedUnit_endsOn"]'
		);

		expect(endDate).toBeInTheDocument();
		expect(endOperation).toBeInTheDocument();
		expect(endOperation.value).toBe('minus');
		expect(endQuantity).toBeInTheDocument();
		expect(endUnit).toBeInTheDocument();
	});
});
