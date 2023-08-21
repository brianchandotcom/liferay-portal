/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {Action} from '../constants/actionTypes';
import {LayoutReportsData} from '../constants/types/LayoutReports';
import {SelectedItem} from '../constants/types/SelectedItem';
interface State {
	data?: LayoutReportsData;
	error?: string | null;
	languageId?: string;
	loading: boolean;
	selectedItem?: SelectedItem | null;
}
export declare const StoreDispatchContext: React.Context<React.Dispatch<
	Action
>>;
export declare const StoreStateContext: React.Context<State>;
export declare function StoreContextProvider({
	children,
	value,
}: {
	children: React.ReactNode;
	value: State;
}): JSX.Element;
export {};
