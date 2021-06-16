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
import ClayDropDown from '@clayui/drop-down';
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import React, {useState} from 'react';

import {
	TYPES,
	useFilteredViolationsDispatch,
} from '../hooks/useFilteredViolations';
import Rule from './Rule';

import type {ImpactValue, Result} from 'axe-core';

const IMPACT_FILTER_OPTIONS = [
	{label: Liferay.Language.get('critical'), value: 'critical'},
	{label: Liferay.Language.get('serious'), value: 'serious'},
	{label: Liferay.Language.get('moderate'), value: 'moderate'},
	{label: Liferay.Language.get('minor'), value: 'minor'},
];

const CATEGORY_FILTER_OPTIONS = [
	{label: 'WCAG 2.0A', value: 'wcag2a'},
	{label: 'WCAG 2.0 Level AA', value: 'wcag2aa'},
	{label: 'WCAG 2.1 Level A', value: 'wcag21a'},
	{label: 'WCAG 2.1 Level AA', value: 'wcag21aa'},
	{label: 'Best Practices', value: 'best-practice'},
];

type ViolationsFilterProps = {
	selectedCategories: Array<String>;
	selectedImpact: Array<ImpactValue>;
};

function ViolationsFilter({
	selectedCategories,
	selectedImpact,
}: ViolationsFilterProps) {
	const [dropdownExpanded, setDropdownExpanded] = useState(false);
	const dispatch = useFilteredViolationsDispatch();

	return (
		<>
			<ClayDropDown
				active={dropdownExpanded}
				closeOnClickOutside
				onActiveChange={setDropdownExpanded}
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
			>
				<ClayDropDown.ItemList>
					<ClayDropDown.Group
						header={Liferay.Language.get('filter-by-impact')}
					>
						{IMPACT_FILTER_OPTIONS.map((item) => {
							const existsImpact =
								selectedImpact.findIndex(
									(element) => element === item.value
								) !== -1;

							return (
								<ClayDropDown.Section
									active={existsImpact}
									key={item.value}
								>
									<ClayCheckbox
										checked={existsImpact}
										data-testid={item.value}
										label={item.label}
										onChange={() => {
											dispatch({
												payload: {
													value: item.value,
												},
												type: existsImpact
													? TYPES.IMPACT_REMOVE
													: TYPES.IMPACT_ADD,
											});
										}}
									></ClayCheckbox>
								</ClayDropDown.Section>
							);
						})}
					</ClayDropDown.Group>
					<ClayDropDown.Group
						header={Liferay.Language.get('filter-by-category')}
					>
						{CATEGORY_FILTER_OPTIONS.map((item) => {
							const existsCategory =
								selectedCategories.findIndex(
									(element) => element === item.value
								) !== -1;

							return (
								<ClayDropDown.Section key={item.value}>
									<ClayCheckbox
										checked={existsCategory}
										data-testid={item.value}
										label={item.label}
										onChange={() => {
											dispatch({
												payload: {
													value: item.value,
												},
												type: existsCategory
													? TYPES.CATEGORY_REMOVE
													: TYPES.CATEGORY_ADD,
											});
										}}
									></ClayCheckbox>
								</ClayDropDown.Section>
							);
						})}
					</ClayDropDown.Group>
				</ClayDropDown.ItemList>
			</ClayDropDown>
		</>
	);
}

type TViolationNext = {
	violationIndex: number;
};

type ViolationsProps = {
	next?: (payload: TViolationNext) => void;
	selectedCategories: Array<String>;
	selectedImpact: Array<ImpactValue>;
	violations: Array<Result>;
};

export default function Violations({
	next,
	selectedCategories,
	selectedImpact,
	violations,
}: ViolationsProps) {
	const hasViolations = !!violations.length;

	return (
		<>
			<div className="sidebar-section">
				<div className="a11y-panel__sidebar--violations-panel-header-title">
					<div className="inline-item inline-item-before">
						<ClayIcon
							className="text-danger"
							symbol="info-circle"
						/>
					</div>
					<div className="inline-item">
						<span className="list-group-title">
							{Liferay.Language.get('accessibility-violations')}
						</span>
					</div>
					<div className="inline-item inline-item-after">
						<ViolationsFilter
							selectedCategories={selectedCategories}
							selectedImpact={selectedImpact}
						/>
					</div>
				</div>
			</div>
			<div className="a11y-panel__sidebar--violations-panel-header-description">
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
				>
					{violations.map((violation, index) => {
						const {id, impact, nodes} = violation;

						return (
							<Rule
								aria-label={Liferay.Util.sub(
									Liferay.Language.get(
										'navigate-to-violation-x'
									),
									id
								)}
								key={id}
								onClick={() => {
									if (next) {
										next({violationIndex: index});
									}
								}}
								quantity={nodes.length}
								ruleSubtext={impact}
								ruleTitle={id}
							/>
						);
					})}
				</ClayList>
			)}
		</>
	);
}
