/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useContext, useEffect, useRef} from 'react';

import TreeviewContext from './TreeviewContext';

export default function useFocus<T extends HTMLElement>(nodeId: string) {
	const {state} = useContext(TreeviewContext);

	const {active, focusedNodeId} = state;

	const focusableRef = useRef<T>(null);

	useEffect(() => {
		if (active && nodeId === focusedNodeId && focusableRef.current) {
			focusableRef.current.focus();
		}
	}, [active, focusedNodeId, nodeId]);

	return focusableRef;
}
