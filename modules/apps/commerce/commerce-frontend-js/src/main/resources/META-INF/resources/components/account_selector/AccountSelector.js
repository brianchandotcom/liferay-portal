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

import ClayButton, { ClayButtonWithIcon } from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import { ClayIconSpriteContext } from '@clayui/icon';
import ClayLink from '@clayui/link';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClaySticker from '@clayui/sticker';
import ClayTable from '@clayui/table';
import PropTypes from 'prop-types';
import React, { useEffect, useState } from 'react';

// import {CSSTransition} from 'react-transition-group';

import {
	CURRENT_ACCOUNT_CHANGED,
	CURRENT_ORDER_CHANGED,
} from '../../utilities/eventsDefinitions';
import { usePersistentState } from '../../utilities/hooks';
import { formatActionUrl } from '../../utilities/index';
import DateRenderer from '../data_renderers/DateRenderer';
import StatusRenderer from '../data_renderers/StatusRenderer';
import Autocomplete from './../autocomplete/Autocomplete';

function formatStickerName(name) {
	const words = name.split(' ');

	if (words.length === 1) {
		return words[0].substring(0, 2).toUpperCase();
	}

	return (words[0].charAt(0) + words[1].charAt(0)).toUpperCase();
}

function AccountSticker({ name, thumbnail }) {
	return (
		<ClaySticker className="mr-3" shape="user-icon" size="xl">
			{thumbnail ? (
				<ClaySticker.Image
					alt={name}
					src={thumbnail}
				/>
			) : (
					formatStickerName(name)
				)}
		</ClaySticker>
	)
}

