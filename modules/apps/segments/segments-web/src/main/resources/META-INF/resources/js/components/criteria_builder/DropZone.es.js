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

import getCN from 'classnames';
import PropTypes from 'prop-types';
import React, {Component} from 'react';
import {DropTarget as dropTarget} from 'react-dnd';

import {DragTypes} from '../../utils/drag-types.es';

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
		propertyKey: destPropertyKey
	} = props;

	const {
		childGroupIds = [],
		criterion,
		groupId: startGroupId,
		index: startIndex,
		propertyKey: startPropertyKey
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
		onMove
	} = props;

	const {
		criterion,
		groupId: startGroupId,
		index: startIndex
	} = monitor.getItem();

	const itemType = monitor.getItemType();

	if (itemType === PROPERTY) {
		onCriterionAdd(destIndex, criterion);
	}
	else if (itemType === CRITERIA_ROW || itemType === CRITERIA_GROUP) {
		onMove(startGroupId, startIndex, destGroupId, destIndex, criterion);
	}
}

class DropZone extends Component {
	static propTypes = {
		before: PropTypes.bool,
		canDrop: PropTypes.bool,
		connectDropTarget: PropTypes.func,
		dropIndex: PropTypes.number.isRequired,
		groupId: PropTypes.string.isRequired,
		hover: PropTypes.bool,
		onCriterionAdd: PropTypes.func.isRequired,
		onMove: PropTypes.func.isRequired,
		propertyKey: PropTypes.string.isRequired
	};

	render() {
		const {before, canDrop, connectDropTarget, hover} = this.props;

		const targetClasses = getCN('drop-zone-target', {
			'drop-zone-target-before': before
		});

		return (
			<div className="drop-zone-root">
				{connectDropTarget(
					<div className={targetClasses}>
						{canDrop && hover && (
							<div className="drop-zone-indicator" />
						)}
					</div>
				)}
			</div>
		);
	}
}

export default dropTarget(
	acceptedDragTypes,
	{
		canDrop,
		drop
	},
	(connect, monitor) => ({
		canDrop: monitor.canDrop(),
		connectDropTarget: connect.dropTarget(),
		hover: monitor.isOver()
	})
)(DropZone);
