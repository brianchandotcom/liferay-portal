/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IFrontendDataSetProps} from '@liferay/frontend-data-set-web';

import {ActionItem, DesignLibraryItem} from '../types';
import {DesignLibraryLinkRenderer} from './cell_renderers/DesignLibraryLinkRenderer';
enum TableCelRenderer {
	DESIGN_LIBRARY_LINK = 'designLibraryLink',
}

export default function DesignLibraryAdminFDSPropsTransformer(
	props: IFrontendDataSetProps
): IFrontendDataSetProps {
	const creationMenu = {
		primaryItems: [
			{
				label: Liferay.Language.get('new-design-library'),
			},
		],
	};

	return {
		...props,
		creationMenu,
		customRenderers: {
			tableCell: [
				{
					component: DesignLibraryLinkRenderer,
					name: TableCelRenderer.DESIGN_LIBRARY_LINK,
					type: 'internal',
				},
			],
		},
		hideManagementBarInEmptyState: true,
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
							contentRenderer:
								TableCelRenderer.DESIGN_LIBRARY_LINK,
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
							label: Liferay.Language.get('modified'),
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
					sticker: 'books',
					symbol: '',
					title: 'name',
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
						href: props.actions.find(
							(action) => action.data.id === 'edit'
						)?.href,
					};
				},

				thumbnail: 'cards2',
			},
		],
	};
}
