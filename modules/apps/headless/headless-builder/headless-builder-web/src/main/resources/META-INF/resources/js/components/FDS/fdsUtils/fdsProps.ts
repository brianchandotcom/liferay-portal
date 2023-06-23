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

import {openEditURL} from '../../utils/urlUtil';
import {getAPIApplicationsFDSFilters} from './fdsFilters';
import {itemPathRenderer, itemStatusRenderer} from './fdsRenderers';

export function getAPIApplicationsFDSProps(
	apiApplicationsURLPath: string,
	editURL: string,
	portletId: string
): IFrontendDataSetProps {
	return {
		apiURL: apiApplicationsURLPath,
		currentURL: window.location.pathname + window.location.search,
		customDataRenderers: {
			itemPathRenderer,
			itemStatusRenderer,
		},
		filters: getAPIApplicationsFDSFilters(),
		id: portletId,
		itemsActions: [
			{
				data: {
					id: 'editAPIApplication',
				},
				icon: 'pencil',
				label: Liferay.Language.get('edit'),
				onClick: ({itemData}: FDSItem) =>
					openEditURL({editURL, id: itemData.id, portletId}),
			},
			{
				data: {
					id: 'delete',
					method: 'delete',
					permissionKey: 'delete',
				},
				icon: 'trash',
				id: 'deleteAPIApplication',
				label: Liferay.Language.get('delete'),
			},
		],
		pagination: {
			initialDelta: 20,
			initialPageNumber: 0,
		},
		portletId,
		showManagementBar: true,
		showPagination: true,
		showSearch: true,
		sidePanelId: 'none',
		style: 'fluid',
		views: [
			{
				contentRenderer: 'table',
				label: 'Table',
				name: 'table',
				schema: {
					fields: [
						{
							actionId: 'editAPIApplication',
							contentRenderer: 'actionLink',
							fieldName: 'title',
							label: Liferay.Language.get('title'),
							localizeLabel: true,
							sortable: true,
						},
						{
							contentRenderer: 'itemPathRenderer',
							expand: false,
							fieldName: 'baseURL',
							label: Liferay.Language.get('url'),
							localizeLabel: true,
						},
						{
							fieldName: 'description',
							label: Liferay.Language.get('description'),
							localizeLabel: true,
							truncate: true,
						},
						{
							contentRenderer: 'date',
							fieldName: 'dateModified',
							label: Liferay.Language.get('last-updated'),
							localizeLabel: true,
							sortable: true,
						},
						{
							contentRenderer: 'itemStatusRenderer',
							fieldName: 'applicationStatus',
							label: Liferay.Language.get('status'),
							sortable: true,
						},
					],
				},
				thumbnail: 'table',
			},
		],
	};
}
