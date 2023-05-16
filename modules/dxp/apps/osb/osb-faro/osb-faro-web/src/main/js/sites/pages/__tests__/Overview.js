import BasePage from 'shared/components/base-page';
import client from 'shared/apollo/client';
import Overview from '../Overview';
import React from 'react';
import {ApolloProvider} from '@apollo/react-components';
import {ChannelContext} from 'shared/context/channel';
import {cleanup, fireEvent, render} from '@testing-library/react';
import {MemoryRouter, Route} from 'react-router-dom';
import {mockChannelContext} from 'test/mock-channel-context';
import {Routes} from 'shared/util/router';

jest.unmock('react-dom');

const MOCK_CONTEXT = {
	rangeKey: {defaultValue: '30'},
	router: {
		params: {
			channelId: '123123',
			groupId: '23'
		},
		query: {
			rangeKey: '30'
		}
	}
};

describe('Sites Dashboard Overview', () => {
	afterEach(cleanup);

	it('render', () => {
		const {container, getAllByText, getByText} = render(
			<ApolloProvider client={client}>
				<MemoryRouter initialEntries={['/workspace/23/123123/sites']}>
					<Route path={Routes.SITES}>
						<ChannelContext.Provider value={mockChannelContext()}>
							<BasePage.Context.Provider value={MOCK_CONTEXT}>
								<Overview
									channelName='Test Channel'
									router={{
										params: {
											channelId: '123123',
											groupId: '23'
										}
									}}
								/>
							</BasePage.Context.Provider>
						</ChannelContext.Provider>
					</Route>
				</MemoryRouter>
			</ApolloProvider>
		);
		fireEvent.click(getByText('All Visitors'));

		expect(getAllByText('All Visitors')[1]).toBeTruthy();
		expect(getByText('Anonymous Visitors')).toBeTruthy();
		expect(getByText('Known Visitors')).toBeTruthy();

		expect(container).toMatchSnapshot();
	});
});
