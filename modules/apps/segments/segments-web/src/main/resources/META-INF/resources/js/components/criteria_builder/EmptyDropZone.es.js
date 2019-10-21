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

import ThemeContext from '../../ThemeContext.es';
import {DragTypes} from '../../utils/drag-types.es';
import EmptyPlaceholder from './EmptyPlaceholder.es';

/**
 * Prevents items from being dropped from other contributors.
 * This method must be called `canDrop`.
 * @param {Object} props Component's current props.
 * @param {DropTargetMonitor} monitor
 * @returns {boolean} True if the target should accept the item.
 */
function canDrop(props, monitor) {
	const {propertyKey: destPropertyKey} = props;
	const {propertyKey: startPropertyKey} = monitor.getItem();

	return destPropertyKey === startPropertyKey;
}

/**
 * Implements the behavior of what will occur when an item is dropped.
 * Adds the criterion dropped.
 * This method must be called `drop`.
 * @param {Object} props Component's current props.
 * @param {DropTargetMonitor} monitor
 */
function drop(props, monitor) {
	const {criterion} = monitor.getItem();

	props.onCriterionAdd(0, criterion);
}

class EmptyDropZone extends Component {
	static contextType = ThemeContext;

	static propTypes = {
		canDrop: PropTypes.bool,
		connectDropTarget: PropTypes.func,
		emptyContributors: PropTypes.bool,
		hover: PropTypes.bool,
		onCriterionAdd: PropTypes.func.isRequired,
		propertyKey: PropTypes.string.isRequired
	};

	render() {
		const {
			canDrop,
			connectDropTarget,
			emptyContributors,
			hover
		} = this.props;

		const displayEmptyDropZone = canDrop || !emptyContributors;

		const emptyZoneClasses = getCN('empty-drop-zone-root', {
			'empty-drop-zone-dashed border-primary rounded':
				displayEmptyDropZone && (!canDrop || !hover)
		});

		const targetClasses = getCN(
			emptyContributors
				? 'empty-drop-zone-target'
				: 'drop-zone-target p-5',
			{
				'empty-drop-zone-target-solid dnd-hover border-primary rounded':
					canDrop && hover
			}
		);

		return (
			<div className={emptyZoneClasses}>
				{connectDropTarget(
					displayEmptyDropZone ? (
						<div className={targetClasses}>
							<div className="empty-drop-zone-indicator" />
						</div>
					) : (
						<div>
							<EmptyPlaceholder />
						</div>
					)
				)}
			</div>
		);
	}
}

export default dropTarget(
	DragTypes.PROPERTY,
	{
		canDrop,
		drop
	},
	(connect, monitor) => ({
		canDrop: monitor.canDrop(),
		connectDropTarget: connect.dropTarget(),
		hover: monitor.isOver()
	})
)(EmptyDropZone);
