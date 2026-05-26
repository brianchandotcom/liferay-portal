import React from 'react';
import {fireEvent, render, waitFor} from '@testing-library/react';
import {GeomapCard} from '../GeomapCard';

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
		const {container} = render(<GeomapCard {...props} />);
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
			<GeomapCard
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
		const {container} = render(<GeomapCard {...props} />);

		const firstRow = container.querySelector(
			'.analytics-geomap-table > tbody > tr'
		);

		fireEvent.mouseOver(firstRow);

		jest.runAllTimers();

		expect(container.querySelector('.lighten-item')).toBeTruthy();
	});

	it('should keep the tooltip hidden on first render', () => {
		const {container} = render(
			<GeomapCard {...props} metricLabel='sessions' />
		);

		const tooltip = container.querySelector('.popover');

		expect(tooltip).toBeTruthy();
		expect(tooltip.style.display).toBe('none');
	});

	it('should show the tooltip with country information on mouseover', async () => {
		const {container} = render(
			<GeomapCard {...props} metricLabel='sessions' />
		);

		const paths = container.querySelectorAll('.analytics-geomap svg path');

		const brazilPath = Array.from(paths).find(
			path => path.__data__?.properties?.name === 'Brazil'
		);

		expect(brazilPath).toBeTruthy();

		const tooltip = container.querySelector('.popover');

		expect(tooltip.style.display).toBe('none');

		fireEvent.mouseOver(brazilPath);

		await waitFor(() => {
			expect(tooltip.style.display).toBe('');
		});

		expect(tooltip).toHaveTextContent('Brazil');
		expect(tooltip).toHaveTextContent('6274');
		expect(tooltip).toHaveTextContent('sessions');
		expect(tooltip).toHaveTextContent('34.3%');
	});

	it('should hide the tooltip on mouseout', async () => {
		const {container} = render(
			<GeomapCard {...props} metricLabel='sessions' />
		);

		const paths = container.querySelectorAll('.analytics-geomap svg path');

		const brazilPath = Array.from(paths).find(
			path => path.__data__?.properties?.name === 'Brazil'
		);

		const tooltip = container.querySelector('.popover');

		fireEvent.mouseOver(brazilPath);

		await waitFor(() => {
			expect(tooltip.style.display).toBe('');
		});

		fireEvent.mouseOut(brazilPath);

		expect(tooltip.style.display).toBe('none');
	});
});
