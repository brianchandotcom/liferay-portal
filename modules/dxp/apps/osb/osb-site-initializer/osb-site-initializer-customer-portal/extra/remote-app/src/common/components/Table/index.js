import ClayTable from '@clayui/table';
import React from 'react';
import TablePagination from './Pagination';

const Table = ({
	activePage = 1,
	columns,
	data,
	hasPagination,
	itemsPerPage = 5,
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

				<ClayTable.Body>
					{data.map((item, index) => (
						<ClayTable.Row key={index}>
							{columns.map((column) => (
								<ClayTable.Cell
									align={column.align}
									className={column.bodyClass}
									headingTitle={column.headingTitle}
									key={item[column.accessor]}
								>
									{item[column.accessor]}
								</ClayTable.Cell>
							))}
						</ClayTable.Row>
					))}
				</ClayTable.Body>
			</ClayTable>
			{hasPagination && (
				<TablePagination
					activePage={activePage}
					itemsPerPage={itemsPerPage}
					setActivePage={setActivePage}
					totalItems={data.length}
				/>
			)}
		</>
	);
};

export default Table;
