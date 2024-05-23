/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {Body, Cell, Head, Row, Table} from '@clayui/core';
import {ClayCheckbox, ClayRadio} from '@clayui/form';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import {FDSTableCellHTMLElementBuilderArgs} from '@liferay/js-api/data-set';
import {ClientExtension} from 'frontend-js-components-web';
import React, {useContext, useMemo, useState} from 'react';

import {IItemsActions} from '../..';
import FrontendDataSetContext, {
	IFrontendDataSetContext,
	TRenderer,
} from '../../FrontendDataSetContext';
import Actions from '../../actions/Actions';
import {getInternalCellRenderer} from '../../cell_renderers/getInternalCellRenderer';
import {
	ILocalizedItemDetails,
	getLocalizedValue,
} from '../../utils/getLocalizedValue';
import {getInputRendererById} from '../../utils/renderer';
import ViewsContext from '../ViewsContext';
import {InlineEditInputRenderer} from './TableCell';

type Field = {
	contentRenderer: string;
	contentRendererClientExtension: boolean;
	fieldName: any;
	label: string;
	localizeLabel: boolean;
	mapData: Function;
	sortable: boolean;
};

type Props = {
	fields: Array<Field>;
	inlineAddingSettings?: {
		apiURL?: string;
		defaultBodyContent?: Record<string, any>;
	};
	itemInlineChanges?: Array<any>;
	items: Array<any>;
	itemsActions: Array<IItemsActions>;
	nestedItemsReferenceKey?: string;
	selectItems: Function;
	selectable?: boolean;
	selectedItemsKey: string;
	selectedItemsValue: any;
	selectionType?: string;
};

type Sorting = {
	column: React.Key;
	direction: 'ascending' | 'descending';
};

const defaultAddItem = {
	editable: true,
	fieldName: 'add',
	id: 'add',
};

const defaultAlwaysVisibleColumns = new Set(['select']);

