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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon, {ClayIconSpriteContext} from '@clayui/icon';
import {Col, Row} from '@clayui/layout';
import ClayLink from '@clayui/link';
import ClaySticker from '@clayui/sticker';
import classNames from 'classnames';
import {fetch} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';

import ServiceProvider from '../../ServiceProvider/index';
import {
	CURRENT_ACCOUNT_UPDATED,
	CURRENT_ORDER_UPDATED,
} from '../../utilities/eventsDefinitions';
import InfiniteScroller from '../infinite_scroller/InfiniteScroller';
import Autocomplete from './../autocomplete/Autocomplete';
import OrdersTable from './OrdersTable';

const ordersHeadlessAPIEndpoint = ServiceProvider.AdminOrderAPI('v1').baseURL;
const accountsHeadlessAPIEndpoint = ServiceProvider.AdminAccountAPI('v1')
	.baseURL;

function formatStickerName(name) {
	const words = name.split(' ');

	if (words.length === 1) {
		return words[0].substring(0, 2).toUpperCase();
	}

	return (words[0].charAt(0) + words[1].charAt(0)).toUpperCase();
}

function AccountSticker({name, thumbnail}, size="xl") {
	return (
		<ClaySticker shape="user-icon" size={size}>
			{thumbnail ? (
				<ClaySticker.Image alt={name} src={thumbnail} />
			) : (
				formatStickerName(name)
			)}
		</ClaySticker>
	);
}

function updateRemoteCurrentAccount(id, url) {
	const formData = new FormData();
	formData.append('accountId', id);

	const formattedURL = new URL(url, themeDisplay.getPortalURL());
	formattedURL.searchParams.append('groupId', themeDisplay.getScopeGroupId());
	formattedURL.searchParams.append('p_auth', Liferay.authToken);

	return fetch(formattedURL, {
		body: formData,
		method: 'POST',
	}).then(() => {
		Liferay.fire(CURRENT_ACCOUNT_UPDATED, {id});
	});
}

