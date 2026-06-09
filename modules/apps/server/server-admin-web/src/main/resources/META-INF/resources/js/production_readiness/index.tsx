/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayEmptyState from '@clayui/empty-state';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import React, {useCallback, useEffect, useMemo, useState} from 'react';

import {fetchResults, ignoreRule, unignoreRule} from './api';
import CategorySection from './components/CategorySection';
import FilterPills from './components/FilterPills';
import SummaryHeader from './components/SummaryHeader';
import {
	FilterValue,
	ProductionReadinessDashboardProps,
	ResultsPayload,
	RuleResult,
} from './types';

const DELTAS = [{label: 10}, {label: 20}, {label: 30}, {label: 50}];

function applyFilter(
	results: RuleResult[],
	filterValue: FilterValue
): RuleResult[] {
	return results.filter((result) => {
		switch (filterValue) {
			case 'failed':
				return !result.ignored && result.status === 'FAIL';
			case 'ignored':
				return result.ignored;
			case 'passed':
				return !result.ignored && result.status === 'PASS';
			default:
				return true;
		}
	});
}

function groupByCategory(results: RuleResult[]): Array<{
	category: string;
	categoryLabel: string;
	results: RuleResult[];
}> {
	const byCategory = new Map<
		string,
		{categoryLabel: string; results: RuleResult[]}
	>();

	for (const result of results) {
		const category = result.category || Liferay.Language.get('other');

		const bucket = byCategory.get(category) ?? {
			categoryLabel: result.category
				? result.categoryLabel
				: Liferay.Language.get('other'),
			results: [],
		};

		bucket.results.push(result);

		byCategory.set(category, bucket);
	}

	return Array.from(byCategory.entries()).map(
		([category, {categoryLabel, results: categoryResults}]) => ({
			category,
			categoryLabel,
			results: categoryResults,
		})
	);
}

export function ProductionReadinessDashboard(
	props: ProductionReadinessDashboardProps
) {
	const [payload, setPayload] = useState<ResultsPayload | null>(null);
	const [loading, setLoading] = useState(true);
	const [error, setError] = useState<string | null>(null);
	const [filterValue, setFilterValue] = useState<FilterValue>('all');
	const [activePage, setActivePage] = useState(1);
	const [delta, setDelta] = useState(10);
	const [togglingRuleKeys, setTogglingRuleKeys] = useState<Set<string>>(
		new Set()
	);

	const load = useCallback(() => {
		setLoading(true);
		setError(null);

		return fetchResults(props.baseResourceURL)
			.then((data) => {
				setPayload(data);
				setLoading(false);
			})
			.catch((fetchError) => {
				setError(String(fetchError));
				setLoading(false);
			});
	}, [props.baseResourceURL]);

	useEffect(() => {
		load();
	}, [load]);

	const onFilterChange = useCallback((value: FilterValue) => {
		setFilterValue(value);
		setActivePage(1);
	}, []);

	const onDeltaChange = useCallback((value: number) => {
		setDelta(value);
		setActivePage(1);
	}, []);

	const onToggleIgnore = useCallback(
		async (result: RuleResult) => {
			setTogglingRuleKeys((current) => {
				const next = new Set(current);

				next.add(result.ruleKey);

				return next;
			});

			try {
				if (result.ignored) {
					await unignoreRule(props.baseResourceURL, result.ruleKey);
				}
				else {
					await ignoreRule(props.baseResourceURL, result.ruleKey);
				}

				await load();
			}
			catch (toggleError) {
				Liferay.Util.openToast({
					message: Liferay.Language.get(
						'your-request-failed-to-complete'
					),
					type: 'danger',
				});
			}
			finally {
				setTogglingRuleKeys((current) => {
					const next = new Set(current);

					next.delete(result.ruleKey);

					return next;
				});
			}
		},
		[load, props.baseResourceURL]
	);

	const {grouped, totalItems} = useMemo(() => {
		if (!payload) {
			return {grouped: [], totalItems: 0};
		}

		const sorted = [...applyFilter(payload.results, filterValue)].sort(
			(a, b) => a.category.localeCompare(b.category)
		);

		const pageResults = sorted.slice(
			(activePage - 1) * delta,
			activePage * delta
		);

		return {
			grouped: groupByCategory(pageResults),
			totalItems: sorted.length,
		};
	}, [activePage, delta, filterValue, payload]);

	useEffect(() => {
		const lastPage = Math.max(1, Math.ceil(totalItems / delta));

		if (activePage > lastPage) {
			setActivePage(lastPage);
		}
	}, [activePage, delta, totalItems]);

	if (loading) {
		return <ClayLoadingIndicator />;
	}

	if (error) {
		return (
			<ClayEmptyState
				description={error}
				title={Liferay.Language.get('an-unexpected-error-occurred')}
			/>
		);
	}

	if (!payload || !payload.results.length) {
		return (
			<ClayEmptyState
				description={Liferay.Language.get(
					'no-production-readiness-rules-are-deployed'
				)}
				title={Liferay.Language.get('no-results-were-found')}
			/>
		);
	}

	const {failed, ignored, passed} = payload.summary;

	return (
		<div className="production-readiness-dashboard">
			<div className="align-items-start d-flex justify-content-between mb-4">
				<SummaryHeader
					failed={failed}
					passed={passed}
					total={failed + ignored + passed}
				/>

				<FilterPills onChange={onFilterChange} value={filterValue} />
			</div>

			<div className="production-readiness-sections">
				{grouped.map(({category, categoryLabel, results}) => (
					<CategorySection
						categoryLabel={categoryLabel}
						key={category}
						onToggleIgnore={onToggleIgnore}
						results={results}
						togglingRuleKeys={togglingRuleKeys}
					/>
				))}
			</div>

			{totalItems > 0 && (
				<ClayPaginationBarWithBasicItems
					activeDelta={delta}
					activePage={activePage}
					deltas={DELTAS}
					ellipsisBuffer={3}
					onDeltaChange={onDeltaChange}
					onPageChange={setActivePage}
					totalItems={totalItems}
				/>
			)}
		</div>
	);
}
