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
import ClayTable from '@clayui/table';
import PropTypes from 'prop-types';
import React from 'react';

import FieldsSelectorDropdown from './FieldsSelectorDropdown';
import TableHeadCell from './TableHeadCell';

function TableHeadRow({
	items,
	schema,
	selectItems,
	selectable,
	selectedItemsKey,
	selectedItemsValue,
	selectionType,
	sorting,
	updateSorting,
	visibleFields,
}) {
	const expandableColumns = visibleFields.some((field) => field.expand);

	function handleCheckboxClick() {
		if (selectedItemsValue.length === items.length) {
			return selectItems([]);
		}

		return selectItems(items.map((item) => item[selectedItemsKey]));
	}

	return (
		<ClayTable.Head>
			<ClayTable.Row>
				{selectable && (
					<ClayTable.Cell headingCell>
						{items.length && selectionType === 'multiple' ? (
							<ClayCheckbox
								checked={!!selectedItemsValue.length}
								indeterminate={
									!!selectedItemsValue.length &&
									items.length !== selectedItemsValue.length
								}
								name="table-head-selector"
								onChange={handleCheckboxClick}
							/>
						) : null}
					</ClayTable.Cell>
				)}
				{visibleFields.map((field) => (
					<TableHeadCell
						{...field}
						expandableColumns={expandableColumns}
						key={field.label}
						sorting={sorting}
						updateSorting={updateSorting}
					/>
				))}
				<ClayTable.Cell className="text-right" headingCell>
					<FieldsSelectorDropdown fields={schema.fields} />
				</ClayTable.Cell>
			</ClayTable.Row>
		</ClayTable.Head>
	);
}

TableHeadRow.propTypes = {
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
	sorting: PropTypes.any,
	updateSorting: PropTypes.func.isRequired,
	visibleFields: PropTypes.arrayOf(
		PropTypes.shape({
			expand: PropTypes.bool,
		})
	),
};

export default TableHeadRow;
