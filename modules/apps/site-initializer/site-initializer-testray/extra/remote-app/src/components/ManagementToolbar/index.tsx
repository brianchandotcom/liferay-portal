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

import ClayManagementToolbar from '@clayui/management-toolbar';
import {useContext, useState} from 'react';

import {ListViewContext, ListViewTypes} from '../../context/ListViewContext';
import i18n from '../../i18n';
import {SortOption} from '../../types';
import {TableProps} from '../Table';
import ManagementToolbarFilterAndOrder, {
	IItem,
} from './ManagementToolbarFilterAndOrder';
import ManagementToolbarResultsBar from './ManagementToolbarResultsBar';
import ManagementToolbarRight from './ManagementToolbarRight';
import ManagementToolbarSearch from './ManagementToolbarSearch';

export type ManagementToolbarProps = {
	addButton?: () => void;
	onSelectAllRows: () => void;
	tableProps: Omit<TableProps, 'items'>;
	totalItems: number;
};

const ManagementToolbar: React.FC<ManagementToolbarProps> = ({
	addButton,
	onSelectAllRows,
	tableProps,
	totalItems,
}) => {
	const [{filters, keywords, sort, viewType}, dispatch] = useContext(
		ListViewContext
	);
	const [showMobile, setShowMobile] = useState(false);

	const viewTypes = [
		{
			active: viewType === 'table',
			label: 'Table',
			onClick: () =>
				dispatch({payload: 'table', type: ListViewTypes.SET_VIEW_TYPE}),
			symbolLeft: 'table',
		},
		{
			active: viewType === 'list',
			label: 'List',
			onClick: () =>
				dispatch({payload: 'list', type: ListViewTypes.SET_VIEW_TYPE}),
			symbolLeft: 'list',
		},
		{
			active: viewType === 'cards',
			label: 'Card',
			onClick: () =>
				dispatch({payload: 'cards', type: ListViewTypes.SET_VIEW_TYPE}),
			symbolLeft: 'cards2',
		},
	];

	const sorteableColumns = tableProps.columns.filter(
		({sorteable}) => sorteable
	);

	let filterItems: IItem[] = [
		{
			items: tableProps.columns.map((column) => ({
				checked: (filters.columns || {})[column.key] ?? true,
				label: column.value,
				onChange: (value: boolean) => {
					dispatch({
						payload: {
							filters: {
								...filters,
								columns: {
									...filters.columns,
									[column.key]: value,
								},
							},
						},
						type: ListViewTypes.SET_UPDATE_FILTERS_AND_SORT,
					});
				},
				type: 'checkbox',
			})),
			label: i18n.translate('columns'),
			type: 'group',
		},
	];

	if (sorteableColumns.length) {
		filterItems = [
			...filterItems,
			{
				items: sorteableColumns.map((column) => ({
					active:
						column.key === sort.key ||
						sorteableColumns.length === 1,
					label: column.value,
					onClick: () =>
						dispatch({
							payload: {
								direction: sort.direction,
								key: column.key,
							},
							type: ListViewTypes.SET_SORT,
						}),
					type: 'item',
				})),
				label: i18n.translate('order-by'),
				type: 'group',
			},
		];
	}

	const onSearch = (searchText: string) => {
		dispatch({payload: searchText, type: ListViewTypes.SET_SEARCH});
	};

	return (
		<>
			<ClayManagementToolbar>
				<ManagementToolbarFilterAndOrder
					filterItems={filterItems}
					onSelectAllRows={onSelectAllRows}
					onSort={() =>
						dispatch({
							payload: {
								...sort,
								direction:
									sort.direction === SortOption.ASC
										? SortOption.DESC
										: SortOption.ASC,
							},
							type: ListViewTypes.SET_SORT,
						})
					}
					sort={sort}
				/>

				<ManagementToolbarSearch
					disabled={false}
					onSubmit={(searchText: string) => onSearch(searchText)}
					searchText={keywords}
					setShowMobile={setShowMobile}
					showMobile={showMobile}
				/>

				<ManagementToolbarRight
					addButton={addButton}
					setShowMobile={setShowMobile}
					viewTypes={viewTypes}
				/>
			</ClayManagementToolbar>

			{keywords && (
				<ManagementToolbarResultsBar
					keywords={keywords}
					onClear={() => {
						onSearch('');
					}}
					totalItems={totalItems}
				/>
			)}
		</>
	);
};

export default ManagementToolbar;
