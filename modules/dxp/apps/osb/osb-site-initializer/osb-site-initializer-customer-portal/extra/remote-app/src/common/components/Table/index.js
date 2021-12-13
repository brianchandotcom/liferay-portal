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
					totalItems={rows.length}
				/>
			)}
		</>
	);
};

export default Table;
