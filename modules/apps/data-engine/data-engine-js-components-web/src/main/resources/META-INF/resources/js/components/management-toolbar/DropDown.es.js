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
import {ClayCheckbox, ClayRadio} from '@clayui/form';
import React from 'react';

export const CheckboxGroup = ({
	checked = [],
	items = [],
	label,
	onAdd,
	onRemove,
}) => {
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
};

export const ItemsGroup = ({checked, items = [], label, onClick}) => {
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
};

export const RadioGroup = ({checked, items = [], label, onChange}) => {
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
};

export default ({children, footerContent, ...otherProps}) => {
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
};
