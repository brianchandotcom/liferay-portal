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

import {ClayCheckbox} from '@clayui/form';
import ClayMultiSelect from '@clayui/multi-select';
import React from 'react';

type TItems = Exclude<
	React.ComponentProps<typeof ClayMultiSelect>['items'],
	undefined
>;

type TItem = TItems[0];

interface IProps extends React.ComponentProps<typeof ClayMultiSelect> {
	items: TItems;
	onItemsChange: Exclude<
		React.ComponentProps<typeof ClayMultiSelect>['onItemsChange'],
		undefined
	>;
	sourceItems: Exclude<
		React.ComponentProps<typeof ClayMultiSelect>['sourceItems'],
		undefined | null
	>;
	value: Exclude<
		React.ComponentProps<typeof ClayMultiSelect>['value'],
		undefined
	>;
}

const isChecked = (items: TItems, item: TItem) => {
	return !!items.find((val) => val.value === item.value);
};

function CheckboxMultiSelect({
	items,
	onItemsChange,
	sourceItems,
	value,
	...otherProps
}: IProps) {
	const toggleItemChecked = (item: TItem) => {
		if (!isChecked(items, item)) {
			onItemsChange([
				...items,
				sourceItems.find(
					(entry) => item.value === entry.value
				) as TItems[0],
			]);
		}
		else {
			onItemsChange(items.filter((entry) => item.value !== entry.value));
		}
	};

	return (
		<ClayMultiSelect
			allowsCustomLabel={false}
			items={items}
			onItemsChange={onItemsChange}
			sourceItems={sourceItems}
			value={value}
			{...otherProps}
		>
			{(item: any) => (
				<ClayMultiSelect.Item
					key={item.value}
					onClick={(event) => {
						event.preventDefault();

						toggleItemChecked(item);
					}}
					textValue={item.label}
				>
					<div className="autofit-row autofit-row-center">
						<div className="autofit-col mr-3">
							<ClayCheckbox
								checked={isChecked(items, item)}
								onClick={(event: any) => {
									event.stopPropagation();

									toggleItemChecked(item);
								}}
							/>
						</div>

						<div className="autofit-col">
							<span>{item.label}</span>
						</div>
					</div>
				</ClayMultiSelect.Item>
			)}
		</ClayMultiSelect>
	);
}

export default CheckboxMultiSelect;
