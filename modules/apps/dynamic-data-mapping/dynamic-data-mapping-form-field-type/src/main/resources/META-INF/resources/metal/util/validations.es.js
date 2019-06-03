export const VALIDATIONS = {
	numeric: [
		{
			label: Liferay.Language.get('is-greater-than-or-equal-to'),
			name: 'lt',
			parameterMessage: Liferay.Language.get('number-placeholder'),
			regex: /^(.+)\<(\d+\.?\d*)?$/,
			template: '{name}<{parameter}'
		},
		{
			label: Liferay.Language.get('is-greater-than'),
			name: 'lteq',
			parameterMessage: Liferay.Language.get('number-placeholder'),
			regex: /^(.+)\<\=(\d+\.?\d*)?$/,
			template: '{name}<={parameter}'
		},
		{
			label: Liferay.Language.get('is-not-equal-to'),
			name: 'eq',
			parameterMessage: Liferay.Language.get('number-placeholder'),
			regex: /^(.+)\=\=(\d+\.?\d*)?$/,
			template: '{name}=={parameter}'
		},
		{
			label: Liferay.Language.get('is-less-than-or-equal-to'),
			name: 'gt',
			parameterMessage: Liferay.Language.get('number-placeholder'),
			regex: /^(.+)\>(\d+\.?\d*)?$/,
			template: '{name}>{parameter}'
		},
		{
			label: Liferay.Language.get('is-less-than'),
			name: 'gteq',
			parameterMessage: Liferay.Language.get('number-placeholder'),
			regex: /^(.+)\>\=(\d+\.?\d*)?$/,
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
