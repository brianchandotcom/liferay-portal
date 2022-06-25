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

import AddToCartButton from '../../../src/main/resources/META-INF/resources/components/add_to_cart/AddToCartButton';

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

describe('Add to Cart Button', () => {
	let addToCartButton;
	let button;

	const addProductsToCartFn = jest.fn();
	const onAdd = jest.fn();

	const defaultProps = {
		...props,
		cpInstances: [
			{
				inCart: false,
				quantity: 1,
				skuId: 123,
				skuOptions: [],
			},
			{
				inCart: false,
				quantity: 3,
				skuId: 234,
				skuOptions: [],
			},
		],
		onAdd,
	};

	beforeEach(() => {
		fetchMock.mock(
			/\/o\/headless-commerce-delivery-cart\/v1.0\/carts\/[0-9]+\?nestedFields=cartItems/,
			(_, options) => {
				if (options.method === 'PATCH') {
					addProductsToCartFn(JSON.parse(options.body));
				}

				return {cartItems: []};
			}
		);

		addToCartButton = render(<AddToCartButton {...defaultProps} />);

		button = addToCartButton.container.querySelector('button');
	});

	afterEach(() => {
		cleanup();

		fetchMock.restore();

		addProductsToCartFn.mockReset();
	});

	it('must render the component', () => {
		expect(addToCartButton.container).toBeInTheDocument();
	});

	it('must be disabled consistently with its prop', () => {
		addToCartButton.rerender(
			<AddToCartButton {...defaultProps} disabled={true} />
		);

		expect(button.disabled).toBe(true);
	});

	it('must add multiple products to the cart', async () => {
		await act(async () => {
			await fireEvent.click(button);
		});

		expect(addProductsToCartFn).toHaveBeenCalledWith({
			cartItems: [
				{
					options: '[]',
					quantity: 1,
					skuId: 123,
				},
				{
					options: '[]',
					quantity: 3,
					skuId: 234,
				},
			],
		});
	});
});
