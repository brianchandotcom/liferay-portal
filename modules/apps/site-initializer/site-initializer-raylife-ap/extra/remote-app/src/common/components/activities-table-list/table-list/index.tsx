/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTable from '@clayui/table';
import classnames from 'classnames';
import React, {useState} from 'react';

const {Body, Cell, Head, Row} = ClayTable;

export type TableListHeaders = {
	bold?: boolean;
	key: string;
	value: string;
};

export type Props = {
	BodyElement?: () => void;
	headers: TableListHeaders[];
	rows: InfoRowContent[];
};

type InfoRowContent = {[keys: string]: string | boolean};

const TableListComponent: React.FC<Props> = ({
	BodyElement = () => null,
	headers,
	rows,
}) => {
	const [selectedRow, setSelectedRow] = useState(rows[0]);

	const toggleRowContent = (item: InfoRowContent) => {
		setSelectedRow(item);
	};

	return (
		<div className="table-list-container w-100">
			<div className="bg-neutral-0 d-flex rounded rounded-top w-100">
				<div className="align-items-center box-activites col d-flex py-2">
					<h2 className="border-link-active font ml-3 mt-2">
						Activities
					</h2>
				</div>

				<div className="align-items-center blue-line-activites border border-bottom box-activites col d-flex position-relative py-2">
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
									(
										header: TableListHeaders,
										index: number
									) => (
										<Cell
											className="pb-5 pt-3 px-3 text-paragraph-sm"
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
										'cursor-pointer position-relative py-2',
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
											header: TableListHeaders,
											index: number
										) => (
											<Cell key={index}>
												<div className="p-3">
													<span
														className={classnames(
															'd-flex w-100',
															{
																'font-table text-nowrap': !header.bold,
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
							<p className="font-table ml-0 pl-3 pt-4">
								{selectedRow.message}
							</p>

							{selectedRow.body && BodyElement()}
						</div>
					</li>
				</div>
			</div>
		</div>
	);
};

export default TableListComponent;
