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

import {getValueFromItem} from '../../../../utilities/index';
import ActionsDropdownRenderer from '../../../data_renderers/ActionsDropdownRenderer';
import CheckboxRenderer from '../../../data_renderers/CheckboxRenderer';
import CommentRenderer from '../../../data_renderers/CommentRenderer';
import RadioRenderer from '../../../data_renderers/RadioRenderer';
import {
	getDataRendererById,
	getDataRendererByUrl,
} from '../../../data_renderers/index';
import DatasetDisplayContext from '../../DatasetDisplayContext';
import TableHeadRow from './TableHeadRow';

function CustomTableCell(props) {
	const {view} = props;
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

function getItemFields(item, fields, itemId, itemsActions) {
	return fields.map((field, i) => {
		const {actionItems, comments} = item;
		const rawValue = getValueFromItem(item, field.fieldName);
		const formattedValue = field.mapData
			? field.mapData(rawValue)
			: rawValue;
		const comment = comments ? comments[field.fieldName] : null;

		return (
			<CustomTableCell
				actions={itemsActions || actionItems}
				comment={comment}
				itemData={item}
				itemId={itemId}
				key={field.fieldName || i}
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

function Table(props) {
	const {
		actionLoading,
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
		(props.itemsActions && props.itemsActions.length) ||
			props.items.find((el) => el.actionItems)
	);

	const SelectionComponent =
		selectionType === 'multiple' ? CheckboxRenderer : RadioRenderer;

	return (
		<div className={`table-style-${props.style}`}>
			<ClayTable borderless hover={false} responsive>
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
												1 +
												((nestedItems &&
													nestedItems.length) ||
													0)
											}
										>
											<SelectionComponent
												checked={
													!!selectedItemsValue.find(
														(el) =>
															String(el) ===
															String(itemId)
													)
												}
												disabled={actionLoading}
												onChange={() =>
													selectItems(itemId)
												}
												value={itemId}
											/>
										</ClayTable.Cell>
									)}
									{getItemFields(
										item,
										props.schema.fields,
										itemId,
										props.itemsActions
									)}
									{showActionItems && (
										<ClayTable.Cell className="dataset-item-actions-wrapper">
											{(props.itemsActions ||
												item.actionItems) && (
												<ActionsDropdownRenderer
													actions={
														props.itemsActions ||
														item.actionItems
													}
													itemData={item}
													itemId={itemId}
												/>
											)}
										</ClayTable.Cell>
									)}
								</ClayTable.Row>
								{nestedItems && nestedItems.length
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
													props.schema.fields,
													nestedItem[nestedItemsKey],
													props.itemsActions
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
	items: PropTypes.arrayOf(PropTypes.object),
	itemsActions: PropTypes.array,
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
