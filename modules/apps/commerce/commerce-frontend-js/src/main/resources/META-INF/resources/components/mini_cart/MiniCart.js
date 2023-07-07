/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classnames from 'classnames';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useState} from 'react';

import ServiceProvider from '../../ServiceProvider/index';
import {
	CURRENT_ACCOUNT_UPDATED,
	CURRENT_ORDER_UPDATED,
} from '../../utilities/eventsDefinitions';
import {showErrorNotification} from '../../utilities/notifications';
import MiniCartContext from './MiniCartContext';
import {
	ADD_PRODUCT,
	CART,
	HEADER,
	ITEM,
	ITEMS_LIST,
	ITEMS_LIST_ACTIONS,
	OPENER,
	ORDER_BUTTON,
	ORDER_IS_EMPTY,
	REMOVE_ALL_ITEMS,
	REVIEW_ORDER,
	SUBMIT_ORDER,
	SUMMARY,
	VIEW_DETAILS,
	YOUR_ORDER,
} from './util/constants';
import {regenerateOrderDetailURL, summaryDataMapper} from './util/index';
import {DEFAULT_LABELS} from './util/labels';
import {resolveCartViews} from './util/views';

const CartResource = ServiceProvider.DeliveryCartAPI('v1');

function MiniCart({
	accountId,
	cartActionURLs,
	cartViews,
	channel,
	displayDiscountLevels,
	displayTotalItemsQuantity,
	itemsQuantity,
	labels,
	onAddToCart,
	orderId,
	productURLSeparator,
	requestQuoteEnabled,
	summaryDataMapper,
	toggleable,
}) {
	const [isOpen, setIsOpen] = useState(!toggleable);
	const [isUpdating, setIsUpdating] = useState(false);
	const [actionURLs, setActionURLs] = useState(cartActionURLs);
	const [CartViews, setCartViews] = useState({});
	const [cartState, setCartState] = useState({
		accountId,
		channel: {channel},
		id: orderId,
		summary: {itemsQuantity},
	});

	const closeCart = () => setIsOpen(false);
	const openCart = () => setIsOpen(true);

	const [replacementSKUList, setReplacementSKUList] = useState([]);

	const resetCartState = useCallback(
		({accountId = 0}) =>
			setCartState({
				accountId,
				id: 0,
				summary: {itemsQuantity: 0},
			}),
		[setCartState]
	);

	const updateCartModel = useCallback(
		async ({order}) => {
			try {
				const updatedCart = order.orderUUID
					? order
					: await CartResource.getCartByIdWithItems(order.id);

				let latestActionURLs;
				let latestCartState;

				setActionURLs((currentURLs) => {
					const orderDetailURL = currentURLs.orderDetailURL;

					latestActionURLs = {
						...currentURLs,
						orderDetailURL: !orderDetailURL
							? regenerateOrderDetailURL(
									updatedCart.orderUUID,
									currentURLs.siteDefaultURL
							  )
							: new URL(orderDetailURL),
					};

					return latestActionURLs;
				});

				setCartState((currentState) => {
					latestCartState = {...currentState, ...updatedCart};

					return latestCartState;
				});

				onAddToCart(latestActionURLs, latestCartState);
			}
			catch (error) {
				showErrorNotification(error);
			}
		},
		[onAddToCart]
	);

	const updateReplacedSKUList = useCallback(
		() =>
			cartState.cartItems
				? setReplacementSKUList(
						cartState.cartItems.filter(
							({replacedSku: replacedSKU}) => Boolean(replacedSKU)
						)
				  )
				: null,
		[cartState.cartItems]
	);

	useEffect(() => {
		updateReplacedSKUList();
	}, [updateReplacedSKUList]);

	useEffect(() => {
		resolveCartViews(cartViews).then((views) => setCartViews(views));
	}, [cartViews]);

	useEffect(() => {
		Liferay.on(CURRENT_ORDER_UPDATED, updateCartModel);

		return () => {
			Liferay.detach(CURRENT_ORDER_UPDATED, updateCartModel);
		};
	}, [updateCartModel]);

	useEffect(() => {
		if (orderId) {
			updateCartModel({order: {id: orderId}});
		}
	}, [orderId, updateCartModel]);

	useEffect(() => {
		Liferay.on(CURRENT_ACCOUNT_UPDATED, resetCartState);

		return () => {
			Liferay.detach(CURRENT_ACCOUNT_UPDATED, resetCartState);
		};
	}, [resetCartState]);

	return (
		<MiniCartContext.Provider
			value={{
				CartViews,
				actionURLs,
				cartState,
				closeCart,
				displayDiscountLevels,
				displayTotalItemsQuantity,
				isOpen,
				isUpdating,
				labels: {...DEFAULT_LABELS, ...labels},
				openCart,
				productURLSeparator,
				replacementSKUList,
				requestQuoteEnabled,
				setCartState,
				setIsUpdating,
				setReplacementSKUList,
				summaryDataMapper,
				toggleable,
				updateCartModel,
			}}
		>
			{!!CartViews[CART] && (
				<div
					className={classnames({
						'is-open': isOpen || !toggleable,
						'mini-cart': true,
					})}
				>
					{toggleable && (
						<>
							<div
								className="mini-cart-overlay"
								onClick={() => setIsOpen(false)}
							/>

							<CartViews.Opener />
						</>
					)}

					<CartViews.Cart />
				</div>
			)}
		</MiniCartContext.Provider>
	);
}

