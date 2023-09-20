/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayCard from '@clayui/card';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {ContentCol} from '@clayui/layout';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import React, {useCallback, useEffect, useRef, useState} from 'react';
import {DragObjectWithType, useDrag, useDrop} from 'react-dnd';
import {getEmptyImage} from 'react-dnd-html5-backend';

import {config} from '../../../../app/config/index';
import {
	DRAG_OVER_POSITIONS,
	DragOverPosition,
} from '../../config/constants/dragOverPositions';

const ACCEPTING_ITEM_TYPE = 'acceptingItemType';

export interface Item {
	id: string;
	name: string;
}

interface ItemProps {
	index: number;
	item: Item;
	numberOfItems: number;
	onDropItem: (
		itemId: Item['id'],
		index: number,
		dragOverPosition?: DragOverPosition
	) => void;
}

export function Item({index, item, numberOfItems, onDropItem}: ItemProps) {
	const {name} = item;

	const {
		handlerRef: mouseDragHandlerRef,
		isDragging: isMouseDragging,
	} = useMouseDragItem(item);

	const {
		dragOverPosition: mouseDragOverPosition,
		targetRef: mouseDropTargetRef,
	} = useMouseDropTarget(item.id, index, onDropItem);

	return (
		<div className="c-pb-3" ref={mouseDropTargetRef}>
			<div ref={mouseDragHandlerRef}>
				<ClayCard
					className={classNames('c-mb-0', {
						dragging: isMouseDragging,
						draggingOver: mouseDragOverPosition,
						draggingOverBottom:
							mouseDragOverPosition ===
							DRAG_OVER_POSITIONS.bottom,
						draggingOverTop:
							mouseDragOverPosition === DRAG_OVER_POSITIONS.top,
					})}
				>
					<ClayCard.Body className="px-0">
						<ClayCard.Row className="align-items-center">
							<ContentCol gutters>
								<ClayIcon
									className="text-secondary"
									symbol="drag"
								/>
							</ContentCol>

							<ContentCol expand>
								<ClayCard.Description
									className="text-uppercase"
									displayType="title"
									title={name}
								>
									{name}
								</ClayCard.Description>
							</ContentCol>

							{!Liferay.FeatureFlags['LPS-196420'] && (
								<ContentCol gutters>
									<ReorderDropdown
										index={index}
										item={item}
										numberOfItems={numberOfItems}
										onDropItem={onDropItem}
									/>
								</ContentCol>
							)}
						</ClayCard.Row>
					</ClayCard.Body>
				</ClayCard>
			</div>
		</div>
	);
}

export function ReorderDropdown({
	index,
	item,
	numberOfItems,
	onDropItem,
}: ItemProps) {
	const items = [
		{
			disabled: index === 0,
			label: Liferay.Language.get('move-up'),
			onClick: () => onDropItem(item.id, index - 1),
			symbolLeft: 'angle-up',
		},
		{
			disabled: index === numberOfItems - 1,
			label: Liferay.Language.get('move-down'),
			onClick: () => onDropItem(item.id, index + 1),
			symbolLeft: 'angle-down',
		},
	];

	return (
		<ClayDropDownWithItems
			items={items}
			trigger={
				<ClayButtonWithIcon
					aria-label={sub(Liferay.Language.get('move-x'), item.name)}
					className="text-secondary"
					displayType="unstyled"
					size="sm"
					symbol="ellipsis-v"
				/>
			}
		/>
	);
}

function useMouseDragItem(item: Item) {
	const [{isDragging}, handlerRef, previewRef] = useDrag({
		begin() {},
		collect: (monitor) => ({
			isDragging: monitor.isDragging(),
		}),
		item: {
			...item,
			namespace: config.portletNamespace,
			type: ACCEPTING_ITEM_TYPE,
		},
	});

	useEffect(() => {
		previewRef(getEmptyImage(), {captureDraggingState: true});
	}, [previewRef]);

	return {
		handlerRef,
		isDragging,
	};
}

export function useMouseDropTarget(
	itemId: string,
	itemIndex: number,
	onDropItem: ItemProps['onDropItem']
) {
	const [dragOverPosition, setDragOverPosition] = useState<
		DragOverPosition | undefined
	>();
	const targetRef = useRef<HTMLElement | null>(null);
	const targetRectRef = useRef<DOMRect | null>(null);

	const [{isOver}, internalSetTargetRef] = useDrop<
		DragObjectWithType & Item,
		void,
		{isOver: boolean}
	>({
		accept: ACCEPTING_ITEM_TYPE,
		canDrop(sourceItem, monitor) {
			return sourceItem.id !== itemId && monitor.isOver();
		},
		collect(monitor) {
			return {
				isOver: monitor.isOver(),
			};
		},
		drop(source, monitor) {
			targetRectRef.current = null;

			if (Liferay.FeatureFlags['LPS-196420'] && monitor.canDrop()) {
				onDropItem(source.id, itemIndex, dragOverPosition);
			}
		},
		hover(source, monitor) {
			if (!monitor.isOver()) {
				targetRectRef.current = null;

				return;
			}

			if (Liferay.FeatureFlags['LPS-196420']) {
				targetRectRef.current =
					targetRectRef.current ||
					targetRef.current!.getBoundingClientRect();

				const targetMiddlePosition =
					targetRectRef.current!.top +
					targetRectRef.current!.height / 2;

				if (monitor.getClientOffset()!.y < targetMiddlePosition) {
					setDragOverPosition(DRAG_OVER_POSITIONS.top);
				}
				else {
					setDragOverPosition(DRAG_OVER_POSITIONS.bottom);
				}
			}
			else if (monitor.canDrop()) {
				onDropItem(source.id, itemIndex);
			}
		},
	});

	const setTargetRef = useCallback(
		(targetElement) => {
			internalSetTargetRef(targetElement);
			targetRef.current = targetElement;
		},
		[internalSetTargetRef]
	);

	return {
		dragOverPosition: isOver ? dragOverPosition : null,
		targetRef: setTargetRef,
	};
}
