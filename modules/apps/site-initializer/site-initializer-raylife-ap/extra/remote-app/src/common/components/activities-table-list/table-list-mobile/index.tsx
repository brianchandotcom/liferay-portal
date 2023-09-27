/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTable from '@clayui/table';
import classnames from 'classnames';
import React, {useEffect, useState} from 'react';

import PanelComponent from '../../panel';
import {Props, TableListHeaders} from '../table-list';

const {Body, Cell, Head, Row} = ClayTable;

const TableListMobileComponent: React.FC<Props> = ({headers, rows}) => {
	const [isPanelExpanded, setIsPanelExpanded] = useState<boolean[]>([]);
	const [isRowSelected, setIsRowSelected] = useState<boolean>(false);

	const ContentDescription = ({
		columnOne,
		columnTwo,
	}: {
		columnOne: string;
		columnTwo: string;
	}) => (
		<div className="d-flex ml-3 my-3 w-100">
			<div className="d-flex font-table ml-2 mr-5 text-nowrap w-50">
				{columnOne}
			</div>

			<div className="font-table-bold text-nowrap">{columnTwo}</div>
		</div>
	);

	const toggleSelectedRow = (index: number) => {
		isPanelExpanded[index] === false
			? setIsRowSelected(true)
			: setIsRowSelected(false);
	};

	const resetExpandedPanel = rows.map(() => {
		return false;
	});

	const displayHistoryPanel = (index: number) => {
		const supportArray = [...isPanelExpanded];
		toggleSelectedRow(index);

		if (isRowSelected === isPanelExpanded[index]) {
			supportArray[index] = !supportArray[index];
			setIsPanelExpanded(supportArray);
		}
		else {
			resetExpandedPanel[index] = !resetExpandedPanel[index];
			setIsPanelExpanded(resetExpandedPanel);
		}
	};

	useEffect(() => {
		setIsPanelExpanded(resetExpandedPanel);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<div className="table-list-container">
			<div className="w-100">
				<div className="bg-neutral-0 col d-flex py-3">
					<h2 className="border-link-active font ml-3 mt-2">
						Activities
					</h2>
				</div>

				<table className="border-right box-table-mobile w-100">
					<Head>
						<Row className="bg-neutral-0 border border-header">
							{headers.map(
								(header: TableListHeaders, index: number) => (
									<Cell
										className="pl-4 py-3 text-paragraph-sm"
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
						<div className="h-100 w-100">
							{rows.map((row, index: number) => {
								return (
									<Row key={index}>
										<div
											className={classnames(
												'cursor-pointer bg-neutral-0 position-relative',
												{
													'dotted-line':
														isPanelExpanded[
															index
														] === false,
													'mb-2':
														isPanelExpanded[
															index
														] === true,
												}
											)}
											key={index}
										>
											<PanelComponent
												Description={
													<ContentDescription
														columnOne={
															row.date as string
														}
														columnTwo={
															row.activity as string
														}
													/>
												}
												hasExpandedButton={false}
												isPanelExpanded={
													isPanelExpanded[index]
												}
												key={index}
												setIsPanelExpanded={() =>
													displayHistoryPanel(index)
												}
											>
												<div className="bg-message bg-neutral-0 d-flex font-table px-3 py-4 rounded">
													{row.message}
												</div>
											</PanelComponent>
										</div>
									</Row>
								);
							})}
						</div>
					</Body>
				</table>
			</div>
		</div>
	);
};

export default TableListMobileComponent;
