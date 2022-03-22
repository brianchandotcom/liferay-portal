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

import ClayButton from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayManagementToolbar from '@clayui/management-toolbar';
import {useState} from 'react';

import {Sort} from '../../context/ListViewContext';
import {SortOption} from '../../types';

export type IItem = {
	active?: boolean;
	checked?: boolean;
	disabled?: boolean;
	href?: string;
	items?: IItem[];
	label?: string;
	name?: string;
	onChange?: Function;
	onClick?: (event: React.MouseEvent<HTMLElement, MouseEvent>) => void;
	symbolLeft?: string;
	symbolRight?: string;
	type?:
		| 'checkbox'
		| 'contextual'
		| 'group'
		| 'item'
		| 'radio'
		| 'radiogroup'
		| 'divider';
	value?: string;
};

type ManagementToolbarFilterAndOrderProps = {
	disabled: boolean;
	filterItems: IItem[];
	onSelectAllRows: () => void;
	onSort: () => void;
	sort: Sort;
};

const ManagementToolbarFilterAndOrder: React.FC<ManagementToolbarFilterAndOrderProps> = ({
	disabled,
	filterItems,
	onSelectAllRows,
	onSort,
	sort,
}) => {
	const [checked, setChecked] = useState(false);

	return (
		<ClayManagementToolbar.ItemList>
			<ClayManagementToolbar.Item>
				<ClayCheckbox
					checked={checked}
					disabled={disabled}
					onChange={() => {
						onSelectAllRows();
						setChecked(!checked);
					}}
				/>
			</ClayManagementToolbar.Item>

			<ClayDropDownWithItems
				items={filterItems}
				trigger={
					<ClayButton
						className="nav-link"
						disabled={disabled}
						displayType="unstyled"
					>
						<span className="navbar-breakpoint-down-d-none">
							<span className="navbar-text-truncate">
								Filter and Order
							</span>

							<ClayIcon
								className="inline-item inline-item-after"
								symbol="caret-bottom"
							/>
						</span>

						<span className="navbar-breakpoint-d-none">
							<ClayIcon symbol="filter" />
						</span>
					</ClayButton>
				}
			/>

			<ClayManagementToolbar.Item>
				<ClayButton
					className="nav-link nav-link-monospaced"
					disabled={disabled}
					displayType="unstyled"
					onClick={onSort}
				>
					<ClayIcon
						symbol={
							sort.direction === SortOption.ASC
								? 'order-list-up'
								: 'order-list-down'
						}
					/>
				</ClayButton>
			</ClayManagementToolbar.Item>
		</ClayManagementToolbar.ItemList>
	);
};

export default ManagementToolbarFilterAndOrder;
