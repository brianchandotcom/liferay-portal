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
import {throttle} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useContext, useLayoutEffect, useMemo, useRef} from 'react';

import Context from './TableContext';

function Cell({children, className, columnName, expand, heading, resizable}) {
	const cellRef = useRef();
	const clientX = useRef({current: null});
	const {
		columnDefinitions,
		draggingAllowed,
		draggingColumnName,
		isFixed,
		registerColumn,
		resizeColumn,
		updateDraggingAllowed,
		updateDraggingColumnName,
	} = useContext(Context);

	const intersectionObserver = useRef(
		new IntersectionObserver(
			(entries) => {
				const cellWidth = entries[0].boundingClientRect.width;

				if (cellWidth) {
					registerColumn(columnName, cellWidth, resizable);
					intersectionObserver.current.disconnect();
				}
			},
			{
				root: null,
				threshold: 1,
			}
		)
	);

	useLayoutEffect(() => {
		if (columnName && heading && !isFixed) {
			intersectionObserver.current.observe(cellRef.current);
		}
	}, [columnName, isFixed, heading]);

	const handleDrag = useMemo(() => {
		return throttle((event) => {
			if (event.clientX === clientX.current || !cellRef.current) {
				return;
			}

			updateDraggingColumnName(columnName);

			clientX.current = event.clientX;

			const {x: headerCellX} = cellRef.current.getClientRects()[0];
			const newWidth = event.clientX - headerCellX;

			resizeColumn(columnName, newWidth, resizable);
		}, 20);
	}, [columnName, resizable, resizeColumn, updateDraggingColumnName]);

	function initializeDrag() {
		window.addEventListener('mousemove', handleDrag);
		window.addEventListener(
			'mouseup',
			() => {
				updateDraggingAllowed(true);
				updateDraggingColumnName(null);
				window.removeEventListener('mousemove', handleDrag);
			},
			{once: true}
		);
	}

	const width = useMemo(() => {
		const columnDetails = columnDefinitions.get(columnName);

		return columnDetails && isFixed && columnDetails.width;
	}, [isFixed, columnDefinitions, columnName]);

	return (
		<div
			className={classNames(
				heading ? 'dnd-th' : 'dnd-td',
				expand && 'expand',
				className
			)}
			ref={cellRef}
			style={{
				width,
			}}
		>
			{children}

			{resizable && (
				<span
					className={classNames('dnd-th-resizer', {
						'is-active': columnName === draggingColumnName,
						'is-allowed': draggingAllowed,
					})}
					onMouseDown={initializeDrag}
				/>
			)}
		</div>
	);
}

Cell.defaultProps = {
	expand: false,
	heading: false,
	resizable: false,
};

Cell.propTypes = {
	className: PropTypes.string,
	columnName: PropTypes.string,
	expand: PropTypes.bool,
	heading: PropTypes.bool,
	resizable: PropTypes.bool,
};

export default Cell;
