import PlanBreakdown from '../PlanBreakdown';
import React from 'react';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('PlanBreakdown', () => {
	it('should render', () => {
		const {container} = render(<PlanBreakdown />);

		expect(container).toMatchSnapshot();
	});
});
