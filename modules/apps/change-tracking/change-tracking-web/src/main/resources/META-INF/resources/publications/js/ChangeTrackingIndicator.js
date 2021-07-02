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

import ClayAlert from '@clayui/alert';
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {Align, ClayDropDownWithItems} from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import ClayManagementToolbar, {
	ClayResultsBar,
} from '@clayui/management-toolbar';
import ClayModal, {useModal} from '@clayui/modal';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import ClaySticker from '@clayui/sticker';
import ClayTable from '@clayui/table';
import {fetch} from 'frontend-js-web';
import React, {useCallback, useEffect, useState} from 'react';

const PublicationsSearchContainer = ({
	ascending,
	column,
	containerView,
	fetchDataURL,
	filterEntries,
	getListItem,
	getTableHead,
	getTableRow,
	orderByItems,
	preferencesPrefix,
	saveDisplayPreferenceURL,
	setAscending,
	setColumn,
	spritemap,
}) => {
	const VIEW_TYPE_LIST = 'list';
	const VIEW_TYPE_TABLE = 'table';

	const [loading, setLoading] = useState(false);
	const [resultsKeywords, setResultsKeywords] = useState('');
	const [searchTerms, setSearchTerms] = useState('');
	const [state, setState] = useState({
		delta: 20,
		page: 1,
	});

	const [viewType, setViewType] = useState(
		getTableRow ? VIEW_TYPE_TABLE : VIEW_TYPE_LIST
	);

	const [initialized, setInitialized] = useState(false);

	useEffect(() => {
		if (initialized) {
			return;
		}

		setInitialized(true);
		setLoading(true);

		fetch(fetchDataURL)
			.then((response) => response.json())
			.then((json) => {
				if (!json.entries) {
					const fetchData = {
						errorMessage: Liferay.Language.get(
							'an-unexpected-error-occurred'
						),
					};

					setState({
						delta: state.delta,
						fetchData,
						page: state.page,
					});

					setLoading(false);

					return;
				}

				const newState = {
					delta: state.delta,
					fetchData: json,
					page: state.page,
				};

				const lastPage = Math.ceil(
					json.entries.length / newState.delta
				);

				if (lastPage < 1) {
					newState.page = 1;
				}
				else if (newState.page > lastPage) {
					newState.page = lastPage;
				}

				setState(newState);

				setLoading(false);
			})
			.catch(() => {
				const fetchData = {
					errorMessage: Liferay.Language.get(
						'an-unexpected-error-occurred'
					),
				};

				setState({
					delta: state.delta,
					fetchData,
					page: state.page,
				});

				setLoading(false);
			});
	}, [fetchDataURL, initialized, state]);

	const [showMobile, setShowMobile] = useState(false);

	const saveDisplayPreference = useCallback(
		(key, value) => {
			if (!preferencesPrefix || !saveDisplayPreferenceURL) {
				return;
			}

			AUI().use('liferay-portlet-url', () => {
				const portletURL = Liferay.PortletURL.createURL(
					saveDisplayPreferenceURL
				);

				portletURL.setParameter('key', preferencesPrefix + '-' + key);
				portletURL.setParameter('value', value.toString());

				fetch(portletURL.toString());
			});
		},
		[preferencesPrefix, saveDisplayPreferenceURL]
	);

	useEffect(() => {
		saveDisplayPreference('order-by-ascending', ascending);
	}, [ascending, saveDisplayPreference]);

	useEffect(() => {
		saveDisplayPreference('order-by-column', column);
	}, [column, saveDisplayPreference]);

	useEffect(() => {
		saveDisplayPreference('view-type', viewType);
	}, [viewType, saveDisplayPreference]);

	const onSubmit = useCallback(
		(keywords, newDelta, newPage) => {
			setResultsKeywords(keywords);

			AUI().use('liferay-portlet-url', () => {
				const portletURL = Liferay.PortletURL.createURL(fetchDataURL);

				if (keywords) {
					portletURL.setParameter('keywords', keywords);
				}
				else {
					portletURL.setParameter('keywords', '');
				}

				setLoading(true);

				fetch(portletURL.toString())
					.then((response) => response.json())
					.then((json) => {
						if (!json.entries) {
							const fetchData = {
								errorMessage: Liferay.Language.get(
									'an-unexpected-error-occurred'
								),
							};

							setState({
								delta: state.delta,
								fetchData,
								page: state.page,
							});

							setLoading(false);

							return;
						}

						const newState = {
							delta: newDelta,
							fetchData: json,
							page: newPage,
						};

						const lastPage = Math.ceil(
							json.entries.length / newState.delta
						);

						if (lastPage < 1) {
							newState.page = 1;
						}
						else if (newState.page > lastPage) {
							newState.page = lastPage;
						}

						setState(newState);

						setLoading(false);
					})
					.catch(() => {
						const fetchData = {
							errorMessage: Liferay.Language.get(
								'an-unexpected-error-occurred'
							),
						};

						setState({
							delta: state.delta,
							fetchData,
							page: state.page,
						});

						setLoading(false);
					});
			});
		},
		[fetchDataURL, state]
	);

	const format = (key, args) => {
		const SPLIT_REGEX = /({\d+})/g;

		const keyArray = key
			.split(SPLIT_REGEX)
			.filter((val) => val.length !== 0);

		for (let i = 0; i < args.length; i++) {
			const arg = args[i];

			const indexKey = `{${i}}`;

			let argIndex = keyArray.indexOf(indexKey);

			while (argIndex >= 0) {
				keyArray.splice(argIndex, 1, arg);

				argIndex = keyArray.indexOf(indexKey);
			}
		}

		return keyArray.join('');
	};

	const onDeltaChange = (value) => {
		const newState = {
			delta: value,
			page: 1,
		};

		if (state.fetchData) {
			newState.fetchData = state.fetchData;
		}

		setState(newState);
	};

	const onPageChange = (value) => {
		const newState = {
			delta: state.delta,
			page: value,
		};

		if (state.fetchData) {
			newState.fetchData = state.fetchData;
		}

		setState(newState);
	};

	const renderManagementToolbar = () => {
		const filterDisabled =
			!state.fetchData ||
			!state.fetchData.entries ||
			state.fetchData.entries.length === 0;

		const searchDisabled =
			!resultsKeywords &&
			state.fetchData &&
			state.fetchData.entries &&
			state.fetchData.entries.length === 0;

		const items = [];

		for (let i = 0; i < orderByItems.length; i++) {
			const orderByItem = orderByItems[i];

			items.push({
				active: column === orderByItem.value,
				label: orderByItem.label,
				onClick: () => setColumn(orderByItem.value),
			});
		}

		items.sort((a, b) => {
			if (a.label < b.label) {
				return -1;
			}

			return 1;
		});

		const viewTypeItems = [];

		if (getListItem) {
			viewTypeItems.push({
				active: viewType === VIEW_TYPE_LIST,
				label: Liferay.Language.get('list'),
				onClick: () => setViewType(VIEW_TYPE_LIST),
				symbolLeft: 'list',
			});
		}

		if (getTableRow) {
			viewTypeItems.push({
				active: viewType === VIEW_TYPE_TABLE,
				label: Liferay.Language.get('table'),
				onClick: () => setViewType(VIEW_TYPE_TABLE),
				symbolLeft: 'table',
			});
		}

		const activeViewTypeItem = viewTypeItems.find((type) => type.active);

		return (
			<ClayManagementToolbar>
				<ClayManagementToolbar.ItemList>
					<ClayManagementToolbar.Item>
						<ClayDropDownWithItems
							items={[
								{
									items,
									label: Liferay.Language.get('order-by'),
									type: 'group',
								},
							]}
							spritemap={spritemap}
							trigger={
								<ClayButton
									className="nav-link"
									disabled={filterDisabled}
									displayType="unstyled"
								>
									<span className="navbar-breakpoint-down-d-none">
										<span className="navbar-text-truncate">
											{Liferay.Language.get(
												'filter-and-order'
											)}
										</span>

										<ClayIcon
											className="inline-item inline-item-after"
											spritemap={spritemap}
											symbol="caret-bottom"
										/>
									</span>
									<span className="navbar-breakpoint-d-none">
										<ClayIcon
											spritemap={spritemap}
											symbol="filter"
										/>
									</span>
								</ClayButton>
							}
						/>
					</ClayManagementToolbar.Item>
					<ClayManagementToolbar.Item
						data-tooltip-align="top"
						title={Liferay.Language.get('reverse-sort-direction')}
					>
						<ClayButton
							className="nav-link nav-link-monospaced"
							disabled={filterDisabled}
							displayType="unstyled"
							onClick={() => setAscending(!ascending)}
						>
							<ClayIcon
								spritemap={spritemap}
								symbol={
									ascending
										? 'order-list-down'
										: 'order-list-up'
								}
							/>
						</ClayButton>
					</ClayManagementToolbar.Item>
				</ClayManagementToolbar.ItemList>
				<ClayManagementToolbar.Search
					onSubmit={(event) => {
						event.preventDefault();

						onSubmit(searchTerms.trim(), state.delta, 1);
					}}
					showMobile={showMobile}
				>
					<ClayInput.Group>
						<ClayInput.GroupItem>
							<ClayInput
								aria-label={Liferay.Language.get('search')}
								className="input-group-inset input-group-inset-after"
								disabled={searchDisabled}
								onChange={(event) =>
									setSearchTerms(event.target.value)
								}
								placeholder={`${Liferay.Language.get(
									'search'
								)}...`}
								type="text"
								value={searchTerms}
							/>
							<ClayInput.GroupInsetItem after tag="span">
								<ClayButtonWithIcon
									className="navbar-breakpoint-d-none"
									displayType="unstyled"
									onClick={() => setShowMobile(false)}
									symbol="times"
								/>
								<ClayButtonWithIcon
									disabled={searchDisabled}
									displayType="unstyled"
									symbol="search"
									type="submit"
								/>
							</ClayInput.GroupInsetItem>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</ClayManagementToolbar.Search>
				<ClayManagementToolbar.ItemList>
					<ClayManagementToolbar.Item className="navbar-breakpoint-d-none">
						<ClayButton
							className="nav-link nav-link-monospaced"
							disabled={searchDisabled}
							displayType="unstyled"
							onClick={() => setShowMobile(true)}
						>
							<ClayIcon symbol="search" />
						</ClayButton>
					</ClayManagementToolbar.Item>
				</ClayManagementToolbar.ItemList>

				{viewTypeItems.length > 1 && (
					<ClayManagementToolbar.ItemList>
						<ClayManagementToolbar.Item
							data-tooltip-align="top"
							title={Liferay.Language.get('display-style')}
						>
							<ClayDropDownWithItems
								items={viewTypeItems}
								spritemap={spritemap}
								trigger={
									<ClayButton
										className="nav-link nav-link-monospaced"
										displayType="unstyled"
									>
										<ClayIcon
											spritemap={spritemap}
											symbol={
												activeViewTypeItem
													? activeViewTypeItem.symbolLeft
													: ''
											}
										/>
									</ClayButton>
								}
							/>
						</ClayManagementToolbar.Item>
					</ClayManagementToolbar.ItemList>
				)}
			</ClayManagementToolbar>
		);
	};

	const renderPagination = () => {
		if (state.fetchData.entries.length <= 5) {
			return '';
		}

		return (
			<ClayPaginationBarWithBasicItems
				activeDelta={state.delta}
				activePage={state.page}
				deltas={[4, 8, 20, 40, 60].map((size) => ({
					label: size,
				}))}
				ellipsisBuffer={3}
				onDeltaChange={(value) => onDeltaChange(value)}
				onPageChange={(value) => onPageChange(value)}
				totalItems={state.fetchData.entries.length}
			/>
		);
	};

	const renderResultsBar = () => {
		if (!resultsKeywords) {
			return '';
		}

		let count = 0;
		let key = Liferay.Language.get('x-results-for');

		if (state.fetchData && state.fetchData.entries) {
			count = state.fetchData.entries.length;

			if (count === 1) {
				key = Liferay.Language.get('x-result-for');
			}
		}

		return (
			<div className="results-bar">
				<ClayResultsBar>
					<ClayResultsBar.Item expand>
						<span className="component-text text-truncate-inline">
							<span className="text-truncate">
								{format(key, [count]) + ' '}

								<strong>{resultsKeywords}</strong>
							</span>
						</span>
					</ClayResultsBar.Item>
					<ClayResultsBar.Item>
						<ClayButton
							className="component-link tbar-link"
							displayType="unstyled"
							onClick={() => {
								onSubmit('', state.delta, 1);
								setSearchTerms('');
							}}
						>
							{Liferay.Language.get('clear')}
						</ClayButton>
					</ClayResultsBar.Item>
				</ClayResultsBar>
			</div>
		);
	};

	const renderBody = () => {
		if (!state.fetchData) {
			return <span aria-hidden="true" className="loading-animation" />;
		}
		else if (state.fetchData.errorMessage) {
			return (
				<ClayAlert
					displayType="danger"
					spritemap={spritemap}
					title={Liferay.Language.get('error')}
				>
					{state.fetchData.errorMessage}
				</ClayAlert>
			);
		}
		else if (state.fetchData.entries.length === 0) {
			let className = 'taglib-empty-result-message';

			if (containerView) {
				className += ' sheet';
			}

			if (loading) {
				className += ' publications-loading';
			}

			return (
				<div
					className={
						containerView
							? 'container-fluid container-fluid-max-xl'
							: ''
					}
				>
					<div className={containerView ? 'container-view' : ''}>
						<div className={className}>
							<div
								className={
									resultsKeywords
										? 'taglib-empty-search-result-message-header'
										: 'taglib-empty-result-message-header'
								}
							/>
							<div className="sheet-text text-center">
								{Liferay.Language.get(
									'no-publications-were-found'
								)}
							</div>
						</div>
					</div>
				</div>
			);
		}

		const entries = filterEntries(
			ascending,
			column,
			state.delta,
			state.fetchData.entries,
			state.page
		);

		if (viewType === VIEW_TYPE_LIST) {
			const items = [];

			for (let i = 0; i < entries.length; i++) {
				items.push(getListItem(entries[i], state.fetchData));
			}

			return (
				<div
					className={
						containerView
							? 'container-fluid container-fluid-max-xl'
							: ''
					}
				>
					<div className={containerView ? 'container-view' : ''}>
						<ClayList
							className={
								loading
									? 'publications-loading publications-table'
									: 'publications-table'
							}
						>
							{items}
						</ClayList>

						{renderPagination()}
					</div>
				</div>
			);
		}
		else if (viewType === VIEW_TYPE_TABLE) {
			const rows = [];

			for (let i = 0; i < entries.length; i++) {
				rows.push(getTableRow(entries[i], state.fetchData));
			}

			return (
				<div
					className={
						containerView
							? 'container-fluid container-fluid-max-xl'
							: ''
					}
				>
					<div className={containerView ? 'container-view' : ''}>
						<ClayTable
							className={
								loading
									? 'publications-loading publications-table'
									: 'publications-table'
							}
							headingNoWrap
							hover={false}
						>
							{getTableHead ? getTableHead() : ''}
							<ClayTable.Body>{rows}</ClayTable.Body>
						</ClayTable>

						{renderPagination()}
					</div>
				</div>
			);
		}

		return '';
	};

	return (
		<>
			{renderManagementToolbar()}
			{renderResultsBar()}
			{renderBody()}
		</>
	);
};

