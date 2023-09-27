/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import React, {
	useCallback,
	useContext,
	useEffect,
	useMemo,
	useRef,
	useState,
} from 'react';
import {useDrag} from 'react-dnd';
import {getEmptyImage} from 'react-dnd-html5-backend';

import {DRAG_FIELD_TYPE_MOVE} from '../../../utils/dragTypes';
import {hasFieldSet} from '../../../utils/fields.es';
import {DND_ORIGIN_TYPE, useDrop} from '../../hooks/useDrop.es';
import {useFormState} from '../../hooks/useForm.es';
import {Actions, ActionsControls, useActions} from '../Actions.es';
import {ParentFieldContext} from '../Field/ParentFieldContext.es';
import FieldDragPreview from '../FieldDragPreview.es';
import {useIsOverTarget as useIsOverKeyboardTarget} from '../KeyboardDNDContext';
import {Placeholder} from '../Placeholder.es';
import ResizableColumn from '../ResizableColumn.es';
import * as DefaultVariant from './DefaultVariant.es';

export function Column({
	allowNestedFields,
	children,
	column,
	editable,
	index: columnIndex,
	itemPath,
	pageIndex,
	resizeInfoRef,
	rowIndex,
	rowRef,
}) {
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

	const overFieldKeyboardTarget = useIsOverKeyboardTarget(
		column.fields.length ? [...itemPath, 0] : itemPath,
		'middle'
	);

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
			type: DRAG_FIELD_TYPE_MOVE,
		},
	});

	useEffect(() => {
		preview(getEmptyImage(), {captureDraggingState: true});
	}, [preview]);

	const handleResize = useCallback((resizing) => setResizing(resizing), []);

	if (!column.fields.length) {
		return (
			<Placeholder
				columnIndex={columnIndex}
				keyboardDNDPosition={{itemPath, position: 'middle'}}
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
					'dragging': resizing || isDragging,
					'hovered': editable && firstField.fieldName === hoveredId,
					'selected': editable && firstField.fieldName === activeId,
					'target-droppable': canDrop || overFieldKeyboardTarget,
					'target-over targetOver':
						(!rootParentField.ddmStructureId &&
							overTarget &&
							canDrop) ||
						resizing ||
						overFieldKeyboardTarget,
				})}
				column={column}
				index={columnIndex}
				pageIndex={pageIndex}
				ref={columnRef}
				rowIndex={rowIndex}
			>
				{editable && (
					<Actions
						activePage={pageIndex}
						field={fieldRootOrCurrent}
						fieldId={firstField.fieldName}
						fieldType={firstField.type}
						isFieldSelected={isFieldSelected}
						isFieldSet={isFieldSet}
						itemPath={itemPath}
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
}

Column.displayName = 'EditorVariant.Column';

export function Page({
	activePage,
	children,
	editable,
	empty,
	forceAriaUpdate,
	header,
	invalidFormMessage,
	pageIndex,
}) {
	const dropTargetRef = useRef();

	const {canDrop, drop, overTarget} = useDrop({
		columnIndex: 0,
		origin: DND_ORIGIN_TYPE.EMPTY,
		pageIndex,
		rowIndex: 0,
	});

	const overKeyboardPageTarget = useIsOverKeyboardTarget(
		[pageIndex],
		'middle'
	);

	const formState = useFormState();

	const hasSingleEmptyColumn = useMemo(() => {
		const {rows} = formState.pages[pageIndex];

		if (rows?.length === 1) {
			const [firstRow] = rows;

			if (firstRow.columns?.length === 1) {
				const [firstColumn] = firstRow.columns;

				if (firstColumn.fields?.length === 0) {
					return true;
				}
			}
		}

		return false;
	}, [formState, pageIndex]);

	const overKeyboardColumnTarget = useIsOverKeyboardTarget(
		hasSingleEmptyColumn ? [pageIndex, 0, 0] : [pageIndex],
		'middle'
	);

	const overKeyboardTarget =
		overKeyboardPageTarget || overKeyboardColumnTarget;

	useEffect(() => {
		if (overKeyboardTarget && dropTargetRef.current) {
			dropTargetRef.current.scrollIntoView({
				behavior: 'auto',
				block: 'center',
				inline: 'center',
			});
		}
	}, [overKeyboardTarget]);

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
								'target-droppable':
									canDrop || overKeyboardTarget,
								'target-over targetOver':
									overTarget || overKeyboardTarget,
							})}
							ref={(element) => {
								if (drop) {
									drop(element);
								}

								dropTargetRef.current = element;
							}}
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
}

Page.displayName = 'EditorVariant.Page';

export function Rows({children, editable, itemPath, pageIndex, rows}) {
	if (!rows) {
		return null;
	}

	return rows.map((row, index) => (
		<div key={index}>
			{editable && index === 0 && (
				<Placeholder
					isRow
					keyboardDNDPosition={{
						itemPath: [...itemPath, 0],
						position: 'top',
					}}
					pageIndex={pageIndex}
					rowIndex={0}
					size={12}
				/>
			)}

			{children({index, row})}

			{editable && (
				<Placeholder
					isRow
					keyboardDNDPosition={{
						itemPath: [...itemPath, index],
						position: 'bottom',
					}}
					pageIndex={pageIndex}
					rowIndex={index + 1}
					size={12}
				/>
			)}
		</div>
	));
}

Rows.displayName = 'EditorVariant.Rows';

export function Row({children, row}) {
	const rowRef = useRef(null);
	const resizeInfoRef = useRef(null);

	return (
		<div className="position-relative row" ref={rowRef}>
			{row.columns.map((column, index) =>
				children({column, index, resizeInfoRef, rowRef})
			)}
		</div>
	);
}

Row.displayName = 'EditorVariant.Row';
