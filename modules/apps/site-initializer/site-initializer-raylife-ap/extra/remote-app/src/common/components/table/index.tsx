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

import Button, {ClayButtonWithIcon} from '@clayui/button';
import ClayTable from '@clayui/table';
import classnames from 'classnames';

import SettingsButton, {ActionObject} from '../settings-button';

type sort = {
	[keys: string]: boolean;
};

type TableRowContentType = {[keys: string]: string};

type TableHeaders = {
	bold?: boolean;
	centered?: boolean;
	clickable?: boolean;
	clickableSort?: boolean;
	greyColor?: boolean;
	hasSort?: boolean;
	icon?: boolean;
	key: string;
	redColor?: boolean;
	requestLabel: string;
	type?: string;
	value: string;
};

type TableProps = {
	actions: ActionObject[];
	data: {[keys: string]: string}[];
	headers: TableHeaders[];
	onClickRules?: (
		item: TableHeaders,
		rowContent: TableRowContentType
	) => void;
	onSaveCurrent?: (item: string) => void;
	setSort?: (item: sort) => void;
	setSortByOrder?: (item: string) => void;
	sort?: sort;
	sortByOrder?: string;
	valuer?: string;
};

enum Order {
	Ascendant = 'asc',
	Descendant = 'desc',
}

const {Body, Cell, Head, Row} = ClayTable;

const Table: React.FC<TableProps> = ({
	data,
	headers,
	actions,
	setSortByOrder,
	setSort,
	sort,
	sortByOrder,
	onClickRules = () => null,
	onSaveCurrent,
}) => {
	const setCurrentHeaderPlus = (item: string) => {
		onSaveCurrent?.(item);
	};

	const updateSort = (colunn: string) => {
		const newSort: sort = {};

		headers.forEach((item) => {
			if (item.requestLabel === colunn) {
				// eslint-disable-next-line no-return-assign
				return (newSort[item.key] = true);
			}
			newSort[item.key] = false;
		});

		setSort?.(newSort);
	};

	return (
		<table className="border-0 ray-table show-quick-actions-on-hover table table-autofit table-list table-responsive">
			<Head>
				<Row className="ray-table-head">
					{headers.map((header: TableHeaders, index: number) => (
						<Cell
							className="py-0 text-paragraph-sm"
							headingCell
							key={index}
						>
							{headers[index].clickableSort && (
								<Button
									displayType="unstyled"
									onClick={() => {
										updateSort(headers[index].requestLabel);
										setCurrentHeaderPlus(
											headers[index].requestLabel
										);

										setSortByOrder;
									}}
								>
									{header.value}
								</Button>
							)}

							{!headers[index].clickableSort && (
								<Button displayType="unstyled">
									{header.value}
								</Button>
							)}

							{sort?.[header.key] && (
								<ClayButtonWithIcon
									className="bg-neutral-0 btn-sm text-brand-primary-darken-1"
									symbol={
										sortByOrder === Order.Ascendant
											? 'order-arrow-up'
											: 'order-arrow-down'
									}
								></ClayButtonWithIcon>
							)}
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
											item.type === 'hasBubble',
										'text-center': item.centered,
									})}
								>
									{item.type === 'hasBubble' && (
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
