import AttributeBreakdownChip from '../AttributeBreakdownChip';
import client from 'shared/apollo/client';
import mockStore from 'test/mock-store';
import React from 'react';
import {ApolloProvider} from '@apollo/react-components';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('AttributeBreakdownChip', () => {
	it('render', () => {
		const {container} = render(
			<ApolloProvider client={client}>
				<Provider store={mockStore()}>
					<DndProvider backend={HTML5Backend}>
						<AttributeBreakdownChip
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
					</DndProvider>
				</Provider>
			</ApolloProvider>
		);

		expect(container).toMatchSnapshot();
	});
});
