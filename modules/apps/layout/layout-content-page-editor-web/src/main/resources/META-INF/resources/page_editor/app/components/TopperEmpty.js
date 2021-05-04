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

import {getLayoutDataItemPropTypes} from '../../prop-types/index';
import {useSelector} from '../contexts/StoreContext';
import selectCanUpdatePageStructure from '../selectors/selectCanUpdatePageStructure';
import {TARGET_POSITIONS} from '../utils/drag-and-drop/constants/targetPositions';
import {useDropTarget} from '../utils/drag-and-drop/useDragAndDrop';
import getLayoutDataItemLabel from '../utils/getLayoutDataItemLabel';

export default function ({children, ...props}) {
	const canUpdatePageStructure = useSelector(selectCanUpdatePageStructure);

	return canUpdatePageStructure ? (
		<TopperEmpty {...props}>{children}</TopperEmpty>
	) : (
		children
	);
}

function TopperEmpty({children, item}) {
	const containerRef = useRef(null);
	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);

	const {
		canDropOverTarget,
		isOverTarget,
		sourceItem,
		targetPosition,
		targetRef,
	} = useDropTarget(item);

	const isFragment = children.type === React.Fragment;
	const realChildren = isFragment ? children.props.children : children;

	const notDroppableMessage =
		isOverTarget && !canDropOverTarget
			? Liferay.Util.sub(
					Liferay.Language.get('a-x-cannot-be-dropped-inside-a-x'),
					[
						getLayoutDataItemLabel(sourceItem, fragmentEntryLinks),
						getLayoutDataItemLabel(item, fragmentEntryLinks),
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
							targetPosition === TARGET_POSITIONS.BOTTOM,
						'drag-over-middle':
							isOverTarget &&
							targetPosition === TARGET_POSITIONS.MIDDLE,
						'drag-over-top':
							isOverTarget &&
							targetPosition === TARGET_POSITIONS.TOP,
						'not-droppable': !!notDroppableMessage,
						'page-editor__topper': true,
					}),
					'data-not-droppable-message': notDroppableMessage,
					ref: (node) => {
						containerRef.current = node;
						targetRef(node);

						// Call the original ref, if any.

						if (typeof child.ref === 'function') {
							child.ref(node);
						}
						else if (child.ref && 'current' in child.ref) {
							child.ref.current = node;
						}
					},
				})}
			</>
		);
	});
}

TopperEmpty.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
};
