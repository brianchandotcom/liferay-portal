import client from 'shared/apollo/client';
import Interests from '../Interests';
import React from 'react';
import {ApolloProvider} from '@apollo/react-components';
import {createMemoryHistory} from 'history';
import {MockedProvider} from '@apollo/react-testing';
import {mockPreferenceReq, mockTimeRangeReq} from 'test/graphql-data';
import {render} from '@testing-library/react';
import {Router} from 'react-router-dom';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

const WrapperComponent = () => {
	const history = createMemoryHistory();

	return (
		<ApolloProvider client={client}>
			<Router history={history}>
				<MockedProvider
					mocks={[mockTimeRangeReq(), mockPreferenceReq()]}
				>
					<Interests router={{params: {}, query: {}}} />
				</MockedProvider>
			</Router>
		</ApolloProvider>
	);
};

describe('Sites Dashboard Interests', () => {
	it('render', async () => {
		const {container} = render(<WrapperComponent />);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});
});
