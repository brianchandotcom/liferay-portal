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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import React, {useMemo, useState} from 'react';

import Rule from './Rule';
import {
	TYPES,
	useFilteredViolationsDispatch,
	useFilteredViolationsValues,
} from './useFilteredViolations';

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

function ViolationsFilter() {
	const [dropdownExpanded, setDropdownExpanded] = useState(false);
	const dispatch = useFilteredViolationsDispatch();
	const {selectedCategories, selectedImpact} = useFilteredViolationsValues();

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
											if (existsImpact) {
												dispatch({
													payload: item.value,
													type: TYPES.IMPACT_REMOVE,
												});

												return;
											}

											dispatch({
												payload: item.value,
												type: TYPES.IMPACT_ADD,
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
											if (existsCategory) {
												dispatch({
													payload: item.value,
													type: TYPES.CATEGORY_REMOVE,
												});

												return;
											}
											dispatch({
												payload: item.value,
												type: TYPES.CATEGORY_ADD,
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

function ViolationsPanelHeaderTitle() {
	return (
		<div className="page-accessibility-tool__sidebar--violations-panel-header-title">
			<div className="inline-item inline-item-before">
				<ClayIcon className="text-danger" symbol="info-circle" />
			</div>
			<div className="inline-item">
				<span className="list-group-title">
					{Liferay.Language.get('accessibility-violations')}
				</span>
			</div>
			<div className="inline-item inline-item-after">
				<ViolationsFilter />
			</div>
		</div>
	);
}

function filterByCategories({receivedTags, selectedCategories}) {
	return selectedCategories.some((category) =>
		receivedTags.includes(category)
	);
}

function filterByImpact({receivedImpact, selectedImpact}) {
	return selectedImpact.includes(receivedImpact);
}

export default function Violations({next, violations: initialViolations}) {
	const {selectedCategories, selectedImpact} = useFilteredViolationsValues();

	const violations = useMemo(() => {
		return initialViolations.filter(({impact, tags}) => {
			let removeFlag = true;

			if (selectedImpact.length) {
				removeFlag = filterByImpact({
					receivedImpact: impact,
					selectedImpact,
				});
			}

			if (selectedCategories.length) {
				removeFlag = filterByCategories({
					receivedTags: tags,
					selectedCategories,
				});
			}

			return removeFlag;
		});
	}, [selectedCategories, selectedImpact, initialViolations]);

	const hasViolations = !!violations.length;

	return (
		<>
			<div className="sidebar-section">
				<ViolationsPanelHeaderTitle />
			</div>
			<div className="page-accessibility-tool__sidebar--violations-panel-header-description">
				{!hasViolations
					? Liferay.Language.get(
							'there-are-no-accessibility-violations-in-this-page'
					  )
					: Liferay.Language.get(
							'set-of-rules-violated-by-the-highlighted-issues'
					  )}
			</div>
			{hasViolations && (
				<ClayList
					aria-label={Liferay.Language.get('violations-list')}
					className="list-group-flush"
					role="tablist"
				>
					{violations.map((violation, index) => {
						const {id, impact, nodes, ...props} = violation;

						return (
							<Rule
								ariaLabel={Liferay.Util.sub(
									Liferay.Language.get(
										'navigate-to-violation-x'
									),
									id
								)}
								id={id}
								impact={impact}
								key={id}
								nodes={nodes}
								onListItemClick={() =>
									next({violationIndex: index})
								}
								quantity={nodes.length}
								subtext={impact}
								title={id}
								{...props}
							/>
						);
					})}
				</ClayList>
			)}
		</>
	);
}
