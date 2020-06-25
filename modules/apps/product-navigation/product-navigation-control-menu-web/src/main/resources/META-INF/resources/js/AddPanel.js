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

import React, {useMemo, useState} from 'react';
import {DndProvider} from 'react-dnd';
import HTML5Backend from 'react-dnd-html5-backend';

import {AddPanelContextProvider} from './AddPanelContext';
import DragAndDrop from './DragAndDrop';
import TabsPanel from './TabsPanel';

import 'product-navigation-control-menu/css/AddPanel.scss';

export const LAYOUT_DATA_ITEM_TYPES = {
	content: 'content',
	widget: 'widget',
};

const AddPanel = ({
	addContentsURLs,
	contents,
	getContentsURL,
	namespace,
	portletNamespace,
	widgets,
}) => {
	const [displayGrid, setDisplayGrid] = useState(false);

	const tabs = useMemo(
		() => [
			{
				collections: widgets.map((collection) => ({
					children: collection.portlets.map(normalizeWidget),
					collectionId: collection.path,
					label: collection.title,
				})),
				id: 'widgets',
				label: Liferay.Language.get('widgets'),
			},
			{
				collections: [
					{
						children: contents.map(normalizeContent),
						collectionId: 'recent-content',
						label: Liferay.Language.get('recent'),
					},
				],
				id: 'content',
				label: Liferay.Language.get('content'),
			},
		],
		[contents, widgets]
	);

	return (
		<div className="sidebar-content__add-panel">
			<AddPanelContextProvider
				value={{
					addContentsURLs,
					contents,
					displayGrid,
					getContentsURL,
					namespace,
					portletNamespace,
					setDisplayGrid,
					widgets,
				}}
			>
				<DndProvider backend={HTML5Backend}>
					<DragAndDrop />
					<TabsPanel tabs={tabs} />
				</DndProvider>
			</AddPanelContextProvider>
		</div>
	);
};

const normalizeWidget = (widget) => {
	return {
		data: {
			instanceable: widget.instanceable,
			portletId: widget.portletId,
			used: widget.used,
		},
		disabled: !widget.instanceable && widget.used,
		icon: widget.instanceable ? 'cards2' : 'square-hole',
		itemId: widget.portletId,
		label: widget.title,
		type: LAYOUT_DATA_ITEM_TYPES.widget,
	};
};

export const normalizeContent = (content) => {
	return {
		category: content.type,
		data: {
			className: content.className,
			classPK: content.classPK,
			draggable: content.draggable,
			instanceable: content.instanceable,
			portletId: content.portletId,
		},
		icon: content.icon,
		itemId: content.portletId,
		label: content.title,
		type: LAYOUT_DATA_ITEM_TYPES.content,
	};
};

export default AddPanel;
