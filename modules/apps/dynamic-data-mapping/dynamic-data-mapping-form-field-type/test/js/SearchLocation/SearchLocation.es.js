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
import {cleanup, fireEvent, render} from '@testing-library/react';
import {FormProvider} from 'data-engine-js-components-web';
import React from 'react';

import SearchLocation from '../../../src/main/resources/META-INF/resources/SearchLocation/SearchLocation.es';

const globalLanguageDirection = Liferay.Language.direction;

const SearchLocationWithProvider = (props) => (
	<FormProvider value={{editingLanguageId: 'en_US', viewMode: true}}>
		<SearchLocation {...props} />
	</FormProvider>
);

const defaultConfig = {
	googlePlacesAPIKey: '',
	label: {
		en_US: 'Search Location',
	},
	labels: {
		address: 'Address',
		city: 'City',
		country: 'Country',
		['postal-code']: 'Postal Code',
		state: 'State',
	},
	layout: ['one-column'],
	name: 'test_search_location_field',
	onBlur: jest.fn(),
	onChange: jest.fn(),
	readOnly: false,
	type: 'search_location',
	visibleFields: ['address', 'city', 'country', 'postal-code', 'state'],
};

const hasAllFields = (getAllByLabelText, labels) => {
	Object.values(labels).forEach((label) => {
		const allByLabelText = getAllByLabelText(label);
		expect(allByLabelText).toHaveLength(2);
		if (!allByLabelText[1]) {
			return false;
		}
	});

	return true;
};

const hasAllFieldsInCorrectOrder = (
	correctFieldsOrderByName,
	elementFields
) => {
	let isInOrder = true;
	const elementFieldNames = [];

	for (const element of elementFields) {
		const elementName = element.getAttribute('name');
		if (!elementName.includes('_edited') && elementName.includes('#')) {
			elementFieldNames.push(elementName);
		}
	}

	correctFieldsOrderByName.forEach((name, index) => {
		const elementName = elementFieldNames[index];
		if (elementName !== name) {
			isInOrder = false;
		}
	});

	return isInOrder;
};

