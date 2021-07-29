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
import type {RuleRaw, Violations} from '../hooks/useA11y';
declare type TViolationNext = {
	ruleId: string;
};
declare type onFilterChange = (
	type: keyof typeof TYPES,
	payload: {
		value: string;
		key: keyof RuleRaw;
	}
) => void;
declare type ViolationsPanelProps = {
	filters: Record<keyof RuleRaw, Array<string>>;
	next?: (payload: TViolationNext) => void;
	onFilterChange: onFilterChange;
	violations: Omit<Violations, 'iframes'>;
};
export default function ViolationsPanel({
	filters,
	next,
	onFilterChange,
	violations,
}: ViolationsPanelProps): JSX.Element;
export {};
