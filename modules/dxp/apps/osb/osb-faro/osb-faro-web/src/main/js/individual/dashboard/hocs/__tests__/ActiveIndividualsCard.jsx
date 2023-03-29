import ActiveIndividualsCard from '../ActiveIndividualsCard';
import client from 'shared/apollo/client';
import React from 'react';
import {ApolloProvider} from '@apollo/react-components';
import {cleanup, render} from '@testing-library/react';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		channelId: '456'
	})
}));

describe('ActiveIndividualsCard', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<ApolloProvider client={client}>
				<ActiveIndividualsCard />
			</ApolloProvider>
		);
		expect(container).toMatchSnapshot();
	});
});
