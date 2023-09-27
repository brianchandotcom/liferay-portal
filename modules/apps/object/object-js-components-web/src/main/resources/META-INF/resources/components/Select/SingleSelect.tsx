/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown from '@clayui/drop-down';
import ClayPopover from '@clayui/popover';
import React, {Children, Fragment, ReactNode, useEffect, useState} from 'react';

import {BaseSelect, CustomItem, SelectProps} from './BaseSelect';

import './index.scss';

interface SingleSelectProps<T> extends SelectProps {
	children?: ReactNode;
	contentRight?: ReactNode;
	onChange?: (selected: T) => void;
	options: T[];
}

export function SingleSelect<
	T extends CustomItem<number | string> = CustomItem
>({
	contentRight,
	children,
	onChange = () => {},
	options,
	...otherProps
}: SingleSelectProps<T>) {
	const [dropdownActive, setDropdownActive] = useState<boolean>(false);
	const arrayChildren = Children.toArray(children);
	const [showPopover, setShowPopover] = useState(false);

	useEffect(() => {
		if (!dropdownActive) {
			setShowPopover(false);
		}
	}, [dropdownActive]);

	return (
		<BaseSelect
			contentRight={contentRight}
			dropdownActive={dropdownActive}
			setDropdownActive={setDropdownActive}
			{...otherProps}
		>
			{options.map((option, index) => {
				let events = {};
				if (option.popover) {
					events = {
						onMouseOut: () => setShowPopover(false),
						onMouseOver: () => setShowPopover(true),
					};
				}

				return (
					<Fragment key={option.name ?? option.value ?? index}>
						<ClayPopover
							alignPosition="right"
							disableScroll={false}
							header={option.popover?.header}
							onShowChange={setShowPopover}
							show={showPopover && !!Object.keys(events).length}
							trigger={
								<ClayDropDown.Item
									{...events}
									active={option.label === otherProps.value}
									className={
										option.type
											? 'lfr-object__single-select--with-label'
											: ''
									}
									disabled={option.disabled}
									key={index}
									onClick={() => {
										setDropdownActive(false);
										onChange(option);
									}}
								>
									<div>{option.label}</div>

									{option.description && (
										<span className="text-small">
											{option.description}
										</span>
									)}

									{arrayChildren?.[index]}
								</ClayDropDown.Item>
							}
						>
							{option.popover?.body}
						</ClayPopover>
					</Fragment>
				);
			})}
		</BaseSelect>
	);
}
