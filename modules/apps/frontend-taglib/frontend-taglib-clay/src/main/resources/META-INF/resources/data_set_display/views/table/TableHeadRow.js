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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayTable from '@clayui/table';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useState} from 'react';

import DataSetDisplayContext from '../../DataSetDisplayContext';
import Checkbox from '../../data_renderers/CheckboxRenderer';
import persistVisibleFieldNames from '../../thunks/persistVisibleFieldNames';
import ViewsContext from '../ViewsContext';

const FieldsSelectorDropdown = ({fields}) => {
	const {id} = useContext(DataSetDisplayContext);
	const [{visibleFieldNames}, dispatch] = useContext(ViewsContext);

	const [active, setActive] = useState(false);
	const [filteredFields, setFilteredFields] = useState(fields);
	const [query, setQuery] = useState('');

	useEffect(() => {
		setFilteredFields(
			fields.filter((field) =>
				field.label.toLowerCase().includes(query.toLowerCase())
			)
		);
	}, [fields, query]);

	return (
		<ClayDropDown
			active={active}
			className="data-set-fields-selector-dropdown"
			onActiveChange={setActive}
			trigger={
				<ClayButtonWithIcon
					borderless
					className="p-0"
					displayType="secondary"
					monospaced={false}
					symbol={active ? 'caret-top' : 'caret-bottom'}
				/>
			}
		>
			<ClayDropDown.Search
				onChange={(event) => setQuery(event.target.value)}
				value={query}
			/>
			{filteredFields.length ? (
				<ClayDropDown.ItemList>
					{filteredFields.map(({fieldName, label}) => (
						<ClayDropDown.Item
							key={fieldName}
							onClick={() => {
								dispatch(
									persistVisibleFieldNames({
										id,
										visibleFieldNames: {
											...visibleFieldNames,
											[fieldName]: !visibleFieldNames[
												fieldName
											],
										},
									})
								);
							}}
						>
							{visibleFieldNames[fieldName] && (
								<ClayIcon symbol="check" />
							)}
							{label}
						</ClayDropDown.Item>
					))}
				</ClayDropDown.ItemList>
			) : (
				<div className="dropdown-section text-muted">
					{Liferay.Language.get('no-fields-were-found')}
				</div>
			)}
		</ClayDropDown>
	);
};

function TableHeadCell({
	contentRenderer,
	expand,
	expandableColumns,
	fieldName,
	label,
	sortable,
	sorting,
	sortingKey: sortingKeyProp,
	updateSorting,
}) {
	const sortingKey =
		sortingKeyProp || (Array.isArray(fieldName) ? fieldName[0] : fieldName);

	const sortingMatch = sorting.find((element) => element.key === sortingKey);

	function handleSortingCellClick(event) {
		event.preventDefault();

		if (sortingMatch) {
			const updatedSortedElements = sorting.map((element) =>
				element.key === sortingKey
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
					fieldName,
					key: sortingKey,
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
					data-senna-off
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
							symbol="order-arrow-up"
						/>
						<ClayIcon
							className={classNames(
								'sorting-icon',
								sortingMatch?.direction === 'desc' && 'active'
							)}
							draggable
							symbol="order-arrow-down"
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
				{visibleFields.map((field, i) => (
					<TableHeadCell
						{...field}
						expandableColumns={expandableColumns}
						key={field.sortingKey || i}
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
	sorting: PropTypes.arrayOf(
		PropTypes.shape({
			direction: PropTypes.oneOf(['asc', 'desc']).isRequired,
			fieldName: PropTypes.oneOfType([PropTypes.string, PropTypes.array]),
		})
	),
};

export default TableHeadRow;
