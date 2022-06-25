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

import '../../tests_utilities/polyfills';

import '@testing-library/jest-dom/extend-expect';
import {cleanup, fireEvent, render} from '@testing-library/react';
import fetchMock from 'fetch-mock';
import React from 'react';
import {act} from 'react-dom/test-utils';

import AddToCart from '../../../src/main/resources/META-INF/resources/components/add_to_cart/AddToCart';
import {
	CART_PRODUCT_QUANTITY_CHANGED,
	CURRENT_ACCOUNT_UPDATED,
} from '../../../src/main/resources/META-INF/resources/utilities/eventsDefinitions';

const props = {
	accountId: 43879,
	cartId: '43882',
	cartUUID: 'a711bf49-a2d3-2c8d-23c9-abaff7d288a5',
	channel: {
		currencyCode: 'USD',
		groupId: '42398',
		id: '42397',
	},
	settings: {
		iconOnly: false,
		productConfiguration: {
			allowedOrderQuantities: [],
			maxOrderQuantity: 50,
			minOrderQuantity: 1,
			multipleOrderQuantity: 1,
		},
	},
	size: 'sm',
};

describe('Add to Cart', () => {
	let addToCart;
	let button;
	let input;

	const addProductToCartFn = jest.fn();

	const defaultProps = {
		...props,
		cpInstance: {
			inCart: false,
			options: [],
			skuId: 42633,
		},
	};

	beforeEach(() => {
		fetchMock.mock(
			/\/o\/headless-commerce-delivery-cart\/v1.0\/carts\/[0-9]+\/items/,
			(_res, {body}) => {
				addProductToCartFn(JSON.parse(body));

				return {};
			}
		);
		fetchMock.mock(
			/\/o\/headless-commerce-delivery-cart\/v1.0\/carts\/[0-9]+\?nestedFields=cartItems/,
			{cartItems: []}
		);

		addToCart = render(<AddToCart {...defaultProps} />);

		input = addToCart.container.querySelector('input');
		button = addToCart.container.querySelector('button');
	});

	afterEach(() => {
		cleanup();

		fetchMock.restore();

		addProductToCartFn.mockReset();
	});

	it('must render the component', () => {
		expect(addToCart.container).toBeInTheDocument();
	});

	it('must be disabled consistently with its prop', () => {
		addToCart.rerender(<AddToCart {...defaultProps} disabled={true} />);

		expect(button.disabled).toBe(true);
	});

	it('must add a product to the cart', async () => {
		await act(async () => {
			await fireEvent.change(input, {target: {value: 3}});

			await fireEvent.click(button);
		});

		expect(addProductToCartFn).toHaveBeenCalledWith({
			options: '[]',
			quantity: 3,
			skuId: 42633,
		});
	});

	it('must focus the quantity selector when a user tries to add to the cart an invalid quantity', async () => {
		addToCart.rerender(
			<AddToCart
				{...defaultProps}
				settings={{
					...defaultProps.settings,
					productConfiguration: {
						allowedOrderQuantities: [],
						maxOrderQuantity: 50,
						minOrderQuantity: 5,
						multipleOrderQuantity: 7,
					},
				}}
			/>
		);

		act(() => {
			fireEvent.change(input, {target: {value: 6}});
		});

		const focusHandler = jest.fn();

		input.addEventListener('focus', focusHandler);

		act(() => {
			fireEvent.click(button);
		});

		expect(addProductToCartFn).not.toHaveBeenCalled();
		expect(focusHandler).toHaveBeenCalled();
	});

	describe('must handle Liferay events', () => {
		it('must be disabled when accountId is not provided', () => {
			act(() => {
				Liferay.fire(CURRENT_ACCOUNT_UPDATED, {
					id: 0,
				});
			});

			expect(button.disabled).toBe(true);

			act(() => {
				Liferay.fire(CURRENT_ACCOUNT_UPDATED, {
					id: 1,
				});
			});

			expect(button.disabled).toBe(false);
		});

		it('must give a UI feedback about the state of sku in the Cart', () => {
			expect(Array.from(button.classList)).not.toContain('is-added');

			act(() => {
				Liferay.fire(CART_PRODUCT_QUANTITY_CHANGED, {
					quantity: 5,
					skuId: defaultProps.cpInstance.skuId,
				});
			});

			expect(Array.from(button.classList)).toContain('is-added');

			act(() => {
				Liferay.fire(CART_PRODUCT_QUANTITY_CHANGED, {
					quantity: 0,
					skuId: defaultProps.cpInstance.skuId,
				});
			});

			expect(Array.from(button.classList)).not.toContain('is-added');
		});
	});
});
