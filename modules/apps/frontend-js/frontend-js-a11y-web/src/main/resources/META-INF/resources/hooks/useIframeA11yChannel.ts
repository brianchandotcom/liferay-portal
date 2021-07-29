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

import {useEffect, useRef} from 'react';

import {SDK} from '../SDK';

import type {Recv} from '../SDK';
import type {Violations} from './useA11y';

type FilteredViolations = Omit<Violations, 'iframes'>;

function segmentViolationsByIframe(
	nodes: Array<string>,
	violations: FilteredViolations
) {
	return nodes.reduce(
		(previousViolations, target) => {
			if (!violations.nodes[target]) {
				return previousViolations;
			}

			const ruleIds = Object.keys(violations.nodes[target]);

			const rules = ruleIds.reduce((previousRules, ruleId) => {
				previousRules[ruleId] = violations.rules[ruleId];

				return previousRules;
			}, {} as FilteredViolations['rules']);

			previousViolations.rules = {...previousViolations.rules, ...rules};
			previousViolations.nodes[target] = violations.nodes[target];

			return previousViolations;
		},
		{nodes: {}, rules: {}} as FilteredViolations
	);
}

export default function useIframeA11yChannel<T, K>(
	iframes: Record<string, Array<string>>,
	violations: FilteredViolations,
	onMessage: Recv<T, K>
) {
	const sdkRef = useRef(new SDK(onMessage, false));

	useEffect(() => {
		for (const iframe in iframes) {
			const iframeWindow = (document.querySelector(
				iframe
			) as HTMLIFrameElement)?.contentWindow;

			if (!iframeWindow) {
				continue;
			}

			sdkRef.current.channel.tx(
				iframeWindow,
				segmentViolationsByIframe(iframes[iframe], violations)
			);
		}
	}, [sdkRef, iframes, violations]);

	useEffect(() => {
		const sdk = sdkRef.current;

		sdk.observe();

		return () => {
			sdk.unobserve();
		};
	}, [sdkRef]);
}
