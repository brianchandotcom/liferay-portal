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

import {ClayInput, ClayRadio} from '@clayui/form';
import ClayTable from '@clayui/table';
import React, {useState} from 'react';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';

const TableHead = ({columns}) => (
	<ClayTable.Head>
		<ClayTable.Row>
			<ClayTable.Cell headingCell />
			{columns.map((column, colIndex) => {
				return (
					<ClayTable.Cell
						headingCell
						key={`column-${column.value}-${colIndex}`}
					>
						{column.label}
					</ClayTable.Cell>
				);
			})}
		</ClayTable.Row>
	</ClayTable.Head>
);

const TableBodyColumns = ({
	columns,
	disabled,
	onBlur,
	onChange,
	onFocus,
	row,
	value,
}) => {
	const columnLabel = Liferay.Language.get('column');
	const rowLabel = Liferay.Language.get('row');

	return columns.map((column, colIndex) => {
		return (
			<ClayTable.Cell key={`cell-${column.value}-${colIndex}`}>
				<ClayRadio
					aria-label={`${rowLabel}: ${row.label}, ${columnLabel}: ${column.label}`}
					checked={column.value === value[row.value]}
					className="form-builder-grid-field"
					disabled={disabled}
					name={row.value}
					onBlur={onBlur}
					onChange={onChange}
					onFocus={onFocus}
					value={column.value}
				/>
			</ClayTable.Cell>
		);
	});
};

const Grid = ({
	columns = [{label: 'col1', value: 'fieldId'}],
	disabled,
	name,
	onBlur,
	onChange,
	onFocus,
	rows = [{label: 'row', value: 'jehf'}],
	value,
	...otherProps
}) => (
	<div className="table-responsive" {...otherProps}>
		{!disabled &&
			rows.map((row, rowIndex) => {
				const inputValue = value[row.value]
					? `${row.value};${value[row.value]}`
					: '';

				return (
					<ClayInput
						aria-hidden="true"
						key={`row-${row.value}-${rowIndex}`}
						name={name}
						type="hidden"
						value={inputValue}
					/>
				);
			})}

		<ClayTable striped>
			<TableHead columns={columns} />

			<ClayTable.Body>
				{rows.map((row, rowIndex) => {
					return (
						<ClayTable.Row
							key={`row-${row.value}-${rowIndex}`}
							name={row.value}
						>
							<ClayTable.Cell>{row.label}</ClayTable.Cell>

							<TableBodyColumns
								columns={columns}
								disabled={disabled}
								onBlur={onBlur}
								onChange={onChange}
								onFocus={onFocus}
								row={row}
								value={value}
							/>
						</ClayTable.Row>
					);
				})}
			</ClayTable.Body>
		</ClayTable>
	</div>
);

const Main = ({
	columns,
	name,
	readOnly,
	rows,
	onChange,
	onFocus,
	onBlur,
	value = {},
	...otherProps
}) => {
	const [state, setState] = useState(value);

	return (
		<FieldBase name={name} readOnly={readOnly} {...otherProps}>
			<Grid
				columns={columns}
				disabled={readOnly}
				name={name}
				onBlur={onBlur}
				onChange={(event) => {
					const {target} = event;
					const value = {
						[target.name]: target.value,
					};

					const newState = {...state, ...value};

					setState(newState);

					onChange(event, newState);
				}}
				onFocus={onFocus}
				rows={rows}
				value={state}
			/>
		</FieldBase>
	);
};

Main.displayName = 'Grid';

export default Main;
