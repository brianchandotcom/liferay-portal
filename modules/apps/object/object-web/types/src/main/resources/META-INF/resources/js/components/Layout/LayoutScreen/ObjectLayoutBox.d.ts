/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {BoxType, TObjectLayoutRow} from '../types';
interface ObjectLayoutBoxProps extends React.HTMLAttributes<HTMLElement> {
	boxIndex: number;
	collapsable: boolean;
	label: string;
	objectLayoutRows?: TObjectLayoutRow[];
	tabIndex: number;
	type: BoxType;
}
export declare function ObjectLayoutBox({
	boxIndex,
	collapsable,
	label,
	objectLayoutRows,
	tabIndex,
	type,
}: ObjectLayoutBoxProps): JSX.Element;
export {};
