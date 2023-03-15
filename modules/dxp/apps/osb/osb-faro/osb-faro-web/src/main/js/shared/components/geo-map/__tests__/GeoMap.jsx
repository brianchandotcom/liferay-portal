import GeoLocation from '../GeoMapCard';
import React from 'react';
import {fireEvent, render} from '@testing-library/react';

jest.unmock('react-dom');

const countries = [
	{
		group: 'United States',
		id: 'United States',
		name: 'United States',
		total: 6911,
		value: '37.7'
	},
	{
		group: 'Brazil',
		id: 'Brazil',
		name: 'Brazil',
		total: 6274,
		value: '34.3'
	},
	{
		group: 'India',
		id: 'India',
		name: 'India',
		total: 574,
		value: '3.1'
	},
	{
		group: 'Spain',
		id: 'Spain',
		name: 'Spain',
		total: 490,
		value: '2.7'
	},
	{
		group: 'Italy',
		id: 'Italy',
		name: 'Italy',
		total: 463,
		value: '2.5'
	},
	{
		color: '#CCCCCC',
		group: 'Others',
		id: 'others',
		name: 'Others',
		total: 3603,
		value: '19.7'
	}
];

const props = {
	data: {
		countries,
		total: countries.length
	},
	filters: {},
	loading: false
};
describe('GeoMapCard', () => {
	it('should render', () => {
		const {container} = render(<GeoLocation {...props} />);
		expect(container).toMatchSnapshot();
	});

	it('should render component when working on Local Network', () => {
		const dataWithLocalNetwork = [
			{
				group: 'Local Network',
				id: 'Local Network',
				name: 'Local Network',
				total: 1,
				value: '100'
			},
			...countries
		];

		const {getByText} = render(
			<GeoLocation
				{...props}
				data={{
					countries: dataWithLocalNetwork,
					total: dataWithLocalNetwork.length
				}}
			/>
		);

		expect(getByText('Local Network')).toBeTruthy();
	});

	it('should highlight the list item when mouse over', () => {
		const {container} = render(<GeoLocation {...props} />);

		const firstRow = container.querySelector(
			'.analytics-geomap-table > tbody > tr'
		);

		fireEvent.mouseOver(firstRow);

		jest.runAllTimers();

		expect(container.querySelector('.lighten-item')).toBeTruthy();
	});
});
