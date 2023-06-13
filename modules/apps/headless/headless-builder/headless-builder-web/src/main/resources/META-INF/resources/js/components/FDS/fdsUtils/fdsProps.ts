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

import {getAPIApplicationsFDSFilters} from './fdsFilters';
import {itemPathRenderer, itemStatusRenderer} from './fdsRenderers';

export function getAPIApplicationsFDSProps(
	apiURL: string,
	portletId: string
): IFrontendDataSetProps {
	return {
		apiURL,
		currentURL: window.location.pathname + window.location.search,
		customDataRenderers: {
			itemPathRenderer,
			itemStatusRenderer,
		},
		filters: getAPIApplicationsFDSFilters(),
		id: portletId,
		itemsActions: [
			{
				icon: 'view',
				id: 'editApiApplication',
				label: Liferay.Language.get('view'),
			},
			{
				data: {
					confirmationMessage: Liferay.Language.get(
						'this-action-cannot-be-undone'
					),
					id: 'delete',
					method: 'delete',
					permissionKey: 'delete',
				},
				icon: 'trash',
				label: Liferay.Language.get('delete'),
				target: 'headless',
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
		style: 'fluid',
		views: [
			{
				contentRenderer: 'table',
				label: 'Table',
				name: 'table',
				schema: {
					fields: [
						{
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
