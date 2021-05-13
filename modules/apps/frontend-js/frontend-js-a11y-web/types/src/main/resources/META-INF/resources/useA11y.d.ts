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

import type {CheckResult, ImpactValue} from 'axe-core';
import type {A11yCheckerOptions} from './A11yChecker';
export declare type Violation = {
	all: Array<CheckResult>;
	any: Array<CheckResult>;
	help: string;
	helpUrl: string;
	id: string;
	impact?: ImpactValue;
};
declare type Violations = {
	modifyIndex: number;
	target: string;
	violations: Array<Violation>;
};
export default function useA11y(
	props: Omit<A11yCheckerOptions, 'callback'>
): Violations[];
export {};
