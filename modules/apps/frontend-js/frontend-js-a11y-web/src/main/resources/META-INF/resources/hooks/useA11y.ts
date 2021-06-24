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

import {useEffect, useState} from 'react';

import {A11yChecker} from '../A11yChecker';

import type {NodeResult, Result} from 'axe-core';

import type {A11yCheckerOptions} from '../A11yChecker';

type Target = string;

type RuleId = string;

export interface RuleRaw extends Omit<Result, 'nodes'> {
	nodes: Array<Target>;
}

export type NodeViolations = Record<RuleId, NodeResult>;

export type Violations = {
	nodes: Record<Target, NodeViolations>;
	rules: Record<RuleId, RuleRaw>;
};

function segmentViolationsByRulesAndNodes(
	violations: Array<Result>,
	prevViolations: Violations
) {
	return violations.reduce<Violations>(
		(prev, current) => {
			const {nodes, ...rule} = current;

			const targets = nodes.map((node) => {
				const target = node.target[0];

				if (prev.nodes[target]) {
					prev.nodes[target][current.id] = node;
				}
				else {
					prev.nodes[target] = {[current.id]: node};
				}

				return target;
			});

			if (!prev.rules[current.id]) {
				prev.rules[current.id] = {
					...rule,
					nodes: targets,
				};
			}
			else {
				prev.rules[current.id].nodes = [
					...new Set([...prev.rules[current.id].nodes, ...targets]),
				];
			}

			return prev;
		},
		{...prevViolations}
	);
}

const defaultState = {
	nodes: {},
	rules: {},
};

export default function useA11y(props: Omit<A11yCheckerOptions, 'callback'>) {
	const [violations, setViolations] = useState<Violations>(defaultState);

	useEffect(() => {
		const checker = new A11yChecker({
			callback: (results) =>
				results.violations.length &&
				setViolations((prevViolations) =>
					segmentViolationsByRulesAndNodes(
						results.violations,
						prevViolations
					)
				),
			...props,
		});

		checker.observe();

		return () => {
			checker.unobserve();
		};
	}, [props]);

	return violations;
}
