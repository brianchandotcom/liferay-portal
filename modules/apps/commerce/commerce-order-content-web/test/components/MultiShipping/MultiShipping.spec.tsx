/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import fetchMock from 'fetch-mock';

import {
	IOrderItem,
	IOrderItemAPIResponse,
} from '../../../src/main/resources/META-INF/resources/js/multi_shipping/Types';

import '@testing-library/jest-dom/extend-expect';
import {RenderResult, cleanup, render, waitFor} from '@testing-library/react';
import React from 'react';
import {act} from 'react-dom/test-utils';

import MultiShipping from '../../../src/main/resources/META-INF/resources/js/multi_shipping/MultiShipping';
import {setFieldValue} from '../../utils/utils.spec';

interface ILocators {
	addDeliveryGroupButton: HTMLButtonElement;
	loadingSpinner: HTMLElement;
	orderItem1Row: HTMLTableRowElement;
	orderItem2Row: HTMLTableRowElement;
	searchInput: HTMLInputElement;
}

const getLocators = (
	orderItems: Array<IOrderItem>,
	renderedComponent: RenderResult
): ILocators => {
	return {
		addDeliveryGroupButton: renderedComponent.queryByRole('button', {
			name: 'add-delivery-group',
		}) as HTMLButtonElement,
		loadingSpinner: renderedComponent.queryByTestId(
			'loadingSpinner'
		) as HTMLInputElement,
		orderItem1Row: renderedComponent.queryByTestId(
			`orderItem${orderItems[0].id}Row`
		) as HTMLTableRowElement,
		orderItem2Row: renderedComponent.queryByTestId(
			`orderItem${orderItems[1].id}Row`
		) as HTMLTableRowElement,
		searchInput: renderedComponent.queryByRole('textbox', {
			name: 'search',
		}) as HTMLInputElement,
	};
};

