/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';
import ClayMultiSelect from '@clayui/multi-select';
import React from 'react';

type TItem = {
	key?: string;
	label?: string;
	value?: string;
	[propName: string]: any;
};

type TItems = Array<TItem>;

interface IProps extends React.ComponentProps<typeof ClayMultiSelect> {
	items: TItems;
	onItemsChange: Exclude<
		React.ComponentProps<typeof ClayMultiSelect>['onItemsChange'],
		undefined
	>;
	sourceItems: TItems;
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
