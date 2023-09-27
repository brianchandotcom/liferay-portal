/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export type FormulaOutput = {
	description: string;
	label: string;
	value: string;
};

export const FORMULA_OUTPUT_OPTIONS: FormulaOutput[] = [
	{
		description: Liferay.Language.get('select-between-two-possible-values'),
		label: Liferay.Language.get('boolean'),
		value: 'Boolean',
	},
	{
		description: Liferay.Language.get('calculate-date-values'),
		label: Liferay.Language.get('date'),
		value: 'Date',
	},
	{
		description: Liferay.Language.get('calculate-decimal-numeric-values'),
		label: Liferay.Language.get('decimal'),
		value: 'Decimal',
	},
	{
		description: Liferay.Language.get(
			'calculate-integer-numeric-values-up-to-nine-digits'
		),
		label: Liferay.Language.get('integer'),
		value: 'Integer',
	},
	{
		description: Liferay.Language.get('add-text-fields'),
		label: Liferay.Language.get('text'),
		value: 'Text',
	},
];
