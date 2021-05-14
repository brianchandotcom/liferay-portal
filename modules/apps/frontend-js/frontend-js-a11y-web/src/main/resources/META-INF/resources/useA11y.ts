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

import {A11yChecker} from './A11yChecker';

import type {CheckResult, ImpactValue, Result} from 'axe-core';

import type {A11yCheckerOptions} from './A11yChecker';

export type Violation = {
	all: Array<CheckResult>;
	any: Array<CheckResult>;
	help: string;
	helpUrl: string;
	id: string;
	impact?: ImpactValue;
};

type Violations = {
	modifyIndex: number;
	target: string;
	violations: Array<Violation>;
};

function segmentViolationsByNode(
	violations: Array<Result>,
	prevViolations: Array<Violations>
) {
	const prevNodes = prevViolations.reduce<Record<string, Violations>>(
		(prev, current) => {

			// Revalidation if the target exists on the DOM

			if (document.querySelector(current.target)) {
				prev[current.target] = current;
			}

			return prev;
		},
		{} as Record<string, Violations>
	);

	const nodes = violations.reduce<Record<string, Violations>>(
		(prev, current) => {
			current.nodes.forEach((node) => {
				const {help, helpUrl, id, impact} = current;

				const violation = {
					all: node.all,
					any: node.any,
					help,
					helpUrl,
					id,
					impact,
				};

				const target = node.target[0];

				if (!prev[target]) {
					prev[target] = {
						modifyIndex: 0,
						target,
						violations: [violation],
					};
				}
				else {
					const hasViolation = prev[target].violations.find(
						(violation) => violation.id === id
					);

					if (!hasViolation) {
						prev[target].modifyIndex++;
						prev[target].violations.push(violation);
					}
				}
			});

			return prev;
		},
		prevNodes
	);

	return Object.values(nodes);
}

export default function useA11y(props: Omit<A11yCheckerOptions, 'callback'>) {
	const [violations, setViolations] = useState<Array<Violations>>([]);

	useEffect(() => {
		const checker = new A11yChecker({
			callback: (results) =>
				results.violations.length &&
				setViolations((prevViolations) =>
					segmentViolationsByNode(results.violations, prevViolations)
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