MiniCart.defaultProps = {
	cartViews: {},
	displayDiscountLevels: false,
	displayTotalItemsQuantity: false,
	itemsQuantity: 0,
	labels: DEFAULT_LABELS,
	onAddToCart: () => {},
	orderId: 0,
	requestQuoteEnabled: false,
	summaryDataMapper,
	toggleable: true,
};

MiniCart.propTypes = {
	cartActionURLs: PropTypes.shape({
		checkoutURL: PropTypes.string,
		orderDetailURL: PropTypes.string,
		productURLSeparator: PropTypes.string,
		siteDefaultURL: PropTypes.string,
	}).isRequired,
	cartViews: PropTypes.shape({
		[CART]: PropTypes.oneOfType([
			PropTypes.shape({
				component: PropTypes.func,
			}),
			PropTypes.shape({
				contentRendererModuleUrl: PropTypes.string,
			}),
		]),
		[HEADER]: PropTypes.oneOfType([
			PropTypes.shape({
				component: PropTypes.func,
			}),
			PropTypes.shape({
				contentRendererModuleUrl: PropTypes.string,
			}),
		]),
		[ITEM]: PropTypes.oneOfType([
			PropTypes.shape({
				component: PropTypes.func,
			}),
			PropTypes.shape({
				contentRendererModuleUrl: PropTypes.string,
			}),
		]),
		[ITEMS_LIST]: PropTypes.oneOfType([
			PropTypes.shape({
				component: PropTypes.func,
			}),
			PropTypes.shape({
				contentRendererModuleUrl: PropTypes.string,
			}),
		]),
		[ITEMS_LIST_ACTIONS]: PropTypes.oneOfType([
			PropTypes.shape({
				component: PropTypes.func,
			}),
			PropTypes.shape({
				contentRendererModuleUrl: PropTypes.string,
			}),
		]),
		[OPENER]: PropTypes.oneOfType([
			PropTypes.shape({
				component: PropTypes.func,
			}),
			PropTypes.shape({
				contentRendererModuleUrl: PropTypes.string,
			}),
		]),
		[ORDER_BUTTON]: PropTypes.oneOfType([
			PropTypes.shape({
				component: PropTypes.func,
			}),
			PropTypes.shape({
				contentRendererModuleUrl: PropTypes.string,
			}),
		]),
		[SUMMARY]: PropTypes.oneOfType([
			PropTypes.shape({
				component: PropTypes.func,
			}),
			PropTypes.shape({
				contentRendererModuleUrl: PropTypes.string,
			}),
		]),
	}),
	displayDiscountLevels: PropTypes.bool,
	displayTotalItemsQuantity: PropTypes.bool,
	itemsQuantity: PropTypes.number,
	labels: PropTypes.shape({
		[ADD_PRODUCT]: PropTypes.string,
		[ORDER_IS_EMPTY]: PropTypes.string,
		[REMOVE_ALL_ITEMS]: PropTypes.string,
		[REVIEW_ORDER]: PropTypes.string,
		[SUBMIT_ORDER]: PropTypes.string,
		[VIEW_DETAILS]: PropTypes.string,
		[YOUR_ORDER]: PropTypes.string,
	}),
	onAddToCart: PropTypes.func,
	orderId: PropTypes.number,
	requestQuoteEnabled: PropTypes.bool,
	summaryDataMapper: PropTypes.func,
	toggleable: PropTypes.bool,
};

export default MiniCart;
