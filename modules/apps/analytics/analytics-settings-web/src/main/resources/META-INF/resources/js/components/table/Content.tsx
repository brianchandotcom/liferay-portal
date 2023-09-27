/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayTable from '@clayui/table';
import classNames from 'classnames';
import React from 'react';

import {OrderBy} from '../../utils/filter';
import {Events, useData, useDispatch} from './Context';
import {EColumnAlign, TColumn} from './types';
interface IContentProps {
	columns: TColumn[];
	disabled: boolean;
	showCheckbox: boolean;
}

const Content: React.FC<IContentProps> = ({
	columns: headerColumns,
	disabled,
	showCheckbox,
}) => {
	const {filter, formattedItems, rows} = useData();
	const dispatch = useDispatch();

	return (
		<ClayTable className="compose-table" hover={!disabled}>
			<ClayTable.Head>
				<ClayTable.Row>
					{showCheckbox && <ClayTable.Cell />}

					{headerColumns.map(
						({
							align = EColumnAlign.Left,
							expanded = false,
							id,
							label,
							show = true,
						}) =>
							show && (
								<ClayTable.Cell
									columnTextAlignment={align}
									expanded={expanded}
									headingCell
									key={id}
								>
									<span>{label}</span>

									{filter.value === id && (
										<span>
											<ClayIcon
												symbol={
													filter.type === OrderBy.Asc
														? 'order-arrow-up'
														: 'order-arrow-down'
												}
											/>
										</span>
									)}
								</ClayTable.Cell>
							)
					)}
				</ClayTable.Row>
			</ClayTable.Head>

			<ClayTable.Body>
				{rows.map((rowId) => {
					const {
						checked = false,
						columns,
						disabled: disabledItem = false,
					} = formattedItems[rowId];

					return (
						<ClayTable.Row
							className={classNames({
								'table-active': checked,
							})}
							data-testid={columns[0].value}
							key={rowId}
						>
							{showCheckbox && (
								<ClayTable.Cell
									className={classNames({
										'text-muted': disabled || disabledItem,
									})}
								>
									<ClayCheckbox
										checked={checked}
										disabled={disabled || disabledItem}
										id={rowId}
										onChange={() => {
											if (!disabled && !disabledItem) {
												dispatch({
													payload: rowId,
													type: Events.ChangeItems,
												});
											}
										}}
									/>
								</ClayTable.Cell>
							)}

							{columns.map(({cellRenderer, id, value}, index) => {
								const {
									align = EColumnAlign.Left,
									show = true,
								} = headerColumns[index];

								return (
									show && (
										<ClayTable.Cell
											className={classNames({
												'text-muted':
													disabled || disabledItem,
											})}
											columnTextAlignment={align}
											key={id}
											role={rowId}
										>
											{cellRenderer
												? cellRenderer(
														formattedItems[rowId]
												  )
												: value}
										</ClayTable.Cell>
									)
								);
							})}
						</ClayTable.Row>
					);
				})}
			</ClayTable.Body>
		</ClayTable>
	);
};

export default Content;
