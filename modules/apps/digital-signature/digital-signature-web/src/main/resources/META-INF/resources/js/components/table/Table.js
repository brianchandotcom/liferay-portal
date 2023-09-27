/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';
import ClayTable from '@clayui/table';
import {sub} from 'frontend-js-web';
import React from 'react';

import DropDown from './DropDown';

const CellWrapper = ({children, colSpan = 1, fieldAlign, index}) => (
	<ClayTable.Cell
		align={index === 0 ? 'left' : fieldAlign}
		className={index > 0 && 'table-cell-expand-smaller'}
		colSpan={colSpan}
		expanded={index === 0}
		headingTitle={index === 0}
		key={index}
	>
		{children}
	</ClayTable.Cell>
);

const Table = ({
	actions,
	align = 'left',
	checkable,
	columns,
	forwardRef,
	items,
	noActionsMessage,
}) => {
	return (
		<div ref={forwardRef}>
			<ClayTable className="thead-valign-top" hover={false}>
				<ClayTable.Head>
					<ClayTable.Row>
						{checkable && (
							<ClayTable.Cell headingCell></ClayTable.Cell>
						)}

						{columns.map((column, index) => (
							<ClayTable.Cell
								align={index === 0 ? 'left' : align}
								className={
									index > 0 && 'table-cell-expand-smaller'
								}
								expanded={index === 0}
								headingCell
								key={index}
							>
								{column.value}
							</ClayTable.Cell>
						))}

						{actions && (
							<ClayTable.Cell headingCell></ClayTable.Cell>
						)}
					</ClayTable.Row>
				</ClayTable.Head>

				<ClayTable.Body>
					{items.map((item, index) => (
						<ClayTable.Row key={index}>
							{checkable && (
								<ClayTable.Cell>
									<ClayCheckbox
										aria-label={sub(
											Liferay.Language.get('select-x'),
											item.name.props.children
										)}
										checked={false}
										disabled={false}
										indeterminate={false}
										onChange={() => {}}
									/>
								</ClayTable.Cell>
							)}

							{columns.map((column, index) => (
								<CellWrapper
									fieldAlign={align}
									index={index}
									key={index}
								>
									{item[column.key]}
								</CellWrapper>
							))}

							{actions && (
								<ClayTable.Cell>
									<DropDown
										actions={actions}
										item={item}
										noActionsMessage={noActionsMessage}
									/>
								</ClayTable.Cell>
							)}
						</ClayTable.Row>
					))}
				</ClayTable.Body>
			</ClayTable>
		</div>
	);
};

export default React.forwardRef((props, ref) => (
	<Table {...props} forwardRef={ref} />
));
