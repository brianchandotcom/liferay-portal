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

// @ts-ignore

import DefaultRenderer from './DefaultRenderer';
import {
	INTERNAL_CELL_RENDERERS,
	InternalCellRenderer,
} from './InternalCellRenderer';

export function getInternalCellRenderer(name: string): InternalCellRenderer {
	const renderer = INTERNAL_CELL_RENDERERS.find(
		(renderer) => renderer.name === name
	);

	if (!renderer) {
		return {
			component: DefaultRenderer,
			label: Liferay.Language.get('default'),
			name: 'default',
			type: 'internal',
		};
	}

	return renderer;
}
