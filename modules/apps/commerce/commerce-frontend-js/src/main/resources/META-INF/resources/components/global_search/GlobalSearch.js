/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClaySticker from '@clayui/sticker';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import ServiceProvider from '../../ServiceProvider/index';
import {debouncePromise} from '../../utilities/debounce';
import {formatActionUrl, getRandomId} from '../../utilities/index';
import {showErrorNotification} from '../../utilities/notifications';

const CartResource = ServiceProvider.DeliveryCartAPI('v1');
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
	fetchData,
	isMounted,
	updateData,
	updateCounter,
	updateLoader
) => {
	return fetchData
		.then((response) => {
			if (isMounted()) {
				updateData(response.items);
				updateCounter(response.totalCount);
				updateLoader(false);
			}
		})
		.catch((error) => {
			if (isMounted()) {
				showErrorNotification(error.message);
				updateData(null);
				updateCounter(null);
				updateLoader(false);
			}
		});
};

function GlobalSearch({
	accountId,
	accountURLTemplate,
	accountsSearchURLTemplate,
	cartURLTemplate,
	cartsSearchURLTemplate,
	channelId,
	fetchDataDebounce,
	globalSearchURLTemplate,
	productURLTemplate,
	productsSearchURLTemplate,
	resultsPageSize,
}) {
	const isMounted = useIsMounted();
	const inputRef = useRef(null);
	const dropdownRef = useRef(null);
	const [accountsLoading, setAccountsLoading] = useState(false);
	const [accounts, setAccounts] = useState(null);
	const [accountsCount, setAccountsCount] = useState(null);
	const [active, setActive] = useState(false);
	const [carts, setCarts] = useState(null);
	const [cartsCount, setCartsCount] = useState(null);
	const [cartsLoading, setCartsLoading] = useState(false);
	const [debouncedGetAccounts, setDebouncedGetAccounts] = useState(null);
	const [debouncedGetCarts, setDebouncedGetCarts] = useState(null);
	const [debouncedGetProducts, setDebouncedGetProducts] = useState(null);
	const [ids] = useState({
		input: 'global-search-input' + getRandomId(),
		menu: 'global-search-menu' + getRandomId(),
	});
	const [products, setProducts] = useState(null);
	const [productsCount, setProductsCount] = useState(null);
	const [productsLoading, setProductsLoading] = useState(false);
	const [query, setQuery] = useState('');

	useEffect(() => {
		setDebouncedGetAccounts(() =>
			debouncePromise(AccountsResource.getAccounts, fetchDataDebounce)
		);
		setDebouncedGetCarts(() =>
			debouncePromise(
				CartResource.getCartsByAccountIdAndChannelId,
				fetchDataDebounce
			)
		);
		setDebouncedGetProducts(() =>
			debouncePromise(
				CatalogResource.getProductsByChannelId,
				fetchDataDebounce
			)
		);
	}, [fetchDataDebounce]);

	const getProducts = useCallback(() => {
		composeDataUpdate(
			debouncedGetProducts(channelId, null, {
				pageSize: resultsPageSize,
				search: query,
			}),
			isMounted,
			setProducts,
			setProductsCount,
			setProductsLoading
		);
	}, [debouncedGetProducts, channelId, resultsPageSize, query, isMounted]);

	const getAccounts = useCallback(() => {
		composeDataUpdate(
			debouncedGetAccounts(null, {
				pageSize: resultsPageSize,
				search: query,
			}),
			isMounted,
			setAccounts,
			setAccountsCount,
			setAccountsLoading
		);
	}, [debouncedGetAccounts, resultsPageSize, query, isMounted]);

	const getCarts = useCallback(() => {
		composeDataUpdate(
			debouncedGetCarts(accountId, channelId, {
				pageSize: resultsPageSize,
				search: query,
			}),
			isMounted,
			setCarts,
			setCartsCount,
			setCartsLoading
		);
	}, [
		debouncedGetCarts,
		accountId,
		channelId,
		query,
		resultsPageSize,
		isMounted,
	]);

	function resetContent() {
		setAccounts(null);
		setCarts(null);
		setProducts(null);
		setAccountsCount(null);
		setCartsCount(null);
		setProductsCount(null);
	}

	useEffect(() => {
		setActive(Boolean(query));

		if (!query) {
			resetContent();

			return;
		}

		setProductsLoading(true);
		setAccountsLoading(true);
		setCartsLoading(true);

		getProducts();
		getAccounts();
		getCarts();
	}, [query, getProducts, getAccounts, getCarts]);

	useEffect(() => {
		function handleClick(event) {
			if (
				!(
					event.target.closest(`#${ids.menu}`) ||
					event.target.id === ids.input
				)
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
		<>
			<ClayInput
				id={ids.input}
				onChange={(event) => setQuery(event.target.value)}
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
						header={Liferay.Language.get('catalog[noun]')}
					>
						{!productsLoading ? (
							products?.length ? (
								<>
									{products.map((product) => (
										<SearchItem
											className="product-item"
											href={formatActionUrl(
												productURLTemplate,
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
												productsSearchURLTemplate,
												{
													query,
												}
											)}
										>
											{sub(
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

					{accountId && (
						<ClayDropDown.Group
							header={Liferay.Language.get('orders')}
						>
							{!cartsLoading ? (
								carts?.length ? (
									<>
										{carts.map((cart) => (
											<ClayDropDown.Item
												className="order-item"
												href={formatActionUrl(
													cartURLTemplate,
													cart
												)}
												key={cart.id}
											>
												{cart.id}
											</ClayDropDown.Item>
										))}
										{cartsCount > carts.length && (
											<ClayDropDown.Item
												href={formatActionUrl(
													cartsSearchURLTemplate,
													{
														query,
													}
												)}
											>
												{sub(
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
					)}

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
												accountURLTemplate,
												account
											)}
											key={account.id}
											label={account.name}
											stickerShape="circle"
											thumbnailUrl={account.logoURL}
										/>
									))}
									{accountsCount > accounts.length && (
										<ClayDropDown.Item
											href={formatActionUrl(
												accountsSearchURLTemplate,
												{
													query,
												}
											)}
										>
											{sub(
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
						href={formatActionUrl(globalSearchURLTemplate, {
							query,
						})}
					>
						{Liferay.Language.get('more-global-results')}
					</ClayDropDown.Item>
				</ClayDropDown.ItemList>
			</ClayDropDown.Menu>
		</>
	);
}

GlobalSearch.propTypes = {
	accountId: PropTypes.number,
	accountURLTemplate: PropTypes.string.isRequired,
	accountsSearchURLTemplate: PropTypes.string.isRequired,
	cartURLTemplate: PropTypes.string.isRequired,
	cartsSearchURLTemplate: PropTypes.string.isRequired,
	channelId: PropTypes.number.isRequired,
	fetchDataDebounce: PropTypes.number.isRequired,
	globalSearchURLTemplate: PropTypes.string.isRequired,
	productURLTemplate: PropTypes.string.isRequired,
	productsSearchURLTemplate: PropTypes.string.isRequired,
	resultsPageSize: PropTypes.number.isRequired,
};

GlobalSearch.defaultProps = {
	fetchDataDebounce: 300,
	resultsPageSize: 4,
};

export default GlobalSearch;
