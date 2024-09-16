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
import classNames from 'classnames';
import {ClientExtension} from 'frontend-js-components-web';
import {throttle} from 'frontend-js-web';
import React, {useContext, useEffect, useMemo, useRef, useState} from 'react';

import {IItemsActions} from '../..';
import FrontendDataSetContext, {
	IFrontendDataSetContext,
	TRenderer,
} from '../../FrontendDataSetContext';
import Actions from '../../actions/Actions';
import {getInternalCellRenderer} from '../../cell_renderers/getInternalCellRenderer';

// @ts-ignore

import persistVisibleFieldNames from '../../thunks/persistVisibleFieldNames';
import {
	ILocalizedItemDetails,
	getLocalizedValue,
} from '../../utils/getLocalizedValue';
import {getInputRendererById} from '../../utils/renderer';
import ViewsContext, {
	IViewsContext,
	TViewsContextDispatch,
} from '../ViewsContext';
import getCellColumnClassName from '../utils/getCellColumnClassName';

// @ts-ignore

import {VIEWS_ACTION_TYPES} from '../viewsReducer';
import {InlineEditInputRenderer} from './TableCell';
import TableContext from './dnd_table/TableContext';

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
	const {appURL, id, itemsChanges, portletId, updateItem} = useContext(
		FrontendDataSetContext
	);
	const [{visibleFieldNames}, viewsDispatch] = useContext(ViewsContext);
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
			alwaysVisibleColumns={
				selectable ? defaultAlwaysVisibleColumns : undefined
			}
			messages={{
				columnsVisibility: Liferay.Language.get(
					'manage-columns-visibility'
				),
				columnsVisibilityDescription: Liferay.Language.get(
					'at-least-one-column-must-remain-visible'
				),
				columnsVisibilityHeader:
					Liferay.Language.get('columns-visibility'),
				expandable: Liferay.Language.get('expandable'),
				sortDescription: Liferay.Language.get('sortable-column'),
				sorting: Liferay.Language.get('sorted-by-column-x-in-x-order'),
			}}
			nestedKey={nestedItemsReferenceKey}
			onSortChange={setSort}
			onVisibleColumnsChange={(columns) => {
				viewsDispatch(
					persistVisibleFieldNames({
						appURL,
						id,
						portletId,
						visibleFieldNames: Object.fromEntries(columns),
					})
				);
			}}
			sort={sort}
			visibleColumns={new Map(Object.entries(visibleFieldNames))}
		>
			<Head
				items={selectable ? [{fieldName: 'select'}, ...fields] : fields}
			>
				{(field) => {
					if (field.fieldName === 'select') {
						if (!!items.length && selectionType !== 'multiple') {
							return (
								<Cell key="select" scope="col" width="51px">
									{null}
								</Cell>
							);
						}

						const title =
							items.length !== selectedItemsValue.length
								? Liferay.Language.get('select-items')
								: Liferay.Language.get('clear-selection');

						return (
							<Cell
								className="cell-select-item"
								key="select"
								scope="col"
								textValue={title}
								width="51px"
							>
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
						<HeadCellResizer
							className={getCellColumnClassName(field.fieldName)}
							columnName={field.fieldName}
							key={field.fieldName}
							sortable={(field as any).sortable}
						>
							{(field as any).label}
						</HeadCellResizer>
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
								const cellColumnName = getCellColumnClassName(
									cell.fieldName
								);

								switch (cell.fieldName) {
									case 'actions': {
										return (
											<Cell
												className="cell-select-item"
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
												className="cell-select-item"
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
												InputRenderer =
													getInputRendererById(
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
													className={cellColumnName}
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

										const localizedValue: ILocalizedItemDetails | null =
											getLocalizedValue(
												item,
												cell.fieldName
											);

										const valuePath =
											localizedValue?.valuePath ??
											undefined;

										return (
											<Cell
												className={cellColumnName}
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

/**
 * Wrapper on top of ClayCell to add column resizer capabilities. This
 * should be removed when Clay implements this feature with accessibility.
 */
function HeadCellResizer({
	children,
	columnName,
	...otherProps
}: React.ComponentProps<typeof Cell> & {
	columnName: string;
}) {
	const {
		draggingAllowed,
		draggingColumnName,
		isFixed,
		resizeColumn,
		updateDraggingAllowed,
		updateDraggingColumnName,
	} = useContext(TableContext);

	const [{modifiedFields}, viewsDispatch]: [
		IViewsContext,
		TViewsContextDispatch,
	] = useContext(ViewsContext);

	const cellRef = useRef<HTMLTableCellElement>(null);
	const clientXRef = useRef({current: null});

	useEffect(() => {
		if (columnName && !isFixed && cellRef.current) {
			const boundingClientRect = cellRef.current.getBoundingClientRect();

			viewsDispatch({
				type: VIEWS_ACTION_TYPES.UPDATE_FIELD,
				value: {
					name: columnName,
					resizable: true,
					width: boundingClientRect.width,
				},
			});
		}
	}, [columnName, isFixed, viewsDispatch]);

	const handleDrag = useMemo(() => {
		return throttle((event) => {
			if (event.clientX === clientXRef.current || !cellRef.current) {
				return;
			}

			updateDraggingColumnName(columnName);

			clientXRef.current = event.clientX;

			const {x: headerCellX} = cellRef.current.getClientRects()[0];
			const newWidth = event.clientX - headerCellX;

			resizeColumn(columnName, newWidth);
		}, 20);
	}, [columnName, resizeColumn, updateDraggingColumnName]);

	function initializeDrag() {
		window.addEventListener('mousemove', handleDrag);
		window.addEventListener(
			'mouseup',
			() => {
				updateDraggingAllowed(true);
				updateDraggingColumnName(null);
				window.removeEventListener('mousemove', handleDrag);
			},
			{once: true}
		);
	}

	const width = useMemo(() => {
		const columnDetails = modifiedFields[columnName];

		return columnDetails && isFixed && columnDetails.width;
	}, [isFixed, modifiedFields, columnName]);

	return (
		<Cell
			{...otherProps}
			UNSAFE_resizable
			UNSAFE_resizerClassName={classNames('dnd-th-resizer', {
				'is-active': columnName === draggingColumnName,
				'is-allowed': draggingAllowed,
			})}
			UNSAFE_resizerOnMouseDown={initializeDrag}
			ref={cellRef}
			scope="col"
			style={{width: width || 'auto'}}
			width={width || 'auto'}
		>
			{children}
		</Cell>
	);
}

HeadCellResizer.displayName = 'Item';

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
