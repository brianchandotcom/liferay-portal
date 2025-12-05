/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {createContext} from 'react';

import {IFilter} from '../management_bar/controls/filters/Filter';

export interface IViewsContext {
	activeCustomViewId: null | string;
	activeView: any;
	customViews: any;
	customViewsEnabled: boolean;
	filters: IFilter[];
	filtersGroups: Array<any>;
	modifiedFields: any;
	paginationDelta: any;
	sorts: Array<any>;
	viewUpdated: boolean;
	views: Array<any>;
	visibleFieldNames: any;
}

export type TViewsContextDispatch = (
	params:
		| {type: string; value: any}
		| ((dispatch: TViewsContextDispatch) => void)
) => void;

const initialState: IViewsContext = {
	activeCustomViewId: null,
	activeView: null,
	customViews: {},
	customViewsEnabled: false,
	filters: [],
	filtersGroups: [],
	modifiedFields: {},
	paginationDelta: null,
	sorts: [],
	viewUpdated: false,
	views: [],
	visibleFieldNames: {},
};

const ViewsContext = createContext<[IViewsContext, TViewsContextDispatch]>([
	initialState,
	() => {},
]);

export default ViewsContext;
