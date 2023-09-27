/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		key: keyof RuleRaw;
		value: string;
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
