import client from 'shared/apollo/client';
import mockStore from 'test/mock-store';
import React from 'react';
import {ApolloProvider} from '@apollo/react-components';
import {cleanup, fireEvent, render} from '@testing-library/react';
import {createMemoryHistory} from 'history';
import {DropdownRangeKey} from '../DropdownRangeKey';
import {MockedProvider} from '@apollo/react-testing';
import {mockPreferenceReq, mockTimeRangeReq} from 'test/graphql-data';
import {Provider} from 'react-redux';
import {Router} from 'react-router-dom';
import {SEVEN_MONTHS} from 'shared/util/constants';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

jest.mock('shared/hooks/useTimeZone', () => ({
	useTimeZone: () => ({
		timeZoneId: 'UTC'
	})
}));

const WrapperComponent = ({children, retentionPeriodTimeRange}) => {
	const history = createMemoryHistory();

	return (
		<ApolloProvider client={client}>
			<Router history={history}>
				<Provider store={mockStore()}>
					<MockedProvider
						mocks={[
							mockTimeRangeReq(),
							mockPreferenceReq(retentionPeriodTimeRange)
						]}
					>
						{children}
					</MockedProvider>
				</Provider>
			</Router>
		</ApolloProvider>
	);
};

describe('DropdownRangeKey', () => {
	afterEach(cleanup);

	it('should render', async () => {
		const {container} = render(
			<WrapperComponent>
				<DropdownRangeKey />
			</WrapperComponent>
		);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('should display a message with retention period for 13 months on date picker', async () => {
		const {container, getByTestId, getByText} = render(
			<WrapperComponent>
				<DropdownRangeKey legacy={false} />
			</WrapperComponent>
		);

		await waitForLoadingToBeRemoved(container);

		fireEvent.click(getByText(/custom range/i));

		const previousMonthButton = getByTestId('previous-month');

		// Expect to disable prev button when month is 14

		for (let month = 1; month < 14; month++) {
			expect(previousMonthButton).not.toBeDisabled();

			if (month === 14) {
				expect(previousMonthButton).toBeDisabled();
			}

			fireEvent.click(previousMonthButton);
		}

		expect(
			getByText(
				"Dates prior to 13 months cannot be selected due to your workspace's data retention period."
			)
		).toBeInTheDocument();
	});

	it('should display a message with retention period for 7 months on date picker', async () => {
		const {container, getByTestId, getByText} = render(
			<WrapperComponent retentionPeriodTimeRange={SEVEN_MONTHS}>
				<DropdownRangeKey legacy={false} />
			</WrapperComponent>
		);

		await waitForLoadingToBeRemoved(container);

		fireEvent.click(getByText(/custom range/i));

		const previousMonthButton = getByTestId('previous-month');

		// Expect to disable prev button when month is 8

		for (let month = 1; month < 8; month++) {
			expect(previousMonthButton).not.toBeDisabled();

			if (month === 8) {
				expect(previousMonthButton).toBeDisabled();
			}

			fireEvent.click(previousMonthButton);
		}
		expect(
			getByText(
				"Dates prior to 7 months cannot be selected due to your workspace's data retention period."
			)
		).toBeInTheDocument();
	});
});
