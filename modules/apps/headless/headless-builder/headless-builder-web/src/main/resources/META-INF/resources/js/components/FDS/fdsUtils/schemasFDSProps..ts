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

export function getAPIApplicationsSchemasFDSProps(
	urlPath: string,
	portletId: string
): IFrontendDataSetProps {
	return {
		...baseFDSProps,
		apiURL: urlPath,
		emptyState: {
			description: '',
			image: '/states/empty_state.gif',
			title: Liferay.Language.get('no-api-schema-found'),
		},
		id: portletId,
		itemsActions: [
			{
				data: {
					id: 'editAPIApplicationSchema',
				},
				icon: 'pencil',
				label: Liferay.Language.get('edit'),
			},
			{
				icon: 'trash',
				id: 'deleteAPIApplicationSchema',
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
							actionId: 'editAPIApplicationSchema',
							contentRenderer: 'actionLink',
							fieldName: 'name',
							label: Liferay.Language.get('name'),
							localizeLabel: true,
							sortable: true,
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
