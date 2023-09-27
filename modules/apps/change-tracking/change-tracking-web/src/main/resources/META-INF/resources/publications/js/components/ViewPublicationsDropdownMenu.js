/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {Align, ClayDropDownWithItems} from '@clayui/drop-down';
import React, {useState} from 'react';

import ManageCollaborators from './manage-collaborators-modal/ManageCollaborators';

export default function ViewPublicationsDropdownMenu({
	checkoutURL,
	deleteURL,
	editURL,
	isPublicationTemplate,
	namespace,
	permissionsURL,
	publishURL,
	reviewURL,
	scheduleURL,
	spritemap,
	...props
}) {
	const [showModal, setShowModal] = useState(false);

	if (!namespace) {
		return (
			<ClayDropDownWithItems
				alignmentPosition={Align.BottomLeft}
				items={[
					{
						href: reviewURL,
						label: Liferay.Language.get('review-changes'),
						symbolLeft: 'list-ul',
					},
				]}
				spritemap={spritemap}
				trigger={
					<ClayButtonWithIcon
						displayType="unstyled"
						small
						spritemap={spritemap}
						symbol="ellipsis-v"
					/>
				}
			/>
		);
	}

	const dropdownItems = [];

	if (checkoutURL) {
		dropdownItems.push({
			href: checkoutURL,
			label: Liferay.Language.get('work-on-publication'),
			symbolLeft: 'radio-button',
		});
	}

	if (editURL) {
		dropdownItems.push({
			href: editURL,
			label: Liferay.Language.get('edit'),
			symbolLeft: 'pencil',
		});
	}

	if (!isPublicationTemplate) {
		dropdownItems.push({
			href: reviewURL,
			label: Liferay.Language.get('review-changes'),
			symbolLeft: 'list-ul',
		});
	}

	if (permissionsURL) {
		dropdownItems.push({
			label: Liferay.Language.get('invite-users'),
			onClick: () => setShowModal(true),
			symbolLeft: 'users',
		});

		dropdownItems.push({
			href: permissionsURL,
			label: Liferay.Language.get('permissions'),
			symbolLeft: 'password-policies',
		});
	}

	if (deleteURL) {
		dropdownItems.push(
			{type: 'divider'},
			{
				href: deleteURL,
				label: Liferay.Language.get('delete'),
				symbolLeft: 'times-circle',
			}
		);
	}

	if (publishURL) {
		dropdownItems.push({type: 'divider'});
	}

	if (scheduleURL) {
		dropdownItems.push({
			href: scheduleURL,
			label: Liferay.Language.get('schedule'),
			symbolLeft: 'calendar',
		});
	}

	if (publishURL) {
		dropdownItems.push({
			href: publishURL,
			label: Liferay.Language.get('publish'),
			symbolLeft: 'change',
		});
	}

	const dropDownWithItemsComponent = (
		<ClayDropDownWithItems
			alignmentPosition={Align.BottomLeft}
			items={dropdownItems}
			spritemap={spritemap}
			trigger={
				<ClayButtonWithIcon
					displayType="unstyled"
					small
					spritemap={spritemap}
					symbol="ellipsis-v"
				/>
			}
		/>
	);

	return isPublicationTemplate ? (
		dropDownWithItemsComponent
	) : (
		<ManageCollaborators
			namespace={namespace}
			setShowModal={setShowModal}
			showModal={showModal}
			trigger={dropDownWithItemsComponent}
			{...props}
		/>
	);
}
