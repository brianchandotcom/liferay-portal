/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayEmptyState from '@clayui/empty-state';
import {ClayCheckbox, ClayRadio} from '@clayui/form';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useContext, useRef, useState} from 'react';

import FrontendDataSetContext from '../../FrontendDataSetContext';
import Actions from '../../actions/Actions';
import {getLocalizedValue} from '../../utils/getLocalizedValue';
import ViewsContext from '../ViewsContext';
import TableCell from './TableCell';
import TableHead from './TableHead';
import TableInlineAddingRow from './TableInlineAddingRow';
import DndTable from './dnd_table/index';

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
			? getLocalizedValue(item, field.fieldName)
			: {};

		return (
			<TableCell
				actions={itemsActions || actionDropdownItems}
				field={field}
				itemData={item}
				itemId={itemId}
				itemInlineChanges={itemInlineChanges}
				key={valuePath ? valuePath.join('_') : field.label}
				rootPropertyName={rootPropertyName}
				value={value}
				valuePath={valuePath}
			/>
		);
	});
}

export function getVisibleFields(fields, visibleFieldNames) {
	const visibleFields = fields.filter(
		({fieldName}) => visibleFieldNames[fieldName]
	);

	return visibleFields.length ? visibleFields : fields;
}

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
	} = useContext(FrontendDataSetContext);
	const [
		{
			activeView: {quickActionsEnabled},
			visibleFieldNames,
		},
	] = useContext(ViewsContext);

	const visibleFields = getVisibleFields(schema.fields, visibleFieldNames);

	const actionExistsRef = useRef(
		Boolean(
			itemsActions?.length ||
				items?.find((item) => item.actionDropdownItems?.length)
		)
	);

	const columnNames = [];

	if (selectable) {
		columnNames.push('item-selector');
	}

	columnNames.push(
		...visibleFields.map((field) => String(field.fieldName)),
		'item-actions'
	);

	return (
		<DndTable.ContextProvider columnNames={columnNames}>
			{(inlineAddingSettings ||
				(!inlineAddingSettings && !!items.length)) && (
				<DndTable.Table
					borderless
					className={classNames(`table-style-${style}`, {
						'with-quick-actions': quickActionsEnabled,
					})}
					hover={false}
					responsive
					striped
				>
					<TableHead
						fields={visibleFields}
						items={items}
						schema={schema}
						selectItems={selectItems}
						selectable={selectable}
						selectedItemsKey={selectedItemsKey}
						selectedItemsValue={selectedItemsValue}
						selectionType={selectionType}
					/>

					<DndTable.Body>
						{inlineAddingSettings && (
							<TableInlineAddingRow
								fields={visibleFields}
								selectable={selectable}
							/>
						)}

						{!!items.length &&
							items.map((item) => {
								const itemId = item[selectedItemsKey ?? 'id'];

								const nestedItems =
									nestedItemsReferenceKey &&
									item[nestedItemsReferenceKey];

								return (
									<React.Fragment key={itemId}>
										<RowWithActions
											active={highlightedItemsValue.includes(
												itemId
											)}
											item={item}
											itemId={itemId}
											itemsActions={itemsActions}
											itemsChanges={itemsChanges}
											selectItems={selectItems}
											selectable={selectable}
											selected={selectedItemsValue.includes(
												itemId
											)}
											selectedItemsValue={
												selectedItemsValue
											}
											selectionType={selectionType}
											visibleFields={visibleFields}
										/>

										{nestedItems &&
											nestedItems.map((nestedItem, i) => (
												<DndTable.Row
													className={classNames(
														'nested',
														highlightedItemsValue.includes(
															nestedItem[
																nestedItemsKey
															]
														) && 'active',
														i ===
															nestedItems.length -
																1 && 'last'
													)}
													key={
														nestedItem[
															nestedItemsKey
														]
													}
													paddingLeftCells={
														selectable && 1
													}
													paddingRightCells={
														actionExistsRef.current &&
														1
													}
												>
													{getItemFields(
														nestedItem,
														visibleFields,
														nestedItem[
															nestedItemsKey
														],
														itemsActions
													)}
												</DndTable.Row>
											))}
									</React.Fragment>
								);
							})}
					</DndTable.Body>
				</DndTable.Table>
			)}

			{!items.length && (
				<ClayEmptyState
					description={Liferay.Language.get(
						'sorry,-no-results-were-found'
					)}
					imgSrc={`${themeDisplay.getPathThemeImages()}/states/search_state.gif`}
					title={Liferay.Language.get('no-results-found')}
				/>
			)}
		</DndTable.ContextProvider>
	);
}

const RowWithActions = ({
	active,
	className,
	item,
	itemId,
	itemsActions,
	itemsChanges,
	selectItems,
	selectable,
	selected,
	selectedItemsValue,
	selectionType,
	visibleFields,
	...props
}) => {
	const [menuActive, setMenuActive] = useState(false);

	const SelectionComponent =
		selectionType === 'multiple' ? ClayCheckbox : ClayRadio;

	return (
		<DndTable.Row
			className={classNames(className, {
				active,
				'menu-active': menuActive,
				selected,
			})}
			{...props}
		>
			{selectable && (
				<DndTable.Cell
					className="item-selector"
					columnName="item-selector"
				>
					<SelectionComponent
						checked={
							!!selectedItemsValue.find(
								(element) => String(element) === String(itemId)
							)
						}
						onChange={() => selectItems(itemId)}
						title={Liferay.Language.get('select-item')}
						value={itemId}
					/>
				</DndTable.Cell>
			)}

			{getItemFields(
				item,
				visibleFields,
				itemId,
				itemsActions,
				itemsChanges[itemId]
			)}

			<DndTable.Cell
				className="item-actions"
				columnName="item-actions"
				defaultWidth="44px"
			>
				{(itemsActions?.length > 0 ||
					item.actionDropdownItems?.length > 0) && (
					<Actions
						actions={itemsActions || item.actionDropdownItems}
						itemData={item}
						itemId={itemId}
						menuActive={menuActive}
						onMenuActiveChange={setMenuActive}
					/>
				)}{' '}
			</DndTable.Cell>
		</DndTable.Row>
	);
};

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
