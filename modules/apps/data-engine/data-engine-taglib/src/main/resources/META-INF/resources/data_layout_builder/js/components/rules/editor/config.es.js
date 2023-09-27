/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const OPERATOR_OPTIONS_TYPES = {
	boolean: 'boolean',
	double: 'number',
	integer: 'number',
	text: 'text',
	user: 'user',
};

export const RIGHT_TYPES = {
	checkbox: 'checkbox',
	checkbox_multiple: 'option',
	grid: 'json',
	radio: 'option',
	select: 'option',
};

export const RIGHT_OPERAND_TYPES = {
	checkbox: 'select',
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
	'calculate': {
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
