/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayCard from '@clayui/card';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {ContentCol} from '@clayui/layout';
import classNames from 'classnames';
import {useId} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {RefObject} from 'react';

import {
	DRAG_OVER_POSITIONS,
	DragOverPosition,
} from '../../config/constants/dragOverPositions';
import {useKeyboardDragItem} from './KeyboardDragAndDropContext';
import {useMouseDragItem, useMouseDropTarget} from './MouseDragAndDropContext';

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
	const dragButtonDescriptionId = useId();
	const itemDescriptionId = useId();
	const {name} = item;

	const {
		handlerRef: mouseDragHandlerRef,
		isDragging: isMouseDragging,
	} = useMouseDragItem(item);

	const {
		dragOverPosition: keyboardDragOverPosition,
		handlerRef: keyboardDragHandlerRef,
		isDragging: isKeyboardDragging,
		targetRef: keyboardDropTargetRef,
	} = useKeyboardDragItem(item, onDropItem);

	const {
		dragOverPosition: mouseDragOverPosition,
		targetRef: mouseDropTargetRef,
	} = useMouseDropTarget(item.id, index, onDropItem);

	const targetRef = (element: HTMLDivElement | null) => {
		keyboardDropTargetRef(element);
		mouseDropTargetRef(element);
	};

	return (
		<div className="c-pb-3" ref={targetRef} role="listitem">
			<div ref={mouseDragHandlerRef}>
				<ClayCard
					className={classNames('c-mb-0', {
						dragging: isMouseDragging || isKeyboardDragging,
						draggingOver:
							mouseDragOverPosition || keyboardDragOverPosition,
						draggingOverBottom:
							mouseDragOverPosition ===
								DRAG_OVER_POSITIONS.bottom ||
							keyboardDragOverPosition ===
								DRAG_OVER_POSITIONS.bottom,
						draggingOverTop:
							mouseDragOverPosition === DRAG_OVER_POSITIONS.top ||
							keyboardDragOverPosition ===
								DRAG_OVER_POSITIONS.top,
					})}
				>
					<ClayCard.Body className="px-0">
						<ClayCard.Row className="align-items-center">
							<ContentCol gutters>
								{Liferay.FeatureFlags['LPS-196420'] ? (
									<ClayButton
										aria-labelledby={`${dragButtonDescriptionId} ${itemDescriptionId}`}
										data-item-id={item.id}
										displayType="unstyled"
										monospaced
										ref={
											(keyboardDragHandlerRef as unknown) as RefObject<
												HTMLButtonElement
											>
										}
										size="xs"
										tabIndex={-1}
									>
										<ClayIcon
											className="text-secondary"
											symbol="drag"
										/>

										<span
											className="sr-only"
											id={dragButtonDescriptionId}
										>
											{Liferay.Language.get('reorder')}
										</span>
									</ClayButton>
								) : (
									<ClayIcon
										className="text-secondary"
										symbol="drag"
									/>
								)}
							</ContentCol>

							<ContentCol expand>
								<ClayCard.Description
									className="text-uppercase"
									displayType="title"
									id={itemDescriptionId}
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
