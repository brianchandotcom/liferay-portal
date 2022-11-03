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

import ClayLayout from '@clayui/layout';
import classnames from 'classnames';
import React, {useContext, useEffect, useRef} from 'react';

import {DND_ORIGIN_TYPE, useDrop} from '../hooks/useDrop.es';
import {ParentFieldContext} from './Field/ParentFieldContext.es';
import {useIsOverTarget as useIsOverKeyboardTarget} from './KeyboardDNDContext';

export function Placeholder({
	columnIndex,
	isRow,
	keyboardDNDPosition,
	pageIndex,
	rowIndex,
	size,
}) {
	const parentField = useContext(ParentFieldContext);
	const placeholderRef = useRef(null);

	const {canDrop, drop, overTarget} = useDrop({
		columnIndex: columnIndex ?? 0,
		origin: DND_ORIGIN_TYPE.EMPTY,
		pageIndex,
		parentField,
		rowIndex,
	});

	const overKeyboardTarget = useIsOverKeyboardTarget(
		keyboardDNDPosition.itemPath,
		keyboardDNDPosition.position
	);

	useEffect(() => {
		if (overKeyboardTarget && placeholderRef.current) {
			placeholderRef.current.scrollIntoView({
				behavior: 'auto',
				block: 'center',
				inline: 'center',
			});
		}
	}, [overKeyboardTarget]);

	const Content = (
		<ClayLayout.Col
			className="col col-ddm col-empty"
			data-ddm-field-column={columnIndex}
			data-ddm-field-page={pageIndex}
			data-ddm-field-row={rowIndex}
			md={size}
		>
			<div
				className={classnames('ddm-target', {
					'target-over targetOver':
						(overTarget &&
							canDrop &&
							!parentField.root?.ddmStructureId) ||
						overKeyboardTarget,
				})}
				ref={(element) => {
					if (!parentField.root?.ddmStructureId && drop) {
						drop(element);
					}

					placeholderRef.current = element;
				}}
			/>
		</ClayLayout.Col>
	);

	if (isRow) {
		return <div className="placeholder row">{Content}</div>;
	}

	return Content;
}
