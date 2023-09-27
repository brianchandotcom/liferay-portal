/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {useEventListener} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import {debounce} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';

import {
	useMovementSource,
	useMovementTarget,
	useMovementTargetPosition,
} from '../contexts/KeyboardMovementContext';
import {TARGET_POSITIONS} from '../utils/drag_and_drop/constants/targetPositions';
import getLayoutDataItemTopperUniqueClassName from '../utils/getLayoutDataItemTopperUniqueClassName';

const INITIAL_STYLE = {opacity: 0};
const DRAG_FEEDBACK_HEIGHT = 6;

const getItemStyle = (keyboardTargetId, keyboardPosition, previewRef) => {
	if (!previewRef.current) {
		return {};
	}

	const movementPosition = getKeyboardMovementPosition(
		keyboardTargetId,
		keyboardPosition,
		previewRef
	);

	if (!movementPosition) {
		return null;
	}

	const {x, y} = movementPosition;

	const transform = `translate(${x}px, ${y}px)`;

	return {
		opacity: 1,
		transform,
	};
};

const getKeyboardMovementPosition = (targetId, targetPosition, previewRef) => {
	const topperCSSClass = getLayoutDataItemTopperUniqueClassName(targetId);
	const topperElement = document.querySelector(`.${topperCSSClass}`);

	if (!topperElement.offsetParent) {
		return null;
	}

	const topperRect = topperElement.getBoundingClientRect();

	const previewRect = previewRef.current.getBoundingClientRect();

	let x;

	if (targetPosition === TARGET_POSITIONS.LEFT) {
		x = topperRect.left - previewRect.width * 0.5;
	}
	else if (targetPosition === TARGET_POSITIONS.RIGHT) {
		x = topperRect.right - previewRect.width * 0.5;
	}
	else {
		x = topperRect.left + topperRect.width * 0.5 - previewRect.width * 0.5;
	}

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
	else {
		y =
			topperRect.top +
			topperRect.height * 0.5 -
			DRAG_FEEDBACK_HEIGHT * 0.5;
	}

	return {x, y};
};

export default function KeyboardMovementPreview() {
	const {itemId} = useMovementTarget();
	const position = useMovementTargetPosition();

	const source = useMovementSource();

	const [style, setStyle] = useState(INITIAL_STYLE);

	const previewRef = useRef();

	useEffect(() => {
		if (itemId) {
			const newStyle = getItemStyle(itemId, position, previewRef);

			if (newStyle) {
				setStyle(getItemStyle(itemId, position, previewRef));
			}
		}
	}, [itemId, position]);

	useEventListener(
		'scroll',
		debounce(() => {
			if (itemId) {
				const newStyle = getItemStyle(itemId, position, previewRef);

				if (newStyle) {
					setStyle(getItemStyle(itemId, position, previewRef));
				}
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
