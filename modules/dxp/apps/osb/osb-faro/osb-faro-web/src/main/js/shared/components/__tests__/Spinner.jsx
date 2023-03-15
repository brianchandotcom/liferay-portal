import React from 'react';
import Spinner from '../Spinner';
import {cleanup, render} from '@testing-library/react';

jest.unmock('react-dom');

describe('Spinner', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<Spinner />);
		expect(container).toMatchSnapshot();
	});

	it('should render with a different size', () => {
		const {container} = render(<Spinner size='sm' />);
		expect(container.querySelector('.loading-animation-sm')).toBeTruthy();
	});

	it('should render with fade in', () => {
		const {container} = render(<Spinner fadeIn />);
		expect(container.querySelector('.spinner-fade-in')).toBeTruthy();
	});
});
