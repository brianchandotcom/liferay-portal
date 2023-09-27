/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
						aria-label="More actions"
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
