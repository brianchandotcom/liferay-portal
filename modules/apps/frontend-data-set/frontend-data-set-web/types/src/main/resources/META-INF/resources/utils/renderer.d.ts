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

import {FDSCellRenderer} from '@liferay/js-api/data-set';
import {ComponentType} from 'react';
import {InternalCellRenderer} from '../cell_renderers/InternalCellRenderer';
export declare function getInputRendererById(id: string): ComponentType;
export interface Renderer {
	type: 'clientExtension' | 'internal';
}
export interface ClientExtensionCellRenderer extends Renderer {
	renderer: FDSCellRenderer;
	type: 'clientExtension';
}
export declare type CellRenderer =
	| ClientExtensionCellRenderer
	| InternalCellRenderer;
export declare function getCellRendererByURL(
	url: string,
	type: 'clientExtension' | 'internal'
): Promise<CellRenderer>;
