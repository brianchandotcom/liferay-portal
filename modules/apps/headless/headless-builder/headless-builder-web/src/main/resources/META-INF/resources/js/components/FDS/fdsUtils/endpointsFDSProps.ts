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

import {baseFDSProps} from './baseFDSProps';
import {itemMethodRenderer, itemPathRenderer} from './fdsRenderers';

export function getAPIApplicationsEndpointsFDSProps(
	urlPath: string,
	portletId: string
): IFrontendDataSetProps {
	return {
		...baseFDSProps,
		apiURL: urlPath,
		customDataRenderers: {
			itemMethodRenderer,
			itemPathRenderer,
		},
		emptyState: {
			description: '',
			image: '/states/empty_state.gif',
			title: Liferay.Language.get('no-api-endpoint-found'),
		},
		id: portletId,
		itemsActions: [
			{
				data: {
					id: 'editAPIApplicationEndpoint',
				},
				icon: 'pencil',
				label: Liferay.Language.get('edit'),
			},
			{
				icon: 'copy',
				id: 'copyEndpointURL',
				label: Liferay.Language.get('copy-url'),
			},
			{
				icon: 'trash',
				id: 'deleteAPIApplicationEndpoint',
				label: Liferay.Language.get('delete'),
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
							contentRenderer: 'itemMethodRenderer',
							fieldName: 'httpMethod',
							label: Liferay.Language.get('method'),
							localizeLabel: true,
						},
						{
							actionId: 'editAPIApplicationEndpoint',
							contentRenderer: 'itemPathRenderer',
							expand: false,
							fieldName: 'path',
							label: Liferay.Language.get('path'),
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
					],
				},
				thumbnail: 'table',
			},
		],
	};
}
