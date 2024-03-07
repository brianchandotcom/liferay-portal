/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClaySticker from '@clayui/sticker';
import classnames from 'classnames';
import {
	DRAG_TYPES,
	useSetKeyboardDNDSourceItem,
} from 'data-engine-js-components-web';
import {sub} from 'frontend-js-web';
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

	const setKeyboardDNDSourceItem = useSetKeyboardDNDSourceItem();

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

	const handleOnKeyDown = (event) => {
		if (event.key === ' ' || event.key === 'Enter') {
			event.preventDefault();

			setKeyboardDNDSourceItem({
				dragType: 'add',
				fieldType: props,
			});
		}
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
			onClick={!disabled && onClick ? handleOnClick : null}
			onDoubleClick={disabled ? null : handleOnDoubleClick}
			ref={disabled ? null : drag}
			role="button"
			title={label}
			verticalAlign="center"
		>
			{draggable && dragAlignment === 'left' && (
				<ClayLayout.ContentCol className="pl-2 pr-2">
					<ClayButtonWithIcon
						aria-label={sub(
							Liferay.Language.get('press-enter-to-add-x-field'),
							[label]
						)}
						disabled={disabled}
						displayType="unstyled"
						onKeyDown={disabled ? null : handleOnKeyDown}
						role="application"
						size="xs"
						symbol="drag"
					/>
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
