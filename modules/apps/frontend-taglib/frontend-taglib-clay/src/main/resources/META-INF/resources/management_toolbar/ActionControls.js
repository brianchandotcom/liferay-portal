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
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayManagementToolbar from '@clayui/management-toolbar';
import React from 'react';

import normalizeDropdownItems from '../normalize_dropdown_items';
import LinkOrButton from './LinkOrButton';

function addAction(item, onActionButtonClick) {
	if (item.type === 'group') {
		return {
			...item,
			items: item.items?.map((child) =>
				addAction(child, onActionButtonClick)
			),
		};
	}

	const clone = {
		onClick: (event) => {
			onActionButtonClick(event, {item});
		},
		...item,
	};

	delete clone.quickAction;

	return clone;
}

const ActionControls = ({
	actionDropdownItems,
	disabled,
	onActionButtonClick,
}) => {
	return (
		<>
			{actionDropdownItems && (
				<>
					{actionDropdownItems
						.flatMap((item) =>
							item.type === 'group' ? item.items : [item]
						)
						.filter((item) => item.quickAction && item.icon)
						.map((item, index) => (
							<ClayManagementToolbar.Item
								className="navbar-breakpoint-down-d-none"
								key={index}
							>
								<LinkOrButton
									className="nav-link nav-link-monospaced"
									disabled={disabled || item.disabled}
									displayType="unstyled"
									href={item.href}
									onClick={(event) => {
										onActionButtonClick(event, {
											item,
										});
									}}
									symbol={item.icon}
									title={item.label}
								/>
							</ClayManagementToolbar.Item>
						))}

					<ClayManagementToolbar.Item>
						<ClayDropDownWithItems
							items={normalizeDropdownItems(
								actionDropdownItems?.map((item) =>
									addAction(item, onActionButtonClick)
								)
							)}
							trigger={
								<ClayButtonWithIcon
									className="nav-link nav-link-monospaced"
									disabled={disabled}
									displayType="unstyled"
									symbol="ellipsis-v"
								/>
							}
						/>
					</ClayManagementToolbar.Item>
				</>
			)}
		</>
	);
};

export default ActionControls;
