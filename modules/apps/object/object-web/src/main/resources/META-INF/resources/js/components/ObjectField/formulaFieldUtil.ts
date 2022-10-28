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
