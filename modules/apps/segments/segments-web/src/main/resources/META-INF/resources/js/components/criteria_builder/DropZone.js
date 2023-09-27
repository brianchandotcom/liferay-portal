/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';
import {DropTarget as dropTarget} from 'react-dnd';

import {DragTypes} from '../../utils/drag-types';

const {CRITERIA_GROUP, CRITERIA_ROW, PROPERTY} = DragTypes;

const acceptedDragTypes = [CRITERIA_GROUP, CRITERIA_ROW, PROPERTY];

/**
 * Prevents groups from dropping within itself and all items from dropping into
 * a position that would not change its' current position.
 * This method must be called `canDrop`.
 * @param {Object} props Component's current props.
 * @param {DropTargetMonitor} monitor
 * @returns {boolean} True if the target should accept the item.
 */
function canDrop(props, monitor) {
	const {
		dropIndex: destIndex,
		groupId: destGroupId,
		propertyKey: destPropertyKey,
	} = props;

	const {
		childGroupIds = [],
		criterion,
		groupId: startGroupId,
		index: startIndex,
		propertyKey: startPropertyKey,
	} = monitor.getItem();

	const disallowedGroupIds = [criterion.groupId, ...childGroupIds];

	const sameOrNestedGroup =
		monitor.getItemType() === CRITERIA_GROUP &&
		disallowedGroupIds.includes(destGroupId);

	const sameIndexInSameGroup =
		startGroupId === destGroupId &&
		(startIndex === destIndex || startIndex === destIndex - 1);

	const samePropertyKey = destPropertyKey === startPropertyKey;

	return !(sameOrNestedGroup || sameIndexInSameGroup) && samePropertyKey;
}

/**
 * Implements the behavior of what will occur when an item is dropped.
 * For properties dropped from the sidebar, a new criterion will be added.
 * For rows and groups being dropped, they will be moved to the dropped
 * position.
 * This method must be called `drop`.
 * @param {Object} props Component's current props.
 * @param {DropTargetMonitor} monitor
 */
function drop(props, monitor) {
	const {
		dropIndex: destIndex,
		groupId: destGroupId,
		onCriterionAdd,
		onMove,
	} = props;

	const {
		criterion,
		groupId: startGroupId,
		index: startIndex,
	} = monitor.getItem();

	const itemType = monitor.getItemType();

	if (itemType === PROPERTY) {
		onCriterionAdd(destIndex, criterion);
	}
	else if (itemType === CRITERIA_ROW || itemType === CRITERIA_GROUP) {
		onMove(startGroupId, startIndex, destGroupId, destIndex, criterion);
	}
}

function DropZone({before, canDrop, connectDropTarget, hover}) {
	return (
		<div className="drop-zone-root position-relative">
			{connectDropTarget(
				<div
					className={classNames('drop-zone-target', {
						'drop-zone-target-before': before,
					})}
				>
					{canDrop && hover && (
						<div className="drop-zone-indicator w-100" />
					)}
				</div>
			)}
		</div>
	);
}

DropZone.propTypes = {
	before: PropTypes.bool,
	canDrop: PropTypes.bool,
	connectDropTarget: PropTypes.func,
	hover: PropTypes.bool,
};

export default dropTarget(
	acceptedDragTypes,
	{
		canDrop,
		drop,
	},
	(connect, monitor) => ({
		canDrop: monitor.canDrop(),
		connectDropTarget: connect.dropTarget(),
		hover: monitor.isOver(),
	})
)(DropZone);
