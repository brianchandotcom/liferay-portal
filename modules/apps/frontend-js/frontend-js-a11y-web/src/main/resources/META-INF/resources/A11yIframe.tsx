/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {ViolationPopover} from './components/ViolationPopover';
import useIframeClient, {Kind} from './hooks/useIframeClient';

import type {Violations} from './hooks/useA11y';

const initialState = {
	nodes: {},
	rules: {},
};

export function A11yIframe() {
	const [violations, dispatch] = useIframeClient<Omit<Violations, 'iframes'>>(
		initialState
	);

	return Object.keys(violations.nodes).map((target, index) => (
		<ViolationPopover
			key={`${target}:${index}`}
			onClick={(target, ruleId) => dispatch(Kind.Click, {ruleId, target})}
			rules={violations.rules}
			target={target}
			violations={Object.keys(violations.nodes[target])}
		/>
	));
}
