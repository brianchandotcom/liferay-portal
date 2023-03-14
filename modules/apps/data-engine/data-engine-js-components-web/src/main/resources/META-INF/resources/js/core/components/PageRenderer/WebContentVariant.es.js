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
import React, {forwardRef, useRef} from 'react';
import {useDrop} from 'react-dnd';

import {EVENT_TYPES} from '../../../custom/form/eventTypes';
import {useForm, useFormState} from '../../hooks/useForm.es';
import FieldRepeatableDND from '../FieldRepeatableDND';

import './WebContentVariant.scss';

const DDM_FORM_ADMIN_PORTLET_NAMESPACE =
	'com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormAdminPortlet';

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

							if (field.repeatable) {
								return (
									<React.Fragment key={index}>
										{index === 0 && (
											<Placeholder
												field={field}
												index={index}
												nestedFieldIndex={
													field.nestedFieldIndex
												}
											/>
										)}

										<FieldRepeatableDND
											field={field}
											index={index}
											nestedFieldIndex={
												field.nestedFieldIndex
											}
										>
											{children}
										</FieldRepeatableDND>

										<Placeholder
											field={field}
											index={index + 1}
											nestedFieldIndex={
												field.nestedFieldIndex
													? field.nestedFieldIndex + 1
													: field.nestedFieldIndex
											}
										/>
									</React.Fragment>
								);
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

Column.displayName = 'WebContentVariant.Column';

function Placeholder({field, index, nestedFieldIndex}) {
	const ref = useRef(null);
	const dispatch = useForm();

	const [{canDrop, overTarget}, dropRef] = useDrop({
		accept: field.fieldName,
		canDrop: () => {
			return true;
		},
		collect: (monitor) => {
			return {
				canDrop: monitor.canDrop(),
				overTarget: monitor.isOver({shallow: true}),
			};
		},
		drop: (item) => {
			if (!ref.current) {
				return;
			}

			const draggedIndex = item.index;
			const targetIndex = index;
			const sourceFieldName = item.id;
			const sourceNestedFieldIndex = item.nestedFieldIndex;
			const targetNestedFieldIndex = nestedFieldIndex;

			dispatch({
				payload: {
					draggedIndex,
					sourceFieldName,
					sourceNestedFieldIndex,
					targetIndex,
					targetNestedFieldIndex,
				},
				type: EVENT_TYPES.FORM_VIEW.REPEATABLE_FIELD.CHANGE_ORDER,
			});

			item.index = targetIndex;
		},
	});

	return (
		<div
			className={classnames('lfr-forms__form-view-ddm-target', {
				'lfr-forms__form-view-ddm-target-over': overTarget && canDrop,
			})}
			droppable="true"
			ref={(element) => {
				dropRef(element);
				ref.current = element;
			}}
		>
			<span droppable="true" />
		</div>
	);
}
