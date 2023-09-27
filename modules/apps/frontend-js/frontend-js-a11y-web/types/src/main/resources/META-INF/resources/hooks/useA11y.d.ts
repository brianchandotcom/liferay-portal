/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
