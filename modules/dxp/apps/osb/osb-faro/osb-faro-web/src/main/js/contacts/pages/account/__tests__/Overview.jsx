import * as data from 'test/data';
import client from 'shared/apollo/client';
import Overview from '../Overview';
import React from 'react';
import {Account} from 'shared/util/records';
import {ApolloProvider} from '@apollo/react-components';
import {render} from '@testing-library/react';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

describe('AccountOverview', () => {
	it('should render', () => {
		const {container} = render(
			<ApolloProvider client={client}>
				<StaticRouter>
					<Overview
						account={data.getImmutableMock(
							Account,
							data.mockAccount
						)}
						groupId='23'
						id='test'
					/>
				</StaticRouter>
			</ApolloProvider>
		);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});
