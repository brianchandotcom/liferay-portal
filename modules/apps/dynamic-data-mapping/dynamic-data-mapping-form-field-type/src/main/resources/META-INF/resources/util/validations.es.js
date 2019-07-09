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

export const VALIDATIONS = {
	numeric: [
		{
			label: Liferay.Language.get('is-greater-than-or-equal-to'),
			name: 'lt',
			parameterMessage: Liferay.Language.get('number-placeholder'),
			regex: /^(.+)<(\d+\.?\d*)?$/,
			template: '{name}<{parameter}'
		},
		{
			label: Liferay.Language.get('is-greater-than'),
			name: 'lteq',
			parameterMessage: Liferay.Language.get('number-placeholder'),
			regex: /^(.+)<=(\d+\.?\d*)?$/,
			template: '{name}<={parameter}'
		},
		{
			label: Liferay.Language.get('is-not-equal-to'),
			name: 'eq',
			parameterMessage: Liferay.Language.get('number-placeholder'),
			regex: /^(.+)==(\d+\.?\d*)?$/,
			template: '{name}=={parameter}'
		},
		{
			label: Liferay.Language.get('is-less-than-or-equal-to'),
			name: 'gt',
			parameterMessage: Liferay.Language.get('number-placeholder'),
			regex: /^(.+)>(\d+\.?\d*)?$/,
			template: '{name}>{parameter}'
		},
		{
			label: Liferay.Language.get('is-less-than'),
			name: 'gteq',
			parameterMessage: Liferay.Language.get('number-placeholder'),
			regex: /^(.+)>=(\d+\.?\d*)?$/,
			template: '{name}>={parameter}'
		}
	],
	string: [
		{
			label: Liferay.Language.get('contains'),
			name: 'notContains',
			parameterMessage: Liferay.Language.get('text'),
			regex: /^NOT\(contains\((.+), "(.*)"\)\)$/,
			template: 'NOT(contains({name}, "{parameter}"))'
		},
		{
			label: Liferay.Language.get('does-not-contain'),
			name: 'contains',
			parameterMessage: Liferay.Language.get('text'),
			regex: /^contains\((.+), "(.*)"\)$/,
			template: 'contains({name}, "{parameter}")'
		},
		{
			label: Liferay.Language.get('is-not-url'),
			name: 'url',
			parameterMessage: '',
			regex: /^isURL\((.+)\)$/,
			template: 'isURL({name})'
		},
		{
			label: Liferay.Language.get('is-not-email'),
			name: 'email',
			parameterMessage: '',
			regex: /^isEmailAddress\((.+)\)$/,
			template: 'isEmailAddress({name})'
		},
		{
			label: Liferay.Language.get('does-not-match'),
			name: 'regularExpression',
			parameterMessage: Liferay.Language.get('regular-expression'),
			regex: /^match\((.+), "(.*)"\)$/,
			template: 'match({name}, "{parameter}")'
		}
	]
};

export default VALIDATIONS;
