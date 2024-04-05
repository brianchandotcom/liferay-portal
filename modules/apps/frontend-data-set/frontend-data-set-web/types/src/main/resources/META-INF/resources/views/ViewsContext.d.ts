/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

export interface IViewsContext {
	activeCustomViewId: null | string;
	activeView: any;
	customViews: any;
	customViewsEnabled: boolean;
	filters: Array<any>;
	modifiedFields: any;
	paginationDelta: any;
	sorts: Array<any>;
	views: Array<any>;
	visibleFieldNames: any;
}
export declare type TViewsContextDispatch = ({
	type,
	value,
}: {
	type: string;
	value: any;
}) => void;
declare const ViewsContext: import('react').Context<[IViewsContext, any]>;
export default ViewsContext;
