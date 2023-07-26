import client from 'shared/apollo/client';
import mockStore from 'test/mock-store';
import React from 'react';
import {ApolloProvider} from '@apollo/react-hooks';
import {cleanup, fireEvent, render} from '@testing-library/react';
import {mockUser} from 'test/data';
import {Overview} from '../Overview';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router-dom';
import {User} from 'shared/util/records';
import {UserRoleNames} from 'shared/util/constants';

jest.unmock('react-dom');

describe('Data Privacy Overview', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container, getByText} = render(
			<ApolloProvider client={client}>
				<Provider store={mockStore()}>
					<StaticRouter>
						<Overview
							currentUser={new User(mockUser())}
							groupId='23'
						/>
					</StaticRouter>
				</Provider>
			</ApolloProvider>
		);

		jest.runAllTimers();
		fireEvent.click(getByText('Select an option'));

		expect(getByText('7 Months')).toBeTruthy();
		expect(getByText('13 Months')).toBeTruthy();
		expect(container).toMatchSnapshot();
	});

	it('should render with disabled buttons in the Suppressed Users section if the user is not an AC admin', () => {
		const {getByTestId} = render(
			<ApolloProvider client={client}>
				<Provider store={mockStore()}>
					<StaticRouter>
						<Overview
							currentUser={
								new User(
									mockUser(0, {
										roleName: UserRoleNames.Member
									})
								)
							}
							groupId='23'
						/>
					</StaticRouter>
				</Provider>
			</ApolloProvider>
		);

		jest.runAllTimers();

		expect(getByTestId('export-suppressed-user-button').disabled).toBe(
			true
		);
		expect(getByTestId('data-retention-period-select-input').disabled).toBe(
			true
		);
	});
});
