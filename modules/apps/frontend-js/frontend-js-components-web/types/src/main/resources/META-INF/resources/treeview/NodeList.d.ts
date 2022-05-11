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
