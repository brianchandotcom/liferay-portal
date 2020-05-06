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

import {
	LayoutDataPropTypes,
	getLayoutDataItemPropTypes,
} from '../../prop-types/index';
import selectCanUpdate from '../selectors/selectCanUpdate';
import {useSelector} from '../store/index';
import {TARGET_POSITION, useDropTarget} from '../utils/useDragAndDrop';
import getLabelName from './layout-data-items/getLabelName';

export default function ({children, ...props}) {
	const canUpdate = useSelector(selectCanUpdate);

	return canUpdate ? (
		<TopperEmpty {...props}>{children}</TopperEmpty>
	) : (
		children
	);
}

function TopperEmpty({children, item, layoutData}) {
	const containerRef = useRef(null);
	const store = useSelector((state) => state);
	const fragmentEntryLinks = store.fragmentEntryLinks;

	const {
		canDropOverTarget,
		isOverTarget,
		sourceItem,
		targetPosition,
		targetRef,
	} = useDropTarget(item, layoutData);

	const isFragment = children.type === React.Fragment;
	const realChildren = isFragment ? children.props.children : children;

	const dataAdvice =
		isOverTarget && !canDropOverTarget
			? Liferay.Util.sub(
					Liferay.Language.get('a-x-cannot-be-dropped-inside-a-x'),
					[
						getLabelName(sourceItem, fragmentEntryLinks),
						getLabelName(item, fragmentEntryLinks),
					]
			  )
			: null;

	return React.Children.map(realChildren, (child) => {
		if (!child) {
			return child;
		}

		return (
			<>
				{React.cloneElement(child, {
					...child.props,
					className: classNames(child.props.className, {
						'drag-over-bottom':
							isOverTarget &&
							targetPosition === TARGET_POSITION.BOTTOM,
						'drag-over-middle':
							isOverTarget &&
							targetPosition === TARGET_POSITION.MIDDLE,
						'drag-over-top':
							isOverTarget &&
							targetPosition === TARGET_POSITION.TOP,
						'not-droppable': !!dataAdvice,
						'page-editor__topper': true,
					}),
					'data-advice': dataAdvice,
					ref: (node) => {
						containerRef.current = node;
						targetRef(node);

						// Call the original ref, if any.

						if (typeof child.ref === 'function') {
							child.ref(node);
						}
					},
				})}
			</>
		);
	});
}

TopperEmpty.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
	layoutData: LayoutDataPropTypes.isRequired,
};
