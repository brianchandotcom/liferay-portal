/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IFrontendDataSetProps} from '@liferay/frontend-data-set-web';

import {baseFDSProps} from './baseFDSProps';

export function getAPISchemasFDSProps(
	urlPath: string,
	portletId: string,
	setMainSchemaNav: ({edit}: {edit: number}) => void
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
				onClick: ({itemData}: FDSItem<APIApplicationSchemaItem>) => {
					setMainSchemaNav({edit: itemData.id});
				},
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
