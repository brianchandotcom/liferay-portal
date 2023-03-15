import React from 'react';
import Tooltip from '../Tooltip';
import {cleanup, fireEvent, render} from '@testing-library/react';

const mockElement = document.createElement('div');

mockElement.getAttribute = jest.fn(name =>
	name === 'title' ? 'Foo Title' : null
);

mockElement.hasAttribute = jest.fn(() => true);
jest.unmock('react-dom');

describe('Tooltip', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<Tooltip />);
		expect(container).toMatchSnapshot();
	});

	it('should display the tooltip on mouseenter', () => {
		const {getByTestId, getByText} = render(
			<div>
				<Tooltip />
				<span data-testid='target' data-tooltip title='foo title' />
			</div>
		);

		fireEvent.mouseEnter(getByTestId('target'));

		jest.runAllTimers();

		expect(getByText('foo title')).toBeTruthy();
	});

	it('should not display the tooltip on mouseleave', () => {
		const {getByTestId, queryByText} = render(
			<div>
				<Tooltip />
				<span data-testid='target' data-tooltip title='foo title' />
			</div>
		);

		fireEvent.mouseEnter(getByTestId('target'));

		jest.runAllTimers();

		expect(queryByText('foo title')).toBeTruthy();

		fireEvent.mouseLeave(getByTestId('target'));

		jest.runAllTimers();

		expect(queryByText('foo title')).toBeNull();
	});
});
