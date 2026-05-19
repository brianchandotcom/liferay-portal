import * as data from 'test/data';
import DataSourcesProvider from 'shared/context/dataSources';
import IndividualProfileRoutesCDP from '../ProfileRoutesCDP';
import mockStore, {mockStoreDataLDP} from 'test/mock-store';
import React from 'react';
import {BrowserRouter} from 'react-router-dom';
import {ChannelContext} from 'shared/context/channel';
import {cleanup, render} from '@testing-library/react';
import {Individual} from 'shared/util/records';
import {mockChannelContext} from 'test/mock-channel-context';
import {Provider} from 'react-redux';

const defaultProps = {
	channelId: '123',
	groupId: '23',
	id: 'test',
	individual: data.getImmutableMock(Individual, data.mockIndividual),
	location: {pathname: ''}
};

jest.unmock('react-dom');

describe('IndividualProfileRoutes', () => {
	beforeAll(() => {
		delete window.location;
	});

	afterEach(cleanup);

	it('should render', () => {
		window.location = {pathname: '/'};

		const {container} = render(
			<Provider store={mockStore(mockStoreDataLDP)}>
				<ChannelContext.Provider value={mockChannelContext()}>
					<DataSourcesProvider groupId={defaultProps.groupId}>
						<BrowserRouter>
							<IndividualProfileRoutesCDP {...defaultProps} />
						</BrowserRouter>
					</DataSourcesProvider>
				</ChannelContext.Provider>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});
});
