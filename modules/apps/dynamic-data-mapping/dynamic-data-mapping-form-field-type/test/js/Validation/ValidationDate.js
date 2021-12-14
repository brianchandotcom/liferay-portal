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
import {FormProvider} from 'data-engine-js-components-web';
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

const generateParameter = () => ({
	en_US: {
		endsOn: {
			date: 'responseDate',
			dateFieldName: 'Date1234',
			quantity: 1,
			type: 'customDate',
			unit: 'days',
		},
		startsFrom: {
			date: 'responseDate',
			dateFieldName: 'Date1234',
			quantity: 1,
			type: 'customDate',
			unit: 'days',
	},
}

})

const parameters = generateParameter();

const localizedValue = jest.fn(() => parameters['en_US']);

const ValidationDateProvider = ({builderPages = [], ...props}) => (
	<FormProvider initialState={{builderPages}}>
		<ValidationDate {...props} />
	</FormProvider>
);

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
			<ValidationDateProvider
				defaultLanguageId="en_US"
				editingLanguageId="en_US"
				localizedValue={localizedValue}
				parameter={parameters}
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
			<ValidationDateProvider
				defaultLanguageId="en_US"
				editingLanguageId="en_US"
				localizedValue={localizedValue}
				parameter={parameters}
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
			<ValidationDateProvider
				builderPages={[]}
				defaultLanguageId="en_US"
				editingLanguageId="en_US"
				localizedValue={localizedValue}
				parameter={parameters}
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
		const {getAllByRole} = render(
			<ValidationDateProvider
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

		const [selectedValidaiton, selectedOperation, selectedUnit] = [...getAllByRole('combobox')];

		const startQuantity = getAllByRole('textbox')

		expect(selectedValidaiton).toHaveValue('futureDates');
		expect(selectedOperation).toHaveValue('plus');
		expect(selectedUnit).toHaveValue('days');
		expect(startQuantity[0]).toHaveValue(1);

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
		const {getAllByRole} = render(
			<ValidationDateProvider
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

		const [acceptedDate, operation, unit] = [...getAllByRole('listbox')];

		const [quantity] = [...getAllByRole('textbox')];

		expect(acceptedDate).toHaveValue('pastDates');
		expect(operation).toHaveValue('minus');
		expect(quantity).toHaveValue(1);
		expect(unit).toHaveValue('days');
	});
});
