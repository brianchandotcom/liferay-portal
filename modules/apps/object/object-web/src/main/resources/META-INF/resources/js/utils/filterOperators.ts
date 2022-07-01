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

export const PICKLIST_OPERATORS: LabelValueObject[] = [
	{
		label: Liferay.Language.get('choose-an-option'),
		value: '',
	},
	{
		label: Liferay.Language.get('includes'),
		value: 'includes',
	},
	{
		label: Liferay.Language.get('excludes'),
		value: 'excludes',
	},
];

export const DATE_OPERATORS: LabelValueObject[] = [
	{
		label: Liferay.Language.get('range'),
		value: 'range',
	},
];

export const NUMERIC_OPERATORS: LabelValueObject[] = [
	{
		label: Liferay.Language.get('equals-to'),
		value: 'eq',
	},
	{
		label: Liferay.Language.get('not-equals-to'),
		value: 'ne',
	},
];
