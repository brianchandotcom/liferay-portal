import Cell from '../Cell';
import React from 'react';
import {cleanup, render} from '@testing-library/react';

jest.unmock('react-dom');

describe('Cell', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<Cell accessor='name' data={{name: 'Test'}} />
		);
		expect(container).toMatchSnapshot();
	});

	it('renders w/ Custom CellRenderer', () => {
		const email = 'test@test.com';
		const title = 'Test Test';

		const {getByText} = render(
			<Cell
				cellRenderer={({data: {email, title}}) => (
					<td className='custom-cell-renderer'>
						<div>{title}</div>
						<div>{email}</div>
					</td>
				)}
				data={{email, title}}
			/>
		);

		expect(getByText(email)).not.toBeNull();
		expect(getByText(title)).not.toBeNull();
	});

	it('renders w/ dataFormatter', () => {
		const {getByText} = render(
			<Cell
				accessor='score'
				data={{score: 10.56}}
				dataFormatter={val => Math.round(val)}
			/>
		);

		expect(getByText('11')).not.toBeNull();
	});
});
