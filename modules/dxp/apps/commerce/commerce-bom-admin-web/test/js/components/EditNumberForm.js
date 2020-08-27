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

import {mount} from 'enzyme';
import React from 'react';

import EditNumberForm from '../../../src/main/resources/META-INF/resources/js/components/EditNumberForm.es';

const spotFormDataEdit = {
	changed: false,
	id: 'SP01',
	number: 1,
	originalData: {
		changed: false,
		id: 'SP01',
		number: 1,
		originalData: {},
		position: {
			x: 75,
			y: 75,
		},
		productId: 'PR01',
		query: 'Product 1',
	},
	position: {
		x: 75,
		y: 75,
	},
	productId: 'PR01',
	query: 'Product 1',
	state: 'edit',
};

const spotFormDataCreate = {
	position: {
		x: 50,
		y: 50,
	},
	state: 'create',
};

const mockedContext = {
	state: {
		app: {
			spritemap: '/spritemap.test.svg',
		},
		area: {
			availableProducts: [],
			highlightedDetail: {
				number: 1,
			},
			mappedProducts: [
				{
					id: 'PR01',
					name: 'Product 1',
					price: '$ 12.99',
					sku: 'sku01',
				},
			],
			spotFormData: spotFormDataEdit,
			spots: [
				{
					id: 'SP01',
					number: 1,
					position: {
						x: 75,
						y: 75,
					},
					productId: 'PR01',
				},
			],
		},
	},
};

describe('Edit number form', () => {
	jest.spyOn(React, 'useContext').mockImplementation(() => mockedContext);

	it('renders without crashing', () => {
		mount(<EditNumberForm />);
	});

	it('is empty if spotFormData has no values', () => {
		jest.spyOn(React, 'useContext').mockImplementation(() => {
			const context = {
				...mockedContext,
			};
			context.state.area.spotFormData = null;

			return context;
		});

		mount(<EditNumberForm />);
	});

	it('do not display a delete button if state !== "edit"', () => {
		jest.spyOn(React, 'useContext').mockImplementation(() => {
			const context = {
				...mockedContext,
			};
			context.state.area.spotFormData = spotFormDataCreate;

			return context;
		});

		const form = mount(<EditNumberForm />);
		expect(form.find('.edit-number-form__delete-btn').length).toBe(0);
	});

	it('display a delete button if state === "edit"', () => {
		jest.spyOn(React, 'useContext').mockImplementation(() => {
			const context = {
				...mockedContext,
			};
			context.state.area.spotFormData = spotFormDataEdit;

			return context;
		});

		const form = mount(<EditNumberForm />);
		expect(form.find('.edit-number-form__delete-btn').length).toBe(1);
	});

	it('be correctly positioned', () => {
		const form = mount(<EditNumberForm />);
		const formStyle = form.find('.edit-number-form-wrapper').getDOMNode()
			.style;
		expect(formStyle.left).toBe('75%');
		expect(formStyle.bottom).toBe('75%');
	});

	describe('Spot element', () => {
		const form = mount(<EditNumberForm />);
		const spot = form.find('.spot-number--placeholder');

		it('display a spot element', () => {
			expect(spot.length).toBe(1);
		});

		it('display the associated number', () => {
			expect(spot.text()).toBe('1');
		});
	});
});
