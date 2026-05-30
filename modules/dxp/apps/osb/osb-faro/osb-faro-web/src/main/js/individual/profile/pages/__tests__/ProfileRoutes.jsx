import * as data from 'test/data';
import DataSourcesProvider from 'shared/context/dataSources';
import IndividualProfileRoutes from '../ProfileRoutes';
import mockStore from 'test/mock-store';
import React from 'react';
import {ChannelContext} from 'shared/context/channel';
import {cleanup, render} from '@testing-library/react';
import {Individual} from 'shared/util/records';
import {MemoryRouter} from 'react-router-dom';
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
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<ChannelContext.Provider value={mockChannelContext()}>
					<DataSourcesProvider groupId={defaultProps.groupId}>
						<MemoryRouter>
							<IndividualProfileRoutes {...defaultProps} />
						</MemoryRouter>
					</DataSourcesProvider>
				</ChannelContext.Provider>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});
});
