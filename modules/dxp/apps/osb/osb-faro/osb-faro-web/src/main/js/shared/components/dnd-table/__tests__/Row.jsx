import React from 'react';
import Row from '../Row';
import {cleanup, render} from '@testing-library/react';
import {noop} from 'lodash';
import {wrapInTestContext} from 'react-dnd-test-utils';

jest.unmock('react-dom');

describe('Row', () => {
	const RowContext = wrapInTestContext(Row);

	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<RowContext
				columns={[
					{
						accessor: 'title'
					}
				]}
				data={{title: 'Test Test'}}
				index={0}
				name='row'
				onMove={noop}
				rowIdentifier='title'
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders w/o drag handle', () => {
		const {container} = render(
			<RowContext
				columns={[
					{
						accessor: 'title'
					}
				]}
				data={{draggable: false, title: 'Test Test'}}
				index={0}
				name='row'
				onMove={noop}
				rowIdentifier='title'
			/>
		);

		expect(container.querySelector('.drag-handle')).not.toBeNull();
	});
});
