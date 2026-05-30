import NavigationWarning from '../NavigationWarning';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {createMemoryRouter, RouterProvider} from 'react-router-dom';

jest.unmock('react-dom');

describe('NavigationWarning', () => {
	afterEach(cleanup);

	it('should render', () => {
		const router = createMemoryRouter([
			{
				element: <NavigationWarning when />,
				path: '/'
			}
		]);

		const {container} = render(<RouterProvider router={router} />);

		expect(container).toBeTruthy();
	});
});
