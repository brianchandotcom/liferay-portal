import * as data from 'test/data';
import client from 'shared/apollo/client';
import Overview from '../Overview';
import React from 'react';
import {Account} from 'shared/util/records';
import {ApolloProvider} from '@apollo/react-components';
import {MockedProvider} from '@apollo/react-testing';
import {mockInterestsReq} from 'test/graphql-data';
import {render} from '@testing-library/react';
import {StaticRouter} from 'react-router';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

describe('AccountOverview', () => {
	it('should render', async () => {
		const {container} = render(
			<ApolloProvider client={client}>
				<StaticRouter>
					<MockedProvider mocks={[mockInterestsReq()]}>
						<Overview
							account={data.getImmutableMock(
								Account,
								data.mockAccount
							)}
							channelId='123'
							groupId='456'
							id='test'
						/>
					</MockedProvider>
				</StaticRouter>
			</ApolloProvider>
		);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});
});
