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

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {useRef} from 'react';
import {useDragLayer} from 'react-dnd';

import {ITEM_ACTIVATION_ORIGINS} from '../config/constants/itemActivationOrigins';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {useSelector} from '../contexts/StoreContext';
import {useWidgets} from '../contexts/WidgetsContext';
import selectLanguageId from '../selectors/selectLanguageId';
import getWidget from '../utils/getWidget';

function getItemIcon(item, fragmentEntryLinks, fragments, widgets) {
	const fragmentEntries = fragments.flatMap(
		(collection) => collection.fragmentEntries
	);

	if (item.type === LAYOUT_DATA_ITEM_TYPES.fragment) {
		const fragmentEntryLink =
			fragmentEntryLinks[item.config.fragmentEntryLinkId];

		if (fragmentEntryLink.portletId) {
			const widget = getWidget(widgets, fragmentEntryLink.portletId);

			return widget.instanceable ? 'square-hole-multi' : 'square-hole';
		}

		return fragmentEntries.find(
			(fragment) =>
				fragment.fragmentEntryKey === fragmentEntryLink.fragmentEntryKey
		).icon;
	}

	return fragmentEntries.find((fragment) => fragment.type === item.type)
		?.icon;
}

const getItemStyles = (currentOffset, ref, rtl) => {
	if (!currentOffset || !ref.current) {
		return {
			display: 'none',
		};
	}

	const rect = ref.current.getBoundingClientRect();
	const x = rtl
		? currentOffset.x + rect.width * 0.5 - window.innerWidth
		: currentOffset.x - rect.width * 0.5;
	const y = currentOffset.y - rect.height * 0.5;

	const transform = `translate(${x}px, ${y}px)`;

	return {
		WebkitTransform: transform,
		transform,
	};
};

export default function DragPreview() {
	const ref = useRef();

	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const fragments = useSelector((state) => state.fragments);
	const languageId = useSelector(selectLanguageId);
	const layoutData = useSelector((state) => state.layoutData);
	const widgets = useWidgets();

	const {currentOffset, isDragging, item} = useDragLayer((monitor) => ({
		currentOffset: monitor.getClientOffset(),
		isDragging: monitor.isDragging(),
		item: monitor.getItem(),
	}));

	if (!isDragging || !item?.id) {
		return null;
	}

	const layoutDataItem = layoutData.items[item?.id];

	const icon =
		item?.icon ||
		getItemIcon(layoutDataItem, fragmentEntryLinks, fragments, widgets);

	return (
		<div className="page-editor__drag-preview">
			<div
				className={classNames('page-editor__drag-preview__content', {
					'page-editor__drag-preview__content__treeview':
						item?.origin === ITEM_ACTIVATION_ORIGINS.sidebar,
				})}
				dir={Liferay.Language.direction[themeDisplay?.getLanguageId()]}
				ref={ref}
				style={getItemStyles(
					currentOffset,
					ref,
					Liferay.Language.direction[languageId] === 'rtl'
				)}
			>
				{icon && (
					<div className="align-items-center d-flex h-100">
						<ClayIcon className="mt-0" symbol={icon} />
					</div>
				)}
				<span className="ml-3 text-truncate">
					{item?.name ? item.name : Liferay.Language.get('element')}
				</span>
			</div>
		</div>
	);
}
