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
import {act, cleanup, fireEvent, render, wait} from '@testing-library/react';
import React from 'react';

import MiniCartContext from '../../../src/main/resources/META-INF/resources/components/mini_cart/MiniCartContext';
import Opener from '../../../src/main/resources/META-INF/resources/components/mini_cart/Opener';

describe('MiniCart Opener', () => {
	const BASE_CONTEXT_MOCK = {
		cartState: {
			summary: {
				itemsQuantity: 0,
			},
		},
		displayTotalItemsQuantity: false,
		openCart: jest.fn(),
	};

	const COMPONENT_SELECTOR = '.mini-cart-opener';

	afterEach(() => {
		jest.resetAllMocks();

		cleanup();
	});

	describe('by default', () => {
		let ComponentElement;

		beforeEach(() => {
			const {container} = render(
				<MiniCartContext.Provider value={BASE_CONTEXT_MOCK}>
					<Opener />
				</MiniCartContext.Provider>
			);

			ComponentElement = container.querySelector(COMPONENT_SELECTOR);
		});

		it('renders a clickable button with a shopping cart icon to open the MiniCart', async () => {
			expect(ComponentElement).toBeInTheDocument();
			expect(ComponentElement.innerHTML).toMatchSnapshot();

			await act(async () => {
				fireEvent.click(ComponentElement);
			});

			await wait(() => {
				expect(BASE_CONTEXT_MOCK.openCart).toHaveBeenCalled();
			});
		});

		it('renders a button showing no number of items as the cart is empty', () => {
			expect(ComponentElement.classList.contains('has-badge')).toBe(
				false
			);
			expect(ComponentElement.dataset.badgeCount).toEqual('0');
		});
	});

	describe('by data flow', () => {
		describe('if there are cart items', () => {
			it('renders a badge with their total count by item type', () => {
				const {asFragment, container} = render(
					<MiniCartContext.Provider
						value={{
							...BASE_CONTEXT_MOCK,
							cartState: {
								...BASE_CONTEXT_MOCK.cartState,
								cartItems: [
									{
										id: 1,
										quantity: 3,
									},
									{
										id: 2,
										quantity: 5,
									},
								],
							},
						}}
					>
						<Opener />
					</MiniCartContext.Provider>
				);

				const ComponentElement = container.querySelector(
					COMPONENT_SELECTOR
				);

				expect(ComponentElement.classList.contains('has-badge')).toBe(
					true
				);
				expect(ComponentElement.dataset.badgeCount).toEqual('2');

				expect(asFragment()).toMatchSnapshot();
			});

			it('if "displayTotalItemsQuantity" is set to true, renders a badge with the total items count from the cart summary', () => {
				const {asFragment, container} = render(
					<MiniCartContext.Provider
						value={{
							...BASE_CONTEXT_MOCK,
							cartState: {
								cartItems: [
									{
										id: 1,
										quantity: 3,
									},
									{
										id: 2,
										quantity: 5,
									},
								],
								summary: {
									itemsQuantity: 8,
								},
							},
							displayTotalItemsQuantity: true,
						}}
					>
						<Opener />
					</MiniCartContext.Provider>
				);

				const ComponentElement = container.querySelector(
					COMPONENT_SELECTOR
				);

				expect(ComponentElement.classList.contains('has-badge')).toBe(
					true
				);
				expect(ComponentElement.dataset.badgeCount).toEqual('8');

				expect(asFragment()).toMatchSnapshot();
			});

			it('if "displayTotalItemsQuantity" is set to true, but there is no summary, renders a badge with the total items count by item type', () => {
				const {container} = render(
					<MiniCartContext.Provider
						value={{
							...BASE_CONTEXT_MOCK,
							cartState: {
								cartItems: [
									{
										id: 1,
										quantity: 3,
									},
									{
										id: 2,
										quantity: 5,
									},
								],
							},
							displayTotalItemsQuantity: true,
						}}
					>
						<Opener />
					</MiniCartContext.Provider>
				);

				const ComponentElement = container.querySelector(
					COMPONENT_SELECTOR
				);

				expect(ComponentElement.classList.contains('has-badge')).toBe(
					true
				);
				expect(ComponentElement.dataset.badgeCount).toEqual('2');
			});
		});
	});
});
