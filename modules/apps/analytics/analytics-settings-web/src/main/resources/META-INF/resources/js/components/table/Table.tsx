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
import React, {useEffect} from 'react';

import {OrderBy, TFilter} from '../../utils/filter';
import {TPagination} from '../../utils/pagination';
import useFetchData from '../../utils/useFecthData';
import TableContext, {Events, useData, useDispatch} from './Context';
import ManagementToolbar from './ManagementToolbar';
import PaginationBar from './PaginationBar';
import StateRenderer from './StateRenderer';

export type TColumn = {
	expanded: boolean;
	label: string;
	sortable?: boolean;
	value: string;
};

export type TItem = {
	checked: boolean;
	columns: string[];
	disabled: boolean;
	id: string;
};

function serializeQueries({
	filter: {type, value},
	keywords,
	pagination: {page, pageSize},
}: {
	filter: TFilter;
	keywords: string;
	pagination: TPagination;
}): string {
	const params = {
		keywords,
		page,
		pageSize,
		sort: `${value}:${type}`,
	};

	// @ts-ignore

	const arrs = Object.keys(params).map((key) => [key, String(params[key])]);
	const path = new URLSearchParams(arrs);

	return decodeURIComponent(path.toString());
}

interface ITableContentProps {
	columns: TColumn[];
	disabled: boolean;
}

const TableContent: React.FC<ITableContentProps> = ({columns, disabled}) => {
	const {filter, items} = useData();
	const dispatch = useDispatch();

	return (
		<ClayTable hover={!disabled}>
			<ClayTable.Head>
				<ClayTable.Row>
					<ClayTable.Cell></ClayTable.Cell>

					{columns.map(({expanded = false, label, value}) => (
						<ClayTable.Cell
							expanded={expanded}
							headingCell
							key={label}
						>
							<span>{label}</span>

							{filter.value === value && (
								<span>
									<ClayIcon
										symbol={
											filter.type === OrderBy.Asc
												? 'order-arrow-up'
												: 'order-arrow-down'
										}
									/>
								</span>
							)}
						</ClayTable.Cell>
					))}
				</ClayTable.Row>
			</ClayTable.Head>

			<ClayTable.Body>
				{items.map(
					(
						{checked, columns, disabled: disabledItem = false, id},
						index
					) => (
						<ClayTable.Row
							className={classNames({
								'table-active': checked,
								'text-muted': disabled,
							})}
							key={id}
						>
							<ClayTable.Cell>
								<ClayCheckbox
									checked={checked}
									disabled={disabled || disabledItem}
									id={id}
									onChange={() => {
										const newItems = [...items];

										if (!disabled && !disabledItem) {
											newItems[index].checked = !newItems[
												index
											].checked;
										}

										dispatch({
											payload: newItems,
											type: Events.ChangeItems,
										});
									}}
								/>
							</ClayTable.Cell>

							{columns.map((label, index: number) => (
								<ClayTable.Cell key={index}>
									{label}
								</ClayTable.Cell>
							))}
						</ClayTable.Row>
					)
				)}
			</ClayTable.Body>
		</ClayTable>
	);
};

interface ITableProps {
	columns: TColumn[];
	disabled?: boolean;
	emptyStateTitle: string;
	fetchFn: (queryString?: string) => Promise<any>;
	mapperItems: (items: any) => TItem[];
	noResultsTitle: string;
	onItemsChange?: (items: TItem[]) => void;
}

const Table: React.FC<ITableProps> = ({
	columns,
	disabled = false,
	emptyStateTitle,
	fetchFn,
	mapperItems,
	noResultsTitle,
	onItemsChange,
}) => {
	const {filter, items, keywords, pagination} = useData();
	const dispatch = useDispatch();

	const {data, error, loading, refetch, refetching} = useFetchData(
		fetchFn,
		serializeQueries({filter, keywords, pagination})
	);

	const empty = !data?.items.length;

	useEffect(() => {
		if (data) {
			const {items, page, pageSize, totalCount} = data;

			dispatch({
				payload: {
					items: mapperItems(items),
					pagination: {
						page,
						pageSize,
						totalCount,
					},
				},
				type: Events.FormatData,
			});
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [data, dispatch]);

	useEffect(() => {
		onItemsChange && onItemsChange(items);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [items]);

	return (
		<>
			<ManagementToolbar
				columns={columns}
				disabled={disabled || (empty && !keywords)}
			/>

			<StateRenderer
				columns={columns}
				data={data}
				disabled={disabled}
				empty={empty}
				emptyStateTitle={emptyStateTitle}
				error={error}
				loading={loading}
				noResultsTitle={noResultsTitle}
				refetch={refetch}
				refetching={refetching}
			>
				<TableContent columns={columns} disabled={disabled} />
			</StateRenderer>

			<PaginationBar disabled={empty} />
		</>
	);
};

const TableWrapper: React.FC<ITableProps> = ({columns, ...otherProps}) => (
	<TableContext>
		<Table {...otherProps} columns={columns} />
	</TableContext>
);

export default TableWrapper;
