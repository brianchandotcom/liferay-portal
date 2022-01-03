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
	},
});

const parameters = generateParameter();

const localizedValue = jest.fn(() => parameters['en_US']);

const ValidationDateProvider = ({
	formBuilder = {},
	dateFieldTypeValidationEnabled = false,
	state,
	...props
}) => (
	<FormProvider
		initialState={{dateFieldTypeValidationEnabled, formBuilder}}
		value={state}
	>
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
				name="validationDate"
				onChange={() => {}}
				parameter={parameters}
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
				name="validationDate"
				onChange={() => {}}
				parameter={parameters}
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
				defaultLanguageId="en_US"
				editingLanguageId="en_US"
				formBuilder={{pages: []}}
				localizedValue={localizedValue}
				name="validationDate"
				onChange={() => {}}
				parameter={parameters}
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
		const [acceptedDate, operation, quantity, unit] = [
			...getAllByRole('textbox'),
		];

		expect(acceptedDate).toHaveValue('futureDates');
		expect(operation).toHaveValue('plus');
		expect(unit).toHaveValue('days');
		expect(quantity).toHaveValue(1);
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

		const [acceptedDate, operation, quantity, unit] = [
			...getAllByRole('textbox'),
		];

		expect(acceptedDate).toHaveValue('pastDates');
		expect(operation).toHaveValue('minus');
		expect(quantity).toHaveValue(1);
		expect(unit).toHaveValue('days');
	});

	it('shows date field', () => {
		const formBuilder = {
			pages: [
				{
					rows: [
						{
							columns: [
								{
									fields: [
										{
											fieldName: 'Date12345678',
											label: 'Date A',
											type: 'date',
										},
									],
									size: 12,
								},
							],
						},
					],
				},
			],
		};

		const parameter = {
			en_US: {
				endsOn: {
					date: 'responseDate',
					quantity: -1,
					type: 'responseDate',
					unit: 'days',
				},
			},
		};

		const localizedValue = jest.fn(() => parameter['en_US']);

		const {getAllByRole} = render(
			<ValidationDateProvider
				dateFieldTypeValidationEnabled={true}
				defaultLanguageId="en_US"
				editingLanguageId="en_US"
				formBuilder={formBuilder}
				localizedValue={localizedValue}
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

		const lastOption = [...getAllByRole('button')].pop();

		expect(lastOption).toHaveValue('Date12345678');
	});

	it('hides date field if it is repeatable', () => {
		const formBuilder = {
			pages: [
				{
					rows: [
						{
							columns: [
								{
									fields: [
										{
											fieldName: 'Date12345678',
											label: 'Date A',
											repeatable: true,
											type: 'date',
										},
									],
									size: 12,
								},
							],
						},
					],
				},
			],
		};

		const parameter = {
			en_US: {
				endsOn: {
					date: 'responseDate',
					quantity: -1,
					type: 'responseDate',
					unit: 'days',
				},
			},
		};

		const localizedValue = jest.fn(() => parameter['en_US']);

		const {getAllByRole} = render(
			<ValidationDateProvider
				defaultLanguageId="en_US"
				editingLanguageId="en_US"
				formBuilder={formBuilder}
				localizedValue={localizedValue}
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

		const lastOption = [...getAllByRole('button')].pop();

		expect(lastOption).not.toHaveValue('Date12345678');
	});

	it('shows date field from a field group', () => {
		const formBuilder = {
			pages: [
				{
					rows: [
						{
							columns: [
								{
									fields: [
										{
											nestedFields: [
												{
													fieldName: 'childDate',
													type: 'date',
												},
											],
											type: 'fieldGroup',
										},
									],
									size: 12,
								},
							],
						},
					],
				},
			],
		};

		const parameter = {
			en_US: {
				endsOn: {
					date: 'responseDate',
					quantity: -1,
					type: 'responseDate',
					unit: 'days',
				},
			},
		};

		const localizedValue = jest.fn(() => parameter['en_US']);

		const {getAllByRole} = render(
			<ValidationDateProvider
				dateFieldTypeValidationEnabled={true}
				defaultLanguageId="en_US"
				editingLanguageId="en_US"
				formBuilder={formBuilder}
				localizedValue={localizedValue}
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

		const lastOption = [...getAllByRole('button')].pop();

		expect(lastOption).toHaveValue('childDate');
	});

	it('hides date field if from a repeatable field group', () => {
		const formBuilder = {
			pages: [
				{
					rows: [
						{
							columns: [
								{
									fields: [
										{
											nestedFields: [
												{
													fieldName: 'childDate',
													type: 'date',
												},
											],
											repeatable: true,
											type: 'fieldGroup',
										},
									],
									size: 12,
								},
							],
						},
					],
				},
			],
		};

		const parameter = {
			en_US: {
				endsOn: {
					date: 'responseDate',
					quantity: -1,
					type: 'responseDate',
					unit: 'days',
				},
			},
		};

		const localizedValue = jest.fn(() => parameter['en_US']);

		const {getAllByRole} = render(
			<ValidationDateProvider
				dateFieldTypeValidationEnabled={true}
				defaultLanguageId="en_US"
				editingLanguageId="en_US"
				formBuilder={formBuilder}
				localizedValue={localizedValue}
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

		const lastOption = [...getAllByRole('button')].pop();

		expect(lastOption).not.toHaveValue('childDate');
	});

	it('shows date fields inside custom date fields for Past dates and operation minus when quantity is negative', () => {
		const formBuilder = {
			pages: [
				{
					rows: [
						{
							columns: [
								{
									fields: [
										{
											fieldName: 'Date12345678',
											label: 'Date A',
											type: 'date',
										},
									],
									size: 12,
								},
							],
						},
					],
				},
			],
		};

		const parameter = {
			en_US: {
				endsOn: {
					date: 'dateField',
					quantity: -10,
					type: 'customDate',
					unit: 'days',
				},
			},
		};

		const localizedValue = jest.fn(() => parameter['en_US']);
		const {getAllByRole} = render(
			<ValidationDateProvider
				dateFieldTypeValidationEnabled={true}
				defaultLanguageId="en_US"
				editingLanguageId="en_US"
				formBuilder={formBuilder}
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

		const [acceptedDate, operation, quantity, unit] = [
			...getAllByRole('textbox'),
		];

		const availableDates = [...getAllByRole('button')];

		expect(availableDates[7]).toHaveValue('Date12345678');
		expect(acceptedDate).toHaveValue('pastDates');
		expect(operation).toHaveValue('minus');
		expect(quantity).toHaveValue(10);
		expect(unit).toHaveValue('days');
	});
});
