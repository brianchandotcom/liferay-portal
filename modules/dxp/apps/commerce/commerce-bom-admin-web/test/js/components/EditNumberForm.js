import React from 'react';
import { mount } from 'enzyme';
import EditNumberForm from '../../../src/main/resources/META-INF/resources/js/components/EditNumberForm.es';

const spotFormDataEdit = {
	changed: false,
	id: 'SP01',
	number: 1,
	originalData: {
		changed: false,
		id: 'SP01',
		number: 1,
		originalData: {
	
		},
		position: {
			x: 75,
			y: 75
		},
		productId: 'PR01',
		query: 'Product 1'
	},
	position: {
		x: 75,
		y: 75
	},
	state: 'edit',
	productId: 'PR01',
	query: 'Product 1'
}


const spotFormDataCreate = {
	state: 'create',
	position: {
		x: 50,
		y: 50
	}
}

const mockedContext = {
	state: {
		app: {
			spritemap: '/spritemap.test.svg'
		},
		area: {
			spotFormData: spotFormDataEdit,
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
				}
			],
			availableProducts: [],
			mappedProducts: [
				{
					id: 'PR01',
					name: 'Product 1',
					price: '$ 12.99',
					sku: 'sku01'
				}
			]
		}
	}
};


describe('Edit number form', () => {

	jest
		.spyOn(React, "useContext")
		.mockImplementation(() => mockedContext);

	it('renders without crashing', () => {		
		mount(<EditNumberForm />);
	});

	it('should be empty if spotFormData has no values', () => {	
		jest
			.spyOn(React, "useContext")
			.mockImplementation(
				() => {
					const context = Object.assign(
						{},
						mockedContext
					)
					context.state.area.spotFormData = null;
					return context;
				}
			);
		
		mount(<EditNumberForm />);
	});

	it('should not display a delete button if state !== "edit"', () => {
		jest
			.spyOn(React, "useContext")
			.mockImplementation(
				() => {
					const context = Object.assign(
						{},
						mockedContext
					)
					context.state.area.spotFormData = spotFormDataCreate;
					return context;
				}
			);
		
		const form = mount(<EditNumberForm />);
		expect(form.find('.edit-number-form__delete-btn').length).toBe(0)
	});

	it('should display a delete button if state === "edit"', () => {
		jest
			.spyOn(React, "useContext")
			.mockImplementation(
				() => {
					const context = Object.assign(
						{},
						mockedContext
					)
					context.state.area.spotFormData = spotFormDataEdit;
					return context;
				}
			);

		const form = mount(<EditNumberForm />);
		expect(form.find('.edit-number-form__delete-btn').length).toBe(1)
	});

	it('should be correctly positioned', () => {
		const form = mount(<EditNumberForm />);
		const formStyle = form.find('.edit-number-form-wrapper').getDOMNode().style;
		expect(formStyle.left).toBe('75%');
		expect(formStyle.bottom).toBe('75%');
	});

	describe('Spot element', () => {
		const form = mount(<EditNumberForm />);
		const spot = form.find('.spot-number--placeholder');

		it('should display a spot element', () => {
			expect(spot.length).toBe(1)
		});

		it('should display the associated number', () => {
			expect(spot.text()).toBe('1')
		})		
	})
	
});
