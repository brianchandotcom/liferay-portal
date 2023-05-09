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

import {Renderer} from '../utils/renderer';

// @ts-ignore

import ActionsLinkRenderer from './ActionLinkRenderer';

// @ts-ignore

import BooleanRenderer from './BooleanRenderer';

// @ts-ignore

import DateRenderer from './DateRenderer';

// @ts-ignore

import DateTimeRenderer from './DateTimeRenderer';

// @ts-ignore

import DefaultRenderer from './DefaultRenderer';

// @ts-ignore

import ImageRenderer from './ImageRenderer';

// @ts-ignore

import LabelRenderer from './LabelRenderer';

// @ts-ignore

import LinkRenderer from './LinkRenderer';

// @ts-ignore

import QuantitySelectorRenderer from './QuantitySelectorRenderer';

// @ts-ignore

import StatusRenderer from './StatusRenderer';

export interface InternalCellRenderer extends Renderer {
	component: React.ComponentType<any>;
	label?: string;
	name?: string;
	type: 'internal';
}

export const INTERNAL_CELL_RENDERERS: Array<InternalCellRenderer> = [
	{
		component: ActionsLinkRenderer,
		label: Liferay.Language.get('action-link'),
		name: 'actionLink',
		type: 'internal',
	},
	{
		component: BooleanRenderer,
		label: Liferay.Language.get('boolean'),
		name: 'boolean',
		type: 'internal',
	},
	{
		component: DateRenderer,
		label: Liferay.Language.get('date'),
		name: 'date',
		type: 'internal',
	},
	{
		component: DateTimeRenderer,
		label: Liferay.Language.get('date-and-time'),
		name: 'dateTime',
		type: 'internal',
	},
	{
		component: DefaultRenderer,
		label: Liferay.Language.get('default'),
		name: 'default',
		type: 'internal',
	},
	{
		component: ImageRenderer,
		label: Liferay.Language.get('image'),
		name: 'image',
		type: 'internal',
	},
	{
		component: LabelRenderer,
		label: Liferay.Language.get('label'),
		name: 'label',
		type: 'internal',
	},
	{
		component: LinkRenderer,
		label: Liferay.Language.get('link'),
		name: 'link',
		type: 'internal',
	},
	{
		component: QuantitySelectorRenderer,
		label: Liferay.Language.get('quantity-selector'),
		name: 'quantitySelector',
		type: 'internal',
	},
	{
		component: StatusRenderer,
		label: Liferay.Language.get('status'),
		name: 'status',
		type: 'internal',
	},
];
