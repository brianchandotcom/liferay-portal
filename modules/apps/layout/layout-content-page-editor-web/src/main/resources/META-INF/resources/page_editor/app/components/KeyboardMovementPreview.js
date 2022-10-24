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
import {useEventListener} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import {debounce} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';

import {
	useMovementSource,
	useMovementTarget,
} from '../contexts/KeyboardMovementContext';
import {TARGET_POSITIONS} from '../utils/drag-and-drop/constants/targetPositions';
import getLayoutDataItemTopperUniqueClassName from '../utils/getLayoutDataItemTopperUniqueClassName';

const INITIAL_STYLE = {opacity: 0};
const DRAG_FEEDBACK_HEIGHT = 6;

const getItemStyle = (keyboardTargetId, keyboardPosition, previewRef) => {
	if (!previewRef.current) {
		return {};
	}

	const {x, y} = getKeyboardMovementPosition(
		keyboardTargetId,
		keyboardPosition,
		previewRef
	);

	const transform = `translate(${x}px, ${y}px)`;

	return {
		opacity: 1,
		transform,
	};
};

const getKeyboardMovementPosition = (targetId, targetPosition, previewRef) => {
	const topperCSSClass = getLayoutDataItemTopperUniqueClassName(targetId);
	const topperElement = document.querySelector(`.${topperCSSClass}`);
	const topperRect = topperElement.getBoundingClientRect();

	const previewRect = previewRef.current.getBoundingClientRect();

	const x =
		topperRect.left + topperRect.width * 0.5 - previewRect.width * 0.5;
	let y;

	if (targetPosition === TARGET_POSITIONS.MIDDLE) {
		y =
			topperRect.bottom -
			topperRect.height * 0.5 -
			previewRect.height * 0.5;
	}
	else if (targetPosition === TARGET_POSITIONS.BOTTOM) {
		y =
			topperRect.bottom -
			previewRect.height * 0.5 -
			DRAG_FEEDBACK_HEIGHT * 0.5;
	}
	else if (targetPosition === TARGET_POSITIONS.TOP) {
		y =
			topperRect.top -
			previewRect.height * 0.5 +
			DRAG_FEEDBACK_HEIGHT * 0.5;
	}

	return {x, y};
};

export default function KeyboardMovementPreview() {
	const {itemId, position} = useMovementTarget();
	const source = useMovementSource();

	const [style, setStyle] = useState(INITIAL_STYLE);

	const previewRef = useRef();

	useEffect(() => {
		if (itemId) {
			setStyle(getItemStyle(itemId, position, previewRef));
		}
	}, [itemId, position]);

	useEventListener(
		'scroll',
		debounce(() => {
			if (itemId) {
				setStyle(getItemStyle(itemId, position, previewRef));
			}
		}, 100),
		true,
		document
	);

	if (!itemId) {
		return null;
	}

	return (
		<div className="cadmin">
			<div className="page-editor__drag-preview">
				<div
					className="page-editor__drag-preview__content"
					ref={previewRef}
					style={style}
				>
					<div className="align-items-center d-flex h-100">
						<ClayIcon className="mt-0" symbol={source?.icon} />
					</div>

					<span
						className={classNames('text-truncate', {
							'ml-3': source?.icon,
						})}
					>
						{source?.name}
					</span>
				</div>
			</div>
		</div>
	);
}
