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
import React, {useContext, useRef} from 'react';

import {DND_ORIGIN_TYPE, useDrop} from '../../hooks/useDrop.es';
import {ParentFieldContext} from '../Field/ParentFieldContext.es';
import {Actions, ActionsControls, useActions} from './Actions.es';
import * as DefaultVariant from './DefaultVariant.es';
import {Placeholder} from './Placeholder.es';

export const Column = ({
	activePage,
	allowNestedFields,
	children,
	column,
	index,
	pageIndex,
	rowIndex,
}) => {
	const parentField = useContext(ParentFieldContext);

	const actionsRef = useRef(null);
	const columnRef = useRef(null);

	const {activeField, hoveredField} = useActions();

	const {drop, overTarget} = useDrop({
		columnIndex: index,
		fieldName: column.fields[0]?.fieldName,
		origin: DND_ORIGIN_TYPE.FIELD,
		pageIndex,
		parentField,
		rowIndex,
	});

	if (column.fields.length === 0 && activePage === pageIndex) {
		return (
			<Placeholder
				columnIndex={index}
				pageIndex={pageIndex}
				rowIndex={rowIndex}
				size={column.size}
			/>
		);
	}

	const firstField = column.fields[0];
	const rootParentField = parentField.root ?? firstField;
	const isFieldSetOrGroup = firstField.type === 'fieldset';
	const isFieldSet = isFieldSetOrGroup && firstField.ddmStructureId;
	const isFieldSelected =
		firstField.fieldName === activeField ||
		firstField.fieldName === hoveredField;

	const addr = {
		'data-ddm-field-column': index,
		'data-ddm-field-page': pageIndex,
		'data-ddm-field-row': rowIndex,
	};

	return (
		<ActionsControls
			activePage={activePage}
			columnRef={columnRef}
			expanded={isFieldSelected}
			field={firstField}
			ref={actionsRef}
		>
			<DefaultVariant.Column
				className={classNames({
					'active-drop-child':
						isFieldSetOrGroup &&
						overTarget &&
						!rootParentField.ddmStructureId,
					hovered: firstField.fieldName === hoveredField,
					selected: firstField.fieldName === activeField,
					'target-over targetOver':
						!rootParentField.ddmStructureId && overTarget,
				})}
				column={column}
				index={index}
				pageIndex={pageIndex}
				ref={columnRef}
				rowIndex={rowIndex}
			>
				<Actions
					activePage={activePage}
					expanded={isFieldSelected}
					field={firstField}
					isFieldSet={isFieldSet}
				/>

				<div
					className={classNames(
						'ddm-resize-handle ddm-resize-handle-left',
						{
							hide: !isFieldSelected,
						}
					)}
					{...addr}
				/>

				<div
					className={classNames('ddm-drag', {
						'py-0': isFieldSetOrGroup,
					})}
					ref={
						allowNestedFields && !rootParentField.ddmStructureId
							? drop
							: undefined
					}
				>
					{column.fields.map((field, index) =>
						children({field, index})
					)}
				</div>

				<div
					className={classNames(
						'ddm-resize-handle ddm-resize-handle-right',
						{
							hide: !isFieldSelected,
						}
					)}
					{...addr}
				/>
			</DefaultVariant.Column>
		</ActionsControls>
	);
};

export const Page = ({
	activePage,
	children,
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
			{empty && activePage === pageIndex ? (
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

export const Rows = ({activePage, children, pageIndex, rows}) => {
	if (!rows) {
		return null;
	}

	return rows.map((row, index) => (
		<div key={index}>
			{index === 0 && activePage === pageIndex && (
				<Placeholder
					isRow
					pageIndex={pageIndex}
					rowIndex={0}
					size={12}
				/>
			)}

			{children({index, row})}

			{activePage === pageIndex && (
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
