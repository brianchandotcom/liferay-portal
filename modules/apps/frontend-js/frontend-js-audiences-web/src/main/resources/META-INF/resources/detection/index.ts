/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {log} from '../log';
import {getBrowserLanguage} from './attributes/browser_language';
import {getBrowserName} from './attributes/browser_name';
import {getBrowserVersion} from './attributes/browser_version';
import {getCookie} from './attributes/cookie';
import {getHostname} from './attributes/hostname';
import {getLocalDate} from './attributes/local_date';
import {getLocalHour} from './attributes/local_hour';
import {getPathname} from './attributes/pathname';
import {getReferrer} from './attributes/referrer';
import {getSearchParam} from './attributes/search_param';
import {getSegments} from './attributes/segments';
import {getUrl} from './attributes/url';
import {getUserAgent} from './attributes/user_agent';
import {between} from './operators/between';
import {eq} from './operators/eq';
import {include} from './operators/include';
import {matches} from './operators/matches';

import type {
	Attribute,
	AudiencesDefinition,
	Combinator,
	Operator,
	Retention,
	Rule,
} from '../index';

export interface AudienceMatch {
	id: string;
	retention: Retention;
}

interface OperatorImpl {
	(actual: any, expected: any): boolean;
}

const COOKIE_PREFIX = 'cookie:';
const SEARCH_PARAM_PREFIX = 'search_param:';

export class Detection {
	private _audiencesDefinition: AudiencesDefinition;

	constructor(audiencesDefinition: AudiencesDefinition) {
		this._audiencesDefinition = audiencesDefinition;
	}

	async run(): Promise<AudienceMatch[]> {
		const matches: {[key: string]: AudienceMatch} = {};

		for (const audience of this._audiencesDefinition.audiences) {
			const {combinator, id, retention, rules} = audience;

			const matched = await evaluateGroup(combinator, rules);

			if (matched) {
				log(`Matched ${retention} audience: ${id}`);

				matches[id] = {
					id,
					retention,
				};
			}
		}

		return Object.values(matches);
	}
}

async function evaluateGroup(
	combinator: Combinator,
	rules: Rule[]
): Promise<boolean> {
	const results = await Promise.all(rules.map(evaluateRule));

	return combinator === 'and'
		? results.every(Boolean)
		: results.some(Boolean);
}

async function evaluateRule(rule: Rule): Promise<boolean> {
	if ('combinator' in rule) {
		return evaluateGroup(rule.combinator, rule.rules);
	}

	const attribute = await getAttribute(rule.attr);
	const operator = getOperator(rule.op);

	return operator(attribute, rule.val);
}

async function getAttribute(attr: Attribute): Promise<any> {
	if (attr === 'browser_language') {
		return getBrowserLanguage();
	}
	else if (attr === 'browser_name') {
		return getBrowserName();
	}
	else if (attr === 'browser_version') {
		return getBrowserVersion();
	}
	else if (attr.startsWith(COOKIE_PREFIX)) {
		return getCookie(attr.slice(COOKIE_PREFIX.length));
	}
	else if (attr === 'hostname') {
		return getHostname();
	}
	else if (attr === 'local_date') {
		return getLocalDate();
	}
	else if (attr === 'local_hour') {
		return getLocalHour();
	}
	else if (attr === 'pathname') {
		return getPathname();
	}
	else if (attr === 'referrer') {
		return getReferrer();
	}
	else if (attr.startsWith(SEARCH_PARAM_PREFIX)) {
		return getSearchParam(attr.slice(SEARCH_PARAM_PREFIX.length));
	}
	else if (attr === 'segments') {
		return getSegments();
	}
	else if (attr === 'url') {
		return getUrl();
	}
	else if (attr === 'user_agent') {
		return getUserAgent();
	}
	else {
		throw new Error(`Unsupported attribute: ${attr}`);
	}
}

function getOperator(op: Operator): OperatorImpl {
	if (op === 'between') {
		return between;
	}
	else if (op === 'eq') {
		return eq;
	}
	else if (op === 'include') {
		return include;
	}
	else if (op === 'matches') {
		return matches;
	}
	else {
		throw new Error(`Unsupported operator: ${op}`);
	}
}
