import * as data from 'test/data';
import mockStore from 'test/mock-store';
import React from 'react';
import {createMemoryRouter, RouterProvider} from 'react-router-dom';
import {DataSource} from 'shared/util/records';
import {Edit} from '../Edit';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		groupId: '23'
	})
}));

jest.mock('shared/hooks/useRequest', () => ({
	useRequest: jest.fn
}));

const csvProps = {
	groupId: '23',
	id: '23'
};

describe('Edit', () => {
	it('should render a CSV data-source page', () => {
		const router = createMemoryRouter([
			{
				element: (
					<Provider store={mockStore()}>
						<Edit
							{...csvProps}
							dataSource={
								new DataSource(data.mockCSVDataSource())
							}
						/>
					</Provider>
				),
				path: '/'
			}
		]);

		const {getByText} = render(<RouterProvider router={router} />);

		expect(getByText('Configure CSV')).toBeInTheDocument();
	});
});
