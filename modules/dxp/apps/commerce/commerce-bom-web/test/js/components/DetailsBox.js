import React from 'react';
import { shallow, mount } from 'enzyme';

import DetailsBox, {
	DetailsListElement
} from '../../../src/main/resources/META-INF/resources/js/components/areas/DetailsBox.es';

const mockedContext = {
	state: {
		app: {
			spritemap: '/spritemap.test.svg'
		},
		area: {
			highlightedDetail: {
				number: 1
			},
			spots: [
				{
					id: 'SP01',
					number: 1,
					position: {
						x: 0,
						y: 0
					},
					productId: 'PR01'
				},
				{
					id: 'SP02',
					number: 1,
					position: {
						x: 50,
						y: 50
					},
					productId: 'PR01'
				},
				{
					id: 'SP03',
					number: 2,
					position: {
						x: 75,
						y: 75
					},
					productId: 'PR02'
				},
				{
					id: 'SP04',
					number: 3,
					position: {
						x: 75,
						y: 75
					},
					productId: 'PR03'
				}
			],
			products: [
				{
					id: 'PR01',
					name: 'Product 1',
					price: '$ 12.99',
					sku: 'sku01'
				},
				{
					id: 'PR02',
					name: 'Product 2',
					price: '$ 12.99',
					sku: 'sku02'
				},
				{
					id: 'PR03',
					name: 'Product 3',
					price: '$ 12.99',
					sku: 'sku03'
				}
			]
		}
	}
};


describe('Details box', () => {

	jest
		.spyOn(React, "useContext")
		.mockImplementation(() => mockedContext);

	it('renders without crashing', () => {		
		mount(<DetailsBox />);
	});

	it('Should correctly group the products', () => {
		const detailsBox = shallow(<DetailsBox />);
		expect(detailsBox.find(DetailsListElement).length).toBe(3);
	});

	it('Should calculate correctly the products to be displayed', () => {
		const detailsBox = shallow(<DetailsBox />);
		const firstListElementProps = detailsBox.find(DetailsListElement).first().props();
		expect(firstListElementProps.number).toBe(1);
		expect(firstListElementProps.name).toBe('Product 1');
		expect(firstListElementProps.sku).toBe('sku01');
	});
});
