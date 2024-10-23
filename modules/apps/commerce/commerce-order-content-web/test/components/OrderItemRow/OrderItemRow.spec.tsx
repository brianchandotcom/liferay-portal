/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import fetchMock from 'fetch-mock';

import {
	IDeliveryGroup,
	IOrderItem,
} from '../../../src/main/resources/META-INF/resources/js/multishipping/Types';

import '@testing-library/jest-dom/extend-expect';
import {RenderResult, cleanup, render, waitFor} from '@testing-library/react';
import React from 'react';
import {act} from 'react-dom/test-utils';

import OrderItemRow from '../../../src/main/resources/META-INF/resources/js/multishipping/OrderItemRow';
import {setFieldValue} from '../../utils/utils.spec';

interface ILocators {
	deliveryGroup1Cell: HTMLElement | null;
	deliveryGroup1Input: HTMLInputElement | null;
	deliveryGroup2Cell: HTMLElement | null;
	deliveryGroup2Input: HTMLInputElement | null;
	image: HTMLImageElement;
	quantityCell: HTMLElement;
	skuNameCell: HTMLElement;
}

const getLocators = (
	deliveryGroups: Array<IDeliveryGroup>,
	orderItemId: number,
	renderedComponent: RenderResult
): ILocators => {
	return {
		deliveryGroup1Cell: renderedComponent.queryByTestId(
			`orderItem${orderItemId}-${deliveryGroups[0]?.id}`
		),
		deliveryGroup1Input: renderedComponent.queryByTestId(
			`orderItem${orderItemId}-${deliveryGroups[0]?.id}Input`
		) as HTMLInputElement,
		deliveryGroup2Cell: renderedComponent.queryByTestId(
			`orderItem${orderItemId}-${deliveryGroups[1]?.id}`
		),
		deliveryGroup2Input: renderedComponent.queryByTestId(
			`orderItem${orderItemId}-${deliveryGroups[1]?.id}Input`
		) as HTMLInputElement,
		image: renderedComponent.queryByRole('img') as HTMLImageElement,
		quantityCell: renderedComponent.getByRole('cell', {name: 'quantity'}),
		skuNameCell: renderedComponent.getByRole('cell', {name: 'sku-name'}),
	};
};

