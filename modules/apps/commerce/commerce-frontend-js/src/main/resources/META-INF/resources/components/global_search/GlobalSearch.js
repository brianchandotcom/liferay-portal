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

import ClayDropDown from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import {ClayIconSpriteContext} from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClaySticker from '@clayui/sticker';
import {useIsMounted} from 'frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import ServiceProvider from '../../ServiceProvider/index';
import {debouncePromise} from '../../utilities/debounce';
import {formatActionUrl, getRandomId} from '../../utilities/index';
import {showErrorNotification} from '../../utilities/notifications';

const OrderResource = ServiceProvider.AdminOrderAPI('v1');
const AccountsResource = ServiceProvider.AdminAccountAPI('v1');
const CatalogResource = ServiceProvider.DeliveryCatalogAPI('v1');

function SearchItem({className, href, label, stickerShape, thumbnailUrl}) {
	return (
		<ClayDropDown.Item className={className} href={href}>
			<ClaySticker className="mr-2" shape={stickerShape}>
				<ClaySticker.Image alt={label} src={thumbnailUrl} />
			</ClaySticker>
			{label}
		</ClayDropDown.Item>
	);
}

const composeDataUpdate = (
	promise,
	isMounted,
	updateData,
	updateCounter,
	updateLoader
) => {
	return promise
		.then((res) => {
			if (!isMounted()) {
				return;
			}
			updateData(res.items);
			updateCounter(res.totalCount);
		})
		.catch((e) => {
			showErrorNotification(e.message);
			updateData(null);
			updateCounter(null);
		})
		.finally(() => {
			if (!isMounted()) {
				return;
			}
			updateLoader(false);
		});
};

