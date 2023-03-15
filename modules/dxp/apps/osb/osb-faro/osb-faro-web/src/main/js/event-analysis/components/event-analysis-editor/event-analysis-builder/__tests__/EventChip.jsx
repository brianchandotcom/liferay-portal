import client from 'shared/apollo/client';
import EventChip from '../EventChip';
import mockStore from 'test/mock-store';
import React from 'react';
import {ApolloProvider} from '@apollo/react-components';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('EventChip', () => {
	it('render', () => {
		const {container} = render(
			<ApolloProvider client={client}>
				<Provider store={mockStore()}>
					<EventChip event={{name: 'View Article'}} />
				</Provider>
			</ApolloProvider>
		);

		expect(container).toMatchSnapshot();
	});
});
