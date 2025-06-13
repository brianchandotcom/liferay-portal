/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox, ClayRadio} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayList from '@clayui/list';
import ClaySticker from '@clayui/sticker';
import classNames from 'classnames';
import React, {forwardRef, useCallback, useContext, useRef} from 'react';
import {type DropTargetMonitor, useDrop} from 'react-dnd';
import {NativeTypes} from 'react-dnd-html5-backend';

import FrontendDataSetContext from '../../FrontendDataSetContext';
import Actions from '../../actions/Actions';
import ImageRenderer from '../../cell_renderers/ImageRenderer';
import GatedDndProvider, {isFileDropEnabled} from '../../drop/GatedDndProvider';
import {getLocalizedValue} from '../../utils/getLocalizedValue';
import {IHeader, IListSchema, IListTitleRenderer} from '../../utils/types';

const Title = ({
	item,
	title,
	titleRenderer,
}: {
	item: any;
	title: string;
	titleRenderer: IListTitleRenderer;
}) => {
	const TitleRendererComponent = titleRenderer?.component;

	if (TitleRendererComponent) {
		return <TitleRendererComponent itemData={item} />;
	}

	if (title) {
		return (
			<ClayList.ItemTitle>
				{getLocalizedValue(item, title)?.value}
			</ClayList.ItemTitle>
		);
	}

	return null;
};

const ListItem = forwardRef<HTMLLIElement, any>(
	(
		{
			className,
			item,
			schema,
		}: {className: string; item: any; schema: IListSchema},
		ref
	) => {
		const {
			itemsActions,
			onSelect,
			selectItems,
			selectable,
			selectedItemsKey,
			selectedItemsValue,
			selectionType,
		} = useContext(FrontendDataSetContext);

		const {description, image, sticker, symbol, title, titleRenderer} =
			schema;

		const SelectionInput =
			selectionType === 'single' ? ClayRadio : ClayCheckbox;

		return (
			<ClayList.Item
				className={classNames(className, {
					active: selectedItemsValue.includes(item[selectedItemsKey]),
				})}
				flex
				onClick={() => {
					if (selectable) {
						selectItems(item[selectedItemsKey]);

						onSelect?.({selectedItems: [item]});
					}
				}}
				ref={ref}
			>
				{selectable && (
					<ClayList.ItemField className="justify-content-center selection-control">
						<SelectionInput
							checked={
								selectedItemsValue
									? selectedItemsValue
											.map((element) => String(element))
											.includes(
												String(item[selectedItemsKey])
											)
									: false
							}
							onChange={() => {}}
							value={item[selectedItemsKey]}
						/>
					</ClayList.ItemField>
				)}

				{image && item[image] ? (
					<ClayList.ItemField>
						<ImageRenderer
							sticker={sticker && item[sticker]}
							value={item[image]}
						/>
					</ClayList.ItemField>
				) : (
					symbol &&
					item[symbol] && (
						<ClayList.ItemField>
							<ClaySticker {...(sticker && item[sticker])}>
								{item[symbol] && (
									<ClayIcon symbol={item[symbol]} />
								)}
							</ClaySticker>
						</ClayList.ItemField>
					)
				)}

				<ClayList.ItemField className="justify-content-center" expand>
					<Title
						item={item}
						title={title}
						titleRenderer={titleRenderer}
					/>

					{description && (
						<ClayList.ItemText>
							{getLocalizedValue(item, description)?.value}
						</ClayList.ItemText>
					)}
				</ClayList.ItemField>

				<ClayList.ItemField>
					{(itemsActions || item.actionDropdownItems) && (
						<Actions
							actions={itemsActions || item.actionDropdownItems}
							itemData={item}
							itemId={item[selectedItemsKey]}
						/>
					)}
				</ClayList.ItemField>
			</ClayList.Item>
		);
	}
);

const ListItemOptionalDropTarget = ({
	item,
	schema,
}: {
	item: any;
	schema: IListSchema;
}) => {
	const {fileDropSettings, handleFileDrop} = useContext(
		FrontendDataSetContext
	);

	const nonDroppableRef = useRef(null);

	const canDrop = useCallback(
		(item: any) =>
			fileDropSettings?.canDrop ? fileDropSettings.canDrop({item}) : true,
		[fileDropSettings]
	);

	const [{isOverCurrent}, dropRef] = useDrop({
		accept: isFileDropEnabled(fileDropSettings) ? [NativeTypes.FILE] : [],
		canDrop() {
			return isFileDropEnabled(fileDropSettings) && canDrop(item);
		},
		collect: (monitor: DropTargetMonitor) => {
			return {
				isOverCurrent:
					isFileDropEnabled(fileDropSettings) &&
					canDrop(item) &&
					monitor.isOver({shallow: true}),
			};
		},
		drop(fileItem: any, monitor) {
			if (monitor.isOver({shallow: true})) {
				handleFileDrop(fileItem, item);
			}
		},
	});

	return (
		<ListItem
			className={classNames({'list-drop-target': isOverCurrent})}
			item={item}
			ref={canDrop(item) ? dropRef : nonDroppableRef}
			schema={schema}
		/>
	);
};

const List = ({
	header,
	items,
	schema,
}: {
	header: IHeader;
	items: any[];
	schema: IListSchema;
}) => {
	const {selectedItemsKey} = useContext(FrontendDataSetContext);

	if (!items?.length) {
		return null;
	}

	return (
		<ClayLayout.Sheet
			className={classNames('list-sheet', {
				'no-header': !header?.title,
			})}
		>
			{header?.title && (
				<ClayLayout.SheetHeader className="mb-4">
					<h2 className="sheet-title">{header?.title}</h2>
				</ClayLayout.SheetHeader>
			)}

			<GatedDndProvider>
				<ClayList>
					{items.map((item: any, index: number) => (
						<ListItemOptionalDropTarget
							item={item}
							key={
								selectedItemsKey
									? item[selectedItemsKey]
									: index
							}
							schema={schema}
						/>
					))}
				</ClayList>
			</GatedDndProvider>
		</ClayLayout.Sheet>
	);
};

export default List;
