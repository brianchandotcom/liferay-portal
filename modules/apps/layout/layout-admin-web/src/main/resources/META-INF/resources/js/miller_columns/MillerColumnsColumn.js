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

import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import {throttle} from 'frontend-js-web';
import React, {useEffect, useRef} from 'react';
import {useDrop} from 'react-dnd';

import MillerColumnsItem from './MillerColumnsItem';
import {ACCEPTING_TYPES} from './constants';

const AUTOSCROLL_DELAY = 20;
const AUTOSCROLL_DISTANCE = 20;
const AUTOSCROLL_RANGE_LENGTH = 20;

const scroll = (columnsContainer, monitor) => {
	const clientOffset = monitor.getClientOffset();
	const containerRect = columnsContainer.current.getBoundingClientRect();

	const hoverClientX = containerRect.right - clientOffset?.x;

	if (hoverClientX < AUTOSCROLL_RANGE_LENGTH) {
		columnsContainer.current.scrollLeft += AUTOSCROLL_DISTANCE;
	}
	else if (hoverClientX > containerRect.width - AUTOSCROLL_RANGE_LENGTH) {
		columnsContainer.current.scrollLeft -= AUTOSCROLL_DISTANCE;
	}
};

const throttledScroll = throttle(scroll, AUTOSCROLL_DELAY);

const isValidTarget = (sources, parent) =>
	!sources.some(
		(source) =>
			!(
				parent &&
				(source.columnIndex > parent.columnIndex + 1 ||
					(source.columnIndex === parent.columnIndex + 1 &&
						source.parentId !== parent.id) ||
					(parent.parentable &&
						source.columnIndex <= parent.columnIndex &&
						!source.active))
			)
	);

const MillerColumnsColumn = ({
	columnItems = [],
	columnsContainer,
	isPrivateLayoutsEnabled,
	items,
	namespace,
	onItemDrop,
	onItemStayHover,
	parent,
	rtl,
}) => {
	const ref = useRef();

	const [{canDrop}, drop] = useDrop({
		accept: ACCEPTING_TYPES.ITEM,
		canDrop(source, monitor) {
			return (
				monitor.isOver({shallow: true}) &&
				isValidTarget(source.items, parent)
			);
		},
		collect: (monitor) => ({
			canDrop: !!monitor.canDrop(),
		}),
		drop(source) {
			if (canDrop) {
				onItemDrop(source.items, parent.id, columnItems.length);
			}
		},
		hover(source, monitor) {
			if (Liferay.Browser.isSafari() && !Liferay.Browser.isChrome()) {
				throttledScroll(columnsContainer, monitor);
			}
		},
	});

	useEffect(() => {
		drop(ref);
	}, [drop]);

	return (
		<ClayLayout.Col
			className={classNames(
				'miller-columns-col show-quick-actions-on-hover',
				{
					'drop-target': canDrop,
				}
			)}
			containerElement="ul"
			lg="4"
			md="6"
			ref={ref}
			size="11"
		>
			{columnItems.map((item, index) => (
				<MillerColumnsItem
					isPrivateLayoutsEnabled={isPrivateLayoutsEnabled}
					item={{...item, itemIndex: index}}
					items={items}
					key={item.key}
					namespace={namespace}
					onItemDrop={onItemDrop}
					onItemStayHover={onItemStayHover}
					rtl={rtl}
				/>
			))}
		</ClayLayout.Col>
	);
};

export default MillerColumnsColumn;
