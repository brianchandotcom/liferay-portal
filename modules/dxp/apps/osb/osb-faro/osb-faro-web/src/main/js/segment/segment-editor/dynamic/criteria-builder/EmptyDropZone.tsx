import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import React, {Component} from 'react';
import {
	AddProperty,
	withReferencedObjectsConsumer
} from '../context/referencedObjects';
import {compose} from 'redux';
import {
	ConnectDropTarget,
	DropTarget as dropTarget,
	DropTargetMonitor
} from 'react-dnd';
import {DragTypes} from '../utils/drag-types';
import {OnCriterionAdd} from '../utils/types';
import {Text} from '@clayui/core';

/**
 * Prevents items from being dropped from other contributors.
 * This method must be called `canDrop`.
 * @returns {boolean} True if the target should accept the item.
 */
const canDrop = (): boolean => true;

/**
 * Implements the behavior of what will occur when an item is dropped.
 * Adds the criterion dropped.
 * This method must be called `drop`.
 */
const drop = (
	{
		addProperty,
		onCriterionAdd
	}: {
		addProperty: AddProperty;
		onCriterionAdd: OnCriterionAdd;
	},
	monitor: DropTargetMonitor
): void => {
	const {criterion, property} = monitor.getItem();

	if (property) {
		addProperty(property);
	}

	onCriterionAdd(0, criterion);
};

interface IEmptyDropZone extends React.HTMLAttributes<HTMLDivElement> {
	addProperty: AddProperty;
	canDrop: boolean;
	connectDropTarget: ConnectDropTarget;
	enabledSequentialSegment: boolean;
	hover?: boolean;
	onCriterionAdd: OnCriterionAdd;
}

class EmptyDropZone extends Component<IEmptyDropZone> {
	render() {
		const {
			canDrop,
			connectDropTarget,
			enabledSequentialSegment,
			hover
		} = this.props;

		const targetClasses = getCN('empty-drop-zone-target', {
			'dnd-hover': canDrop && hover,
			'enabled-sequential-segment': enabledSequentialSegment
		});

		return (
			<div className='empty-drop-zone-root'>
				{connectDropTarget(
					<div className={targetClasses}>
						<div className='empty-drop-zone-indicator' />

						<div className='empty-drop-zone-message'>
							<div>
								<ClayIcon
									className='icon-root icon-size-md mr-3'
									symbol='ac_rule'
								/>

								<Text size={4}>
									{Liferay.Language.get(
										'drag-and-drop-criterion-from-the-right-to-add-rules'
									)}
								</Text>
							</div>

							{!enabledSequentialSegment && (
								<div>
									<ClayIcon
										className='icon-root icon-size-md mr-3'
										symbol='ac_group'
									/>

									<Text size={4}>
										{Liferay.Language.get(
											'drag-and-drop-over-an-existing-criteria-to-form-groups'
										)}
									</Text>
								</div>
							)}
						</div>
					</div>
				)}
			</div>
		);
	}
}

export default compose(
	withReferencedObjectsConsumer,
	dropTarget(
		DragTypes.Property,
		{
			canDrop,
			drop
		},
		(connect, monitor) => ({
			canDrop: monitor.canDrop(),
			connectDropTarget: connect.dropTarget(),
			hover: monitor.isOver()
		})
	)
)(EmptyDropZone);
