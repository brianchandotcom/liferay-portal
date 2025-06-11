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
import React, {useContext} from 'react';

import FrontendDataSetContext from '../../FrontendDataSetContext';
import Actions from '../../actions/Actions';
import ImageRenderer from '../../cell_renderers/ImageRenderer';
import {IHeader, IListSchema, IListTitleRenderer} from '../../index';
import {getLocalizedValue} from '../../utils/getLocalizedValue';

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

const ListItem = ({item, schema}: {item: any; schema: IListSchema}) => {
	const {
		itemsActions,
		onSelect,
		selectItems,
		selectable,
		selectedItemsKey,
		selectedItemsValue,
		selectionType,
	} = useContext(FrontendDataSetContext);

	const {description, image, sticker, symbol, title, titleRenderer} = schema;

	const SelectionInput =
		selectionType === 'single' ? ClayRadio : ClayCheckbox;

	return (
		<ClayList.Item
			className={classNames({
				active: selectedItemsValue.includes(item[selectedItemsKey]),
			})}
			flex
			onClick={() => {
				if (selectable) {
					selectItems(item[selectedItemsKey]);

					onSelect?.({selectedItems: [item]});
				}
			}}
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
							{item[symbol] && <ClayIcon symbol={item[symbol]} />}
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

			<ClayList>
				{items.map((item: any, index: number) => (
					<ListItem
						item={item}
						key={selectedItemsKey ? item[selectedItemsKey] : index}
						schema={schema}
					/>
				))}
			</ClayList>
		</ClayLayout.Sheet>
	);
};

export default List;