function AccountSelector(props) {
	const [active, setActive] = useState(false);
	const [currentAccount, updateCurrentAccount] = usePersistentState('currentAccount');
	const [currentOrder, updateCurrentOrder] = usePersistentState('currentOrder');
	const [currentView, setCurrentView] = useState('accounts');

	useEffect(() => {
		function handleOrderChanged({ order }) {
			if (order && (order.id !== currentOrder?.id)) {
				updateCurrentOrder(order)
			}
		}

		Liferay.on(CURRENT_ORDER_CHANGED, handleOrderChanged)

		return () => Liferay.detach(CURRENT_ORDER_CHANGED, handleOrderChanged)
	}, [currentOrder, updateCurrentOrder])

	useEffect(() => {
		Liferay.fire(CURRENT_ORDER_CHANGED, currentOrder);
	}, [currentOrder]);

	useEffect(() => {
		Liferay.fire(CURRENT_ACCOUNT_CHANGED, currentAccount);
	}, [currentAccount]);

	return (
		<>
			<ClayIconSpriteContext.Provider value={props.spritemap}>
				<ClayDropDown
					active={active}
					className="dropdown-wide dropdown-wide-container"
					onActiveChange={setActive}
					trigger={
						<ClayButton className="d-flex" displayType="secondary">
							{currentAccount && (
								<AccountSticker {...currentAccount} />
							)}
							<div className="account-selector-info">
								{currentAccount ? (
									<>
										<h4 className="text-left">
											{currentAccount.name}
										</h4>
										<small className="order-details">
											{currentOrder ? (
												<>
													<span className="order-id">
														{currentOrder?.id}
													</span>
													<span className="order-label">
														{currentOrder?.orderStatusInfo.label_i18n}
													</span>
												</>
											) : (
													Liferay.Language.get(
														'no-orders-available'
													)
												)}
										</small>
									</>
								) : (
										<h4>
											{Liferay.Language.get(
												'select-account-and-order'
											)}
										</h4>
									)}
							</div>
						</ClayButton>
					}
				>
					{/* <CSSTransition
						className="menu-primary"
						in={currentView === 'accounts'}
						timeout={100}
						unmountOnExit
					> */}
					{currentView === 'accounts' && (
						<Autocomplete
							alwaysActive={true}
							apiUrl="/o/headless-commerce-admin-account/v1.0/accounts/"
							autofill={true}
							customView={({ items }) => {
								if (items && items.length) {

									return (
										<ClayDropDown.ItemList className="dropdown-section">
											<ClayDropDown.Group
												header={Liferay.Language.get(
													'select-accounts'
												)}
											>
												{items.map((item) => (
													<ClayDropDown.Item
														key={item.id}
														onClick={(_) => {
															setCurrentView(
																'orders'
															);
															updateCurrentAccount(
																item
															);
															updateCurrentOrder(
																null
															);
														}}
													>
														{item.name}
													</ClayDropDown.Item>
												))}
											</ClayDropDown.Group>
										</ClayDropDown.ItemList>
									)
								}
								if (items && !items.length) {
									return (
										<ClayDropDown.Caption>
											{Liferay.Language.get('orders-not-found')}
										</ClayDropDown.Caption>
									)
								}

								return (<ClayLoadingIndicator className="mt-3" />)


							}}
							infinityScrollMode={true}
							inputClass="p-2"
							inputName="account-search"
							inputPlaceholder={Liferay.Language.get(
								'search-accounts'
							)}
							itemsKey="id"
							itemsLabel="name"
						/>
					)}
					{/* </CSSTransition>

					<CSSTransition
						classNames="menu-secondary"
						in={currentView === 'orders'}
						timeout={100}
						unmountOnExit
					> */}
					{currentView === 'orders' && (
						<>
							<div className="dropdown-section inline-item">
								<ClayButtonWithIcon
									displayType="secondary"
									onClick={() => {
										setCurrentView('accounts');
									}}
									symbol="angle-left-small"
								/>
								<h4 className="dropdown-section m-auto pl-4 text-center" href="#">{currentAccount?.name}</h4>
							</div>
							<Autocomplete
								alwaysActive={true}
								apiUrl={`/o/headless-commerce-admin-order/v1.0/orders?sort=modifiedDate:desc&filter=(accountId/any(x:(x eq ${currentAccount.id})))`}
								autofill={true}
								customView={({ items }) => {
									if (items && items.length) {
										return (
											<>
												<ClayDropDown.ItemList className="dropdown-list-padding inline-scroller">
													<ClayDropDown.Group>
														<ClayTable borderless hover>
															<ClayTable.Head>
																<ClayTable.Row>
																	<ClayTable.Cell
																		headingCell
																	>
																		{Liferay.Language.get(
																			'order-number'
																		)}
																	</ClayTable.Cell>
																	<ClayTable.Cell
																		headingCell
																	>
																		{Liferay.Language.get(
																			'status'
																		)}
																	</ClayTable.Cell>
																	<ClayTable.Cell
																		headingCell
																	>
																		{Liferay.Language.get(
																			'last-modified'
																		)}
																	</ClayTable.Cell>
																</ClayTable.Row>
															</ClayTable.Head>
															<ClayTable.Body>
																{items.map((item) => (
																	<ClayTable.Row
																		key={item.id}
																	>
																		<ClayTable.Cell
																			headingTitle
																		>
																			<a href={formatActionUrl(props.viewOrderUrl, item)}>
																				{item.id}
																			</a>
																		</ClayTable.Cell>
																		<ClayTable.Cell>
																			<StatusRenderer
																				value={
																					item.orderStatusInfo
																				}
																			/>
																		</ClayTable.Cell>
																		<ClayTable.Cell>
																			<DateRenderer
																				value={
																					item.modifiedDate
																				}
																			/>
																		</ClayTable.Cell>
																	</ClayTable.Row>
																))}
															</ClayTable.Body>
														</ClayTable>
													</ClayDropDown.Group>
												</ClayDropDown.ItemList>
												<div className="dropdown-section">
													<ClayLink
														className="btn btn-primary d-block"
														displayType="unstyled"
														href={props.createNewOrderUrl}>
														{Liferay.Language.get("create-new-order")}
													</ClayLink>
												</div>
											</>
										)
									} else if (items && !items.length) {
										return (
											<ClayDropDown.Caption>
												{Liferay.Language.get('orders-not-found')}
											</ClayDropDown.Caption>
										)
									} else {
										return ( 
											<ClayLoadingIndicator className="mt-3"/>
										)
									}

								}}
								infinityScrollMode={true}
								inputClass="dropdown-section"
								inputName="order-search"
								inputPlaceholder={Liferay.Language.get(
									'search-order'
								)}
								itemsKey="id"
								itemsLabel="name"
								onItemsUpdated={(items) => {
									if (!currentOrder && items?.length) {
										updateCurrentOrder(
											items[0]
										)
									}
								}}
							/>
						</>

					)}
					{/* </CSSTransition> */}

				</ClayDropDown>
			</ClayIconSpriteContext.Provider>

		</>
	);
}

AccountSelector.propTypes = {
	createNewOrderUrl: PropTypes.string,
	spritemap: PropTypes.string,
	viewOrderUrl: PropTypes.string,
};

export default AccountSelector;
