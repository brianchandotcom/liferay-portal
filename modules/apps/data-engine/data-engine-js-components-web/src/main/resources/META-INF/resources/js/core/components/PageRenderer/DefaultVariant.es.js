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

import {useFormState} from '../../hooks/useForm.es';

const DDM_FORM_ADMIN_PORTLET_NAMESPACE =
	'com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet';

const JOURNAL_WEB_PORTLET_NAMESPACE =
	'com_liferay_journal_web_portlet_JournalPortlet';

export function Container({activePage, children, isBuilder = true, pageIndex}) {
	return (
		<div
			className={classnames('fade tab-pane', {
				'active show': activePage === pageIndex,
				'hide': activePage !== pageIndex,
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
}

Container.displayName = 'DefaultVariant.Container';

export const Column = forwardRef(
	(
		{
			children,
			className,
			column,
			columnClassName,
			index,
			onClick,
			onMouseLeave,
			onMouseOver,
			pageIndex,
			rowIndex,
			viewMode,
		},
		ref
	) => {
		const {portletId} = useFormState();

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
				className={classnames('col-ddm', columnClassName)}
				key={index}
				md={column.size}
				onClick={onClick}
				onMouseLeave={onMouseLeave}
				onMouseOver={onMouseOver}
				ref={ref}
			>
				{!!column.fields.length && (
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
						{column.fields.map((field, index) => {
							if (
								viewMode &&
								portletId.includes(
									DDM_FORM_ADMIN_PORTLET_NAMESPACE
								)
							) {
								field.predefinedValue = '';
							}

							return typeof children === 'function'
								? children({field, index})
								: children;
						})}
					</div>
				)}
			</ClayLayout.Col>
		);
	}
);

Column.displayName = 'DefaultVariant.Column';

export function Page({
	children,
	forceAriaUpdate,
	header: Header,
	invalidFormMessage,
	pageIndex,
}) {
	return (
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
}

Page.displayName = 'DefaultVariant.Page';

export function PageHeader({description, title}) {
	const {portletId} = useFormState();
	const isWebContentPortlet = portletId.includes(
		JOURNAL_WEB_PORTLET_NAMESPACE
	);

	return (
		<>
			{title && <div className="lfr-ddm-form-page-title">{title}</div>}
			{!isWebContentPortlet && description && (
				<div className="lfr-ddm-form-page-description">
					{description}
				</div>
			)}
		</>
	);
}

PageHeader.displayName = 'DefaultVariant.PageHeader';

export function Row({children, index, row}) {
	return (
		<div className="position-relative row" key={index}>
			{row.columns.map((column, index) => children({column, index}))}
		</div>
	);
}

Row.displayName = 'DefaultVariant.Row';

export function Rows({children, rows}) {
	if (!rows) {
		return null;
	}

	return rows.map((row, index) => (
		<div className="ddm-row" key={index}>
			{children({index, row})}
		</div>
	));
}

Rows.displayName = 'DefaultVariant.Rows';
