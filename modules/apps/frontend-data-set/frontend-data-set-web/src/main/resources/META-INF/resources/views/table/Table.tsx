/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox, ClayRadio} from '@clayui/form';
import {LinkOrButton} from '@clayui/shared';
import classNames from 'classnames';
import React, {useContext, useState} from 'react';

import {IItemsActions} from '../..';
import FrontendDataSetContext from '../../FrontendDataSetContext';
import Actions from '../../actions/Actions';
import {
	ILocalizedItemDetails,
	getLocalizedValue,
} from '../../utils/getLocalizedValue';
import ViewsContext from '../ViewsContext';
import {ClayTable} from './ClayTable';

// @ts-ignore

import FieldsSelectorDropdown from './FieldsSelectorDropdown';
import TableCell from './TableCell';
import TableHeadCell from './TableHeadCell';

// @ts-ignore

import TableInlineAddingRow from './TableInlineAddingRow';
import TableContext from './dnd_table/TableContext';

// @ts-ignore

import DndTable from './dnd_table/index';

interface IField {
	fieldName: string | [];
	label: string;
	mapData: Function;
}
interface ISchema {
	fields: Array<IField>;
}

const getVisibleFields = ({
	fields,
	visibleFieldNames,
}: {
	fields: Array<any>;
	visibleFieldNames: Array<string>;
}) => {
	const visibleFields = fields.filter(
		({fieldName}) => visibleFieldNames[fieldName]
	);

	return visibleFields.length ? visibleFields : fields;
};

const Head = ({
	fields,
	items,
	itemsActions,
	schema,
	selectItems,
	selectable,
	selectedItemsKey,
	selectedItemsValue,
	selectionType,
}: {
	fields: Array<IField>;
	items: Array<any>;
	itemsActions: Array<IItemsActions>;
	schema: ISchema;
	selectItems: Function;
	selectable: boolean | undefined;
	selectedItemsKey: string;
	selectedItemsValue: any;
	selectionType: string | undefined;
}) => {
	const {isFixed} = useContext(TableContext);

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
					defaultWidth={0}
					heading
				>
					<FieldsSelectorDropdown fields={schema.fields} />

					{!isFixed &&
						itemsActions?.length === 1 &&
						!itemsActions[0].icon &&
						itemsActions[0].label && (
							<LinkOrButton
								className="btn btn-secondary btn-sm"
								href="#"
								monospaced={false}
								style={{
									'visibility': 'hidden',
									'white-space': 'nowrap',
								}}
							>
								{itemsActions[0].label}
							</LinkOrButton>
						)}
				</DndTable.Cell>
			</DndTable.Row>
		</DndTable.Head>
	);
};

const ItemCells = ({
	fields,
	item,
	itemId,
	itemInlineChanges,
	itemsActions,
}: {
	fields: Array<any>;
	item: any;
	itemId: string;
	itemInlineChanges?: Array<any> | undefined;
	itemsActions: Array<IItemsActions>;
}) => {
	return (
		<>
			{fields.map((field) => {
				const {actionDropdownItems} = item;

				const localizedValue: ILocalizedItemDetails | null =
					getLocalizedValue(item, field.fieldName);

				const valuePath = localizedValue?.valuePath ?? undefined;

				return (
					<TableCell
						actions={itemsActions || actionDropdownItems}
						field={field}
						itemData={item}
						itemId={itemId}
						itemInlineChanges={itemInlineChanges}
						key={field.fieldName}
						rootPropertyName={
							localizedValue?.rootPropertyName ?? undefined
						}
						value={localizedValue?.value ?? undefined}
						valuePath={valuePath}
					/>
				);
			})}
		</>
	);
};

