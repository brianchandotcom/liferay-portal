/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '../../tests_utilities/polyfills';

import '@testing-library/jest-dom';
import {act, fireEvent, render, waitFor} from '@testing-library/react';
import React from 'react';

import ServiceProvider from '../../../src/main/resources/META-INF/resources/ServiceProvider';
import MiniCart from '../../../src/main/resources/META-INF/resources/components/mini_cart/MiniCart';
import MiniCartContext, {
	DEFAULT_MINI_CART_CONTEXT_VALUE,
} from '../../../src/main/resources/META-INF/resources/components/mini_cart/MiniCartContext';
import {CART} from '../../../src/main/resources/META-INF/resources/components/mini_cart/util/constants';
import {
	CURRENT_ACCOUNT_UPDATED,
	CURRENT_ORDER_UPDATED,
} from '../../../src/main/resources/META-INF/resources/utilities/eventsDefinitions';
import * as Notificationtests_utilities from '../../../src/main/resources/META-INF/resources/utilities/notifications';
import {getMockedCart} from '../../tests_utilities/fake_data/carts';

jest.mock(
	'../../../src/main/resources/META-INF/resources/ServiceProvider',
	() => {
		const cartApi = {
			getCartById: jest.fn(),
			getCartItemsByCartId: jest.fn(),
		};

		const cache = {DeliveryCartAPI: jest.fn(() => cartApi)};

		return {
			__esModule: true,
			default: new Proxy(
				{},
				{
					get: (_, prop) => {
						if (!cache[prop]) {
							cache[prop] = jest.fn(() => ({}));
						}

						return cache[prop];
					},
				}
			),
		};
	}
);

