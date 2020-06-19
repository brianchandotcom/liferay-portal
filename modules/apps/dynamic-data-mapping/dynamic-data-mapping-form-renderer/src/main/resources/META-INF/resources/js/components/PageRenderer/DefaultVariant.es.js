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

import classnames from 'classnames';
import React, {useContext} from 'react';

import {DND_ORIGIN_TYPE, useDrop} from '../../hooks/useDrop.es';
import {ParentFieldContext} from '../Field/ParentFieldContext.es';

export const Container = ({
	activePage,
	children,
	isBuilder = true,
	pageIndex,
}) => (
	<div
		className={classnames('fade tab-pane', {
			'active show': activePage === pageIndex,
			hide: activePage !== pageIndex,
		})}
		role="tabpanel"
	>
		{isBuilder ? (
			<div className="form-builder-layout">{children}</div>
		) : (
			children
		)}
	</div>
);

export const Column = ({
	activePage,
	allowNestedFields,
	children,
	column,
	editable,
	index,
	pageIndex,
	rowIndex,
}) => {
	const parentField = useContext(ParentFieldContext);
	const {drop, overTarget} = useDrop({
		columnIndex: index,
		fieldName: column.fields[0]?.fieldName,
		origin: DND_ORIGIN_TYPE.FIELD,
		pageIndex,
		parentField,
		rowIndex,
	});

	if (column.fields.length === 0 && editable && activePage === pageIndex) {
		return (
			<Placeholder
				columnIndex={index}
				pageIndex={pageIndex}
				rowIndex={rowIndex}
				size={column.size}
			/>
		);
	}

	const fields = column.fields.map((field, index) =>
		children({field, index})
	);

	const addr = {
		'data-ddm-field-column': index,
		'data-ddm-field-page': pageIndex,
		'data-ddm-field-row': rowIndex,
	};

	const firstField = column.fields[0];

	return (
		<div {...addr} className={`col-md-${column.size} col-ddm`} key={index}>
			{editable && column.fields.length > 0 ? (
				<div
					className={classnames(
						'ddm-field-container ddm-target h-100',
						{
							'active-drop-child':
								firstField.type === 'fieldset' && overTarget,
							'ddm-fieldset':
								firstField.type === 'fieldset' &&
								firstField.ddmStructureId,
							selected: firstField.selected,
							'target-over targetOver': overTarget,
						}
					)}
					data-field-name={firstField.fieldName}
				>
					<div
						className={classnames(
							'ddm-resize-handle ddm-resize-handle-left',
							{
								hide: !firstField.selected,
							}
						)}
						{...addr}
					/>

					<div
						className="ddm-drag"
						ref={allowNestedFields ? drop : undefined}
					>
						{fields}
					</div>

					<div
						className={classnames(
							'ddm-resize-handle ddm-resize-handle-right',
							{
								hide: !firstField.selected,
							}
						)}
						{...addr}
					/>
				</div>
			) : (
				fields
			)}
		</div>
	);
};

export const Page = ({
	activePage,
	children,
	editable,
	empty,
	header: Header,
	pageIndex,
}) => {
	const {canDrop, drop, overTarget} = useDrop({
		columnIndex: 0,
		origin: DND_ORIGIN_TYPE.EMPTY,
		pageIndex,
		rowIndex: 0,
	});

	return (
		<div
			className="active ddm-form-page lfr-ddm-form-page"
			data-ddm-page={pageIndex}
		>
			{activePage === pageIndex && Header}

			{empty && editable && activePage === pageIndex ? (
				<div className="row">
					<div
						className="col col-ddm col-empty col-md-12 last-col lfr-initial-col mb-4 mt-5"
						data-ddm-field-column="0"
						data-ddm-field-page={pageIndex}
						data-ddm-field-row="0"
					>
						<div
							className={classnames('ddm-empty-page ddm-target', {
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
					</div>
				</div>
			) : (
				children
			)}
		</div>
	);
};

export const PageHeader = ({description, title}) => (
	<>
		{title && <h2 className="lfr-ddm-form-page-title">{title}</h2>}
		{description && (
			<h3 className="lfr-ddm-form-page-description">{description}</h3>
		)}
	</>
);

export const Placeholder = ({
	columnIndex,
	isRow,
	pageIndex,
	rowIndex,
	size,
}) => {
	const parentField = useContext(ParentFieldContext);
	const {drop, overTarget} = useDrop({
		columnIndex: columnIndex ?? 0,
		origin: DND_ORIGIN_TYPE.EMPTY,
		pageIndex,
		parentField,
		rowIndex,
	});

	const Content = (
		<div
			className={`col col-ddm col-empty col-md-${size}`}
			data-ddm-field-column={columnIndex}
			data-ddm-field-page={pageIndex}
			data-ddm-field-row={rowIndex}
		>
			<div
				className={classnames('ddm-target', {
					'target-over targetOver': overTarget,
				})}
				ref={drop}
			/>
		</div>
	);

	if (isRow) {
		return <div className="placeholder row">{Content}</div>;
	}

	return Content;
};

export const Row = ({children, index, row}) => (
	<div className="position-relative row" key={index}>
		{row.columns.map((column, index) => children({column, index}))}
	</div>
);

export const Rows = ({activePage, children, editable, pageIndex, rows}) => {
	if (!rows) {
		return null;
	}

	return rows.map((row, index) => (
		<div key={index}>
			{index === 0 && editable && activePage === pageIndex && (
				<Placeholder
					isRow
					pageIndex={pageIndex}
					rowIndex={0}
					size={12}
				/>
			)}

			{children({index, row})}

			{editable && activePage === pageIndex && (
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
