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
import DropDown from './DropDown.es';
import React from 'react';

const {Body, Cell, Head, Row} = ClayTable;

export default function Table(props) {
	const {actions, columns, rows} = props;

	return (
		<ClayTable>
			<Head>
				<Row>
					{columns.map((column, index) => (
						<Cell
							key={index}
							expanded={index === 0}
							headingCell
							headingTitle
						>
							{Object.values(column)[0]}
						</Cell>
					))}
					{actions.length > 0 && <Cell>{''}</Cell>}
				</Row>
			</Head>
			<Body>
				{rows.map(row => (
					<Row key={row.id}>
						{columns.map((column, index) => (
							<Cell key={index} headingTitle={index === 0}>
								{row[Object.keys(column)[0]]}
							</Cell>
						))}
						<Cell>
							<DropDown actions={actions} row={row} />
						</Cell>
					</Row>
				))}
			</Body>
		</ClayTable>
	);
}
