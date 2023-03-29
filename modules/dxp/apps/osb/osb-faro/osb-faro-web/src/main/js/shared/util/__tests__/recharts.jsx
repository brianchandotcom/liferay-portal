import React from 'react';
import {
	getAxisTickText,
	getTextWidth,
	getYAxisLabel,
	getYAxisWidth,
	RechartsTooltip
} from '../recharts';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('Recharts Util', () => {
	describe('getTextWidth', () => {
		it('should return text width', () => {
			expect(getTextWidth('test')).toEqual(52);
		});
	});

	describe('getAxisTickText', () => {
		it('should return a function', () => {
			expect(getAxisTickText('x')).toBeFunction();
		});

		it('should render when returned function is called', () => {
			const {container} = render(
				getAxisTickText('x')({
					payload: {offset: 2, value: 4},
					textAnchor: 'middle',
					x: 12,
					y: 12
				})
			);

			expect(container).toMatchSnapshot();
		});
	});

	describe('RechartsTooltip', () => {
		it('should render', () => {
			const {container} = render(
				<RechartsTooltip
					dateTitle='12-12-12'
					rows={[{label: 'test', value: 123}]}
					title='Test Title'
				/>
			);

			expect(container).toMatchSnapshot();
		});
	});

	describe('getYAxisLabel', () => {
		it('should return a function', () => {
			expect(getYAxisLabel('Test')).toBeFunction();
		});

		it('should render when returned function is called', () => {
			const {container} = render(
				getYAxisLabel('Test')({
					viewBox: {
						height: 14,
						width: 26,
						x: 12,
						y: 24
					}
				})
			);

			expect(container).toMatchSnapshot();
		});
	});

	describe('getYAxisWidth', () => {
		it('should max y-axis width', () => {
			expect(
				getYAxisWidth([{title: 'test test'}, {title: 'meow'}], 'title')
			).toBe(112);
		});

		it('should minWidth for y-axis width', () => {
			const minWidth = 60;

			expect(getYAxisWidth([{title: 'test'}], 'title', minWidth)).toBe(
				minWidth
			);
		});
	});
});
