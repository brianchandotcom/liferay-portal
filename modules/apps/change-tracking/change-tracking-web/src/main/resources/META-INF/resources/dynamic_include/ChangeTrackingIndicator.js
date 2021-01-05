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
import {ClayTooltipProvider} from '@clayui/tooltip';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

const ChangeTrackingIndicator = ({
	dropdownItems,
	getSelectPublicationsURL,
	iconClass,
	iconName,
	spritemap,
	title,
}) => {
	const SORT_COLUMN_NAME = 'SORT_COLUMN_NAME';
	const SORT_COLUMN_MODIFIED_DATE = 'SORT_COLUMN_MODIFIED_DATE';

	const [ascending, setAscending] = useState(false);
	const [column, setColumn] = useState(SORT_COLUMN_MODIFIED_DATE);
	const [delta, setDelta] = useState(20);
	const [fetchData, setFetchData] = useState(null);
	const [loading, setLoading] = useState(false);
	const [page, setPage] = useState(1);
	const [resultsKeywords, setResultsKeywords] = useState('');
	const [searchTerms, setSearchTerms] = useState('');
	const [showMobile, setShowMobile] = useState(false);
	const [showModal, setShowModal] = useState(false);

	const indicatorDropdownItems = dropdownItems.slice(0);

	for (let i = 0; i < indicatorDropdownItems.length; i++) {
		const dropdownItem = indicatorDropdownItems[i];

		if (dropdownItem.href === '') {
			indicatorDropdownItems[i] = {
				label: dropdownItem.label,
				onClick: () => setShowModal(true),
				symbolLeft: dropdownItem.symbolLeft,
			};

			break;
		}
	}

	/* eslint-disable no-unused-vars */
	const {observer, onClose} = useModal({
		onClose: () => setShowModal(false),
	});

	useEffect(() => {
		if (!showModal) {
			setAscending(false);
			setColumn(SORT_COLUMN_MODIFIED_DATE);
			setDelta(20);
			setFetchData(null);
			setPage(1);
			setResultsKeywords('');
			setSearchTerms('');
			setShowMobile(false);
			setLoading(false);

			return;
		}

		AUI().use('liferay-portlet-url', () => {
			const portletURL = Liferay.PortletURL.createURL(
				getSelectPublicationsURL
			);

			setLoading(true);

			fetch(portletURL.toString())
				.then((response) => response.json())
				.then((json) => {
					if (!json.entries) {
						setFetchData({
							errorMessage: Liferay.Language.get(
								'an-unexpected-error-occurred'
							),
						});

						return;
					}

					setFetchData(json);
					setLoading(false);
				})
				.catch((response) => {
					setFetchData({
						errorMessage: Liferay.Language.get(
							'an-unexpected-error-occurred'
						),
					});
					setLoading(false);
				});
		});
	}, [getSelectPublicationsURL, showModal]);

	const filterEntries = (entries) => {
		const filteredEntries = entries.slice(0);

		if (column === SORT_COLUMN_MODIFIED_DATE) {
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

				if (a.name < b.name) {
					return -1;
				}

				if (a.name > b.name) {
					return 1;
				}

				return 0;
			});
		}
		else if (column === SORT_COLUMN_NAME) {
			filteredEntries.sort((a, b) => {
				if (a.name < b.name) {
					if (ascending) {
						return -1;
					}

					return 1;
				}

				if (a.name > b.name) {
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

		return filteredEntries;
	};

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

	const getUserName = (entry) => {
		return fetchData.userInfo[entry.userId].userName;
	};

	const getUserPortraitHTML = (entry) => {
		return {__html: fetchData.userInfo[entry.userId].userPortraitHTML};
	};

	const handleDeltaChange = (delta) => {
		setDelta(delta);
		setPage(1);
	};

	const onSubmit = (keywords) => {
		setResultsKeywords(keywords);

		AUI().use('liferay-portlet-url', () => {
			const portletURL = Liferay.PortletURL.createURL(
				getSelectPublicationsURL
			);

			if (keywords) {
				portletURL.setParameter('keywords', keywords);
			}

			setLoading(true);

			fetch(portletURL.toString())
				.then((response) => response.json())
				.then((json) => {
					if (!json.entries) {
						setFetchData({
							errorMessage: Liferay.Language.get(
								'an-unexpected-error-occurred'
							),
						});

						return;
					}

					setFetchData(json);
					setLoading(false);
				})
				.catch((response) => {
					setFetchData({
						errorMessage: Liferay.Language.get(
							'an-unexpected-error-occurred'
						),
					});
					setLoading(false);
				});
		});
	};

	const renderList = () => {
		if (!fetchData || !fetchData.entries) {
			return '';
		}

		if (fetchData.entries.length === 0) {
			return (
				<div className="taglib-empty-result-message">
					<div className="taglib-empty-result-message-header" />
					<div className="sheet-text text-center">
						{Liferay.Language.get('no-publications-were-found')}
					</div>
				</div>
			);
		}

		const items = [];

		const entries = filterEntries(fetchData.entries);

		for (let i = 0; i < entries.length; i++) {
			const entry = entries[i];

			if (entry.checkoutURL) {
				items.push(
					<ClayList.Item flex>
						<ClayList.ItemField>
							<div
								dangerouslySetInnerHTML={getUserPortraitHTML(
									entry
								)}
								data-tooltip-align="top"
								title={getUserName(entry)}
							/>
						</ClayList.ItemField>
						<ClayList.ItemField>
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
								<ClayList.ItemTitle>
									{entry.name}
								</ClayList.ItemTitle>
								<ClayList.ItemText subtext>
									{entry.description}
								</ClayList.ItemText>
							</a>
						</ClayList.ItemField>
					</ClayList.Item>
				);
			}
			else {
				items.push(
					<ClayList.Item flex>
						<ClayList.ItemField>
							<div
								dangerouslySetInnerHTML={getUserPortraitHTML(
									entry
								)}
								data-tooltip-align="top"
								title={getUserName(entry)}
							/>
						</ClayList.ItemField>
						<ClayList.ItemField
							className="font-italic"
							data-tooltip-align="top"
							title={Liferay.Language.get(
								'already-working-on-this-publication'
							)}
						>
							<ClayList.ItemTitle>
								{entry.name}
							</ClayList.ItemTitle>
							<ClayList.ItemText subtext>
								{entry.description}
							</ClayList.ItemText>
						</ClayList.ItemField>
					</ClayList.Item>
				);
			}
		}

		return (
			<>
				<ClayTooltipProvider>
					<ClayList
						className={loading ? 'select-publications-loading' : ''}
					>
						{items}
					</ClayList>
				</ClayTooltipProvider>

				{fetchData.entries.length > 5 && (
					<ClayPaginationBarWithBasicItems
						activeDelta={delta}
						activePage={page}
						deltas={[4, 8, 20, 40, 60].map((size) => ({
							label: size,
						}))}
						ellipsisBuffer={3}
						onDeltaChange={(delta) => handleDeltaChange(delta)}
						onPageChange={(page) => setPage(page)}
						totalItems={fetchData.entries.length}
					/>
				)}
			</>
		);
	};

	const renderManagementToolbar = () => {
		const items = [
			{
				active: column === SORT_COLUMN_MODIFIED_DATE,
				label: Liferay.Language.get('modified-date'),
				onClick: () => setColumn(SORT_COLUMN_MODIFIED_DATE),
			},
			{
				active: column === SORT_COLUMN_NAME,
				label: Liferay.Language.get('name'),
				onClick: () => setColumn(SORT_COLUMN_NAME),
			},
		];

		items.sort((a, b) => {
			if (a.label < b.label) {
				return -1;
			}

			return 1;
		});

		const toolbarDropdownItems = [
			{
				items,
				label: Liferay.Language.get('order-by'),
				type: 'group',
			},
		];

		return (
			<ClayManagementToolbar>
				<ClayManagementToolbar.ItemList>
					<ClayManagementToolbar.Item>
						<ClayDropDownWithItems
							items={toolbarDropdownItems}
							spritemap={spritemap}
							trigger={
								<ClayButton
									className="nav-link"
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
						className="lfr-portal-tooltip"
						title={Liferay.Language.get('reverse-sort-direction')}
					>
						<ClayButton
							className={
								'nav-link nav-link-monospaced ' +
								(ascending
									? 'order-arrow-down-active'
									: 'order-arrow-up-active')
							}
							displayType="unstyled"
							onClick={() => setAscending(!ascending)}
						>
							<ClayIcon
								spritemap={spritemap}
								symbol="order-arrow"
							/>
						</ClayButton>
					</ClayManagementToolbar.Item>
				</ClayManagementToolbar.ItemList>

				<ClayManagementToolbar.Search
					onSubmit={(event) => {
						event.preventDefault();

						onSubmit(searchTerms.trim());
					}}
					showMobile={showMobile}
				>
					<ClayInput.Group>
						<ClayInput.GroupItem>
							<ClayInput
								aria-label={Liferay.Language.get('search')}
								className="input-group-inset input-group-inset-after"
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
							displayType="unstyled"
							onClick={() => setShowMobile(true)}
						>
							<ClayIcon symbol="search" />
						</ClayButton>
					</ClayManagementToolbar.Item>
				</ClayManagementToolbar.ItemList>
			</ClayManagementToolbar>
		);
	};

	const renderResultsBar = () => {
		if (!fetchData || !fetchData.entries || !resultsKeywords) {
			return '';
		}

		let key = Liferay.Language.get('x-results-for');

		if (fetchData.entries.length === 1) {
			key = Liferay.Language.get('x-result-for');
		}

		return (
			<div className="results-bar">
				<ClayResultsBar>
					<ClayResultsBar.Item expand>
						<span className="component-text text-truncate-inline">
							<span className="text-truncate">
								{format(key, [fetchData.entries.length]) + ' '}

								<strong>{resultsKeywords}</strong>
							</span>
						</span>
					</ClayResultsBar.Item>
					<ClayResultsBar.Item>
						<ClayButton
							className="component-link tbar-link"
							displayType="unstyled"
							onClick={() => {
								onSubmit('');
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
					{renderManagementToolbar()}
					{renderResultsBar()}

					{!fetchData && (
						<span
							aria-hidden="true"
							className="loading-animation"
						/>
					)}

					{fetchData && fetchData.errorMessage && (
						<ClayAlert
							displayType="danger"
							spritemap={spritemap}
							title={Liferay.Language.get('error')}
						>
							{fetchData.errorMessage}
						</ClayAlert>
					)}

					{renderList()}
				</ClayModal.Body>
			</ClayModal>
		);
	};

	return (
		<>
			{renderModal()}

			<ClayDropDownWithItems
				alignmentPosition={Align.BottomCenter}
				items={indicatorDropdownItems}
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

export default function (props) {
	return <ChangeTrackingIndicator {...props} />;
}
