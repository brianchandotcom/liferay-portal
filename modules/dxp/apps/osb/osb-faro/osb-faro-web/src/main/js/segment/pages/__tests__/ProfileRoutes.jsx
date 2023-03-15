import * as data from 'test/data';
import mockStore from 'test/mock-store';
import React from 'react';
import {BrowserRouter} from 'react-router-dom';
import {ChannelContext} from 'shared/context/channel';
import {cleanup, render} from '@testing-library/react';
import {mockChannelContext} from 'test/mock-channel-context';
import {Provider} from 'react-redux';
import {Segment} from 'shared/util/records';
import {SegmentProfileRoutes} from '../ProfileRoutes';

const defaultProps = {
	channelId: '123',
	groupId: '23',
	id: 'test',
	location: {pathname: ''},
	segment: data.getImmutableMock(Segment, data.mockSegment)
};

jest.unmock('react-dom');

describe('SegmentProfileRoutes', () => {
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
						<SegmentProfileRoutes {...defaultProps} />
					</ChannelContext.Provider>
				</BrowserRouter>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});
});
