import PaginationBar from '../PaginationBar';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

const DefaultComponent = props => (
	<StaticRouter>
		<PaginationBar
			href=''
			page={3}
			selectedDelta={10}
			totalItems={100}
			{...props}
		/>
	</StaticRouter>
);

describe('PaginationBar', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<DefaultComponent />);
		expect(container).toMatchSnapshot();
	});

	it('should render with small size', () => {
		const {container} = render(<DefaultComponent size='sm' />);

		expect(container.querySelector('.pagination-sm')).toBeTruthy();
	});

	it('should render with large size', () => {
		const {container} = render(<DefaultComponent size='lg' />);

		expect(container.querySelector('.pagination-lg')).toBeTruthy();
	});

	it('should render different deltas', () => {
		const {container} = render(
			<DefaultComponent
				deltas={[1, 2, 3, 4]}
				page={1}
				selectedDelta={1}
				totalItems={10}
			/>
		);

		expect(container).toMatchSnapshot();
	});
});
