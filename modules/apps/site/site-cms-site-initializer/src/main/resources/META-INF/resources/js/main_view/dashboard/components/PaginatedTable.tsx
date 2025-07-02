/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Body, Cell, Head, Row, Table, Text} from '@clayui/core';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
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

const mapData = (data: InventoryAnalysisDataType): TableData[] => {
	return data.inventoryAnalysisItems.map(({count, title}) => {
		const percentage = (count / data.totalCount) * 100;

		title = title === 'Unknown' ? '' : title;

		return {
			percentage,
			title,
			volume: <VolumeChart percentage={percentage} volume={count} />,
		};
	});
};

interface IPaginatedTable {
	currentStructureTypeLabel: string;
	inventoryAnalysisData: InventoryAnalysisDataType | undefined;
}

const PaginatedTable: React.FC<IPaginatedTable> = ({
	currentStructureTypeLabel,
	inventoryAnalysisData,
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
			setTableData(mapData(inventoryAnalysisData));
			setPage(initialTableValues.page);
			setDelta(initialTableValues.delta);
		}
	}, [inventoryAnalysisData]);

	const handlePageChange = (newPage: number) => {
		setPage(newPage);
	};

	const handleDeltaChange = (newDelta: number) => {
		setDelta(newDelta);
		setPage(1);
	};

	return (
		<div>
			<Table
				borderless
				columnsVisibility={false}
				hover={false}
				striped={false}
			>
				<Head
					items={[
						{
							id: 'title',
							name: Liferay.Language.get('structure-label'),
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
						<Cell
							expanded={column.id === 'volume'}
							key={column.id}
							width={column.width}
						>
							{column.name}
						</Cell>
					)}
				</Head>

				<Body items={displayedItems}>
					{(row) => (
						<Row>
							<Cell width="10%">
								<Text size={3} weight="semi-bold">
									{row['title'] ||
										`No ${currentStructureTypeLabel}`}
								</Text>
							</Cell>

							<Cell expanded width="80%">
								{row['volume']}
							</Cell>

							<Cell align="left" width="10%">
								<Text size={3} weight="semi-bold">
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
