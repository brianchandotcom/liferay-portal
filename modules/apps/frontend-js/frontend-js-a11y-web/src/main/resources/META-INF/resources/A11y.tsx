/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import useIframeA11yChannel from './hooks/useIframeA11yChannel';
import {Kind} from './hooks/useIframeClient';

import type {A11yCheckerOptions} from './A11yChecker';

type Params = {
	name?: string;
	ruleId?: string;
	target?: string;
};

type IframePayload = {
	ruleId: string;
	target: string;
};

export function A11y(props: Omit<A11yCheckerOptions, 'callback'>) {
	const violations = useA11y(props);

	const [state, dispatch] = useFilterViolations(violations);

	const [params, setParams] = useState<Params>();

	const [activePage, setActivePage] = useState(0);

	const nodes = Object.keys(violations.nodes);

	useLayoutEffect(() => {
		if (nodes.length) {
			document.body.classList.add('a11y-body');

			return () => {
				document.body.classList.remove('a11y-body');
			};
		}
	}, [nodes]);

	const navigateToOccurrence = useCallback(
		(ruleId: string, target: string) => {
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
		},
		[dispatch]
	);

	useIframeA11yChannel<IframePayload, Kind>(
		violations.iframes,
		state.violations,
		({ruleId, target}, kind) => {
			if (kind === Kind.Click) {
				navigateToOccurrence(ruleId, target);
			}
		}
	);

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

	if (!nodes.length) {
		return null;
	}

	return (
		<>
			{Object.keys(state.violations.nodes)
				.filter((target) => !target.includes('/'))
				.map((target, index) => (
					<ViolationPopover
						key={`${target}:${index}`}
						onClick={(target, ruleId) =>
							navigateToOccurrence(ruleId, target)
						}
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
