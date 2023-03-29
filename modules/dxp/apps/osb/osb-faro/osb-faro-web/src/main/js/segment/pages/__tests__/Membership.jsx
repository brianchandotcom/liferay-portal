import * as data from 'test/data';
import Membership, {MembershipChart} from '../Membership';
import mockStore from 'test/mock-store';
import React from 'react';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';
import {Segment} from 'shared/util/records';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

const defaultProps = {
	channelId: '123',
	groupId: '23',
	growthHistory: {data: []},
	id: '321',
	segment: data.getImmutableMock(Segment, data.mockSegment),
	timeZoneId: 'UTC'
};

describe('Membership', () => {
	const WrappedComponent = props => (
		<Provider store={mockStore()}>
			<StaticRouter>
				<Membership {...defaultProps} {...props} />
			</StaticRouter>
		</Provider>
	);

	it('should render', () => {
		const {container} = render(<WrappedComponent />);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});

describe('MembershipChart', () => {
	const WrappedComponent = props => (
		<StaticRouter>
			<MembershipChart {...defaultProps} {...props} />
		</StaticRouter>
	);

	it('should render', () => {
		const {container} = render(<WrappedComponent />);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});
