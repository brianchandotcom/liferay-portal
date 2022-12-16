/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import '@testing-library/jest-dom/extend-expect';
import {act, render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import fetch from 'jest-fetch-mock';
import React from 'react';

import Attributes from './Attributes';

const response = {
	account: 25,
	order: 0,
	people: 44,
	product: 34,
};

describe('Attributes', () => {
	afterEach(() => {
		jest.restoreAllMocks();
	});

	it('renders Attributes without crashing', async () => {
		fetch.mockResponseOnce(JSON.stringify(response));

		await act(async () => {
			render(<Attributes />);
		});

		expect(screen.getByText('people')).toBeInTheDocument();

		expect(screen.getByText('account')).toBeInTheDocument();

		expect(screen.getByText('products')).toBeInTheDocument();

		expect(screen.getByText('order')).toBeInTheDocument();
	});

	it('renders Attributes with values for account, order, people, product', async () => {
		fetch.mockResponseOnce(JSON.stringify(response));

		render(<Attributes />);

		expect(await screen.findByRole(/people/i)).toHaveTextContent(
			/People44Selected/i
		);
		expect(await screen.findByRole(/account/i)).toHaveTextContent(
			/Account25Selected/i
		);
		expect(await screen.findByRole(/product/i)).toHaveTextContent(
			/Products34Selected/i
		);
		expect(await screen.findByRole(/order/i)).toHaveTextContent(
			/Order0Selected/i
		);
	});

	it('renders Attributes with select buttons', async () => {
		fetch.mockResponseOnce(JSON.stringify(response));

		await act(async () => {
			render(<Attributes />);
		});

		const buttons = screen.getAllByText('select-attributes');

		expect(buttons).toBeTruthy();

		expect(buttons[0]).toHaveAttribute('type', 'button');

		expect(buttons[0]).toBeInTheDocument();
	});

	it('renders Modal when select button is clicked', async () => {
		fetch.mockResponseOnce(JSON.stringify(response));

		render(<Attributes />);

		const buttons = await screen.findAllByText('select-attributes');

		const modalContent = document.getElementsByClassName('modal-content');

		userEvent.click(buttons[0]);

		expect(modalContent).toBeTruthy();
	});
});
