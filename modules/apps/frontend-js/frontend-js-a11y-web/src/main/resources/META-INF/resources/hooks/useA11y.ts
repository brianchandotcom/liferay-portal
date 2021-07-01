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
	previousViolations: Violations
) {

	// Maintains a list of target elements to ensure that targets are unique
	// and we don't add violations with different selectors for the
	// same element.

	const elements = new WeakMap<Element, string>();

	// Validates the previous list of violations by removing elements that are
	// no longer visible in the DOM.

	const revalidatedViolations = Object.values(
		previousViolations.rules
	).reduce(
		(previousViolation, rule) => {

			// Revalidation if the target exists on the DOM

			const nodes = rule.nodes.filter((node) => {
				const element = document.querySelector(node);

				if (!element) {
					delete previousViolation.nodes[node][rule.id];

					if (!Object.values(previousViolation.nodes[node]).length) {
						delete previousViolation.nodes[node];
					}
				}
				else {
					elements.set(element, node);
				}

				return element;
			});

			if (nodes.length) {
				previousViolation.rules[rule.id] = {
					...rule,
					nodes,
				};
			}
			else {
				delete previousViolation.rules[rule.id];
			}

			return previousViolation;
		},
		{...previousViolations}
	);

	return violations.reduce<Violations>((previousViolation, current) => {
		const {nodes, ...rule} = current;

		const staleTargets = new Set<string>();

		const targets = nodes.map((node) => {
			const target = node.target[0];

			if (previousViolation.nodes[target]) {
				previousViolation.nodes[target][current.id] = node;
			}
			else {
				const element = document.querySelector(target);

				// The selector can be different for each analysis, but the
				// element will be the same, we just keep the target updated
				// to prevent two violations pointing to the same element and
				// we keep duplicate information.

				if (element && elements.has(element)) {
					const previousNode = elements.get(element) as string;

					previousViolation.nodes[target] = {
						...previousViolation.nodes[previousNode],
						[current.id]: node,
					};

					delete previousViolation.nodes[previousNode];

					staleTargets.add(previousNode);
				}
				else {
					previousViolation.nodes[target] = {[current.id]: node};
				}
			}

			return target;
		});

		if (!previousViolation.rules[current.id]) {
			previousViolation.rules[current.id] = {
				...rule,
				nodes: targets,
			};
		}
		else {

			// Removes stale targets from rules that are no longer valid or
			// the selector has been updated.

			const nodes = previousViolation.rules[current.id].nodes.filter(
				(node) => !staleTargets.has(node)
			);

			previousViolation.rules[current.id].nodes = [
				...new Set([...nodes, ...targets]),
			];
		}

		staleTargets.clear();

		return previousViolation;
	}, revalidatedViolations);
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