export function ClayTable({
	fields,
	inlineAddingSettings,
	itemInlineChanges,
	items,
	itemsActions,
	nestedItemsReferenceKey,
	selectItems,
	selectable,
	selectedItemsKey,
	selectedItemsValue,
	selectionType,
}: Props) {
	const {itemsChanges, updateItem} = useContext(FrontendDataSetContext);
	const [sort, setSort] = useState<Sorting | null>(null);

	const filteredItems = useMemo(() => {
		if (!sort) {
			return items;
		}

		return items.sort((a, b) => {
			const column = (sort.column as string).includes(',')
				? (sort.column as string).split(',')
				: (sort.column as string);
			const aValue: ILocalizedItemDetails | null = getLocalizedValue(
				a,
				column
			);
			const bValue: ILocalizedItemDetails | null = getLocalizedValue(
				b,
				column
			);

			let cmp = new Intl.Collator('en', {numeric: true}).compare(
				aValue?.value ?? '',
				bValue?.value ?? ''
			);

			if (sort.direction === 'descending') {
				cmp *= -1;
			}

			return cmp;
		});
	}, [sort, items]);

	const SelectionComponent =
		selectionType === 'multiple' ? ClayCheckbox : ClayRadio;

	return (
		<Table
			alwaysVisibleColumns={defaultAlwaysVisibleColumns}
			nestedKey={nestedItemsReferenceKey}
			onSortChange={setSort}
			sort={sort}
		>
			<Head
				items={selectable ? [{fieldName: 'select'}, ...fields] : fields}
			>
				{(field) => {
					if (field.fieldName === 'select') {
						if (!!items.length && selectionType !== 'multiple') {

							// @ts-ignore

							return <Cell key="select" width="51px" />;
						}

						const title =
							items.length !== selectedItemsValue.length
								? Liferay.Language.get('select-items')
								: Liferay.Language.get('clear-selection');

						return (
							<Cell key="select" textValue={title} width="51px">
								<ClayCheckbox
									checked={!!selectedItemsValue.length}
									indeterminate={
										!!selectedItemsValue.length &&
										items.length !==
											selectedItemsValue.length
									}
									name="table-head-selector"
									onChange={() => {
										if (
											selectedItemsValue.length ===
											items.length
										) {
											return selectItems([]);
										}

										return selectItems(
											items.map(
												(item) => item[selectedItemsKey]
											)
										);
									}}
									title={title}
								/>
							</Cell>
						);
					}

					return (
						<Cell
							key={field.fieldName}
							sortable={(field as any).sortable}
						>
							{(field as any).label}
						</Cell>
					);
				}}
			</Head>

			<Body
				defaultItems={
					inlineAddingSettings
						? [...filteredItems, defaultAddItem]
						: items
				}
			>
				{(item) => {
					const id = item[selectedItemsKey ?? 'id'];

					const items = [...fields, {fieldName: 'actions'}];

					return (
						<Row
							items={
								selectable
									? [{fieldName: 'select'}, ...items]
									: items
							}
							key={id}
						>
							{(cell) => {
								switch (cell.fieldName) {
									case 'actions': {
										return (
											<Cell
												key={`${id}:actions`}
												textValue={Liferay.Language.get(
													'select-item'
												)}
											>
												{item.editable ? (
													<AddActions />
												) : (
													(itemsActions?.length > 0 ||
														item.actionDropdownItems
															?.length > 0) && (
														<Actions
															actions={
																itemsActions ||
																item.actionDropdownItems
															}
															itemData={item}
															itemId={id}
														/>
													)
												)}
											</Cell>
										);
									}
									case 'select':
										return (
											<Cell
												key={`${id}:select`}
												textValue={Liferay.Language.get(
													'select-item'
												)}
											>
												{!item.editable && (
													<SelectionComponent
														checked={
															!!selectedItemsValue.find(
																(
																	element: any
																) =>
																	String(
																		element
																	) ===
																	String(id)
															)
														}
														onChange={() =>
															selectItems(id)
														}
														title={Liferay.Language.get(
															'select-item'
														)}
														value={id}
													/>
												)}
											</Cell>
										);
									default: {
										if (item.editable) {
											const field = cell as any;
											let InputRenderer: any = null;

											if (
												field.inlineEditSettings?.type
											) {
												InputRenderer = getInputRendererById(
													field.inlineEditSettings
														.type
												);
											}

											const valuePath = Array.isArray(
												field.fieldName
											)
												? field.fieldName.map(
														(property: string) =>
															property === 'LANG'
																? Liferay.ThemeDisplay.getDefaultLanguageId()
																: property
												  )
												: [field.fieldName];

											const rootPropertyName =
												valuePath[0];

											const newItem =
												itemsChanges![0] || {};

											return (
												<Cell
													key={`${id}:${cell.fieldName}`}
												>
													{InputRenderer ? (
														<InputRenderer
															updateItem={(
																value: string
															) => {
																updateItem(
																	0,
																	rootPropertyName,
																	valuePath,
																	value
																);
															}}
															value={
																newItem[
																	rootPropertyName
																] &&
																newItem[
																	rootPropertyName
																].value
															}
															valuePath={
																rootPropertyName
															}
														/>
													) : null}
												</Cell>
											);
										}

										const localizedValue: ILocalizedItemDetails | null = getLocalizedValue(
											item,
											cell.fieldName
										);

										const valuePath =
											localizedValue?.valuePath ??
											undefined;

										return (
											<Cell
												key={`${id}:${cell.fieldName}`}
											>
												<CellRenderer
													actions={
														itemsActions ||
														item.actionDropdownItems
													}
													field={cell}
													itemData={item}
													itemId={id}
													itemInlineChanges={
														itemInlineChanges
													}
													rootPropertyName={
														localizedValue?.rootPropertyName ??
														undefined
													}
													value={
														localizedValue?.value ??
														undefined
													}
													valuePath={valuePath}
												/>
											</Cell>
										);
									}
								}
							}}
						</Row>
					);
				}}
			</Body>
		</Table>
	);
}

