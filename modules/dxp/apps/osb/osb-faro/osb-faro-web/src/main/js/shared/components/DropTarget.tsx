import getCN from 'classnames';
import HTML5Backend, {NativeTypes} from 'react-dnd-html5-backend';
import React from 'react';
import {DndProvider, useDrop} from 'react-dnd';

interface ITargetProps extends React.HTMLAttributes<HTMLElement> {
	message: React.ReactNode;
	targetType: string;
}

const Target: React.FC<ITargetProps> = ({
	children,
	message,
	onDrop,
	targetType
}) => {
	const [{isOver}, drop] = useDrop({
		accept: targetType,
		collect: (monitor: any) => ({
			endDrag: monitor.getDropResult(),
			isOver: monitor.isOver()
		}),
		drop: (item: object, monitor: any) => {
			if (onDrop) {
				onDrop(monitor.getItem());
			}
		}
	});

	return (
		<div
			className={getCN('drop-target-container', {
				'file-over': isOver
			})}
			ref={drop}
		>
			{children}

			<div className='drop-zone'>{message}</div>
		</div>
	);
};

export const TYPES = NativeTypes;
export default Object.assign(
	props => (
		<DndProvider backend={HTML5Backend}>
			<Target {...props} />
		</DndProvider>
	),
	{TYPES: NativeTypes}
);
