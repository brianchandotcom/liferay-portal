/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Body, Cell, Head, Row, Table, Text} from '@clayui/core';
import {WeightFont} from '@clayui/core/lib/typography/Heading';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {sub} from 'frontend-js-web';
import React, {useEffect, useMemo, useState} from 'react';

import {InventoryAnalysisDataType} from './InventoryAnalysisCard';

type TableData = {
	percentage: number;
	title: string;
	volume: JSX.Element;
};

const initialTableValues = {
	delta: 10,
	page: 1,
};

const viewSpecs = {
	chart: {
		expandable: 'volume',
		textWeight: 'semi-bold',
	},
	table: {
		expandable: 'title',
		textWeight: 'normal',
	},
};

const VolumeChart = ({
	percentage,
	volume,
}: {
	percentage: number;
	volume: number;
}) => {
	return (
		<div className="cms-dashboard__inventory-analysis__bar-chart">
			<div
				className="cms-dashboard__inventory-analysis__bar-chart__bar"
				style={{width: `${percentage}%`}}
			/>

			<div className="cms-dashboard__inventory-analysis__bar-chart__value">
				<Text size={3} weight="semi-bold">
					{volume}
				</Text>
			</div>
		</div>
	);
};

const mapData = (
	data: InventoryAnalysisDataType,
	viewType: 'chart' | 'table'
): TableData[] => {
	return data.inventoryAnalysisItems.map(({count, title}) => {
		const percentage = (count / data.totalCount) * 100;

		title = title === 'Unknown' ? '' : title;

		return {
			percentage,
			title,
			volume:
				viewType === 'chart' ? (
					<VolumeChart percentage={percentage} volume={count} />
				) : (
					<Text size={3} weight="normal">
						{count}
					</Text>
				),
		};
	});
};

interface IPaginatedTable {
	currentStructureTypeLabel: string;
	inventoryAnalysisData: InventoryAnalysisDataType | undefined;
	viewType: 'chart' | 'table';
}

const PaginatedTable: React.FC<IPaginatedTable> = ({
	currentStructureTypeLabel,
	inventoryAnalysisData,
	viewType,
}) => {
	const [delta, setDelta] = useState(initialTableValues.delta);
	const [page, setPage] = useState(initialTableValues.page);
	const [tableData, setTableData] = useState<TableData[]>([]);

	const displayedItems = useMemo(() => {
		const startIndex = (page - 1) * delta;
		const endIndex = startIndex + delta;

		return tableData.slice(startIndex, endIndex);
	}, [page, delta, tableData]);

	useEffect(() => {
		if (inventoryAnalysisData) {
			setTableData(mapData(inventoryAnalysisData, viewType));
			setPage(initialTableValues.page);
			setDelta(initialTableValues.delta);
		}
	}, [inventoryAnalysisData, viewType]);

	const handlePageChange = (newPage: number) => {
		setPage(newPage);
	};

	const handleDeltaChange = (newDelta: number) => {
		setDelta(newDelta);
		setPage(1);
	};

	return (
		<div className="px-3">
			<Table
				borderless={viewType === 'chart'}
				columnsVisibility={false}
				hover={false}
				striped={false}
			>
				<Head
					items={[
						{
							id: 'title',
							name: Liferay.Language.get(
								currentStructureTypeLabel
							),
							width: '200px',
						},
						{
							id: 'volume',
							name: Liferay.Language.get('assets-volume'),
							width: 'calc(100% - 310px)',
						},
						{
							id: 'percentage',
							name: Liferay.Language.get('%-of-assets'),
							width: '110px',
						},
					]}
				>
					{(column) => (
						<Cell key={column.id} width={column.width}>
							{column.name}
						</Cell>
					)}
				</Head>

				<Body items={displayedItems}>
					{(row) => (
						<Row>
							<Cell
								className={
									viewType === 'chart' ? 'borderless' : ''
								}
								expanded={
									viewSpecs[viewType].expandable === 'volume'
								}
								width="10%"
							>
								<Text size={3} weight="semi-bold">
									{row['title'] ||
										sub(
											Liferay.Language.get('no-x'),
											currentStructureTypeLabel
										)}
								</Text>
							</Cell>

							<Cell
								align="right"
								className={
									viewType === 'chart' ? 'borderless' : ''
								}
								expanded={
									viewSpecs[viewType].expandable === 'volume'
								}
								width="80%"
							>
								{row['volume']}
							</Cell>

							<Cell
								align="right"
								className={
									viewType === 'chart' ? 'borderless' : ''
								}
								width="10%"
							>
								<Text
									size={3}
									weight={
										viewSpecs[viewType]
											.textWeight as WeightFont
									}
								>
									{`${row['percentage'].toFixed(2)}%`}
								</Text>
							</Cell>
						</Row>
					)}
				</Body>
			</Table>

			<ClayPaginationBarWithBasicItems
				activeDelta={delta}
				className="mt-3"
				ellipsisBuffer={3}
				ellipsisProps={{'aria-label': 'More', 'title': 'More'}}
				onActiveChange={(newPage: number) => handlePageChange(newPage)}
				onDeltaChange={(newDelta: number) =>
					handleDeltaChange(newDelta)
				}
				totalItems={tableData.length}
			/>
		</div>
	);
};

export default PaginatedTable;
