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
