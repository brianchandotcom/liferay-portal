/* eslint-disable no-console */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayTable from '@clayui/table';
import React from 'react';

import ClayIconProvider from '../../context/ClayIconProvider';

import './index.css';

const {Body, Cell, Head, Row} = ClayTable;

const Table = ({data, headers = []}) => {
	return (
		<table className="border-0 general-table mb-4 show-quick-actions-on-hover table table-autofit table-list">
			<ClayIconProvider>
				<Head>
					<Row>
						{headers.map((header, index) => (
							<Cell
								className="header py-0 text-paragraph-sm"
								headingCell
								key={index}
							>
								{header.value}
							</Cell>
						))}

						<Cell className="header py-0" headingCell></Cell>
					</Row>
				</Head>

				<Body>
					{data.map((rowContent, rowIndex) => (
						<Row key={rowIndex}>
							{headers.map((item, index) => (
								<Cell className="border-top-0" key={index}>
									{rowContent[item.key]}
								</Cell>
							))}

							<Cell className="border-top-0 top-button-border">
								<div className="bg-neutral-0 border-neutral-0 btn btn-inverted btn-solid btn-style-secondary btn-table d-flex justify-content-end">
									<span className="text-paragraph">
										<ClayIcon symbol="angle-right" />
									</span>
								</div>
							</Cell>
						</Row>
					))}
				</Body>
			</ClayIconProvider>
		</table>
	);
};

export default Table;
