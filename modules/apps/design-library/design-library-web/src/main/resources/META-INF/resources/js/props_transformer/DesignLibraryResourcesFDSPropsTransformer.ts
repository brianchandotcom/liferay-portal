/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IFrontendDataSetProps} from '@liferay/frontend-data-set-web';

import {ActionItem, DesignLibraryItem} from '../types';

export default function DesignLibraryResourcesFDSPropsTransformer(
	props: IFrontendDataSetProps
): IFrontendDataSetProps {
	const creationMenu = {
		primaryItems: [
			{
				label: Liferay.Language.get('new-style-book'),
			},
		],
	};

	return {
		...props,
		creationMenu,
		hideManagementBarInEmptyState: true,
		selectedItemsKey: 'embedded.id',
		selectionType: 'multiple',
		showSelectAll: true,
		views: [
			{
				contentRenderer: 'table',
				default: true,
				label: Liferay.Language.get('table'),
				name: 'table',
				schema: {
					fields: [
						{
							actionId: 'edit',
							contentRenderer: 'actionLink',
							fieldName: 'name',
							label: Liferay.Language.get('title'),
							localizeLabel: true,
							sortable: true,
						},
						{
							fieldName: 'creatorUserId',
							label: Liferay.Language.get('author'),
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
			{
				contentRenderer: 'cards',
				label: Liferay.Language.get('cards'),
				name: 'cards',
				schema: {
					description: 'dateModified',
					symbol: '',
					title: 'title',
				},
				setItemComponentProps: ({
					item,
					props,
				}: {
					item: DesignLibraryItem;
					props: {actions: ActionItem[]};
				}) => {
					return {
						...props,
						description: `asdfasdf: ${new Date(item.dateModified).toLocaleString()}`,
					};
				},
				thumbnail: 'cards2',
			},
		],
	};
}