export default ({
	checkoutDropdownItem,
	createDropdownItem,
	getSelectPublicationsURL,
	iconClass,
	iconName,
	orderByAscending,
	orderByColumn,
	preferencesPrefix,
	reviewDropdownItem,
	saveDisplayPreferenceURL,
	spritemap,
	title,
}) => {
	const COLUMN_MODIFIED_DATE = 'modifiedDate';
	const COLUMN_NAME = 'name';

	let initialAscending = false;

	if (orderByAscending === true.toString()) {
		initialAscending = true;
	}

	const [ascending, setAscending] = useState(initialAscending);

	let initialColumn = orderByColumn;

	if (
		!initialColumn ||
		(initialColumn !== COLUMN_NAME &&
			initialColumn !== COLUMN_MODIFIED_DATE)
	) {
		initialColumn = COLUMN_MODIFIED_DATE;
	}

	const [column, setColumn] = useState(initialColumn);

	const [showModal, setShowModal] = useState(false);

	const dropdownItems = [];

	if (checkoutDropdownItem) {
		dropdownItems.push(checkoutDropdownItem);
	}

	dropdownItems.push({
		label: Liferay.Language.get('select-a-publication'),
		onClick: () => setShowModal(true),
		symbolLeft: 'cards2',
	});

	if (createDropdownItem) {
		dropdownItems.push(createDropdownItem);
	}

	if (reviewDropdownItem) {
		dropdownItems.push({type: 'divider'});
		dropdownItems.push(reviewDropdownItem);
	}

	/* eslint-disable no-unused-vars */
	const {observer, onClose} = useModal({
		onClose: () => setShowModal(false),
	});

	const filterEntries = (ascending, column, delta, entries, page) => {
		const filteredEntries = entries.slice(0);

		if (column === COLUMN_MODIFIED_DATE) {
			filteredEntries.sort((a, b) => {
				if (a.modifiedDate < b.modifiedDate) {
					if (ascending) {
						return -1;
					}

					return 1;
				}

				if (a.modifiedDate > b.modifiedDate) {
					if (ascending) {
						return 1;
					}

					return -1;
				}

				const nameA = a.name.toLowerCase();
				const nameB = b.name.toLowerCase();

				if (nameA < nameB) {
					return -1;
				}

				if (nameA > nameB) {
					return 1;
				}

				return 0;
			});
		}
		else if (column === COLUMN_NAME) {
			filteredEntries.sort((a, b) => {
				const nameA = a.name.toLowerCase();
				const nameB = b.name.toLowerCase();

				if (nameA < nameB) {
					if (ascending) {
						return -1;
					}

					return 1;
				}

				if (nameA > nameB) {
					if (ascending) {
						return 1;
					}

					return -1;
				}

				if (a.modifiedDate < b.modifiedDate) {
					return -1;
				}

				if (a.modifiedDate > b.modifiedDate) {
					return 1;
				}

				return 0;
			});
		}

		if (entries.length > 5) {
			return filteredEntries.slice(delta * (page - 1), delta * page);
		}

		return filteredEntries;
	};

	const renderUserPortrait = (entry, userInfo) => {
		const user = userInfo[entry.userId];

		return (
			<ClaySticker
				className={`sticker-user-icon ${
					user.portraitURL
						? ''
						: 'user-icon-color-' + (entry.userId % 10)
				}`}
				data-tooltip-align="top"
				title={user.userName}
			>
				{user.portraitURL ? (
					<div className="sticker-overlay">
						<img className="sticker-img" src={user.portraitURL} />
					</div>
				) : (
					<ClayIcon symbol="user" />
				)}
			</ClaySticker>
		);
	};

	const getListItem = (entry, fetchData) => {
		const dropdownItems = [];

		let itemField = (
			<ClayList.ItemField
				className="font-italic"
				data-tooltip-align="top"
				expand
				title={Liferay.Language.get(
					'already-working-on-this-publication'
				)}
			>
				<ClayList.ItemTitle>{entry.name}</ClayList.ItemTitle>
				<ClayList.ItemText subtext>
					{entry.description}
				</ClayList.ItemText>
			</ClayList.ItemField>
		);

		if (entry.checkoutURL) {
			dropdownItems.push({
				label: Liferay.Language.get('work-on-publication'),
				onClick: () => {
					AUI().use('liferay-portlet-url', () => {
						const portletURL = Liferay.PortletURL.createURL(
							entry.checkoutURL
						);

						portletURL.setParameter(
							'redirect',
							window.location.pathname + window.location.search
						);

						submitForm(document.hrefFm, portletURL.toString());
					});
				},
				symbolLeft: 'radio-button',
			});

			itemField = (
				<ClayList.ItemField expand>
					<a
						onClick={() => {
							AUI().use('liferay-portlet-url', () => {
								const portletURL = Liferay.PortletURL.createURL(
									entry.checkoutURL
								);

								portletURL.setParameter(
									'redirect',
									window.location.pathname +
										window.location.search
								);

								submitForm(
									document.hrefFm,
									portletURL.toString()
								);
							});
						}}
					>
						<ClayList.ItemTitle>{entry.name}</ClayList.ItemTitle>
						<ClayList.ItemText subtext>
							{entry.description}
						</ClayList.ItemText>
					</a>
				</ClayList.ItemField>
			);
		}
		else if (entry.readOnly) {
			itemField = (
				<ClayList.ItemField expand>
					<ClayButton
						data-tooltip-align="top"
						disabled
						displayType="unstyled"
						title={Liferay.Language.get(
							'you-do-not-have-permission-to-update-this-publication'
						)}
					>
						<ClayList.ItemTitle>{entry.name}</ClayList.ItemTitle>
						<ClayList.ItemText subtext>
							{entry.description}
						</ClayList.ItemText>
					</ClayButton>
				</ClayList.ItemField>
			);
		}

		dropdownItems.push({
			label: Liferay.Language.get('review-changes'),
			onClick: () => {
				AUI().use('liferay-portlet-url', () => {
					const portletURL = Liferay.PortletURL.createURL(
						entry.viewURL
					);

					portletURL.setParameter(
						'redirect',
						window.location.pathname + window.location.search
					);

					submitForm(document.hrefFm, portletURL.toString());
				});
			},
			symbolLeft: 'list-ul',
		});

		return (
			<ClayList.Item flex>
				<ClayList.ItemField>
					{renderUserPortrait(entry, fetchData.userInfo)}
				</ClayList.ItemField>
				{itemField}
				{entry.viewURL && (
					<>
						<ClayList.ItemField>
							<ClayList.QuickActionMenu>
								{entry.checkoutURL && (
									<ClayList.QuickActionMenu.Item
										data-tooltip-align="top"
										onClick={() => {
											AUI().use(
												'liferay-portlet-url',
												() => {
													const portletURL = Liferay.PortletURL.createURL(
														entry.checkoutURL
													);

													portletURL.setParameter(
														'redirect',
														window.location
															.pathname +
															window.location
																.search
													);

													submitForm(
														document.hrefFm,
														portletURL.toString()
													);
												}
											);
										}}
										spritemap={spritemap}
										symbol="radio-button"
										title={Liferay.Language.get(
											'work-on-publication'
										)}
									/>
								)}
								<ClayList.QuickActionMenu.Item
									data-tooltip-align="top"
									onClick={() => {
										AUI().use('liferay-portlet-url', () => {
											const portletURL = Liferay.PortletURL.createURL(
												entry.viewURL
											);

											portletURL.setParameter(
												'redirect',
												window.location.pathname +
													window.location.search
											);

											submitForm(
												document.hrefFm,
												portletURL.toString()
											);
										});
									}}
									spritemap={spritemap}
									symbol="list-ul"
									title={Liferay.Language.get(
										'review-changes'
									)}
								/>
							</ClayList.QuickActionMenu>
						</ClayList.ItemField>
						<ClayList.ItemField>
							<ClayDropDownWithItems
								alignmentPosition={Align.BottomLeft}
								items={dropdownItems}
								trigger={
									<ClayButtonWithIcon
										displayType="unstyled"
										small
										spritemap={spritemap}
										symbol="ellipsis-v"
									/>
								}
							/>
						</ClayList.ItemField>
					</>
				)}
			</ClayList.Item>
		);
	};

	const renderModal = () => {
		if (!showModal) {
			return '';
		}

		return (
			<ClayModal
				className="modal-height-full select-publications"
				observer={observer}
				size="lg"
				spritemap={spritemap}
			>
				<ClayModal.Header withTitle>
					{Liferay.Language.get('select-a-publication')}
				</ClayModal.Header>
				<ClayModal.Body scrollable>
					<PublicationsSearchContainer
						ascending={ascending}
						column={column}
						fetchDataURL={getSelectPublicationsURL}
						filterEntries={filterEntries}
						getListItem={getListItem}
						orderByItems={[
							{
								label: Liferay.Language.get('modified-date'),
								value: COLUMN_MODIFIED_DATE,
							},
							{
								label: Liferay.Language.get('name'),
								value: COLUMN_NAME,
							},
						]}
						preferencesPrefix={preferencesPrefix}
						saveDisplayPreferenceURL={saveDisplayPreferenceURL}
						setAscending={setAscending}
						setColumn={setColumn}
						spritemap={spritemap}
					/>
				</ClayModal.Body>
			</ClayModal>
		);
	};

	return (
		<>
			{renderModal()}

			<ClayDropDownWithItems
				alignmentPosition={Align.BottomCenter}
				items={dropdownItems}
				trigger={
					<button className="change-tracking-indicator-button">
						<ClayIcon className={iconClass} symbol={iconName} />

						<span className="change-tracking-indicator-title">
							{title}
						</span>

						<ClayIcon symbol="caret-bottom" />
					</button>
				}
			/>
		</>
	);
};
