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
	Source,
	Target,
	useMovementSource,
	useMovementTarget,
} from '../../contexts/KeyboardMovementContext';
import getDropZoneElementClassname from '../../utils/getDropZoneElementClassName';

const INITIAL_STYLE = {opacity: 0};

type Style = {
	opacity?: number;
	transform?: string;
};

const getKeyboardMovementPosition = (
	source: Source,
	target: Target,
	preview: HTMLDivElement
) => {
	const {groupId, index, position} = target;
	const {propertyKey} = source;

	const className = getDropZoneElementClassname(
		propertyKey,
		groupId,
		index,
		position
	);

	const targetElement = document.querySelector(`.${className}`);

	if (!targetElement || !preview) {
		return null;
	}

	const targetRect = targetElement.getBoundingClientRect();

	const previewRect = preview.getBoundingClientRect();

	const x =
		targetRect.left + targetRect.width * 0.5 - previewRect.width * 0.5;

	const y =
		targetRect.bottom - targetRect.height * 0.5 - previewRect.height * 0.5;

	return {x, y};
};

const getItemStyle = (
	source: Source | null,
	target: Target | null,
	preview: HTMLDivElement | null
) => {
	if (!source || !target || !preview) {
		return {};
	}

	const movementPosition = getKeyboardMovementPosition(
		source,
		target,
		preview
	);

	if (!movementPosition) {
		return {};
	}

	const {x, y} = movementPosition;

	return {
		opacity: 1,
		transform: `translate(${x}px, ${y}px)`,
	};
};

export default function KeyboardMovementPreview() {
	const [style, setStyle] = useState<Style>(INITIAL_STYLE);

	const source = useMovementSource();
	const target = useMovementTarget();

	const previewRef = useRef<HTMLDivElement>(null);

	useEffect(() => {
		const newStyle = getItemStyle(source, target, previewRef.current);

		setStyle(newStyle);
	}, [source, target]);

	useEventListener(
		'scroll',
		debounce(() => {
			const newStyle = getItemStyle(source, target, previewRef.current);

			setStyle(newStyle);
		}, 100),
		true,
		document
	);

	if (!source) {
		return null;
	}

	return (
		<div
			className="segments__keyboard-movement-preview"
			ref={previewRef}
			style={style}
		>
			<div className="segments__keyboard-movement-preview__content">
				<div className="align-items-center d-flex h-100">
					<ClayIcon className="mt-0" symbol={source.icon} />
				</div>

				<span
					className={classNames('text-truncate', {
						'ml-3': source.icon,
					})}
				>
					{source.label}
				</span>
			</div>
		</div>
	);
}