function AccountSelector(props) {
	const [active, setActive] = useState(false);
	const [currentAccount, updateCurrentAccount] = useState(
		props.currentAccount
	);
	const [currentOrder, updateCurrentOrder] = useState(props.currentOrder);
	const [currentView, setCurrentView] = useState(
		props.currentOrder ? 'orders' : 'accounts'
	);
	const accountsListWrapperRef = useRef();
	const ordersListWrapperRef = useRef();

	useEffect(() => {
		function handleOrderUpdated({id, orderStatusInfo}) {
			updateCurrentOrder((current) => {
				if ((!current && id) || (current && !id) || current.id !== id) {
					return {
						id,
						orderStatusInfo,
					};
				}

				return current;
			});
		}

		Liferay.on(CURRENT_ORDER_UPDATED, handleOrderUpdated);

		return () => Liferay.detach(CURRENT_ORDER_UPDATED, handleOrderUpdated);
	}, [updateCurrentOrder]);

	const loadingView = (
		<ClayDropDown.Caption>
			{Liferay.Language.get('loading')}
		</ClayDropDown.Caption>
	);

	return (
		<ClayIconSpriteContext.Provider value={props.spritemap}>
			<ClayDropDown
				active={active}
				alignmentPosition={props.alignmentPosition}
				className="account-selector-dropdown"
				menuElementAttrs={{className: 'account-selector-dropdown-menu'}}
				onActiveChange={setActive}
				trigger={
					<ClayButton
						className={classNames(
							'btn-account-selector',
							currentAccount && 'account-selected'
						)}
						displayType="secondary"
					>
						{currentAccount ? (
							<>
								<AccountSticker
									className="current-account-thumbnail"
									{...currentAccount}
								/>
								<div className="current-info-container">
									<h4 className="account-name text-truncate">
										{currentAccount.name}
									</h4>
									<small className="order-details">
										{currentOrder ? (
											<Row>
												<Col
													className="border-right order-id"
													size="auto"
												>
													{currentOrder.id}
												</Col>
												<Col className="order-label">
													{
														currentOrder
															.orderStatusInfo
															.label_i18n
													}
												</Col>
											</Row>
										) : (
											<Row>
												<Col className="no-account-selected-placeholder">
													{Liferay.Language.get(
														'no-orders-available'
													)}
												</Col>
											</Row>
										)}
									</small>
								</div>
							</>
						) : (
							<h4 className="no-account-selected-placeholder text-truncate">
								{Liferay.Language.get(
									'select-account-and-order'
								)}
							</h4>
						)}
						<ClayIcon symbol={active ? 'angle-up' : 'angle-down'} />
					</ClayButton>
				}
			>
				{currentView === 'accounts' && (
					<>
						<ClayDropDown.Section>
							<Autocomplete
								apiUrl={accountsHeadlessAPIEndpoint}
								contentWrapperRef={accountsListWrapperRef}
								customView={({
									getPage,
									items,
									loading,
									totalCount,
								}) => {
									if (!items && loading) {
										return loadingView;
									}

									if ((!items || !items.length) && !loading) {
										return (
											<ClayDropDown.Caption>
												{Liferay.Language.get(
													'no-accounts-were-found'
												)}
											</ClayDropDown.Caption>
										);
									}

									return (
										<ClayDropDown.ItemList className="mb-0">
											<ClayDropDown.Group
												header={Liferay.Language.get(
													'select-accounts'
												)}
											>
												<InfiniteScroller
													onBottomTouched={() =>
														!loading && getPage()
													}
													scrollCompleted={
														items &&
														items.length ===
															totalCount
													}
												>
													{items.map((item) => (
														<ClayDropDown.Item
															key={item.id}
															onClick={(_) => {
																updateRemoteCurrentAccount(
																	item.id,
																	props.setCurrentAccountURL
																);
																updateCurrentAccount(
																	item
																);
																setCurrentView(
																	'orders'
																);
																updateCurrentOrder(
																	null
																);
															}}
														>
															{item.name}
														</ClayDropDown.Item>
													))}
												</InfiniteScroller>
											</ClayDropDown.Group>
										</ClayDropDown.ItemList>
									);
								}}
								infinityScrollMode={true}
								inputName="account-search"
								inputPlaceholder={Liferay.Language.get(
									'search-accounts'
								)}
								itemsKey="id"
								itemsLabel="name"
								pageSize={10}
							/>
						</ClayDropDown.Section>
						<ClayDropDown.Divider />
						<div ref={accountsListWrapperRef} />
					</>
				)}
				{currentView === 'orders' && (
					<>
						<ClayDropDown.Section className="d-flex">
							<ClayButtonWithIcon
								className="flex-shrink-0"
								displayType="secondary"
								monospaced
								onClick={() => {
									setCurrentView('accounts');
								}}
								symbol="angle-left-small"
							/>
							<h4 className="m-auto pl-2 text-center text-truncate">
								{currentAccount.name}
							</h4>
						</ClayDropDown.Section>
						<ClayDropDown.Divider />
						<ClayDropDown.Section>
							<Autocomplete
								apiUrl={`${ordersHeadlessAPIEndpoint}?sort=modifiedDate:desc&filter=(accountId/any(x:(x eq ${currentAccount.id})))`}
								contentWrapperRef={ordersListWrapperRef}
								customView={({
									getPage,
									items,
									loading,
									totalCount,
								}) => {
									if (!items && loading) {
										return loadingView;
									}

									if ((!items || !items.length) && !loading) {
										return (
											<ClayDropDown.Caption>
												{Liferay.Language.get(
													'no-orders-were-found'
												)}
											</ClayDropDown.Caption>
										);
									}

									return (
										<ClayDropDown.Group>
											<InfiniteScroller
												onBottomTouched={() =>
													!loading && getPage()
												}
												scrollCompleted={
													items &&
													items.length === totalCount
												}
											>
												<OrdersTable
													orders={items}
													selectOrderURL={
														props.selectOrderURL
													}
												/>
											</InfiniteScroller>
										</ClayDropDown.Group>
									);
								}}
								infinityScrollMode={true}
								inputName="order-search"
								inputPlaceholder={Liferay.Language.get(
									'search-order'
								)}
								itemsKey="id"
								itemsLabel="name"
								pageSize={10}
							/>
						</ClayDropDown.Section>
						<ClayDropDown.Divider />
						<div ref={ordersListWrapperRef} />
						<ClayDropDown.Divider />
						<ClayDropDown.Section>
							<ClayLink
								className="btn btn-primary d-block"
								displayType="unstyled"
								href={props.createNewOrderURL}
							>
								{Liferay.Language.get('create-new-order')}
							</ClayLink>
						</ClayDropDown.Section>
					</>
				)}
			</ClayDropDown>
		</ClayIconSpriteContext.Provider>
	);
}

AccountSelector.propTypes = {
	alignmentPosition: PropTypes.number,
	createNewOrderURL: PropTypes.string.isRequired,
	currentAccount: PropTypes.shape({
		id: PropTypes.string,
		name: PropTypes.string,
		thumbnail: PropTypes.string,
	}),
	currentOrder: PropTypes.shape({
		id: PropTypes.string,
		orderStatusInfo: PropTypes.shape({
			label_i18n: PropTypes.string,
		}),
	}),
	selectOrderURL: PropTypes.string.isRequired,
	setCurrentAccountURL: PropTypes.string.isRequired,
	spritemap: PropTypes.string.isRequired,
};

AccountSelector.defaultProps = {
	alignmentPosition: 3,
};

export default AccountSelector;
