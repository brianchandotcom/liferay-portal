import mockStore from 'test/mock-store';
import React from 'react';
import {BrowserRouter} from 'react-router-dom';
import {ChannelContext} from 'shared/context/channel';
import {cleanup, render} from '@testing-library/react';
import {mockChannelContext} from 'test/mock-channel-context';
import {Provider} from 'react-redux';
import {SegmentProfileRoutes} from '../ProfileRoutes';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		channelId: '123',
		groupId: '23',
		id: 'test'
	})
}));

describe('SegmentProfileRoutes', () => {
	afterEach(cleanup);

	beforeAll(() => {
		delete window.location;
	});

	it('should render', async () => {
		window.location = {pathname: '/'};

		const {container} = render(
			<Provider store={mockStore()}>
				<BrowserRouter>
					<ChannelContext.Provider value={mockChannelContext()}>
						<SegmentProfileRoutes />
					</ChannelContext.Provider>
				</BrowserRouter>
			</Provider>
		);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});
});
