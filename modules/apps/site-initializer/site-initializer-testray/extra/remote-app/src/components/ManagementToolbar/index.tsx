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
import {RendererFields} from '../Form/Renderer';
import {TableProps} from '../Table';
import ManagementToolbarLeft from './ManagementToolbarLeft';
import ManagementToolbarResultsBar from './ManagementToolbarResultsBar';
import ManagementToolbarRight, {IItem} from './ManagementToolbarRight';
import ManagementToolbarSearch from './ManagementToolbarSearch';

export type ManagementToolbarProps = {
	addButton?: () => void;
	filterFields?: RendererFields[];
	onSelectAllRows: () => void;
	rowSelectable?: boolean;
	tableProps: Omit<TableProps, 'items'>;
	totalItems: number;
};

const ManagementToolbar: React.FC<ManagementToolbarProps> = ({
	addButton,
	filterFields,
	onSelectAllRows,
	rowSelectable,
	tableProps,
	totalItems,
}) => {
	const [{filters, keywords}, dispatch] = useContext(ListViewContext);
	const [showMobile, setShowMobile] = useState(false);

	const disabled = totalItems === 0;

	const columns = [
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

	const onSearch = (searchText: string) => {
		dispatch({payload: searchText, type: ListViewTypes.SET_SEARCH});
	};

	return (
		<>
			<ClayManagementToolbar>
				<ManagementToolbarLeft
					disabled={disabled}
					onSelectAllRows={onSelectAllRows}
					rowSelectable={rowSelectable}
				/>

				<ManagementToolbarSearch
					disabled={disabled}
					onSubmit={(searchText: string) => onSearch(searchText)}
					searchText={keywords}
					setShowMobile={setShowMobile}
					showMobile={showMobile}
				/>

				<ManagementToolbarRight
					addButton={addButton}
					columns={columns as IItem[]}
					disabled={disabled}
					filterFields={filterFields}
					setShowMobile={setShowMobile}
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
