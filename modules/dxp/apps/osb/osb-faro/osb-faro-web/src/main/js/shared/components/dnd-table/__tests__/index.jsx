import DndTable from '../index';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {noop} from 'lodash';

jest.unmock('react-dom');

describe('DndTable', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
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
		);
		expect(container).toMatchSnapshot();
	});
});
