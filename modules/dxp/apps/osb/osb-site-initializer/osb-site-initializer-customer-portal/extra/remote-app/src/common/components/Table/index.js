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
import React from 'react';
import TablePagination from './Pagination';
import TableSkeleton from './Skeleton';

const Table = ({
	activePage = 1,
	columns,
	hasPagination,
	isLoading = false,
	itemsPerPage = 5,
	rows,
	setActivePage,
	totalCount,
	...props
}) => {
	return (
		<>
			<ClayTable {...props}>
				<ClayTable.Head>
					<ClayTable.Row>
						{columns.map((column) => (
							<ClayTable.Cell
								align={column.align}
								className={
									column.header.styles ||
									'bg-neutral-1 font-weight-bold text-neutral-8'
								}
								expanded={column.expanded}
								headingCell
								key={column.accessor}
							>
								{column.header.name}
							</ClayTable.Cell>
						))}
					</ClayTable.Row>
				</ClayTable.Head>

				{!isLoading ? (
					<ClayTable.Body>
						{rows.map((row, index) => (
							<ClayTable.Row key={index}>
								{columns.map((column) => (
									<ClayTable.Cell
										align={column.align}
										className={column.bodyClass}
										headingTitle={column.headingTitle}
										key={row[column.accessor]}
									>
										{row[column.accessor]}
									</ClayTable.Cell>
								))}
							</ClayTable.Row>
						))}
					</ClayTable.Body>
				) : (
					<TableSkeleton
						totalColumns={columns.length}
						totalItems={itemsPerPage}
					/>
				)}
			</ClayTable>

			{hasPagination && (
				<TablePagination
					activePage={activePage}
					itemsPerPage={itemsPerPage}
					setActivePage={setActivePage}
					totalItems={totalCount}
				/>
			)}
		</>
	);
};

export default Table;
