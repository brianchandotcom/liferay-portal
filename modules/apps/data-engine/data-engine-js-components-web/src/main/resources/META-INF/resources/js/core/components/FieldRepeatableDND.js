/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {useEffect, useRef} from 'react';
import {useDrag} from 'react-dnd';
import {getEmptyImage} from 'react-dnd-html5-backend';

import FieldDragPreview from './FieldDragPreview.es';

const FieldRepeatableDND = ({children, field, index, nestedFieldIndex}) => {
	const dragPreviewContainerRef = useRef(null);

	const [{isDragging}, dragRef, preview] = useDrag({
		canDrag: () => {
			return !['INPUT', 'TEXTAREA'].includes(
				document.activeElement?.tagName
			);
		},
		canDrop() {
			return true;
		},
		item: {
			id: field.name,
			index,
			nestedFieldIndex,
			preview: () => (
				<FieldDragPreview
					className="lfr-forms__form-view-field-dragging"
					containerRef={dragPreviewContainerRef}
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
			className={
				(field.hidden ? 'hide ' : '') +
				classNames('lfr-forms__form-view-field-repeatable-dnd', {
					'lfr-forms__form-view-field-repeatable-dnd--dragging':
						isDragging,
				})
			}
			ref={dragPreviewContainerRef}
		>
			<div className="lfr-forms__form-view-field-topbar">
				<span className="inline-item inline-item-before" ref={dragRef}>
					<ClayIcon symbol="drag" />
				</span>

				{field.label}
			</div>

			{typeof children === 'function'
				? children({field, index})
				: children}
		</div>
	);
};

export default FieldRepeatableDND;
