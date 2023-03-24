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
import {memo} from 'react';
import {Link} from 'react-router-dom';
import i18n from '~/i18n';

type TableChartProps = {
	matrixData: number[][];
	title: string;
};

const columns = [
	i18n.translate('passed'),
	i18n.translate('failed'),
	i18n.translate('blocked'),
	i18n.translate('test-fix'),
	i18n.translate('dnr'),
];

const STATUS_COLOR = {
	BLOCKED: 'blocked',
	DNR: 'dnr',
	FAILED: 'failed',
	PASSED: 'passed',
	TEST_FIX: 'test-fix',
};

const colors = [
	[
		STATUS_COLOR.PASSED,
		STATUS_COLOR.FAILED,
		STATUS_COLOR.BLOCKED,
		STATUS_COLOR.TEST_FIX,
		STATUS_COLOR.PASSED,
	],
	[
		STATUS_COLOR.FAILED,
		STATUS_COLOR.FAILED,
		STATUS_COLOR.FAILED,
		STATUS_COLOR.FAILED,
		STATUS_COLOR.FAILED,
	],
	[
		STATUS_COLOR.BLOCKED,
		STATUS_COLOR.FAILED,
		STATUS_COLOR.BLOCKED,
		STATUS_COLOR.BLOCKED,
		STATUS_COLOR.BLOCKED,
	],
	[
		STATUS_COLOR.TEST_FIX,
		STATUS_COLOR.FAILED,
		STATUS_COLOR.BLOCKED,
		STATUS_COLOR.TEST_FIX,
		STATUS_COLOR.TEST_FIX,
	],
	[
		STATUS_COLOR.PASSED,
		STATUS_COLOR.FAILED,
		STATUS_COLOR.BLOCKED,
		STATUS_COLOR.TEST_FIX,
		STATUS_COLOR.DNR,
	],
];

const TableChart: React.FC<TableChartProps> = ({matrixData, title}) => (
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

				{columns.map((horizontalColumn, index) => (
					<td
						className="text-paragraph-xs tr-table-chart__column-title"
						key={index}
					>
						B {horizontalColumn}
					</td>
				))}
			</tr>

			{columns.map((verticalColumn, verticalColumnIndex) => (
				<tr key={verticalColumnIndex}>
					<td className="text-paragraph-xs tr-table-chart__column-title">
						A {verticalColumn}
					</td>

					{columns.map((_, horizontalColumnIndex) => {
						const value =
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
								{value > 0 && (
									<Link className="font-weight-bold" to="">
										{value}
									</Link>
								)}
							</td>
						);
					})}
				</tr>
			))}
		</tbody>
	</table>
);

export default memo(TableChart);
