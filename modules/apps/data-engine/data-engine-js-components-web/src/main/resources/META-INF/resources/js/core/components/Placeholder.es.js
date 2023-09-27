/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
