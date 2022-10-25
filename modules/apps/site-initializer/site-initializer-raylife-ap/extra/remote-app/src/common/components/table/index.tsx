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

import SettingsButton, {ActionObject} from '../settings-button';

const {Body, Cell, Head, Row} = ClayTable;

type TableProps = {
	actions: ActionObject[];
	data: {[keys: string]: string}[];
	headers: TableHeaders[];
	onClickRules?: (item: any, rowContent: any) => void;
};

type TableHeaders = {
	bold?: boolean;
	clickable?: boolean;
	greyColor?: boolean;
	key: string;
	redColor?: boolean;
	type?: string;
	value: string;
};

const Table: React.FC<TableProps> = ({
	data,
	headers,
	actions,
	onClickRules = () => null,
}) => {
	return (
		<table className="border-0 ray-table show-quick-actions-on-hover table table-autofit table-list table-responsive">
			<Head>
				<Row>
					{headers.map((header, index) => (
						<Cell
							className="py-0 text-paragraph-sm"
							headingCell
							key={index}
						>
							{header.value}
						</Cell>
					))}

					{!!actions.length && (
						<Cell className="py-0" headingCell></Cell>
					)}
				</Row>
			</Head>

			<Body>
				{data.map((rowContent, rowIndex) => (
					<Row key={rowIndex}>
						{headers.map((item, index) => (
							<Cell
								className={classnames('border-top-0', {
									'ray-row-table-danger':
										rowContent.isRedLine === 'true',
								})}
								key={index}
							>
								<div
									className={classnames({
										'align-items-center d-flex':
											item.type === 'status',
									})}
								>
									{item.type === 'status' && (
										<div
											className={`${rowContent[
												item.key
											].toLowerCase()} flex-shrink-0 mr-2 rounded-circle status-color`}
										></div>
									)}

									<span
										className={classnames('', {
											'cursor-pointer': !!item.clickable,
											'font-weight-bolder': !!item.bold,
											'text-danger font-weight-bolder':
												Number(rowContent[item.key]) <
													15 ||
												rowContent[item.key] ===
													'Due Today',
											'text-neutral-7': !!item.greyColor,
										})}
										onClick={() => {
											onClickRules(item, rowContent);
										}}
									>
										{rowContent[item.key]}
									</span>
								</div>
							</Cell>
						))}

						{!!actions.length && (
							<Cell
								className={classnames('border-top-0', {
									'ray-row-table-danger':
										rowContent.isRedLine === 'true',
								})}
							>
								<SettingsButton
									actions={actions}
									identifier={rowContent.key}
								/>
							</Cell>
						)}
					</Row>
				))}
			</Body>
		</table>
	);
};

export default Table;
