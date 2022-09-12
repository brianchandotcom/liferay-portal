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

import TableColumn from '../../../../common/interfaces/tableColumn';

interface TableProps<T> {
	borderless?: boolean;
	columns: TableColumn<T>[];
	responsive?: boolean;
	rows: T[];
}

const Table = <T extends unknown>({columns, rows, ...props}: TableProps<T>) => (
	<ClayTable {...props} noWrap={true} tableVerticalAlignment="middle">
		<ClayTable.Head>
			<ClayTable.Row>
				{columns.map((column: TableColumn<T>, index: number) => (
					<ClayTable.Cell align="left" headingCell key={index}>
						<span className="text-neutral-10">{column.label}</span>
					</ClayTable.Cell>
				))}
			</ClayTable.Row>
		</ClayTable.Head>

		<ClayTable.Body>
			{rows.map((row, index) => (
				<ClayTable.Row key={index}>
					{columns.map((column, index) => {
						const data = row[column.columnKey as keyof T];

						return (
							<ClayTable.Cell
								align="left"
								className="font-weight-normal py-5 text-neutral-10"
								headingCell
								key={index}
							>
								{column.render
									? column.render(data, row)
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
