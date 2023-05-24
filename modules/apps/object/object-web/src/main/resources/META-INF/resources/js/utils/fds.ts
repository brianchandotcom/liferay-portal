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
