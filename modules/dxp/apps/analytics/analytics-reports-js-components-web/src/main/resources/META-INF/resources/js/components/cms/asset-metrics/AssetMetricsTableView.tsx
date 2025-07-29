/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {ClayPaginationWithBasicItems} from '@clayui/pagination';
import ClayPaginationBar from '@clayui/pagination-bar';
import ClayTable from '@clayui/table';
import React, {useEffect, useState} from 'react';

import {MetricType} from '../../../types/global';
import {formatDate} from '../../../utils/date';
import {toThousands} from '../../../utils/math';
import {ICommonProps} from './AssetMetrics';

function formatDateForTable(dateStr: string): string {
	const date = new Date(dateStr);
	const formatted = formatDate(date);

	return formatted.charAt(0).toUpperCase() + formatted.slice(1);
}

const AssetMetricsTableView: React.FC<ICommonProps> = ({
	histogram,
	metricType,
}) => {
	const [delta, setDelta] = useState(10);
	const [page, setPage] = useState(1);

	const title: Partial<Record<MetricType, string>> = {
		[MetricType.Views]: Liferay.Language.get('views'),
		[MetricType.Impressions]: Liferay.Language.get('impressions'),
		[MetricType.Downloads]: Liferay.Language.get('downloads'),
	};

	useEffect(() => {
		setPage(1);
	}, [metricType]);

	const formattedData = histogram.metrics.map((metric) => ({
		date: formatDateForTable(metric.valueKey),
		previous: toThousands(metric.previousValue),
		value: toThousands(metric.value),
	}));

	const totalPages = Math.ceil(formattedData.length / delta);

	const startIndex = (page - 1) * delta;

	const displayedItems = formattedData.slice(startIndex, startIndex + delta);

	return (
		<div>
			<ClayTable hover={false} responsive>
				<ClayTable.Head>
					<ClayTable.Row>
						<ClayTable.Cell headingCell>
							{Liferay.Language.get('date')}
						</ClayTable.Cell>

						<ClayTable.Cell align="right" headingCell>
							{title[metricType]}
						</ClayTable.Cell>

						<ClayTable.Cell align="right" headingCell>
							{Liferay.Language.get('previous-period')}
						</ClayTable.Cell>
					</ClayTable.Row>
				</ClayTable.Head>

				<ClayTable.Body>
					{displayedItems.map((row, index) => (
						<ClayTable.Row key={index}>
							<ClayTable.Cell>{row.date}</ClayTable.Cell>

							<ClayTable.Cell align="right">
								{row.value}
							</ClayTable.Cell>

							<ClayTable.Cell align="right">
								{row.previous}
							</ClayTable.Cell>
						</ClayTable.Row>
					))}
				</ClayTable.Body>
			</ClayTable>

			<div className="align-items-center d-flex justify-content-between mt-3">
				<ClayPaginationBar.DropDown
					items={[10, 20, 30, 50].map((label) => ({
						label: `${label} ${Liferay.Language.get('items')}`,
						onClick: () => {
							setDelta(label);
							setPage(1);
						},
					}))}
					trigger={
						<ClayButton displayType="unstyled">
							<span>{delta}</span>

							<span>{Liferay.Language.get('items')}</span>

							<ClayIcon symbol="caret-double-l" />
						</ClayButton>
					}
				/>

				<ClayPaginationWithBasicItems
					active={page}
					ellipsisBuffer={2}
					ellipsisProps={{'aria-label': 'More', 'title': 'More'}}
					onActiveChange={setPage}
					totalPages={totalPages}
				/>
			</div>
		</div>
	);
};

export {AssetMetricsTableView};
