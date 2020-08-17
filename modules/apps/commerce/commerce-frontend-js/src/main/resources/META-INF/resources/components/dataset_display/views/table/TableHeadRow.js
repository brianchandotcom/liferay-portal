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
import React, {useContext} from 'react';

import Checkbox from '../../../data_renderers/CheckboxRenderer';
import DatasetDisplayContext from '../../DatasetDisplayContext';

function TableHeadCell(props) {
	const sortingKey =
		props.sortingKey ||
		(Array.isArray(props.fieldName) ? props.fieldName[0] : props.fieldName);

	const sortingMatch = props.sorting.find((el) => el.key === sortingKey);

	function handleSortingCellClick(e) {
		e.preventDefault();

		if (sortingMatch) {
			const updatedSortedElements = props.sorting.map((el) =>
				el.key === sortingKey
					? {
							...el,
							direction: el.direction === 'asc' ? 'desc' : 'asc',
					  }
					: el
			);
			props.updateSorting(updatedSortedElements);
		}
		else {
			props.updateSorting([
				{
					direction: 'asc',
					key: sortingKey,
				},
			]);
		}
	}

	return (
		<ClayTable.Cell
			className={classNames(
				props.contentRenderer &&
					`content-renderer-${props.contentRenderer}`,
				props.expandableColumns
					? props.expand && 'table-cell-expand-small'
					: 'table-cell-expand-smaller'
			)}
			headingCell
			headingTitle
		>
			{props.sortable ? (
				<a
					className="inline-item text-nowrap text-truncate-inline"
					data-senna-off
					href="#"
					onClick={handleSortingCellClick}
				>
					{props.label || ''}
					<span className="inline-item inline-item-after sorting-icons-wrapper">
						<ClayIcon
							className={classNames(
								'sorting-icon',
								sortingMatch &&
									sortingMatch.direction === 'asc' &&
									'active'
							)}
							draggable
							symbol="order-arrow-up"
						/>
						<ClayIcon
							className={classNames(
								'sorting-icon',
								sortingMatch &&
									sortingMatch.direction === 'desc' &&
									'active'
							)}
							draggable
							symbol="order-arrow-down"
						/>
					</span>
				</a>
			) : (
				props.label || ''
			)}
		</ClayTable.Cell>
	);
}

function TableHeadRow(props) {
	const {actionLoading} = useContext(DatasetDisplayContext);

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
					key={field.sortingKey || field.fieldName || i}
					sorting={props.sorting}
					updateSorting={props.updateSorting}
				/>
			);
		});
	};

	function handleCheckboxClick() {
		if (props.selectedItemsValue.length === props.items.length) {
			return props.selectItems([]);
		}

		return props.selectItems(
			props.items.map((item) => item[props.selectedItemsKey])
		);
	}

	return (
		<ClayTable.Head>
			<ClayTable.Row>
				{props.selectable && (
					<ClayTable.Cell headingCell>
						{props.items.length &&
						props.selectionType === 'multiple' ? (
							<Checkbox
								checked={!!props.selectedItemsValue.length}
								disabled={actionLoading}
								indeterminate={
									!!props.selectedItemsValue.length &&
									props.items.length !==
										props.selectedItemsValue.length
								}
								name="table-head-selector"
								onChange={handleCheckboxClick}
							/>
						) : null}
					</ClayTable.Cell>
				)}
				{getColumns(props.schema.fields)}
				{props.showActionItems && <ClayTable.Cell headingCell />}
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
					PropTypes.arrayOf(PropTypes.string),
				]),
				label: PropTypes.string,
				sortable: PropTypes.bool,
				sortingKey: PropTypes.string,
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
