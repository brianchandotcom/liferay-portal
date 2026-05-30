import ErrorPage from '../ErrorPage';
import React from 'react';
import {MemoryRouter} from 'react-router-dom';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

const DefaultComponent = props => (
	<MemoryRouter>
		<ErrorPage {...props} />
	</MemoryRouter>
);

describe('ErrorPage', () => {
	it('should render', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});

	it('should render a custom message', () => {
		const {getByText} = render(<DefaultComponent message='foo bar' />);

		expect(getByText('foo bar')).toBeTruthy();
	});
});
