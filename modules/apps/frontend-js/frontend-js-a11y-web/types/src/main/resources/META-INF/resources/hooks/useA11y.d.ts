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

import type {NodeResult, Result} from 'axe-core';
import type {A11yCheckerOptions} from '../A11yChecker';
declare type Target = string;
declare type RuleId = string;
declare type IframeId = string;
export interface RuleRaw extends Omit<Result, 'nodes'> {
	nodes: Array<Target>;
}
export declare type NodeViolations = Record<RuleId, NodeResult>;
export declare type Violations = {
	iframes: Record<IframeId, Array<Target>>;
	nodes: Record<Target, NodeViolations>;
	rules: Record<RuleId, RuleRaw>;
};
export default function useA11y(
	props: Omit<A11yCheckerOptions, 'callback'>
): Violations;
export {};
