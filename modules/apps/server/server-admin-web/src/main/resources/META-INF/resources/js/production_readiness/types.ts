/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export type RuleStatus = 'PASS' | 'FAIL';

export type RuleSeverity = 'CRITICAL' | 'HIGH' | 'LOW' | 'MEDIUM';

export interface RuleResult {
	category: string;
	categoryLabel: string;
	currentValue?: string;
	ignored: boolean;
	message: string;
	name: string;
	recommendedValue?: string;
	ruleKey: string;
	severity: RuleSeverity;
	status: RuleStatus;
}

export interface Summary {
	failed: number;
	ignored: number;
	passed: number;
}

export interface ResultsPayload {
	results: RuleResult[];
	summary: Summary;
}

export interface ProductionReadinessDashboardProps {
	baseResourceURL: string;
}

export type FilterValue = 'all' | 'failed' | 'ignored' | 'passed';
