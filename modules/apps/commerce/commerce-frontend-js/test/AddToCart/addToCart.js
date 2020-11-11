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

jest.mock('../../src/main/resources/META-INF/resources/ServiceProvider/index');

import ServiceProvider from '../../src/main/resources/META-INF/resources/ServiceProvider/index';

import '@testing-library/jest-dom/extend-expect';
import {cleanup, fireEvent, render} from '@testing-library/react';
import fetchMock from 'fetch-mock';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';

import AddToCartWrapper from '../../src/main/resources/META-INF/resources/components/add_to_cart/AddToCartWrapper';
import {
	accountId,
	channelId,
	currencyCode,
	orderId,
	productId,
	skuId,
	spritemap,
} from '../utilities/fake_data/add_to_cart_data';
const mockeData = {
	accountId,
	channelId,
	currencyCode,
	orderId,
	productId,
	skuId,
	spritemap,
};
const defaultSettings = {
	maxQuantity: 5,
	minQuantity: 2,
};

describe('Add To Cart Button behaviour', () => {
	beforeEach(() => {
		jest.resetAllMocks();

		ServiceProvider.DeliveryCartAPI = jest.fn().mockReturnValue({
			createItemByCartId: jest.fn(),
		});
	});

	afterEach(() => {
		cleanup();
	});

	it('disaply Quantityselector as select input', () => {
		const addToCart = render(
			<AddToCartWrapper settings={{...defaultSettings}} {...mockeData} />
		);
		expect(addToCart.getByText('add-to-cart')).toBeInTheDocument();
		expect(addToCart.getByRole('listbox')).toBeInTheDocument();
	});

	it('QuantitySelector as datalist input', () => {
		const addToCart = render(
			<AddToCartWrapper
				quantitySelectorStyle={'datalist'}
				{...mockeData}
			/>
		);
		expect(addToCart.getByRole('textbox')).toBeInTheDocument();
	});

	it('QuantitySelector as datalist input', () => {
		const addToCart = render(
			<AddToCartWrapper
				preSelectedQuantity={13}
				quantitySelectorStyle={'datalist'}
				settings={{...defaultSettings}}
				{...mockeData}
			/>
		);
		expect(addToCart.getByRole('textbox')).toBeInTheDocument();
	});

	it("display the text 'minimum-quantity-per-order-is' if the minQuantity is greater 1", () => {
		const addToCart = render(
			<AddToCartWrapper
				quantitySelectorStyle={'datalist'}
				settings={{
					maxQuantity: 5,
					minQuantity: 4,
				}}
				{...mockeData}
			/>
		);
		expect(
			addToCart.getByTestId('minimum-quantity-alert')
		).toBeInTheDocument();
	});

	it("display the text 'select-the-product-quantity-allowed-per-order' when allowedQuantity is not empty", () => {
		const addToCart = render(
			<AddToCartWrapper
				settings={{
					allowedQuantity: [2, 5, 88],
					maxQuantity: 5,
					minQuantity: 4,
				}}
				{...mockeData}
			/>
		);
		expect(
			addToCart.getByTestId('allowed-quantity-alert')
		).toBeInTheDocument();
		expect(addToCart.getByText('88')).toBeInTheDocument();
	});

	it("display the text 'multiple-quantity-per-order' when multipleQuantity is greater 1", () => {
		const options = {
			productId: 43939,
			quantity: 18,
			skuId: 43712,
		};
		const addToCart = render(
			<AddToCartWrapper
				quantitySelectorStyle={'select'}
				settings={{
					maxQuantity: 15,
					minQuantity: 1,
					multipleQuantity: 3,
				}}
				{...mockeData}
			/>
		);
		expect(
			addToCart.getByTestId('multiple-quantity-alert')
		).toBeInTheDocument();
		fireEvent.change(addToCart.getByTestId('select'), {target: {value: 6}});
		fireEvent.click(addToCart.getByRole('button'));
		expect(
			ServiceProvider.DeliveryCartAPI('v1').createItemByCartId
		).toHaveBeenCalledWith(orderId, options);
	});

	it('when the addToCart button is clicked, always the selectedQuantity as 1 will be sent', () => {
		const options = {
			productId: 43939,
			quantity: 1,
			skuId: 43712,
		};
		const addToCart = render(
			<AddToCartWrapper
				quantitySelectorStyle={'datalist'}
				{...mockeData}
			/>
		);
		fireEvent.click(addToCart.getByRole('button'));
		expect(ServiceProvider.DeliveryCartAPI).toHaveBeenCalledWith('v1');
		expect(
			ServiceProvider.DeliveryCartAPI('v1').createItemByCartId
		).toHaveBeenCalledWith(orderId, options);
	});

	it('when a quantity is selected and the addToCart button is clicked, always the selected Quantity will be sent', () => {
		const options = {
			productId: 43939,
			quantity: 13,
			skuId: 43712,
		};
		const {getByRole, getByTestId} = render(
			<AddToCartWrapper
				settings={{
					maxQuantity: 15,
					minQuantity: 1,
				}}
				{...mockeData}
			/>
		);
		fireEvent.change(getByTestId('select'), {target: {value: 13}});
		fireEvent.click(getByRole('button'));
		expect(
			ServiceProvider.DeliveryCartAPI('v1').createItemByCartId
		).toHaveBeenCalledWith(orderId, options);
	});

	it('if the user choose from the select input, and the addToCart button is disabled, will be sent the correct quantity', () => {
		const options = {
			productId: 43939,
			quantity: 13,
			skuId: 43712,
		};
		const {getByRole} = render(
			<AddToCartWrapper
				disableAddToCartButton={true}
				settings={{
					maxQuantity: 15,
					minQuantity: 1,
				}}
				{...mockeData}
			/>
		);
		fireEvent.change(getByRole('listbox'), {target: {value: 13}});
		expect(
			ServiceProvider.DeliveryCartAPI('v1').createItemByCartId
		).toHaveBeenCalledWith(orderId, options);
	});

	it('if QuantitySelector input is disabled, clicking addToCart button will send the correct quantity', () => {
		const options = {
			productId: 43939,
			quantity: 1,
			skuId: 43712,
		};
		const {getByRole} = render(
			<AddToCartWrapper
				disableQuantitySelector={true}
				settings={{
					maxQuantity: 15,
					minQuantity: 1,
				}}
				{...mockeData}
			/>
		);
		fireEvent.click(getByRole('button'));
		expect(
			ServiceProvider.DeliveryCartAPI('v1').createItemByCartId
		).toHaveBeenCalledWith(orderId, options);
	});
});
