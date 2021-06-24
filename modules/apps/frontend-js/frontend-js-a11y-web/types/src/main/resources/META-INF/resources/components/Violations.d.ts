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

import './Violations.scss';
import {TYPES} from '../hooks/useFilterViolations';
import type {ImpactValue} from 'axe-core';
import type {Violations} from '../hooks/useA11y';
declare type TViolationNext = {
	ruleId: string;
};
declare type ViolationsPanelProps = {
	next?: (payload: TViolationNext) => void;
	onFilterChange: (type: keyof typeof TYPES, value: string) => void;
	selectedCategories: Array<String>;
	selectedImpact: Array<ImpactValue>;
	violations: Violations;
};
export default function ViolationsPanel({
	next,
	onFilterChange,
	selectedCategories,
	selectedImpact,
	violations,
}: ViolationsPanelProps): JSX.Element;
export {};
