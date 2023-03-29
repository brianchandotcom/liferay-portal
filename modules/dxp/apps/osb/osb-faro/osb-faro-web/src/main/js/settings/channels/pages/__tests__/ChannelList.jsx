import * as API from 'shared/api';
import * as data from 'test/data';
import ChannelList from '../ChannelList';
import mockStore, {mockStoreData} from 'test/mock-store';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {MemoryRouter, Route} from 'react-router-dom';
import {Provider} from 'react-redux';
import {RemoteData} from 'shared/util/records';
import {Routes} from 'shared/util/router';

jest.unmock('react-dom');

const defaultProps = {
	groupId: '23'
};

describe('Channels List', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<MemoryRouter
					initialEntries={['/workspace/23/settings/properties']}
				>
					<Route path={Routes.SETTINGS_CHANNELS}>
						<ChannelList {...defaultProps} />
					</Route>
				</MemoryRouter>
			</Provider>
		);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('should not render add button if user is not an admin', () => {
		API.user.fetchCurrentUser.mockReturnValueOnce(
			Promise.resolve(data.mockMemberUser('24'))
		);

		const {queryByText} = render(
			<Provider
				store={mockStore(
					mockStoreData.setIn(
						['currentUser'],
						new RemoteData({data: '24', loading: false})
					)
				)}
			>
				<MemoryRouter
					initialEntries={['/workspace/23/settings/properties']}
				>
					<Route path={Routes.SETTINGS_CHANNELS}>
						<ChannelList {...defaultProps} />
					</Route>
				</MemoryRouter>
			</Provider>
		);

		expect(queryByText('New Property')).toBeNull();
	});
});
