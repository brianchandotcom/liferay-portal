import DataSourcesProvider from 'shared/context/dataSources';
import mockStore from 'test/mock-store';
import React from 'react';
import {ChannelContext} from 'shared/context/channel';
import {cleanup, render, screen} from '@testing-library/react';
import {MemoryRouter} from 'react-router-dom';
import {mockChannelContext} from 'test/mock-channel-context';
import {Provider} from 'react-redux';
import {SegmentProfileRoutes} from '../ProfileRoutes';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

jest.mock('../Overview', () => () => <div>{'SegmentOverview'}</div>);
jest.mock('../OverviewRealTime', () => () => (
	<div>{'SegmentOverviewRealTime'}</div>
));

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

	it('should render', async () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<MemoryRouter>
					<ChannelContext.Provider value={mockChannelContext()}>
						<DataSourcesProvider groupId='23'>
							<SegmentProfileRoutes />
						</DataSourcesProvider>
					</ChannelContext.Provider>
				</MemoryRouter>
			</Provider>
		);

		await waitForLoadingToBeRemoved(container);

		expect(screen.getAllByText('Seattle0').length).toBeGreaterThan(0);
	});
});
