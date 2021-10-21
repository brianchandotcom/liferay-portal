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

import './Violations.scss';

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayList from '@clayui/list';
import React from 'react';

import {TYPES} from '../hooks/useFilterViolations';
import Rule from './Rule';

import type {ImpactValue} from 'axe-core';

import type {RuleRaw, Violations} from '../hooks/useA11y';

const IMPACT_FILTER_OPTIONS = [
	{
		'data-testid': 'critical',
		'label': Liferay.Language.get('critical'),
		'value': 'critical',
	},
	{
		'data-testid': 'serious',
		'label': Liferay.Language.get('serious'),
		'value': 'serious',
	},
	{
		'data-testid': 'moderate',
		'label': Liferay.Language.get('moderate'),
		'value': 'moderate',
	},
	{
		'data-testid': 'minor',
		'label': Liferay.Language.get('minor'),
		'value': 'minor',
	},
];

const CATEGORY_FILTER_OPTIONS = [
	{label: 'WCAG 2.0A', value: 'wcag2a'},
	{
		'data-testid': 'wcag2aa',
		'label': 'WCAG 2.0 Level AA',
		'value': 'wcag2aa',
	},
	{label: 'WCAG 2.1 Level A', value: 'wcag21a'},
	{label: 'WCAG 2.1 Level AA', value: 'wcag21aa'},
	{
		'data-testid': 'best-practice',
		'label': 'Best Practices',
		'value': 'best-practice',
	},
];

const IMPACT_PRIORITY = {
	critical: 4,
	minor: 1,
	moderate: 2,
	serious: 3,
} as const;

const getImpactPriority = (impact: ImpactValue) =>
	impact ? IMPACT_PRIORITY[impact] : IMPACT_PRIORITY.minor;

const sortByImpact = (violations: Array<RuleRaw>) =>
	violations.sort((currentViolation, nextViolation) => {
		if (nextViolation.impact && currentViolation.impact) {
			return (
				getImpactPriority(nextViolation.impact) -
				getImpactPriority(currentViolation.impact)
			);
		}

		return 0;
	});

type TViolationNext = {
	ruleId: string;
};

type onFilterChange = (
	type: keyof typeof TYPES,
	payload: {value: string; key: keyof RuleRaw}
) => void;

type ViolationsPanelProps = {
	filters: Record<keyof RuleRaw, Array<string>>;
	next?: (payload: TViolationNext) => void;
	onFilterChange: onFilterChange;
	violations: Omit<Violations, 'iframes'>;
};

const getItems = (
	onChange: onFilterChange,
	getChecked: (value: string) => boolean
) => {
	return [
		{
			items: IMPACT_FILTER_OPTIONS.map(
				({label, value, ...otherProps}) => ({
					...otherProps,
					checked: getChecked(value),
					label,
					onChange: (checked: boolean) =>
						onChange(
							checked ? TYPES.ADD_FILTER : TYPES.REMOVE_FILTER,
							{key: 'impact', value}
						),
					type: 'checkbox' as const,
				})
			),
			label: Liferay.Language.get('filter-by-impact'),
			type: 'group' as const,
		},
		{
			items: CATEGORY_FILTER_OPTIONS.map(
				({label, value, ...otherProps}) => ({
					...otherProps,
					checked: getChecked(value),
					label,
					onChange: (checked: boolean) =>
						onChange(
							checked ? TYPES.ADD_FILTER : TYPES.REMOVE_FILTER,
							{key: 'tags', value}
						),
					type: 'checkbox' as const,
				})
			),
			label: Liferay.Language.get('filter-by-category'),
			type: 'group' as const,
		},
	];
};

export default function ViolationsPanel({
	filters,
	next,
	onFilterChange,
	violations,
}: ViolationsPanelProps) {
	const rules = sortByImpact(Object.values(violations.rules));

	const hasViolations = Boolean(rules.length);

	return (
		<>
			<div className="sidebar-section">
				<div className="a11y-panel--violations-header">
					<ClayLayout.ContentRow noGutters verticalAlign="center">
						<ClayLayout.ContentCol>
							<ClayLayout.ContentSection>
								<ClayIcon
									className="text-danger"
									symbol="info-circle"
								/>
							</ClayLayout.ContentSection>
						</ClayLayout.ContentCol>

						<ClayLayout.ContentCol expand>
							<ClayLayout.ContentSection>
								<span className="component-title">
									{Liferay.Language.get(
										'accessibility-violations'
									)}
								</span>
							</ClayLayout.ContentSection>
						</ClayLayout.ContentCol>

						<ClayLayout.ContentCol>
							<ClayDropDownWithItems
								closeOnClickOutside
								items={getItems(
									onFilterChange,
									(value) =>
										filters.impact?.includes(value) ||
										filters.tags?.includes(value)
								)}
								menuElementAttrs={{className: 'a11y-dropdown'}}
								trigger={
									<ClayButtonWithIcon
										aria-label={Liferay.Language.get(
											'open-violations-filter'
										)}
										displayType="unstyled"
										small
										symbol="filter"
									/>
								}
							/>
						</ClayLayout.ContentCol>
					</ClayLayout.ContentRow>
				</div>
			</div>

			<div className="a11y-panel--violations-description">
				{!hasViolations
					? Liferay.Language.get(
							'there-are-no-accessibility-violations-in-this-page'
					  )
					: Liferay.Language.get(
							'check-the-list-of-violated-rules-highlighted-on-the-page'
					  )}
			</div>

			{hasViolations && (
				<ClayList
					aria-label={Liferay.Language.get('violations-list')}
					className="list-group-flush"
					role="tablist"
					showQuickActionsOnHover={false}
				>
					{rules.map(({id, impact, nodes}) => (
						<Rule
							aria-label={Liferay.Util.sub(
								Liferay.Language.get('navigate-to-violation-x'),
								id
							)}
							key={id}
							onClick={() => next!({ruleId: id})}
							quantity={nodes.length}
							ruleSubtext={impact}
							ruleTitle={id}
						/>
					))}
				</ClayList>
			)}
		</>
	);
}
