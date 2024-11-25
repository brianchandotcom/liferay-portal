/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useStateSafe} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import React, {KeyboardEvent, useCallback, useEffect, useRef} from 'react';

import './Resizer.scss';

const ALLOWED_KEYS = ['ArrowLeft', 'ArrowRight', 'Home', 'End'] as const;

type AllowedKey = (typeof ALLOWED_KEYS)[number];

function isAllowedKey(key: string): key is AllowedKey {
	return ALLOWED_KEYS.includes(key as AllowedKey);
}

interface ResizerProps {
	ariaControls: string;
	ariaLabel: string;
	className: string;
	maxWidth: number;
	minWidth: number;
	resizeStep: number;
	setWidth: Function;
	style: React.CSSProperties;
	tabIndex: number;
	targetRef: React.RefObject<HTMLDivElement>;
	width: number;
}

export default function Resizer({
	ariaControls,
	ariaLabel,
	className,
	maxWidth,
	minWidth,
	resizeStep,
	setWidth,
	tabIndex = 0,
	targetRef,
	width,
	...props
}: ResizerProps) {
	const [resizing, setResizing] = useStateSafe(false);

	const resizerRef = useRef<HTMLDivElement>(null);

	const widthRef = useRef(width);

	// False positive - react-compiler/react-compiler
	// eslint-disable-next-line react-compiler/react-compiler
	widthRef.current = width;

	const getInitialWidth = useCallback(
		(currentWidth) =>
			!currentWidth && targetRef.current
				? targetRef.current.offsetWidth
				: currentWidth,
		[targetRef]
	);

	const handleKeyDown = (event: KeyboardEvent<HTMLInputElement>) => {
		const {key} = event;

		if (!isAllowedKey(key)) {
			return;
		}

		event.preventDefault();
		event.stopPropagation();

		const initialWidth = getInitialWidth(width);

		const rtl =
			Liferay.Language.direction[Liferay.ThemeDisplay.getLanguageId()] ===
			'rtl';

		const increaseWidth = () =>
			Math.min(maxWidth, initialWidth + resizeStep);

		const decreaseWidth = () =>
			Math.max(minWidth, initialWidth - resizeStep);

		if (event.key === 'ArrowLeft') {
			setWidth(rtl ? increaseWidth() : decreaseWidth());
		}
		else if (event.key === 'ArrowRight') {
			setWidth(rtl ? decreaseWidth() : increaseWidth());
		}
		else if (event.key === 'Home') {
			setWidth(minWidth);
		}
		else if (event.key === 'End') {
			setWidth(maxWidth);
		}
	};

	useEffect(() => {
		const resizeElement = resizerRef.current;

		if (!resizeElement) {
			return;
		}

		let initialWidth: number;
		let initialCursorPosition: number;

		const handleMouseMove = (event: MouseEvent) => {
			const cursorDelta = event.clientX - initialCursorPosition;
			const dir =
				Liferay.Language.direction[
					Liferay.ThemeDisplay.getLanguageId()
				];
			if (dir === 'rtl') {
				setWidth(
					Math.min(
						maxWidth,
						Math.max(minWidth, initialWidth - cursorDelta)
					)
				);
			}
			else {
				setWidth(
					Math.min(
						maxWidth,
						Math.max(minWidth, initialWidth + cursorDelta)
					)
				);
			}
		};

		const stopResizing = () => {
			setResizing(false);

			document.body.removeEventListener('mousemove', handleMouseMove);
			document.body.removeEventListener('mouseleave', stopResizing);
			document.body.removeEventListener('mouseup', stopResizing);
		};

		const handleMouseDown = (event: MouseEvent) => {
			setResizing(true);

			event.preventDefault();

			initialWidth = getInitialWidth(widthRef.current);
			initialCursorPosition = event.clientX;

			document.body.addEventListener('mousemove', handleMouseMove);
			document.body.addEventListener('mouseleave', stopResizing);
			document.body.addEventListener('mouseup', stopResizing);
		};

		resizeElement.addEventListener('mousedown', handleMouseDown);

		return () => {
			stopResizing();
			resizeElement.removeEventListener('mousedown', handleMouseDown);
		};
	}, [
		getInitialWidth,
		maxWidth,
		minWidth,
		resizerRef,
		setResizing,
		setWidth,
		widthRef,
	]);

	return (
		<div
			aria-controls={ariaControls}
			aria-label={ariaLabel}
			aria-orientation="vertical"
			aria-valuemax={maxWidth}
			aria-valuemin={minWidth}
			aria-valuenow={width}
			className={classNames('layout__resizer', className, {
				['layout__resizer--resizing']: resizing,
			})}
			onKeyDown={handleKeyDown}
			ref={resizerRef}
			role="separator"
			tabIndex={tabIndex}
			{...props}
		/>
	);
}