describe('MiniCart', () => {
	const BASE_PROPS = {
		cartActionURLs: {
			baseOrderDetailURL:
				'http://site-default.url/com_liferay_commerce_order_content_web_internal_portlet_CommerceOpenOrderContentPortlet',
			checkoutURL: 'http://checkout.url',
			orderDetailURL: '',
			productURLSeparator: 'p',
			siteDefaultURL: 'http://site-default.url',
		},
		channel: {id: 42},
		onAddToCart: jest.fn(),
	};

	const CART_WITH_ITEMS_MOCK = getMockedCart(true);

	const COMPONENT_SELECTOR = '.mini-cart';

	const CartResource = ServiceProvider.DeliveryCartAPI('v1');

	let onCurrentOrderUpdated = () => {};
	let onCurrentAccountUpdated = () => {};

	beforeEach(() => {
		jest.spyOn(
			Notificationtests_utilities,
			'showErrorNotification'
		).mockImplementation(() => {});

		CartResource.getCartById.mockImplementation(() =>
			Promise.resolve(CART_WITH_ITEMS_MOCK)
		);
		CartResource.getCartItemsByCartId.mockImplementation(() =>
			Promise.resolve({items: [], lastPage: 1})
		);

		window.Liferay = {
			Language: {
				get: jest.fn((text) => text),
			},
			ThemeDisplay: {
				isSignedIn: jest.fn(() => true),
			},
			detach: jest.fn(),
			fire: jest.fn(),
			on: jest.fn((eventName, callback) => {
				if (eventName === CURRENT_ACCOUNT_UPDATED) {
					onCurrentAccountUpdated = callback;
				}
				else if (eventName === CURRENT_ORDER_UPDATED) {
					onCurrentOrderUpdated = callback;
				}
			}),
		};
	});

	afterEach(() => {
		jest.resetAllMocks();
	});

	describe('by default', () => {
		it(
			'renders the MiniCart in closed state with ' +
				'the default view components and an overlay div',
			async () => {
				const {asFragment, container} = render(
					<MiniCart {...BASE_PROPS} />
				);

				await waitFor(() =>
					expect(
						container.querySelector(COMPONENT_SELECTOR)
					).toBeInTheDocument()
				);

				const MiniCartElement =
					container.querySelector(COMPONENT_SELECTOR);
				const MiniCartOverlayElement = MiniCartElement.querySelector(
					`${COMPONENT_SELECTOR}-overlay`
				);

				expect(MiniCartElement).toBeInTheDocument();
				expect(MiniCartElement.classList.contains('is-open')).toBe(
					false
				);
				expect(MiniCartOverlayElement).toBeInTheDocument();

				expect(asFragment()).toMatchSnapshot();
			}
		);

		it('wraps the MiniCart with a Context provider for all of its child components', async () => {
			let Context = {};

			const ConsumerComponent = () => {
				Context = React.useContext(MiniCartContext);

				return <></>;
			};

			const MIGRATING_PROPS_TO_CONTEXT = {
				...BASE_PROPS,
				cartViews: {
					[CART]: {component: ConsumerComponent},
				},
				displayDiscountLevels: true,
				toggleable: false,
			};

			const {container} = render(
				<MiniCart {...MIGRATING_PROPS_TO_CONTEXT} />
			);

			await waitFor(() =>
				expect(
					container.querySelector(COMPONENT_SELECTOR)
				).toBeInTheDocument()
			);

			expect(Context).not.toEqual(DEFAULT_MINI_CART_CONTEXT_VALUE);
			expect(Context.displayDiscountLevels).toBe(true);
			expect(Context.toggleable).toBe(false);
		});
	});

	describe('by interaction', () => {
		describe('if cart is toggleable', () => {
			it('click on the Opener button opens the MiniCart', async () => {
				const {container} = render(<MiniCart {...BASE_PROPS} />);

				await waitFor(() =>
					expect(
						container.querySelector(COMPONENT_SELECTOR)
					).toBeInTheDocument()
				);

				await act(async () => {
					const MiniCartOpenerButton = container.querySelector(
						`${COMPONENT_SELECTOR}-opener`
					);

					fireEvent.click(MiniCartOpenerButton);
				});

				await waitFor(() => {
					const MiniCartElement =
						container.querySelector(COMPONENT_SELECTOR);

					expect(MiniCartElement.classList.contains('is-open')).toBe(
						true
					);
				});
			});

			it('if the MiniCart is open, click on the overlay closes the MiniCart', async () => {
				const {container} = render(<MiniCart {...BASE_PROPS} />);

				await waitFor(() =>
					expect(
						container.querySelector(COMPONENT_SELECTOR)
					).toBeInTheDocument()
				);

				await act(async () => {
					const MiniCartOverlayElement = container.querySelector(
						`${COMPONENT_SELECTOR}-overlay`
					);

					fireEvent.click(MiniCartOverlayElement);
				});

				await waitFor(() => {
					const MiniCartElement =
						container.querySelector(COMPONENT_SELECTOR);

					expect(MiniCartElement.classList.contains('is-open')).toBe(
						false
					);
				});
			});
		});

		describe('if cart is toggleable', () => {
			it('the MiniCart is open from the start, and neither the Opener nor the overlay are rendered', async () => {
				const PROPS = {
					...BASE_PROPS,
					toggleable: false,
				};

				const {container} = render(<MiniCart {...PROPS} />);

				await waitFor(() =>
					expect(
						container.querySelector(COMPONENT_SELECTOR)
					).toBeInTheDocument()
				);

				const MiniCartElement =
					container.querySelector(COMPONENT_SELECTOR);
				const MiniCartOverlayElement = MiniCartElement.querySelector(
					`${COMPONENT_SELECTOR}-overlay`
				);
				const MiniCartOpenerButton = MiniCartElement.querySelector(
					`${COMPONENT_SELECTOR}-opener`
				);

				expect(MiniCartElement.classList.contains('is-open')).toBe(
					true
				);
				expect(MiniCartOverlayElement).not.toBeInTheDocument();
				expect(MiniCartOpenerButton).not.toBeInTheDocument();
			});
		});
	});

	describe('by data flow', () => {
		describe('if the order ID is defined and > 0', () => {
			it('calls the API to fetch the cart by orderId', async () => {
				const PROPS = {
					...BASE_PROPS,
					orderId: 123,
					toggleable: false,
				};

				const {container} = render(<MiniCart {...PROPS} />);

				await waitFor(() =>
					expect(
						container.querySelector(COMPONENT_SELECTOR)
					).toBeInTheDocument()
				);

				expect(CartResource.getCartById).toHaveBeenCalledWith(
					PROPS.orderId
				);
			});

			it('if the request fails, displays an error via Liferay Notification', async () => {
				const ERROR = 'error';

				CartResource.getCartById.mockImplementation(() =>
					Promise.reject(ERROR)
				);

				const PROPS = {
					...BASE_PROPS,
					orderId: 123,
					toggleable: false,
				};

				const {container} = render(<MiniCart {...PROPS} />);

				await waitFor(() =>
					expect(
						container.querySelector(COMPONENT_SELECTOR)
					).toBeInTheDocument()
				);

				await waitFor(() => {
					expect(CartResource.getCartById).toHaveBeenCalledWith(
						PROPS.orderId
					);

					expect(
						Notificationtests_utilities.showErrorNotification
					).toHaveBeenCalledWith(ERROR);
				});
			});

			describe('if the request succeeds', () => {
				it('updates the cart action URLs', async () => {
					let Context = {};

					const ConsumerComponent = () => {
						Context = React.useContext(MiniCartContext);

						return <></>;
					};

					const PROPS = {
						...BASE_PROPS,
						cartViews: {
							[CART]: {component: ConsumerComponent},
						},
						orderId: 123,
					};

					const {container} = render(<MiniCart {...PROPS} />);

					await waitFor(() =>
						expect(
							container.querySelector(COMPONENT_SELECTOR)
						).toBeInTheDocument()
					);

					expect(CartResource.getCartById).toHaveBeenCalledWith(
						PROPS.orderId
					);

					expect(
						Context.actionURLs.orderDetailURL.includes(
							CART_WITH_ITEMS_MOCK.orderUUID
						)
					).toBe(true);
				});

				it('calls the "onAddToCart" custom function', async () => {
					let Context = {};

					const ConsumerComponent = () => {
						Context = React.useContext(MiniCartContext);

						return <></>;
					};

					const PROPS = {
						...BASE_PROPS,
						cartViews: {
							[CART]: {component: ConsumerComponent},
						},
						orderId: 123,
					};

					const {container} = render(<MiniCart {...PROPS} />);

					await waitFor(() =>
						expect(
							container.querySelector(COMPONENT_SELECTOR)
						).toBeInTheDocument()
					);

					expect(CartResource.getCartById).toHaveBeenCalledWith(
						PROPS.orderId
					);

					expect(PROPS.onAddToCart).toHaveBeenCalledWith(
						Context.actionURLs,
						Context.cartState
					);
				});

				it('renders the MiniCart displaying the cart content correctly', async () => {
					const PROPS = {
						...BASE_PROPS,
						orderId: 123,
					};

					const {asFragment, container} = render(
						<MiniCart {...PROPS} />
					);

					await waitFor(() =>
						expect(
							container.querySelector(COMPONENT_SELECTOR)
						).toBeInTheDocument()
					);

					expect(asFragment()).toMatchSnapshot();
				});
			});
		});
	});

	describe('by event', () => {
		it(`on "${CURRENT_ORDER_UPDATED}" event, calls the API with the ID of the order and updates the MiniCart`, async () => {
			const INCOMING_ORDER_UPDATED_PAYLOAD = {order: {id: 999}};

			const {container} = render(<MiniCart {...BASE_PROPS} />);

			await waitFor(() =>
				expect(
					container.querySelector(COMPONENT_SELECTOR)
				).toBeInTheDocument()
			);

			await act(async () => {
				onCurrentOrderUpdated(INCOMING_ORDER_UPDATED_PAYLOAD);
			});

			await waitFor(() => {
				expect(CartResource.getCartById).toHaveBeenCalledWith(
					INCOMING_ORDER_UPDATED_PAYLOAD.order.id
				);
			});
		});

		it(`on "${CURRENT_ACCOUNT_UPDATED}" event, resets the cart state`, async () => {
			let Context = {};

			const ConsumerComponent = () => {
				Context = React.useContext(MiniCartContext);

				return <></>;
			};

			const MIGRATING_PROPS_TO_CONTEXT = {
				...BASE_PROPS,
				cartViews: {
					[CART]: {component: ConsumerComponent},
				},
				orderId: 43620,
			};

			const {container} = render(
				<MiniCart {...MIGRATING_PROPS_TO_CONTEXT} />
			);

			await waitFor(() =>
				expect(
					container.querySelector(COMPONENT_SELECTOR)
				).toBeInTheDocument()
			);

			await waitFor(() => {
				expect(Context.cartState).toEqual({
					...CART_WITH_ITEMS_MOCK,
					channel: {channel: BASE_PROPS.channel},
				});
			});

			await act(async () => {
				onCurrentAccountUpdated({});
			});

			await waitFor(() => {
				expect(Context.cartState).toEqual({
					accountId: 0,
					channel: {channel: BASE_PROPS.channel},
					id: 0,
					summary: {
						itemsQuantity: 0,
					},
				});
			});
		});
	});
});
