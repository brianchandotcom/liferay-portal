/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTable from '@clayui/table';
import classNames from 'classnames';
import {useEffect, useState} from 'react';
import TablePagination from './Pagination';
import TableSkeleton from './Skeleton';

const Table = ({
	checkboxConfig,
	columns,
	hasCheckbox,
	hasPagination,
	isLoading = false,
	paginationConfig,
	rows,
	...props
}) => {
	const [isAllCheckboxsSelected, setIsAllCheckboxsSelected] = useState(false);
	const {checkboxesChecked, setCheckboxesChecked} = checkboxConfig;

	const {
		activePage,
		itemsPerPage,
		labels,
		listItemsPerPage,
		setActivePage,
		setItemsPerPage,
		showDeltasDropDown,
		totalCount,
	} = paginationConfig;

	useEffect(() => {
		if (
			rows?.length &&
			hasCheckbox &&
			rows.every((row) => checkboxesChecked.includes(row.id))
		) {
			return setIsAllCheckboxsSelected(true);
		}

		return setIsAllCheckboxsSelected(false);
	}, [checkboxesChecked, hasCheckbox, rows]);

	const handleCheckboxClick = (event, id) => {
		const {checked} = event.target;

		if (checked) {
			return setCheckboxesChecked((previousCheckboxesChecked) => [
				...previousCheckboxesChecked,
				id,
			]);
		}

		setCheckboxesChecked((previousCheckboxesChecked) =>
			previousCheckboxesChecked.filter(
				(CheckboxChecked) => CheckboxChecked !== id
			)
		);
	};

	const handleToggleAllCheckboxsSelected = () => {
		setIsAllCheckboxsSelected(
			(previousIsAllCheckboxsSelected) => !previousIsAllCheckboxsSelected
		);

		if (isAllCheckboxsSelected) {
			setCheckboxesChecked([]);

			return;
		}
		setCheckboxesChecked(rows.map((row) => row.id));
	};

	return (
		<>
			<ClayTable {...props}>
				<ClayTable.Head>
					<ClayTable.Row>
						{hasCheckbox && (
							<ClayTable.Cell className="text-center">
								<input
									checked={isAllCheckboxsSelected}
									onChange={handleToggleAllCheckboxsSelected}
									type="checkbox"
								/>
							</ClayTable.Cell>
						)}

						{columns.map((column) => (
							<ClayTable.Cell
								align={column.align}
								className={
									column.header.styles ||
									'bg-neutral-1 font-weight-bold text-neutral-8'
								}
								headingCell
								key={column.accessor}
								noWrap={column.header.noWrap}
							>
								{column.header.description ? (
									<div>
										<p className="font-weight-bold m-0 text-neutral-10">
											{column.header.name}
										</p>

										<p className="font-weight-normal m-0 text-neutral-7 text-paragraph-sm">
											{column.header.description}
										</p>
									</div>
								) : (
									column.header.name
								)}
							</ClayTable.Cell>
						))}
					</ClayTable.Row>
				</ClayTable.Head>

				{!isLoading ? (
					<ClayTable.Body>
						{rows.map((row, rowIndex) => (
							<ClayTable.Row
								className={classNames({
									'cp-common-table-active-row': checkboxesChecked.find(
										(checkboxChecked) =>
											checkboxChecked === row.id
									),
								})}
								key={row.id || rowIndex}
							>
								{hasCheckbox && (
									<ClayTable.Cell
										align="center"
										className="border-0"
										key={`checkbox-${rowIndex}`}
									>
										<input
											checked={checkboxesChecked.includes(
												row.id
											)}
											onChange={(event) =>
												handleCheckboxClick(
													event,
													row.id
												)
											}
											type="checkbox"
										/>
									</ClayTable.Cell>
								)}

								{columns.map((column, columnIndex) => (
									<ClayTable.Cell
										align={column.align}
										className={column.bodyClass}
										columnTextAlignment={column.align}
										expanded={column.expanded}
										key={`${rowIndex}-${columnIndex}`}
										noWrap={column.noWrap}
										onClick={() => {
											if (
												!column.disableCustomClickOnRow &&
												row.customClickOnRow
											) {
												return row.customClickOnRow();
											}
										}}
										truncate={column.truncate}
									>
										{row[column.accessor]}
									</ClayTable.Cell>
								))}
							</ClayTable.Row>
						))}
					</ClayTable.Body>
				) : (
					<TableSkeleton
						hasCheckbox={hasCheckbox}
						totalColumns={columns.length}
						totalItems={itemsPerPage}
					/>
				)}
			</ClayTable>

			{!!hasPagination && !!totalCount && (
				<TablePagination
					activePage={activePage}
					itemsPerPage={itemsPerPage}
					labels={labels}
					listItemsPerPage={listItemsPerPage}
					setActivePage={setActivePage}
					setItemsPerPage={setItemsPerPage}
					showDeltasDropDown={showDeltasDropDown}
					totalItems={totalCount}
				/>
			)}
		</>
	);
};

Table.defaultProps = {
	checkboxConfig: {checkboxesChecked: [], setCheckboxesChecked: () => {}},
	paginationConfig: {
		activePage: 1,
		itemsPerPage: 5,
		labels: '',
		listItemsPerPage: [],
		setActivePage: () => {},
		setItemsPerPage: () => {},
		showDeltasDropDown: false,
		totalCount: 1,
	},
};

export default Table;
