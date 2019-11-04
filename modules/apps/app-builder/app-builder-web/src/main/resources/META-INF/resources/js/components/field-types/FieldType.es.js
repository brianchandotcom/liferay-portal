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

import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClaySticker from '@clayui/sticker';
import {ClayTooltipProvider} from '@clayui/tooltip';
import classnames from 'classnames';
import React, {useEffect, useState} from 'react';
import {useDrag} from 'react-dnd';
import {getEmptyImage} from 'react-dnd-html5-backend';

import Button from '../../components/button/Button.es';
import {DRAG_FIELD_TYPE} from '../../utils/dragTypes.es';
import FieldTypeDragPreview from './FieldTypeDragPreview.es';

const ICONS = {
	checkbox_multiple: 'select-from-list',
	document_library: 'upload',
	numeric: 'caret-double',
	radio: 'radio-button',
	select: 'list'
};

export default props => {
	const {
		active,
		className,
		deleteLabel = Liferay.Language.get('delete'),
		description,
		disabled,
		dragAlignment = 'left',
		dragType = DRAG_FIELD_TYPE,
		icon,
		label,
		name,
		onClick = () => {},
		onDelete,
		onDoubleClick = () => {}
	} = props;

	const [{dragging}, drag, preview] = useDrag({
		canDrag: _ => !disabled,
		collect: monitor => ({
			dragging: monitor.isDragging()
		}),
		item: {
			data: {...props},
			preview: () => <FieldTypeDragPreview {...props} />,
			type: dragType
		}
	});

	useEffect(() => {
		preview(getEmptyImage(), {captureDraggingState: true});
	}, [preview]);

	const handleOnClick = () => {
		if (disabled) {
			return;
		}

		onClick({...props});
	};

	const handleOnDoubleClick = () => {
		if (disabled) {
			return;
		}

		onDoubleClick({...props});
	};

	const [loading, setLoading] = useState(false);

	const fieldIcon = ICONS[icon] ? ICONS[icon] : icon;

	return (
		<div
			className={classnames(
				className,
				'autofit-row',
				'autofit-row-center',
				'field-type',
				{
					active,
					disabled,
					dragging,
					loading
				}
			)}
			data-field-type-name={name}
			onClick={() => handleOnClick()}
			onDoubleClick={() => handleOnDoubleClick()}
			ref={drag}
		>
			{dragAlignment === 'left' && (
				<div className="autofit-col pl-2 pr-2">
					<ClayIcon symbol="drag" />
				</div>
			)}

			<div
				className={classnames('autofit-col', 'pr-2', {
					'pl-2': dragAlignment === 'right'
				})}
			>
				<ClaySticker
					className="app-builder-field-sticker"
					displayType="light"
					size="md"
				>
					<ClayIcon symbol={fieldIcon} />
				</ClaySticker>
			</div>

			<div className="autofit-col autofit-col-expand pr-2">
				<h4 className="list-group-title text-truncate">
					<span>{label}</span>
				</h4>

				{description && (
					<p className="list-group-subtitle text-truncate">
						<small>{description}</small>
					</p>
				)}
			</div>

			{dragAlignment === 'right' && (
				<div className="autofit-col pr-2">
					<ClayIcon symbol="drag" />
				</div>
			)}

			{onDelete && (
				<div className="field-type-remove-icon">
					{loading ? (
						<ClayLoadingIndicator />
					) : (
						<ClayTooltipProvider>
							<Button
								borderless
								data-tooltip-align="right"
								data-tooltip-delay="200"
								displayType="secondary"
								onClick={event => {
									event.stopPropagation();

									setLoading(true);

									onDelete(name)
										.then(() => setLoading(false))
										.catch(error => {
											setLoading(false);

											throw error;
										});
								}}
								symbol="times-circle"
								title={deleteLabel}
							/>
						</ClayTooltipProvider>
					)}
				</div>
			)}
		</div>
	);
};
