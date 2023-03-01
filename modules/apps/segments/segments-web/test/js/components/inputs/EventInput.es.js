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

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import EventInput from '../../../../src/main/resources/META-INF/resources/js/components/inputs/EventInput';

const HAS_OPERATOR_SELECTOR = 'select-has-operator';

const INTEGER_OPERATOR = 'integer-operator';

const INTEGER_NUMBER = 'integer-number';

const PROPERTY_LABEL = 'Downloaded Document & Media';

const mockEventCriterion = {
	assetId: '603744226255659006',
	day: {
		operatorName: 'eq',
		value: '2023-02-28',
	},
	operatorName: 'le',
	operatorNot: 'true',
	propertyName: 'documentDownloaded',
	value: '2',
};

const mockSelectedProperty = {
	label: 'Downloaded Document & Media',
	name: 'documentDownloaded',
	options: null,
	selectEntity: {
		id: 'selectEntity',
		multiple: true,
		title: 'Select',
		uri:
			'http://localhost:8080/group/control_panel/manage/-/select/file/selectEntity?_com_liferay_item_selector_web_portlet_ItemSelectorPortlet_0_json=%7B%22desiredItemSelectorReturnTypes%22%3A%22fileentry%22%7D&p_p_auth=F6dc9xTK',
	},
	type: 'event',
};

describe('EventInput', () => {
	afterEach(cleanup);

	it('renders Event Input for Documents and Media Events', () => {
		const mockOnChange = jest.fn();
		const mockOnInputChange = jest.fn();

		const {getByTestId} = render(
			<EventInput
				criterion={mockEventCriterion}
				onChange={mockOnChange}
				onInputChange={mockOnInputChange}
				propertyLabel={PROPERTY_LABEL}
				selectedProperty={mockSelectedProperty}
				value={mockEventCriterion.value}
			/>
		);

		expect(getByTestId(HAS_OPERATOR_SELECTOR).value).toBe('has-not');

		expect(getByTestId(INTEGER_OPERATOR).value).toBe(
			mockEventCriterion.operatorName
		);

		expect(getByTestId(INTEGER_NUMBER).value).toBe(
			mockEventCriterion.value
		);
	});

	it('renders Event Input for Documents and Media changing inputs', () => {
		const mockOnChange = jest.fn();
		const mockOnInputChange = jest.fn();

		const {getByTestId} = render(
			<EventInput
				criterion={mockEventCriterion}
				onChange={mockOnChange}
				onInputChange={mockOnInputChange}
				propertyLabel={PROPERTY_LABEL}
				selectedProperty={mockSelectedProperty}
				value={mockEventCriterion.value}
			/>
		);

		const hasSelectorElement = getByTestId(HAS_OPERATOR_SELECTOR);

		fireEvent.click(hasSelectorElement, {
			target: {value: 'has'},
		});

		expect(hasSelectorElement.value).toBe('has');

		const integerSelectorElement = getByTestId(INTEGER_OPERATOR);

		fireEvent.click(integerSelectorElement, {
			target: {value: 'ge'},
		});

		expect(integerSelectorElement.value).toBe('ge');

		const integerInputElement = getByTestId(INTEGER_NUMBER);

		expect(integerInputElement.value).toBe(mockEventCriterion.value);

		fireEvent.click(integerInputElement, {
			target: {value: '3'},
		});

		expect(integerInputElement.value).toBe('3');
	});
});
