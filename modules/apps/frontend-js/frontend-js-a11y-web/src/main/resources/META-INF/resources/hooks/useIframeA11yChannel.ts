/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
