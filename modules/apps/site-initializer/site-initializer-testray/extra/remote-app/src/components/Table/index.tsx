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

import ClayTable from '@clayui/table';
import classNames from 'classnames';
import React from 'react';
import {useNavigate} from 'react-router-dom';

const {Body, Cell, Head, Row} = ClayTable;

type Column<T = any> = {
	clickable?: boolean;
	key: string;
	render?: (itemValue: any, item: T) => String | React.ReactNode;
	value: string;
};

export type TableProps<T = any> = {
	actions?: any[];
	className?: string;
	columns: Column[];
	items: T[];
	navigateTo?: (item: T) => string;
};

const Table: React.FC<TableProps> = ({
	actions,
	className,
	columns,
	items,
	navigateTo,
}) => {
	const navigate = useNavigate();

	return (
		<ClayTable borderless className={className} hover={false}>
			<Head>
				<Row>
					{columns.map((column, index) => (
						<Cell headingTitle key={index}>
							{column.value}
						</Cell>
					))}

					{actions && <Cell headingCell></Cell>}
				</Row>
			</Head>

			<Body>
				{items.map((item, index) => (
					<Row key={index}>
						{columns.map((column, columnIndex) => (
							<Cell
								className={classNames({
									'cursor-pointer': column.clickable,
								})}
								expanded={columnIndex === 0}
								headingCell={columnIndex === 0}
								key={columnIndex}
								onClick={() => {
									if (navigateTo && column.clickable) {
										navigate(navigateTo(item));
									}
								}}
							>
								{column.render
									? column.render(item[column.key], item)
									: item[column.key]}
							</Cell>
						))}

						{actions && <Cell>Dropdown</Cell>}
					</Row>
				))}
			</Body>
		</ClayTable>
	);
};

export default Table;
