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

import ClayDropDown from '@clayui/drop-down';
import ClayPopover from '@clayui/popover';
import React, {Children, Fragment, ReactNode, useEffect, useState} from 'react';

import {BaseSelect, CustomItem, SelectProps} from './BaseSelect';

import './index.scss';

interface IProps<T extends CustomItem<number | string> = CustomItem>
	extends SelectProps {
	children?: ReactNode;
	onChange?: (selected: T) => void;
	options: T[];
}

export function SingleSelect<
	T extends CustomItem<number | string> = CustomItem
>({onChange = () => {}, options, children, ...otherProps}: IProps<T>) {
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
