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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import classNames from 'classnames';
import React, {useRef, useState} from 'react';
import {useDrag, useDrop} from 'react-dnd';

import './BuilderListItem.scss';

interface IProps {
	disableEdit?: boolean;
	hasDragAndDrop?: boolean;
	index: number;
	label?: string;
	objectFieldName: string;
	onChangeColumnOrder?: (draggedIndex: number, targetIndex: number) => void;
	onDeleteColumn: (objectFieldName: string) => void;
	onEditing?: (boolean: boolean) => void;
	onEditingObjectFieldName?: (objectFieldName: string) => void;
	onVisibleEditModal?: (boolean: boolean) => void;
	secondColumnValue?: string;
	thirdColumnValues?: TThirdColumnValues[] | string;
}

type TThirdColumnValues = {
	label: string;
	value: string;
};

type TItemHover = {
	index: number;
	type: string;
};

type TDraggedOffset = {
	x: number;
	y: number;
} | null;

const BuilderListItem: React.FC<IProps> = ({
	disableEdit,
	hasDragAndDrop,
	index,
	label,
	objectFieldName,
	onChangeColumnOrder,
	onDeleteColumn,
	onEditing,
	onEditingObjectFieldName,
	onVisibleEditModal,
	secondColumnValue,
	thirdColumnValues,
}) => {
	const [active, setActive] = useState<boolean>(false);

	const ref = useRef<HTMLLIElement>(null);

	const [{isDragging}, dragRef] = useDrag({
		collect: (monitor) => ({
			isDragging: monitor.isDragging(),
		}),
		item: {
			index,
			type: 'FIELD',
		},
	});

	const [, dropRef] = useDrop({
		accept: 'FIELD',
		hover(item: TItemHover, monitor) {
			if (!ref.current || !onChangeColumnOrder) {
				return;
			}

			const draggedIndex = item.index;
			const targetIndex = index;

			if (draggedIndex === targetIndex) {
				return;
			}

			const targetSize = ref.current.getBoundingClientRect();
			const targetCenter = (targetSize.bottom - targetSize.top) / 2;

			const draggedOffset: TDraggedOffset = monitor.getClientOffset();

			if (!draggedOffset) {
				return;
			}

			const draggedTop = draggedOffset.y - targetSize.top;

			if (
				(draggedIndex < targetIndex && draggedTop < targetCenter) ||
				(draggedIndex > targetIndex && draggedTop > targetCenter)
			) {
				return;
			}

			onChangeColumnOrder(draggedIndex, targetIndex);

			item.index = targetIndex;
		},
	});

	dragRef(dropRef(ref));

	const handleEnableEditModal = (objectFieldName: string) => {
		onEditingObjectFieldName && onEditingObjectFieldName(objectFieldName);
		onEditing && onEditing(true);
		onVisibleEditModal && onVisibleEditModal(true);
	};

	return (
		<ClayList.Item
			className={classNames(
				'lfr-object__object-custom-view-builder-item',
				{
					'lfr-object__object-custom-view-builder-item--dragging': isDragging,
				}
			)}
			flex
			ref={hasDragAndDrop ? ref : null}
		>
			{hasDragAndDrop && (
				<ClayList.ItemField>
					<ClayButtonWithIcon displayType={null} symbol="drag" />
				</ClayList.ItemField>
			)}

			<ClayList.ItemField
				className={classNames(
					'lfr-object__object-builder-list-item-first-column',
					!hasDragAndDrop &&
						'lfr-object__object-builder-list-item-first-column--not-draggable'
				)}
				expand
			>
				<ClayList.ItemTitle>{label}</ClayList.ItemTitle>
			</ClayList.ItemField>

			<ClayList.ItemField
				className={classNames(
					'lfr-object__object-builder-list-item-second-column',
					!hasDragAndDrop &&
						'lfr-object__object-builder-list-item-second-column--not-draggable'
				)}
				expand
			>
				<ClayList.ItemText>{secondColumnValue}</ClayList.ItemText>
			</ClayList.ItemField>

			<ClayList.ItemField
				className={classNames(
					'lfr-object__object-builder-list-item-third-column',
					!hasDragAndDrop &&
						'lfr-object__object-builder-list-item-third-column--not-draggable'
				)}
				expand
			>
				<ClayList.ItemText>
					{Array.isArray(thirdColumnValues)
						? thirdColumnValues?.map((value, index) => {
								return index !== thirdColumnValues.length - 1
									? `${value.label}, `
									: value.label;
						  })
						: thirdColumnValues}
				</ClayList.ItemText>
			</ClayList.ItemField>

			<ClayDropDown
				active={active}
				menuElementAttrs={{
					className: 'lfr-object__object-builder-list-item-dropdown',
				}}
				onActiveChange={setActive}
				trigger={
					<ClayButtonWithIcon
						displayType="unstyled"
						symbol="ellipsis-v"
					/>
				}
			>
				<ClayDropDown.ItemList>
					{!disableEdit && (
						<ClayDropDown.Item
							onClick={() =>
								handleEnableEditModal(objectFieldName)
							}
						>
							<ClayIcon
								className="lfr-object__object-custom-view-builder-item-icon"
								symbol="pencil"
							/>

							{Liferay.Language.get('edit')}
						</ClayDropDown.Item>
					)}

					<ClayDropDown.Item
						onClick={() => onDeleteColumn(objectFieldName)}
					>
						<ClayIcon
							className="lfr-object__object-custom-view-builder-item-icon"
							symbol="trash"
						/>

						{Liferay.Language.get('delete')}
					</ClayDropDown.Item>
				</ClayDropDown.ItemList>
			</ClayDropDown>
		</ClayList.Item>
	);
};

export default BuilderListItem;
