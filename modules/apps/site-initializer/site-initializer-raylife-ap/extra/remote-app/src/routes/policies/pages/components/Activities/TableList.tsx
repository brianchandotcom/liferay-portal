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

import ClayTable from '@clayui/table';
import classnames from 'classnames';
import React, {useState} from 'react';

import './index.scss';

export type TableHeaders = {
	bold?: boolean;
	key: string;
	value: string;
};

type Props = {
	headers: TableHeaders[];
	rows: InfoRowContent[];
};

type InfoRowContent = {[keys: string]: string};

const {Body, Cell, Head, Row} = ClayTable;

const TableList: React.FC<Props> = ({headers, rows}) => {
	const [selectedRow, setSelectedRow] = useState(rows[0]);

	const toggleRowContent = (item: InfoRowContent) => {
		setSelectedRow(item);
	};

	return (
		<div>
			<div className="bg-neutral-0 d-flex w-100">
				<div className="align-items-center box-activites col d-flex">
					<h2 className="border-link-active font ml-1">Activies</h2>
				</div>

				<div className="align-items-center blue-line-activites border border-bottom box-activites col d-flex position-relative">
					<p className="font ml-2 mt-3 text-nowrap">
						{selectedRow.activity}
					</p>
				</div>
			</div>

			<div className="d-flex">
				<div className="d-flex w-50">
					<table className="border-right box-table w-100">
						<Head>
							<Row className="border border-header">
								{headers.map(
									(header: TableHeaders, index: number) => (
										<Cell
											className="p-3 text-paragraph-sm"
											headingCell
											key={index}
										>
											{header.value}
										</Cell>
									)
								)}
							</Row>
						</Head>

						<Body>
							{rows.map((row, rowIndex: number) => (
								<Row
									className={classnames(
										'cursor-pointer position-relative ',
										{
											'box-shadow border-line-selected position-relative ':
												selectedRow === row,
											'dotted-line ': selectedRow !== row,
										}
									)}
									key={rowIndex}
									onClick={() => toggleRowContent(row)}
								>
									{headers.map(
										(
											header: TableHeaders,
											index: number
										) => (
											<Cell key={index}>
												<div className="p-3">
													<span
														className={classnames(
															'd-flex  w-100',
															{
																'font-table': !header.bold,
																'font-table-bold align-items-start':
																	header.bold,
															}
														)}
													>
														{row[header.key]}
													</span>
												</div>
											</Cell>
										)
									)}
								</Row>
							))}
						</Body>
					</table>
				</div>

				<div className="bg-neutral-0 box-info d-flex ml-1 rounded w-50">
					<li className="bg-neutral-0 box-info d-flex flex-column float-right rounded w-100">
						<div>
							<p className="font-table ml-0 pt-4">
								{selectedRow.message}
							</p>
						</div>
					</li>
				</div>
			</div>
		</div>
	);
};

export default TableList;
