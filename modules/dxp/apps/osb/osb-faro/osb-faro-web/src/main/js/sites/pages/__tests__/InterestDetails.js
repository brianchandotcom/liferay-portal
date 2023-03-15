import BasePage from 'shared/components/base-page';
import client from 'shared/apollo/client';
import InterestDetails from '../InterestDetails';
import React from 'react';
import {ApolloProvider} from '@apollo/react-components';
import {ChannelContext} from 'shared/context/channel';
import {mockChannelContext} from 'test/mock-channel-context';
import {render} from '@testing-library/react';
import {StaticRouter} from 'react-router-dom';

jest.unmock('react-dom');

const MOCK_CONTEXT = {
	rangeKey: {defaultValue: '30'},
	router: {
		params: {
			channelId: '123',
			groupId: '2000'
		},
		query: {
			rangeKey: '30'
		}
	}
};

describe('Sites Dashboard InterestDetails', () => {
	it('render', () => {
		const {container} = render(
			<ApolloProvider client={client}>
				<StaticRouter>
					<ChannelContext.Provider value={mockChannelContext()}>
						<BasePage.Context.Provider value={MOCK_CONTEXT}>
							<InterestDetails
								channelName='Test Channel'
								router={{
									params: {channelId: '456', groupId: '123'},
									query: {rangeKey: '30'}
								}}
							/>
						</BasePage.Context.Provider>
					</ChannelContext.Provider>
				</StaticRouter>
			</ApolloProvider>
		);

		expect(container).toMatchSnapshot();
	});
});
