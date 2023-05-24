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

import {IFrontendDataSetProps} from '@liferay/frontend-data-set-web';
export interface fdsItem<T> {
	action: {
		id: string;
	};
	itemData: T;
	openSidePanel: ({url}: {url: string}) => void;
	value: LocalizedValue<string>;
}
export interface IFDSTableProps extends IFrontendDataSetProps {
	objectDefinitionExternalReferenceCode: string;
	url: string;
}
export declare function formatActionURL(url: string, id: number): string;
export declare const defaultDataSetProps: {
	actionParameterName: string;
	currentURL: string;
	customViewsEnabled: boolean;
	pagination: {
		deltas: {
			label: number;
		}[];
		initialDelta: number;
		initialPageNumber: number;
	};
	showManagementBar: boolean;
	showPagination: boolean;
	showSearch: boolean;
};
