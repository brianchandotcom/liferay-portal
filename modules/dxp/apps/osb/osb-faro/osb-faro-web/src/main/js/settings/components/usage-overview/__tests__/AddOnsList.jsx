import AddOnsList from '../AddOnsList';
import React from 'react';
import {fromJS} from 'immutable';
import {mockPlan} from 'test/data';
import {Plan} from 'shared/util/records';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

const DefaultComponent = props => (
	<AddOnsList
		currentPlan={new Plan(fromJS(mockPlan()))}
		planType='enterprise'
		{...props}
	/>
);

describe('AddOnsList', () => {
	it('should render', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});

	it('should render as inactive when the active prop is false', () => {
		const {container} = render(<DefaultComponent active={false} />);

		expect(container.querySelector('.inactive')).toBeTruthy();
	});

	it('should render with the quantity of addons subscribed if any', () => {
		const {queryByText} = render(<DefaultComponent />);

		expect(queryByText('2X')).toBeTruthy();
		expect(queryByText('1X')).toBeTruthy();
	});
});
