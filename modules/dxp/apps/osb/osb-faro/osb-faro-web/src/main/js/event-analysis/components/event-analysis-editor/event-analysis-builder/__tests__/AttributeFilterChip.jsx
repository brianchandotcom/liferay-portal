import AttributeFilterChip from '../AttributeFilterChip';
import client from 'shared/apollo/client';
import mockStore from 'test/mock-store';
import React from 'react';
import {ApolloProvider} from '@apollo/react-components';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';
import {wrapInTestContext} from 'react-dnd-test-utils';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		channelId: '456'
	})
}));

describe('AttributeFilterChip', () => {
	const AttributeFilterChipContext = wrapInTestContext(AttributeFilterChip);

	it('render', () => {
		const {container} = render(
			<ApolloProvider client={client}>
				<Provider store={mockStore()}>
					<AttributeFilterChipContext
						attribute={{
							dataType: 'STRING',
							displayName: 'Article View',
							id: '0',
							name: 'articleView'
						}}
						filter={{
							attributeId: '0',
							dataType: 'STRING',
							operator: 'eq',
							type: 'event',
							value: ['Stuff']
						}}
						index={1}
					/>
				</Provider>
			</ApolloProvider>
		);

		expect(container).toMatchSnapshot();
	});
});
