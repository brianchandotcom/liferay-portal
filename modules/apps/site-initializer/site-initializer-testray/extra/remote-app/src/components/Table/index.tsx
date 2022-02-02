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
import React from 'react';

const {Body, Cell, Head, Row} = ClayTable;

export type TableProps = {
	actions?: any[];
	className?: string;
	columns: any[];
	items: any[];
};

type CellWrapperProps = {
	colSpan?: number;
	index: number;
};

const CellWrapper: React.FC<CellWrapperProps> = ({
	children,
	colSpan = 1,
	index,
}) => (
	<Cell
		colSpan={colSpan}
		expanded={index === 0}
		headingTitle={index === 0}
		key={index}
	>
		{children}
	</Cell>
);

const Table: React.FC<TableProps> = ({actions, className, columns, items}) => (
	<ClayTable className={className} hover={false}>
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
						<CellWrapper index={columnIndex} key={columnIndex}>
							{column.render
								? column.render(item[column.key], item)
								: item[column.key]}
						</CellWrapper>
					))}

					{actions && <Cell>Dropdown</Cell>}
				</Row>
			))}
		</Body>
	</ClayTable>
);

export default Table;
