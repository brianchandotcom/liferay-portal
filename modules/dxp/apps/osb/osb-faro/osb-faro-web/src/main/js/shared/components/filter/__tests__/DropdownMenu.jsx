import DropdownMenu, {InputItem, OptionItem} from '../DropdownMenu';
import React from 'react';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

const MOCK_ITEMS = [
	{
		items: [
			{
				category: 'category1',
				checked: true,
				inputType: 'radio',
				label: 'label a',
				value: '1'
			},
			{
				category: 'category1',
				checked: false,
				inputType: 'radio',
				label: 'label b',
				value: '2'
			},
			{
				category: 'category1',
				checked: false,
				inputType: 'radio',
				label: 'label c',
				value: '100'
			},
			{
				category: 'category1',
				checked: false,
				inputType: 'radio',
				label: 'label d',
				value: '50'
			}
		],
		label: 'Location',
		name: 'location',
		value: '100'
	},
	{
		items: [
			{
				category: 'category2',
				checked: true,
				inputType: 'radio',
				items: [
					{
						category: 'category2',
						checked: true,
						inputType: 'radio',
						label: 'label a',
						value: '1'
					},
					{
						category: 'category2',
						checked: false,
						inputType: 'radio',
						label: 'label b',
						value: '2'
					},
					{
						category: 'category2',
						checked: false,
						inputType: 'radio',
						label: 'label c',
						value: '100'
					},
					{
						category: 'category2',
						checked: false,
						inputType: 'radio',
						label: 'label d',
						value: '50'
					}
				],
				label: 'label a',
				value: '1'
			},
			{
				category: 'category2',
				checked: false,
				inputType: 'radio',
				label: 'label b',
				value: '2'
			},
			{
				category: 'category2',
				checked: false,
				inputType: 'radio',
				label: 'label c',
				value: '100'
			},
			{
				category: 'category2',
				checked: false,
				inputType: 'radio',
				label: 'label d',
				value: '50'
			}
		],
		label: 'Devices',
		name: 'devices',
		value: '100'
	}
];

const [INDIVIDUAL_ITEM] = MOCK_ITEMS[0].items;

describe('DropdownMenu', () => {
	it('should render', () => {
		const {container} = render(<DropdownMenu />);

		expect(container).toMatchSnapshot();
	});

	it('should render w/ search', () => {
		const {container} = render(<DropdownMenu hasSearch />);

		expect(
			container.querySelector('.analytics-dropdown-menu-search-container')
		).toBeTruthy();
	});

	it('should render w/ items', () => {
		const {getByText} = render(<DropdownMenu items={MOCK_ITEMS} />);

		MOCK_ITEMS.forEach(({label}) => {
			expect(getByText(label)).toBeTruthy();
		});
	});
});

describe('InputItem', () => {
	it('should render', () => {
		const {container} = render(<InputItem item={INDIVIDUAL_ITEM} />);

		expect(container).toMatchSnapshot();
	});
});

describe('OptionItem', () => {
	it('should render', () => {
		const {container} = render(<OptionItem item={INDIVIDUAL_ITEM} />);

		expect(container).toMatchSnapshot();
	});
});
