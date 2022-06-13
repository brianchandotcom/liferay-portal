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
import ClayDropDown from '@clayui/drop-down';
import React, {useState} from 'react';

import ClayIconProvider from '../../context/ClayIconProvider';

const {Item, ItemList} = ClayDropDown;

type SettingsButtonProps = {
	actions: ActionObject[];
	identifier: string;
};

export type ActionObject = {
	action: (identifier: string) => void;
	disabled?: (identifier: string) => boolean | boolean;
	value: string;
};

const SettingsButton: React.FC<SettingsButtonProps> = ({
	actions,
	identifier,
}) => {
	const [active, setActive] = useState<boolean>(false);

	const handleDisabled = (action: ActionObject, index: number) => {
		const isDisabled =
			typeof action.disabled === 'function'
				? action.disabled(identifier)
				: action.disabled;

		return (
			<Item
				disabled={isDisabled}
				key={index}
				onClick={() => {
					if (!isDisabled) {
						action.action(identifier);
					}
				}}
			>
				{action.value}
			</Item>
		);
	};

	return (
		<ClayIconProvider>
			<ClayDropDown
				active={active}
				onActiveChange={(newVal: boolean) => setActive(newVal)}
				trigger={
					<ClayButtonWithIcon
						className="btn btn-monospaced btn-sm dropdown-toggle"
						displayType={null}
						id="settingsDropdownMenuToggle"
						symbol="ellipsis-v"
					/>
				}
			>
				<ItemList>
					{actions.map((action, index) =>
						action.disabled ? (
							handleDisabled(action, index)
						) : (
							<Item
								key={index}
								onClick={() => {
									setActive(false);

									action.action(identifier);
								}}
							>
								{action.value}
							</Item>
						)
					)}
				</ItemList>
			</ClayDropDown>
		</ClayIconProvider>
	);
};

export default SettingsButton;