function GlobalSearch(props) {
	const [query, updateQuery] = useState('');
	const isMounted = useIsMounted();
	const inputRef = useRef(null);
	const dropdownRef = useRef(null);
	const [accountsLoading, updateAccountsLoading] = useState(false);
	const [accounts, updateAccounts] = useState(null);
	const [accountsCount, updateAccountsCount] = useState(null);
	const [debouncedGetAccounts, updateDebouncedGetAccounts] = useState(null);
	const [ordersLoading, updateOrdersLoading] = useState(false);
	const [orders, updateOrders] = useState(null);
	const [ordersCount, updateOrdersCount] = useState(null);
	const [debouncedGetOrders, updateDebouncedGetOrders] = useState(null);
	const [productsLoading, updateProductsLoading] = useState(false);
	const [products, updateProducts] = useState(null);
	const [productsCount, updateProductsCount] = useState(null);
	const [debouncedGetProducts, updateDebouncedGetProducts] = useState(null);
	const [active, setActive] = useState(false);
	const [ids] = useState({
		input: 'global-search-input' + getRandomId(),
		menu: 'global-search-menu' + getRandomId(),
	});

	useEffect(() => {
		updateDebouncedGetAccounts(() =>
			debouncePromise(
				AccountsResource.getAccounts,
				props.fetchDataDebounce
			)
		);
		updateDebouncedGetOrders(() =>
			debouncePromise(OrderResource.getOrders, props.fetchDataDebounce)
		);
		updateDebouncedGetProducts(() =>
			debouncePromise(
				CatalogResource.getProductsByChannelId,
				props.fetchDataDebounce
			)
		);
	}, [props.fetchDataDebounce]);

	const getProducts = useCallback(() => {
		composeDataUpdate(
			debouncedGetProducts(props.channelId, null, {
				pageSize: props.resultsPageSize,
				search: query,
			}),
			isMounted,
			updateProducts,
			updateProductsCount,
			updateProductsLoading
		);
	}, [
		debouncedGetProducts,
		props.channelId,
		props.resultsPageSize,
		query,
		isMounted,
	]);

	const getAccounts = useCallback(() => {
		composeDataUpdate(
			debouncedGetAccounts(null, {
				pageSize: props.resultsPageSize,
				search: query,
			}),
			isMounted,
			updateAccounts,
			updateAccountsCount,
			updateAccountsLoading
		);
	}, [debouncedGetAccounts, props.resultsPageSize, query, isMounted]);

	const getOrders = useCallback(() => {
		composeDataUpdate(
			debouncedGetOrders(null, {
				pageSize: props.resultsPageSize,
				search: query,
			}),
			isMounted,
			updateOrders,
			updateOrdersCount,
			updateOrdersLoading
		);
	}, [debouncedGetOrders, props.resultsPageSize, query, isMounted]);

	function resetContent() {
		updateAccounts(null);
		updateOrders(null);
		updateProducts(null);
		updateAccountsCount(null);
		updateOrdersCount(null);
		updateProductsCount(null);
	}

	useEffect(() => {
		setActive(Boolean(query));

		if (!query) {
			resetContent();

			return;
		}

		updateProductsLoading(true);
		updateAccountsLoading(true);
		updateOrdersLoading(true);

		getProducts();
		getAccounts();
		getOrders();
	}, [query, getProducts, getAccounts, getOrders]);

	useEffect(() => {
		function handleClick(e) {
			if (
				!(e.target.closest(`#${ids.menu}`) || e.target.id === ids.input)
			) {
				setActive(false);
			}
		}

		if (active) {
			document.addEventListener('click', handleClick);
		}
		else {
			document.removeEventListener('click', handleClick);
		}

		return () => {
			document.removeEventListener('click', handleClick);
		};
	}, [active, ids]);

	return (
		<ClayIconSpriteContext.Provider value={props.spritemap}>
			<ClayInput
				id={ids.input}
				onChange={(e) => updateQuery(e.target.value)}
				onClick={() => {
					if (query) {
						setActive(true);
					}
				}}
				placeholder={Liferay.Language.get('search')}
				ref={inputRef}
				value={query}
			/>
			<ClayDropDown.Menu
				active={active}
				alignElementRef={inputRef}
				className="dropdown-wide"
				id={ids.menu}
				onSetActive={() => setActive(false)}
				ref={dropdownRef}
			>
				<ClayDropDown.ItemList>
					<ClayDropDown.Group
						header={Liferay.Language.get('catalog')}
					>
						{!productsLoading ? (
							products?.length ? (
								<>
									{products.map((product) => (
										<SearchItem
											className="product-item"
											href={formatActionUrl(
												props.productURLTemplate,
												product
											)}
											key={product.id}
											label={product.name}
											thumbnailUrl={product.urlImage}
										/>
									))}
									{productsCount > products.length && (
										<ClayDropDown.Item
											href={formatActionUrl(
												props.productsSearchURLTemplate,
												{
													query,
												}
											)}
										>
											{Liferay.Util.sub(
												Liferay.Language.get(
													'search-x-in-catalog'
												),
												query
											)}
										</ClayDropDown.Item>
									)}
								</>
							) : (
								<ClayDropDown.Item>
									{Liferay.Language.get(
										'no-products-were-found'
									)}
								</ClayDropDown.Item>
							)
						) : (
							<ClayDropDown.Item>
								<ClayLoadingIndicator small />
							</ClayDropDown.Item>
						)}
					</ClayDropDown.Group>
					<ClayDropDown.Group header={Liferay.Language.get('orders')}>
						{!ordersLoading ? (
							orders?.length ? (
								<>
									{orders.map((order) => (
										<ClayDropDown.Item
											className="order-item"
											href={formatActionUrl(
												props.orderURLTemplate,
												order
											)}
											key={order.id}
										>
											{order.id}
										</ClayDropDown.Item>
									))}
									{ordersCount > orders.length && (
										<ClayDropDown.Item
											href={formatActionUrl(
												props.ordersSearchURLTemplate,
												{
													query,
												}
											)}
										>
											{Liferay.Util.sub(
												Liferay.Language.get(
													'search-x-in-orders'
												),
												query
											)}
										</ClayDropDown.Item>
									)}
								</>
							) : (
								<ClayDropDown.Item>
									{Liferay.Language.get(
										'no-orders-were-found'
									)}
								</ClayDropDown.Item>
							)
						) : (
							<ClayDropDown.Item>
								<ClayLoadingIndicator small />
							</ClayDropDown.Item>
						)}
					</ClayDropDown.Group>
					<ClayDropDown.Group
						header={Liferay.Language.get('accounts')}
					>
						{!accountsLoading ? (
							accounts?.length ? (
								<>
									{accounts.map((account) => (
										<SearchItem
											className="account-item"
											href={formatActionUrl(
												props.accountURLTemplate,
												account
											)}
											key={account.id}
											label={account.name}
											stickerShape={'circle'}
											thumbnailUrl={account.logoURL}
										/>
									))}
									{accountsCount > accounts.length && (
										<ClayDropDown.Item
											href={formatActionUrl(
												props.accountsSearchURLTemplate,
												{
													query,
												}
											)}
										>
											{Liferay.Util.sub(
												Liferay.Language.get(
													'search-x-in-accounts'
												),
												query
											)}
										</ClayDropDown.Item>
									)}
								</>
							) : (
								<ClayDropDown.Item>
									{Liferay.Language.get(
										'no-accounts-were-found'
									)}
								</ClayDropDown.Item>
							)
						) : (
							<ClayDropDown.Item>
								<ClayLoadingIndicator small />
							</ClayDropDown.Item>
						)}
					</ClayDropDown.Group>
					<ClayDropDown.Divider />
					<ClayDropDown.Item
						href={formatActionUrl(props.globalSearchURLTemplate, {
							query,
						})}
					>
						{Liferay.Language.get('more-global-results')}
					</ClayDropDown.Item>
				</ClayDropDown.ItemList>
			</ClayDropDown.Menu>
		</ClayIconSpriteContext.Provider>
	);
}

GlobalSearch.propTypes = {
	accountURLTemplate: PropTypes.string.isRequired,
	accountsSearchURLTemplate: PropTypes.string.isRequired,
	channelId: PropTypes.number.isRequired,
	fetchDataDebounce: PropTypes.number.isRequired,
	globalSearchURLTemplate: PropTypes.string.isRequired,
	orderURLTemplate: PropTypes.string.isRequired,
	ordersSearchURLTemplate: PropTypes.string.isRequired,
	productURLTemplate: PropTypes.string.isRequired,
	productsSearchURLTemplate: PropTypes.string.isRequired,
	resultsPageSize: PropTypes.number.isRequired,
	spritemap: PropTypes.string.isRequired,
};

GlobalSearch.defaultProps = {
	fetchDataDebounce: 300,
	resultsPageSize: 4,
};

export default GlobalSearch;