describe('Field Search Location', () => {
	beforeAll(() => {
		Liferay.Language.direction = {
			en_US: 'rtl',
		};

		window.google = {
			maps: {
				event: {
					removeListener: jest.fn(),
				},
				places: {
					Autocomplete: class {},
				},
			},
		};
	});

	afterAll(() => {
		Liferay.Language.direction = globalLanguageDirection;
	});

	afterEach(cleanup);

	it('must to be show search location fields', () => {
		const {getAllByLabelText} = render(
			<SearchLocationWithProvider {...defaultConfig} />
		);

		expect(hasAllFields(getAllByLabelText, defaultConfig.labels)).toBe(
			true
		);
	});

	it('must to be show search location fields in correct order', () => {
		const {container} = render(
			<SearchLocationWithProvider {...defaultConfig} />
		);

		const correctFieldsOrderByName = [
			'test_search_location_field#place',
			'test_search_location_field#address',
			'test_search_location_field#city',
			'test_search_location_field#country',
			'test_search_location_field#postal-code',
			'test_search_location_field#state',
		];

		const renderedFields = container.getElementsByTagName('input');

		expect(
			hasAllFieldsInCorrectOrder(correctFieldsOrderByName, renderedFields)
		).toBe(true);
	});

	it('must to be reflect the visible fields settings - remove field', () => {
		delete defaultConfig.labels.city;
		defaultConfig.visibleFields.splice(1, 1);

		const {getAllByLabelText} = render(
			<SearchLocationWithProvider {...defaultConfig} />
		);

		expect(hasAllFields(getAllByLabelText, defaultConfig.labels)).toBe(
			true
		);
	});

	it('must to be show search location fields in correct order - remove field', () => {
		const {container} = render(
			<SearchLocationWithProvider {...defaultConfig} />
		);

		const correctFieldsOrderByName = [
			'test_search_location_field#place',
			'test_search_location_field#address',
			'test_search_location_field#country',
			'test_search_location_field#postal-code',
			'test_search_location_field#state',
		];

		const renderedFields = container.getElementsByTagName('input');

		expect(
			hasAllFieldsInCorrectOrder(correctFieldsOrderByName, renderedFields)
		).toBe(true);
	});

	it('must to be reflect the visible fields settings - add field', () => {
		defaultConfig.labels.city = 'City';
		defaultConfig.visibleFields.splice(1, 0, 'city');

		const {getAllByLabelText} = render(
			<SearchLocationWithProvider {...defaultConfig} />
		);

		expect(hasAllFields(getAllByLabelText, defaultConfig.labels)).toBe(
			true
		);
	});

	it('must to be show search location fields in correct order - add field', () => {
		const {container} = render(
			<SearchLocationWithProvider {...defaultConfig} />
		);

		const correctFieldsOrderByName = [
			'test_search_location_field#place',
			'test_search_location_field#address',
			'test_search_location_field#city',
			'test_search_location_field#country',
			'test_search_location_field#postal-code',
			'test_search_location_field#state',
		];

		const renderedFields = container.getElementsByTagName('input');

		expect(
			hasAllFieldsInCorrectOrder(correctFieldsOrderByName, renderedFields)
		).toBe(true);
	});

	it('must to be reflect the one column layout settings', () => {
		const {container} = render(
			<SearchLocationWithProvider {...defaultConfig} />
		);

		const fieldsWithLayoutBehavior = container.getElementsByClassName(
			'col-md-12'
		);

		expect(fieldsWithLayoutBehavior.length).toBe(
			defaultConfig.visibleFields.length
		);
	});

	it('must to be reflect the two columns layout settings', () => {
		defaultConfig.layout = ['two-columns'];
		const {container} = render(
			<SearchLocationWithProvider {...defaultConfig} />
		);

		const fieldsWithLayoutBehavior = container.getElementsByClassName(
			'col-md-6'
		);

		expect(fieldsWithLayoutBehavior.length).toBe(
			defaultConfig.visibleFields.length - 1
		);
	});

	it('must to be reflect the visible fields settings - remove field - layout changed', () => {
		delete defaultConfig.labels.city;
		defaultConfig.visibleFields.splice(1, 1);

		const {getAllByLabelText} = render(
			<SearchLocationWithProvider {...defaultConfig} />
		);

		expect(hasAllFields(getAllByLabelText, defaultConfig.labels)).toBe(
			true
		);
	});

	it('must to be show search location fields in correct order - remove field - layout changed', () => {
		const {container} = render(
			<SearchLocationWithProvider {...defaultConfig} />
		);

		const correctFieldsOrderByName = [
			'test_search_location_field#place',
			'test_search_location_field#address',
			'test_search_location_field#country',
			'test_search_location_field#postal-code',
			'test_search_location_field#state',
		];

		const renderedFields = container.getElementsByTagName('input');

		expect(
			hasAllFieldsInCorrectOrder(correctFieldsOrderByName, renderedFields)
		).toBe(true);
	});

	it('must to be reflect the visible fields settings - add field - layout changed', () => {
		defaultConfig.labels.city = 'City';
		defaultConfig.visibleFields.splice(1, 0, 'city');

		const {getAllByLabelText} = render(
			<SearchLocationWithProvider {...defaultConfig} />
		);

		expect(hasAllFields(getAllByLabelText, defaultConfig.labels)).toBe(
			true
		);
	});

	it('must to be show search location fields in correct order - add field - layout changed', () => {
		const {container} = render(
			<SearchLocationWithProvider {...defaultConfig} />
		);

		const correctFieldsOrderByName = [
			'test_search_location_field#place',
			'test_search_location_field#address',
			'test_search_location_field#city',
			'test_search_location_field#country',
			'test_search_location_field#postal-code',
			'test_search_location_field#state',
		];

		const renderedFields = container.getElementsByTagName('input');

		expect(
			hasAllFieldsInCorrectOrder(correctFieldsOrderByName, renderedFields)
		).toBe(true);
	});

	it('must to be append google dropdown places script', async () => {
		const {getAllByLabelText} = await render(
			<SearchLocationWithProvider {...defaultConfig} />
		);

		const searchLocationField = getAllByLabelText('Search Location');
		expect(searchLocationField).toHaveLength(2);
		const searchLocationFieldTagName = searchLocationField[1].getElementsByTagName(
			'script'
		);
		expect(searchLocationFieldTagName).toHaveLength(1);
		const googlePlacesScriptElement = searchLocationFieldTagName.item(0);

		expect(!!googlePlacesScriptElement).toBe(true);
	});

	it('shows error message only for empty fields that triggered the onBlur or onChange event', () => {
		const {container} = render(
			<SearchLocationWithProvider
				{...defaultConfig}
				displayErrors={true}
				errorMessage="This field is required."
				valid={false}
				value='{"address": "Address"}'
			/>
		);

		const address = container.querySelector(
			'input[name="test_search_location_field#address"]'
		);

		fireEvent.blur(address);

		const city = container.querySelector(
			'input[name="test_search_location_field#city"]'
		);

		fireEvent.change(city, {
			target: {
				value: 'City',
			},
		});

		const country = container.querySelector(
			'input[name="test_search_location_field#country"]'
		);

		fireEvent.blur(country);

		expect(
			container.getElementsByClassName('form-feedback-group').length
		).toBe(2);
	});

	it('shows error message for all empty fields if page validation failed', () => {
		const {container} = render(
			<SearchLocationWithProvider
				{...defaultConfig}
				displayErrors={true}
				errorMessage="This field is required."
				pageValidationFailed={true}
				valid={false}
				value='{"address": "Address"}'
			/>
		);

		expect(
			container.getElementsByClassName('form-feedback-group').length
		).toBe(5);
	});
});
