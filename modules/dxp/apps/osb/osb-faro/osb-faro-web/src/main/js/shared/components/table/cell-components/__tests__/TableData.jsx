import React from 'react';
import TableData from '../TableData';
import {render} from '@testing-library/react';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

describe('TableData', () => {
	it('should render', () => {
		const {container} = render(<TableData />);

		expect(container).toMatchSnapshot();
	});

	it('should render with Link', () => {
		const {container, getAllByText} = render(
			<StaticRouter>
				<TableData title='My Title' url='foo/bar' />
			</StaticRouter>
		);

		expect(container.querySelector('a')).toHaveAttribute(
			'href',
			'/foo/bar'
		);
		expect(getAllByText('My Title')).toBeTruthy();
	});

	it('should render with Empty message', () => {
		const {container, getByText} = render(
			<TableData emptyMessage='Empty Message' />
		);

		expect(container.querySelector('.text-secondary')).toBeTruthy();
		expect(getByText('Empty Message')).toBeTruthy();
	});
});