describe('MultiShipping', () => {
	afterEach(() => {
		fetchMock.restore();
		jest.clearAllMocks();

		cleanup();
	});

	it('Must display order items with no delivery groups', async () => {
		const orderItems = Array(2)
			.fill(0)
			.map((_, currentIndex) => {
				return {
					deliveryGroup: `deliveryGroup${currentIndex}`,
					id: 1000 + currentIndex,
					name: `ProductName${currentIndex}`,
					options: '[]',
					quantity: 3 + currentIndex,
					replacedSkuId: 0,
					settings: {
						maxQuantity: 10000,
						minQuantity: 1,
						multipleQuantity: 1,
					},
					shippingAddressId: 1000 + currentIndex,
					sku: `SKU${currentIndex}`,
					skuId: 100 + currentIndex,
					thumbnail: '/o/commerce-media/default/?groupId=33472',
				};
			}) as Array<IOrderItem>;

		fetchMock.get(
			/headless-commerce-delivery-cart\/.*\/carts\/.*\/items\?.*/i,
			(): IOrderItemAPIResponse => {
				return {
					items: orderItems,
				} as IOrderItemAPIResponse;
			}
		);

		const renderedComponent = render(
			<MultiShipping accountId={10} orderId={10} />
		);

		const {addDeliveryGroupButton, loadingSpinner, searchInput} =
			getLocators(orderItems, renderedComponent);

		expect(addDeliveryGroupButton).toBeVisible();
		expect(addDeliveryGroupButton).toBeDisabled();
		expect(loadingSpinner).toBeVisible();
		expect(searchInput).toBeVisible();
		expect(searchInput).toBeDisabled();

		await waitFor(() => {
			expect(loadingSpinner).not.toBeInTheDocument();
		});

		expect(addDeliveryGroupButton).toBeEnabled();
		expect(searchInput).toBeEnabled();

		const {orderItem1Row, orderItem2Row} = getLocators(
			orderItems,
			renderedComponent
		);

		expect(orderItem1Row).toBeVisible();
		expect(orderItem2Row).toBeVisible();
	});

	it('Must paginate the order items', async () => {
		const orderItems = Array(5)
			.fill(0)
			.map((_, currentIndex) => {
				return {
					deliveryGroup: `deliveryGroup${currentIndex}`,
					id: 1000 + currentIndex,
					name: `ProductName${currentIndex}`,
					options: '[]',
					quantity: 3 + currentIndex,
					replacedSkuId: 0,
					settings: {
						maxQuantity: 10000,
						minQuantity: 1,
						multipleQuantity: 1,
					},
					shippingAddressId: 1000 + currentIndex,
					sku: `SKU${currentIndex}`,
					skuId: 100 + currentIndex,
					thumbnail: '/o/commerce-media/default/?groupId=33472',
				};
			}) as Array<IOrderItem>;

		fetchMock.get(
			/headless-commerce-delivery-cart\/.*\/carts\/.*\/items\?.*/i,
			(): IOrderItemAPIResponse => {
				return {
					items: orderItems,
				} as IOrderItemAPIResponse;
			}
		);

		const renderedComponent = render(
			<MultiShipping accountId={10} orderId={10} />
		);

		const {loadingSpinner} = getLocators(orderItems, renderedComponent);

		await waitFor(() => {
			expect(loadingSpinner).not.toBeInTheDocument();
		});

		expect(
			renderedComponent.queryByTestId(`orderItem${orderItems[0].id}Row`)
		).toBeVisible();
		expect(
			renderedComponent.queryByTestId(`orderItem${orderItems[1].id}Row`)
		).toBeVisible();
		expect(
			renderedComponent.queryByTestId(`orderItem${orderItems[2].id}Row`)
		).toBeVisible();
		expect(
			renderedComponent.queryByTestId(`orderItem${orderItems[3].id}Row`)
		).toBeVisible();
		expect(
			renderedComponent.queryByTestId(`orderItem${orderItems[4].id}Row`)
		).toBeVisible();

		await act(async () => {
			expect(
				renderedComponent.queryByRole('combobox', {
					name: 'Items Per Page',
				})
			).not.toBeNull();
			(
				renderedComponent.queryByRole('combobox', {
					name: 'Items Per Page',
				}) as HTMLButtonElement
			).click();
		});

		await waitFor(() => {
			expect(
				renderedComponent.queryByRole('option', {name: '4 items'})
			).toBeVisible();
		});

		await act(async () => {
			expect(
				renderedComponent.queryByRole('option', {name: '4 items'})
			).not.toBeNull();
			(
				renderedComponent.queryByRole('option', {
					name: '4 items',
				}) as HTMLButtonElement
			).click();
		});

		expect(
			renderedComponent.queryByTestId(`orderItem${orderItems[0].id}Row`)
		).toBeVisible();
		expect(
			renderedComponent.queryByTestId(`orderItem${orderItems[1].id}Row`)
		).toBeVisible();
		expect(
			renderedComponent.queryByTestId(`orderItem${orderItems[2].id}Row`)
		).toBeVisible();
		expect(
			renderedComponent.queryByTestId(`orderItem${orderItems[3].id}Row`)
		).toBeVisible();
		expect(
			renderedComponent.queryByTestId(`orderItem${orderItems[4].id}Row`)
		).toBeNull();

		await act(async () => {
			expect(
				renderedComponent.queryByRole('button', {
					name: 'Go to the next page, 2',
				})
			).not.toBeNull();
			(
				renderedComponent.queryByRole('button', {
					name: 'Go to the next page, 2',
				}) as HTMLButtonElement
			).click();
		});

		expect(
			renderedComponent.queryByTestId(`orderItem${orderItems[0].id}Row`)
		).toBeNull();
		expect(
			renderedComponent.queryByTestId(`orderItem${orderItems[1].id}Row`)
		).toBeNull();
		expect(
			renderedComponent.queryByTestId(`orderItem${orderItems[2].id}Row`)
		).toBeNull();
		expect(
			renderedComponent.queryByTestId(`orderItem${orderItems[3].id}Row`)
		).toBeNull();
		expect(
			renderedComponent.queryByTestId(`orderItem${orderItems[4].id}Row`)
		).toBeVisible();
	});

	it('Table must be searchable', async () => {
		const orderItems = Array(2)
			.fill(0)
			.map((_, currentIndex) => {
				return {
					deliveryGroup: `deliveryGroup${currentIndex}`,
					id: 1000 + currentIndex,
					name: `ProductName${currentIndex}`,
					options: '[]',
					quantity: 3 + currentIndex,
					replacedSkuId: 0,
					settings: {
						maxQuantity: 10000,
						minQuantity: 1,
						multipleQuantity: 1,
					},
					shippingAddressId: 1000 + currentIndex,
					sku: `SKU${currentIndex}`,
					skuId: 100 + currentIndex,
					thumbnail: '/o/commerce-media/default/?groupId=33472',
				};
			}) as Array<IOrderItem>;

		fetchMock.get(
			/headless-commerce-delivery-cart\/.*\/carts\/.*\/items\?.*/i,
			(): IOrderItemAPIResponse => {
				return {
					items: orderItems,
				} as IOrderItemAPIResponse;
			}
		);

		const renderedComponent = render(
			<MultiShipping accountId={10} orderId={10} />
		);

		const {loadingSpinner, searchInput} = getLocators(
			orderItems,
			renderedComponent
		);

		await waitFor(() => {
			expect(loadingSpinner).not.toBeInTheDocument();
		});

		expect(
			renderedComponent.queryByTestId(`orderItem${orderItems[0].id}Row`)
		).toBeVisible();
		expect(
			renderedComponent.queryByTestId(`orderItem${orderItems[1].id}Row`)
		).toBeVisible();

		await setFieldValue(searchInput, orderItems[0].name as string);

		expect(
			renderedComponent.queryByTestId(`orderItem${orderItems[0].id}Row`)
		).toBeVisible();
		expect(
			renderedComponent.queryByTestId(`orderItem${orderItems[1].id}Row`)
		).toBeNull();

		await setFieldValue(searchInput, orderItems[1].sku as string);

		expect(
			renderedComponent.queryByTestId(`orderItem${orderItems[0].id}Row`)
		).toBeNull();
		expect(
			renderedComponent.queryByTestId(`orderItem${orderItems[1].id}Row`)
		).toBeVisible();

		await setFieldValue(searchInput, 'S');

		expect(
			renderedComponent.queryByTestId(`orderItem${orderItems[0].id}Row`)
		).toBeVisible();
		expect(
			renderedComponent.queryByTestId(`orderItem${orderItems[1].id}Row`)
		).toBeVisible();
	});

	it('Must disable add delivery group if 20 are already available', async () => {
		const orderItems = Array(20)
			.fill(0)
			.map((_, currentIndex) => {
				return {
					deliveryGroup: `deliveryGroup${currentIndex}`,
					id: 1000 + currentIndex,
					name: `ProductName${currentIndex}`,
					options: '[]',
					quantity: 3 + currentIndex,
					replacedSkuId: 0,
					settings: {
						maxQuantity: 10000,
						minQuantity: 1,
						multipleQuantity: 1,
					},
					shippingAddressId: 1000 + currentIndex,
					sku: `SKU${currentIndex}`,
					skuId: 100 + currentIndex,
					thumbnail: '/o/commerce-media/default/?groupId=33472',
				};
			}) as Array<IOrderItem>;

		fetchMock.get(
			/headless-commerce-delivery-cart\/.*\/carts\/.*\/items\?.*/i,
			(): IOrderItemAPIResponse => {
				return {
					items: orderItems,
				} as IOrderItemAPIResponse;
			}
		);

		const renderedComponent = render(
			<MultiShipping accountId={10} orderId={10} />
		);

		const {addDeliveryGroupButton, loadingSpinner} = getLocators(
			orderItems,
			renderedComponent
		);

		await waitFor(() => {
			expect(loadingSpinner).not.toBeInTheDocument();
		});

		expect(addDeliveryGroupButton).toBeDisabled();
	});

	it('Must disable everything if readonly', async () => {
		const orderItems = Array(2)
			.fill(0)
			.map((_, currentIndex) => {
				return {
					deliveryGroup: `deliveryGroup${currentIndex}`,
					id: 1000 + currentIndex,
					name: `ProductName${currentIndex}`,
					options: '[]',
					quantity: 3 + currentIndex,
					replacedSkuId: 0,
					settings: {
						maxQuantity: 10000,
						minQuantity: 1,
						multipleQuantity: 1,
					},
					shippingAddressId: 1000 + currentIndex,
					sku: `SKU${currentIndex}`,
					skuId: 100 + currentIndex,
					thumbnail: '/o/commerce-media/default/?groupId=33472',
				};
			}) as Array<IOrderItem>;

		fetchMock.get(
			/headless-commerce-delivery-cart\/.*\/carts\/.*\/items\?.*/i,
			(): IOrderItemAPIResponse => {
				return {
					items: orderItems,
				} as IOrderItemAPIResponse;
			}
		);

		const renderedComponent = render(
			<MultiShipping accountId={10} orderId={10} readonly={true} />
		);

		const {addDeliveryGroupButton, loadingSpinner} = getLocators(
			orderItems,
			renderedComponent
		);

		await waitFor(() => {
			expect(loadingSpinner).not.toBeInTheDocument();
		});

		expect(addDeliveryGroupButton).toBeDisabled();

		renderedComponent
			.getAllByTestId(/orderItem.*Input/)
			.forEach((input) => expect(input).toBeDisabled());

		renderedComponent
			.getAllByTestId(/deliveryGroup.*Actions/)
			.forEach((input) => expect(input).toBeDisabled());
	});

	it('Table should not display delivery group columns', async () => {
		const orderItems = Array(2)
			.fill(0)
			.map((_, currentIndex) => {
				return {
					id: 1000 + currentIndex,
					name: `ProductName${currentIndex}`,
					options: '[]',
					quantity: 3 + currentIndex,
					replacedSkuId: 0,
					settings: {
						maxQuantity: 10000,
						minQuantity: 1,
						multipleQuantity: 1,
					},
					sku: `SKU${currentIndex}`,
					skuId: 100 + currentIndex,
					thumbnail: '/o/commerce-media/default/?groupId=33472',
				};
			}) as Array<IOrderItem>;

		fetchMock.get(
			/headless-commerce-delivery-cart\/.*\/carts\/.*\/items\?.*/i,
			(): IOrderItemAPIResponse => {
				return {
					items: orderItems,
				} as IOrderItemAPIResponse;
			}
		);

		const renderedComponent = render(
			<MultiShipping accountId={10} orderId={10} />
		);

		const {loadingSpinner} = getLocators(orderItems, renderedComponent);

		await waitFor(() => {
			expect(loadingSpinner).not.toBeInTheDocument();
		});

		expect(
			renderedComponent.queryAllByTestId(/orderItem.*Input/)
		).toHaveLength(0);
	});

	it('Table should display delivery group column with correct quantities', async () => {
		const orderItems = Array(3)
			.fill(0)
			.map((_, currentIndex) => {
				return {
					deliveryGroup: `deliveryGroup`,
					id: 1000 + currentIndex,
					name: `ProductName${currentIndex}`,
					options: '[]',
					quantity: 3 + currentIndex,
					replacedSkuId: 0,
					settings: {
						maxQuantity: 10000,
						minQuantity: 1,
						multipleQuantity: 1,
					},
					shippingAddressId: 1000,
					sku: `SKU${currentIndex}`,
					skuId: 100 + currentIndex,
					thumbnail: '/o/commerce-media/default/?groupId=33472',
				};
			}) as Array<IOrderItem>;

		fetchMock.get(
			/headless-commerce-delivery-cart\/.*\/carts\/.*\/items\?.*/i,
			(): IOrderItemAPIResponse => {
				return {
					items: orderItems,
				} as IOrderItemAPIResponse;
			}
		);

		const renderedComponent = render(
			<MultiShipping accountId={10} orderId={10} />
		);

		const {loadingSpinner} = getLocators(orderItems, renderedComponent);

		await waitFor(() => {
			expect(loadingSpinner).not.toBeInTheDocument();
		});

		expect(
			renderedComponent.queryAllByTestId(/deliveryGroup.*Actions/)
		).toHaveLength(1);

		renderedComponent
			.queryAllByTestId(/orderItem.*Input/)
			.forEach((input, index) => {
				expect(input).toHaveValue(3 + index);
			});
	});

	it('Table should display same product in multiple delivery groups', async () => {
		const orderItems = [
			{
				deliveryGroup: `deliveryGroup1`,
				id: 1000,
				name: `ProductName1`,
				options: '[]',
				quantity: 3,
				replacedSkuId: 0,
				settings: {
					maxQuantity: 10000,
					minQuantity: 1,
					multipleQuantity: 1,
				},
				shippingAddressId: 1000,
				sku: `SKU1`,
				skuId: 100,
				thumbnail: '/o/commerce-media/default/?groupId=33472',
			},
			{
				deliveryGroup: `deliveryGroup2`,
				id: 1001,
				name: `ProductName1`,
				options: '[]',
				quantity: 5,
				replacedSkuId: 0,
				settings: {
					maxQuantity: 10000,
					minQuantity: 1,
					multipleQuantity: 1,
				},
				shippingAddressId: 1001,
				sku: `SKU1`,
				skuId: 100,
				thumbnail: '/o/commerce-media/default/?groupId=33472',
			},
			{
				deliveryGroup: `deliveryGroup2`,
				id: 1002,
				name: `ProductName2`,
				options: '[]',
				quantity: 8,
				replacedSkuId: 0,
				settings: {
					maxQuantity: 10000,
					minQuantity: 1,
					multipleQuantity: 1,
				},
				shippingAddressId: 1001,
				sku: `SKU2`,
				skuId: 102,
				thumbnail: '/o/commerce-media/default/?groupId=33472',
			},
		] as Array<IOrderItem>;

		fetchMock.get(
			/headless-commerce-delivery-cart\/.*\/carts\/.*\/items\?.*/i,
			(): IOrderItemAPIResponse => {
				return {
					items: orderItems,
				} as IOrderItemAPIResponse;
			}
		);

		const renderedComponent = render(
			<MultiShipping accountId={10} orderId={10} />
		);

		const {loadingSpinner} = getLocators(orderItems, renderedComponent);

		await waitFor(() => {
			expect(loadingSpinner).not.toBeInTheDocument();
		});

		expect(
			renderedComponent.queryAllByTestId(/deliveryGroup.*Actions/)
		).toHaveLength(2);

		const orderItem1Inputs =
			renderedComponent.queryAllByTestId(/orderItem1000.*Input/);

		expect(orderItem1Inputs[0]).toHaveValue(3);
		expect(orderItem1Inputs[1]).toHaveValue(5);

		const orderItem2Inputs =
			renderedComponent.queryAllByTestId(/orderItem1002.*Input/);

		expect(orderItem2Inputs[0]).not.toHaveValue();
		expect(orderItem2Inputs[1]).toHaveValue(8);
	});

	it('Must fix missing delivery groups', async () => {
		const orderItems = [
			{
				deliveryGroup: `deliveryGroup1`,
				id: 1000,
				name: `ProductName1`,
				options: '[]',
				quantity: 3,
				replacedSkuId: 0,
				settings: {
					maxQuantity: 10000,
					minQuantity: 1,
					multipleQuantity: 1,
				},
				shippingAddressId: 1000,
				sku: `SKU1`,
				skuId: 100,
				thumbnail: '/o/commerce-media/default/?groupId=33472',
			},
			{
				deliveryGroup: '',
				id: 1001,
				name: `ProductName1`,
				options: '[]',
				quantity: 5,
				replacedSkuId: 0,
				settings: {
					maxQuantity: 10000,
					minQuantity: 1,
					multipleQuantity: 1,
				},
				shippingAddressId: 0,
				sku: `SKU1`,
				skuId: 100,
				thumbnail: '/o/commerce-media/default/?groupId=33472',
			},
			{
				deliveryGroup: `deliveryGroup1`,
				id: 1002,
				name: `ProductName2`,
				options: '[]',
				quantity: 8,
				replacedSkuId: 0,
				settings: {
					maxQuantity: 10000,
					minQuantity: 1,
					multipleQuantity: 1,
				},
				shippingAddressId: 1000,
				sku: `SKU2`,
				skuId: 102,
				thumbnail: '/o/commerce-media/default/?groupId=33472',
			},
		] as Array<IOrderItem>;

		const mockFetch = jest
			.fn()
			.mockReturnValueOnce(() => {
				return {
					items: orderItems,
				} as IOrderItemAPIResponse;
			})
			.mockReturnValue(() => {
				orderItems[1].deliveryGroup = 'deliveryGroup1';
				orderItems[1].shippingAddressId = 1000;

				return {
					cartItems: orderItems,
				} as IOrderItemAPIResponse;
			});

		fetchMock.get(
			/headless-commerce-delivery-cart\/.*\/carts\/.*\/items\?.*/i,
			mockFetch
		);

		fetchMock.patch(
			/headless-commerce-delivery-cart\/.*\/carts\/.*\?.*/i,
			mockFetch
		);

		const renderedComponent = render(
			<MultiShipping accountId={10} orderId={10} />
		);

		const {loadingSpinner} = getLocators(orderItems, renderedComponent);

		await waitFor(() => {
			expect(loadingSpinner).not.toBeInTheDocument();
		});

		expect(
			renderedComponent.queryAllByTestId(/deliveryGroup.*Actions/)
		).toHaveLength(1);
		expect(
			renderedComponent.queryAllByTestId(/orderItem.*Row/)
		).toHaveLength(3);
		expect(
			renderedComponent.queryByTestId(/orderItem1001.*Input/)
		).toHaveValue(5);
	});

	it('Must show correct display groups for different products', async () => {
		const orderItems = [
			{
				deliveryGroup: `deliveryGroup1`,
				id: 1000,
				name: `ProductName1`,
				options: '[]',
				quantity: 3,
				replacedSkuId: 0,
				requestedDeliveryDate: '2024-12-12',
				settings: {
					maxQuantity: 10000,
					minQuantity: 1,
					multipleQuantity: 1,
				},
				shippingAddressId: 1000,
				sku: `SKU1`,
				skuId: 100,
				thumbnail: '/o/commerce-media/default/?groupId=33472',
			},
			{
				deliveryGroup: `deliveryGroup1`,
				id: 1002,
				name: `ProductName2`,
				options: '[]',
				quantity: 8,
				replacedSkuId: 0,
				requestedDeliveryDate: '2024-12-12',
				settings: {
					maxQuantity: 10000,
					minQuantity: 1,
					multipleQuantity: 1,
				},
				shippingAddressId: 1000,
				sku: `SKU2`,
				skuId: 102,
				thumbnail: '/o/commerce-media/default/?groupId=33472',
			},
		] as Array<IOrderItem>;

		const mockFetch = jest
			.fn()
			.mockReturnValueOnce(() => {
				return {
					items: orderItems,
				} as IOrderItemAPIResponse;
			})
			.mockReturnValueOnce(() => {
				orderItems[1].deliveryGroup = 'deliveryGroup2';
				orderItems[1].requestedDeliveryDate = '2024-12-12';
				orderItems[1].shippingAddressId = 1000;

				return {
					items: orderItems,
				} as IOrderItemAPIResponse;
			})
			.mockReturnValueOnce(() => {
				orderItems[1].deliveryGroup = 'deliveryGroup1';
				orderItems[1].requestedDeliveryDate = '2024-12-13';
				orderItems[1].shippingAddressId = 1000;

				return {
					items: orderItems,
				} as IOrderItemAPIResponse;
			})
			.mockReturnValueOnce(() => {
				orderItems[1].deliveryGroup = 'deliveryGroup1';
				orderItems[1].requestedDeliveryDate = '2024-12-12';
				orderItems[1].shippingAddressId = 1001;

				return {
					items: orderItems,
				} as IOrderItemAPIResponse;
			});

		fetchMock.get(
			/headless-commerce-delivery-cart\/.*\/carts\/.*\/items\?.*/i,
			mockFetch
		);

		let renderedComponent = render(
			<MultiShipping accountId={10} orderId={10} />
		);

		let {loadingSpinner} = getLocators(orderItems, renderedComponent);

		await waitFor(() => {
			expect(loadingSpinner).not.toBeInTheDocument();
		});

		expect(
			renderedComponent.queryAllByTestId(/deliveryGroup.*Actions/)
		).toHaveLength(1);
		expect(
			renderedComponent.queryAllByTestId(/orderItem.*Row/)
		).toHaveLength(2);

		renderedComponent.unmount();

		renderedComponent = render(
			<MultiShipping accountId={10} orderId={10} />
		);

		({loadingSpinner} = getLocators(orderItems, renderedComponent));

		await waitFor(() => {
			expect(loadingSpinner).not.toBeInTheDocument();
		});

		expect(
			renderedComponent.queryAllByTestId(/deliveryGroup.*Actions/)
		).toHaveLength(2);
		expect(
			renderedComponent.queryAllByTestId(/orderItem.*Row/)
		).toHaveLength(2);

		renderedComponent.unmount();

		renderedComponent = render(
			<MultiShipping accountId={10} orderId={10} />
		);

		({loadingSpinner} = getLocators(orderItems, renderedComponent));

		await waitFor(() => {
			expect(loadingSpinner).not.toBeInTheDocument();
		});

		expect(
			renderedComponent.queryAllByTestId(/deliveryGroup.*Actions/)
		).toHaveLength(2);
		expect(
			renderedComponent.queryAllByTestId(/orderItem.*Row/)
		).toHaveLength(2);

		renderedComponent.unmount();

		renderedComponent = render(
			<MultiShipping accountId={10} orderId={10} />
		);

		({loadingSpinner} = getLocators(orderItems, renderedComponent));

		await waitFor(() => {
			expect(loadingSpinner).not.toBeInTheDocument();
		});

		expect(
			renderedComponent.queryAllByTestId(/deliveryGroup.*Actions/)
		).toHaveLength(2);
		expect(
			renderedComponent.queryAllByTestId(/orderItem.*Row/)
		).toHaveLength(2);
	});

	it('Must show correct display groups for same product', async () => {
		const orderItems = [
			{
				deliveryGroup: `deliveryGroup1`,
				id: 1000,
				name: `ProductName1`,
				options: '[]',
				quantity: 3,
				replacedSkuId: 0,
				requestedDeliveryDate: '2024-12-12',
				settings: {
					maxQuantity: 10000,
					minQuantity: 1,
					multipleQuantity: 1,
				},
				shippingAddressId: 1000,
				sku: `SKU1`,
				skuId: 100,
				thumbnail: '/o/commerce-media/default/?groupId=33472',
			},
			{
				deliveryGroup: `deliveryGroup1`,
				id: 1002,
				name: `ProductName1`,
				options: '[]',
				quantity: 8,
				replacedSkuId: 0,
				requestedDeliveryDate: '2024-12-12',
				settings: {
					maxQuantity: 10000,
					minQuantity: 1,
					multipleQuantity: 1,
				},
				shippingAddressId: 1000,
				sku: `SKU1`,
				skuId: 100,
				thumbnail: '/o/commerce-media/default/?groupId=33472',
			},
		] as Array<IOrderItem>;

		const mockFetch = jest
			.fn()
			.mockReturnValueOnce(() => {
				return {
					items: orderItems,
				} as IOrderItemAPIResponse;
			})
			.mockReturnValueOnce(() => {
				orderItems[1].deliveryGroup = 'deliveryGroup2';
				orderItems[1].requestedDeliveryDate = '2024-12-12';
				orderItems[1].shippingAddressId = 1000;

				return {
					items: orderItems,
				} as IOrderItemAPIResponse;
			})
			.mockReturnValueOnce(() => {
				orderItems[1].deliveryGroup = 'deliveryGroup1';
				orderItems[1].requestedDeliveryDate = '2024-12-13';
				orderItems[1].shippingAddressId = 1000;

				return {
					items: orderItems,
				} as IOrderItemAPIResponse;
			})
			.mockReturnValueOnce(() => {
				orderItems[1].deliveryGroup = 'deliveryGroup1';
				orderItems[1].requestedDeliveryDate = '2024-12-12';
				orderItems[1].shippingAddressId = 1001;

				return {
					items: orderItems,
				} as IOrderItemAPIResponse;
			});

		fetchMock.get(
			/headless-commerce-delivery-cart\/.*\/carts\/.*\/items\?.*/i,
			mockFetch
		);

		let renderedComponent = render(
			<MultiShipping accountId={10} orderId={10} />
		);

		let {loadingSpinner} = getLocators(orderItems, renderedComponent);

		await waitFor(() => {
			expect(loadingSpinner).not.toBeInTheDocument();
		});

		expect(
			renderedComponent.queryAllByTestId(/deliveryGroup.*Actions/)
		).toHaveLength(1);
		expect(
			renderedComponent.queryAllByTestId(/orderItem.*Row/)
		).toHaveLength(2);

		renderedComponent.unmount();

		renderedComponent = render(
			<MultiShipping accountId={10} orderId={10} />
		);

		({loadingSpinner} = getLocators(orderItems, renderedComponent));

		await waitFor(() => {
			expect(loadingSpinner).not.toBeInTheDocument();
		});

		expect(
			renderedComponent.queryAllByTestId(/deliveryGroup.*Actions/)
		).toHaveLength(2);
		expect(
			renderedComponent.queryAllByTestId(/orderItem.*Row/)
		).toHaveLength(1);

		renderedComponent.unmount();

		renderedComponent = render(
			<MultiShipping accountId={10} orderId={10} />
		);

		({loadingSpinner} = getLocators(orderItems, renderedComponent));

		await waitFor(() => {
			expect(loadingSpinner).not.toBeInTheDocument();
		});

		expect(
			renderedComponent.queryAllByTestId(/deliveryGroup.*Actions/)
		).toHaveLength(2);
		expect(
			renderedComponent.queryAllByTestId(/orderItem.*Row/)
		).toHaveLength(1);

		renderedComponent.unmount();

		renderedComponent = render(
			<MultiShipping accountId={10} orderId={10} />
		);

		({loadingSpinner} = getLocators(orderItems, renderedComponent));

		await waitFor(() => {
			expect(loadingSpinner).not.toBeInTheDocument();
		});

		expect(
			renderedComponent.queryAllByTestId(/deliveryGroup.*Actions/)
		).toHaveLength(2);
		expect(
			renderedComponent.queryAllByTestId(/orderItem.*Row/)
		).toHaveLength(1);
	});
});
