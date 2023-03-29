import client from 'shared/apollo/client';
import React from 'react';
import Touchpoints from '../Touchpoints';
import {ApolloProvider} from '@apollo/react-components';
import {render} from '@testing-library/react';
import {StaticRouter} from 'react-router';
import {waitForLoading} from 'test/helpers';

jest.unmock('react-dom');

describe('Sites Dashboard Touchpoints Page', () => {
	it('render', async () => {
		const {container} = render(
			<ApolloProvider client={client}>
				<StaticRouter>
					<Touchpoints router={{params: {}, query: {}}} />
				</StaticRouter>
			</ApolloProvider>
		);

		await waitForLoading(container);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});
