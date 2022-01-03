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

import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import React from 'react';

function DDMSelect({
	className,
	disabled,
	label,
	name,
	onChange,
	options,
	value: selectedValue,
}: IProps) {
	let selectedLabel: string | undefined;

	const items: IItem[] = [];

	options.forEach(({label, value}) => {
		items.push({
			label,
			onClick: (event: React.MouseEvent<HTMLElement, MouseEvent>) =>
				onChange?.(event as any),
			value: value as any,
		});

		if (value === selectedValue) {
			selectedLabel = label;
		}
	});

	const select = (
		<div className="form-builder-select-field input-group-container">
			<div className="form-control results-chosen select-field-trigger">
				<div className="option-selected">{selectedLabel}</div>

				<input name={name} type="hidden" value={selectedValue} />

				<a className="select-arrow-down-container">
					<ClayIcon symbol="caret-double" />
				</a>
			</div>
		</div>
	);

	const displaySelect = disabled ? (
		select
	) : (
		<ClayDropDownWithItems
			items={items}
			menuElementAttrs={{className: 'ddm-select-dropdown'}}
			trigger={select}
		/>
	);

	return (
		<label className={className}>
			{label}

			{displaySelect}
		</label>
	);
}

export default DDMSelect;

interface IProps {
	className?: string;
	disabled?: boolean;
	label: string;
	name: string;
	onChange: React.ChangeEventHandler<HTMLInputElement>;
	options: IOption[];
	value: string;
}

interface IOption {
	label: string;
	onClick?: React.ChangeEvent;
	value: string;
}

interface IItem {
	items?: IItem[];
	label?: string;
	onClick?: (event: React.MouseEvent<HTMLElement, MouseEvent>) => void;
	type?: 'group' | 'divider';
	value?: string;
}
