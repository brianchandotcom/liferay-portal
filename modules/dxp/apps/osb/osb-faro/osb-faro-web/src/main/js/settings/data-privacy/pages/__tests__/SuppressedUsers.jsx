import * as data from 'test/data';
import mockStore from 'test/mock-store';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {MemoryRouter, Route} from 'react-router-dom';
import {MockedProvider} from '@apollo/react-testing';
import {mockSuppressedUsersListReq} from 'test/graphql-data';
import {Provider} from 'react-redux';
import {Routes} from 'shared/util/router';
import {SuppressedUsers} from '../SuppressedUsers';
import {waitForLoading} from 'test/helpers';

jest.unmock('react-dom');

const mockItems = [
	{
		createDate: data.getTimestamp(),
		dataControlTaskBatchId: '123',
		dataControlTaskCreateDate: data.getTimestamp(),
		dataControlTaskStatus: 'PENDING',
		emailAddress: 'Test@liferay.com',
		id: '321'
	}
];

describe('SuppressedUsers', () => {
	afterEach(cleanup);

	it('should render', async () => {
		const {container} = render(
			<MockedProvider mocks={[mockSuppressedUsersListReq(mockItems)]}>
				<Provider store={mockStore()}>
					<MemoryRouter
						initialEntries={[
							'/workspace/23/settings/data-privacy/suppressed-users?delta=5'
						]}
					>
						<Route
							path={Routes.SETTINGS_DATA_PRIVACY_SUPPRESSED_USERS}
						>
							<SuppressedUsers
								router={{
									params: {groupId: '23'},
									query: {delta: '5', page: '1'}
								}}
							/>
						</Route>
					</MemoryRouter>
				</Provider>
			</MockedProvider>
		);

		await waitForLoading(container);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});
