import DndTable from '../index';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';
import {noop} from 'lodash';

jest.unmock('react-dom');

describe('DndTable', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<DndProvider backend={HTML5Backend}>
				<DndTable
					columns={[
						{
							accessor: 'title',
							label: 'Title'
						}
					]}
					items={[
						{
							title: 'Test Test'
						}
					]}
					onItemsChange={noop}
				/>
			</DndProvider>
		);
		expect(container).toMatchSnapshot();
	});
});
