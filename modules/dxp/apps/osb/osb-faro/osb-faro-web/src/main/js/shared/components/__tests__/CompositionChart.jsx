import CompositionChart, {CompositionLegend} from '../CompositionChart';
import React from 'react';
import {cleanup, render} from '@testing-library/react';

jest.unmock('react-dom');

describe('CompositionChart', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<CompositionChart />);

		expect(container).toBeTruthy();
	});
});

describe('CompositionLegend', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<CompositionLegend
				items={[
					{color: 'blue', label: 'foo', value: 25},
					{color: 'pink', label: 'bar', value: 75}
				]}
				total={100}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render "< 1%" if percentage is less than 1%', () => {
		const {container} = render(
			<CompositionLegend
				items={[
					{color: 'blue', label: 'foo', value: 1},
					{color: 'pink', label: 'bar', value: 1}
				]}
				total={1000}
			/>
		);

		container
			.querySelectorAll('b')
			.forEach(element => expect(element).toHaveTextContent('< 1%'));
	});

	it('should render "0%" if percentage is 0%', () => {
		const {container} = render(
			<CompositionLegend
				items={[
					{color: 'blue', label: 'foo', value: 0},
					{color: 'pink', label: 'bar', value: 0}
				]}
				total={1000}
			/>
		);

		container
			.querySelectorAll('b')
			.forEach(element => expect(element).toHaveTextContent('0%'));
	});
});
