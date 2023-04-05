import getCN from 'classnames';
import React from 'react';
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
import {OnCriterionAdd, OnMove} from '../utils/types';

const acceptedDragTypes = [
	DragTypes.CriteriaGroup,
	DragTypes.CriteriaRow,
	DragTypes.Property
];

/**
 * Prevents groups from dropping within itself and all items from dropping into
 * a position that would not change its' current position.
 * This method must be called `canDrop`.
 * @returns {boolean} True if the target should accept the item.
 */
const canDrop = (
	{
		criteriaGroupId: destGroupId,
		dropIndex: destIndex
	}: {
		criteriaGroupId: string;
		dropIndex: number;
	},
	monitor: DropTargetMonitor
): boolean => {
	const {
		childGroupIds = [],
		criteriaGroupId: startGroupId,
		criterion,
		index: startIndex
	} = monitor.getItem();

	const disallowedGroupIds = [criterion.criteriaGroupId, ...childGroupIds];

	const sameOrNestedGroup =
		monitor.getItemType() === DragTypes.CriteriaGroup &&
		disallowedGroupIds.includes(destGroupId);

	const sameIndexInSameGroup =
		startGroupId === destGroupId &&
		(startIndex === destIndex || startIndex === destIndex - 1);

	return !(sameOrNestedGroup || sameIndexInSameGroup);
};

/**
 * Implements the behavior of what will occur when an item is dropped.
 * For properties dropped from the sidebar, a new criterion will be added.
 * For rows and groups being dropped, they will be moved to the dropped
 * position.
 * This method must be called `drop`.
 * @param {Object} props Component's current props.
 * @param {DropTargetMonitor} monitor
 */
const drop = (
	{
		addProperty,
		criteriaGroupId: destGroupId,
		dropIndex: destIndex,
		onCriterionAdd,
		onMove
	}: {
		addProperty: AddProperty;
		criteriaGroupId: string;
		dropIndex: number;
		onCriterionAdd: OnCriterionAdd;
		onMove: OnMove;
	},
	monitor: DropTargetMonitor
): void => {
	const {
		criteriaGroupId: startGroupId,
		criterion,
		id,
		index: startIndex,
		property
	} = monitor.getItem();

	const itemType = monitor.getItemType();

	if (itemType === DragTypes.Property && !id) {
		const {entityName, type} = property;

		analytics.track('Dynamic Segment Creation - Added Attribute', {
			entityName,
			type
		});
	}

	if (property) {
		addProperty(property);
	}

	if (itemType === DragTypes.Property) {
		onCriterionAdd(destIndex, criterion);
	} else if (
		itemType === DragTypes.CriteriaRow ||
		itemType === DragTypes.CriteriaGroup
	) {
		onMove(startGroupId, startIndex, destGroupId, destIndex, criterion);
	}
};

interface IDropZoneProps {
	addProperty: AddProperty;
	before?: boolean;
	canDrop: boolean;
	connectDropTarget: ConnectDropTarget;
	criteriaGroupId: string;
	dropIndex: number;
	hover?: boolean;
	onCriterionAdd: OnCriterionAdd;
	onMove: OnMove;
}

const DropZone: React.FC<IDropZoneProps> = ({
	before,
	canDrop,
	connectDropTarget,
	hover
}) => (
	<div className='drop-zone-root'>
		{connectDropTarget(
			<div
				className={getCN('drop-zone-target', {
					'drop-zone-target-before': before
				})}
			>
				{canDrop && hover && <div className='drop-zone-indicator' />}
			</div>
		)}
	</div>
);

export default compose(
	withReferencedObjectsConsumer,
	dropTarget(
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
	)
)(DropZone);
