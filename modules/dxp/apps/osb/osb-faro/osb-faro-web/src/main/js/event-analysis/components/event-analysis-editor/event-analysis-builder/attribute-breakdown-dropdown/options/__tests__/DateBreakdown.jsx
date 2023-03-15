import DateBreakdown from '../DateBreakdown';
import React from 'react';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('DateBreakdown', () => {
	it('should render', () => {
		const {container} = render(<DateBreakdown onSubmit={jest.fn()} />);

		expect(container).toMatchSnapshot();
	});
});
