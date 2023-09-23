/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown, {Align, ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayList from '@clayui/list';
import ClayModal, {useModal} from '@clayui/modal';
import ClaySticker from '@clayui/sticker';
import {navigate as navigateUtil, openConfirmModal} from 'frontend-js-web';
import React, {useState} from 'react';

import PublicationTimeline from './PublicationTimeline';
import PublicationsSearchContainer from './PublicationsSearchContainer';

export default function ChangeTrackingIndicator({
	checkoutDropdownItem,
	conflictIconClass,
	conflictIconLabel,
	conflictIconName,
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
	timelineIconClass,
	timelineIconName,
	timelineItems,
	title,
}) {
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

	const navigate = (url, action) => {
		AUI().use('liferay-portlet-url', () => {
			const portletURL = Liferay.PortletURL.createURL(url);

			portletURL.setParameter(
				'redirect',
				window.location.pathname + window.location.search
			);

			if (action) {
				submitForm(document.hrefFm, portletURL.toString());

				return;
			}

			navigateUtil(portletURL.toString());
		});
	};

	const dropdownItems = [];

	if (checkoutDropdownItem) {
		dropdownItems.push({
			label: checkoutDropdownItem.label,
			onClick: () => {
				if (!checkoutDropdownItem.confirmationMessage) {
					navigate(checkoutDropdownItem.href, true);
				}
				else {
					openConfirmModal({
						message: checkoutDropdownItem.confirmationMessage,
						onConfirm: (isConfirmed) => {
							if (isConfirmed) {
								navigate(checkoutDropdownItem.href, true);
							}
						},
					});
				}
			},
			symbolLeft: checkoutDropdownItem.symbolLeft,
		});
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

				{!!entry.description && (
					<ClayList.ItemText subtext>
						{entry.description}
					</ClayList.ItemText>
				)}
			</ClayList.ItemField>
		);

		if (entry.checkoutURL) {
			dropdownItems.push({
				label: Liferay.Language.get('work-on-publication'),
				onClick: () => navigate(entry.checkoutURL, true),
				symbolLeft: 'radio-button',
			});

			itemField = (
				<ClayList.ItemField expand>
					<a onClick={() => navigate(entry.checkoutURL, true)}>
						<ClayList.ItemTitle>{entry.name}</ClayList.ItemTitle>

						{!!entry.description && (
							<ClayList.ItemText subtext>
								{entry.description}
							</ClayList.ItemText>
						)}
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

						{!!entry.description && (
							<ClayList.ItemText subtext>
								{entry.description}
							</ClayList.ItemText>
						)}
					</ClayButton>
				</ClayList.ItemField>
			);
		}

		dropdownItems.push({
			label: Liferay.Language.get('review-changes'),
			onClick: () => navigate(entry.viewURL),
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
										onClick={() =>
											navigate(entry.checkoutURL, true)
										}
										spritemap={spritemap}
										symbol="radio-button"
										title={Liferay.Language.get(
											'work-on-publication'
										)}
									/>
								)}

								<ClayList.QuickActionMenu.Item
									data-tooltip-align="top"
									onClick={() => navigate(entry.viewURL)}
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

	const renderConflictIcon = (conflictIconClass, conflictIconName) => {
		if (conflictIconClass && conflictIconName) {
			return (
				<ClayIcon
					className={conflictIconClass}
					style={{fontSize: 'medium'}}
					symbol={conflictIconName}
				/>
			);
		}
	};

	const renderTimeline = (timelineItems) => {
		if (timelineItems) {
			return (
				<ClayDropDown
					alignmentPosition={Align.BottomCenter}
					trigger={
						<ClayButton
							aria-controls="publication-timeline-dropdown"
							className="change-tracking-timeline-button"
						>
							<ClayIcon
								className={timelineIconClass}
								symbol={timelineIconName}
							/>
						</ClayButton>
					}
				>
					<PublicationTimeline timelineItems={timelineItems} />
				</ClayDropDown>
			);
		}
	};

	return (
		<>
			{renderModal()}

			<ClayLayout.ContentRow style={{justifyContent: 'center'}}>
				<ClayLayout.ContentCol>
					<div
						className="c-inner"
						style={{
							margin: '2px',
							padding: '1px',
							width: '16px',
						}}
						tabIndex="-1"
						title={conflictIconLabel}
					>
						{renderConflictIcon(
							conflictIconClass,
							conflictIconName
						)}
					</div>
				</ClayLayout.ContentCol>

				<ClayLayout.ContentCol>
					<ClayDropDownWithItems
						alignmentPosition={Align.BottomCenter}
						items={dropdownItems}
						trigger={
							<button className="change-tracking-indicator-button">
								<ClayIcon
									className={iconClass}
									symbol={iconName}
								/>

								<span className="change-tracking-indicator-title">
									{title}
								</span>

								<ClayIcon symbol="caret-bottom" />
							</button>
						}
					/>
				</ClayLayout.ContentCol>

				<ClayLayout.ContentCol>
					<div
						className="c-inner"
						style={{
							padding: '1px',
							width: '21px',
						}}
						tabIndex="-1"
						title="Timeline"
					>
						{renderTimeline(timelineItems)}
					</div>
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>
		</>
	);
}
