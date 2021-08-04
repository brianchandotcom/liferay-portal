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

import React, {useCallback, useLayoutEffect, useState} from 'react';

import {ErrorBoundary} from './components/ErrorBoundary';
import {NotFound} from './components/NotFound';
import Occurrence from './components/Occurrence';
import {StackNavigator} from './components/StackNavigator';
import Violation from './components/Violation';
import {ViolationPopover} from './components/ViolationPopover';
import Violations from './components/Violations';
import useA11y from './hooks/useA11y';
import {TYPES, useFilterViolations} from './hooks/useFilterViolations';

import type {A11yCheckerOptions} from './A11yChecker';

type Params = {
	name?: string;
	ruleId?: string;
	target?: string;
};

export function A11y(props: Omit<A11yCheckerOptions, 'callback'>) {
	const violations = useA11y(props);

	const [state, dispatch] = useFilterViolations(violations);

	const [params, setParams] = useState<Params>();

	const [activePage, setActivePage] = useState(0);

	const nodes = Object.keys(violations.nodes);

	useLayoutEffect(() => {
		if (nodes.length > 0) {
			document.body.classList.add('a11y-body');

			return () => {
				document.body.classList.remove('a11y-body');
			};
		}
	}, [nodes]);

	const onParamsChange = useCallback(
		(newParams: Params) => {
			if (newParams?.ruleId) {
				dispatch({
					payload: {key: 'id', value: newParams.ruleId},
					type: TYPES.ADD_FILTER,
				});
			}
			else if (params?.ruleId) {
				dispatch({
					payload: {key: 'id', value: params.ruleId},
					type: TYPES.REMOVE_FILTER,
				});
			}

			if (newParams?.target) {
				dispatch({
					payload: {key: 'nodes', value: newParams.target},
					type: TYPES.ADD_FILTER,
				});
			}
			else if (params?.target) {
				dispatch({
					payload: {key: 'nodes', value: params.target},
					type: TYPES.REMOVE_FILTER,
				});
			}

			setParams(newParams);
		},
		[dispatch, params]
	);

	if (nodes.length === 0) {
		return null;
	}

	return (
		<>
			{Object.keys(state.violations.nodes).map((target, index) => (
				<ViolationPopover
					key={`${target}:${index}`}
					onClick={(target, ruleId) => {
						dispatch({
							payload: {key: 'id', value: ruleId},
							type: TYPES.ADD_FILTER,
						});
						dispatch({
							payload: {key: 'nodes', value: target},
							type: TYPES.ADD_FILTER,
						});
						setActivePage(2);
						setParams({
							name: Liferay.Util.sub(
								Liferay.Language.get('occurrence-x'),
								'1'
							),
							ruleId,
							target,
						});
					}}
					rules={state.violations.rules}
					target={target}
					violations={Object.keys(state.violations.nodes[target])}
				/>
			))}

			<div className="a11y-panel sidebar sidebar-light">
				<StackNavigator<Params>
					activePage={activePage}
					onActiveChange={setActivePage}
					onParamsChange={onParamsChange}
					params={params}
				>
					<Violations
						onFilterChange={(type, payload) =>
							dispatch({payload, type})
						}
						{...state}
					/>

					<ErrorBoundary
						fallback={
							<NotFound
								description={Liferay.Language.get(
									'the-rule-was-not-found-it-is-common-when-the-elements-corresponding-to-the-rule-no-longer-exist-in-the-dom'
								)}
								onClick={() => {
									const {ruleId} = params as Required<Params>;

									dispatch({
										payload: {
											key: 'id',
											value: ruleId,
										},
										type: TYPES.REMOVE_FILTER,
									});
									setActivePage(0);
									setParams(undefined);
								}}
								title={Liferay.Language.get('rule-not-found')}
							/>
						}
					>
						<Violation violations={state.violations} />
					</ErrorBoundary>

					<ErrorBoundary
						fallback={
							<NotFound
								description={Liferay.Language.get(
									'the-occurrence-was-not-found-it-is-common-when-the-element-no-longer-exists-in-the-dom'
								)}
								onClick={() => {
									const {ruleId, target} = params as Required<
										Params
									>;

									dispatch({
										payload: {
											key: 'nodes',
											value: target,
										},
										type: TYPES.REMOVE_FILTER,
									});
									dispatch({
										payload: {
											key: 'id',
											value: ruleId,
										},
										type: TYPES.REMOVE_FILTER,
									});
									setActivePage(0);
									setParams(undefined);
								}}
								title={Liferay.Language.get(
									'occurrence-not-found'
								)}
							/>
						}
					>
						<Occurrence violations={state.violations} />
					</ErrorBoundary>
				</StackNavigator>
			</div>
		</>
	);
}
