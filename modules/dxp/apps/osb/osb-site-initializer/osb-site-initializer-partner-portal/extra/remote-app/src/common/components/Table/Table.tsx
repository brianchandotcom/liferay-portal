/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayTable from '@clayui/table';

import TableColumn from '../../interfaces/tableColumn';

interface TableProps<T> {
	className?: string;
	columns: TableColumn<T>[];
	customClickOnRow?: (row: T) => void;
	rows: T[];
}

const Table = <T extends unknown>({
	className,
	columns,
	customClickOnRow,
	rows,
}: TableProps<T>) => (
	<ClayTable
		borderless
		className={className}
		noWrap
		responsive
		tableVerticalAlignment="middle"
	>
		<ClayTable.Head>
			<ClayTable.Row>
				{columns.map((column: TableColumn<T>, index: number) => (
					<ClayTable.Cell
						align="left"
						className="align-baseline border-neutral-2 rounded-0"
						headingCell
						key={index}
						truncate
					>
						<p className="mt-4 text-neutral-10">{column.label}</p>
					</ClayTable.Cell>
				))}
			</ClayTable.Row>
		</ClayTable.Head>

		<ClayTable.Body>
			{rows.map((row, rowIndex) => (
				<ClayTable.Row key={rowIndex}>
					{columns.map((column, colIndex) => {
						const data: any = row[column.columnKey as keyof T];

						return (
							<ClayTable.Cell
								align="left"
								className="border-0 font-weight-normal py-4 text-neutral-10"
								headingCell
								key={colIndex}
								noWrap
								onClick={() => {
									if (customClickOnRow) {
										return customClickOnRow(row);
									}
								}}
								truncate
							>
								{column.render
									? column.render(data, row, rowIndex)
									: data}
							</ClayTable.Cell>
						);
					})}
				</ClayTable.Row>
			))}
		</ClayTable.Body>
	</ClayTable>
);

export default Table;
