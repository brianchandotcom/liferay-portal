/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown, {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayEmptyState from '@clayui/empty-state';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayTable from '@clayui/table';
import classNames from 'classnames';
import {ManagementToolbar} from 'frontend-js-components-web';
import fuzzy from 'fuzzy';
import React, {useEffect, useRef, useState} from 'react';
import {DndProvider, useDrag, useDrop} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import {FUZZY_OPTIONS} from '../Constants';
import Search from './Search';

import '../../css/OrderableTable.scss';

interface IAction {
	icon: string;
	label: string;
	onClick: Function;
}

interface IContentRendererProps {
	item: any;
	query: string;
}

interface IContentRenderer {
	component: React.FC<IContentRendererProps>;
	textMatch?: Function;
}

interface IField {
	contentRenderer?: IContentRenderer;
	headingTitle?: boolean;
	label: string;
	name: string;
}

interface IOrderableTableRowProps {
	actions?: Array<IAction>;
	fields: Array<IField>;
	index: number;
	item: any;
	onOrderChange: Function;
	query: string;
}

const OrderableTableRow = ({
	actions,
	fields,
	index,
	item,
	onOrderChange,
	query,
}: IOrderableTableRowProps) => {
	const tableRowRef = useRef<HTMLTableRowElement>(null);

	const [{isDragging}, dragRef] = useDrag({
		collect: (monitor) => ({
			isDragging: monitor.isDragging(),
		}),
		item: {
			index,
			type: 'FIELD',
		},
	});

	const [, dropRef] = useDrop({
		accept: 'FIELD',
		hover(item: {index: number; type: string}, monitor) {
			if (!tableRowRef.current || !onOrderChange) {
				return;
			}

			const draggedIndex = item.index;
			const targetIndex = index;

			if (draggedIndex === targetIndex) {
				return;
			}

			const targetSize = tableRowRef.current.getBoundingClientRect();
			const targetCenter = (targetSize.bottom - targetSize.top) / 2;

			const draggedOffset: {
				x: number;
				y: number;
			} | null = monitor.getClientOffset();

			if (!draggedOffset) {
				return;
			}

			const draggedTop = draggedOffset.y - targetSize.top;

			if (
				(draggedIndex < targetIndex && draggedTop < targetCenter) ||
				(draggedIndex > targetIndex && draggedTop > targetCenter)
			) {
				return;
			}

			onOrderChange({draggedIndex, targetIndex});

			item.index = targetIndex;
		},
	});

	dragRef(dropRef(tableRowRef));

	return (
		<ClayTable.Row
			className={classNames('orderable-table-row', {
				dragging: isDragging,
			})}
			key={index}
			ref={tableRowRef}
		>
			<ClayTable.Cell className="drag-handle-cell">
				<ClayButtonWithIcon
					aria-label={Liferay.Util.sub(
						Liferay.Language.get('drag-x'),
						item.label || Liferay.Language.get('item')
					)}
					displayType={null}
					size="sm"
					symbol="drag"
				/>
			</ClayTable.Cell>

			{fields.map((field) => {
				if (field.contentRenderer) {
					const Component = field.contentRenderer
						.component as React.FC<IContentRendererProps>;

					return (
						<ClayTable.Cell key={field.name}>
							<Component item={item} query={query} />
						</ClayTable.Cell>
					);
				}

				const itemFieldValue = String(item[field.name]);

				const fuzzyMatch = fuzzy.match(
					query,
					itemFieldValue,
					FUZZY_OPTIONS
				);

				return (
					<ClayTable.Cell
						headingTitle={field.headingTitle}
						key={field.name}
					>
						{fuzzyMatch ? (
							<span
								dangerouslySetInnerHTML={{
									__html: fuzzyMatch.rendered,
								}}
							/>
						) : (
							<span>{itemFieldValue}</span>
						)}
					</ClayTable.Cell>
				);
			})}

			{actions && (
				<ClayTable.Cell className="actions-cell">
					<ClayDropDown
						trigger={
							<ClayButton
								className="component-action"
								displayType="unstyled"
							>
								<ClayIcon symbol="ellipsis-v" />

								<span className="sr-only">
									{Liferay.Language.get('actions')}
								</span>
							</ClayButton>
						}
					>
						<ClayDropDown.ItemList>
							{actions.map(({icon, label, onClick}) => (
								<ClayDropDown.Item
									key={label}
									onClick={() =>
										onClick({
											item,
										})
									}
								>
									{icon && (
										<span className="pr-2">
											<ClayIcon symbol={icon} />
										</span>
									)}

									{label}
								</ClayDropDown.Item>
							))}
						</ClayDropDown.ItemList>
					</ClayDropDown>
				</ClayTable.Cell>
			)}
		</ClayTable.Row>
	);
};

interface IOrderableTableProps {
	actions?: Array<IAction>;
	className?: string;
	creationMenuItems?: React.ComponentProps<
		typeof ClayDropDownWithItems
	>['items'];
	creationMenuLabel?: string;
	disableSave?: boolean;
	fields: Array<IField>;
	items: Array<any>;
	noItemsButtonLabel: string;
	noItemsDescription: string;
	noItemsTitle: string;
	onCancelButtonClick: Function;
	onOrderChange: (args: {orderedItems: any[]}) => void;
	onSaveButtonClick: Function;
	title?: string;
}

const OrderableTable = ({
	actions,
	className,
	creationMenuItems,
	creationMenuLabel = Liferay.Language.get('add'),
	disableSave,
	fields,
	items: initialItems,
	noItemsButtonLabel,
	noItemsDescription,
	noItemsTitle,
	onCancelButtonClick,
	onOrderChange,
	onSaveButtonClick,
	title,
}: IOrderableTableProps) => {
	const [items, setItems] = useState(initialItems);
	const [query, setQuery] = useState('');

	useEffect(() => setItems(initialItems), [initialItems]);

	const onSearch = (query: string) => {
		setQuery(query);

		const regexp = new RegExp(query, 'i');

		setItems(
			query
				? initialItems.filter((item) =>
						fields.some((field) => {
							if (field.contentRenderer?.textMatch) {
								return String(
									field.contentRenderer.textMatch(item)
								).match(regexp);
							}

							return String(item[field.name]).match(regexp);
						})
				  ) || []
				: initialItems
		);
	};

	const handleOnOrderChange = ({
		draggedIndex,
		targetIndex,
	}: {
		draggedIndex: number;
		targetIndex: number;
	}) => {
		const orderedItems = items.slice(0);

		orderedItems.splice(draggedIndex, 1);

		orderedItems.splice(targetIndex, 0, items[draggedIndex]);

		setItems(orderedItems);

		onOrderChange({
			orderedItems,
		});
	};

	return (
		<ClayLayout.Sheet
			className={classNames('orderable-table-sheet', className)}
		>
			{title && (
				<ClayLayout.SheetHeader>
					<h2 className="sheet-title">{title}</h2>
				</ClayLayout.SheetHeader>
			)}

			<ClayLayout.SheetSection>
				<ManagementToolbar.Container>
					<ManagementToolbar.ItemList expand>
						<ManagementToolbar.Item className="nav-item-expand">
							<Search onSearch={onSearch} query={query} />
						</ManagementToolbar.Item>

						{creationMenuItems?.length && (
							<ManagementToolbar.Item>
								{creationMenuItems.length > 1 ? (
									<ClayDropDownWithItems
										items={creationMenuItems}
										trigger={
											<ClayButtonWithIcon
												aria-label={creationMenuLabel}
												className="nav-btn nav-btn-monospaced"
												symbol="plus"
												title={creationMenuLabel}
											/>
										}
									/>
								) : (
									<ClayButtonWithIcon
										aria-label={
											creationMenuItems[0].label ??
											creationMenuLabel
										}
										className="nav-btn nav-btn-monospaced"
										onClick={creationMenuItems[0].onClick}
										symbol="plus"
										title={
											creationMenuItems[0].label ??
											creationMenuLabel
										}
									/>
								)}
							</ManagementToolbar.Item>
						)}
					</ManagementToolbar.ItemList>
				</ManagementToolbar.Container>

				{items.length ? (
					<ClayTable className="orderable-table">
						<ClayTable.Head>
							<ClayTable.Row>
								<ClayTable.Cell className="drag-handle-cell" />

								{fields.map((field) => (
									<ClayTable.Cell
										headingCell
										key={field.name}
									>
										{field.label}
									</ClayTable.Cell>
								))}

								{actions && (
									<ClayTable.Cell className="actions-cell" />
								)}
							</ClayTable.Row>
						</ClayTable.Head>

						<ClayTable.Body>
							<DndProvider backend={HTML5Backend}>
								{items.map((item, index) => (
									<OrderableTableRow
										actions={actions}
										fields={fields}
										index={index}
										item={item}
										key={item.id || index}
										onOrderChange={handleOnOrderChange}
										query={query}
									/>
								))}
							</DndProvider>
						</ClayTable.Body>
					</ClayTable>
				) : query ? (
					<ClayEmptyState
						description={Liferay.Language.get(
							'sorry,-no-results-were-found'
						)}
						title={Liferay.Language.get('no-results-found')}
					/>
				) : (
					<ClayEmptyState
						description={noItemsDescription}
						title={noItemsTitle}
					>
						{creationMenuItems?.length &&
							(creationMenuItems.length > 1 ? (
								<ClayDropDownWithItems
									alignmentPosition={4}
									items={creationMenuItems}
									trigger={
										<ClayButton
											aria-label={creationMenuLabel}
											displayType="secondary"
										>
											{noItemsButtonLabel}
										</ClayButton>
									}
								/>
							) : (
								<ClayButton
									displayType="secondary"
									onClick={creationMenuItems[0].onClick}
								>
									{noItemsButtonLabel}
								</ClayButton>
							))}
					</ClayEmptyState>
				)}
			</ClayLayout.SheetSection>

			{!!items.length && (
				<ClayLayout.SheetFooter>
					<ClayButton.Group spaced>
						<ClayButton
							disabled={disableSave}
							onClick={() => onSaveButtonClick()}
						>
							{Liferay.Language.get('save')}
						</ClayButton>

						<ClayButton
							displayType="secondary"
							onClick={() => onCancelButtonClick()}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>
					</ClayButton.Group>
				</ClayLayout.SheetFooter>
			)}
		</ClayLayout.Sheet>
	);
};

export default OrderableTable;
