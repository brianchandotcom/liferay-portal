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
import {FilteredViolationsContextProvider} from './components/useFilteredViolations';

function isBetweenRange({childrenCount, newIndex}) {
	return 0 <= newIndex && newIndex <= childrenCount;
}

function SidebarPanelsNavigator({children}) {
	const [activePageIndex, setActivePageIndex] = useState(
		PagesEnum.Violations
	);

	const [navigationState, setNavigationState] = useState({});

	return (
		<div className="page-accessibility-tool__sidebar sidebar sidebar-light">
			{React.Children.map(children, (child, index) => {
				const childrenCount = React.Children.count(children);

				if (index === activePageIndex) {
					return (
						child &&
						React.cloneElement(child, {
							...child.props,
							index: activePageIndex,
							key: index,
							navigationState,
							next: (newPayload) => {
								const newIndex = activePageIndex + 1;

								if (
									isBetweenRange({
										childrenCount,
										newIndex,
									})
								) {
									setActivePageIndex(newIndex);
									setNavigationState(newPayload);
								}
							},
							previous: (newPayload) => {
								const newIndex = activePageIndex - 1;

								if (
									isBetweenRange({
										childrenCount,
										newIndex,
									})
								) {
									setActivePageIndex(newIndex);

									if (newPayload) {
										setNavigationState(newPayload);
									}
								}
							},
						})
					);
				}
			})}
		</div>
	);
}

export const PagesEnum = Object.freeze({
	Occurrence: 2,
	Violation: 1,
	Violations: 0,
});

const IMPACT_PRIORITY = {
	critical: 4,
	minor: 1,
	moderate: 2,
	serious: 3,
};

function getImpactPriority(impact) {
	return IMPACT_PRIORITY[impact];
}

function sortByImpact(violations) {
	return violations.sort(
		(currentViolation, nextViolation) =>
			getImpactPriority(nextViolation.impact) -
			getImpactPriority(currentViolation.impact)
	);
}

export default function PageAccessibilityToolSidebar({violations}) {
	const sortedByImpactViolations = sortByImpact(violations);

	return (
		<FilteredViolationsContextProvider>
			<SidebarPanelsNavigator>
				<Violations violations={sortedByImpactViolations} />
				<Violation violations={sortedByImpactViolations} />
				<Occurrence violations={sortedByImpactViolations} />
			</SidebarPanelsNavigator>
		</FilteredViolationsContextProvider>
	);
}
