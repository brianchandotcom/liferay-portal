import * as data from 'test/data';
import IndividualProfileRoutes from '../ProfileRoutes';
import mockStore from 'test/mock-store';
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
			<Provider store={mockStore()}>
				<ChannelContext.Provider value={mockChannelContext()}>
					<BrowserRouter>
						<IndividualProfileRoutes {...defaultProps} />
					</BrowserRouter>
				</ChannelContext.Provider>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});
});
