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
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useState, useEffect, useContext} from 'react';

import {getValueFromItem} from '../../../../utilities/index.es';
import DatasetDisplayContext from '../../DatasetDisplayContext.es';
import ActionsDropdownRenderer from '../../data_renderer/ActionsDropdownRenderer';
import CheckboxRenderer from '../../data_renderer/CheckboxRenderer';
import CommentRenderer from '../../data_renderer/CommentRenderer';
import RadioRenderer from '../../data_renderer/RadioRenderer';
import {
	getDataRendererById,
	getDataRendererByUrl
} from '../../data_renderer/index';
import TableHeadRow from './TableHeadRow.es';

function CustomTableCell(props) {
	const {view} = props;
	const [currentView, updateCurrentView] = useState({
		...view,
		Component: view.contentRendererModuleUrl
			? null
			: getDataRendererById(view.contentRenderer)
	});
	const [loading, setLoading] = useState(false);

	useEffect(() => {
		if (loading) {
			return;
		}
		if (currentView.contentRendererModuleUrl) {
			setLoading(true);
			getDataRendererByUrl(currentView.contentRendererModuleUrl).then(
				Component => {
					updateCurrentView({
						...currentView,
						Component
					});
					setLoading(false);
				}
			);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [currentView]);

	return (
		<ClayTable.Cell>
			{currentView.Component && !loading ? (
				<currentView.Component
					actions={props.actions}
					itemData={props.itemData}
					itemId={props.itemId}
					options={props.options}
					value={props.value}
				/>
			) : (
				<span
					aria-hidden="true"
					className="loading-animation loading-animation-sm"
				/>
			)}
			{props.comment && (
				<CommentRenderer>{props.comment}</CommentRenderer>
			)}
		</ClayTable.Cell>
	);
}

function Table(props) {
	const {
		highlightedItemsValue,
		selectItems,
		selectable,
		selectedItemsKey,
		selectedItemsValue,
		selectionType,
		sorting,
		updateSorting
	} = useContext(DatasetDisplayContext);

	const showActionItems = Boolean(
		(props.itemActions && props.itemActions.length) ||
			props.items.find(el => el.actionItems)
	);

	const SelectionComponent =
		selectionType === 'multiple' ? CheckboxRenderer : RadioRenderer;

	return (
		<div className={`table-style-${props.style}`}>
			<ClayTable borderless hover={false} responsive={false}>
				<TableHeadRow
					items={props.items}
					schema={props.schema}
					selectItems={selectItems}
					selectable={selectable}
					selectedItemsKey={selectedItemsKey}
					selectedItemsValue={selectedItemsValue}
					selectionType={selectionType}
					showActionItems={showActionItems}
					sorting={sorting}
					updateSorting={updateSorting}
				/>
				<ClayTable.Body>
					{props.items.map((item, i) => {
						const itemId = item[selectedItemsKey] || i;

						return (
							<ClayTable.Row
								className={classNames(
									highlightedItemsValue.includes(itemId) &&
										'active'
								)}
								key={itemId}
							>
								{selectable && (
									<ClayTable.Cell>
										<SelectionComponent
											checked={
												!!selectedItemsValue.find(
													el =>
														String(el) ===
														String(itemId)
												)
											}
											onChange={() => selectItems(itemId)}
											value={itemId}
										/>
									</ClayTable.Cell>
								)}
								{props.schema.fields.map((field, i) => {
									const fieldName = field.fieldName;
									const {actionItems, ...otherProps} = item;
									const rawValue = getValueFromItem(
										item,
										fieldName
									);
									const formattedValue = field.mapData
										? field.mapData(rawValue)
										: rawValue;
									const comment = otherProps.comments
										? otherProps.comments[field.fieldName]
										: null;
									return (
										<CustomTableCell
											actions={
												props.itemActions || actionItems
											}
											comment={comment}
											itemData={item}
											itemId={itemId}
											key={fieldName || i}
											options={field}
											value={formattedValue}
											view={{
												contentRenderer:
													field.contentRenderer,
												contentRendererModuleUrl:
													field.contentRendererModuleUrl
											}}
										/>
									);
								})}
								{showActionItems ? (
									props.itemActions || item.actionItems ? (
										<ClayTable.Cell className="text-right">
											<ActionsDropdownRenderer
												actions={
													props.itemActions ||
													item.actionItems
												}
												itemData={item}
												itemId={itemId}
											/>
										</ClayTable.Cell>
									) : (
										<ClayTable.Cell />
									)
								) : null}
							</ClayTable.Row>
						);
					})}
				</ClayTable.Body>
			</ClayTable>
		</div>
	);
}

Table.propTypes = {
	itemActions: PropTypes.array,
	items: PropTypes.arrayOf(PropTypes.object),
	schema: PropTypes.shape({
		fields: PropTypes.arrayOf(
			PropTypes.shape({
				fieldName: PropTypes.oneOfType([
					PropTypes.string,
					PropTypes.array
				]).isRequired,
				mapData: PropTypes.func
			})
		).isRequired
	}).isRequired,
	style: PropTypes.string.isRequired
};

Table.defaultProps = {
	items: []
};

export default Table;