const RowWithActions = ({
	active,
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
	...otherProps
}: {
	active: boolean;
	item: any;
	itemId: string;
	itemsActions: Array<IItemsActions>;
	itemsChanges: Array<any> | undefined;
	selectItems: Function;
	selectable: boolean | undefined;
	selected: boolean;
	selectedItemsValue: any;
	selectionType: string | undefined;
	visibleFields: Array<any>;
}) => {
	const [menuActive, setMenuActive] = useState(false);

	const SelectionComponent =
		selectionType === 'multiple' ? ClayCheckbox : ClayRadio;

	return (
		<DndTable.Row
			className={classNames({
				active,
				'menu-active': menuActive,
				selected,
			})}
			{...otherProps}
		>
			{selectable && (
				<DndTable.Cell
					className="item-selector"
					columnName="item-selector"
				>
					<SelectionComponent
						checked={
							!!selectedItemsValue.find(
								(element: any) =>
									String(element) === String(itemId)
							)
						}
						onChange={() => selectItems(itemId)}
						title={Liferay.Language.get('select-item')}
						value={itemId}
					/>
				</DndTable.Cell>
			)}

			<ItemCells
				fields={visibleFields}
				item={item}
				itemId={itemId}
				itemInlineChanges={itemsChanges}
				itemsActions={itemsActions}
			/>

			<DndTable.Cell className="item-actions" columnName="item-actions">
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

const Body = ({
	highlightedItemsValue,
	inlineAddingSettings,
	items,
	itemsActions,
	itemsChanges,
	nestedItemsKey,
	nestedItemsReferenceKey,
	selectItems,
	selectable,
	selectedItemsKey,
	selectedItemsValue,
	selectionType,
	visibleFields,
}: {
	highlightedItemsValue: any;
	inlineAddingSettings: any;
	items: Array<any>;
	itemsActions: Array<IItemsActions>;
	itemsChanges: any;
	nestedItemsKey: string | undefined;
	nestedItemsReferenceKey: string | undefined;
	selectItems: Function;
	selectable: boolean | undefined;
	selectedItemsKey: string;
	selectedItemsValue: any;
	selectionType: string | undefined;
	visibleFields: Array<any>;
}) => {
	const actionExists = Boolean(
		itemsActions?.length ||
			items?.find((item) => item.actionDropdownItems?.length)
	);

	return (
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
								active={highlightedItemsValue.includes(itemId)}
								item={item}
								itemId={itemId}
								itemsActions={itemsActions}
								itemsChanges={itemsChanges}
								selectItems={selectItems}
								selectable={selectable}
								selected={selectedItemsValue.includes(itemId)}
								selectedItemsValue={selectedItemsValue}
								selectionType={selectionType}
								visibleFields={visibleFields}
							/>

							{nestedItems &&
								nestedItemsKey &&
								nestedItems.map(
									(nestedItem: any, i: number) => (
										<DndTable.Row
											className={classNames(
												'nested',
												highlightedItemsValue.includes(
													nestedItem[nestedItemsKey]
												) && 'active',
												i === nestedItems.length - 1 &&
													'last'
											)}
											key={nestedItem[nestedItemsKey]}
											paddingLeftCells={selectable && 1}
											paddingRightCells={
												actionExists && 1
											}
										>
											<ItemCells
												fields={visibleFields}
												item={nestedItem}
												itemId={
													nestedItem[nestedItemsKey]
												}
												itemsActions={itemsActions}
											/>
										</DndTable.Row>
									)
								)}
						</React.Fragment>
					);
				})}
		</DndTable.Body>
	);
};

const Table = ({
	items = [],
	itemsActions,
	schema,
	style,
}: {
	items: Array<any>;
	itemsActions: Array<IItemsActions>;
	schema: ISchema;
	style: string;
}) => {
	const {
		highlightedItemsValue,
		inlineAddingSettings,
		itemsChanges,
		nestedItemsKey,
		nestedItemsReferenceKey,
		selectItems,
		selectable,
		selectedItemsKey = 'id',
		selectedItemsValue,
		selectionType,
	} = useContext(FrontendDataSetContext);
	const [
		{
			activeView: {quickActionsEnabled},
			visibleFieldNames,
		},
	] = useContext(ViewsContext);

	const visibleFields = getVisibleFields({
		fields: schema.fields,
		visibleFieldNames,
	});

	const columnNames = [];

	if (selectable) {
		columnNames.push('item-selector');
	}

	columnNames.push(
		...visibleFields.map((field) => String(field.fieldName)),
		'item-actions'
	);

	if (Liferay.FeatureFlags['LPS-193005']) {
		return (
			<DndTable.TableContextProvider
				columnNames={visibleFields.map((field) =>
					String(field.fieldName)
				)}
			>
				<ClayTable
					fields={schema.fields as any}
					inlineAddingSettings={inlineAddingSettings}
					itemInlineChanges={itemsChanges}
					items={items}
					itemsActions={itemsActions}
					nestedItemsReferenceKey={nestedItemsReferenceKey}
					selectItems={selectItems}
					selectable={selectable}
					selectedItemsKey={selectedItemsKey}
					selectedItemsValue={selectedItemsValue}
					selectionType={selectionType}
				/>
			</DndTable.TableContextProvider>
		);
	}

	return (
		<DndTable.TableContextProvider columnNames={columnNames}>
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
					<Head
						fields={visibleFields}
						items={items}
						itemsActions={itemsActions}
						schema={schema}
						selectItems={selectItems}
						selectable={selectable}
						selectedItemsKey={selectedItemsKey}
						selectedItemsValue={selectedItemsValue}
						selectionType={selectionType}
					/>

					<Body
						highlightedItemsValue={highlightedItemsValue}
						inlineAddingSettings={inlineAddingSettings}
						items={items}
						itemsActions={itemsActions}
						itemsChanges={itemsChanges}
						nestedItemsKey={nestedItemsKey}
						nestedItemsReferenceKey={nestedItemsReferenceKey}
						selectItems={selectItems}
						selectable={selectable}
						selectedItemsKey={selectedItemsKey}
						selectedItemsValue={selectedItemsValue}
						selectionType={selectionType}
						visibleFields={visibleFields}
					/>
				</DndTable.Table>
			)}
		</DndTable.TableContextProvider>
	);
};

export default Table;
