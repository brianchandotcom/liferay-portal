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

import {ALL} from '../../../src/main/resources/META-INF/resources/components/add_to_cart/constants';
import CartItemsListActions from '../../../src/main/resources/META-INF/resources/components/mini_cart/CartItemsListActions';
import MiniCartContext from '../../../src/main/resources/META-INF/resources/components/mini_cart/MiniCartContext';
import {
	REMOVE_ALL_ITEMS,
	VIEW_DETAILS,
} from '../../../src/main/resources/META-INF/resources/components/mini_cart/util/constants';
import {DEFAULT_LABELS} from '../../../src/main/resources/META-INF/resources/components/mini_cart/util/labels';
import * as BaseUtils from '../../../src/main/resources/META-INF/resources/utilities';
import {PRODUCT_REMOVED_FROM_CART} from '../../../src/main/resources/META-INF/resources/utilities/eventsDefinitions';

describe('MiniCart Items List Actions', () => {
	const BASE_CONTEXT_MOCK = {
		CartResource: {
			updateCartById: jest
				.fn()
				.mockReturnValue(Promise.resolve({id: 101})),
		},
		actionURLs: {
			orderDetailURL: 'http://order-detail.url',
		},
		cartState: {
			id: 101,
		},
		labels: DEFAULT_LABELS,
		setIsUpdating: jest.fn(),
		updateCartModel: jest.fn().mockReturnValue(Promise.resolve()),
	};

	const COMPONENT_SELECTOR = '.mini-cart-header';

	beforeEach(() => {
		BASE_CONTEXT_MOCK.CartResource.updateCartById = jest
			.fn()
			.mockReturnValue(Promise.resolve({id: 101}));
		BASE_CONTEXT_MOCK.setIsUpdating = jest.fn();
		BASE_CONTEXT_MOCK.updateCartModel = jest
			.fn()
			.mockReturnValue(Promise.resolve());

		jest.spyOn(BaseUtils, 'liferayNavigate');

		window.Liferay = {
			Language: {
				get: jest.fn((text) => text),
			},

			fire: jest.fn(),
		};
	});

	afterEach(() => {
		jest.resetAllMocks();

		cleanup();
	});

	describe('by default', () => {
		it(
			'renders the MiniCart items list action buttons ' +
				`${VIEW_DETAILS} and ${REMOVE_ALL_ITEMS} as disabled, ` +
				'plus the action confirmation prompt as hidden',
			() => {
				const {container} = render(
					<MiniCartContext.Provider value={BASE_CONTEXT_MOCK}>
						<CartItemsListActions />
					</MiniCartContext.Provider>
				);

				const ActionsWrapperElement = container.querySelector(
					COMPONENT_SELECTOR
				);
				const ActionsElement = ActionsWrapperElement.querySelector(
					`${COMPONENT_SELECTOR}-actions`
				);
				const ActionButtonsElements = ActionsElement.querySelectorAll(
					'.action'
				);

				expect(ActionsWrapperElement).toBeInTheDocument();
				expect(ActionsElement).toBeInTheDocument();
				expect(ActionButtonsElements.length).toEqual(2);

				ActionButtonsElements.forEach((element) => {
					expect(element).toBeInTheDocument();
					expect(element).toBeDisabled();
					expect(element.innerHTML).toMatchSnapshot();
				});

				const ConfirmationPromptElement = container.querySelector(
					'.confirmation-prompt'
				);

				expect(ConfirmationPromptElement).toBeInTheDocument();
				expect(
					ConfirmationPromptElement.classList.contains('hide')
				).toBe(true);
			}
		);
	});

	describe('by data flow', () => {
		const WITH_ITEMS_CONTEXT_MOCK = {
			...BASE_CONTEXT_MOCK,
			cartState: {
				...BASE_CONTEXT_MOCK.cartState,
				cartItems: [{id: 1}],
			},
		};

		describe('if there are cart items', () => {
			it('the MiniCart items list action buttons are clickable', () => {
				const {container} = render(
					<MiniCartContext.Provider value={WITH_ITEMS_CONTEXT_MOCK}>
						<CartItemsListActions />
					</MiniCartContext.Provider>
				);

				const ActionsWrapperElement = container.querySelector(
					COMPONENT_SELECTOR
				);
				const ActionsElement = ActionsWrapperElement.querySelector(
					`${COMPONENT_SELECTOR}-actions`
				);
				const ActionButtonsElements = ActionsElement.querySelectorAll(
					'.action'
				);

				expect(ActionsElement).toBeInTheDocument();
				expect(ActionButtonsElements.length).toEqual(2);

				ActionButtonsElements.forEach((element) => {
					expect(element).toBeInTheDocument();
					expect(element).not.toBeDisabled();
					expect(element.innerHTML).toMatchSnapshot();
				});
			});

			it('renders the items count in singular noun form', () => {
				const {container} = render(
					<MiniCartContext.Provider value={WITH_ITEMS_CONTEXT_MOCK}>
						<CartItemsListActions />
					</MiniCartContext.Provider>
				);

				const ActionsWrapperElement = container.querySelector(
					COMPONENT_SELECTOR
				);
				const ItemsCountTextElement = ActionsWrapperElement.querySelector(
					`${COMPONENT_SELECTOR}-resume`
				);

				expect(ItemsCountTextElement).toBeInTheDocument();

				expect(ItemsCountTextElement.innerHTML).toMatchSnapshot();
				expect(window.Liferay.Language.get).toHaveBeenCalledWith(
					'product'
				);
			});

			it('renders the items count in plural noun form', () => {
				const {container} = render(
					<MiniCartContext.Provider
						value={{
							...WITH_ITEMS_CONTEXT_MOCK,
							cartState: {
								...WITH_ITEMS_CONTEXT_MOCK.cartState,
								cartItems: [{id: 1}, {id: 2}],
							},
						}}
					>
						<CartItemsListActions />
					</MiniCartContext.Provider>
				);

				const ActionsWrapperElement = container.querySelector(
					COMPONENT_SELECTOR
				);
				const ItemsCountTextElement = ActionsWrapperElement.querySelector(
					`${COMPONENT_SELECTOR}-resume`
				);

				expect(ItemsCountTextElement).toBeInTheDocument();

				expect(ItemsCountTextElement.innerHTML).toMatchSnapshot();
				expect(window.Liferay.Language.get).toHaveBeenCalledWith(
					'products'
				);
			});

			describe('by interaction', () => {
				it(`if the "${VIEW_DETAILS}" button is clicked, navigates to the order detail page URL`, async () => {
					const {container} = render(
						<MiniCartContext.Provider
							value={WITH_ITEMS_CONTEXT_MOCK}
						>
							<CartItemsListActions />
						</MiniCartContext.Provider>
					);

					const ActionsWrapperElement = container.querySelector(
						COMPONENT_SELECTOR
					);
					const ActionsElement = ActionsWrapperElement.querySelector(
						`${COMPONENT_SELECTOR}-actions`
					);
					const [viewDetailsButton] = ActionsElement.querySelectorAll(
						'.action'
					);

					await act(async () => {
						fireEvent.click(viewDetailsButton);
					});

					await wait(() => {
						expect(BaseUtils.liferayNavigate).toHaveBeenCalledWith(
							WITH_ITEMS_CONTEXT_MOCK.actionURLs.orderDetailURL
						);
					});
				});

				it(
					`if the "${REMOVE_ALL_ITEMS}" button is clicked, ` +
						'unhides the confirmation prompt with yes/no buttons',
					async () => {
						const {container} = render(
							<MiniCartContext.Provider
								value={WITH_ITEMS_CONTEXT_MOCK}
							>
								<CartItemsListActions />
							</MiniCartContext.Provider>
						);

						const ActionsWrapperElement = container.querySelector(
							COMPONENT_SELECTOR
						);
						const ActionsElement = ActionsWrapperElement.querySelector(
							`${COMPONENT_SELECTOR}-actions`
						);
						const [
							,
							removeAllItemsButton,
						] = ActionsElement.querySelectorAll('.action');

						await act(async () => {
							fireEvent.click(removeAllItemsButton);
						});

						await wait(() => {
							const ConfirmationPromptElement = container.querySelector(
								'.confirmation-prompt'
							);

							expect(
								ConfirmationPromptElement
							).toBeInTheDocument();
							expect(
								ConfirmationPromptElement.classList.contains(
									'hide'
								)
							).toBe(false);
							expect(ConfirmationPromptElement).toMatchSnapshot();
						});
					}
				);

				it(
					`if the "${REMOVE_ALL_ITEMS}" button is clicked, ` +
						'and then the "No" button is clicked, ' +
						'hides the confirmation prompt with yes/no buttons',
					async () => {
						const {container, getByText} = render(
							<MiniCartContext.Provider
								value={WITH_ITEMS_CONTEXT_MOCK}
							>
								<CartItemsListActions />
							</MiniCartContext.Provider>
						);

						const ActionsWrapperElement = container.querySelector(
							COMPONENT_SELECTOR
						);
						const ActionsElement = ActionsWrapperElement.querySelector(
							`${COMPONENT_SELECTOR}-actions`
						);
						const [
							,
							removeAllItemsButton,
						] = ActionsElement.querySelectorAll('.action');

						await act(async () => {
							fireEvent.click(removeAllItemsButton);
						});

						await act(async () => {
							fireEvent.click(getByText('no'));
						});

						await wait(() => {
							const ConfirmationPromptElement = container.querySelector(
								'.confirmation-prompt'
							);

							expect(
								ConfirmationPromptElement.classList.contains(
									'hide'
								)
							).toBe(true);
						});
					}
				);

				it(
					`if the "${REMOVE_ALL_ITEMS}" button is clicked, ` +
						'and then the "Yes" button is clicked, ' +
						'calls the API to empty the cart and returns to the initial state on success',
					async () => {
						const {container, getByText} = render(
							<MiniCartContext.Provider
								value={WITH_ITEMS_CONTEXT_MOCK}
							>
								<CartItemsListActions />
							</MiniCartContext.Provider>
						);

						const ActionsWrapperElement = container.querySelector(
							COMPONENT_SELECTOR
						);
						const ActionsElement = ActionsWrapperElement.querySelector(
							`${COMPONENT_SELECTOR}-actions`
						);
						const [
							,
							removeAllItemsButton,
						] = ActionsElement.querySelectorAll('.action');

						await act(async () => {
							fireEvent.click(removeAllItemsButton);
						});

						await act(async () => {
							fireEvent.click(getByText('yes'));
						});

						await wait(() => {
							const {
								CartResource,
								setIsUpdating,
								updateCartModel,
							} = WITH_ITEMS_CONTEXT_MOCK;
							const {
								id: orderId,
							} = WITH_ITEMS_CONTEXT_MOCK.cartState;

							expect(
								CartResource.updateCartById
							).toHaveBeenCalledWith(orderId, {cartItems: []});
							expect(updateCartModel).toHaveBeenCalledWith({
								id: orderId,
							});
							expect(setIsUpdating).toHaveBeenCalledTimes(2);
							expect(setIsUpdating.mock.calls).toEqual([
								[true],
								[false],
							]);
							expect(window.Liferay.fire).toHaveBeenCalledWith(
								PRODUCT_REMOVED_FROM_CART,
								{
									skuId: ALL,
								}
							);
						});
					}
				);
			});
		});
	});
});
