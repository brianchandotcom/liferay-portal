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

import ClayIcon from '@clayui/icon';
import ClayTable from '@clayui/table';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

import Checkbox from '../../data_renderer/CheckboxRenderer';

function TableHeadCell({
	contentRenderer,
	expand,
	expandableColumns,
	fieldName,
	label,
	sortable,
	sorting,
	updateSorting,
}) {
	const sortingMatch = sorting.find(
		(element) => element.fieldName === fieldName
	);

	function handleSortingCellClick(event) {
		event.preventDefault();

		if (sortingMatch) {
			const updatedSortedElements = sorting.map((element) =>
				element.fieldName === fieldName
					? {
							...element,
							direction:
								element.direction === 'asc' ? 'desc' : 'asc',
					  }
					: element
			);
			updateSorting(updatedSortedElements);
		}
		else {
			updateSorting([
				{
					direction: 'asc',
					fieldName,
				},
			]);
		}
	}

	return (
		<ClayTable.Cell
			className={classNames(
				contentRenderer && `content-renderer-${contentRenderer}`,
				expandableColumns
					? expand && 'table-cell-expand-small'
					: 'table-cell-expand-smaller'
			)}
			headingCell
			headingTitle
		>
			{sortable ? (
				<a
					className="inline-item text-nowrap text-truncate-inline"
					href="#"
					onClick={handleSortingCellClick}
				>
					{label || ''}
					<span className="inline-item inline-item-after sorting-icons-wrapper">
						<ClayIcon
							className={classNames(
								'sorting-icon',
								sortingMatch?.direction === 'asc' && 'active'
							)}
							draggable
							symbol={'order-arrow-up'}
						/>
						<ClayIcon
							className={classNames(
								'sorting-icon',
								sortingMatch?.direction === 'desc' && 'active'
							)}
							draggable
							symbol={'order-arrow-down'}
						/>
					</span>
				</a>
			) : (
				label || ''
			)}
		</ClayTable.Cell>
	);
}

function TableHeadRow({
	items,
	schema,
	selectItems,
	selectable,
	selectedItemsKey,
	selectedItemsValue,
	selectionType,
	showActionItems,
	sorting,
	updateSorting,
}) {
	const getColumns = (fields) => {
		const expandableColumns = fields.reduce(
			(expandable, field) => expandable || Boolean(field.expand),
			false
		);

		return fields.map((field, i) => {
			return (
				<TableHeadCell
					{...field}
					expandableColumns={expandableColumns}
					key={field.fieldName || i}
					sorting={sorting}
					updateSorting={updateSorting}
				/>
			);
		});
	};

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
							<Checkbox
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
				{getColumns(schema.fields)}
				{showActionItems && <ClayTable.Cell headingCell />}
			</ClayTable.Row>
		</ClayTable.Head>
	);
}

TableHeadRow.propTypes = {
	items: PropTypes.array,
	schema: PropTypes.shape({
		fields: PropTypes.arrayOf(
			PropTypes.shape({
				contentRenderer: PropTypes.string,
				expand: PropTypes.bool,
				fieldName: PropTypes.oneOfType([
					PropTypes.string,
					PropTypes.array,
				]),
				label: PropTypes.string,
				sortable: PropTypes.bool,
			}).isRequired
		),
	}),
	selectedItemsValue: PropTypes.arrayOf(
		PropTypes.oneOfType([PropTypes.string, PropTypes.number])
	),
	selectionType: PropTypes.oneOf(['single', 'multiple']),
	showActionItems: PropTypes.bool,
	sorting: PropTypes.arrayOf(
		PropTypes.shape({
			direction: PropTypes.oneOf(['asc', 'desc']).isRequired,
			fieldName: PropTypes.oneOfType([PropTypes.string, PropTypes.array]),
		})
	),
};

export default TableHeadRow;
