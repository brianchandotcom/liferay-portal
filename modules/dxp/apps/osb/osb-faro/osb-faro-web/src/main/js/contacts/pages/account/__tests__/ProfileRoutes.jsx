import * as data from 'test/data';
import mockStore from 'test/mock-store';
import React from 'react';
import {Account} from 'shared/util/records';
import {AccountProfileRoutes} from '../ProfileRoutes';
import {BrowserRouter} from 'react-router-dom';
import {ChannelContext} from 'shared/context/channel';
import {cleanup, render} from '@testing-library/react';
import {mockChannelContext} from 'test/mock-channel-context';
import {Provider} from 'react-redux';

const defaultProps = {
	account: data.getImmutableMock(Account, data.mockAccount),
	channelId: '123',
	groupId: '23',
	id: 'test',
	location: {pathname: ''}
};

jest.unmock('react-dom');

describe('AccountProfileRoutes', () => {
	afterEach(cleanup);

	beforeAll(() => {
		delete window.location;
	});

	it('should render', () => {
		window.location = {pathname: '/'};

		const {container} = render(
			<Provider store={mockStore()}>
				<BrowserRouter>
					<ChannelContext.Provider value={mockChannelContext()}>
						<AccountProfileRoutes {...defaultProps} />
					</ChannelContext.Provider>
				</BrowserRouter>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});
});
