/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	IFrontendDataSetProps,

	// @ts-ignore

} from '@liferay/frontend-data-set-web';

export interface fdsItem<T> {
	action: {id: string};
	itemData: T;
	openSidePanel: ({url}: {url: string}) => void;
	value: LocalizedValue<string>;
}

export interface IFDSTableProps extends IFrontendDataSetProps {
	objectDefinitionExternalReferenceCode: string;
	url: string;
}

export function formatActionURL(url: string, id: number) {
	if (!url) {
		return '';
	}

	return url
		.replace(new RegExp('{(.*?)}', 'mg'), id.toString())
		.replace(new RegExp('(%7B.*?%7D)', 'mg'), id.toString());
}

export const defaultDataSetProps = {
	actionParameterName: '',
	currentURL: window.location.pathname + window.location.search,
	customViewsEnabled: false,
	pagination: {
		deltas: [
			{
				label: 4,
			},
			{
				label: 8,
			},
			{
				label: 20,
			},
			{
				label: 40,
			},
			{
				label: 60,
			},
		],
		initialDelta: 0,
		initialPageNumber: 0,
	},
	showManagementBar: true,
	showPagination: true,
	showSearch: true,
};
