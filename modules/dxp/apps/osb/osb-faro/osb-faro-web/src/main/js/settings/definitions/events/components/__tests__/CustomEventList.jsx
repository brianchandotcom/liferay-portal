import * as API from 'shared/api';
import * as data from 'test/data';
import * as NotificationAlertList from 'shared/components/NotificationAlertList';
import client from 'shared/apollo/client';
import CustomEventList from '../CustomEventList';
import mockStore from 'test/mock-store';
import React from 'react';
import {ApolloProvider} from '@apollo/react-components';
import {cleanup, render} from '@testing-library/react';
import {MemoryRouter, Route} from 'react-router-dom';
import {MockedProvider} from '@apollo/react-testing';
import {mockEventDefinitionsReq} from 'test/graphql-data';
import {NotificationSubtypes} from 'shared/util/records/Notification';
import {Provider} from 'react-redux';
import {range} from 'lodash';
import {Routes} from 'shared/util/router';
import {useCurrentUser} from 'shared/hooks/useCurrentUser';
import {waitForLoading} from 'test/helpers';

jest.unmock('react-dom');

jest.mock('shared/hooks/useCurrentUser', () => ({
	useCurrentUser: jest.fn()
}));

const mockNotificationAlertList = NotificationAlertList;

const WrappedComponent = props => (
	<ApolloProvider client={client}>
		<Provider store={mockStore()}>
			<MemoryRouter
				initialEntries={[
					'/workspace/23/settings/definitions/events/custom?delta=1'
				]}
			>
				<Route path={Routes.SETTINGS_DEFINITIONS_EVENTS_CUSTOM}>
					<MockedProvider
						mocks={[
							mockEventDefinitionsReq(
								[
									data.mockEventDefinition(0, {
										__typename: 'EventDefinition',
										type: 'CUSTOM'
									})
								],
								{
									blocked: false,
									eventType: 'CUSTOM'
								}
							)
						]}
					>
						<CustomEventList groupId='23' {...props} />
					</MockedProvider>
				</Route>
			</MemoryRouter>
		</Provider>
	</ApolloProvider>
);

describe('CustomEventList', () => {
	afterAll(cleanup);

	it('should render', async () => {
		useCurrentUser.mockImplementation(() => ({isAdmin: () => true}));

		const {container, queryByTestId} = render(<WrappedComponent />);

		await waitForLoading(container);

		jest.runAllTimers();

		expect(queryByTestId('select-all-checkbox')).toBeTruthy();
		expect(container).toMatchSnapshot();
	});

	it('should render w/o select all checkbox if user is not an admin', async () => {
		useCurrentUser.mockImplementation(() => ({isAdmin: () => false}));

		API.user.fetchCurrentUser.mockReturnValueOnce(
			Promise.resolve(data.mockMemberUser('23'))
		);

		const {container, queryByTestId} = render(<WrappedComponent />);

		await waitForLoading(container);

		jest.runAllTimers();

		expect(queryByTestId('select-all-checkbox')).toBeNull();
	});

	it('should render alert notification', async () => {
		useCurrentUser.mockImplementation(() => ({isAdmin: () => true}));

		mockNotificationAlertList.useNotificationsAPI = jest.fn(() => ({
			data: range(1).map(i =>
				data.mockNotification(i, {
					subtype: NotificationSubtypes.BlockedEventsLimit
				})
			),
			loading: false,
			refetch: () => {}
		}));

		const {container} = render(<WrappedComponent />);

		await waitForLoading(container);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});
