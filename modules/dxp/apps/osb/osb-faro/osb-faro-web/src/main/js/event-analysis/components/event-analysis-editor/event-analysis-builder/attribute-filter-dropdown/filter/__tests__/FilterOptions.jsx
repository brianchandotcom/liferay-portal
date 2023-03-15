import client from 'shared/apollo/client';
import FilterOptions from '../index';
import mockStore from 'test/mock-store';
import React from 'react';
import {ApolloProvider} from '@apollo/react-components';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';
import {withAttributesProvider} from '../../../../context/attributes';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		channelId: '456'
	})
}));

describe('FilterOptions', () => {
	it('should render', () => {
		const WrappedFilterOptions = withAttributesProvider(FilterOptions);

		const {container} = render(
			<ApolloProvider client={client}>
				<Provider store={mockStore()}>
					<WrappedFilterOptions
						attribute={{
							dataType: 'STRING',
							displayName: 'Filed Ticket',
							id: '4',
							name: 'filedTicket'
						}}
						onActiveChange={jest.fn()}
						onAttributeChange={jest.fn()}
						onEditClick={jest.fn()}
					/>
				</Provider>
			</ApolloProvider>
		);

		expect(container).toMatchSnapshot();
	});
});
