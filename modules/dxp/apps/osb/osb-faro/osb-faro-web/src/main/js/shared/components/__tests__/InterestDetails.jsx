import InterestDetails from '../InterestDetails';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {MemoryRouter, Route} from 'react-router-dom';
import {MockedProvider} from '@apollo/react-testing';
import {mockTouchpointsReq} from 'test/graphql-data';
import {Routes} from 'shared/util/router';
import {waitForLoading} from 'test/helpers';

jest.unmock('react-dom');

const mockItems = [
	{
		__typename: 'PageMetric',
		assetId: 'https://www.liferay.com',
		assetTitle: 'Dashboard - Retail',
		avgTimeOnPageMetric: {
			__typename: 'Metric',
			value: 23
		},
		bounceRateMetric: {
			__typename: 'Metric',
			value: 0.23
		},
		dataSourceId: '123123',
		entrancesMetric: {
			__typename: 'Metric',
			value: 56
		},
		exitRateMetric: {
			__typename: 'Metric',
			value: 0.53
		},
		viewsMetric: {__typename: 'Metric', value: 243.0},
		visitorsMetric: {
			__typename: 'Metric',
			value: 45.0
		}
	}
];

const defaultProps = {
	router: {
		params: {
			channelId: '321321',
			groupId: '23',
			id: '321',
			interestId: 'test'
		}
	}
};

const DefaultComponent = () => (
	<MockedProvider mocks={[mockTouchpointsReq(mockItems, {size: 2})]}>
		<MemoryRouter
			initialEntries={[
				'/workspace/23/321321/contacts/accounts/123123/interests/test'
			]}
		>
			<Route path={Routes.CONTACTS_ACCOUNT_INTEREST_DETAILS}>
				<InterestDetails {...defaultProps} />
			</Route>
		</MemoryRouter>
	</MockedProvider>
);

describe('InterestDetails', () => {
	afterEach(cleanup);

	it('renders', async () => {
		const {container} = render(<DefaultComponent />);

		await waitForLoading(container);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});
