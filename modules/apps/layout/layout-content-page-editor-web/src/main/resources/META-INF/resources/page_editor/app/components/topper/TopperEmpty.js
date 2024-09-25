/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React, {useRef} from 'react';

import {getLayoutDataItemPropTypes} from '../../../prop_types/index';
import {ITEM_ACTIVATION_ORIGINS} from '../../config/constants/itemActivationOrigins';
import {
	useHoverItem,
	useIsActive,
	useIsHovered,
	useSelectItem,
} from '../../contexts/ControlsContext';
import {
	useMovementTarget,
	useMovementTargetPosition,
} from '../../contexts/KeyboardMovementContext';
import {useSelector, useSelectorCallback} from '../../contexts/StoreContext';
import selectCanUpdatePageStructure from '../../selectors/selectCanUpdatePageStructure';
import selectLayoutDataItemLabel from '../../selectors/selectLayoutDataItemLabel';
import {TARGET_POSITIONS} from '../../utils/drag_and_drop/constants/targetPositions';
import {
	useDropTarget,
	useIsDroppable,
} from '../../utils/drag_and_drop/useDragAndDrop';
import useDropContainerId from '../../utils/useDropContainerId';
import {TopperLabel} from './TopperLabel';

export default function ({children, item, ...props}) {
	const canUpdatePageStructure = useSelector(selectCanUpdatePageStructure);

	const isHovered = useIsHovered();
	const isActive = useIsActive();

	return canUpdatePageStructure ? (
		<MemoizedTopperEmpty
			{...props}
			isActive={isActive(item.itemId)}
			isHovered={isHovered(item.itemId)}
			item={item}
		>
			{children}
		</MemoizedTopperEmpty>
	) : (
		children
	);
}

const TopperEmpty = ({
	children,
	className,
	isActive,
	isHovered,
	item,
	itemElement,
}) => {
	const containerRef = useRef(null);

	const {isOverTarget, targetPosition, targetRef} = useDropTarget(item);
	const {itemId: movementTargetItemId} = useMovementTarget();
	const movementTargetPosition = useMovementTargetPosition();

	const dropTargetPosition = targetPosition || movementTargetPosition;

	const isFragment = children.type === React.Fragment;
	const realChildren = isFragment ? children.props.children : children;

	const dropContainerId = useDropContainerId();
	const isDroppable = useIsDroppable();

	const isValidDrop =
		(isDroppable && isOverTarget) || movementTargetItemId === item.itemId;

	const name = useSelectorCallback(
		(state) => selectLayoutDataItemLabel(state, item),
		[item]
	);

	const hoverItem = useHoverItem();
	const selectItem = useSelectItem();

	return React.Children.map(realChildren, (child) => {
		if (!child) {
			return child;
		}

		return (
			<>
				{React.cloneElement(child, {
					...child.props,
					className: classNames(
						child.props.className,
						className,
						'page-editor__topper',
						{
							'active': isActive,
							'drag-over-bottom':
								isValidDrop &&
								dropTargetPosition === TARGET_POSITIONS.BOTTOM,
							'drag-over-middle':
								isValidDrop &&
								dropTargetPosition === TARGET_POSITIONS.MIDDLE,
							'drag-over-top':
								isValidDrop &&
								dropTargetPosition === TARGET_POSITIONS.TOP,
							'drop-container': dropContainerId === item.itemId,
							'hovered': isHovered,
						}
					),
					onClick: (event) => {
						event.stopPropagation();

						selectItem(item.itemId, {
							origin: ITEM_ACTIVATION_ORIGINS.layout,
						});
					},
					onMouseLeave: (event) => {
						event.stopPropagation();

						if (isHovered) {
							hoverItem(null, {
								origin: ITEM_ACTIVATION_ORIGINS.layout,
							});
						}
					},
					onMouseOver: (event) => {
						event.stopPropagation();

						hoverItem(item.itemId, {
							origin: ITEM_ACTIVATION_ORIGINS.layout,
						});
					},
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

				{isActive ? (
					<TopperLabel itemElement={itemElement}>
						<ul className="tbar-nav">
							<li className="d-inline-block page-editor__topper__item page-editor__topper__title tbar-item tbar-item-expand">
								{name}
							</li>
						</ul>
					</TopperLabel>
				) : null}
			</>
		);
	});
};

const MemoizedTopperEmpty = React.memo(TopperEmpty);

TopperEmpty.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
};
