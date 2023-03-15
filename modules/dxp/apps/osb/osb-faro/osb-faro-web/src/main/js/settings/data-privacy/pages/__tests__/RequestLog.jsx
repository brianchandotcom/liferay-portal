import client from 'shared/apollo/client';
import mockStore from 'test/mock-store';
import React from 'react';
import RequestLog from '../RequestLog';
import {ApolloProvider} from '@apollo/react-components';
import {cleanup, render} from '@testing-library/react';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router-dom';

jest.unmock('react-dom');

describe('RequestLog', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<ApolloProvider client={client}>
				<Provider store={mockStore()}>
					<StaticRouter>
						<RequestLog
							router={{params: {groupId: '23'}, query: {}}}
						/>
					</StaticRouter>
				</Provider>
			</ApolloProvider>
		);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});
