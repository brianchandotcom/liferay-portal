/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactElement} from 'react';
export interface FilterImplementation<
	T extends FilterImplementationArgs<unknown>
> {
	Component: (args: T) => ReactElement;
	getOdataString: (args: T) => string;
	getSelectedItemsLabel: (args: T) => string;
}
export interface FilterImplementationArgs<T> {
	id: string;
	selectedData: T;
	setFilter: (args: SetFilterArgs) => void;
}
export interface SetFilterArgs {
	active?: boolean;
	id?: string;
	odataFilterString?: string;
	selectedData?: unknown;
}
interface FilterComponentArgs {
	id: string;
	moduleURL: string;
	type: 'clientExtension' | 'dateRange' | 'selection';
}
declare const FILTER_IMPLEMENTATIONS: {
	clientExtension: FilterImplementation<
		import('./implementation/clientExtensionFilter').ClientExtensionFilterImplementationArgs
	>;
	dateRange: FilterImplementation<
		import('./implementation/dateRangeFilter').DateRangeFilterImplementationArgs
	>;
	selection: FilterImplementation<
		import('./implementation/selectionFilter').SelectionFilterImplementationArgs
	>;
};
declare const Filter: ({
	id,
	moduleURL,
	type,
	...otherProps
}: FilterComponentArgs) => JSX.Element;
export {FILTER_IMPLEMENTATIONS};
export default Filter;
