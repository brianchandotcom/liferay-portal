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

import {RendererFields} from '../components/Form/Renderer';

type Filters = {
	[key: string]: RendererFields[];
};

type Filter = {
	[key: string]: RendererFields;
};

const baseFilters: Filter = {
	priority: {
		label: 'Priority',
		name: 'priority',
		options: ['1', '2', '3', '4', '5'],
		type: 'checkbox',
	},
};

const filters: Filters = {
	case: [
		baseFilters.priority,
		{
			label: 'Case Type',
			name: 'caseType',
			options: [{label: 'Staging', value: 'staging'}],
			type: 'select',
		},
		{
			label: 'Case Name',
			name: 'caseName',
			type: 'text',
		},
		{
			label: 'Team',
			name: 'team',
			options: [{label: 'Solutions', value: 'solutions'}],
			type: 'select',
		},
		{
			label: 'Component',
			name: 'component',
			type: 'text',
		},
	],
};

export {filters};
