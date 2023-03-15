import NumberBreakdown from '../NumberBreakdown';
import React from 'react';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('NumberBreakdown', () => {
	it('should render', () => {
		const {container} = render(<NumberBreakdown onSubmit={jest.fn()} />);

		expect(container).toMatchSnapshot();
	});
});