describe('OrderItemRow', () => {
	const handleSubmit = jest.fn();

	afterEach(() => {
		fetchMock.restore();
		jest.clearAllMocks();

		cleanup();
	});

	it('Must display order item information without inputs', async () => {
		const orderItem = {
			id: 100,
			name: 'Product1',
			options: '[]',
			productId: 1000,
			quantity: 2,
			replacedSkuId: 0,
			settings: {
				maxQuantity: 10000,
				minQuantity: 1,
				multipleQuantity: 1,
			},
			sku: 'SKU1',
			skuId: 1001,
			thumbnail: '/o/commerce-media/default/?groupId=33472',
		} as IOrderItem;

		const renderedComponent = render(
			<OrderItemRow
				deliveryGroups={[]}
				handleSubmit={handleSubmit}
				orderId={10}
				orderItem={orderItem}
			/>
		);

		const {deliveryGroup1Cell, image, quantityCell, skuNameCell} =
			getLocators([], orderItem.id, renderedComponent);

		expect(image).toHaveAttribute('src', orderItem.thumbnail);
		expect(skuNameCell).toHaveTextContent(orderItem.sku as string);
		expect(quantityCell).toHaveTextContent(String(orderItem.quantity));
		expect(deliveryGroup1Cell).not.toBeInTheDocument();
	});

	it('Must display order item information with empty inputs for all delivery groups', async () => {
		const deliveryGroups = [
			{
				addressId: 100,
				deliveryDate: '',
				id: 10000,
				name: 'DeliveryGroup1',
			},
			{
				addressId: 100,
				deliveryDate: '',
				id: 10001,
				name: 'DeliveryGroup2',
			},
		];

		const orderItem = {
			id: 100,
			name: 'Product1',
			options: '[]',
			productId: 1000,
			quantity: 2,
			replacedSkuId: 0,
			settings: {
				maxQuantity: 10000,
				minQuantity: 1,
				multipleQuantity: 1,
			},
			sku: 'SKU1',
			skuId: 1001,
			thumbnail: '/o/commerce-media/default/?groupId=33472',
		} as IOrderItem;

		const renderedComponent = render(
			<OrderItemRow
				deliveryGroups={deliveryGroups}
				handleSubmit={handleSubmit}
				orderId={10}
				orderItem={orderItem}
			/>
		);

		const {
			deliveryGroup1Cell,
			deliveryGroup1Input,
			deliveryGroup2Cell,
			deliveryGroup2Input,
			image,
			quantityCell,
			skuNameCell,
		} = getLocators(deliveryGroups, orderItem.id, renderedComponent);

		expect(image).toHaveAttribute('src', orderItem.thumbnail);
		expect(skuNameCell).toHaveTextContent(orderItem.sku as string);
		expect(quantityCell).toHaveTextContent(String(orderItem.quantity));
		expect(deliveryGroup1Cell).toBeInTheDocument();
		expect(deliveryGroup2Cell).toBeInTheDocument();
		expect(deliveryGroup1Input).toBeInTheDocument();
		expect(deliveryGroup1Input).not.toHaveValue();
		expect(deliveryGroup2Input).toBeInTheDocument();
		expect(deliveryGroup2Input).not.toHaveValue();
	});

	it('Must disable all the fields', async () => {
		const deliveryGroups = [
			{
				addressId: 100,
				deliveryDate: '',
				id: 10000,
				name: 'DeliveryGroup1',
			},
			{
				addressId: 100,
				deliveryDate: '',
				id: 10001,
				name: 'DeliveryGroup2',
			},
		];

		const orderItem: IOrderItem = {
			id: 100,
			name: 'Product1',
			options: '[]',
			productId: 1000,
			quantity: 8,
			replacedSkuId: 0,
			requestedDeliveryDate: '',
			settings: {
				maxQuantity: 10000,
				minQuantity: 1,
				multipleQuantity: 1,
			},
			shippingAddressId: 0,
			sku: 'SKU1',
			skuId: 1001,
			skuUnitOfMeasure: {} as any,
			thumbnail: '/o/commerce-media/default/?groupId=33472',
		} as IOrderItem;

		const renderedComponent = render(
			<OrderItemRow
				deliveryGroups={deliveryGroups}
				disabled={true}
				handleSubmit={handleSubmit}
				orderId={10}
				orderItem={orderItem as any}
			/>
		);

		const {deliveryGroup1Input, deliveryGroup2Input} = getLocators(
			deliveryGroups,
			orderItem.id,
			renderedComponent
		);

		expect(deliveryGroup1Input).toBeInTheDocument();
		expect(deliveryGroup1Input).toBeDisabled();
		expect(deliveryGroup2Input).toBeInTheDocument();
		expect(deliveryGroup2Input).toBeDisabled();
	});

	it('Must display only first delivery group input field filled', async () => {
		const deliveryGroups = [
			{
				addressId: 100,
				deliveryDate: '',
				id: 10000,
				name: 'DeliveryGroup1',
			},
			{
				addressId: 100,
				deliveryDate: '',
				id: 10001,
				name: 'DeliveryGroup2',
			},
		];

		const orderItem: IOrderItem = {
			deliveryGroups: {
				10000: {
					options: '[]',
					orderItemId: 100,
					originalQuantity: 4,
					quantity: 4,
					replacedSkuId: 0,
					skuId: 1001,
					skuUnitOfMeasure: {},
				},
			},
			id: 100,
			name: 'Product1',
			options: '[]',
			productId: 1000,
			quantity: 4,
			replacedSkuId: 0,
			requestedDeliveryDate: '',
			settings: {
				maxQuantity: 10000,
				minQuantity: 1,
				multipleQuantity: 1,
			},
			shippingAddressId: 0,
			sku: 'SKU1',
			skuId: 1001,
			skuUnitOfMeasure: {} as any,
			thumbnail: '/o/commerce-media/default/?groupId=33472',
		} as IOrderItem;

		const renderedComponent = render(
			<OrderItemRow
				deliveryGroups={deliveryGroups}
				handleSubmit={handleSubmit}
				orderId={10}
				orderItem={orderItem as any}
			/>
		);

		const {deliveryGroup1Input, deliveryGroup2Input, quantityCell} =
			getLocators(deliveryGroups, orderItem.id, renderedComponent);

		expect(quantityCell).toHaveTextContent(String(orderItem.quantity));
		expect(deliveryGroup1Input).toBeInTheDocument();
		expect(deliveryGroup1Input).toHaveValue(4);
		expect(deliveryGroup2Input).toBeInTheDocument();
		expect(deliveryGroup2Input).not.toHaveValue();
	});

	it('Must display only second delivery group input field filled', async () => {
		const deliveryGroups = [
			{
				addressId: 100,
				deliveryDate: '',
				id: 10000,
				name: 'DeliveryGroup1',
			},
			{
				addressId: 100,
				deliveryDate: '',
				id: 10001,
				name: 'DeliveryGroup2',
			},
		];

		const orderItem: IOrderItem = {
			deliveryGroups: {
				10001: {
					options: '[]',
					orderItemId: 100,
					originalQuantity: 4,
					quantity: 4,
					replacedSkuId: 0,
					skuId: 1001,
					skuUnitOfMeasure: {},
				},
			},
			id: 100,
			name: 'Product1',
			options: '[]',
			productId: 1000,
			quantity: 4,
			replacedSkuId: 0,
			requestedDeliveryDate: '',
			settings: {
				maxQuantity: 10000,
				minQuantity: 1,
				multipleQuantity: 1,
			},
			shippingAddressId: 0,
			sku: 'SKU1',
			skuId: 1001,
			skuUnitOfMeasure: {} as any,
			thumbnail: '/o/commerce-media/default/?groupId=33472',
		} as IOrderItem;

		const renderedComponent = render(
			<OrderItemRow
				deliveryGroups={deliveryGroups}
				handleSubmit={handleSubmit}
				orderId={10}
				orderItem={orderItem as any}
			/>
		);

		const {deliveryGroup1Input, deliveryGroup2Input, quantityCell} =
			getLocators(deliveryGroups, orderItem.id, renderedComponent);

		expect(quantityCell).toHaveTextContent(String(orderItem.quantity));
		expect(deliveryGroup1Input).toBeInTheDocument();
		expect(deliveryGroup1Input).not.toHaveValue();
		expect(deliveryGroup2Input).toBeInTheDocument();
		expect(deliveryGroup2Input).toHaveValue(4);
	});

	it('Must display both delivery group input fields filled', async () => {
		const deliveryGroups = [
			{
				addressId: 100,
				deliveryDate: '',
				id: 10000,
				name: 'DeliveryGroup1',
			},
			{
				addressId: 100,
				deliveryDate: '',
				id: 10001,
				name: 'DeliveryGroup2',
			},
		];

		const orderItem: IOrderItem = {
			deliveryGroups: {
				10000: {
					options: '[]',
					orderItemId: 100,
					originalQuantity: 4,
					quantity: 4,
					replacedSkuId: 0,
					skuId: 1001,
					skuUnitOfMeasure: {},
				},
				10001: {
					options: '[]',
					orderItemId: 101,
					originalQuantity: 4,
					quantity: 4,
					replacedSkuId: 0,
					skuId: 1001,
					skuUnitOfMeasure: {},
				},
			},
			id: 100,
			name: 'Product1',
			options: '[]',
			productId: 1000,
			quantity: 8,
			replacedSkuId: 0,
			requestedDeliveryDate: '',
			settings: {
				maxQuantity: 10000,
				minQuantity: 1,
				multipleQuantity: 1,
			},
			shippingAddressId: 0,
			sku: 'SKU1',
			skuId: 1001,
			skuUnitOfMeasure: {} as any,
			thumbnail: '/o/commerce-media/default/?groupId=33472',
		} as IOrderItem;

		const renderedComponent = render(
			<OrderItemRow
				deliveryGroups={deliveryGroups}
				handleSubmit={handleSubmit}
				orderId={10}
				orderItem={orderItem as any}
			/>
		);

		const {deliveryGroup1Input, deliveryGroup2Input, quantityCell} =
			getLocators(deliveryGroups, orderItem.id, renderedComponent);

		expect(quantityCell).toHaveTextContent(String(orderItem.quantity));
		expect(deliveryGroup1Input).toBeInTheDocument();
		expect(deliveryGroup1Input).toHaveValue(4);
		expect(deliveryGroup2Input).toBeInTheDocument();
		expect(deliveryGroup2Input).toHaveValue(4);
	});

	it('Must update quantity', async () => {
		fetchMock
			.patch(
				/headless-commerce-delivery-cart\/.*\/cart-items\/100/i,
				(): IOrderItem => {
					return {
						deliveryGroup: '10000',
						options: '[]',
						quantity: 10,
						replacedSkuId: 0,
						requestedDeliveryDate: '',
						shippingAddressId: 100,
						skuId: 1001,
					} as IOrderItem;
				}
			)
			.patch(
				/headless-commerce-delivery-cart\/.*\/cart-items\/101/i,
				(): IOrderItem => {
					return {
						deliveryGroup: '10001',
						options: '[]',
						quantity: 8,
						replacedSkuId: 0,
						requestedDeliveryDate: '',
						shippingAddressId: 100,
						skuId: 1001,
					} as IOrderItem;
				}
			);

		const handleSubmitWrapper = jest.fn((param: IOrderItem) => {
			handleSubmit(param.deliveryGroups);
		});

		const deliveryGroups = [
			{
				addressId: 100,
				deliveryDate: '',
				id: 10000,
				name: 'DeliveryGroup1',
			},
			{
				addressId: 100,
				deliveryDate: '',
				id: 10001,
				name: 'DeliveryGroup2',
			},
		];

		const orderItem: IOrderItem = {
			deliveryGroups: {
				10000: {
					options: '[]',
					orderItemId: 100,
					originalQuantity: 4,
					quantity: 4,
					replacedSkuId: 0,
					skuId: 1001,
					skuUnitOfMeasure: {},
				},
				10001: {
					options: '[]',
					orderItemId: 101,
					originalQuantity: 4,
					quantity: 4,
					replacedSkuId: 0,
					skuId: 1001,
					skuUnitOfMeasure: {},
				},
			},
			id: 100,
			name: 'Product1',
			options: '[]',
			productId: 1000,
			quantity: 8,
			replacedSkuId: 0,
			requestedDeliveryDate: '',
			settings: {
				maxQuantity: 10000,
				minQuantity: 1,
				multipleQuantity: 1,
			},
			shippingAddressId: 0,
			sku: 'SKU1',
			skuId: 1001,
			skuUnitOfMeasure: {} as any,
			thumbnail: '/o/commerce-media/default/?groupId=33472',
		} as IOrderItem;

		const renderedComponent = render(
			<OrderItemRow
				deliveryGroups={deliveryGroups}
				handleSubmit={handleSubmitWrapper}
				orderId={10}
				orderItem={orderItem as any}
			/>
		);

		const {deliveryGroup1Input, deliveryGroup2Input, quantityCell} =
			getLocators(deliveryGroups, orderItem.id, renderedComponent);

		expect(quantityCell).toHaveTextContent(String(orderItem.quantity));
		expect(deliveryGroup1Input).toBeInTheDocument();
		expect(deliveryGroup1Input).toHaveValue(4);
		expect(deliveryGroup2Input).toBeInTheDocument();
		expect(deliveryGroup2Input).toHaveValue(4);

		await setFieldValue(deliveryGroup1Input, 10);

		expect(quantityCell).toHaveTextContent(String(14));

		await waitFor(() => {
			expect(handleSubmit).toBeCalledWith({
				10000: {
					options: '[]',
					orderItemId: 100,
					originalQuantity: 10,
					quantity: 10,
					replacedSkuId: 0,
					skuId: 1001,
					skuUnitOfMeasure: {},
				},
				10001: {
					options: '[]',
					orderItemId: 101,
					originalQuantity: 4,
					quantity: 4,
					replacedSkuId: 0,
					skuId: 1001,
					skuUnitOfMeasure: {},
				},
			});
		});

		await setFieldValue(deliveryGroup2Input, 8);

		expect(quantityCell).toHaveTextContent(String(18));

		await waitFor(() => {
			expect(handleSubmit).toBeCalledWith({
				10000: {
					options: '[]',
					orderItemId: 100,
					originalQuantity: 10,
					quantity: 10,
					replacedSkuId: 0,
					skuId: 1001,
					skuUnitOfMeasure: {},
				},
				10001: {
					options: '[]',
					orderItemId: 101,
					originalQuantity: 8,
					quantity: 8,
					replacedSkuId: 0,
					skuId: 1001,
					skuUnitOfMeasure: {},
				},
			});
		});
	});

	it('Must save a new delivery group', async () => {
		fetchMock
			.post(
				/headless-commerce-delivery-cart\/.*\/carts\/.*\/items/i,
				(): IOrderItem => {
					return {
						deliveryGroup: '10001',
						id: 200,
						options: '[]',
						quantity: 20,
						replacedSkuId: 0,
						requestedDeliveryDate: '',
						shippingAddressId: 100,
						skuId: 1001,
					} as IOrderItem;
				}
			)
			.patch(
				/headless-commerce-delivery-cart\/.*\/cart-items\/200/i,
				(): IOrderItem => {
					return {
						deliveryGroup: '10001',
						options: '[]',
						quantity: 8,
						replacedSkuId: 0,
						requestedDeliveryDate: '',
						shippingAddressId: 100,
						skuId: 1001,
					} as IOrderItem;
				}
			);

		const handleSubmitWrapper = jest.fn((param: IOrderItem) => {
			handleSubmit(param.deliveryGroups);
		});

		const deliveryGroups = [
			{
				addressId: 100,
				deliveryDate: '',
				id: 10000,
				name: 'DeliveryGroup1',
			},
			{
				addressId: 100,
				deliveryDate: '',
				id: 10001,
				name: 'DeliveryGroup2',
			},
		];

		const orderItem: IOrderItem = {
			deliveryGroups: {
				10000: {
					options: '[]',
					orderItemId: 100,
					originalQuantity: 4,
					quantity: 4,
					replacedSkuId: 0,
					skuId: 1001,
					skuUnitOfMeasure: {},
				},
			},
			id: 100,
			name: 'Product1',
			options: '[]',
			productId: 1000,
			quantity: 4,
			replacedSkuId: 0,
			requestedDeliveryDate: '',
			settings: {
				maxQuantity: 10000,
				minQuantity: 1,
				multipleQuantity: 1,
			},
			shippingAddressId: 0,
			sku: 'SKU1',
			skuId: 1001,
			skuUnitOfMeasure: {} as any,
			thumbnail: '/o/commerce-media/default/?groupId=33472',
		} as IOrderItem;

		const renderedComponent = render(
			<OrderItemRow
				deliveryGroups={deliveryGroups}
				handleSubmit={handleSubmitWrapper}
				orderId={10}
				orderItem={orderItem as any}
			/>
		);

		const {deliveryGroup2Input, quantityCell} = getLocators(
			deliveryGroups,
			orderItem.id,
			renderedComponent
		);

		expect(quantityCell).toHaveTextContent(String(orderItem.quantity));
		expect(deliveryGroup2Input).toBeInTheDocument();
		expect(deliveryGroup2Input).not.toHaveValue();

		await setFieldValue(deliveryGroup2Input, 20);

		expect(quantityCell).toHaveTextContent(String(24));

		await waitFor(() => {
			expect(handleSubmit).toBeCalledWith({
				10000: {
					options: '[]',
					orderItemId: 100,
					originalQuantity: 4,
					quantity: 4,
					replacedSkuId: 0,
					skuId: 1001,
					skuUnitOfMeasure: {},
				},
				10001: {
					options: '[]',
					orderItemId: 200,
					originalQuantity: 20,
					quantity: 20,
					replacedSkuId: 0,
					skuId: 1001,
					skuUnitOfMeasure: {},
				},
			});
		});

		await setFieldValue(deliveryGroup2Input, 8);

		expect(quantityCell).toHaveTextContent(String(12));

		await waitFor(() => {
			expect(handleSubmit).toBeCalledWith({
				10000: {
					options: '[]',
					orderItemId: 100,
					originalQuantity: 4,
					quantity: 4,
					replacedSkuId: 0,
					skuId: 1001,
					skuUnitOfMeasure: {},
				},
				10001: {
					options: '[]',
					orderItemId: 200,
					originalQuantity: 8,
					quantity: 8,
					replacedSkuId: 0,
					skuId: 1001,
					skuUnitOfMeasure: {},
				},
			});
		});
	});

	it('Must delete a new delivery group', async () => {
		fetchMock.delete(
			/headless-commerce-delivery-cart\/.*\/cart-items\/.*/i,
			JSON.stringify({})
		);

		const handleSubmitWrapper = jest.fn((param: IOrderItem) => {
			handleSubmit(param.deliveryGroups);
		});

		const deliveryGroups = [
			{
				addressId: 100,
				deliveryDate: '',
				id: 10000,
				name: 'DeliveryGroup1',
			},
			{
				addressId: 100,
				deliveryDate: '',
				id: 10001,
				name: 'DeliveryGroup2',
			},
		];

		const orderItem: IOrderItem = {
			deliveryGroups: {
				10000: {
					options: '[]',
					orderItemId: 100,
					originalQuantity: 4,
					quantity: 4,
					replacedSkuId: 0,
					skuId: 1001,
					skuUnitOfMeasure: {},
				},
				10001: {
					options: '[]',
					orderItemId: 101,
					originalQuantity: 4,
					quantity: 4,
					replacedSkuId: 0,
					skuId: 1001,
					skuUnitOfMeasure: {},
				},
			},
			id: 100,
			name: 'Product1',
			options: '[]',
			productId: 1000,
			quantity: 8,
			replacedSkuId: 0,
			requestedDeliveryDate: '',
			settings: {
				maxQuantity: 10000,
				minQuantity: 1,
				multipleQuantity: 1,
			},
			shippingAddressId: 0,
			sku: 'SKU1',
			skuId: 1001,
			skuUnitOfMeasure: {} as any,
			thumbnail: '/o/commerce-media/default/?groupId=33472',
		} as IOrderItem;

		const renderedComponent = render(
			<OrderItemRow
				deliveryGroups={deliveryGroups}
				handleSubmit={handleSubmitWrapper}
				orderId={10}
				orderItem={orderItem as any}
			/>
		);

		const {deliveryGroup1Input, deliveryGroup2Input, quantityCell} =
			getLocators(deliveryGroups, orderItem.id, renderedComponent);

		expect(quantityCell).toHaveTextContent(String(orderItem.quantity));
		expect(deliveryGroup1Input).toBeInTheDocument();
		expect(deliveryGroup1Input).toHaveValue(4);
		expect(deliveryGroup2Input).toBeInTheDocument();
		expect(deliveryGroup2Input).toHaveValue(4);

		await setFieldValue(deliveryGroup1Input, 0);

		expect(quantityCell).toHaveTextContent(String(4));

		await waitFor(() => {
			expect(handleSubmit).toBeCalledWith({
				10001: {
					options: '[]',
					orderItemId: 101,
					originalQuantity: 4,
					quantity: 4,
					replacedSkuId: 0,
					skuId: 1001,
					skuUnitOfMeasure: {},
				},
			});
		});

		await setFieldValue(deliveryGroup2Input, 0);

		expect(quantityCell).toHaveTextContent(String(0));

		await waitFor(() => {
			expect(handleSubmit).toBeCalledWith({});
		});
	});

	it('Must open sku modal detail', async () => {
		const orderItem: IOrderItem = {
			id: 100,
			name: 'Product1',
			options: '[]',
			productId: 1000,
			quantity: 8,
			replacedSkuId: 0,
			requestedDeliveryDate: '',
			settings: {
				maxQuantity: 10000,
				minQuantity: 1,
				multipleQuantity: 1,
			},
			shippingAddressId: 0,
			sku: 'SKU1',
			skuId: 1001,
			skuUnitOfMeasure: {} as any,
			thumbnail: '/o/commerce-media/default/?groupId=33472',
		} as IOrderItem;

		const renderedComponent = render(
			<OrderItemRow
				deliveryGroups={[]}
				handleSubmit={handleSubmit}
				orderId={10}
				orderItem={orderItem as any}
			/>
		);

		const skuName = renderedComponent.getByText(orderItem.sku as string);

		await act(async () => {
			skuName.click();
		});

		await waitFor(() => {
			expect(
				renderedComponent.getByText(`view ${orderItem.sku} details`)
			).toBeVisible();
		});
	});
});
