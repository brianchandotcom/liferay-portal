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
import ClayPopover from '@clayui/popover';
import ClaySticker from '@clayui/sticker';
import {
	createPortletURL,
	fetch,
	navigate as navigateUtil,
	openConfirmModal,
} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import PublicationTimeline from './PublicationTimeline';
import PublicationsSearchContainer from './PublicationsSearchContainer';

export default function ChangeTrackingIndicator({
	checkoutDropdownItem,
	contextChangeButtons,
	createDropdownItem,
	getConflictInfoURL,
	getSelectPublicationsURL,
	iconClass,
	iconName,
	namespace,
	orderByAscending,
	orderByColumn,
	preferencesPrefix,
	previewProductionDropdownItem,
	returnToPublicationDropdownItem,
	reviewDropdownItem,
	saveDisplayPreferenceURL,
	spritemap,
	timelineClassNameId,
	timelineClassPK,
	timelineEditURL,
	timelineIconClass,
	timelineIconName,
	timelineItemsURL,
	title,
	warningBody,
	warningButton,
	warningHeader,
	warningLearnLink,
}) {
	const COLUMN_MODIFIED_DATE = 'modifiedDate';
	const COLUMN_NAME = 'name';

	const [ascending, setAscending] = useState(orderByAscending === 'true');
	const [closeWarning, setCloseWarning] = useState(false);
	const [column, setColumn] = useState(
		orderByColumn === COLUMN_NAME ? COLUMN_NAME : COLUMN_MODIFIED_DATE
	);
	const [showModal, setShowModal] = useState(false);
	const [showWarning, setShowWarning] = useState(
		warningBody || warningHeader
	);

	const navigate = (url, action) => {
		const portletURL = createPortletURL(url, {
			redirect: window.location.pathname + window.location.search,
		});

		if (action) {
			submitForm(document.hrefFm, portletURL.toString());

			return;
		}

		navigateUtil(portletURL);
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

	if (previewProductionDropdownItem) {
		dropdownItems.push(previewProductionDropdownItem);
	}

	if (returnToPublicationDropdownItem) {
		dropdownItems.push(returnToPublicationDropdownItem);
	}
	else {
		dropdownItems.push({
			label: Liferay.Language.get('select-a-publication'),
			onClick: () => setShowModal(true),
			symbolLeft: 'cards2',
		});
	}

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
		const sortedEntries = entries.slice(0).sort((a, b) => {
			if (column === COLUMN_MODIFIED_DATE) {
				if (a.modifiedDate < b.modifiedDate) {
					return ascending ? -1 : 1;
				}
				else if (a.modifiedDate > b.modifiedDate) {
					return ascending ? 1 : -1;
				}
			}
			else if (column === COLUMN_NAME) {
				const nameA = a.name.toLowerCase();
				const nameB = b.name.toLowerCase();
				if (nameA < nameB) {
					return ascending ? -1 : 1;
				}
				else if (nameA > nameB) {
					return ascending ? 1 : -1;
				}
			}

			return 0;
		});

		return entries.length > 5
			? sortedEntries.slice(delta * (page - 1), delta * page)
			: sortedEntries;
	};

	const renderUserPortrait = (entry, userInfo) => {
		const user = userInfo[entry.userId];

		if (!user) {
			return <ClaySticker />;
		}

		return (
			<ClaySticker
				className={`sticker-user-icon ${
					user.portraitURL
						? ''
						: `user-icon-color-${entry.userId % 10}`
				}`}
				data-tooltip-align="top"
				title={user.userName}
			>
				{user.portraitURL ? (
					<div className="sticker-overlay">
						<img
							alt=""
							className="sticker-img"
							src={user.portraitURL}
						/>
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
										aria-label="actions"
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
						namespace={namespace}
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

	const [fetchData, setFetchData] = useState(null);
	const [conflictIconClass, setConflictIconClass] = useState(null);
	const [conflictIconLabel, setConflictIconLabel] = useState(null);
	const [conflictIconName, setConflictIconName] = useState(null);

	useEffect(() => {
		if (getConflictInfoURL) {
			fetch(createPortletURL(getConflictInfoURL))
				.then((response) => response.json())
				.then((json) => {
					if (json) {
						setConflictIconClass(json.conflictIconClass);
						setConflictIconLabel(json.conflictIconLabel);
						setConflictIconName(json.conflictIconName);
					}
				})
				.catch(() => {
					setFetchData({
						errorMessage: Liferay.Language.get(
							'an-unexpected-error-occurred'
						),
					});
				});
		}
	}, [getConflictInfoURL]);

	const renderConflictIcon = () => {
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

	const renderDropdown = () => {
		return (
			<ClayDropDownWithItems
				alignmentPosition={Align.BottomCenter}
				items={dropdownItems}
				menuElementAttrs={{style: {zIndex: 1021}}}
				trigger={renderTrigger}
			/>
		);
	};

	const renderWarning = () => {
		return (
			<ClayPopover
				alignPosition="bottom"
				closeOnClickOutside={true}
				disableScroll={true}
				header={
					<ClayLayout.ContentRow verticalAlign="center">
						<ClayLayout.ContentCol expand>
							{warningHeader}
						</ClayLayout.ContentCol>

						<ClayLayout.ContentCol>
							<ClayButtonWithIcon
								aria-label={Liferay.Language.get('close')}
								displayType="unstyled"
								onClick={() => {
									setShowWarning(false);
								}}
								size="xs"
								symbol="times"
								title={Liferay.Language.get('close')}
							/>
						</ClayLayout.ContentCol>
					</ClayLayout.ContentRow>
				}
				onShowChange={setShowWarning}
				show={showWarning}
				style={{maxWidth: contextChangeButtons ? '711px' : '421px'}}
				trigger={renderTrigger}
			>
				<ClayLayout.ContainerFluid>
					<ClayLayout.Row style={{paddingBottom: '20px'}}>
						<ClayLayout.Col>
							<span>{warningBody}</span>

							{warningLearnLink && (
								<a href={warningLearnLink}>Learn More</a>
							)}
						</ClayLayout.Col>
					</ClayLayout.Row>

					<ClayLayout.Row>
						{contextChangeButtons && (
							<>
								<ClayLayout.Col>
									<ClayButton
										displayType="secondary"
										onClick={() => {
											setCloseWarning(true);
										}}
										size="sm"
										style={{
											whiteSpace: 'nowrap',
											width: 'auto',
										}}
									>
										{Liferay.Language.get(
											'stay-in-current-publication'
										)}
									</ClayButton>
								</ClayLayout.Col>

								<ClayLayout.Col>
									<ClayButton
										displayType="secondary"
										onClick={() => {
											setShowModal(true);
											setCloseWarning(true);
										}}
										size="sm"
										style={{
											whiteSpace: 'nowrap',
											width: 'auto',
										}}
									>
										{Liferay.Language.get(
											'select-a-publication'
										)}
									</ClayButton>
								</ClayLayout.Col>
							</>
						)}

						<ClayLayout.Col>
							{warningButton && checkoutDropdownItem && (
								<ClayButton
									displayType="secondary"
									onClick={() => {
										if (
											!checkoutDropdownItem.confirmationMessage
										) {
											navigate(
												checkoutDropdownItem.href,
												true
											);
										}
										else {
											openConfirmModal({
												message:
													checkoutDropdownItem.confirmationMessage,
												onConfirm: (isConfirmed) => {
													if (isConfirmed) {
														navigate(
															checkoutDropdownItem.href,
															true
														);
													}
												},
											});
										}
									}}
									size={contextChangeButtons ? 'sm' : 'xs'}
								>
									{Liferay.Language.get('work-on-production')}
								</ClayButton>
							)}
						</ClayLayout.Col>
					</ClayLayout.Row>
				</ClayLayout.ContainerFluid>
			</ClayPopover>
		);
	};

	const renderTrigger = (
		<button className="change-tracking-indicator-button">
			<ClayIcon className={iconClass} symbol={iconName} />

			<span className="change-tracking-indicator-title">{title}</span>

			<ClayIcon symbol="caret-bottom" />
		</button>
	);

	const renderTimeline = () => {
		if (timelineItemsURL !== null) {
			return (
				<ClayDropDown
					alignmentPosition={Align.BottomCenter}
					renderMenuOnClick
					trigger={
						<ClayButton
							aria-controls="publication-timeline-dropdown"
							aria-label="timeline-button"
							className="change-tracking-timeline-button"
						>
							<ClayIcon
								className={timelineIconClass}
								symbol={timelineIconName}
							/>
						</ClayButton>
					}
				>
					<PublicationTimeline
						namespace={namespace}
						navigate={navigate}
						spritemap={spritemap}
						timelineClassNameId={timelineClassNameId}
						timelineClassPK={timelineClassPK}
						timelineEditURL={timelineEditURL}
						timelineItemsURL={timelineItemsURL}
					/>
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
						{renderConflictIcon()}
					</div>
				</ClayLayout.ContentCol>

				<ClayLayout.ContentCol>
					{showWarning && !closeWarning
						? renderWarning()
						: renderDropdown()}
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
						{renderTimeline()}
					</div>
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>
		</>
	);
}
