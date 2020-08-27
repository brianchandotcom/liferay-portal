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

import {mount, shallow} from 'enzyme';
import React from 'react';

import DetailsBox, {
	DetailsListElement,
} from '../../../src/main/resources/META-INF/resources/js/components/areas/DetailsBox.es';

const mockedContext = {
	state: {
		app: {
			spritemap: '/spritemap.test.svg',
		},
		area: {
			highlightedDetail: {
				number: 1,
			},
			products: [
				{
					id: 'PR01',
					name: 'Product 1',
					price: '$ 12.99',
					sku: 'sku01',
				},
				{
					id: 'PR02',
					name: 'Product 2',
					price: '$ 12.99',
					sku: 'sku02',
				},
				{
					id: 'PR03',
					name: 'Product 3',
					price: '$ 12.99',
					sku: 'sku03',
				},
			],
			spots: [
				{
					id: 'SP01',
					number: 1,
					position: {
						x: 0,
						y: 0,
					},
					productId: 'PR01',
				},
				{
					id: 'SP02',
					number: 1,
					position: {
						x: 50,
						y: 50,
					},
					productId: 'PR01',
				},
				{
					id: 'SP03',
					number: 2,
					position: {
						x: 75,
						y: 75,
					},
					productId: 'PR02',
				},
				{
					id: 'SP04',
					number: 3,
					position: {
						x: 75,
						y: 75,
					},
					productId: 'PR03',
				},
			],
		},
	},
};

describe('Details box', () => {
	jest.spyOn(React, 'useContext').mockImplementation(() => mockedContext);

	it('renders without crashing', () => {
		mount(<DetailsBox />);
	});

	it('group the products', () => {
		const detailsBox = shallow(<DetailsBox />);
		expect(detailsBox.find(DetailsListElement).length).toBe(3);
	});

	it('calculate correctly the products to be displayed', () => {
		const detailsBox = shallow(<DetailsBox />);
		const firstListElementProps = detailsBox
			.find(DetailsListElement)
			.first()
			.props();
		expect(firstListElementProps.number).toBe(1);
		expect(firstListElementProps.name).toBe('Product 1');
		expect(firstListElementProps.sku).toBe('sku01');
	});
});
