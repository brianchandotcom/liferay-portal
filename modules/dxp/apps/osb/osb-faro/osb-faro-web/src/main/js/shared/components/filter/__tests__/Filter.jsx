import Filter from '..';
import React from 'react';
import {fireEvent, render} from '@testing-library/react';
import {noop} from 'lodash';

jest.unmock('react-dom');

const MOCK_ITEMS = [
	{
		hasSearch: true,
		items: [
			{
				category: 'Location',
				checked: false,
				inputType: 'radio',
				label: 'Albania',
				value: '2'
			},
			{
				category: 'Location',
				checked: false,
				inputType: 'radio',
				label: 'Brazil',
				value: '2'
			},
			{
				category: 'Location',
				checked: false,
				inputType: 'radio',
				label: 'Jamaica',
				value: '2'
			},
			{
				category: 'Location',
				checked: false,
				inputType: 'radio',
				label: 'United States',
				value: '2'
			},
			{
				category: 'Location',
				checked: false,
				inputType: 'radio',
				label: 'Portugual',
				value: '2'
			}
		],
		label: 'Location',
		name: 'location',
		value: '156'
	},
	{
		hasSearch: false,
		items: [
			{
				category: 'Devices',
				checked: false,
				inputType: 'radio',
				label: 'Desktop',
				value: '9'
			},
			{
				category: 'Devices',
				checked: false,
				inputType: 'radio',
				label: 'EReader',
				value: '9'
			},
			{
				category: 'Devices',
				checked: false,
				inputType: 'radio',
				label: 'Mobile',
				value: '9'
			},
			{
				category: 'Devices',
				checked: false,
				inputType: 'radio',
				label: 'SmartPhone',
				value: '9'
			},
			{
				category: 'Devices',
				checked: false,
				inputType: 'radio',
				label: 'Tablet',
				value: '9'
			}
		],
		label: 'Devices',
		name: 'devices'
	}
];

describe('Filter', () => {
	it('should render', () => {
		const {container} = render(<Filter items={MOCK_ITEMS} />);

		expect(container).toMatchSnapshot();
	});

	it('should call onClick on handleClickApplyFilter', () => {
		const spy = jest.fn();

		const {getByText} = render(
			<Filter items={MOCK_ITEMS} onChange={spy} />
		);

		fireEvent.click(getByText('Albania'));

		expect(spy).toBeCalled();
	});

	it('should render de Clear Filter Button', () => {
		const {getByText} = render(
			<Filter items={MOCK_ITEMS} onChange={noop} />
		);

		fireEvent.click(getByText('Albania'));
		fireEvent.click(getByText('Phone'));

		expect(getByText('Clear Filter')).toBeTruthy();
	});
});
