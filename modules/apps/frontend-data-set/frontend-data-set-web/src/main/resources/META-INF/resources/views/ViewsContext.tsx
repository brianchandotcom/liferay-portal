/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {createContext} from 'react';

import {IFilter} from '../management_bar/controls/filters/Filter';

export interface IViewsContext {
	activeSnapshotERC: null | string;
	activeView: any;
	defaultSnapshot?: any;
	groupedFDSFilters: Array<any>;
	modifiedFields: any;
	paginationDelta: any;
	snapshotUpdated: boolean;
	snapshots: Array<ISnapshot>;
	snapshotsEnabled: boolean;
	sorts: Array<any>;
	views: Array<any>;
	visibleFieldNames: any;
}

export interface ISnapshot {
	configuration?: any;
	erc: string;
	label: string;
}

export type TViewsContextDispatch = ({
	type,
	value,
}: {
	type: string;
	value: any;
}) => void;

const ViewsContext = createContext<[IViewsContext, any]>([
	{
		activeSnapshotERC: null,
		activeView: null,
		groupedFDSFilters: [],
		modifiedFields: {},
		paginationDelta: null,
		snapshotUpdated: false,
		snapshots: [],
		snapshotsEnabled: false,
		sorts: [],
		views: [],
		visibleFieldNames: {},
	},
	() => {},
]);

export default ViewsContext;
