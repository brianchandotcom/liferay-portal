import BasePage from 'shared/components/base-page';
import client from 'shared/apollo/client';
import mockStore from 'test/mock-store';
import React from 'react';
import TopPagesCard from '../TopPagesCard';
import {ApolloProvider} from '@apollo/react-components';
import {MockedProvider} from '@apollo/react-testing';
import {
	mockPreferenceReq,
	mockSitesTopPagesReq,
	mockTimeRangeReq
} from 'test/graphql-data';
import {Provider} from 'react-redux';
import {RangeKeyTimeRanges} from 'shared/util/constants';
import {render} from '@testing-library/react';
import {StaticRouter} from 'react-router-dom';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

const MOCK_CONTEXT = {
	filters: {},
	router: {
		params: {
			channelId: '123',
			groupId: '456'
		},
		query: {
			rangeKey: RangeKeyTimeRanges.Last30Days
		}
	}
};

const DefaultComponent = () => (
	<Provider store={mockStore()}>
		<ApolloProvider client={client}>
			<BasePage.Context.Provider value={MOCK_CONTEXT}>
				<StaticRouter>
					<MockedProvider
						mocks={[
							mockTimeRangeReq(),
							mockPreferenceReq(),
							mockSitesTopPagesReq()
						]}
					>
						<TopPagesCard
							footer={{
								href: 'link-to-the-next-page',
								label: 'view pages'
							}}
							label='card label'
						/>
					</MockedProvider>
				</StaticRouter>
			</BasePage.Context.Provider>
		</ApolloProvider>
	</Provider>
);

describe('TopPagesCard', () => {
	it('renders', async () => {
		const {container} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});
});
