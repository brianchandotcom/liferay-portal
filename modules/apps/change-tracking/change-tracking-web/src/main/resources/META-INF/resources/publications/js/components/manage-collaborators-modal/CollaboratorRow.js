/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';
import ClayTable from '@clayui/table';
import React, {useState} from 'react';

const CollaboratorRow = ({
	handleSelect,
	readOnly,
	roles,
	selectedItems,
	spritemap,
	updatedRoles,
	user,
}) => {
	const [active, setActive] = useState(false);

	let activeRole = roles[0];
	let changed = false;
	let className = '';

	if (user.new) {
		activeRole = selectedItems[user.userId.toString()];
		className = 'table-add';
	}
	else if (
		Object.prototype.hasOwnProperty.call(
			updatedRoles,
			user.userId.toString()
		)
	) {
		changed = true;

		activeRole = updatedRoles[user.userId.toString()];

		if (updatedRoles[user.userId.toString()].value === -1) {
			className = 'table-delete';
		}
		else if (
			updatedRoles[user.userId.toString()].value !== user.roleValue
		) {
			className = 'table-active';
		}
	}
	else {
		for (let i = 0; i < roles.length; i++) {
			if (roles[i].value === Number.parseInt(user.roleValue, 10)) {
				activeRole = roles[i];

				break;
			}
		}
	}

	const dropdownItems = [];

	for (let i = 0; i < roles.length; i++) {
		dropdownItems.push({
			className:
				activeRole.value !== roles[i].value &&
				user.roleValue === roles[i].value
					? 'font-italic'
					: '',
			description: roles[i].shortDescription,
			label: roles[i].label,
			onClick: () => {
				setActive(false);
				handleSelect(roles[i]);
			},
			symbolLeft: activeRole.value === roles[i].value ? 'check' : '',
		});
	}

	dropdownItems.push(
		{
			type: 'divider',
		},
		{
			label: Liferay.Language.get('remove'),
			onClick: () => {
				setActive(false);
				handleSelect({
					label: Liferay.Language.get('remove'),
					value: -1,
				});
			},
			symbolLeft: activeRole.value === -1 ? 'check' : '',
		}
	);

	let title = null;

	if (user.isOwner) {
		title = Liferay.Language.get(
			'owners-can-view,-edit,-publish,-and-invite-other-users'
		);
	}
	else if (activeRole.longDescription) {
		title = activeRole.longDescription;
	}

	let label = activeRole.label;

	if (user.isOwner) {
		label = Liferay.Language.get('owner');
	}
	else if (user.new) {
		label = Liferay.Language.get('add') + ' (' + label + ')';
	}
	else if (
		changed &&
		Object.prototype.hasOwnProperty.call(user, 'roleValue')
	) {
		label = label + ' (' + user.roleLabel + ')';
	}

	return (
		<ClayTable.Row className={className} key={user.userId}>
			<ClayTable.Cell>
				<ClaySticker
					className={`sticker-user-icon ${
						user.portraitURL
							? ''
							: 'user-icon-color-' + (user.userId % 10)
					}`}
					size="lg"
				>
					{user.portraitURL ? (
						<div className="sticker-overlay">
							<img
								className="sticker-img"
								src={user.portraitURL}
							/>
						</div>
					) : (
						<ClayIcon symbol="user" />
					)}
				</ClaySticker>
			</ClayTable.Cell>

			<ClayTable.Cell className="table-cell-expand">
				{user.isCurrentUser
					? user.fullName + ' (' + Liferay.Language.get('you') + ')'
					: user.fullName}
			</ClayTable.Cell>

			<ClayTable.Cell className="table-cell-expand">
				{user.emailAddress}
			</ClayTable.Cell>

			<ClayTable.Cell className="table-column-text-end">
				{readOnly ? (
					<div
						className="role-read-only"
						data-tooltip-align="top"
						title={title}
					>
						{label}

						<ClayIcon
							spritemap={spritemap}
							symbol="exclamation-circle"
						/>
					</div>
				) : (
					<ClayDropDown
						active={active}
						alignmentPosition={Align.BottomLeft}
						hasLeftSymbols={true}
						menuWidth="sm"
						onActiveChange={setActive}
						spritemap={spritemap}
						trigger={
							<ClayButton
								borderless
								data-tooltip-align="top"
								disabled={user.isCurrent || user.isOwner}
								displayType="secondary"
								small
								title={title}
							>
								{label}

								<span className="inline-item inline-item-after">
									<ClayIcon
										spritemap={spritemap}
										symbol="caret-bottom"
									/>
								</span>
							</ClayButton>
						}
					>
						<ClayDropDown.ItemList>
							<ClayDropDown.Group>
								{dropdownItems.map((item, i) => {
									if (item.type === 'divider') {
										return <ClayDropDown.Divider key={i} />;
									}

									return (
										<ClayDropDown.Item
											className={item.className}
											key={i}
											onClick={item.onClick}
											symbolLeft={item.symbolLeft}
										>
											<strong>{item.label}</strong>

											<div>{item.description}</div>
										</ClayDropDown.Item>
									);
								})}
							</ClayDropDown.Group>
						</ClayDropDown.ItemList>
					</ClayDropDown>
				)}
			</ClayTable.Cell>
		</ClayTable.Row>
	);
};

export default CollaboratorRow;
