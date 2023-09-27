/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
