/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	clear,
	get,
	on,
	runDetection,
	runHandlers,
	setLogEnabled,
} from './implementation';

// JSON API

export interface AudiencesDefinition {
	audiences: Audience[];
}

export interface Audience {
	combinator: Combinator;
	id: string;
	retention: Retention;
	rules: Rule[];
}

export type Combinator = 'and' | 'or';

export type Retention = 'BROWSER' | 'PAGE' | 'TAB';

export type Rule = LeafRule | RuleGroup;

export interface LeafRule {
	attr: Attribute;
	op: Operator;
	val: any;
}

export interface RuleGroup {
	combinator: Combinator;
	rules: Rule[];
}

export type Attribute =
	| 'browser_language'
	| 'browser_name'
	| 'browser_version'
	| `cookie:${string}`
	| 'hostname'
	| 'local_date'
	| 'local_hour'
	| 'pathname'
	| 'referrer'
	| `search_param:${string}`
	| 'segments'
	| 'url'
	| 'user_agent';
export type Operator = 'between' | 'eq' | 'include' | 'matches';

// JavaScript API

export interface Handler {
	name: string | undefined;
	(): Promise<void> | void;
}

export interface AudiencesAPI {
	clear(retention?: Retention): void;
	get(): Set<string>;
	on(audienceId: string, handler: Handler): void;
	runDetection(audiencesDefinitionURL: string): Promise<void>;
	runHandlers(): Promise<void>;
	setLogEnabled(enabled: boolean): void;
}

export const audiences: AudiencesAPI = {
	clear,
	get,
	on,
	runDetection,
	runHandlers,
	setLogEnabled,
};
