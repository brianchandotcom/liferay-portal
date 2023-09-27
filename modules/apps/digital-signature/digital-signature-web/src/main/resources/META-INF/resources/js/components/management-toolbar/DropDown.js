/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown from '@clayui/drop-down';
import {ClayCheckbox, ClayRadio} from '@clayui/form';
import React from 'react';

export function CheckboxGroup({
	checked = [],
	items = [],
	label,
	onAdd,
	onRemove,
}) {
	return (
		<ClayDropDown.Group header={label}>
			<ClayDropDown.ItemList>
				{items.map(({value, ...item}, index) => (
					<ClayDropDown.Section key={index}>
						<ClayCheckbox
							{...item}
							checked={checked.includes(value)}
							onChange={({target: {checked}}) =>
								checked ? onAdd(value) : onRemove(value)
							}
							value={value}
						/>
					</ClayDropDown.Section>
				))}
			</ClayDropDown.ItemList>
		</ClayDropDown.Group>
	);
}

export function ItemsGroup({checked, items = [], label, onClick}) {
	return (
		<ClayDropDown.Group header={label}>
			<ClayDropDown.ItemList>
				{items.map(({label, value}, index) => (
					<ClayDropDown.Item
						active={checked === value}
						href=""
						key={index}
						onClick={() => onClick(value)}
						value={value}
					>
						{label}
					</ClayDropDown.Item>
				))}
			</ClayDropDown.ItemList>
		</ClayDropDown.Group>
	);
}

export function RadioGroup({checked, items = [], label, onChange}) {
	return (
		<ClayDropDown.Group header={label}>
			<ClayDropDown.ItemList>
				{items.map(({value, ...item}, index) => (
					<ClayDropDown.Section key={index}>
						<ClayRadio
							{...item}
							checked={checked === value}
							onChange={() => onChange(value)}
							value={value}
						/>
					</ClayDropDown.Section>
				))}
			</ClayDropDown.ItemList>
		</ClayDropDown.Group>
	);
}

export default function DropDown({children, footerContent, ...otherProps}) {
	return (
		<ClayDropDown {...otherProps}>
			<ClayDropDown.ItemList className="dropdown-fixed-height inline-scroller">
				{children}
			</ClayDropDown.ItemList>

			{footerContent && (
				<div className="dropdown-footer dropdown-section pt-3">
					{footerContent}
				</div>
			)}
		</ClayDropDown>
	);
}
