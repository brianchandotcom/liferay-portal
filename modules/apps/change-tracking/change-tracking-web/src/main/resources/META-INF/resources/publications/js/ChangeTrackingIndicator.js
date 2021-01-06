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

import {Align, ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import ClayModal, {useModal} from '@clayui/modal';
import React, {useState} from 'react';

import PublicationsSearchContainer from './PublicationsSearchContainer';

const ChangeTrackingIndicator = ({
	dropdownItems,
	getSelectPublicationsURL,
	iconClass,
	iconName,
	spritemap,
	title,
}) => {
	const SORT_COLUMN_MODIFIED_DATE = 'SORT_COLUMN_MODIFIED_DATE';
	const SORT_COLUMN_NAME = 'SORT_COLUMN_NAME';

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

	const filterEntries = (ascending, column, delta, entries, page) => {
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

		if (entries.length > 5) {
			return filteredEntries.slice(delta * (page - 1), delta * page);
		}

		return filteredEntries;
	};

	const getUserName = (entry, fetchData) => {
		return fetchData.userInfo[entry.userId].userName;
	};

	const getUserPortraitHTML = (entry, fetchData) => {
		return {__html: fetchData.userInfo[entry.userId].userPortraitHTML};
	};

	const getListItem = (entry, fetchData) => {
		if (entry.checkoutURL) {
			return (
				<ClayList.Item flex>
					<ClayList.ItemField>
						<div
							dangerouslySetInnerHTML={getUserPortraitHTML(
								entry,
								fetchData
							)}
							data-tooltip-align="top"
							title={getUserName(entry, fetchData)}
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
			return (
				<ClayList.Item flex>
					<ClayList.ItemField>
						<div
							dangerouslySetInnerHTML={getUserPortraitHTML(
								entry,
								fetchData
							)}
							data-tooltip-align="top"
							title={getUserName(entry, fetchData)}
						/>
					</ClayList.ItemField>
					<ClayList.ItemField
						className="font-italic"
						data-tooltip-align="top"
						title={Liferay.Language.get(
							'already-working-on-this-publication'
						)}
					>
						<ClayList.ItemTitle>{entry.name}</ClayList.ItemTitle>
						<ClayList.ItemText subtext>
							{entry.description}
						</ClayList.ItemText>
					</ClayList.ItemField>
				</ClayList.Item>
			);
		}
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
						defaultColumn={SORT_COLUMN_MODIFIED_DATE}
						fetchDataURL={getSelectPublicationsURL}
						filterEntries={filterEntries}
						getListItem={getListItem}
						orderByItems={[
							{
								label: Liferay.Language.get('modified-date'),
								value: SORT_COLUMN_MODIFIED_DATE,
							},
							{
								label: Liferay.Language.get('name'),
								value: SORT_COLUMN_NAME,
							},
						]}
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
