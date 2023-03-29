import AddPropertyForm from '../AddPropertyForm';
import React from 'react';
import {cleanup, render} from '@testing-library/react';

jest.unmock('react-dom');

describe('DistributionCard AddPropertyForm', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<AddPropertyForm />);

		expect(container).toMatchSnapshot();
	});

	it.skip('renders w/ context dropdown', () => {
		const {container} = render(<AddPropertyForm showContext />);

		expect(container.querySelector('.context-select')).toBeTruthy();
	});
});
