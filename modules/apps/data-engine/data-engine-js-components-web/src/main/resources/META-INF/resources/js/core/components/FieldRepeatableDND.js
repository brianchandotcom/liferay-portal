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

import classNames from 'classnames';
import React, {useEffect, useRef} from 'react';
import {useDrag} from 'react-dnd';
import {getEmptyImage} from 'react-dnd-html5-backend';

import FieldDragPreview from './FieldDragPreview.es';

const FieldRepeatableDND = ({children, field, index, nestedFieldIndex}) => {
	const ref = useRef(null);

	const [{isDragging}, dragRef, preview] = useDrag({
		canDrop() {
			return true;
		},
		item: {
			id: field.fieldName,
			index,
			nestedFieldIndex,
			preview: () => (
				<FieldDragPreview
					className="lfr-forms__form-view-field-dragging"
					containerRef={ref}
				/>
			),
			type: field.fieldName,
		},
	});

	useEffect(() => {
		preview(getEmptyImage(), {captureDraggingState: true});
	}, [preview]);

	return (
		<div
			className={classNames('lfr-forms__form-view-field-repeatable-dnd', {
				'lfr-forms__form-view-field-repeatable-dnd--dragging': isDragging,
			})}
			ref={(element) => {
				dragRef(element);
				ref.current = element;
			}}
		>
			<div className="lfr-forms__form-view-field-topbar">
				{field.label}
			</div>

			{typeof children === 'function'
				? children({field, index})
				: children}
		</div>
	);
};

export default FieldRepeatableDND;
