/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useStateSafe} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import React, {useCallback, useEffect, useRef} from 'react';

export const MAX_COLUMN_WIDTH = 672;
export const MIN_COLUMN_WIDTH = 286;
export const COLUMN_WIDTH_RESIZE_STEP = 20;

const MillerColumnsResizer = ({
	columnRef,
	columnWidth,
	index,
	setColumnWidth,
}) => {
	const separatorRef = useRef();

	const columnWidthRef = useRef(columnWidth);

	// eslint-disable-next-line react-compiler/react-compiler
	columnWidthRef.current = columnWidth;

	const getInitialColumnWidth = useCallback(
		(currentColumnWidth) =>
			!currentColumnWidth && columnRef.current
				? columnRef.current.offsetWidth
				: currentColumnWidth,
		[columnRef]
	);

	const handleSeparatorKeyDown = (event) => {
		const initialColumnWidth = getInitialColumnWidth(columnWidth);

		if (
			Liferay.Language.direction?.[themeDisplay?.getLanguageId()] ===
			'rtl'
		) {
			if (event.key === 'ArrowLeft') {
				setColumnWidth(
					Math.min(
						MAX_COLUMN_WIDTH,
						initialColumnWidth + COLUMN_WIDTH_RESIZE_STEP
					)
				);
			}
			else if (event.key === 'ArrowRight') {
				setColumnWidth(
					Math.max(
						MIN_COLUMN_WIDTH,
						initialColumnWidth - COLUMN_WIDTH_RESIZE_STEP
					)
				);
			}
			else if (event.key === 'Home') {
				setColumnWidth(MIN_COLUMN_WIDTH);
			}
			else if (event.key === 'End') {
				setColumnWidth(MAX_COLUMN_WIDTH);
			}
		}
		else {
			if (event.key === 'ArrowLeft') {
				setColumnWidth(
					Math.max(
						MIN_COLUMN_WIDTH,
						initialColumnWidth - COLUMN_WIDTH_RESIZE_STEP
					)
				);
			}
			else if (event.key === 'ArrowRight') {
				setColumnWidth(
					Math.min(
						MAX_COLUMN_WIDTH,
						initialColumnWidth + COLUMN_WIDTH_RESIZE_STEP
					)
				);
			}
			else if (event.key === 'Home') {
				setColumnWidth(MIN_COLUMN_WIDTH);
			}
			else if (event.key === 'End') {
				setColumnWidth(MAX_COLUMN_WIDTH);
			}
		}
	};

	const [resizing, setResizing] = useStateSafe(false);

	useEffect(() => {
		const columnElement = separatorRef.current;

		if (!columnElement) {
			return;
		}

		let initialColumnWidth;
		let initialCursorPosition;

		const handleMouseMove = (event) => {
			const cursorDelta = event.clientX - initialCursorPosition;

			if (
				Liferay.Language.direction?.[themeDisplay?.getLanguageId()] ===
				'rtl'
			) {
				setColumnWidth(
					Math.min(
						MAX_COLUMN_WIDTH,
						Math.max(
							MIN_COLUMN_WIDTH,
							initialColumnWidth - cursorDelta
						)
					)
				);
			}
			else {
				setColumnWidth(
					Math.min(
						MAX_COLUMN_WIDTH,
						Math.max(
							MIN_COLUMN_WIDTH,
							initialColumnWidth + cursorDelta
						)
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

		const handleMouseDown = (event) => {
			setResizing(true);

			event.preventDefault();

			initialColumnWidth = getInitialColumnWidth(columnWidthRef.current);
			initialCursorPosition = event.clientX;

			document.body.addEventListener('mousemove', handleMouseMove);
			document.body.addEventListener('mouseleave', stopResizing);
			document.body.addEventListener('mouseup', stopResizing);
		};

		columnElement.addEventListener('mousedown', handleMouseDown);

		return () => {
			stopResizing();
			columnElement.removeEventListener('mousedown', handleMouseDown);
		};
	}, [
		separatorRef,
		setResizing,
		setColumnWidth,
		columnWidthRef,
		getInitialColumnWidth,
	]);

	return (
		<div
			aria-label={Liferay.Language.get('resize-column')}
			aria-orientation="vertical"
			aria-valuemax={MAX_COLUMN_WIDTH}
			aria-valuemin={MIN_COLUMN_WIDTH}
			aria-valuenow={columnWidth}
			className={classNames('miller-columns-col__resizer', {
				'miller-columns-col__resizer--resizing': resizing,
			})}
			key={index}
			onKeyDown={handleSeparatorKeyDown}
			ref={separatorRef}
			role="separator"
			tabIndex={0}
		/>
	);
};

export default MillerColumnsResizer;
