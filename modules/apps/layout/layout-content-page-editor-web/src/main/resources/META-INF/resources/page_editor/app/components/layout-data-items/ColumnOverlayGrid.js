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
import React from 'react';
import {createPortal} from 'react-dom';

import {FragmentsEditorShim} from './FragmentsEditorShim';

export default function ColumnOverlayGrid({
	columnSpacing,
	highlightedColumn,
	rowRect
}) {
	return createPortal(
		<div
			className="column-overlay-grid position-fixed"
			style={{
				height: `${rowRect.height}px`,
				left: `${rowRect.left}px`,
				top: `${rowRect.top}px`,
				width: `${rowRect.width}px`
			}}
		>
			<FragmentsEditorShim />

			<div
				className={classNames('container-fluid h-100 py-0', {
					'px-0': !columnSpacing
				})}
			>
				<div
					className={classNames('h-100 row', {
						'no-gutters': !columnSpacing
					})}
				>
					{[...Array(12).keys()].map(column => (
						<div
							className={classNames(
								'col column-overlay-grid__column',
								{
									'column-overlay-grid__column--highlighted':
										column === highlightedColumn
								}
							)}
							key={`col-overlay-grid-column-${column}`}
						>
							<div className="column-overlay-grid__column-content h-100" />
						</div>
					))}
				</div>
			</div>
		</div>,
		document.body
	);
}
