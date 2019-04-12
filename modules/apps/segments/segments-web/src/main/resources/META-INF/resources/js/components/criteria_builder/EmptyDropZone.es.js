import getCN from 'classnames';
import PropTypes from 'prop-types';
import React, {Component} from 'react';
import ThemeContext from '../../ThemeContext.es';
import {DragTypes} from '../../utils/drag-types.es';
import {DropTarget as dropTarget} from 'react-dnd';

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

		const emptyZoneClasses = getCN(
			'empty-drop-zone-root',
			{
				'empty-drop-zone-dashed border-primary rounded': !canDrop || !hover
			}
		);

		const targetClasses = getCN(
			emptyContributors ? 'empty-drop-zone-target' : 'drop-zone-target p-5',
			{
				'empty-drop-zone-target-solid dnd-hover border-primary rounded': canDrop && hover
			}
		);

		return (
			<div className={emptyZoneClasses}>
				{connectDropTarget(
					<div className={targetClasses}>
						<div className="empty-drop-zone-indicator" />
					</div>
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