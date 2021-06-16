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

import React, {useState} from 'react';

import Occurrence from './components/Occurrence';
import Violation from './components/Violation';
import Violations from './components/Violations';
import {FilteredViolationsContextProvider} from './hooks/useFilteredViolations';

import type {ImpactValue, Result} from 'axe-core';

function isBetweenRange(childrenCount: number, newIndex: number) {
	return 0 <= newIndex && newIndex <= childrenCount;
}

type SidebarPanelNavigatorProps = {
	children: React.ReactNode;
};

type TNavigationState = {
	occurrenceIndex?: number;
	occurrenceName?: string;
	violationsIndex: number;
};

function SidebarPanelsNavigator({children}: SidebarPanelNavigatorProps) {
	const [activePageIndex, setActivePageIndex] = useState(0);

	const [navigationState, setNavigationState] = useState<TNavigationState>({
		violationsIndex: 0,
	});

	return (
		<div className="a11y-panel__sidebar sidebar sidebar-light">
			{React.Children.map(children, (child, index) => {
				const childrenCount = React.Children.count(children);

				if (index === activePageIndex && React.isValidElement(child)) {
					return (
						child &&
						React.cloneElement(child, {
							index: activePageIndex,
							key: index,
							navigationState,
							next: (newPayload: TNavigationState) => {
								const newIndex = activePageIndex + 1;

								if (isBetweenRange(childrenCount, newIndex)) {
									setActivePageIndex(newIndex);
									setNavigationState(newPayload);
								}
							},
							previous: (newPayload: TNavigationState) => {
								const newIndex = activePageIndex - 1;

								if (isBetweenRange(childrenCount, newIndex)) {
									setActivePageIndex(newIndex);

									if (newPayload) {
										setNavigationState(newPayload);
									}
								}
							},
							...child.props,
						})
					);
				}
			})}
		</div>
	);
}

const IMPACT_PRIORITY = {
	critical: 4,
	minor: 1,
	moderate: 2,
	serious: 3,
} as const;

function getImpactPriority(impact: ImpactValue) {
	if (impact) {
		return IMPACT_PRIORITY[impact];
	}

	return IMPACT_PRIORITY.minor;
}

function sortByImpact(violations: Array<Result>) {
	return violations.sort((currentViolation, nextViolation) => {
		if (nextViolation.impact && currentViolation.impact) {
			return (
				getImpactPriority(nextViolation.impact) -
				getImpactPriority(currentViolation.impact)
			);
		}

		return 0;
	});
}

type A11yPanelProps = {
	violations: Array<Result>;
};

export function A11yPanel({violations}: A11yPanelProps) {
	const sortedByImpactViolations = sortByImpact(violations);

	return (
		<FilteredViolationsContextProvider value={sortedByImpactViolations}>
			{({filteredViolations, selectedCategories, selectedImpact}) => (
				<SidebarPanelsNavigator>
					<Violations
						selectedCategories={selectedCategories}
						selectedImpact={selectedImpact}
						violations={filteredViolations}
					/>
					<Violation violations={filteredViolations} />
					<Occurrence violations={filteredViolations} />
				</SidebarPanelsNavigator>
			)}
		</FilteredViolationsContextProvider>
	);
}
