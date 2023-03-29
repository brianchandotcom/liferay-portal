import AttributeBreakdownChip from '../AttributeBreakdownChip';
import client from 'shared/apollo/client';
import mockStore from 'test/mock-store';
import React from 'react';
import {ApolloProvider} from '@apollo/react-components';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';
import {wrapInTestContext} from 'react-dnd-test-utils';

jest.unmock('react-dom');

describe('AttributeBreakdownChip', () => {
	const AttributeBreakdownChipContext = wrapInTestContext(
		AttributeBreakdownChip
	);

	it('render', () => {
		const {container} = render(
			<ApolloProvider client={client}>
				<Provider store={mockStore()}>
					<AttributeBreakdownChipContext
						attribute={{
							dataType: 'STRING',
							displayName: 'Article View',
							id: '0',
							name: 'articleView'
						}}
						breakdown={{
							attributeId: '0',
							dataType: 'STRING',
							type: 'event'
						}}
						index={1}
					/>
				</Provider>
			</ApolloProvider>
		);

		expect(container).toMatchSnapshot();
	});
});
