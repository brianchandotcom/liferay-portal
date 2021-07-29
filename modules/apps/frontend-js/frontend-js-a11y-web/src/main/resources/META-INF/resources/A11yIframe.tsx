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
