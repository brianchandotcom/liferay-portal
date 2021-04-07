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
import React, {
	useCallback,
	useContext,
	useEffect,
	useRef,
	useState,
} from 'react';
import {useDrag} from 'react-dnd';
import {getEmptyImage} from 'react-dnd-html5-backend';

import {hasFieldSet} from '../../../utils/fields.es';
import {DND_ORIGIN_TYPE, useDrop} from '../../hooks/useDrop.es';
import {Actions, ActionsControls, useActions} from '../Actions.es';
import {ParentFieldContext} from '../Field/ParentFieldContext.es';
import FieldDragPreview from '../FieldDragPreview.es';
import {Placeholder} from '../Placeholder.es';
import ResizableColumn from '../ResizableColumn.es';
import * as DefaultVariant from './DefaultVariant.es';

export const Column = ({
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

	const {
		state: {activeId, hoveredId},
	} = useActions();

	const {canDrop, drop, overTarget} = useDrop({
		columnIndex,
		field: firstField,
		fieldName: firstField?.fieldName,
		origin: DND_ORIGIN_TYPE.FIELD,
		pageIndex,
		parentField,
		rowIndex,
	});

	const [{isDragging}, drag, preview] = useDrag({
		item: {
			data: firstField ?? undefined,
			preview: () => <FieldDragPreview containerRef={resizeRef} />,
			sourceIndexes: {
				columnIndex,
				pageIndex,
				rowIndex,
			},
			sourceParentField: parentField,
			type: DragTypes.DRAG_FIELD_TYPE_MOVE,
		},
	});

	useEffect(() => {
		preview(getEmptyImage(), {captureDraggingState: true});
	}, [preview]);

	const handleResize = useCallback((resizing) => setResizing(resizing), []);

	if (column.fields.length === 0) {
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
	const isFieldSet = hasFieldSet(firstField);

	const isFieldSelected =
		firstField.fieldName === activeId || firstField.fieldName === hoveredId;

	const fieldRootOrCurrent =
		!editable && hasFieldSet(parentField.root)
			? parentField.root
			: firstField;

	return (
		<ActionsControls
			actionsRef={actionsRef}
			activePage={pageIndex}
			columnRef={columnRef}
			field={fieldRootOrCurrent}
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
						(!rootParentField.ddmStructureId &&
							overTarget &&
							canDrop) ||
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
						parentFieldName={parentField?.fieldName}
						ref={actionsRef}
					/>
				)}

				<ResizableColumn
					currentLoc={{columnIndex, pageIndex, rowIndex}}
					disabled={!isFieldSelected || !editable}
					instanceId={firstField.instanceId}
					onResizing={handleResize}
					resizeInfoRef={resizeInfoRef}
					rowRef={rowRef}
				>
					<div
						className={classNames('ddm-drag', {
							'py-0': isFieldSetOrGroup,
						})}
						ref={(node) => {
							if (
								allowNestedFields &&
								!rootParentField.ddmStructureId
							) {
								drag(drop(node));
							}
							else if (!hasFieldSet(parentField)) {
								drag(node);
							}
							resizeRef.current = node;
						}}
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
					</div>
				</ResizableColumn>
			</DefaultVariant.Column>
		</ActionsControls>
	);
};

Column.displayName = 'EditorVariant.Column';

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
