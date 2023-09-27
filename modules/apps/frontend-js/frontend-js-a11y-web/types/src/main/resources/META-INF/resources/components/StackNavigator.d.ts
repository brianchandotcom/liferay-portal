/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
declare type StackNavigatorProps<T> = {
	activePage: number;
	children: Array<React.ReactElement>;
	onActiveChange: (index: number) => void;
	onParamsChange: (payload: T) => void;
	params: T | undefined;
};
export declare function StackNavigator<T>({
	activePage,
	children,
	onActiveChange,
	onParamsChange,
	params,
}: StackNavigatorProps<T>): React.ReactElement<
	any,
	string | React.JSXElementConstructor<any>
>;
export {};
