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

import {ClayButtonWithIcon} from '@clayui/button';
import {Align, ClayDropDownWithItems} from '@clayui/drop-down';
import React, {useState} from 'react';

import ManageCollaborators from './ManageCollaborators';

export default ({
	checkoutURL,
	deleteURL,
	editURL,
	namespace,
	permissionsURL,
	publishURL,
	reviewURL,
	scheduleURL,
	spritemap,
	...props
}) => {
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

	dropdownItems.push({
		href: reviewURL,
		label: Liferay.Language.get('review-changes'),
		symbolLeft: 'list-ul',
	});

	const [showModal, setShowModal] = useState(false);

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

	return (
		<ManageCollaborators
			namespace={namespace}
			setShowModal={setShowModal}
			showModal={showModal}
			trigger={
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
			}
			{...props}
		/>
	);
};
