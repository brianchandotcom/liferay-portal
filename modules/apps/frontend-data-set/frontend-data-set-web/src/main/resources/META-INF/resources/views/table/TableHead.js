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

import {ClayCheckbox} from '@clayui/form';
import PropTypes from 'prop-types';
import React from 'react';

import FieldsSelectorDropdown from './FieldsSelectorDropdown';
import TableHeadCell from './TableHeadCell';
import DndTable from './dnd_table/index';

function TableHead({
	fields,
	items,
	schema,
	selectItems,
	selectable,
	selectedItemsKey,
	selectedItemsValue,
	selectionType,
}) {
	function handleCheckboxClick() {
		if (selectedItemsValue.length === items.length) {
			return selectItems([]);
		}

		return selectItems(items.map((item) => item[selectedItemsKey]));
	}

	return (
		<DndTable.Head>
			<DndTable.Row>
				{selectable && (
					<DndTable.Cell
						className="item-selector"
						columnName="item-selector"
						heading
					>
						{!!items.length && selectionType === 'multiple' ? (
							<ClayCheckbox
								checked={!!selectedItemsValue.length}
								indeterminate={
									!!selectedItemsValue.length &&
									items.length !== selectedItemsValue.length
								}
								name="table-head-selector"
								onChange={handleCheckboxClick}
								title={
									items.length !== selectedItemsValue.length
										? Liferay.Language.get('select-items')
										: Liferay.Language.get(
												'clear-selection'
										  )
								}
							/>
						) : null}
					</DndTable.Cell>
				)}

				{fields.map((field) => (
					<TableHeadCell {...field} key={field.label} />
				))}

				<DndTable.Cell
					className="item-actions"
					columnName="item-actions"
					heading
				>
					<FieldsSelectorDropdown fields={schema.fields} />
				</DndTable.Cell>
			</DndTable.Row>
		</DndTable.Head>
	);
}

TableHead.propTypes = {
	fields: PropTypes.array,
	items: PropTypes.array,
	schema: PropTypes.shape({
		fields: PropTypes.any,
	}),
	selectItems: PropTypes.func,
	selectable: PropTypes.bool,
	selectedItemsKey: PropTypes.string,
	selectedItemsValue: PropTypes.arrayOf(
		PropTypes.oneOfType([PropTypes.string, PropTypes.number])
	),
	selectionType: PropTypes.oneOf(['single', 'multiple']),
};

export default TableHead;
