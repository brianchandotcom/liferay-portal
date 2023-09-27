/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import type {NodeViolations, RuleRaw, Violations} from './useA11y';
export declare const TYPES: {
	readonly ADD_FILTER: 'ADD_FILTER';
	readonly REMOVE_FILTER: 'REMOVE_FILTER';
};
declare type TAction = {
	payload: {
		key: keyof RuleRaw;
		value: string;
	};
	type: keyof typeof TYPES;
};
export declare function useFilterViolations(
	value: Violations
): readonly [
	{
		readonly filters: Record<keyof RuleRaw, string[]>;
		readonly violations: {
			readonly nodes: Record<string, NodeViolations>;
			readonly rules: Record<string, RuleRaw>;
		};
	},
	import('react').Dispatch<TAction>
];
export {};
