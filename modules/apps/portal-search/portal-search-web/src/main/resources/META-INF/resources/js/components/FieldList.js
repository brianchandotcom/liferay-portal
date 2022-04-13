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

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput, ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React, {useRef} from 'react';
import {DndProvider, useDrag, useDrop} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

export const ITEM_TYPES = {
	FIELD: 'field',
};

export const ORDERS = {
	ASC: {
		label: Liferay.Language.get('ascending'),
		value: 'ascending',
	},
	DESC: {
		label: Liferay.Language.get('descending'),
		value: 'descending',
	},
};

/**
 * Moves an item in an array. Does not mutate the original array.
 * @param {Array} list The list to move an item.
 * @param {number} from The index of the item being moved.
 * @param {number} to The new index that the item will be moved to.
 *  @return {Array} Array of items with new order.
 */
export function move(list, from, to) {
	const listWithInserted = [
		...list.slice(0, to),
		list[from],
		...list.slice(to, list.length),
	];

	const updatedFrom = from > to ? from + 1 : from;

	return listWithInserted.filter((_, index) => index !== updatedFrom);
}

function DropZone({index, move}) {
	const [{isOver}, drop] = useDrop(
		{
			accept: ITEM_TYPES.FIELD,
			collect: (monitor) => ({
				isOver: !!monitor.isOver(),
			}),
			drop: (source) => {
				move(source.index, index);
			},
		},
		[move]
	);

	return (
		<div className="field-drop-zone" ref={drop}>
			{isOver && <div className="field-drop-zone-over" />}
		</div>
	);
}

function Field({field, index, label, onChange, onDelete, order}) {
	const _handleChangeValue = (value) => (event) => {
		onChange({[value]: event.target.value});
	};

	const [{isDragging}, drag] = useDrag({
		collect: (monitor) => ({
			isDragging: !!monitor.isDragging(),
		}),
		item: {index, type: ITEM_TYPES.FIELD},
	});

	return (
		<div
			ref={drag}
			style={{
				cursor: 'move',
				opacity: isDragging ? 0.5 : 1,
			}}
		>
			<ClayForm.Group className="field-item">
				<ClayInput.Group>
					<ClayInput.GroupItem shrink>
						<ClayButton
							borderless
							className="drag-handle"
							displayType="secondary"
							monospaced
							small
						>
							<ClayIcon symbol="drag" />
						</ClayButton>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem>
						<label htmlFor="indexedFieldName">
							{Liferay.Language.get('indexed-field-name')}
						</label>

						<ClayInput
							id="indexedFieldName"
							onChange={_handleChangeValue('field')}
							type="text"
							value={field}
						/>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem>
						<label htmlFor="displayLabel">
							{Liferay.Language.get('display-label')}
						</label>

						<ClayInput
							id="displayLabel"
							onChange={_handleChangeValue('label')}
							type="text"
							value={label}
						/>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem>
						<label htmlFor="order">
							{Liferay.Language.get('order')}
						</label>

						<ClaySelect
							aria-label={Liferay.Language.get('select-order')}
							id="order"
							onChange={_handleChangeValue('order')}
							value={order}
						>
							{Object.keys(ORDERS).map((key) => (
								<ClaySelect.Option
									key={ORDERS[key].value}
									label={ORDERS[key].label}
									value={ORDERS[key].value}
								/>
							))}
						</ClaySelect>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem shrink>
						<ClayButton
							borderless
							displayType="secondary"
							monospaced
							onClick={onDelete}
							small
						>
							<ClayIcon symbol="trash" />
						</ClayButton>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayForm.Group>
		</div>
	);
}

function FieldList({fields, onChangeFields}) {
	const idCounterRef = useRef(10000); // Starts at 10000 to avoid conflicts with existing fields.

	const _handleAddField = () => {
		onChangeFields([
			...fields,
			{
				field: '',
				id: idCounterRef.current++,
				label: '',
				order: ORDERS.ASC.value,
			},
		]);
	};

	const _handleChangeField = (index) => (value) => {
		onChangeFields(
			fields.map((item, i) => (i === index ? {...item, ...value} : item))
		);
	};

	const _handleDeleteField = (index) => {
		onChangeFields(fields.filter((_, i) => i !== index));
	};

	const _handleMoveField = (from, to) => {
		onChangeFields(move(fields, from, to));
	};

	return (
		<DndProvider backend={HTML5Backend}>
			{fields.map((item, index) => (
				<div key={item.id}>
					<DropZone index={index} move={_handleMoveField} />

					<Field
						field={item.field}
						index={index}
						label={item.label}
						onChange={_handleChangeField(index)}
						onDelete={() => _handleDeleteField(index)}
						order={item.order}
					/>
				</div>
			))}

			<DropZone index={fields.length} move={_handleMoveField} />

			<ClayButton displayType="secondary" onClick={_handleAddField}>
				<span className="inline-item inline-item-before">
					<ClayIcon symbol="plus" />
				</span>

				{Liferay.Language.get('add-option')}
			</ClayButton>
		</DndProvider>
	);
}

export default FieldList;
