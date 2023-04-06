import AcquisitionsCard from '../AcquisitionsCard';
import BasePage from 'shared/components/base-page';
import client from 'shared/apollo/client';
import React from 'react';
import {ApolloProvider} from '@apollo/react-components';
import {CompositionTypes, RangeKeyTimeRanges} from 'shared/util/constants';
import {mockAcquisitionsReq, mockTimeRangeReq} from 'test/graphql-data';
import {MockedProvider} from '@apollo/react-testing';
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
	<ApolloProvider client={client}>
		<BasePage.Context.Provider value={MOCK_CONTEXT}>
			<StaticRouter>
				<MockedProvider
					mocks={[mockTimeRangeReq(), mockAcquisitionsReq()]}
				>
					<AcquisitionsCard
						compositionBagName={CompositionTypes.Acquisitions}
						label='card label'
					/>
				</MockedProvider>
			</StaticRouter>
		</BasePage.Context.Provider>
	</ApolloProvider>
);

describe('AcquisitionsCard', () => {
	it('renders', async () => {
		const {container} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});
});
