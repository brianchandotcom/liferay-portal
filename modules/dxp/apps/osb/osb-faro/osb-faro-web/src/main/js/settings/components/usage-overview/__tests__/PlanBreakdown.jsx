import PlanBreakdown from '../PlanBreakdown';
import React from 'react';
import {mockAddOns} from 'test/data';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('PlanBreakdown', () => {
	it('should render', () => {
		const {container} = render(<PlanBreakdown addOns={mockAddOns()} />);

		expect(container).toMatchSnapshot();
	});
});
