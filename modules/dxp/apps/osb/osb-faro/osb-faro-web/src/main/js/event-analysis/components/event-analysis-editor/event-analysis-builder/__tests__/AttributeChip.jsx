import AttributeChip, {DragTypes} from '../AttributeChip';
import client from 'shared/apollo/client';
import mockStore from 'test/mock-store';
import React from 'react';
import {ApolloProvider} from '@apollo/react-components';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';
import {wrapInTestContext} from 'react-dnd-test-utils';

jest.unmock('react-dom');

describe('AttributeChip', () => {
	const AttributeChipContext = wrapInTestContext(AttributeChip);

	it('render', () => {
		const {container} = render(
			<ApolloProvider client={client}>
				<Provider store={mockStore()}>
					<AttributeChipContext
						dataType='STRING'
						dragType={DragTypes.AttributeBreakdownChip}
						id='0'
						index={1}
						label='Event'
						onCloseClick={jest.fn()}
						onMove={jest.fn()}
						value='Article Title'
					/>
				</Provider>
			</ApolloProvider>
		);

		expect(container).toMatchSnapshot();
	});
});
