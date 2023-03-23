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
import {Link} from 'react-router-dom';
import {translate} from '~/i18n';

type TableChartProps = {
	matrixData: number[][];
	title: string;
};

const COLORS = {
	BLOCKED: 'blocked',
	DNR: 'dnr',
	FAILED: 'failed',
	PASSED: 'passed',
	TEST_FIX: 'test-fix',
};

const COLUMNS = {
	BLOCKED: translate('blocked'),
	DNR: translate('dnr'),
	FAILED: translate('failed'),
	PASSED: translate('passed'),
	TEST_FIX: translate('test-fix'),
};

const TableChart: React.FC<TableChartProps> = ({matrixData, title}) => {
	const columns = {
		horizontalColumns: [
			`B ${COLUMNS.PASSED}`,
			`B ${COLUMNS.FAILED}`,
			`B ${COLUMNS.BLOCKED}`,
			`B ${COLUMNS.TEST_FIX}`,
			`B ${COLUMNS.DNR}`,
		],
		verticalColumns: [
			`A ${COLUMNS.PASSED}`,
			`A ${COLUMNS.FAILED}`,
			`A ${COLUMNS.BLOCKED}`,
			`A ${COLUMNS.TEST_FIX}`,
			`A ${COLUMNS.DNR}`,
		],
	};

	const colors = [
		[
			COLORS.PASSED,
			COLORS.FAILED,
			COLORS.BLOCKED,
			COLORS.TEST_FIX,
			COLORS.PASSED,
		],
		[
			COLORS.FAILED,
			COLORS.FAILED,
			COLORS.FAILED,
			COLORS.FAILED,
			COLORS.FAILED,
		],
		[
			COLORS.BLOCKED,
			COLORS.FAILED,
			COLORS.BLOCKED,
			COLORS.BLOCKED,
			COLORS.BLOCKED,
		],
		[
			COLORS.TEST_FIX,
			COLORS.FAILED,
			COLORS.BLOCKED,
			COLORS.TEST_FIX,
			COLORS.TEST_FIX,
		],
		[
			COLORS.PASSED,
			COLORS.FAILED,
			COLORS.BLOCKED,
			COLORS.TEST_FIX,
			COLORS.DNR,
		],
	];

	return (
		<table className="table table-borderless table-sm tr-table-chart">
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

					{columns.horizontalColumns.map((horizontalColumn) => (
						<td
							className="text-paragraph-xs tr-table-chart__column-title"
							key={horizontalColumn}
						>
							{horizontalColumn}
						</td>
					))}
				</tr>

				{columns.verticalColumns.map(
					(verticalColumn, verticalColumnIndex) => (
						<tr key={verticalColumn}>
							<td className="text-paragraph-xs tr-table-chart__column-title">
								{verticalColumn}
							</td>

							{columns.horizontalColumns.map(
								(_, horizontalColumnIndex) => {
									const dataType =
										matrixData[verticalColumnIndex][
											horizontalColumnIndex
										];

									return (
										<td
											className={classNames(
												'border py-2 tr-table-chart__data-area text-center',
												colors[verticalColumnIndex][
													horizontalColumnIndex
												]
											)}
											key={`${verticalColumnIndex}-${horizontalColumnIndex}`}
										>
											{dataType > 0 && (
												<Link
													className="font-weight-bold"
													to=""
												>
													{dataType}
												</Link>
											)}
										</td>
									);
								}
							)}
						</tr>
					)
				)}
			</tbody>
		</table>
	);
};

export default TableChart;
