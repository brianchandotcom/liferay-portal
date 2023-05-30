import AttributeChip, {DragTypes} from '../AttributeChip';
import client from 'shared/apollo/client';
import mockStore from 'test/mock-store';
import React from 'react';
import {ApolloProvider} from '@apollo/react-components';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('AttributeChip', () => {
	it('render', () => {
		const {container} = render(
			<ApolloProvider client={client}>
				<Provider store={mockStore()}>
					<DndProvider backend={HTML5Backend}>
						<AttributeChip
							dataType='STRING'
							dragType={DragTypes.AttributeBreakdownChip}
							id='0'
							index={1}
							label='Event'
							onCloseClick={jest.fn()}
							onMove={jest.fn()}
							value='Article Title'
						/>
					</DndProvider>
				</Provider>
			</ApolloProvider>
		);

		expect(container).toMatchSnapshot();
	});
});
