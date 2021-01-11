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
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import ClayManagementToolbar from '@clayui/management-toolbar';
import React from 'react';

import getDataAttributes from '../get_data_attributes';

const ActionControls = ({
	actionDropdownItems,
	disabled,
	onActionButtonClick,
}) => {
	return (
		<>
			{actionDropdownItems && (
				<ClayManagementToolbar.ItemList>
					{actionDropdownItems
						.filter((item) => item.quickAction && item.icon)
						.map((item, index) => (
							<ClayManagementToolbar.Item
								className="navbar-breakpoint-down-d-none"
								key={index}
							>
								{item.href ? (
									<ClayLink
										className="nav-link nav-link-monospaced"
										displayType="unstyled"
										href={item.href}
										title={item.label}
									>
										<ClayIcon symbol={item.icon} />
									</ClayLink>
								) : (
									<ClayButtonWithIcon
										className="nav-link nav-link-monospaced"
										disabled={disabled}
										displayType="unstyled"
										onClick={(event) => {
											onActionButtonClick(event, {item});
										}}
										symbol={item.icon}
										title={item.label}
									/>
								)}
							</ClayManagementToolbar.Item>
						))}

					<ClayManagementToolbar.Item>
						<ClayDropDownWithItems
							items={actionDropdownItems?.map((item) => {
								const {data, ...rest} = item;

								const dataAttributes = getDataAttributes(data);

								const clone = {
									onClick: (event) => {
										onActionButtonClick(event, {item});
									},
									...dataAttributes,
									...rest,
								};

								delete clone.quickAction;

								return clone;
							})}
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
				</ClayManagementToolbar.ItemList>
			)}
		</>
	);
};

export default ActionControls;
