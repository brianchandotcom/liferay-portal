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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClaySticker from '@clayui/sticker';
import classnames from 'classnames';
import {DRAG_TYPES} from 'data-engine-js-components-web';
import React, {useEffect, useState} from 'react';
import {useDrag} from 'react-dnd';
import {getEmptyImage} from 'react-dnd-html5-backend';

import DropDown from '../drop-down/DropDown.es';

import './FieldType.scss';
import FieldTypeDragPreview from './FieldTypeDragPreview.es';

const ICONS = {
	checkbox_multiple: 'select-from-list',
	document_library: 'upload',
	numeric: 'caret-double',
	radio: 'radio-button',
	select: 'list',
};

const FieldType = (props) => {
	const {
		actions,
		active,
		className,
		deleteLabel = Liferay.Language.get('delete'),
		description,
		disabled,
		dragAlignment = 'left',
		draggable = true,
		dragType = DRAG_TYPES.DRAG_FIELD_TYPE_ADD,
		icon,
		label,
		name,
		required,
		onClick,
		onDelete,
		onDoubleClick,
	} = props;

	const [{dragging}, drag, preview] = useDrag({
		canDrag: (_) => !disabled && draggable,
		collect: (monitor) => ({
			dragging: monitor.isDragging(),
		}),
		item: {
			data: {...props},
			preview: () => <FieldTypeDragPreview {...props} />,
			type: dragType,
		},
	});

	useEffect(() => {
		preview(getEmptyImage(), {captureDraggingState: true});
	}, [preview]);

	const handleOnClick = () => {
		onClick({...props});
	};

	const handleOnDoubleClick = () => {
		if (disabled) {
			return;
		}

		onDoubleClick?.({...props});
	};

	const [loading, setLoading] = useState(false);

	const fieldIcon = ICONS[icon] ? ICONS[icon] : icon;

	return (
		<ClayLayout.ContentRow
			className={classnames(className, 'field-type', {
				active,
				disabled,
				dragging,
				loading,
			})}
			data-field-type-name={name}
			onClick={onClick && handleOnClick}
			onDoubleClick={handleOnDoubleClick}
			ref={drag}
			title={label}
			verticalAlign="center"
		>
			{draggable && dragAlignment === 'left' && (
				<ClayLayout.ContentCol className="pl-2 pr-2">
					<ClayIcon symbol="drag" />
				</ClayLayout.ContentCol>
			)}

			<ClayLayout.ContentCol
				className={classnames('pr-2', {
					'pl-2': dragAlignment === 'right',
				})}
			>
				<ClaySticker
					className="data-layout-builder-field-sticker"
					displayType="dark"
					size="md"
				>
					<ClayIcon symbol={fieldIcon} />
				</ClaySticker>
			</ClayLayout.ContentCol>
			<ClayLayout.ContentCol className="pr-2" expand>
				<div className="d-flex list-group-title">
					<span className="text-truncate">{label}</span>
					{required && (
						<span className="reference-mark">
							<ClayIcon symbol="asterisk" />
						</span>
					)}
				</div>
				{description && (
					<p className="list-group-subtitle text-truncate">
						<small>{description}</small>
					</p>
				)}
			</ClayLayout.ContentCol>

			<div className="autofit-col pr-2">
				{actions && <DropDown actions={actions} />}
			</div>

			{draggable && dragAlignment === 'right' && (
				<ClayLayout.ContentCol className="pr-2">
					<ClayIcon symbol="drag" />
				</ClayLayout.ContentCol>
			)}

			{onDelete && (
				<div className="field-type-remove-icon">
					{loading ? (
						<ClayLoadingIndicator />
					) : (
						<ClayButtonWithIcon
							borderless
							data-tooltip-align="right"
							data-tooltip-delay="200"
							displayType="secondary"
							onClick={(event) => {
								event.stopPropagation();

								setLoading(true);

								onDelete(name)
									.then(() => setLoading(false))
									.catch((error) => {
										setLoading(false);

										throw error;
									});
							}}
							symbol="times-circle"
							title={deleteLabel}
						/>
					)}
				</div>
			)}
		</ClayLayout.ContentRow>
	);
};

FieldType.displayName = 'FieldType';
export default FieldType;
