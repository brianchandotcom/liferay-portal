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

type IframeId = string;

export interface RuleRaw extends Omit<Result, 'nodes'> {
	nodes: Array<Target>;
}

export type NodeViolations = Record<RuleId, NodeResult>;

export type Violations = {
	iframes: Record<IframeId, Array<Target>>;
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
				const targets = node.split('/');
				const target = targets[targets.length - 1];

				let element: Element | undefined | null;

				if (targets.length > 1) {
					element = (document.querySelector(targets[0]) as
						| HTMLIFrameElement
						| undefined)?.contentDocument?.querySelector(target);
				}
				else {
					element = document.querySelector(target);
				}

				if (element) {
					if (previousViolation.nodes[node]) {
						previousViolation.nodes[node][rule.id] =
							previousViolations.nodes[node][rule.id];
					}
					else {
						previousViolation.nodes[node] = {
							[rule.id]: previousViolations.nodes[node][rule.id],
						};
					}

					if (targets.length > 1) {
						previousViolation.iframes[targets[0]] = [
							...new Set([
								...(previousViolation.iframes[targets[0]] ??
									[]),
								node,
							]),
						];
					}

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

			return previousViolation;
		},
		{iframes: {}, nodes: {}, rules: {}} as Violations
	);

	return violations.reduce<Violations>((previousViolation, current) => {
		const {nodes, ...rule} = current;

		const staleTargets = new Set<string>();

		const targets = nodes.map((node) => {

			// The node is constructed by an `Array<string>` which more
			// than one element represents the element level compared
			// to iframes.
			//
			// If the target is of an element inside the iframe, the first
			// element of the Array will be the id of the iframe and the
			// last is element.
			//
			// ['#iframe', '.btn[type=\"submit\"]'] -> '#iframe/.btn[type=\"submit\"]'

			const target = node.target.join('/');

			if (node.target.length > 1) {
				const iframe = node.target[0];

				previousViolation.iframes[iframe] = [
					...new Set([
						...(previousViolation.iframes[iframe] ?? []),
						target,
					]),
				];
			}

			if (previousViolation.nodes[target]) {
				previousViolation.nodes[target][current.id] = node;
			}
			else {
				const element = document.querySelector(
					node.target[node.target.length - 1]
				);

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

					// Updates the new target value for all rules with
					// reference to the old value.

					Object.keys(previousViolation.nodes[previousNode]).forEach(
						(ruleId) => {
							previousViolation.rules[ruleId].nodes = [
								...previousViolation.rules[ruleId].nodes.filter(
									(node) => node !== previousNode
								),
								target,
							];
						}
					);

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
	iframes: {},
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