function AddActions() {
	const {createInlineItem, itemsChanges, toggleItemInlineEdit} = useContext(
		FrontendDataSetContext
	);

	const isMounted = useIsMounted();
	const [loading, setLoading] = useState(false);
	const itemHasChanged =
		itemsChanges![0] && !!Object.keys(itemsChanges![0]).length;

	return (
		<div className="d-flex ml-auto">
			<ClayButtonWithIcon
				aria-labelledby={Liferay.Language.get('close')}
				className="mr-1"
				disabled={!itemHasChanged}
				displayType="secondary"
				onClick={() => {
					toggleItemInlineEdit(0);
				}}
				size="sm"
				symbol="times-small"
			/>

			{loading ? (
				<ClayButton
					aria-labelledby={Liferay.Language.get('loading')}
					disabled
					monospaced
					size="sm"
				>
					<ClayLoadingIndicator size="sm" />
				</ClayButton>
			) : (
				<ClayButtonWithIcon
					aria-labelledby={Liferay.Language.get('confirm')}
					disabled={loading || !itemHasChanged}
					onClick={() => {
						setLoading(true);
						createInlineItem().finally(() => {
							if (isMounted()) {
								setLoading(false);
							}
						});
					}}
					size="sm"
					symbol="check"
				/>
			)}
		</div>
	);
}

type CellRendererProps = {
	actions: any;
	field: any;
	itemData: any;
	itemId: string;
	itemInlineChanges: any;
	rootPropertyName: any;
	value: any;
	valuePath: any;
};

function CellRenderer({
	actions,
	field,
	itemData,
	itemId,
	itemInlineChanges,
	rootPropertyName,
	value,
	valuePath,
}: CellRendererProps) {
	const {
		customDataRenderers,
		customRenderers,
		inlineEditingSettings,
		loadData,
		openSidePanel,
	}: IFrontendDataSetContext = useContext(FrontendDataSetContext);
	const [{modifiedFields}] = useContext(ViewsContext) as any;

	const cellRenderer = useMemo(() => {
		if (field.contentRendererClientExtension) {
			const mergedField = {...field, ...modifiedFields[field.fieldName]};

			return {
				htmlElementBuilder: mergedField.htmlElementBuilder,
				type: 'clientExtension',
			};
		}

		const contentRenderer = field.contentRenderer || 'default';

		const customTableCellRenderer = customRenderers?.tableCell?.find(
			(renderer: TRenderer) => renderer.name === contentRenderer
		);

		if (customTableCellRenderer) {
			return customTableCellRenderer;
		}

		if (customDataRenderers && customDataRenderers[contentRenderer]) {
			return {
				component: customDataRenderers[contentRenderer],
				type: 'internal',
			};
		}

		return getInternalCellRenderer(contentRenderer);
	}, [customDataRenderers, customRenderers, field, modifiedFields]);

	if (
		inlineEditingSettings &&
		(itemInlineChanges || inlineEditingSettings.alwaysOn)
	) {
		return (
			<>
				<InlineEditInputRenderer
					actions={actions}
					itemData={itemData}
					itemId={itemId}
					options={field}
					rootPropertyName={rootPropertyName}
					type={field.inlineEditSettings.type}
					value={value}
					valuePath={valuePath}
				/>
			</>
		);
	}

	if (cellRenderer?.type === 'clientExtension') {
		return (
			<>
				<ClientExtension<FDSTableCellHTMLElementBuilderArgs>
					args={{value}}
					htmlElementBuilder={cellRenderer.htmlElementBuilder}
				/>
			</>
		);
	}

	if (cellRenderer?.type === 'internal' && cellRenderer.component) {
		const CellRendererComponent = cellRenderer.component;

		return (
			<>
				{CellRendererComponent && (
					<CellRendererComponent
						actions={actions}
						itemData={itemData}
						itemId={itemId}
						loadData={loadData}
						openSidePanel={openSidePanel}
						options={field}
						rootPropertyName={rootPropertyName}
						value={value}
						valuePath={valuePath}
					/>
				)}
			</>
		);
	}

	return null;
}
