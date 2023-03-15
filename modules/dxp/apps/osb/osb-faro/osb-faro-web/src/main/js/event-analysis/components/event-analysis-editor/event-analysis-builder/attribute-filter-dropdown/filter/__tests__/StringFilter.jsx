import client from 'shared/apollo/client';
import mockStore from 'test/mock-store';
import React from 'react';
import StringFilter from '../StringFilter';
import {ApolloProvider} from '@apollo/react-components';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		channelId: '456'
	})
}));

describe('StringFilter', () => {
	it('should render', () => {
		const {container} = render(
			<ApolloProvider client={client}>
				<Provider store={mockStore()}>
					<StringFilter onSubmit={jest.fn()} />
				</Provider>
			</ApolloProvider>
		);
		expect(container).toMatchSnapshot();
	});
});
