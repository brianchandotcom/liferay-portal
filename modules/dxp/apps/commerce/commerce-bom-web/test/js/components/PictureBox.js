import React from 'react';
import { shallow, mount } from 'enzyme';

import PictureBox, { PartDetail } from '../../../src/main/resources/META-INF/resources/js/components/areas/PictureBox.es';
import areaActions from '../../../src/main/resources/META-INF/resources/js/actions/area.es';
import appActions from '../../../src/main/resources/META-INF/resources/js/actions/app.es';

const mockedContext = {
	actions: Object.assign({}, areaActions, appActions),
	state: {
		app: {
			spritemap: '/spritemap.test.svg'
		},
		area: {
			name: 'test name',
			imageUrl: '/testImg.jpg',
			spotFormData: null,
			highlightedDetail: {
				number: 1
			},
			spots: [
				{
					id: 'SP01',
					number: 1,
					position: {
						x: 75,
						y: 75
					},
					productId: 'PR01'
				},
				{
					id: 'SP02',
					number: 1,
					position: {
						x: 25,
						y: 25
					},
					productId: 'PR01'
				}
			],
			products: [
				{
					id: 'PR01',
					name: 'Product 1',
					price: '$ 12.99',
					sku: 'sku01',
					url: '/test-product',
					thumbnailUrl: '/test-product'
				}
			]
		}
	}
};


describe('PictureBox', () => {

	jest
		.spyOn(React, "useContext")
		.mockImplementation(() => mockedContext);

	it('renders without crashing', () => {		
		mount(<PictureBox />);
	});
	
	it('should display the picture', () => {		
		const pictureBox = shallow(<PictureBox />);
		expect(pictureBox.find(".picture-box__image").prop("src")).toEqual(mockedContext.state.area.imageUrl);
	});

	describe('Spots', () => {
		const pictureBox = mount(<PictureBox />);
		const partDetails = pictureBox.find(PartDetail);

		it('should display the parts details', () => {		
			expect(partDetails.length).toEqual(2);
		});

		it('should receive the correct props', () => {		
			const firstPartDetailProps = partDetails.first().props();

			expect(firstPartDetailProps.id).toEqual('SP01');
			expect(firstPartDetailProps.number).toEqual(1);
			expect(firstPartDetailProps.position).toEqual({x: 75, y: 75});
			expect(firstPartDetailProps.productId).toEqual('PR01');
		});
	});

});
