/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '../../tests_utilities/polyfills';

import '@testing-library/jest-dom';
import {act, fireEvent, render, waitFor} from '@testing-library/react';
import React from 'react';

import ServiceProvider from '../../../src/main/resources/META-INF/resources/ServiceProvider/index';
import CartItem from '../../../src/main/resources/META-INF/resources/components/mini_cart/CartItem';
import MiniCartContext from '../../../src/main/resources/META-INF/resources/components/mini_cart/MiniCartContext';
import {
	REMOVAL_CANCELING_TIMEOUT,
	REMOVAL_TIMEOUT,
} from '../../../src/main/resources/META-INF/resources/components/mini_cart/util/constants';
import {CART_PRODUCT_QUANTITY_CHANGED} from '../../../src/main/resources/META-INF/resources/utilities/eventsDefinitions';
import {
	mockCartItem,
	mockCartItemWithBulkPrice,
	mockCartItemWithTierPrice,
} from '../fixtures/cartFixtures';

jest.mock(
	'../../../src/main/resources/META-INF/resources/ServiceProvider/index',
	() => {
		const cartApi = {
			deleteItemById: jest.fn(),
			updateItemById: jest.fn(),
		};

		return {
			__esModule: true,
			default: {
				DeliveryCartAPI: jest.fn(() => cartApi),
			},
		};
	}
);

