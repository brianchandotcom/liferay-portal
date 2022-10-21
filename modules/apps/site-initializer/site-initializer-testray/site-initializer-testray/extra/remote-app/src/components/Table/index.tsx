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
import ClayIcon from '@clayui/icon';
import ClayTable from '@clayui/table';
import classNames from 'classnames';
import React, {useState} from 'react';
import {useNavigate} from 'react-router-dom';
import {KeyedMutator} from 'swr';

import {Sort} from '../../context/ListViewContext';
import useContextMenu from '../../hooks/useContextMenu';
import {APIResponse} from '../../services/rest';
import {Action, SortDirection, SortOption} from '../../types';
import {Permission} from '../../util/permission';
import ContextMenu from '../ContextMenu';
import DropDown from '../DropDown/DropDown';

type Column<T = any> = {
	clickable?: boolean;
	key: string;
	render?: (
		itemValue: any,
		item: T,
		mutate: KeyedMutator<APIResponse<T>>
	) => String | React.ReactNode;
	size?: 'sm' | 'md' | 'lg' | 'xl' | 'none';
	sorteable?: boolean;
	value: string;
};

export type TableProps<T = any> = {
	actions?: Action[];
	allRowsChecked?: boolean;
	columns: Column<T>[];
	items: T[];
	mutate: KeyedMutator<T>;
	navigateTo?: (item: T) => string;
	onClickRow?: (item: T) => void;
	onSelectAllRows: () => void;
	onSelectRow?: (row: any) => void;
	onSort: (columnTable: string, direction: SortDirection) => void;
	rowSelectable?: boolean;
	rowWrap?: boolean;
	selectedRows?: number[];
	sort?: Sort;
};

const Table: React.FC<TableProps> = ({
	allRowsChecked = false,
	actions,
	columns,
	items,
	mutate,
	navigateTo,
	onClickRow,
	onSelectAllRows,
	onSelectRow,
	onSort,
	rowSelectable = false,
	rowWrap = false,
	selectedRows = [],
	sort,
}) => {
	const [firstRowAction] = items;

	const filteredActions = actions
		? Permission.filterActions(actions, firstRowAction?.actions)
		: [];

	const displayActionColumn = !!filteredActions.length;

	const {
		contextMenuState,
		handleContext,
		setContextMenuState,
	} = useContextMenu(displayActionColumn);

	const [activeRow, setActiveRow] = useState<number | undefined>();
	const [sorted, setSorted] = useState<SortDirection>(SortOption.ASC);

	const navigate = useNavigate();

	const onMouseLeaveRow = () => {
		if (displayActionColumn) {
			setActiveRow(undefined);
		}
	};

	const onMouseOverRow = (rowIndex: number) => {
		if (displayActionColumn) {
			setActiveRow(rowIndex);
		}
	};

	const changeSort = (key: string) => {
		onSort(key, sorted);
		setSorted(
			sorted === SortOption.DESC ? SortOption.ASC : SortOption.DESC
		);
	};

	const getSortSymbol = (key: string) => {
		if (!sort) {
			return '';
		}

		if (sort.key === key) {
			return sort.direction === SortOption.ASC
				? 'caret-top-l'
				: 'caret-bottom-l';
		}

		return 'caret-double-l';
	};

	return (
		<>
			<ClayTable borderless className="testray-table" hover>
				<ClayTable.Head>
					<ClayTable.Row>
						{rowSelectable && (
							<ClayTable.Cell>
								<ClayCheckbox
									checked={allRowsChecked}
									onChange={() => {
										onSelectAllRows();
									}}
								/>
							</ClayTable.Cell>
						)}

						{columns.map((column, index) => (
							<ClayTable.Cell
								className="align-items-center text-nowrap"
								headingTitle
								key={index}
							>
								<>
									{column.value}

									{column.sorteable && (
										<ClayIcon
											className="cursor-pointer ml-1"
											onClick={() =>
												changeSort(column.key)
											}
											symbol={getSortSymbol(column.key)}
										/>
									)}
								</>
							</ClayTable.Cell>
						))}

						{displayActionColumn && <ClayTable.Cell />}
					</ClayTable.Row>
				</ClayTable.Head>

				<ClayTable.Body>
					{items.map((item, rowIndex) => (
						<ClayTable.Row
							active={
								rowIndex === contextMenuState.rowIndex &&
								contextMenuState.visible
							}
							className={classNames('table-row', {
								'text-nowrap': !rowWrap,
								'text-wrap': rowWrap,
							})}
							key={rowIndex}
							onContextMenu={(event) => {
								if (displayActionColumn) {
									handleContext({
										actions: filteredActions,
										event,
										item,
										rowIndex,
									});
								}
							}}
							onMouseLeave={() => onMouseLeaveRow()}
							onMouseOver={() => onMouseOverRow(rowIndex)}
						>
							{rowSelectable && onSelectRow && (
								<ClayTable.Cell>
									<ClayCheckbox
										checked={selectedRows.includes(item.id)}
										onChange={() => onSelectRow(item.id)}
									/>
								</ClayTable.Cell>
							)}

							{columns.map((column, columnIndex) => (
								<ClayTable.Cell
									className={classNames('text-dark', {
										'cursor-pointer': column.clickable,
										'table-cell-expand':
											column.size === 'sm',
										'table-cell-expand-small':
											column.size === 'xl',
										'table-cell-expand-smaller':
											column.size === 'lg',
										'table-cell-expand-smallest':
											column.size === 'md',
									})}
									key={columnIndex}
									onClick={() => {
										if (column.clickable) {
											if (onClickRow) {
												onClickRow(item);
											}

											if (navigateTo) {
												navigate(navigateTo(item));
											}
										}
									}}
								>
									{column.render
										? column.render(
												item[column.key],
												{
													...item,
													rowIndex,
												},
												mutate
										  )
										: item[column.key]}
								</ClayTable.Cell>
							))}

							{displayActionColumn && (
								<ClayTable.Cell
									align="right"
									className="py-0 table-action-column table-cell-expand"
								>
									{activeRow === rowIndex ? (
										<DropDown
											actions={filteredActions as any}
											item={item}
											mutate={mutate}
										/>
									) : (
										<div />
									)}
								</ClayTable.Cell>
							)}
						</ClayTable.Row>
					))}
				</ClayTable.Body>
			</ClayTable>

			{displayActionColumn && contextMenuState.visible && (
				<ContextMenu
					contextMenuState={contextMenuState}
					mutate={mutate}
					setContextMenuState={setContextMenuState}
				/>
			)}
		</>
	);
};

export default Table;
