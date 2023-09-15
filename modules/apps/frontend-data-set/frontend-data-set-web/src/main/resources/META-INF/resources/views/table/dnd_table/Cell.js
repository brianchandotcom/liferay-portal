/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTable from '@clayui/table';
import classNames from 'classnames';
import {throttle} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useContext, useLayoutEffect, useMemo, useRef} from 'react';

import ViewsContext from '../../ViewsContext';
import {VIEWS_ACTION_TYPES} from '../../viewsReducer';
import Context from './TableContext';

function Cell({
	children,
	className,
	columnName,
	defaultWidth = 'auto',
	heading,
	resizable,
}) {
	const {
		draggingAllowed,
		draggingColumnName,
		isFixed,
		resizeColumn,
		updateDraggingAllowed,
		updateDraggingColumnName,
	} = useContext(Context);
	const [{modifiedFields}, viewsDispatch] = useContext(ViewsContext);

	const cellRef = useRef();
	const clientXRef = useRef({current: null});

	useLayoutEffect(() => {
		if (columnName && heading && !isFixed) {
			const boundingClientRect = cellRef.current.getBoundingClientRect();

			viewsDispatch({
				type: VIEWS_ACTION_TYPES.UPDATE_FIELD,
				value: {
					name: columnName,
					resizable,
					width: boundingClientRect.width,
				},
			});
		}
	}, [columnName, isFixed, heading, resizable, viewsDispatch]);

	const handleDrag = useMemo(() => {
		return throttle((event) => {
			if (event.clientX === clientXRef.current || !cellRef.current) {
				return;
			}

			updateDraggingColumnName(columnName);

			clientXRef.current = event.clientX;

			const {x: headerCellX} = cellRef.current.getClientRects()[0];
			const newWidth = event.clientX - headerCellX;

			resizeColumn(columnName, newWidth);
		}, 20);
	}, [columnName, resizeColumn, updateDraggingColumnName]);

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
		const columnDetails = modifiedFields[columnName];

		return columnDetails && isFixed && columnDetails.width;
	}, [isFixed, modifiedFields, columnName]);

	const content = (
		<>
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
		</>
	);

	if (Liferay.FeatureFlags['LPS-193005']) {
		return (
			<ClayTable.Cell
				className={className}
				headingCell={heading}
				ref={cellRef}
				style={{
					width: width ?? defaultWidth,
				}}
			>
				{content}
			</ClayTable.Cell>
		);
	}

	return (
		<div
			className={classNames(heading ? 'dnd-th' : 'dnd-td', className)}
			ref={cellRef}
			style={{
				width,
			}}
		>
			{content}
		</div>
	);
}

Cell.defaultProps = {
	heading: false,
	resizable: false,
};

Cell.propTypes = {
	className: PropTypes.string,
	columnName: PropTypes.string,
	heading: PropTypes.bool,
	resizable: PropTypes.bool,
};

export default Cell;
