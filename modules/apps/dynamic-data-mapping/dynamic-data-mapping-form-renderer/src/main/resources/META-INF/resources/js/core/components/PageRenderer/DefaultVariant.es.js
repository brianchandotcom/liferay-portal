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
import classnames from 'classnames';
import React, {forwardRef} from 'react';

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

Container.displayName = 'DefaultVariant.Container';

export const Column = forwardRef(
	(
		{
			children,
			className,
			column,
			index,
			onClick,
			onMouseLeave,
			onMouseOver,
			pageIndex,
			rowIndex,
		},
		ref
	) => {
		const addr = {
			'data-ddm-field-column': index,
			'data-ddm-field-page': pageIndex,
			'data-ddm-field-row': rowIndex,
		};

		const firstField = column.fields[0];
		const isFieldSetOrGroup = firstField?.type === 'fieldset';
		const isFieldSet = firstField?.ddmStructureId && isFieldSetOrGroup;

		return (
			<ClayLayout.Col
				{...addr}
				className="col-ddm"
				key={index}
				md={column.size}
				onClick={onClick}
				onMouseLeave={onMouseLeave}
				onMouseOver={onMouseOver}
				ref={ref}
			>
				{column.fields.length > 0 && (
					<div
						className={classnames(
							'ddm-field-container ddm-target h-100',
							{
								'ddm-fieldset': !!isFieldSet,
								'fields-group': !!isFieldSetOrGroup,
							},
							className
						)}
						data-field-name={firstField.fieldName}
					>
						{column.fields.map((field, index) =>
							typeof children === 'function'
								? children({field, index})
								: children
						)}
					</div>
				)}
			</ClayLayout.Col>
		);
	}
);

Column.displayName = 'DefaultVariant.Column';

export const Page = ({
	children,
	forceAriaUpdate,
	header: Header,
	invalidFormMessage,
	pageIndex,
}) => (
	<div
		className="active ddm-form-page lfr-ddm-form-page"
		data-ddm-page={pageIndex}
	>
		{invalidFormMessage && (
			<span aria-atomic="true" aria-live="polite" className="sr-only">
				{invalidFormMessage}
				<span aria-hidden="true">{forceAriaUpdate}</span>
			</span>
		)}

		{Header}

		{children}
	</div>
);

Page.displayName = 'DefaultVariant.Page';

export const PageHeader = ({description, title}) => (
	<>
		{title && <h2 className="lfr-ddm-form-page-title">{title}</h2>}
		{description && (
			<h3 className="lfr-ddm-form-page-description">{description}</h3>
		)}
	</>
);

PageHeader.displayName = 'DefaultVariant.PageHeader';

export const Row = ({children, index, row}) => (
	<div className="position-relative row" key={index}>
		{row.columns.map((column, index) => children({column, index}))}
	</div>
);

Row.displayName = 'DefaultVariant.Row';

export const Rows = ({children, rows}) => {
	if (!rows) {
		return null;
	}

	return rows.map((row, index) => (
		<div key={index}>{children({index, row})}</div>
	));
};

Rows.displayName = 'DefaultVariant.Rows';
