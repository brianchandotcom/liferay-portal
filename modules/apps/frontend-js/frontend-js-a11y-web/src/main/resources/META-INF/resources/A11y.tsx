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

import React, {useLayoutEffect} from 'react';

import Occurrence from './components/Occurrence';
import {StackNavigator} from './components/StackNavigator';
import Violation from './components/Violation';
import {ViolationPopover} from './components/ViolationPopover';
import Violations from './components/Violations';
import useA11y from './hooks/useA11y';
import {useFilterViolations} from './hooks/useFilterViolations';

import type {A11yCheckerOptions} from './A11yChecker';

export function A11y(props: Omit<A11yCheckerOptions, 'callback'>) {
	const violations = useA11y(props);

	const [state, dispatch] = useFilterViolations(violations);

	const nodes = Object.keys(state.violations.nodes);

	useLayoutEffect(() => {
		if (nodes.length > 0) {
			document.body.classList.add('a11y-body');

			return () => {
				document.body.classList.remove('a11y-body');
			};
		}
	}, [nodes]);

	if (Object.keys(violations.nodes).length === 0) {
		return null;
	}

	return (
		<>
			{nodes.map((target, index) => (
				<ViolationPopover
					key={`${target}:${index}`}
					rules={state.violations.rules}
					target={target}
					violations={Object.keys(state.violations.nodes[target])}
				/>
			))}

			<div className="a11y-panel__sidebar sidebar sidebar-light">
				<StackNavigator>
					<Violations
						onFilterChange={(type, value) =>
							dispatch({payload: {value}, type})
						}
						{...state}
					/>
					<Violation violations={state.violations} />
					<Occurrence violations={state.violations} />
				</StackNavigator>
			</div>
		</>
	);
}
