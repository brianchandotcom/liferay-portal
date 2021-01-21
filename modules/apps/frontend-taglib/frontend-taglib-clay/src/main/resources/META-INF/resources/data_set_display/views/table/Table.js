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

import {ClayCheckbox, ClayRadio} from '@clayui/form';
import ClayTable from '@clayui/table';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import DataSetDisplayContext from '../../DataSetDisplayContext';
import ActionsDropdownRenderer from '../../data_renderers/ActionsDropdownRenderer';
import {getValueDetailsFromItem} from '../../utils/index';
import ViewsContext from '../ViewsContext';
import TableCell from './TableCell';
import TableHeadRow from './TableHeadRow';
import TableInlineAddingRow from './TableInlineAddingRow';

function getItemFields(
	item,
	fields,
	itemId,
	itemsActions,
	itemInlineChanges = null
) {
	return fields.map((field) => {
		const {actionDropdownItems} = item;
		const {rootPropertyName, value, valuePath} = field.fieldName
			? getValueDetailsFromItem(item, field.fieldName)
			: {};

		return (
			<TableCell
				actions={itemsActions || actionDropdownItems}
				inlineEditSettings={field.inlineEditSettings}
				itemData={item}
				itemId={itemId}
				itemInlineChanges={itemInlineChanges}
				key={valuePath ? valuePath.join('_') : field.label}
				options={field}
				rootPropertyName={rootPropertyName}
				value={value}
				valuePath={valuePath}
				view={{
					contentRenderer: field.contentRenderer,
					contentRendererModuleURL: field.contentRendererModuleURL,
				}}
			/>
		);
	});
}

export const getVisibleFields = (fields, visibleFieldNames) => {
	const visibleFields = fields.filter(
		({fieldName}) => visibleFieldNames[fieldName]
	);

	return visibleFields.length ? visibleFields : fields;
};

function Table({items, itemsActions, schema, style}) {
	const {
		highlightedItemsValue,
		inlineAddingSettings,
		itemsChanges,
		nestedItemsKey,
		nestedItemsReferenceKey,
		selectItems,
		selectable,
		selectedItemsKey,
		selectedItemsValue,
		selectionType,
		sorting,
		updateSorting,
	} = useContext(DataSetDisplayContext);
	const [{visibleFieldNames}] = useContext(ViewsContext);

	const visibleFields = getVisibleFields(schema.fields, visibleFieldNames);

	const showActionItems = Boolean(
		itemsActions?.length ||
			items.find((item) => item.actions || item.actionDropdownItems)
	);

	const SelectionComponent =
		selectionType === 'multiple' ? ClayCheckbox : ClayRadio;

	return (
		<div className={`table-style-${style}`}>
			<ClayTable borderless hover={false} responsive>
				<TableHeadRow
					items={items}
					schema={schema}
					selectItems={selectItems}
					selectable={selectable}
					selectedItemsKey={selectedItemsKey}
					selectedItemsValue={selectedItemsValue}
					selectionType={selectionType}
					sorting={sorting}
					updateSorting={updateSorting}
					visibleFields={visibleFields}
				/>
				<ClayTable.Body>
					{inlineAddingSettings && (
						<TableInlineAddingRow
							fields={visibleFields}
							selectable={selectable}
						/>
					)}
					{items.map((item) => {
						const itemId = item[selectedItemsKey];
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
											className="data-set-item-selector-wrapper"
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
										visibleFields,
										itemId,
										itemsActions,
										itemsChanges[itemId]
									)}
									<ClayTable.Cell className="data-set-item-actions-wrapper">
										{(showActionItems || item.actions) && (
											<ActionsDropdownRenderer
												actions={
													itemsActions ||
													item.actions ||
													item.actionDropdownItems
												}
												itemData={item}
												itemId={itemId}
											/>
										)}
									</ClayTable.Cell>
								</ClayTable.Row>
								{nestedItems?.length
									? nestedItems.map((nestedItem, i) => (
											<ClayTable.Row
												className={classNames(
													'data-set-sub-item',
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
													itemsActions
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
