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
import React, {useRef} from 'react';

import useDragAndDrop, {EDGE} from './useDragAndDrop';

export default function TopperEmpty({acceptDrop, children, item, layoutData}) {
	const containerRef = useRef(null);

	const {canDrop, drop, edge, isDragging, isOver} = useDragAndDrop({
		accept: acceptDrop,
		containerRef,
		item,
		layoutData
	});

	const childrenElement = children({canDrop, isOver});

	const isFragment = childrenElement.type === React.Fragment;
	const realChildren = isFragment
		? childrenElement.props.children
		: childrenElement;

	return React.Children.map(realChildren, child => {
		if (!child) {
			return child;
		}

		return React.cloneElement(child, {
			...child.props,
			className: classNames(child.props.className, {
				'drag-over-bottom': edge === EDGE.BOTTOM && isOver,
				'drag-over-top': edge === EDGE.TOP && isOver,
				dragged: isDragging,
				'page-editor-topper': true
			}),
			ref: node => {
				containerRef.current = node;
				drop(node);

				// Call the original ref, if any.
				if (typeof child.props.ref === 'function') {
					child.props.ref(node);
				}
			}
		});
	});
}
