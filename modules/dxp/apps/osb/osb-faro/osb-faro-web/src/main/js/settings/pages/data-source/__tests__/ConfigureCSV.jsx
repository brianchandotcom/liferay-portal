import ConfigureCSV from '../ConfigureCSV';
import mockStore from 'test/mock-store';
import React from 'react';
import {createMemoryRouter, RouterProvider} from 'react-router-dom';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		channelId: '456',
		groupId: '2000'
	})
}));

describe('ConfigureCSV', () => {
	it('should render', () => {
		const router = createMemoryRouter([
			{
				element: (
					<Provider store={mockStore()}>
						<ConfigureCSV groupId='23' id='123' />
					</Provider>
				),
				path: '/'
			}
		]);

		const {container} = render(<RouterProvider router={router} />);

		expect(container).toMatchSnapshot();
	});
});
