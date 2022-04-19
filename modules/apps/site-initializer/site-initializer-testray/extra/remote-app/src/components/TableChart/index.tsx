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

import classNames from 'classnames';

type DataProps = {
	redirectTo?: string;
	value?: number;
};

type TableChartProps = {
	colors: string[][];
	columns: string[][];
	data: DataProps[][];
	title: string;
};

const TableChart: React.FC<TableChartProps> = ({
	colors,
	columns,
	data,
	title,
}) => {
	const [horizontalColumns, verticalColumns] = columns;

	return (
		<table className="table table-borderless table-chart table-sm">
			<thead>
				<tr>
					<td className="border-0 pb-2" colSpan={6}>
						{title}
					</td>
				</tr>
			</thead>

			<tbody>
				<tr>
					<th></th>

					{horizontalColumns.map((horizontalColumn) => (
						<td
							className="text-neutral-7 text-paragraph-xs"
							key={horizontalColumn}
						>
							{horizontalColumn}
						</td>
					))}
				</tr>

				{verticalColumns.map((verticalColumn, verticalColumnIndex) => (
					<tr key={verticalColumn}>
						<td className="text-neutral-7 text-paragraph-xs">
							{verticalColumn}
						</td>

						{horizontalColumns.map((_, horizontalColumnIndex) => {
							const dataType =
								data[verticalColumnIndex][
									horizontalColumnIndex
								];

							return (
								<td
									className={classNames(
										'border py-2 table-chart-data-area text-right',
										colors[verticalColumnIndex][
											horizontalColumnIndex
										]
									)}
									key={`${verticalColumnIndex}-${horizontalColumnIndex}`}
								>
									{dataType?.value && (
										<a
											className="text-neutral-10"
											href={dataType?.redirectTo}
										>
											{dataType.value}
										</a>
									)}
								</td>
							);
						})}
					</tr>
				))}
			</tbody>
		</table>
	);
};

export default TableChart;
