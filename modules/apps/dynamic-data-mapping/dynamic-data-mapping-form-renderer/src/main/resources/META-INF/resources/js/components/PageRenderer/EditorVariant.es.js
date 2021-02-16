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

import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import {DragTypes} from 'data-engine-taglib';
import {useEventListener} from 'frontend-js-react-web';
import React, {
	forwardRef,
	useContext,
	useEffect,
	useRef,
	useState,
} from 'react';
import {useDrag} from 'react-dnd';
import {getEmptyImage} from 'react-dnd-html5-backend';

import {EVENT_TYPES} from '../../actions/eventTypes.es';
import {DND_ORIGIN_TYPE, useDrop} from '../../hooks/useDrop.es';
import {useForm} from '../../hooks/useForm.es';
import {hasFieldSet} from '../../util/fields.es';
import {Actions, ActionsControls, useActions} from '../Actions.es';
import {ParentFieldContext} from '../Field/ParentFieldContext.es';
import {Placeholder} from '../Placeholder.es';
import * as DefaultVariant from './DefaultVariant.es';

const DIRECTIONS = {
	LEFT: 'left',
	RIGHT: 'right',
};

const MAX_COLUMNS = 12;

const FieldDragPreview = ({containerRef}) => {
	const ref = useRef(null);

	/**
	 * This hack was needed to capture the field snapshot.
	 * Currently the Field is loaded lazily and the preview
	 * will look like a loading state field.
	 */
	useEffect(() => {
		/**
		 * It copies the width of the field and clone the DOM element
		 * to replace the ref inner FieldDragPreview
		 */
		const {width} = getComputedStyle(containerRef.current);
		const element = containerRef.current.cloneNode(true);

		ref.current.parentElement.style.width = width;

		ref.current.appendChild(element);

		return () => {
			ref.current.remove();
		};
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [containerRef.current]);

	return (
		<DraggableField className="dragging">
			<div ref={ref} />
		</DraggableField>
	);
};

const DraggableField = forwardRef(({children, className}, ref) => (
	<div className={classNames('ddm-drag', className)} ref={ref}>
		{children}
	</div>
));

export const Column = ({
	activePage,
	allowNestedFields,
	children,
	column,
	editable,
	index: columnIndex,
	pageIndex,
	resizeInfoRef,
	rowIndex,
	rowRef,
}) => {
	const parentField = useContext(ParentFieldContext);

	const actionsRef = useRef(null);
	const columnRef = useRef(null);
	const resizeRef = useRef(null);

	const [resizing, setResizing] = useState(false);

	const firstField = column.fields[0];
	const isFieldSet = hasFieldSet(firstField);

	const [{activeId, hoveredId}] = useActions();

	const {canDrop, drop, overTarget} = useDrop({
		columnIndex,
		fieldName: column.fields[0]?.fieldName,
		origin: DND_ORIGIN_TYPE.FIELD,
		pageIndex,
		parentField,
		rowIndex,
	});

	const dragType = isFieldSet
		? DragTypes.DRAG_FIELDSET_MOVE
		: DragTypes.DRAG_FIELD_TYPE_MOVE;

	const [{isDragging}, drag, preview] = useDrag({
		item: {
			data: firstField ?? undefined,
			pageIndex,
			preview: () => <FieldDragPreview containerRef={resizeRef} />,
			type: dragType,
		},
	});

	useEffect(() => {
		preview(getEmptyImage(), {captureDraggingState: true});
	}, [preview]);

	if (editable && column.fields.length === 0 && activePage === pageIndex) {
		return (
			<Placeholder
				columnIndex={columnIndex}
				pageIndex={pageIndex}
				rowIndex={rowIndex}
				size={column.size}
			/>
		);
	}

	const rootParentField = parentField.root ?? firstField;
	const isFieldSetOrGroup = firstField.type === 'fieldset';

	const isFieldSelected =
		firstField.fieldName === activeId || firstField.fieldName === hoveredId;

	const fieldId =
		!editable && hasFieldSet(parentField.root)
			? parentField.root.fieldName
			: firstField.fieldName;

	return (
		<ActionsControls
			actionsRef={actionsRef}
			activePage={pageIndex}
			columnRef={columnRef}
			fieldId={fieldId}
		>
			<DefaultVariant.Column
				className={classNames({
					'active-drop-child':
						isFieldSetOrGroup &&
						overTarget &&
						!rootParentField.ddmStructureId,
					dragging: resizing || isDragging,
					hovered: editable && firstField.fieldName === hoveredId,
					selected: editable && firstField.fieldName === activeId,
					'target-droppable': canDrop,
					'target-over targetOver':
						(!rootParentField.ddmStructureId && overTarget) ||
						resizing,
				})}
				column={column}
				index={columnIndex}
				pageIndex={pageIndex}
				ref={columnRef}
				rowIndex={rowIndex}
			>
				{editable && isFieldSelected && (
					<Actions
						activePage={pageIndex}
						fieldId={firstField.fieldName}
						fieldType={firstField.type}
						isFieldSet={isFieldSet}
						ref={actionsRef}
					/>
				)}

				<ResizableColumn
					allowNestedFields={allowNestedFields}
					columnIndex={columnIndex}
					drag={drag}
					drop={drop}
					editable={editable}
					instanceId={firstField.instanceId}
					isFieldSelected={isFieldSelected}
					isFieldSetOrGroup={isFieldSetOrGroup}
					onResizing={(resizing) => setResizing(resizing)}
					pageIndex={pageIndex}
					ref={resizeRef}
					resizeInfoRef={resizeInfoRef}
					rootParentField={rootParentField}
					rowIndex={rowIndex}
					rowRef={rowRef}
				>
					{column.fields.map((field, index) =>
						children({
							field,
							index,
							loc: {
								columnIndex,
								pageIndex,
								rowIndex,
							},
						})
					)}
				</ResizableColumn>
			</DefaultVariant.Column>
		</ActionsControls>
	);
};

Column.displayName = 'EditorVariant.Column';

const ResizableColumn = forwardRef(
	(
		{
			allowNestedFields,
			children,
			columnIndex,
			drag,
			drop,
			editable,
			instanceId,
			isFieldSelected,
			isFieldSetOrGroup,
			onResizing,
			pageIndex,
			resizeInfoRef,
			rootParentField,
			rowIndex,
			rowRef,
		},
		ref
	) => {
		const {loc = []} = useContext(ParentFieldContext);

		const dispatch = useForm();

		const handleMouseDown = (event, direction) => {
			event.preventDefault();
			event.stopPropagation();

			resizeInfoRef.current = {
				...resizeInfoRef.current,
				direction,
				instanceId,
			};

			onResizing(true);
		};

		const handleMouseMove = (event) => {
			if (
				resizeInfoRef.current &&
				resizeInfoRef.current.instanceId === instanceId
			) {
				let column = Math.floor(
					((event.clientX -
						rowRef.current?.getBoundingClientRect().left) *
						(MAX_COLUMNS * 10)) /
						rowRef.current?.clientWidth /
						10
				);

				if (column > MAX_COLUMNS - 1) {
					column = MAX_COLUMNS - 1;
				}

				if (
					column >= 0 &&
					column !== resizeInfoRef.current.lastColumnValue
				) {
					resizeInfoRef.current = {
						...resizeInfoRef.current,
						lastColumnValue: column,
					};

					dispatch({
						payload: {
							column,
							direction: resizeInfoRef.current.direction,
							loc: [...loc, {columnIndex, pageIndex, rowIndex}],
						},
						type: EVENT_TYPES.COLUMN_RESIZED,
					});
				}
			}
		};

		useEventListener('mousemove', handleMouseMove, false, document.body);

		useEventListener(
			'mouseup',
			() => {
				onResizing(false);

				resizeInfoRef.current = null;
			},
			false,
			document.body
		);

		return (
			<>
				<div
					className={classNames(
						'ddm-resize-handle ddm-resize-handle-left',
						{
							hide: !isFieldSelected || !editable,
						}
					)}
					onMouseDown={(event) =>
						handleMouseDown(event, DIRECTIONS.LEFT)
					}
				/>

				<DraggableField
					className={classNames({
						'py-0': isFieldSetOrGroup,
					})}
					ref={
						allowNestedFields && !rootParentField.ddmStructureId
							? drag(drop(ref))
							: drag(ref)
					}
				>
					{children}
				</DraggableField>

				<div
					className={classNames(
						'ddm-resize-handle ddm-resize-handle-right',
						{
							hide: !isFieldSelected || !editable,
						}
					)}
					onMouseDown={(event) =>
						handleMouseDown(event, DIRECTIONS.RIGHT)
					}
				/>
			</>
		);
	}
);

export const Page = ({
	activePage,
	children,
	editable,
	empty,
	forceAriaUpdate,
	header,
	invalidFormMessage,
	pageIndex,
}) => {
	const {canDrop, drop, overTarget} = useDrop({
		columnIndex: 0,
		origin: DND_ORIGIN_TYPE.EMPTY,
		pageIndex,
		parentField: {},
		rowIndex: 0,
	});

	return (
		<DefaultVariant.Page
			activePage={activePage}
			forceAriaUpdate={forceAriaUpdate}
			header={header}
			invalidFormMessage={invalidFormMessage}
			pageIndex={pageIndex}
		>
			{editable && empty ? (
				<ClayLayout.Row>
					<ClayLayout.Col
						className="col-ddm col-empty last-col lfr-initial-col mb-4 mt-5"
						data-ddm-field-column="0"
						data-ddm-field-page={pageIndex}
						data-ddm-field-row="0"
					>
						<div
							className={classNames('ddm-empty-page ddm-target', {
								'target-droppable': canDrop,
								'target-over targetOver': overTarget,
							})}
							ref={drop}
						>
							<p className="ddm-empty-page-message">
								{Liferay.Language.get(
									'drag-fields-from-the-sidebar-to-compose-your-form'
								)}
							</p>
						</div>
					</ClayLayout.Col>
				</ClayLayout.Row>
			) : (
				children
			)}
		</DefaultVariant.Page>
	);
};

Page.displayName = 'EditorVariant.Page';

export const Rows = ({children, editable, pageIndex, rows}) => {
	if (!rows) {
		return null;
	}

	return rows.map((row, index) => (
		<div key={index}>
			{editable && index === 0 && (
				<Placeholder
					isRow
					pageIndex={pageIndex}
					rowIndex={0}
					size={12}
				/>
			)}

			{children({index, row})}

			{editable && (
				<Placeholder
					isRow
					pageIndex={pageIndex}
					rowIndex={index + 1}
					size={12}
				/>
			)}
		</div>
	));
};

Rows.displayName = 'EditorVariant.Rows';

export const Row = ({children, index, row}) => {
	const rowRef = useRef(null);
	const resizeInfoRef = useRef(null);

	return (
		<div className="position-relative row" key={index} ref={rowRef}>
			{row.columns.map((column, index) =>
				children({column, index, resizeInfoRef, rowRef})
			)}
		</div>
	);
};

Row.displayName = 'EditorVariant.Row';
