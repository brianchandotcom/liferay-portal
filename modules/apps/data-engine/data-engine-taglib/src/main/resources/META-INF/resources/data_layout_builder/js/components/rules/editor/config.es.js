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

export const OPERATOR_OPTIONS_TYPES = {
	double: 'number',
	integer: 'number',
	text: 'text',
	user: 'user',
};

export const RIGHT_TYPES = {
	checkbox_multiple: 'option',
	grid: 'json',
	radio: 'option',
	select: 'option',
};

export const RIGHT_OPERAND_TYPES = {
	checkbox_multiple: 'select',
	field: 'select',
	grid: 'grid',
	radio: 'select',
	select: 'select',
};

export const DEFAULT_RULE = {
	actions: [
		{
			target: '',
			type: '',
		},
	],
	conditions: [
		{
			operands: [
				{
					type: '',
					value: '',
				},
			],
			operator: '',
		},
	],
	logicalOperator: 'OR',
};

export const ACTION_TARGET_SHAPE = {
	'auto-fill': {
		ddmDataProviderInstanceUUID: null,
		inputs: {},
		outputs: {},
	},
	calculate: {
		expression: '',
	},
};

export const ACTIONS_OPTIONS = [
	{
		label: Liferay.Language.get('show'),
		value: 'show',
	},
	{
		label: Liferay.Language.get('enable'),
		value: 'enable',
	},
	{
		label: Liferay.Language.get('require'),
		value: 'require',
	},
	{
		label: Liferay.Language.get('autofill'),
		value: 'auto-fill',
	},
	{
		label: Liferay.Language.get('calculate'),
		value: 'calculate',
	},
	{
		label: Liferay.Language.get('jump-to-page'),
		value: 'jump-to-page',
	},
];
