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

import React from 'react';

import Occurrence from './components/Occurrence';
import {StackNavigator} from './components/StackNavigator';
import Violation from './components/Violation';
import Violations from './components/Violations';
import {useFilteredViolations} from './hooks/useFilteredViolations';

import type {ImpactValue, Result} from 'axe-core';

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
	const [
		{filteredViolations, selectedCategories, selectedImpact},
		dispatch,
	] = useFilteredViolations(sortByImpact(violations));

	return (
		<div className="a11y-panel__sidebar sidebar sidebar-light">
			<StackNavigator>
				<Violations
					onFilterChange={(type, value) =>
						dispatch({payload: {value}, type})
					}
					selectedCategories={selectedCategories}
					selectedImpact={selectedImpact}
					violations={filteredViolations}
				/>
				<Violation violations={filteredViolations} />
				<Occurrence violations={filteredViolations} />
			</StackNavigator>
		</div>
	);
}
