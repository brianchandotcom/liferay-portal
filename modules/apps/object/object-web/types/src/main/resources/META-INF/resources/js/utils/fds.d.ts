/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
