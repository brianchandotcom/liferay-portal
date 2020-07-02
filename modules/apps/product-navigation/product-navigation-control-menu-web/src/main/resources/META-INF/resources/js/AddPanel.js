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

import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useMemo, useState} from 'react';
import {DndProvider} from 'react-dnd';
import HTML5Backend from 'react-dnd-html5-backend';

import DragAndDrop from './DragAndDrop';
import DragPreview from './DragPreview';
import TabsPanel from './TabsPanel';
import {LAYOUT_DATA_ITEM_TYPES} from './constants/layoutDataItemTypes';

const INITIAL_STATE = {
	addContentsURLs: null,
	contents: null,
	displayGrid: false,
	getContentsURL: null,
	namespace: null,
	plid: null,
	portletNamespace: null,
	setDisplayGrid: () => null,
	setWidgets: () => null,
	widgets: null,
};

export const AddPanelContext = React.createContext(INITIAL_STATE);
const AddPanelContextProvider = AddPanelContext.Provider;

const AddPanel = ({
	addContentsURLs,
	contents,
	getContentsURL,
	languageDirection,
	languageId,
	namespace,
	plid,
	portletNamespace,
	widgets: widgetsItems,
}) => {
	const [displayGrid, setDisplayGrid] = useState(false);
	const [widgets, setWidgets] = useState(widgetsItems);

	const rtl = languageDirection[languageId] === 'rtl';

	useEffect(() => {
		const removePortlet = (item) => {
			const portlet = {...item, itemId: item.portletId};
			const updatedWidgets = updateUsedWidget({
				item: portlet,
				used: false,
				widgets,
			});

			setWidgets(updatedWidgets);
		};

		Liferay.on('closePortlet', removePortlet);

		return () => {
			Liferay.detach('closePortlet', removePortlet);
		};
	}, [widgets]);

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
		<div
			className={classNames('sidebar-body__add-panel', {
				rtl,
			})}
		>
			<AddPanelContextProvider
				value={{
					addContentsURLs,
					contents,
					displayGrid,
					getContentsURL,
					namespace,
					plid,
					portletNamespace,
					setDisplayGrid,
					setWidgets,
					widgets,
				}}
			>
				<DndProvider backend={HTML5Backend}>
					<DragPreview rtl={rtl} />
					<DragAndDrop />
					<TabsPanel tabs={tabs} />
				</DndProvider>
			</AddPanelContextProvider>
		</div>
	);
};

export const updateUsedWidget = ({item, used = true, widgets}) => {
	return widgets.map((collection) => {
		return {
			...collection,
			portlets: collection.portlets.map((portlet) => {
				return portlet.portletId === item.itemId &&
					!portlet.instanceable
					? {...portlet, used}
					: portlet;
			}),
		};
	});
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
		itemId: `${content.portletId}_${content.classPK}`,
		label: content.title,
		type: LAYOUT_DATA_ITEM_TYPES.content,
	};
};

AddPanel.propTypes = {
	addContentsURLs: PropTypes.arrayOf(PropTypes.shape({})).isRequired,
	contents: PropTypes.arrayOf(PropTypes.shape({})).isRequired,
	getContentsURL: PropTypes.string.isRequired,
	languageDirection: PropTypes.shape({}),
	languageId: PropTypes.string.isRequired,
	namespace: PropTypes.string.isRequired,
	plid: PropTypes.string.isRequired,
	portletNamespace: PropTypes.string.isRequired,
	widgets: PropTypes.arrayOf(PropTypes.shape({})).isRequired,
};

export default AddPanel;
