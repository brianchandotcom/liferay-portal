import NavigationWarning from '../NavigationWarning';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

describe('NavigationWarning', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<StaticRouter>
				<NavigationWarning when />
			</StaticRouter>
		);

		expect(container).toBeTruthy();
	});
});
