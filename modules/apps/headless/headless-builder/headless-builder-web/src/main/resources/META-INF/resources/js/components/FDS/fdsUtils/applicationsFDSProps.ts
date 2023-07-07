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
import {baseFDSProps} from './baseFDSProps';
import {getAPIApplicationsFDSFilters} from './fdsFilters';
import {itemStatusRenderer, itemURLRenderer} from './fdsRenderers';

export function getAPIApplicationsFDSProps(
	apiApplicationsURLPath: string,
	editURL: string,
	portletId: string
): IFrontendDataSetProps {
	return {
		...baseFDSProps,
		apiURL: apiApplicationsURLPath,
		customDataRenderers: {
			itemStatusRenderer,
			itemURLRenderer,
		},
		emptyState: {
			description: '',
			image: '/states/empty_state.gif',
			title: Liferay.Language.get('no-api-application-found'),
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
				onClick: ({itemData}: FDSItem<APIApplicationItem>) =>
					openEditURL({editURL, id: itemData.id, portletId}),
			},
			{
				icon: 'trash',
				id: 'deleteAPIApplication',
				label: Liferay.Language.get('delete'),
			},
			{
				icon: 'change',
				id: 'changePublicationStatus',
				label: Liferay.Language.get('change-publication-status'),
			},
		],
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
							contentRenderer: 'itemURLRenderer',
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
							contentRenderer: 'dateTime',
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
