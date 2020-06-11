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
import React, {useContext, useEffect, useState} from 'react';

import DatasetDisplayContext from '../../DatasetDisplayContext';
import ActionsDropdownRenderer from '../../data_renderer/ActionsDropdownRenderer';
import CheckboxRenderer from '../../data_renderer/CheckboxRenderer';
import CommentRenderer from '../../data_renderer/CommentRenderer';
import RadioRenderer from '../../data_renderer/RadioRenderer';
import {
	getDataRendererById,
	getDataRendererByUrl,
} from '../../data_renderer/index';
import {getValueFromItem} from '../../utilities/index';
import TableHeadRow from './TableHeadRow';

function CustomTableCell({
	actions,
	comment,
	itemData,
	itemId,
	options,
	value,
	view,
}) {
	const [currentView, updateCurrentView] = useState({
		...view,
		Component: view.contentRendererModuleUrl
			? null
			: getDataRendererById(view.contentRenderer),
	});
	const [loading, setLoading] = useState(false);

	useEffect(() => {
		if (loading) {
			return;
		}
		if (currentView.contentRendererModuleUrl) {
			setLoading(true);
			getDataRendererByUrl(currentView.contentRendererModuleUrl).then(
				(Component) => {
					updateCurrentView({
						...currentView,
						Component,
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
					actions={actions}
					itemData={itemData}
					itemId={itemId}
					options={options}
					value={value}
				/>
			) : (
				<span
					aria-hidden="true"
					className="loading-animation loading-animation-sm"
				/>
			)}
			{comment && <CommentRenderer>{comment}</CommentRenderer>}
		</ClayTable.Cell>
	);
}

function getItemFields(item, fields, itemId, itemActions) {
	return fields.map((field, i) => {
		const fieldName = field.fieldName;
		const {actionItems, ...otherProps} = item;
		const rawValue = getValueFromItem(item, fieldName);
		const formattedValue = field.mapData
			? field.mapData(rawValue)
			: rawValue;
		const comment = otherProps.comments
			? otherProps.comments[field.fieldName]
			: null;

		return (
			<CustomTableCell
				actions={itemActions || actionItems}
				comment={comment}
				itemData={item}
				itemId={itemId}
				key={fieldName || i}
				options={field}
				value={formattedValue}
				view={{
					contentRenderer: field.contentRenderer,
					contentRendererModuleUrl: field.contentRendererModuleUrl,
				}}
			/>
		);
	});
}

function Table({itemActions, items, schema, style}) {
	const {
		highlightedItemsValue,
		nestedItemsKey,
		nestedItemsReferenceKey,
		selectItems,
		selectable,
		selectedItemsKey,
		selectedItemsValue,
		selectionType,
		sorting,
		updateSorting,
	} = useContext(DatasetDisplayContext);

	const showActionItems = Boolean(
		itemActions?.length || items.find((element) => element.actionItems)
	);

	const SelectionComponent =
		selectionType === 'multiple' ? CheckboxRenderer : RadioRenderer;

	return (
		<div className={`table-style-${style}`}>
			<ClayTable borderless hover={false} responsive>
				<TableHeadRow
					items={items}
					schema={schema}
					selectable={selectable}
					selectedItemsKey={selectedItemsKey}
					selectedItemsValue={selectedItemsValue}
					selectionType={selectionType}
					selectItems={selectItems}
					showActionItems={showActionItems}
					sorting={sorting}
					updateSorting={updateSorting}
				/>
				<ClayTable.Body>
					{items.map((item, i) => {
						const itemId = item[selectedItemsKey] || i;
						const nestedItems =
							nestedItemsReferenceKey &&
							item[nestedItemsReferenceKey];

						return (
							<React.Fragment key={itemId}>
								<ClayTable.Row
									className={classNames(
										highlightedItemsValue.includes(
											itemId
										) && 'active'
									)}
								>
									{selectable && (
										<ClayTable.Cell
											className="dataset-item-selector-wrapper"
											rowSpan={
												1 + (nestedItems?.length || 0)
											}
										>
											<SelectionComponent
												checked={
													!!selectedItemsValue.find(
														(element) =>
															String(element) ===
															String(itemId)
													)
												}
												onChange={() =>
													selectItems(itemId)
												}
												value={itemId}
											/>
										</ClayTable.Cell>
									)}
									{getItemFields(
										item,
										schema.fields,
										itemId,
										itemActions
									)}
									{showActionItems && (
										<ClayTable.Cell className="dataset-item-actions-wrapper">
											{(itemActions ||
												item.actionItems) && (
												<ActionsDropdownRenderer
													actions={
														itemActions ||
														item.actionItems
													}
													itemData={item}
													itemId={itemId}
												/>
											)}
										</ClayTable.Cell>
									)}
								</ClayTable.Row>
								{nestedItems?.length
									? nestedItems.map((nestedItem, i) => (
											<ClayTable.Row
												className={classNames(
													'dataset-sub-item',
													highlightedItemsValue.includes(
														nestedItem[
															nestedItemsKey
														]
													) && 'active',
													i ===
														nestedItems.length -
															1 && 'last-of-group'
												)}
												key={nestedItem[nestedItemsKey]}
											>
												{getItemFields(
													nestedItem,
													schema.fields,
													nestedItem[nestedItemsKey],
													itemActions
												)}
												{showActionItems ? (
													<ClayTable.Cell />
												) : null}
											</ClayTable.Row>
									  ))
									: null}
							</React.Fragment>
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
					PropTypes.array,
				]),
				mapData: PropTypes.func,
			})
		).isRequired,
	}).isRequired,
	style: PropTypes.string.isRequired,
};

Table.defaultProps = {
	items: [],
};

export default Table;