describe('MiniCart Item', () => {
	const CartResource = ServiceProvider.DeliveryCartAPI('v1');

	const BASE_CONTEXT_MOCK = {
		actionURLs: {
			orderDetailURL: 'http://order-detail.url',
			productURLSeparator: 'p',
			siteDefaultURL: 'http://localhost:8080/group/siteDefaultUrl',
		},
		cartState: {
			id: 101,
		},
		displayDiscountLevels: false,
		setEditedItem: jest.fn(),
		setIsUpdating: jest.fn(),
		updateCartModel: jest.fn().mockReturnValue(Promise.resolve()),
	};

	const BASE_PROPS = {
		item: {
			adaptiveMediaImageHTMLTag: '',
			cartItems: [],
			id: 202,
			name: 'An Item',
			options: '[]',
			price: {
				currency: 'USD',
				discount: 0.0,
				discountFormatted: '$ 0.00',
				discountPercentage: '0.00',
				discountPercentageLevel1: 0.0,
				discountPercentageLevel2: 0.0,
				discountPercentageLevel3: 0.0,
				discountPercentageLevel4: 0.0,
				finalPrice: 8,
				finalPriceFormatted: '$ 8.00',
				price: 8,
				priceFormatted: '$ 8.00',
				promoPrice: 8,
				promoPriceFormatted: '$ 8.00',
			},
			productURLs: {
				en_US: 'u-joint',
			},
			quantity: 1,
			settings: {
				maxQuantity: 2,
				minQuantity: 1,
				multipleQuantity: 1,
			},
			sku: 'ITEM0001',
			skuId: 10001,
			thumbnail: '',
		},
		updateCartItem: jest.fn(),
	};

	const COMPONENT_SELECTOR = '.mini-cart-item';

	const REMOVAL_ANIMATION_MS = 1000;

	const renderCartItem = (props = BASE_PROPS, context = BASE_CONTEXT_MOCK) =>
		render(
			<MiniCartContext.Provider value={context}>
				<CartItem
					{...props.item}
					updateCartItem={props.updateCartItem}
				/>
			</MiniCartContext.Provider>
		);

	beforeEach(() => {
		jest.useFakeTimers();

		CartResource.deleteItemById.mockReturnValue(Promise.resolve());
		CartResource.updateItemById.mockReturnValue(Promise.resolve());

		BASE_CONTEXT_MOCK.setIsUpdating = jest.fn();
		BASE_CONTEXT_MOCK.updateCartModel = jest
			.fn()
			.mockReturnValue(Promise.resolve());

		window.Liferay = {
			Language: {
				get: jest.fn((text) => text),
			},

			detach: jest.fn(),
			fire: jest.fn(),
			on: jest.fn(),
		};
	});

	afterEach(() => {
		jest.resetAllMocks();
	});

	afterAll(() => {
		jest.useRealTimers();
	});

	describe('by default', () => {
		it(
			'renders the cart item with its information (ItemInfoView), ' +
				'quantity (QuantitySelector), price (Price), ' +
				'and a button to remove it from the cart, ' +
				'plus a hidden div to handle the item removal cancellation',
			() => {
				const {container} = renderCartItem();

				const CartItemElement =
					container.querySelector(COMPONENT_SELECTOR);

				expect(CartItemElement.innerHTML).toMatchSnapshot();
			}
		);

		it("...also with the item's thumbnail", () => {
			const WITH_THUMBNAIL_PROPS = {
				...BASE_PROPS,
				item: {
					...BASE_PROPS.item,
					adaptiveMediaImageHTMLTag:
						'<picture><img src="http://some.url/thumbnail.png" /></picture>',
				},
			};

			const {container} = renderCartItem(WITH_THUMBNAIL_PROPS);

			const CartItemElement = container.querySelector(COMPONENT_SELECTOR);
			const CartItemThumbnailElement = CartItemElement.querySelector(
				`${COMPONENT_SELECTOR}-thumbnail`
			);

			expect(CartItemThumbnailElement).toBeInTheDocument();
			expect(CartItemElement.innerHTML).toMatchSnapshot();
		});

		it('renders the product image with its alt attribute in the thumbnail', () => {
			const WITH_IMAGE_ALT_PROPS = {
				...BASE_PROPS,
				item: {
					...BASE_PROPS.item,
					adaptiveMediaImageHTMLTag:
						'<picture><img alt="An Item" src="http://some.url/thumbnail.png" /></picture>',
				},
			};

			const {container} = renderCartItem(WITH_IMAGE_ALT_PROPS);

			const thumbnailImage = container.querySelector(
				`${COMPONENT_SELECTOR}-thumbnail img`
			);

			expect(thumbnailImage).toHaveAttribute('alt', 'An Item');
		});

		it('redirects to the product page when the cart item is clicked', () => {
			const {getByRole} = renderCartItem();

			expect(getByRole('link').href).toBe(
				BASE_CONTEXT_MOCK.actionURLs.siteDefaultURL +
					'/' +
					BASE_CONTEXT_MOCK.actionURLs.productURLSeparator +
					'/' +
					BASE_PROPS.item.productURLs.en_US
			);
		});

		it('does not redirect to the product page when the quantity selector is clicked', () => {
			const {getByRole} = renderCartItem();

			const mockClick = jest.fn();

			getByRole('link').addEventListener('click', mockClick);

			fireEvent.click(getByRole('spinbutton'));
			fireEvent.change(getByRole('spinbutton'), {target: {value: '12'}});

			expect(mockClick).not.toBeCalled();
		});

		it('does not redirect to the product page when the remove button is clicked', () => {
			const {getAllByRole, getByRole} = renderCartItem();

			const mockClick = jest.fn();

			getByRole('link').addEventListener('click', mockClick);

			fireEvent.click(getAllByRole('button')[0]);

			expect(mockClick).not.toBeCalled();
		});
	});

	describe('by data flow', () => {
		describe('if the cart item has errors', () => {
			it('renders a div with icon and error text', () => {
				const ERRORS_PROPS = {
					...BASE_PROPS,
					item: {
						...BASE_PROPS.item,
						errorMessages: ['error'],
					},
				};

				const {container} = renderCartItem(ERRORS_PROPS);

				const ErrorsElement = container.querySelector(
					`${COMPONENT_SELECTOR}-errors`
				);

				expect(ErrorsElement).toBeInTheDocument();
				expect(ErrorsElement.innerHTML).toMatchSnapshot();
			});
		});
	});

	describe('by interaction', () => {
		describe('if the cart item delete button is clicked', () => {
			it('unhides the div to handle the item removal cancellation', async () => {
				const {container} = renderCartItem();

				const CartItemDeleteButton = container.querySelector(
					`${COMPONENT_SELECTOR}-actions button`
				);

				await act(async () => {
					fireEvent.click(CartItemDeleteButton);
				});

				await act(async () => {
					jest.advanceTimersByTime(100);
				});

				const CartItemElement =
					container.querySelector(COMPONENT_SELECTOR);
				const CartItemRemovalElement = CartItemElement.querySelector(
					`${COMPONENT_SELECTOR}-is-removing-wrapper.active`
				);

				expect(CartItemRemovalElement).toBeInTheDocument();
				expect(CartItemElement.innerHTML).toMatchSnapshot();
			});

			it('if no action is performed, calls the API to remove the item from the order', async () => {
				const {container} = renderCartItem();

				const CartItemElement =
					container.querySelector(COMPONENT_SELECTOR);
				const CartItemDeleteButton = container.querySelector(
					`${COMPONENT_SELECTOR}-actions button`
				);

				await act(async () => {
					fireEvent.click(CartItemDeleteButton);
				});

				await act(async () => {
					jest.advanceTimersByTime(
						REMOVAL_ANIMATION_MS +
							REMOVAL_CANCELING_TIMEOUT +
							REMOVAL_TIMEOUT
					);
				});

				await waitFor(() => {
					expect(CartResource.deleteItemById).toHaveBeenCalledWith(
						BASE_PROPS.item.id
					);
				});

				const {setIsUpdating, updateCartModel} = BASE_CONTEXT_MOCK;
				const {id: orderId} = BASE_CONTEXT_MOCK.cartState;

				expect(updateCartModel).toHaveBeenCalledWith({
					order: {id: orderId},
					refreshItems: true,
				});

				expect(setIsUpdating).toHaveBeenCalledTimes(2);
				expect(setIsUpdating.mock.calls).toEqual([[true], [false]]);
				expect(window.Liferay.fire).toHaveBeenCalledWith(
					CART_PRODUCT_QUANTITY_CHANGED,
					{
						quantity: 0,
						skuId: BASE_PROPS.item.skuId,
					}
				);

				expect(CartItemElement.classList.contains('is-removed')).toBe(
					true
				);
			});

			it('if the "undo" button is clicked, cancels the removal of the item', async () => {
				const {container, getByText} = renderCartItem();

				const CartItemDeleteButton = container.querySelector(
					`${COMPONENT_SELECTOR}-actions button`
				);

				await act(async () => {
					fireEvent.click(CartItemDeleteButton);
				});

				await act(async () => {
					jest.advanceTimersByTime(REMOVAL_ANIMATION_MS);
				});

				await act(async () => {
					fireEvent.click(getByText('undo'));
				});

				const CartItemRemovalElement = container.querySelector(
					`${COMPONENT_SELECTOR}-is-removing-wrapper`
				);

				expect(CartItemRemovalElement).toBeInTheDocument();
				expect(
					CartItemRemovalElement.classList.contains('canceled')
				).toBe(true);

				await act(async () => {
					jest.advanceTimersByTime(REMOVAL_CANCELING_TIMEOUT);
				});
			});
		});

		describe('if the cart item quantity is edited', () => {
			it('debounces a call to the API to update the item quantity, then refreshes the cart', async () => {
				const UPDATED_QUANTITY = 2;

				CartResource.updateItemById.mockImplementation(() =>
					Promise.resolve({quantity: UPDATED_QUANTITY})
				);

				const {getByRole} = renderCartItem();

				await act(async () => {
					fireEvent.change(getByRole('spinbutton'), {
						target: {value: `${UPDATED_QUANTITY}`},
					});
				});

				await act(async () => {
					jest.advanceTimersByTime(1000);
				});

				const {setIsUpdating, updateCartModel} = BASE_CONTEXT_MOCK;
				const {id: orderId} = BASE_CONTEXT_MOCK.cartState;

				await waitFor(() => {
					expect(updateCartModel).toHaveBeenCalledWith({
						order: {id: orderId},
					});
				});

				expect(CartResource.updateItemById).toHaveBeenCalledWith(
					BASE_PROPS.item.id,
					{quantity: UPDATED_QUANTITY}
				);
				expect(setIsUpdating.mock.calls).toEqual([[true], [false]]);

				const [itemUpdater] = BASE_PROPS.updateCartItem.mock.calls[0];

				expect(itemUpdater({id: BASE_PROPS.item.id})).toEqual({
					id: BASE_PROPS.item.id,
					quantity: UPDATED_QUANTITY,
				});
			});

			it('if the request fails, surfaces the error on the item and does not refresh the cart', async () => {
				const ERROR_MESSAGE = 'The quantity is not available';

				CartResource.updateItemById.mockImplementation(() =>
					Promise.reject({message: ERROR_MESSAGE})
				);

				const updateCartItem = jest.fn();

				const {getByRole} = renderCartItem({
					...BASE_PROPS,
					updateCartItem,
				});

				await act(async () => {
					fireEvent.change(getByRole('spinbutton'), {
						target: {value: '2'},
					});
				});

				await act(async () => {
					jest.advanceTimersByTime(1000);
				});

				await waitFor(() => {
					expect(updateCartItem).toHaveBeenCalled();
				});

				const [itemUpdater] = updateCartItem.mock.calls[0];

				expect(itemUpdater({})).toEqual({
					errorMessages: [ERROR_MESSAGE],
				});
				expect(
					BASE_CONTEXT_MOCK.updateCartModel
				).not.toHaveBeenCalled();
				expect(BASE_CONTEXT_MOCK.setIsUpdating.mock.calls).toEqual([
					[true],
					[false],
				]);
			});
		});
	});

	describe('with various price shapes and item interactions', () => {
		const renderWithItem = (item, contextOverrides = {}) =>
			render(
				<MiniCartContext.Provider
					value={{...BASE_CONTEXT_MOCK, ...contextOverrides}}
				>
					<CartItem {...item} updateCartItem={jest.fn()} />
				</MiniCartContext.Provider>
			);

		it('renders the item name, SKU, list price, and quantity selector', () => {
			const item = mockCartItem({
				name: 'Sample Product',
				quantity: 3,
				sku: 'SKU-VISIBLE',
			});

			const {container, getByRole, getByText} = renderWithItem(item);

			expect(getByText('Sample Product')).toBeInTheDocument();
			expect(getByText('SKU-VISIBLE')).toBeInTheDocument();

			const priceWrapper = container.querySelector(
				`${COMPONENT_SELECTOR}-price`
			);

			expect(priceWrapper).toHaveTextContent(item.price.priceFormatted);
			expect(getByRole('spinbutton').value).toBe('3');
			expect(
				container.querySelector(`${COMPONENT_SELECTOR}-actions button`)
			).toBeInTheDocument();
		});

		it('renders the tier-discounted final price when quantity reaches the tier', () => {
			const item = mockCartItemWithTierPrice();

			const {container, getByRole} = renderWithItem(item);

			const priceWrapper = container.querySelector(
				`${COMPONENT_SELECTOR}-price`
			);

			expect(priceWrapper).toBeInTheDocument();
			expect(
				priceWrapper.querySelector('.price-value-promo')
			).toHaveTextContent(item.price.promoPriceFormatted);
			expect(getByRole('spinbutton').value).toBe(String(item.quantity));
		});

		it('renders the bulk-discounted price and reflects the bulk multipleQuantity in the quantity input step', () => {
			const item = mockCartItemWithBulkPrice();

			const {container, getByRole} = renderWithItem(item);

			const priceWrapper = container.querySelector(
				`${COMPONENT_SELECTOR}-price`
			);
			const quantityInput = getByRole('spinbutton');

			expect(
				priceWrapper.querySelector('.price-value-promo')
			).toHaveTextContent(item.price.promoPriceFormatted);
			expect(quantityInput.step).toBe(
				String(item.settings.multipleQuantity)
			);
		});
	});
});
