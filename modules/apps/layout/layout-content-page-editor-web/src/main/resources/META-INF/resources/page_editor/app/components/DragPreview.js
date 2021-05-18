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
import {useSelector} from '../contexts/StoreContext';
import selectLanguageId from '../selectors/selectLanguageId';

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

	const languageId = useSelector(selectLanguageId);

	const {currentOffset, isDragging, item} = useDragLayer((monitor) => ({
		currentOffset: monitor.getClientOffset(),
		isDragging: monitor.isDragging(),
		item: monitor.getItem(),
	}));

	if (!isDragging) {
		return null;
	}

	return (
		<div className="page-editor__drag-preview">
			<div
				className={classNames('page-editor__drag-preview__content', {
					'page-editor__drag-preview__content__treeview':
						item?.origin === ITEM_ACTIVATION_ORIGINS.sidebar,
				})}
				ref={ref}
				style={getItemStyles(
					currentOffset,
					ref,
					Liferay.Language.direction[languageId] === 'rtl'
				)}
			>
				{item?.icon && <ClayIcon className="mt-0" symbol={item.icon} />}
				{item?.name ? item.name : Liferay.Language.get('element')}
			</div>
		</div>
	);
}
