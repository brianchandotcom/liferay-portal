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

import React, {useMemo} from 'react';

import 'product-navigation-control-menu/css/AddPanel.scss';

import TabsPanel from './TabsPanel';

export const LAYOUT_DATA_ITEM_TYPES = {
	fragment: 'fragment',
};

const AddPanel = ({contents, portletNamespace, widgets}) => {
	const tabs = useMemo(
		() => [
			{
				collections: widgets.map((collection) => ({
					children: collection.portlets.map(normalizeWidget),
					collectionId: collection.path,
					label: collection.title,
				})),
				label: Liferay.Language.get('widgets'),
			},
			{
				collections: [
					{
						children: contents.map(normalizeContent),
						label: Liferay.Language.get('recent'),
					},
				],
				label: Liferay.Language.get('content'),
			},
		],
		[contents, widgets]
	);

	return (
		<div className="sidebar-content__panel">
			<TabsPanel portletNamespace={portletNamespace} tabs={tabs} />
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
		type: LAYOUT_DATA_ITEM_TYPES.fragment,
	};
};

const normalizeContent = (content) => {
	return {
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
		type: content.type,
	};
};

export default AddPanel;
