/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {Node} from './TreeviewContext';
export default function NodeList({
	NodeComponent,
	nodes,
	onBlur,
	onFocus,
	onMouseDown,
	role,
	tabIndex,
}: IProps): JSX.Element | null;
interface IProps {
	NodeComponent: React.ComponentType<{
		node: Node;
	}>;
	nodes: Node[];
	onBlur?: () => void;
	onFocus?: (event: React.FocusEvent<HTMLDivElement>) => void;
	onMouseDown?: (event: React.MouseEvent<HTMLDivElement>) => void;
	role?: string;
	tabIndex?: number;
}
export {};
