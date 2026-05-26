/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {
	Attribute,
	Audience,
	AudiencesDefinition,
	Combinator,
	LeafRule,
	Operator,
	Retention,
	Rule,
	RuleGroup,
} from '../index';

const ATTRIBUTES: Attribute[] = [
	'browser_language',
	'browser_name',
	'browser_version',
	'hostname',
	'local_date',
	'local_hour',
	'pathname',
	'referrer',
	'segments',
	'url',
	'user_agent',
];

const COMBINATORS: Combinator[] = ['and', 'or'];

const COOKIE_PREFIX = 'cookie:';

const OPERATORS: Operator[] = ['between', 'eq', 'include', 'matches'];

const RETENTIONS: Retention[] = ['BROWSER', 'PAGE', 'TAB'];

const SEARCH_PARAM_PREFIX = 'search_param:';

export function check(audiencesDefinition: AudiencesDefinition) {
	const what = 'Audiences definition';

	checkObject(audiencesDefinition, what);
	checkKeys(audiencesDefinition, ['audiences'], what);
	checkArray(audiencesDefinition.audiences, `${what} field 'audiences'`);

	for (let i = 0; i < audiencesDefinition.audiences.length; i++) {
		checkAudience(audiencesDefinition.audiences[i], i);
	}
}

function checkAudience(audience: Audience, index: number) {
	const what = `Audience '${audience?.id ?? `#${index + 1}`}'`;

	checkObject(audience, what);
	checkKeys(audience, ['combinator', 'id', 'retention', 'rules'], what);
	checkString(audience.id, `${what} field 'id'`);
	checkOneOf(audience.combinator, COMBINATORS, `${what} field 'combinator'`);
	checkOneOf(audience.retention, RETENTIONS, `${what} field 'retention'`);
	checkRules(audience.rules, `${what} field 'rules'`);
}

function checkKeys(thing: any, expectedKeys: string[], what: string) {
	for (const actualKey of Object.keys(thing)) {
		if (!expectedKeys.includes(actualKey)) {
			throw new Error(`${what} has unexpected field '${actualKey}'`);
		}
	}

	for (const expectedKey of expectedKeys) {
		if (!(expectedKey in thing)) {
			throw new Error(`${what} is missing field '${expectedKey}'`);
		}
	}
}

function checkOneOf<T extends string>(value: any, allowed: T[], what: string) {
	if (!allowed.includes(value)) {
		throw new Error(
			`${what} must be one of: ${allowed.map((v) => `'${v}'`).join(', ')}`
		);
	}
}

function checkRules(rules: Rule[], what: string) {
	checkArray(rules, what);

	for (let i = 0; i < rules.length; i++) {
		checkRule(rules[i], `${what}[${i}]`);
	}
}

function checkRule(rule: Rule, what: string) {
	checkObject(rule, what);

	if ('combinator' in rule) {
		checkRuleGroup(rule, what);
	}
	else {
		checkLeafRule(rule as LeafRule, what);
	}
}

function checkRuleGroup(ruleGroup: RuleGroup, what: string) {
	checkKeys(ruleGroup, ['combinator', 'rules'], what);
	checkOneOf(ruleGroup.combinator, COMBINATORS, `${what} field 'combinator'`);
	checkRules(ruleGroup.rules, `${what} field 'rules'`);
}

function checkLeafRule(leafRule: LeafRule, what: string) {
	checkKeys(leafRule, ['attr', 'op', 'val'], what);
	checkAttribute(leafRule.attr, `${what} field 'attr'`);
	checkOneOf(leafRule.op, OPERATORS, `${what} field 'op'`);
}

function checkAttribute(value: any, what: string) {
	checkString(value, what);

	if (
		value.startsWith(COOKIE_PREFIX) ||
		value.startsWith(SEARCH_PARAM_PREFIX)
	) {
		return;
	}

	if (!ATTRIBUTES.includes(value)) {
		throw new Error(
			`${what} must be one of: ${ATTRIBUTES.map((v) => `'${v}'`).join(', ')}, ` +
				`'${COOKIE_PREFIX}<name>', '${SEARCH_PARAM_PREFIX}<name>'`
		);
	}
}

function checkArray(thing: any, what: string) {
	if (!Array.isArray(thing)) {
		throw new Error(`${what} must be an array`);
	}
}

function checkObject(thing: any, what: string) {
	if (thing === null || typeof thing !== 'object' || Array.isArray(thing)) {
		throw new Error(`${what} must be an object`);
	}
}

function checkString(thing: any, what: string) {
	if (typeof thing !== 'string') {
		throw new Error(`${what} must be a string`);
	}
}
